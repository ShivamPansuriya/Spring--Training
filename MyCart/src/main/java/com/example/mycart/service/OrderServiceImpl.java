package com.example.mycart.service;

import com.example.mycart.exception.ApiException;
import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.CartItem;
import com.example.mycart.model.Order;
import com.example.mycart.model.OrderItems;
import com.example.mycart.payloads.OrderDTO;
import com.example.mycart.repository.OrderItemsRepository;
import com.example.mycart.repository.OrderRepository;
import com.example.mycart.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
//@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public class OrderServiceImpl extends AbstractGenericService<Order,OrderDTO, Long> implements OrderService
{
    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Order create(Long userId)
    {
        var user = userService.findById(userId);

        var cartItems = cartService.getCartItems(userId);

        var validProducts = cartItems.stream()
                .filter(cartItem -> (inventoryService.getInventoryByProduct(cartItem.getProductId()).getQuantity() - cartItem.getQuantity())>0)
                .toList();

        if (validProducts.isEmpty()) {
            throw new ApiException("Cannot create an order with an empty cart");
        }

        var order = new Order();

        order.setUserId(user.getId());

        order.setOrderDate(LocalDateTime.now());

        order.setStatus(OrderStatus.PENDING);

        var savedOrder = repository.save(order);

        List<OrderItems> orderItems = validProducts.stream()
                .map(cartItem -> createOrderItem(order, cartItem))
                .toList();

        order.setTotalAmount(calculateTotalAmount(orderItems));

        cartService.clearCart(userId);

        return savedOrder;
    }

    @Override
    @Transactional
    public List<Order> getOrdersByUser(Long userId)
    {
        var user = userService.findById(userId);

        return repository.findByUserIdOrderByOrderDateDesc(user.getId());
    }

    @Override
    @Transactional
    public Page<Order> getOrdersByUser(Long userId, int pageNo)
    {
        var user = userService.findById(userId);

        return repository.findByUserIdOrderByOrderDateDesc(user.getId(), PageRequest.of(pageNo,10));

    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status)
    {
        var order = findById(orderId);

        if(order.getStatus().ordinal() < OrderStatus.SHIPPED.ordinal() && status.ordinal() >= OrderStatus.SHIPPED.ordinal())
        {
            orderItemsRepository.findOrderItemsByOrderIdEquals(orderId).forEach(orderItem ->
            {
                var inventory = inventoryService.getInventoryByProduct(orderItem.getProductId());

                inventory.setQuantity(inventory.getQuantity() - orderItem.getQuantity());
            });
        }

        order.setStatus(status);

        return repository.save(order);

    }

    @Override
    @Transactional
    public Order cancelOrder(Long orderId)
    {
        var order = findById(orderId);

        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new ApiException("Cannot cancel an order that has been shipped or delivered");
        }

        order.setStatus(OrderStatus.CANCELLED);

        return repository.save(order);

    }

    @Override
    public Order removeOrderItem(Long orderId, Long productId)
    {
        var order = findById(orderId);

        if(order.getStatus().ordinal() >= OrderStatus.SHIPPED.ordinal())
        {
            throw new ApiException("cannot remove item that is shipped,ordered or cancelled");
        }

        var product = productService.findById(productId);

        var orderItem = orderItemsRepository.findByOrderIdAndProductId(order.getId(),product.getId())
                .orElseThrow(()-> new ResourceNotFoundException("OrderItem", "id", productId));

        orderItemsRepository.delete(orderItem);

        return order;
    }

    @Override
    public Page<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, int pageNo)
    {
        return repository.findOrderBetweenDate(startDate,endDate, PageRequest.of(pageNo,10));
    }

    @Override
    public Page<OrderItems> findOrderItemsByOrder(Long orderId, int pageNo) {
        return orderItemsRepository.findOrderItemsByOrder(orderId,PageRequest.of(pageNo,10));
    }

    private OrderItems createOrderItem(Order order, CartItem cartItem)
    {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(cartItem.getProductId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(productService.findById(cartItem.getProductId()).getPrice());
        return orderItemsRepository.save(orderItem);
    }

    private BigDecimal calculateTotalAmount(List<OrderItems> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return repository;
    }

    @Override
    protected Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    protected Class<OrderDTO> getDtoClass() {
        return OrderDTO.class;
    }

}

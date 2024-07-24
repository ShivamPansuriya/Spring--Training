package com.example.mycart.service;

import com.example.mycart.exception.ApiException;
import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.CartItem;
import com.example.mycart.model.Order;
import com.example.mycart.model.OrderItems;
import com.example.mycart.payloads.inheritDTO.OrderDTO;
import com.example.mycart.repository.OrderItemsRepository;
import com.example.mycart.repository.OrderRepository;
import com.example.mycart.utils.GenericSpecification;
import com.example.mycart.utils.OrderStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
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

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public Order create(Long userId)
    {
        var user = userService.findUserById(userId);

        var cartItems = cartService.getCartItems(userId);

        var validProducts = cartItems.stream()
                .filter(cartItem -> (inventoryService.findById(productService.findByProductId(cartItem.getProductId()).getInventoryId()).getQuantity() - cartItem.getQuantity())>0)
                .toList();

        if (validProducts.isEmpty()) {
            throw new ApiException("Cannot create an order with an empty cart");
        }

        var order = new Order();

        order.setUserId(user.getId());

        order.setOrderDate(LocalDateTime.now());

        order.setStatus(OrderStatus.PENDING);

        List<OrderItems> orderItems = validProducts.stream()
                .map(cartItem -> createOrderItem(order, cartItem))
                .toList();

//        order.setOrderItems(orderItems);

        order.setTotalAmount(calculateTotalAmount(orderItems));

        var savedOrder = repository.save(order);

        user.getOrders().add(savedOrder.getId());

        cartService.clearCart(userId);

        return savedOrder;
    }

    public Order findOrderById(Long orderId)
    {
        return repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order","id",orderId));
    }

//    @Override
//    @Cacheable
//    public OrderDTO getOrderById(Long orderId)
//    {
//        var order = findOrderById(orderId);
//
//        return mapper.map(order, OrderDTO.class);
//    }

    @Override
    @Transactional
    public List<Order> getOrdersByUser(Long userId)
    {
        var user = userService.findUserById(userId);

        return repository.findByUserIdOrderByOrderDateDesc(user.getId());

    }

    @Override
    @Transactional
    @CachePut
    public Order updateOrderStatus(Long orderId, OrderStatus status)
    {
        var order = findOrderById(orderId);

        if(order.getStatus().ordinal() < OrderStatus.SHIPPED.ordinal() && status.ordinal() >= OrderStatus.SHIPPED.ordinal())
        {
            order.getOrderItemsId().forEach(orderItemId ->
            {
                var orderItem = orderItemsRepository.findById(orderItemId).orElseThrow(()->new ResourceNotFoundException("orderItem","id",orderItemId));

                var inventoryId = productService.findByProductId(orderItem.getProductId()).getInventoryId();

                var inventory = inventoryService.findById(inventoryId);

                inventory.setQuantity(inventory.getQuantity() - orderItem.getQuantity());
            });
        }

        order.setStatus(status);

        return repository.save(order);

    }

    @Override
    @Transactional
    @CacheEvict
    public Order cancelOrder(Long orderId)
    {
        var order = findOrderById(orderId);

        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new ApiException("Cannot cancel an order that has been shipped or delivered");
        }

        order.setStatus(OrderStatus.CANCELLED);

        return repository.save(order);

    }

    @Override
    @CachePut
    public Order removeOrderItem(Long orderId, Long productId)
    {
        var order = findOrderById(orderId);

        if(order.getStatus().ordinal() >= OrderStatus.SHIPPED.ordinal())
        {
            throw new ApiException("cannot remove item that is shipped,ordered or cancelled");
        }

        var product = productService.findByProductId(productId);

        var orderItem = orderItemsRepository.findByOrderIdAndProductId(order.getId(),product.getId())
                .orElseThrow(()-> new ResourceNotFoundException("OrderItem", "id", productId));

        order.getOrderItemsId().remove(orderItem.getId());

        orderItemsRepository.delete(orderItem);

        return order;
    }

    @Override
    public List<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate)
    {
        return repository.findOrderBetweenDate(startDate,endDate);
    }

    @Override
    public Page<OrderItems> findOrderItemsByOrder(Long orderId, int pageNo) {
        return orderItemsRepository.findOrderItemsByOrderId(orderId,PageRequest.of(pageNo,10));
    }

//    @Override
//    public Page<Order> findOrderByUser(Long userId, int pageNo) {
//        return repository.findAll(GenericSpecification.getList("userId",userId), PageRequest.of(pageNo,10));
//    }


    private OrderItems createOrderItem(Order order, CartItem cartItem)
    {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(cartItem.getProductId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(productService.findByProductId(cartItem.getProductId()).getPrice());
        return orderItem;
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

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
    private UserService userService;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderDTO create(Long userId)
    {
        var user = userService.findUserById(userId);

        var cartItems = cartService.getCartItems(userId);

        var validProducts = cartItems.stream()
                .filter(cartItem -> (cartItem.getProduct().getInventory().getQuantity() - cartItem.getQuantity())>0)
                .toList();

        if (validProducts.isEmpty()) {
            throw new ApiException("Cannot create an order with an empty cart");
        }

        var order = new Order();

        order.setUser(user);

        order.setOrderDate(LocalDateTime.now());

        order.setStatus(OrderStatus.PENDING);

        List<OrderItems> orderItems = validProducts.stream()
                .map(cartItem -> createOrderItem(order, cartItem))
                .toList();

        order.setOrderItems(orderItems);

        order.setTotalAmount(calculateTotalAmount(orderItems));

        user.getOrders().add(order);

        Order savedOrder = repository.save(order);

        cartService.clearCart(userId);

        return mapper.map(savedOrder, OrderDTO.class);
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
    public List<OrderDTO> getOrdersByUser(Long userId)
    {
        var user = userService.findUserById(userId);

        var orders = repository.findByUserOrderByOrderDateDesc(user);

        return orders.stream().map(order -> mapper.map(order, OrderDTO.class)).toList();
    }

    @Override
    @Transactional
    @CachePut
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status)
    {
        var order = findOrderById(orderId);

        if(order.getStatus().ordinal() < OrderStatus.SHIPPED.ordinal() && status.ordinal() >= OrderStatus.SHIPPED.ordinal())
        {
            order.getOrderItems().forEach(orderItem ->
            {
                var inventory = orderItem.getProduct().getInventory();
                inventory.setQuantity(inventory.getQuantity() - orderItem.getQuantity());
            });
        }

        order.setStatus(status);

        var updatedOder =  repository.save(order);

        return mapper.map(updatedOder, OrderDTO.class);
    }

    @Override
    @Transactional
    @CacheEvict
    public OrderDTO cancelOrder(Long orderId)
    {
        var order = findOrderById(orderId);

        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new ApiException("Cannot cancel an order that has been shipped or delivered");
        }

        order.setStatus(OrderStatus.CANCELLED);

        var updatedOrder = repository.save(order);

        return mapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
    @CachePut
    public OrderDTO removeOrderItem(Long orderId, Long productId)
    {
        var order = findOrderById(orderId);

        if(order.getStatus().ordinal() >= OrderStatus.SHIPPED.ordinal())
        {
            throw new ApiException("cannot remove item that is shipped,ordered or cancelled");
        }

        var product = productService.findByProductId(productId);

        var orderItem = orderItemsRepository.findByOrderAndProduct(order,product)
                .orElseThrow(()-> new ResourceNotFoundException("OrderItem", "id", productId));

        order.getOrderItems().remove(orderItem);

        orderItemsRepository.delete(orderItem);

        return mapper.map(order,OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate)
    {
        var orders = repository.findOrderBetweenDate(startDate,endDate);

        return orders.stream().map(order -> mapper.map(order, OrderDTO.class)).toList();
    }


    private OrderItems createOrderItem(Order order, CartItem cartItem)
    {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrder(order);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getProduct().getPrice());
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

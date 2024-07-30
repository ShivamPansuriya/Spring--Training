package com.example.mycart.service;

import com.example.mycart.exception.ApiException;
import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.CartItem;
import com.example.mycart.model.Order;
import com.example.mycart.model.OrderItems;
import com.example.mycart.payloads.OrderDTO;
import com.example.mycart.repository.BaseRepository;
import com.example.mycart.repository.OrderItemsRepository;
import com.example.mycart.repository.OrderRepository;
import com.example.mycart.repository.SoftDeletesRepository;
import com.example.mycart.utils.OrderStatus;
import com.example.mycart.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Service
//@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
@Slf4j
public class OrderServiceImpl extends AbstractGenericService<Order,OrderDTO, Long> implements OrderService
{
    private final OrderRepository repository;

    private final OrderItemsRepository orderItemsRepository;

    private final CartService cartService;

    private final ProductService productService;

    private final InventoryService inventoryService;

    private final ReentrantLock orderCreationLock = new ReentrantLock(true);

    public OrderServiceImpl(OrderRepository repository, OrderItemsRepository orderItemsRepository, CartService cartService, ProductService productService, InventoryService inventoryService) {
        this.repository = repository;
        this.orderItemsRepository = orderItemsRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @Override
    @Transactional
    public Order create(Long userId)
    {
        var cartItems = cartService.getCartItems(userId);

        orderCreationLock.lock();

        List<CartItem> validProducts;

        try {
            validProducts = cartItems.stream()
                    .filter(cartItem ->
                    {
                        var inventory = inventoryService.getInventoryByProduct(cartItem.getProductId());
                        if(inventory==null)
                            return false;
                        var remains = inventory.getQuantity() - cartItem.getQuantity();
                        if (remains > 0) {
                            inventory.setQuantity(remains);
                            return true;
                        }
                        return false;
                    })
                    .toList();
        }
        finally {
            orderCreationLock.unlock();
        }

        if (validProducts.isEmpty()) {
            throw new ApiException("Cannot create an order with an empty cart");
        }

        var order = new Order();

        order.setUserId(userId);

        order.setOrderDate(LocalDateTime.now());

        order.setStatus(OrderStatus.PENDING);

        order.setTotalAmount(calculateTotalAmount(validProducts));

        var savedOrder = repository.save(order);

        CompletableFuture.supplyAsync(()->
        {
            validProducts.forEach(cartItem -> createOrderItem(order, cartItem));

            log.debug("Long order process Complete");

            cartService.clearCart(userId);

            return true;
        });

        return savedOrder;
    }

    @Override
    @Transactional
    public List<Order> getOrdersByUser(Long userId)
    {
        return repository.findByUserIdOrderByOrderDateDesc(userId);
    }

    @Override
    @Transactional
    public Page<Order> getOrdersByUser(Long userId, int pageNo)
    {
        return repository.findByUserIdOrderByOrderDateDesc(userId, PageRequest.of(pageNo,10));

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

        var orderItem = orderItemsRepository.findByOrderIdAndProductId(orderId,productId)
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

    private void createOrderItem(Order order, CartItem cartItem)
    {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(cartItem.getProductId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(productService.findById(cartItem.getProductId()).getPrice());
        orderItemsRepository.save(orderItem);
    }

    private BigDecimal calculateTotalAmount(List<CartItem> orderItems) {
        return orderItems.stream()
                .map(item -> productService.findById(item.getProductId()).getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    protected SoftDeletesRepository<Order, Long> getRepository() {
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

package com.example.mycart.utils;

import com.example.mycart.model.*;
import com.example.mycart.payloads.inheritDTO.*;
import com.example.mycart.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper<T extends BaseEntity,D extends BaseDTO>
{
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    public D map(T entity, int pageNo)
    {
        if(entity instanceof Vendor vendor)
        {
            var dto = mapper.map(entity,VendorDTO.class);
            var products =productService.findProductByVendor(vendor.getId(),pageNo);
            var page = products.map(NamedEntity::getName);
            dto.setProducts(page);
            return (D) dto;
        }
        else if (entity instanceof Product product)
        {
            var dto = mapper.map(product, ProductDTO.class);
            var category = categoryService.findById(product.getCategoryId());
            var vendor = vendorService.findById(product.getVendorId());
            dto.setCategoryName(category.getName());
            dto.setVendorName(vendor.getName());
            return (D) dto;
        }
        else if (entity instanceof User user)
        {
            var dto = mapper.map(user, UserDTO.class);
            var orders = orderService.getOrdersByUser(user.getId())
                    .stream()
                    .map(BaseEntity::getId)
                    .toList();
            dto.setOrders(orders);
            return (D)dto;
        }
        else if(entity instanceof Cart cart)
        {
            var dto = mapper.map(cart, CartDTO.class);

            var cartItem = cartService.getCartItems(cart.getUserId());

            var cartItemDto = cartItem.stream().map(this::mapToCartItemDTO).toList();

            dto.setCartItems(cartItemDto);

            return (D)dto;

        }
        else if (entity instanceof CartItem cartItem)
        {
            return (D) mapToCartItemDTO(cartItem);
        }
        else if (entity instanceof Category category)
        {
            var dto = mapper.map(category, CategoryDTO.class);

            var products = productService.getProductByCategory(category.getId(),pageNo);

            dto.setProductsNames(products.map(NamedEntity::getName));

            var subCategories = categoryService.getSubcategories(category.getId(),pageNo);

            dto.setSubCategoriesNames(subCategories.map(NamedEntity::getName));

            return (D)dto;
        }
        else if (entity instanceof Inventory inventory)
        {
            var dto = mapper.map(inventory, InventoryDTO.class);

            dto.setProductName(productService.findByProductId(inventory.getProductId()).getName());

            return (D)dto;
        }
        else if (entity instanceof Order order)
        {
            var dto = mapper.map(order,OrderDTO.class);

            dto.setUserName(userService.findUserById(order.getUserId()).getName());

            var orderItems = orderService.findOrderItemsByOrder(order.getId(),pageNo);

            dto.setOrderItems(orderItems.map(this::mapToOrderItemDTO));

            return (D)dto;
        }
        else if (entity instanceof OrderItems orderItems)
        {
            return (D)mapToOrderItemDTO(orderItems);
        }
        return null;
    }

    private CartItemDTO mapToCartItemDTO(CartItem cartItem)
    {
        var itemDto = mapper.map(cartItem, CartItemDTO.class);

        itemDto.setProductName(productService.findByProductId(cartItem.getProductId()).getName());

        return itemDto;
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItems orderItem)
    {
        var itemDto = mapper.map(orderItem, OrderItemDTO.class);

        itemDto.setProductName(productService.findByProductId(orderItem.getProductId()).getName());

        return itemDto;
    }

}

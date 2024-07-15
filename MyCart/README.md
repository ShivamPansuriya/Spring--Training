# eCommerce Project Plan

## List of Features
1. **User Management**: Register, profile management.
2. **Product Management**: Add, update, delete, view products.
3. **Category Management**: Add, update, delete, view categories and subcategories.
4. **Order Management**: Create, update, view orders.
5. **Cart Management**: Add to cart, update cart, view cart.
6. **Review System**: Add, update, delete, view reviews.
7. **Inventory Management**: Track product stock levels.
8. **Vendor Management**: Manage vendor information and products.(create, update, delete)
9. **Report Generation**: Export data (e.g., vendor list) to Excel or CSV.
10. **Exception Handling**: Handle and log exceptions.
11. **Multi-threading**: Asynchronous tasks.
12. **Caching**: Improve performance with caching.
13. **Logging**: Use loggers for exception handling and activity tracking.

## List of Entities and Relationships
### User
- **Fields**: id, username, email, password, firstName, lastName, address
- **Relationships**: One-to-Many with Order, One-to-One with Cart, One-to-Many with Review

### Product
- **Fields**: id, name, description, price
- **Relationships**: Many-to-One with Category, Many-to-One with Vendor, One-to-One with Inventory, One-to-Many with Review, Many-to-Many with Discount

### Category
- **Fields**: id, name, description
- **Relationships**: Many-to-One with Category (self-referencing for parentCategory), One-to-Many with Category (subCategories), One-to-Many with Product

### Order
- **Fields**: id, orderDate, status, totalAmount
- **Relationships**: Many-to-One with User, One-to-Many with OrderItem, One-to-One with Payment

### OrderItem
- **Fields**: id, quantity, price
- **Relationships**: Many-to-One with Order, Many-to-One with Product

### Cart
- **Fields**: id
- **Relationships**: One-to-One with User, One-to-Many with CartItem

### CartItem
- **Fields**: id, quantity
- **Relationships**: Many-to-One with Cart, Many-to-One with Product

### Review
- **Fields**: id, rating, comment, reviewDate
- **Relationships**: Many-to-One with User, Many-to-One with Product

### Inventory
- **Fields**: id, quantity, lastUpdated
- **Relationships**: One-to-One with Product

### Vendor
- **Fields**: id, name, description, email, phone
- **Relationships**: One-to-Many with Product

## Entity Fields (Identifying Reusability and Designing Hierarchy)


### User
- **Fields**: id, username, email, password, firstName, lastName, address

### Product
- **Fields**: id, name, description, price

### Category
- **Fields**: id, name, description

### Order
- **Fields**: id, orderDate, status, totalAmount

### OrderItem
- **Fields**: id, quantity, price

### Cart
- **Fields**: id, user_id

### CartItem
- **Fields**: id, quantity

### Review
- **Fields**: id, rating, comment, reviewDate

### Inventory
- **Fields**: id, quantity, lastUpdated

### Vendor
- **Fields**: id, name, description, email, phone

## Entity Relationships (Diagram)
### User
- **One-to-Many**: Order
- **One-to-One**: Cart
- **One-to-Many**: Review

### Product
- **Many-to-One**: Category
- **Many-to-One**: Vendor
- **One-to-One**: Inventory
- **One-to-Many**: Review

### Category
- **Many-to-One**: Category (parentCategory)
- **One-to-Many**: Category (subCategories)
- **One-to-Many**: Product

### Order
- **Many-to-One**: User
- **One-to-Many**: OrderItem

### OrderItem
- **Many-to-One**: Order
- **Many-to-One**: Product

### Cart
- **One-to-One**: User
- **One-to-Many**: CartItem

### CartItem
- **Many-to-One**: Cart
- **Many-to-One**: Product

### Review
- **Many-to-One**: User
- **Many-to-One**: Product

### Inventory
- **One-to-One**: Product

### Vendor
- **One-to-Many**: Product

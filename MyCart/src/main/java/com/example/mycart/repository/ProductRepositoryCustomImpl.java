package com.example.mycart.repository;

import com.example.mycart.model.Category;
import com.example.mycart.model.OrderItems;
import com.example.mycart.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.mycart.constants.Constants.ID;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> findByCategoryOrderByPriceAsc(Long categoryId, Pageable page)
    {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var product = query.from(Product.class);
        var category = query.from(Category.class);

        query.select(product);
        query.where(
                cb.and(
                    cb.equal(product.get("categoryId"),category.get(ID)),
                        cb.equal(product.get("deleted"), false),
                cb.or(
                    cb.equal(product.get("categoryId"), categoryId),
                    cb.equal(category.get("parentCategoryId"), categoryId)
                    )
                )
        );
        query.orderBy(cb.asc(product.get("price")));

        var results = entityManager.createQuery(query)
                .setFirstResult((int) page.getOffset())
                .setMaxResults(page.getPageSize())
                .getResultList();

        return new PageImpl<>(results, page, results.size());
    }

    @Override
    public Optional<Product> findByNameAndCategoryAndVendor(String name, Long categoryId, Long vendorId) {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var root = query.from(Product.class);

        query.where(cb.and(
                cb.equal(root.get("name"), name),
                cb.equal(root.get("categoryId"), categoryId),
                cb.equal(root.get("vendorId"), vendorId),
                cb.equal(root.get("deleted"), false)
        ));

        var results = entityManager.createQuery(query)
                .setMaxResults(1)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Page<Object[]> findTopSellingProducts(int limit, Pageable page)
    {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createTupleQuery();

        var product = query.from(Product.class);

        var orderItem = query.from(OrderItems.class);

        query.multiselect(
                product.get(ID),
                product.get("name"),
                cb.sum(orderItem.get("quantity")).alias("total_quantity"),
                cb.sum(cb.prod(orderItem.get("price"), orderItem.get("quantity"))).alias("total_revenue")
                );

        query.where(
                cb.and(
                    cb.equal(product.get(ID), orderItem.get("productId")),
                        cb.equal(product.get("deleted"), false),
                        cb.equal(orderItem.get("deleted"), false)
                )
        );
        query.groupBy(product.get(ID), product.get("name"));
        query.orderBy(cb.desc(cb.sum(orderItem.get("quantity"))));

        var result = entityManager.createQuery(query)
                .setMaxResults(limit)
                .setFirstResult((int) page.getOffset())
                .getResultList()
                .stream()
                .map(tuple -> {
                    var size = tuple.getElements().size();
                    var obj = new Object[size];
                    for(int i=0; i<size; ++i)
                    {
                        obj[i] = tuple.get(i);
                    }
                    return obj;

                })
                .toList();

        return new PageImpl<>(result, page, result.size());
    }

}
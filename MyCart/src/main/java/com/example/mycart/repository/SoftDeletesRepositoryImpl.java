package com.example.mycart.repository;

import com.example.mycart.model.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SoftDeletesRepositoryImpl<T extends BaseEntity<Long>, ID> extends BaseRepositoryImpl<T, ID>
        implements SoftDeletesRepository<T, ID>
{

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    private final Class<T> domainClass;
    private static final String DELETED_FIELD = "deleted";

    public SoftDeletesRepositoryImpl(JpaEntityInformation jpaEntityInformation,EntityManager em) {
        super(jpaEntityInformation,em);
        this.entityInformation = jpaEntityInformation;
        this.domainClass = jpaEntityInformation.getJavaType();
        this.em = em;
    }

    @Override
    public Optional<T> findById(ID id){
        var entity = super.findById(id);
        if(entity.isPresent() && entity.get().isDeleted())
        {
            return Optional.ofNullable(this.em.find(this.getDomainClass(), id));
        }
        return entity;
    }

    @Override
    public List<T> findAll(){
        if (isFieldDeletedAtExists()) return super.findAll(notDeleted());
        return super.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return (pageable.isUnpaged() ? new PageImpl(this.findAll()) : this.findAll(notDeleted(), pageable));
    }

    @Override
    public List<T> findAll(Sort sort){
        if (isFieldDeletedAtExists()) return super.findAll(notDeleted(), sort);
        return super.findAll(sort);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        softDelete(entity, true);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends T> entities)
    {
        Assert.notNull(entities, "Entities must not be null");

        for (T entity : entities) {
            softDelete(entity,true);
        }

    }

    private boolean isFieldDeletedAtExists() {
        try {
            domainClass.getSuperclass().getDeclaredField(DELETED_FIELD);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (isFieldDeletedAtExists())
            return super.findOne(Specification.where(new ByIdSpecification<>(entityInformation, id)).and(notDeleted()));
        return super.findOne(Specification.where(new ByIdSpecification<>(entityInformation, id)));
    }

    private void softDelete(T entity, boolean status) {
        Assert.notNull(entity, "The entity must not be null!");

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaUpdate<T> update = cb.createCriteriaUpdate(domainClass);

        Root<T> root = update.from(domainClass);

        update.set(DELETED_FIELD, status);

        update.where(
                cb.equal(
                        root.<ID>get(Objects.requireNonNull(entityInformation.getIdAttribute()).getName()),
                        entityInformation.getId(entity)
                )
        );

        em.createQuery(update).executeUpdate();
    }

    private static final class ByIdSpecification<T, ID> implements Specification<T> {

        private final transient JpaEntityInformation<T, ?> entityInformation;
        private final transient ID id;

        ByIdSpecification(JpaEntityInformation<T, ?> entityInformation, ID id) {
            this.entityInformation = entityInformation;
            this.id = id;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.equal(root.<ID>get(Objects.requireNonNull(entityInformation.getIdAttribute()).getName()), id);
        }
    }

    private static final class DeletedIsNUll<T> implements Specification<T> {

        private static final long serialVersionUID = -940322276301888908L;

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.isNull(root.<LocalDateTime>get(DELETED_FIELD));
        }

    }

    private static <T> Specification<T> notDeleted() {
        return Specification.where(new DeletedIsNUll<>());
    }

}
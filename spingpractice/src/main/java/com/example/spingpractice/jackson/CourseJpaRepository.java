package com.example.spingpractice.jackson;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class CourseJpaRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Course> getByUserAndCourse(String name, String courseName){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);

        Root<Course> root = criteriaQuery.from(Course.class);

        Predicate byName = criteriaBuilder.equal(root.get("name"), name);

        Predicate byCourseName = criteriaBuilder.equal(root.get("courseName"), courseName);

        Predicate condition  = criteriaBuilder.and(byName,byCourseName);

        TypedQuery<Course> query = entityManager.createQuery(criteriaQuery.where(condition));
        return query.getResultList();
    }

    public List<Object> groupByName(String variable){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(Object.class);

        Root<Course> root = criteriaQuery.from(Course.class);

        criteriaQuery.multiselect(root.get("courseName"), criteriaBuilder.count(root))
                .groupBy(root.get(variable));

        Predicate ageLimit = criteriaBuilder.lessThan(root.get("age"),20);
        TypedQuery<Object> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().stream().toList();
    }

    public List<Course> moreThan10Students()
    {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(Course.class);

        var root = criteriaQuery.from(Course.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.gt(criteriaBuilder.size(root.get("students")),10));

        var query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Course> orderByName()
    {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(Course.class);

        var root = criteriaQuery.from(Course.class);

        criteriaQuery.select(root)
                .orderBy(criteriaBuilder.asc(root.get("name")), criteriaBuilder.asc(root.get("courseName")));

        var query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Object> getStudentsByCourseId()
    {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(Object.class);

        var root = criteriaQuery.from(Course.class);

        criteriaQuery.select(root.get("students"))
                .where(criteriaBuilder.equal(root.get("number"),2));

        var query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

}

package com.example.jpahibernate.courses.jpa;

import com.example.jpahibernate.courses.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CourseJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Course findById(Long id) {
        return em.find(Course.class, id);
    }


    public void insert(Course course) {
        em.merge(course);
    }

    public void delete(long id) {
        em.remove(em.find(Course.class,id));
    }
}

package com.example.hibernate.repository;

import com.example.hibernate.entity.Course;
import com.example.hibernate.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CourseRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Course getById(Long id) {
        return entityManager.find(Course.class, id);
    }

    public void deleteById(Long id) {
        entityManager.remove(getById(id));
    }

    public Course save(Course course) {
        if(course.getId() == null) {
            entityManager.persist(course);
        }
        else
        {
            entityManager.merge(course);
        }
        return course;
    }

    public void addReviewsForCourse()
    {
        var course = getById(10003L);

        var review = new Review("5", "hands on");

        course.addReview(review);
        review.setCourse(course);
        entityManager.persist(review);
    }

    public void addReviewsForCourse(Long courseId, List<Review> reviews)
    {
        var course = getById(courseId);

        for(Review review : reviews)
        {
            course.addReview(review);
            review.setCourse(course);
            entityManager.persist(review);
        }
    }
}

package com.example.hibernate.repository;

import com.example.hibernate.entity.Passport;
import com.example.hibernate.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class StudentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Student getById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public void deleteById(Long id) {
        entityManager.remove(getById(id));
    }

    public Student save(Student student) {
        if(student.getId() == null) {
            entityManager.persist(student);
        }
        else
        {
            entityManager.merge(student);
        }
        return student;
    }

    public void saveStudentWithPassport(){
        var passport = new Passport("Z123456");
        entityManager.persist(passport);

        var student = new Student("Shivam");
        student.setPassport(passport);
        entityManager.persist(student);
    }


}

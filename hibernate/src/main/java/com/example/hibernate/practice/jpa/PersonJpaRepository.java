package com.example.hibernate.practice.jpa;

import com.example.hibernate.practice.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//@Transactional
public class PersonJpaRepository {

//    @PersistenceContext
    private EntityManager em;

//    public List<Person> findAll() {
//        var nameQuery = em.createNamedQuery("find_all_person", Person.class);
//        return nameQuery.getResultList();
//    }

    public Person findById(int id) {
        return em.find(Person.class, id);
    }

    public Person insert(Person person) {
        return em.merge(person);
    }

    public Person update(Person person) {
        return em.merge(person);
    }

    public void deleteById(int id) {
        var person = em.find(Person.class, id);
        em.remove(person);
    }
}

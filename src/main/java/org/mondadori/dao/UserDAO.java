package org.mondadori.dao;

import jakarta.persistence.EntityManager;
import org.mondadori.entities.User;

public class UserDAO {
    private EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public void save(User user) {
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }
}

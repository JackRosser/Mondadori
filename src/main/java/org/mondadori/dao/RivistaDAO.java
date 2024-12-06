package org.mondadori.dao;

import jakarta.persistence.EntityManager;
import org.mondadori.entities.Riviste;

public class RivistaDAO {
    private EntityManager em;

    public RivistaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Riviste riviste) {
        try {
            em.getTransaction().begin();
            em.persist(riviste);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Riviste findById(String isbn) {
        return em.find(Riviste.class, isbn);
    }
}

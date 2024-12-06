package org.mondadori.dao;

import jakarta.persistence.EntityManager;
import org.mondadori.entities.Rivista;

public class RivistaDAO {
    private EntityManager em;

    public RivistaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Rivista rivista) {
        try {
            em.getTransaction().begin();
            em.persist(rivista);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Rivista findById(String isbn) {
        return em.find(Rivista.class, isbn);
    }
}

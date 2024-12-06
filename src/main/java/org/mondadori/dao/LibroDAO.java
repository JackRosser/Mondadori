package org.mondadori.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.mondadori.entities.Libri;


public class LibroDAO {
    EntityManager em;

    private EntityManagerFactory emf;

    public LibroDAO(EntityManager em) {
this.em = em;
    }


    public void save(Libri libri) {

        try {
            em.getTransaction().begin();
            em.persist(libri);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Libri findById(String isbn) {
     return em.find(Libri.class, isbn);

    }

}

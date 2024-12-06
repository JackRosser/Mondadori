package org.mondadori.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.mondadori.entities.Libro;


public class LibroDAO {
    EntityManager em;

    private EntityManagerFactory emf;

    public LibroDAO(EntityManager em) {
this.em = em;
    }


    public void save(Libro libro) {

        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Libro findById(String isbn) {
     return em.find(Libro.class, isbn);

    }

}

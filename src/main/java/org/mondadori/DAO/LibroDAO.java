package org.mondadori.DAO;

import org.mondadori.entities.Libro;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LibroDAO {

    private EntityManagerFactory emf;

    public LibroDAO() {
        this.emf = Persistence.createEntityManagerFactory("unit");
    }


    public void save(Libro libro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Libro findById(String isbn) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Libro.class, isbn);
        } finally {
            em.close();
        }
    }

    public void close() {
        emf.close();
    }
}

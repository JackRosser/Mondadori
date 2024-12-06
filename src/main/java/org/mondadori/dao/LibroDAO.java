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
            em.persist(libri); // Inserisce l'entità senza gestire la transazione
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il salvataggio del libro", e);
        }
    }


    public Libri findById(String isbn) {
     return em.find(Libri.class, isbn);

    }

}

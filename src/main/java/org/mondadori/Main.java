package org.mondadori;

import org.mondadori.entities.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Libro libro = new Libro();
            libro.setIsbn("978-3-16-148410-0");
            libro.setTitolo("Il Signore degli Anelli");
            libro.setAnnoPubblicazione(1954);
            libro.setNumeroPagine(1178);
            libro.setAutore("J.R.R. Tolkien");
            libro.setGenere("Fantasy");

            em.persist(libro);

            em.getTransaction().commit();

            Libro savedBook = em.find(Libro.class, "978-3-16-148410-0");
            System.out.println("Libro salvato: " + savedBook.getTitolo());

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}

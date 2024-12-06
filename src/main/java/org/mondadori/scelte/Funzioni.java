package org.mondadori.scelte;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.mondadori.dao.LibroDAO;
import org.mondadori.entities.Libri;

import java.util.List;
import java.util.function.Function;

public class Funzioni {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");

    // GESTISCO L'EM UNA VOLTA SOLA
    private static <T> T executeInTransaction(Function<EntityManager, T> action) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T result = action.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Errore durante l'operazione", e);
        } finally {
            em.close();
        }
    }

    // AGGIUNGO IL LIBRO
    public static void aggiuntaLibro(String isbn, String titolo, int annoPubblicazione, int numeroPagine, String autore, String genere) {
        executeInTransaction(em -> {
            LibroDAO libroDAO = new LibroDAO(em);
            Libri libri = new Libri();
            libri.setIsbn(isbn);
            libri.setTitolo(titolo);
            libri.setAnnoPubblicazione(annoPubblicazione);
            libri.setNumeroPagine(numeroPagine);
            libri.setAutore(autore);
            libri.setGenere(genere);
            libroDAO.save(libri);
            System.out.println("Libro aggiunto: " + titolo);
            return null;
        });
    }

    // TOLGO IL LIBRO SFRUTTANDO L'ISBN
    public static void rimuoviLibro(String isbn) {
        executeInTransaction(em -> {
            LibroDAO libroDAO = new LibroDAO(em);
            Libri libro = libroDAO.findById(isbn);
            if (libro != null) {
                em.remove(libro);
                System.out.println("Libro rimosso: " + isbn);
            } else {
                System.out.println("Libro non trovato con ISBN: " + isbn);
            }
            return null;
        });
    }

    // CERCO IL LIBRO PER ISBN
    public static Libri trovaLibroPerIsbn(String isbn) {
        return executeInTransaction(em -> {
            LibroDAO libroDAO = new LibroDAO(em);
            return libroDAO.findById(isbn);
        });
    }

    // CERCO IL LIBRO PER ANNO
    public static List<Libri> trovaLibriPerAnno(int annoPubblicazione) {
        return executeInTransaction(em -> {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.annoPubblicazione = :anno", Libri.class);
            query.setParameter("anno", annoPubblicazione);
            return query.getResultList();
        });
    }

    // CERCO IL LIBRO PER AUTORE
    public static List<Libri> trovaLibriPerAutore(String autore) {
        return executeInTransaction(em -> {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.autore = :autore", Libri.class);
            query.setParameter("autore", autore);
            return query.getResultList();
        });
    }

    // CERCO IL LIBRO PER TITOLO
    public static List<Libri> trovaLibriPerTitolo(String titolo) {
        return executeInTransaction(em -> {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.titolo LIKE :titolo", Libri.class);
            query.setParameter("titolo", "%" + titolo + "%");
            return query.getResultList();
        });
    }
}

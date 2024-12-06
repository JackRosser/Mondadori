package org.mondadori.scelte;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.mondadori.dao.LibroDAO;
import org.mondadori.entities.Libri;
import org.mondadori.entities.Prestiti;

import java.time.LocalDate;
import java.util.List;

public class Funzioni {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");

    // Aggiunge un libro al catalogo
    public static void aggiuntaLibro(String isbn, String titolo, int annoPubblicazione, int numeroPagine, String autore, String genere) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            LibroDAO libroDAO = new LibroDAO(em);
            Libri libro = new Libri();
            libro.setIsbn(isbn);
            libro.setTitolo(titolo);
            libro.setAnnoPubblicazione(annoPubblicazione);
            libro.setNumeroPagine(numeroPagine);
            libro.setAutore(autore);
            libro.setGenere(genere);
            libroDAO.save(libro);
            em.getTransaction().commit();
            System.out.println("Libro aggiunto con successo: " + titolo);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Errore durante l'aggiunta del libro", e);
        } finally {
            em.close();
        }
    }

    // Rimuove un libro dal catalogo in base all'ISBN
    public static void rimuoviLibro(String isbn) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            LibroDAO libroDAO = new LibroDAO(em);
            Libri libro = libroDAO.findById(isbn);
            if (libro != null) {
                em.remove(libro);
                em.getTransaction().commit();
                System.out.println("Libro rimosso con successo: " + isbn);
            } else {
                em.getTransaction().rollback();
                System.out.println("Libro non trovato con ISBN: " + isbn);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Errore durante la rimozione del libro", e);
        } finally {
            em.close();
        }
    }

    // Cerca un libro per ISBN
    public static Libri trovaLibroPerIsbn(String isbn) {
        EntityManager em = emf.createEntityManager();
        try {
            LibroDAO libroDAO = new LibroDAO(em);
            Libri libro = libroDAO.findById(isbn);
            if (libro == null) {
                System.out.println("Libro non trovato con ISBN: " + isbn);
            }
            return libro;
        } finally {
            em.close();
        }
    }

    // Cerca libri per anno di pubblicazione
    public static List<Libri> trovaLibriPerAnno(int annoPubblicazione) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.annoPubblicazione = :anno", Libri.class);
            query.setParameter("anno", annoPubblicazione);
            List<Libri> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun libro trovato per l'anno: " + annoPubblicazione);
            }
            return risultati;
        } finally {
            em.close();
        }
    }

    // Cerca libri per autore
    public static List<Libri> trovaLibriPerAutore(String autore) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.autore = :autore", Libri.class);
            query.setParameter("autore", autore);
            List<Libri> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun libro trovato per l'autore: " + autore);
            }
            return risultati;
        } finally {
            em.close();
        }
    }

    // Cerca libri per titolo o parte di esso
    public static List<Libri> trovaLibriPerTitolo(String titolo) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.titolo LIKE :titolo", Libri.class);
            query.setParameter("titolo", "%" + titolo + "%");
            List<Libri> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun libro trovato per il titolo: " + titolo);
            }
            return risultati;
        } finally {
            em.close();
        }
    }

    // Cerca tutti gli elementi in prestito attualmente non restituiti
    public static List<Prestiti> trovaElementiInPrestito() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Prestiti> query = em.createQuery(
                    "SELECT p FROM Prestiti p WHERE p.dataRestituzioneEffettiva IS NULL", Prestiti.class
            );
            List<Prestiti> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun elemento attualmente in prestito.");
            }
            return risultati;
        } finally {
            em.close();
        }
    }

    // Cerca i prestiti scaduti e non restituiti
    public static List<Prestiti> trovaPrestitiScaduti() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Prestiti> query = em.createQuery(
                    "SELECT p FROM Prestiti p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < :oggi",
                    Prestiti.class
            );
            query.setParameter("oggi", LocalDate.now());
            List<Prestiti> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun prestito scaduto e non restituito.");
            }
            return risultati;
        } finally {
            em.close();
        }
    }
}

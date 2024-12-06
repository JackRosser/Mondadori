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
import java.util.function.Function;

public class Funzioni {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");

    // Metodo per gestire le transazioni
    private static <T> T executeInTransaction(Function<EntityManager, T> action) {
        EntityManager em = emf.createEntityManager();
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin(); // Inizia la transazione
            }
            T result = action.apply(em);
            em.getTransaction().commit(); // Conferma la transazione
            return result;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback in caso di errore
            }
            throw new RuntimeException("Errore durante l'operazione", e);
        } finally {
            em.close(); // Chiude l'EntityManager
        }
    }

    // Aggiunge un libro al catalogo
    public static void aggiuntaLibro(String isbn, String titolo, int annoPubblicazione, int numeroPagine, String autore, String genere) {
        executeInTransaction(em -> {
            LibroDAO libroDAO = new LibroDAO(em);
            Libri libro = new Libri();
            libro.setIsbn(isbn);
            libro.setTitolo(titolo);
            libro.setAnnoPubblicazione(annoPubblicazione);
            libro.setNumeroPagine(numeroPagine);
            libro.setAutore(autore);
            libro.setGenere(genere);
            libroDAO.save(libro);
            System.out.println("Libro aggiunto con successo: " + titolo);
            return null;
        });
    }

    // Rimuove un libro dal catalogo in base all'ISBN
    public static void rimuoviLibro(String isbn) {
        executeInTransaction(em -> {
            LibroDAO libroDAO = new LibroDAO(em);
            Libri libro = libroDAO.findById(isbn);
            if (libro != null) {
                em.remove(libro);
                System.out.println("Libro rimosso con successo: " + isbn);
            } else {
                System.out.println("Libro non trovato con ISBN: " + isbn);
            }
            return null;
        });
    }

    // Cerca un libro per ISBN
    public static Libri trovaLibroPerIsbn(String isbn) {
        return executeInTransaction(em -> {
            LibroDAO libroDAO = new LibroDAO(em);
            Libri libro = libroDAO.findById(isbn);
            if (libro == null) {
                System.out.println("Libro non trovato con ISBN: " + isbn);
            }
            return libro;
        });
    }

    // Cerca libri per anno di pubblicazione
    public static List<Libri> trovaLibriPerAnno(int annoPubblicazione) {
        return executeInTransaction(em -> {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.annoPubblicazione = :anno", Libri.class);
            query.setParameter("anno", annoPubblicazione);
            List<Libri> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun libro trovato per l'anno: " + annoPubblicazione);
            }
            return risultati;
        });
    }

    // Cerca libri per autore
    public static List<Libri> trovaLibriPerAutore(String autore) {
        return executeInTransaction(em -> {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.autore = :autore", Libri.class);
            query.setParameter("autore", autore);
            List<Libri> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun libro trovato per l'autore: " + autore);
            }
            return risultati;
        });
    }

    // Cerca libri per titolo o parte di esso
    public static List<Libri> trovaLibriPerTitolo(String titolo) {
        return executeInTransaction(em -> {
            TypedQuery<Libri> query = em.createQuery("SELECT l FROM Libri l WHERE l.titolo LIKE :titolo", Libri.class);
            query.setParameter("titolo", "%" + titolo + "%");
            List<Libri> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun libro trovato per il titolo: " + titolo);
            }
            return risultati;
        });
    }

    // Cerca tutti gli elementi in prestito attualmente non restituiti
    public static List<Prestiti> trovaElementiInPrestito() {
        return executeInTransaction(em -> {
            TypedQuery<Prestiti> query = em.createQuery(
                    "SELECT p FROM Prestiti p WHERE p.dataRestituzioneEffettiva IS NULL", Prestiti.class
            );
            List<Prestiti> risultati = query.getResultList();
            if (risultati.isEmpty()) {
                System.out.println("Nessun elemento attualmente in prestito.");
            }
            return risultati;
        });
    }

    // Cerca i prestiti scaduti e non restituiti
    public static List<Prestiti> trovaPrestitiScaduti() {
        return executeInTransaction(em -> {
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
        });
    }
}

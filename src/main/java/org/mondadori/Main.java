package org.mondadori;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.mondadori.dao.LibroDAO;
import org.mondadori.dao.RivistaDAO;
import org.mondadori.entities.Libro;
import org.mondadori.entities.Rivista;


public class Main {
   public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");
        EntityManager em = emf.createEntityManager();

        // GESTIONE LIBRO

        // LibroDAO libroDAO = new LibroDAO(em);
        // Libro libro = new Libro();
        // libro.setIsbn("978-3-16-148410-0");
        // libro.setTitolo("Il Signore degli Anelli");
        // libro.setAnnoPubblicazione(1954);
        // libro.setNumeroPagine(1178);
        // libro.setAutore("J.R.R. Tolkien");
        // libro.setGenere("Fantasy");
        // libroDAO.save(libro);

        // Libro savedBook = libroDAO.findById("978-3-16-148410-0");
        // if (savedBook != null) {
            // System.out.println("Libro salvato: " + savedBook.getTitolo());
        // } else {
            // System.out.println("Libro non trovato.");
        // }
        // em.close();

       // GESTIONE RIVISTE

       // RivistaDAO rivistaDAO = new RivistaDAO(em);
       // Rivista rivista = new Rivista();
       // rivista.setIsbn("67895");
       // rivista.setTitolo("Rivista di Moda");
       // rivista.setAnnoPubblicazione(2024);
       // rivista.setNumeroPagine(100);
       // rivista.setPeriodicita(Rivista.Periodicita.MENSILE);

       // rivistaDAO.save(rivista);

       // Ricerca della Rivista per ISBN
       // Rivista foundRivista = rivistaDAO.findById("12345");
       // if (foundRivista != null) {
           // System.out.println("Rivista trovata: " + foundRivista.getTitolo());
       // } else {
           // System.out.println("Rivista non trovata.");
       // }

       // em.close();
       // emf.close();

    }
}
package org.mondadori;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.mondadori.dao.LibroDAO;
import org.mondadori.dao.PrestitoDAO;
import org.mondadori.dao.RivistaDAO;
import org.mondadori.dao.UserDAO;
import org.mondadori.entities.Libri;
import org.mondadori.entities.Prestiti;
import org.mondadori.entities.Riviste;
import org.mondadori.entities.User;

import java.time.LocalDate;

public class TestNonUsare {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");
        EntityManager em = emf.createEntityManager();

        // Test UserDAO
        UserDAO userDAO = new UserDAO(em);
        User user = new User();
        user.setNome("Gino");
        user.setCognome("Paoli");
        user.setDataDiNascita(LocalDate.of(1999, 6, 20));
        user.setNumeroTessera("U12345");
        userDAO.save(user);

        User foundUser = userDAO.findById(user.getId());
        System.out.println("User found: " + (foundUser != null ? foundUser.getNome() : "Not found"));

        // Test LibroDAO
        LibroDAO libroDAO = new LibroDAO(em);
        Libri libri = new Libri();
        libri.setIsbn("978-3-16-148410-0");
        libri.setTitolo("Il Signore degli Anelli");
        libri.setAnnoPubblicazione(1954);
        libri.setNumeroPagine(1178);
        libri.setAutore("J.R.R. Tolkien");
        libri.setGenere("Fantasy");
        libroDAO.save(libri);

        Libri foundLibri = libroDAO.findById(libri.getIsbn());
        System.out.println("Libro found: " + (foundLibri != null ? foundLibri.getTitolo() : "Not found"));

        // Test RivistaDAO
        RivistaDAO rivistaDAO = new RivistaDAO(em);
        Riviste riviste = new Riviste();
        riviste.setIsbn("12345");
        riviste.setTitolo("Rivista di Tecnologia");
        riviste.setAnnoPubblicazione(2024);
        riviste.setNumeroPagine(50);
        riviste.setPeriodicita(Riviste.Periodicita.MENSILE);
        rivistaDAO.save(riviste);

        Riviste foundRiviste = rivistaDAO.findById(riviste.getIsbn());
        System.out.println("Rivista found: " + (foundRiviste != null ? foundRiviste.getTitolo() : "Not found"));

        // Test PrestitoDAO
        // PrestitoDAO prestitoDAO = new PrestitoDAO(em);
       //  Prestiti prestiti = new Prestiti();
        // prestiti.setUser(user);
        // prestiti.setElementoPrestato(libri);
        // prestiti.setDataInizioPrestito(LocalDate.now());
        // prestiti.setDataRestituzionePrevista(LocalDate.now().plusDays(30));
        // prestitoDAO.save(prestiti);

        // Prestiti foundPrestiti = prestitoDAO.findById(prestiti.getId());
        // System.out.println("Prestito found: " + (foundPrestiti != null ? "Prestito esistente" : "Not found"));

        // Close EntityManager and EntityManagerFactory
        em.close();
        emf.close();
    }
}

package org.mondadori;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.mondadori.dao.LibroDAO;
import org.mondadori.dao.PrestitoDAO;
import org.mondadori.dao.RivistaDAO;
import org.mondadori.dao.UserDAO;
import org.mondadori.entities.Libro;
import org.mondadori.entities.Prestito;
import org.mondadori.entities.Rivista;
import org.mondadori.entities.User;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");
        EntityManager em = emf.createEntityManager();

        // Test UserDAO
        UserDAO userDAO = new UserDAO(em);
        User user = new User();
        user.setNome("Mario");
        user.setCognome("Rossi");
        user.setDataDiNascita(LocalDate.of(1980, 5, 15));
        user.setNumeroTessera("U12345");
        userDAO.save(user);

        User foundUser = userDAO.findById(user.getId());
        System.out.println("User found: " + (foundUser != null ? foundUser.getNome() : "Not found"));

        // Test LibroDAO
        LibroDAO libroDAO = new LibroDAO(em);
        Libro libro = new Libro();
        libro.setIsbn("978-3-16-148410-0");
        libro.setTitolo("Il Signore degli Anelli");
        libro.setAnnoPubblicazione(1954);
        libro.setNumeroPagine(1178);
        libro.setAutore("J.R.R. Tolkien");
        libro.setGenere("Fantasy");
        libroDAO.save(libro);

        Libro foundLibro = libroDAO.findById(libro.getIsbn());
        System.out.println("Libro found: " + (foundLibro != null ? foundLibro.getTitolo() : "Not found"));

        // Test RivistaDAO
        RivistaDAO rivistaDAO = new RivistaDAO(em);
        Rivista rivista = new Rivista();
        rivista.setIsbn("12345");
        rivista.setTitolo("Rivista di Tecnologia");
        rivista.setAnnoPubblicazione(2024);
        rivista.setNumeroPagine(50);
        rivista.setPeriodicita(Rivista.Periodicita.MENSILE);
        rivistaDAO.save(rivista);

        Rivista foundRivista = rivistaDAO.findById(rivista.getIsbn());
        System.out.println("Rivista found: " + (foundRivista != null ? foundRivista.getTitolo() : "Not found"));

        // Test PrestitoDAO
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);
        Prestito prestito = new Prestito();
        prestito.setUser(user);
        prestito.setElementoPrestato(libro);
        prestito.setDataInizioPrestito(LocalDate.now());
        prestito.setDataRestituzionePrevista(LocalDate.now().plusDays(30));
        prestitoDAO.save(prestito);

        Prestito foundPrestito = prestitoDAO.findById(prestito.getId());
        System.out.println("Prestito found: " + (foundPrestito != null ? "Prestito esistente" : "Not found"));

        // Close EntityManager and EntityManagerFactory
        em.close();
        emf.close();
    }
}

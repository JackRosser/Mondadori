package org.mondadori;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.mondadori.dao.PrestitoDAO;

import java.time.LocalDate;

public class MainPrestiti {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");
        EntityManager em = emf.createEntityManager();

        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        // Associare il libro all'utente
        Long userId = 1L;
        String isbn = "978-3-16-148410-0";
        LocalDate dataInizio = LocalDate.now();
        LocalDate dataRestituzionePrevista = dataInizio.plusWeeks(2);

        prestitoDAO.associaLibroAUtente(userId, isbn, dataInizio, dataRestituzionePrevista);
    }
}

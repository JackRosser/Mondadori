package org.mondadori;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.mondadori.dao.UserDAO;
import org.mondadori.entities.User;

import java.time.LocalDate;

public class MainUser {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");
        EntityManager em = emf.createEntityManager();

        try {
            // Creazione dell'oggetto UserDAO
            UserDAO userDao = new UserDAO(em);

            // Creazione di un nuovo utente
            User nuovoUtente = new User();
            nuovoUtente.setNome("Ciccio");
            nuovoUtente.setCognome("Franco");
            nuovoUtente.setDataDiNascita(LocalDate.of(2002, 5, 15));
            nuovoUtente.setNumeroTessera("U987666");

            // Salvataggio nel database
            userDao.save(nuovoUtente);

            System.out.println("Utente creato con successo: " + nuovoUtente.getNome() + " " + nuovoUtente.getCognome());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}

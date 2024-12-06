package org.mondadori.dao;

import jakarta.persistence.EntityManager;
import org.mondadori.entities.Prestiti;
import org.mondadori.entities.User;
import org.mondadori.entities.Product;

import java.time.LocalDate;

public class PrestitoDAO {
    private EntityManager em; // Gestore delle operazioni di persistenza

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Prestiti prestiti) {
        try {
            em.getTransaction().begin();
            em.persist(prestiti);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Prestiti findById(Long id) {
        return em.find(Prestiti.class, id);
    }

    public void associaLibroAUtente(Long userId, String isbn, LocalDate dataInizio, LocalDate dataRestituzionePrevista) {
        try {
            em.getTransaction().begin();

            // Carica l'utente esistente
            User user = em.find(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User con ID " + userId + " non trovato.");
            }

            // Carica il libro esistente
            Product product = em.find(Product.class, isbn);
            if (product == null) {
                throw new IllegalArgumentException("Libro con ISBN " + isbn + " non trovato.");
            }

            // Crea il nuovo prestito
            Prestiti prestito = new Prestiti();
            prestito.setUser(user);
            prestito.setElementoPrestato(product);
            prestito.setDataInizioPrestito(dataInizio);
            prestito.setDataRestituzionePrevista(dataRestituzionePrevista);

            // Salva il prestito
            em.persist(prestito);
            em.getTransaction().commit();
            System.out.println("Libro associato all'utente con successo.");
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
}

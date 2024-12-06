package org.mondadori.dao;

import jakarta.persistence.EntityManager;
import org.mondadori.entities.Prestiti;

public class PrestitoDAO {
    private EntityManager em; // Gestore delle operazioni di persistenza

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Prestiti prestiti) {
        try {
            em.getTransaction().begin(); // Inizio della transazione
            em.persist(prestiti);        // Salvataggio dell'entità
            em.getTransaction().commit(); // Completamento della transazione
        } catch (Exception e) {
            e.printStackTrace();         // Log dell'errore
            em.getTransaction().rollback(); // Annullamento della transazione
        }
    }

    public Prestiti findById(Long id) {
        return em.find(Prestiti.class, id); // Ricerca tramite la classe e l'ID
    }
}

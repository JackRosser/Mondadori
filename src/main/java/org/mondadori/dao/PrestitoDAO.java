package org.mondadori.dao;

import jakarta.persistence.EntityManager;
import org.mondadori.entities.Prestito;

public class PrestitoDAO {
    private EntityManager em; // Gestore delle operazioni di persistenza

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Prestito prestito) {
        try {
            em.getTransaction().begin(); // Inizio della transazione
            em.persist(prestito);        // Salvataggio dell'entità
            em.getTransaction().commit(); // Completamento della transazione
        } catch (Exception e) {
            e.printStackTrace();         // Log dell'errore
            em.getTransaction().rollback(); // Annullamento della transazione
        }
    }

    public Prestito findById(Long id) {
        return em.find(Prestito.class, id); // Ricerca tramite la classe e l'ID
    }
}

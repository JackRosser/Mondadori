package org.mondadori.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Riviste extends Product {
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;


    public enum Periodicita {
        SETTIMANALE, MENSILE, SEMESTRALE
    }


    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }
}

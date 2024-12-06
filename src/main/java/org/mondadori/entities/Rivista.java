package org.mondadori.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
public class Rivista extends Product {
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

package org.mondadori;

import org.mondadori.DAO.LibroDAO;
import org.mondadori.entities.Libro;

public class Main {
    public static void main(String[] args) {
        LibroDAO libroDAO = new LibroDAO();

        Libro libro = new Libro();
        libro.setIsbn("978-3-16-148410-0");
        libro.setTitolo("Il Signore degli Anelli");
        libro.setAnnoPubblicazione(1954);
        libro.setNumeroPagine(1178);
        libro.setAutore("J.R.R. Tolkien");
        libro.setGenere("Fantasy");

        libroDAO.save(libro);

        Libro savedBook = libroDAO.findById("978-3-16-148410-0");
        if (savedBook != null) {
            System.out.println("Libro salvato: " + savedBook.getTitolo());
        } else {
            System.out.println("Libro non trovato.");
        }

        libroDAO.close();
    }
}

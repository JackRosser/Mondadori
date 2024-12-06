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
import org.mondadori.scelte.Funzioni;

import java.time.LocalDate;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");
        EntityManager em = emf.createEntityManager();

        UserDAO userDAO = new UserDAO(em);
        LibroDAO libroDAO = new LibroDAO(em);
        RivistaDAO rivistaDAO = new RivistaDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("--- Mondadori 2.0 di Rossettini Giacomo ---");
            System.out.println("--- Al momento è una BETA e quindi le funzionalità sono presenti solo per i LIBRI ---");
            System.out.println("\n1. Aggiunta di un libro al catalogo");
            System.out.println("2. Rimozione di un libro dal catalogo dato un codice ISBN");
            System.out.println("3. Ricerca per ISBN");
            System.out.println("4. Ricerca per anno di pubblicazione");
            System.out.println("5. Ricerca per autore");
            System.out.println("6. Ricerca per titolo o parte di esso");
            System.out.println("7. Ricerca elementi attualmente in prestito dato un numero di tessera utente");
            System.out.println("8. Ricerca prestiti scaduti e non restituiti");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Aggiunta di un elemento del catalogo...");
                    System.out.println("\nSpecifica titolo");
                    String titolo = scanner.nextLine();
                    System.out.println("Specifica ISBN");
                    String isbn = scanner.nextLine();
                    System.out.println("Specifica anno di pubblicazione");
                    int annoPubb = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Specifica numero di pagine");
                    int pages = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Specifica autore");
                    String autore = scanner.nextLine();
                    System.out.println("Specifica genere e non ho tempo di fare gli enum quindi sarà una bolgia, mi dispiace");
                    String genre = scanner.nextLine();
                    Funzioni.aggiuntaLibro(isbn, titolo, annoPubb, pages, autore, genre);
                    break;

                case 2:
                    System.out.println("Rimozione di un elemento del catalogo");
                    System.out.print("Inserisci il codice ISBN: ");
                    String isbnToRemove = scanner.nextLine();
                    Funzioni.rimuoviLibro(isbnToRemove);
                    break;

                case 3:
                    System.out.println("Ricerca per ISBN");
                    System.out.print("Inserisci il codice ISBN: ");
                    String isbnToFind = scanner.nextLine();
                    Funzioni.trovaLibroPerIsbn(isbnToFind);
                    break;

                case 4:
                    System.out.println("Ricerca per anno di pubblicazione");
                    System.out.print("Inserisci l'anno: ");
                    int yearToFind = scanner.nextInt();
                    scanner.nextLine();
                    Funzioni.trovaLibriPerAnno(yearToFind);
                    break;

                case 5:
                    System.out.println("Ricerca per autore");
                    System.out.print("Inserisci il nome dell'autore: ");
                    String authorToFind = scanner.nextLine();
                    Funzioni.trovaLibriPerAutore(authorToFind);
                    break;

                case 6:
                    System.out.println("Ricerca per titolo o parte di esso");
                    System.out.print("Inserisci il titolo o una parte di esso: ");
                    String titleToFind = scanner.nextLine();
                    Funzioni.trovaLibriPerTitolo(titleToFind);
                    break;

                case 7:
                    System.out.println("Ricerca degli elementi in prestito");
                    System.out.print("Inserisci il numero di tessera utente: ");
                    String cardNumber = scanner.nextLine();
                    // Aggiungi logica per cercare elementi in prestito
                    break;

                case 8:
                    System.out.println("Ricerca prestiti scaduti e non restituiti");
                    // Aggiungi logica per cercare prestiti scaduti
                    break;

                case 0:
                    System.out.println("Ciao e grazie");
                    break;

                default:
                    System.out.println("Scelta non valida! Ritenta e sarai più fortunato");
            }
        } while (choice != 0);

        scanner.close();
        em.close();
        emf.close();
    }
}


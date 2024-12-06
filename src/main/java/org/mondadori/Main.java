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

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");

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
                    loopOperation(scanner, () -> {
                        System.out.println("Rimozione di un elemento del catalogo");
                        System.out.print("Inserisci il codice ISBN (0 per tornare indietro): ");
                        String isbnToRemove = scanner.nextLine();
                        if (!isbnToRemove.equals("0")) {
                            Funzioni.rimuoviLibro(isbnToRemove);
                        }
                    });
                    break;

                case 3:
                    loopOperation(scanner, () -> {
                        System.out.println("Ricerca per ISBN");
                        System.out.print("Inserisci il codice ISBN (0 per tornare indietro): ");
                        String isbnToFind = scanner.nextLine();
                        if (!isbnToFind.equals("0")) {
                            Libri libro = Funzioni.trovaLibroPerIsbn(isbnToFind);
                            System.out.println(libro != null ? libro.getTitolo() : "Libro non trovato");
                        }
                    });
                    break;

                case 4:
                    loopOperation(scanner, () -> {
                        System.out.println("Ricerca per anno di pubblicazione");
                        System.out.print("Inserisci l'anno (0 per tornare indietro): ");
                        String yearInput = scanner.nextLine();
                        if (!yearInput.equals("0")) {
                            try {
                                int yearToFind = Integer.parseInt(yearInput);
                                List<Libri> libriPerAnno = Funzioni.trovaLibriPerAnno(yearToFind);
                                libriPerAnno.forEach(libroYear -> System.out.println(libroYear.getTitolo()));
                            } catch (NumberFormatException e) {
                                System.out.println("Anno non valido. Inserisci un numero.");
                            }
                        }
                    });
                    break;

                case 5:
                    loopOperation(scanner, () -> {
                        System.out.println("Ricerca per autore");
                        System.out.print("Inserisci il nome dell'autore (0 per tornare indietro): ");
                        String authorToFind = scanner.nextLine();
                        if (!authorToFind.equals("0")) {
                            List<Libri> libriPerAutore = Funzioni.trovaLibriPerAutore(authorToFind);
                            libriPerAutore.forEach(libroAuthor -> System.out.println(libroAuthor.getTitolo()));
                        }
                    });
                    break;

                case 6:
                    loopOperation(scanner, () -> {
                        System.out.println("Ricerca per titolo o parte di esso");
                        System.out.print("Inserisci il titolo o una parte di esso (0 per tornare indietro): ");
                        String titleToFind = scanner.nextLine();
                        if (!titleToFind.equals("0")) {
                            List<Libri> libriPerTitolo = Funzioni.trovaLibriPerTitolo(titleToFind);
                            libriPerTitolo.forEach(libroTitle -> System.out.println(libroTitle.getTitolo()));
                        }
                    });
                    break;

                case 7:
                    System.out.println("Ricerca degli elementi in prestito");
                    System.out.print("Inserisci il numero di tessera utente: ");
                    String cardNumber = scanner.nextLine();
                    List<Prestiti> prestiti = Funzioni.prestitoTessera(cardNumber);
                    if (prestiti.isEmpty()) {
                        System.out.println("Nessun prestito attivo trovato per il numero di tessera: " + cardNumber);
                    } else {
                        System.out.println("Prestiti attivi per il numero di tessera " + cardNumber + ":");
                        for (Prestiti prestito : prestiti) {
                            System.out.println("ID Prestito: " + prestito.getId() +
                                    ", Titolo: " + prestito.getElementoPrestato().getTitolo() +
                                    ", Data Inizio Prestito: " + prestito.getDataInizioPrestito() +
                                    ", Data Restituzione Prevista: " + prestito.getDataRestituzionePrevista());
                        }
                    }
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
        emf.close();
    }

    private static void loopOperation(Scanner scanner, Runnable operation) {
        String input;
        do {
            operation.run();
            System.out.println("Premi 0 per tornare indietro o Enter per continuare.");
            input = scanner.nextLine();
        } while (!input.equals("0"));
    }
}



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.mondadori.dao.LibroDAO;
import org.mondadori.entities.Libri;

public class TestGiacomo {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit");
        EntityManager em = emf.createEntityManager();

        LibroDAO libroDAO = new LibroDAO(em);
        Libri libri = new Libri();
        libri.setIsbn("8888");
        libri.setTitolo("Prova");
        libri.setAnnoPubblicazione(2024);
        libri.setNumeroPagine(1178);
        libri.setAutore("Provolo");
        libri.setGenere("Commedia");
        libroDAO.save(libri);
    }
}

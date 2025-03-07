package ca.cal.tp2.DAO;

import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Livre;
import ca.cal.tp2.modele.CD;
import ca.cal.tp2.modele.DVD;

import static ca.cal.tp2.DAO.EmParentDAO.executeInTransaction;
import java.util.List;

public class DocumentDAOImpl implements DocumentDAO {

    @Override
    public List<Document> getDocuments() {
        return executeInTransaction(em ->
                em.createQuery("SELECT d FROM Document d", Document.class)
                        .getResultList()
        );
    }

    @Override
    public Livre ajouterLivre(Livre livre) {
        return executeInTransaction(em -> {
            em.persist(livre);
            return livre;
        });
    }

    @Override
    public CD ajouterCD(CD cd) {
        return executeInTransaction(em -> {
            em.persist(cd);
            return cd;
        });
    }

    @Override
    public DVD ajouterDVD(DVD dvd) {
        return executeInTransaction(em -> {
            em.persist(dvd);
            return dvd;
        });
    }

    @Override
    public List<Document> rechercherParTitre(String titre) {
        return executeInTransaction(em -> {
            String query = "SELECT d FROM Document d WHERE LOWER(d.titre) LIKE :titre";
            return em.createQuery(query, Document.class)
                    .setParameter("titre", "%" + titre.toLowerCase() + "%")
                    .getResultList();
        });
    }

    @Override
    public List<Livre> rechercherLivresParAuteur(String auteur) {
        return executeInTransaction(em -> {
            String query = "SELECT l FROM Livre l WHERE LOWER(l.auteur) LIKE :auteur";
            return em.createQuery(query, Livre.class)
                    .setParameter("auteur", "%" + auteur.toLowerCase() + "%")
                    .getResultList();
        });
    }

    @Override
    public List<CD> rechercherCDsParArtiste(String artiste) {
        return executeInTransaction(em -> {
            String query = "SELECT c FROM CD c WHERE LOWER(c.artiste) LIKE :artiste";
            return em.createQuery(query, CD.class)
                    .setParameter("artiste", "%" + artiste.toLowerCase() + "%")
                    .getResultList();
        });
    }

    @Override
    public List<DVD> rechercherDVDsParDirecteur(String directeur) {
        return executeInTransaction(em -> {
            String query = "SELECT d FROM DVD d WHERE LOWER(d.directeur) LIKE :directeur";
            return em.createQuery(query, DVD.class)
                    .setParameter("directeur", "%" + directeur.toLowerCase() + "%")
                    .getResultList();
        });
    }

    @Override
    public Document getDocumentById(long documentId) {
        return executeInTransaction(em ->
                em.find(Document.class, documentId)
        );
    }

    @Override
    public boolean updateNombreExemplaires(long documentId, int nouveauNombreExemplaires) {
        return executeInTransaction(em -> {
            Document document = em.find(Document.class, documentId);
            if (document == null) {
                return false;
            }
            document.setNombreExemplaires(nouveauNombreExemplaires);
            return true;
        });
    }
}
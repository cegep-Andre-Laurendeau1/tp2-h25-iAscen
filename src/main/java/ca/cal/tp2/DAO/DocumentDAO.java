package ca.cal.tp2.DAO;

import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Livre;
import ca.cal.tp2.modele.CD;
import ca.cal.tp2.modele.DVD;

import java.util.List;

public interface DocumentDAO {
    List<Document> getDocuments();
    Livre ajouterLivre(Livre livre);
    CD ajouterCD(CD cd);
    DVD ajouterDVD(DVD dvd);
    List<Document> rechercherParTitre(String titre);
    List<Livre> rechercherLivresParAuteur(String auteur);
    List<CD> rechercherCDsParArtiste(String artiste);
    List<DVD> rechercherDVDsParDirecteur(String directeur);
    Document getDocumentById(long documentId);
    boolean updateNombreExemplaires(long documentId, int nouveauNombreExemplaires);
}
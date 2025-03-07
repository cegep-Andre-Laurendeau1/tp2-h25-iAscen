package ca.cal.tp2.DAO;

import ca.cal.tp2.modele.Emprunt;
import ca.cal.tp2.modele.EmpruntDetail;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Emprunteur;

import static ca.cal.tp2.DAO.EmParentDAO.executeInTransaction;
import java.util.Date;
import java.util.List;

public class EmpruntDAOImpl implements EmpruntDAO {

    @Override
    public List<Emprunt> getEmprunts() {
        return executeInTransaction(em ->
                em.createQuery("SELECT e FROM Emprunt e", Emprunt.class)
                        .getResultList()
        );
    }

    @Override
    public Emprunt creerEmprunt(Emprunteur emprunteur, Date dateEmprunt) {
        return executeInTransaction(em -> {
            Emprunteur emprunteurManaged = em.find(Emprunteur.class, emprunteur.getUserID());
            if (emprunteurManaged == null) {
                throw new IllegalArgumentException("Emprunteur non trouvé avec ID: " + emprunteur.getUserID());
            }

            Emprunt emprunt = new Emprunt();
            emprunt.setEmprunteur(emprunteurManaged);
            emprunt.setDateEmprunt(dateEmprunt);
            emprunt.setStatus("ACTIF");

            em.persist(emprunt);
            return emprunt;
        });
    }

    @Override
    public EmpruntDetail ajouterDocumentAEmprunt(Emprunt emprunt, Document document, Date dateRetourPrevue) {
        return executeInTransaction(em -> {
            Emprunt empruntManaged = em.find(Emprunt.class, emprunt.getId());
            if (empruntManaged == null) {
                throw new IllegalArgumentException("Emprunt non trouvé avec ID: " + emprunt.getId());
            }

            Document documentManaged = em.find(Document.class, document.getDocumentID());
            if (documentManaged == null) {
                throw new IllegalArgumentException("Document non trouvé avec ID: " + document.getDocumentID());
            }

            if (!documentManaged.verifieDisponibilite()) {
                throw new IllegalStateException("Document non disponible: " + documentManaged.getTitre());
            }

            documentManaged.setNombreExemplaires(documentManaged.getNombreExemplaires() - 1);

            EmpruntDetail detail = new EmpruntDetail();
            detail.setEmprunt(empruntManaged);
            detail.setDocument(documentManaged);
            detail.setDateRetourPrevue(dateRetourPrevue);
            detail.setStatus("EMPRUNTE");

            em.persist(detail);
            return detail;
        });
    }

    @Override
    public List<Emprunt> getEmpruntsParEmprunteur(long emprunteurId) {
        return executeInTransaction(em -> {
            String query = "SELECT e FROM Emprunt e WHERE e.emprunteur.userID = :emprunteurId";
            return em.createQuery(query, Emprunt.class)
                    .setParameter("emprunteurId", emprunteurId)
                    .getResultList();
        });
    }

    @Override
    public Emprunt getEmpruntById(long empruntId) {
        return executeInTransaction(em ->
                em.find(Emprunt.class, empruntId)
        );
    }

    @Override
    public List<EmpruntDetail> getDetailsEmprunt(long empruntId) {
        return executeInTransaction(em -> {
            String query = "SELECT ed FROM EmpruntDetail ed WHERE ed.emprunt.id = :empruntId";
            return em.createQuery(query, EmpruntDetail.class)
                    .setParameter("empruntId", empruntId)
                    .getResultList();
        });
    }

    @Override
    public boolean enregistrerRetour(long empruntDetailId, Date dateRetourActuelle) {
        return executeInTransaction(em -> {
            EmpruntDetail detail = em.find(EmpruntDetail.class, empruntDetailId);
            if (detail == null) {
                return false;
            }

            detail.setDateRetourActuelle(dateRetourActuelle);
            detail.setStatus("RETOURNE");

            Document document = detail.getDocument();
            document.setNombreExemplaires(document.getNombreExemplaires() + 1);
            return true;
        });
    }

    @Override
    public boolean updateStatutEmprunt(long empruntId, String nouveauStatut) {
        return executeInTransaction(em -> {
            Emprunt emprunt = em.find(Emprunt.class, empruntId);
            if (emprunt == null) {
                return false;
            }

            emprunt.setStatus(nouveauStatut);
            return true;
        });
    }
}
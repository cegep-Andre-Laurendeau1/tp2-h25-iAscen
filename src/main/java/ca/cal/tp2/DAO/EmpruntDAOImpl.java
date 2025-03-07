package ca.cal.tp2.DAO;

import ca.cal.tp2.modele.Emprunt;
import ca.cal.tp2.modele.EmpruntDetail;
import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Emprunteur;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

public class EmpruntDAOImpl implements EmpruntDAO {

    private final EntityManagerFactory emf;

    public EmpruntDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("tp2.h25");
    }

    @Override
    public List<Emprunt> getEmprunts() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            List<Emprunt> emprunts = em.createQuery("SELECT e FROM Emprunt e", Emprunt.class)
                    .getResultList();

            em.getTransaction().commit();
            return emprunts;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Emprunt creerEmprunt(Emprunteur emprunteur, Date dateEmprunt) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Emprunteur emprunteurManaged = em.find(Emprunteur.class, emprunteur.getUserID());
            if (emprunteurManaged == null) {
                throw new IllegalArgumentException("Emprunteur non trouvé avec ID: " + emprunteur.getUserID());
            }

            Emprunt emprunt = new Emprunt();
            emprunt.setEmprunteur(emprunteurManaged);
            emprunt.setDateEmprunt(dateEmprunt);
            emprunt.setStatus("ACTIF");

            em.persist(emprunt);

            em.getTransaction().commit();
            return emprunt;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public EmpruntDetail ajouterDocumentAEmprunt(Emprunt emprunt, Document document, Date dateRetourPrevue) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

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

            em.getTransaction().commit();
            return detail;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emprunt> getEmpruntsParEmprunteur(long emprunteurId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String query = "SELECT e FROM Emprunt e WHERE e.emprunteur.userID = :emprunteurId";
            List<Emprunt> emprunts = em.createQuery(query, Emprunt.class)
                    .setParameter("emprunteurId", emprunteurId)
                    .getResultList();

            em.getTransaction().commit();
            return emprunts;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Emprunt getEmpruntById(long empruntId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Emprunt emprunt = em.find(Emprunt.class, empruntId);

            em.getTransaction().commit();
            return emprunt;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<EmpruntDetail> getDetailsEmprunt(long empruntId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String query = "SELECT ed FROM EmpruntDetail ed WHERE ed.emprunt.id = :empruntId";
            List<EmpruntDetail> details = em.createQuery(query, EmpruntDetail.class)
                    .setParameter("empruntId", empruntId)
                    .getResultList();

            em.getTransaction().commit();
            return details;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean enregistrerRetour(long empruntDetailId, Date dateRetourActuelle) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            EmpruntDetail detail = em.find(EmpruntDetail.class, empruntDetailId);
            if (detail == null) {
                return false;
            }

            detail.setDateRetourActuelle(dateRetourActuelle);
            detail.setStatus("RETOURNE");

            Document document = detail.getDocument();
            document.setNombreExemplaires(document.getNombreExemplaires() + 1);

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateStatutEmprunt(long empruntId, String nouveauStatut) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Emprunt emprunt = em.find(Emprunt.class, empruntId);
            if (emprunt == null) {
                return false;
            }

            emprunt.setStatus(nouveauStatut);

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
package ca.cal.tp2.DAO;

import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Livre;
import ca.cal.tp2.modele.CD;
import ca.cal.tp2.modele.DVD;

import jakarta.persistence.*;
import java.util.List;

public class DocumentDAOImpl implements DocumentDAO {

    private final EntityManagerFactory emf;

    public DocumentDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("tp2.h25");
    }

    @Override
    public List<Document> getDocuments() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            List<Document> documents = em.createQuery("SELECT d FROM Document d", Document.class)
                    .getResultList();

            em.getTransaction().commit();
            return documents;
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
    public Livre ajouterLivre(Livre livre) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(livre);

            em.getTransaction().commit();
            return livre;
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
    public CD ajouterCD(CD cd) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(cd);

            em.getTransaction().commit();
            return cd;
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
    public DVD ajouterDVD(DVD dvd) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(dvd);

            em.getTransaction().commit();
            return dvd;
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
    public List<Document> rechercherParTitre(String titre) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String query = "SELECT d FROM Document d WHERE LOWER(d.titre) LIKE :titre";
            List<Document> documents = em.createQuery(query, Document.class)
                    .setParameter("titre", "%" + titre.toLowerCase() + "%")
                    .getResultList();

            em.getTransaction().commit();
            return documents;
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
    public List<Livre> rechercherLivresParAuteur(String auteur) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String query = "SELECT l FROM Livre l WHERE LOWER(l.auteur) LIKE :auteur";
            List<Livre> livres = em.createQuery(query, Livre.class)
                    .setParameter("auteur", "%" + auteur.toLowerCase() + "%")
                    .getResultList();

            em.getTransaction().commit();
            return livres;
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
    public List<CD> rechercherCDsParArtiste(String artiste) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String query = "SELECT c FROM CD c WHERE LOWER(c.artiste) LIKE :artiste";
            List<CD> cds = em.createQuery(query, CD.class)
                    .setParameter("artiste", "%" + artiste.toLowerCase() + "%")
                    .getResultList();

            em.getTransaction().commit();
            return cds;
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
    public List<DVD> rechercherDVDsParDirecteur(String directeur) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String query = "SELECT d FROM DVD d WHERE LOWER(d.directeur) LIKE :directeur";
            List<DVD> dvds = em.createQuery(query, DVD.class)
                    .setParameter("directeur", "%" + directeur.toLowerCase() + "%")
                    .getResultList();

            em.getTransaction().commit();
            return dvds;
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
    public Document getDocumentById(long documentId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Document document = em.find(Document.class, documentId);

            em.getTransaction().commit();
            return document;
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
    public boolean updateNombreExemplaires(long documentId, int nouveauNombreExemplaires) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Document document = em.find(Document.class, documentId);
            if (document == null) {
                return false;
            }

            document.setNombreExemplaires(nouveauNombreExemplaires);

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
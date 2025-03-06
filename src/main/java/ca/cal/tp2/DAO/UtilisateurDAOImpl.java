package ca.cal.tp2.DAO;

import ca.cal.tp2.modele.Utilisateur;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.modele.Prepose;

import jakarta.persistence.*;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {

    private final EntityManagerFactory emf;

    public UtilisateurDAOImpl() {
        this.emf = Persistence.createEntityManagerFactory("tp2.h25");
    }

    @Override
    public List<Utilisateur> getUtilisateurs() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            List<Utilisateur> utilisateurs = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)
                    .getResultList();

            em.getTransaction().commit();
            return utilisateurs;
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
    public List<Emprunteur> getEmprunteurs() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            List<Emprunteur> emprunteurs = em.createQuery("SELECT e FROM Emprunteur e", Emprunteur.class)
                    .getResultList();

            em.getTransaction().commit();
            return emprunteurs;
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
    public List<Prepose> getPreposes() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            List<Prepose> preposes = em.createQuery("SELECT p FROM Prepose p", Prepose.class)
                    .getResultList();

            em.getTransaction().commit();
            return preposes;
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
    public Emprunteur ajouterEmprunteur(Emprunteur emprunteur) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(emprunteur);

            em.getTransaction().commit();
            return emprunteur;
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
    public Prepose ajouterPrepose(Prepose prepose) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(prepose);

            em.getTransaction().commit();
            return prepose;
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
    public List<Emprunteur> rechercherEmprunteurParNom(String nom) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            String query = "SELECT e FROM Emprunteur e WHERE LOWER(e.name) LIKE :nom";
            List<Emprunteur> emprunteurs = em.createQuery(query, Emprunteur.class)
                    .setParameter("nom", "%" + nom.toLowerCase() + "%")
                    .getResultList();

            em.getTransaction().commit();
            return emprunteurs;
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
    public Utilisateur getUtilisateurById(long utilisateurId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Utilisateur utilisateur = em.find(Utilisateur.class, utilisateurId);

            em.getTransaction().commit();
            return utilisateur;
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
    public Emprunteur getEmprunteurById(long emprunteurId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Emprunteur emprunteur = em.find(Emprunteur.class, emprunteurId);

            em.getTransaction().commit();
            return emprunteur;
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
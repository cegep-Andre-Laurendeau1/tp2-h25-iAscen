package ca.cal.tp2.DAO;

import ca.cal.tp2.modele.Utilisateur;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.modele.Prepose;

import static ca.cal.tp2.DAO.EmParentDAO.executeInTransaction;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {

    @Override
    public List<Utilisateur> getUtilisateurs() {
        return executeInTransaction(em ->
                em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)
                        .getResultList()
        );
    }

    @Override
    public List<Emprunteur> getEmprunteurs() {
        return executeInTransaction(em ->
                em.createQuery("SELECT e FROM Emprunteur e", Emprunteur.class)
                        .getResultList()
        );
    }

    @Override
    public List<Prepose> getPreposes() {
        return executeInTransaction(em ->
                em.createQuery("SELECT p FROM Prepose p", Prepose.class)
                        .getResultList()
        );
    }

    @Override
    public Emprunteur ajouterEmprunteur(Emprunteur emprunteur) {
        return executeInTransaction(em -> {
            em.persist(emprunteur);
            return emprunteur;
        });
    }

    @Override
    public Prepose ajouterPrepose(Prepose prepose) {
        return executeInTransaction(em -> {
            em.persist(prepose);
            return prepose;
        });
    }

    @Override
    public List<Emprunteur> rechercherEmprunteurParNom(String nom) {
        return executeInTransaction(em -> {
            String query = "SELECT e FROM Emprunteur e WHERE LOWER(e.name) LIKE :nom";
            return em.createQuery(query, Emprunteur.class)
                    .setParameter("nom", "%" + nom.toLowerCase() + "%")
                    .getResultList();
        });
    }

    @Override
    public Utilisateur getUtilisateurById(long utilisateurId) {
        return executeInTransaction(em ->
                em.find(Utilisateur.class, utilisateurId)
        );
    }

    @Override
    public Emprunteur getEmprunteurById(long emprunteurId) {
        return executeInTransaction(em ->
                em.find(Emprunteur.class, emprunteurId)
        );
    }
}
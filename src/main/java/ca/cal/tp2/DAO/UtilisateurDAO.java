package ca.cal.tp2.DAO;

import ca.cal.tp2.modele.Utilisateur;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.modele.Prepose;

import java.util.List;

public interface UtilisateurDAO {
    List<Utilisateur> getUtilisateurs();
    List<Emprunteur> getEmprunteurs();
    List<Prepose> getPreposes();
    Emprunteur ajouterEmprunteur(Emprunteur emprunteur);
    Prepose ajouterPrepose(Prepose prepose);
    List<Emprunteur> rechercherEmprunteurParNom(String nom);
    Utilisateur getUtilisateurById(long utilisateurId);
    Emprunteur getEmprunteurById(long emprunteurId);
}
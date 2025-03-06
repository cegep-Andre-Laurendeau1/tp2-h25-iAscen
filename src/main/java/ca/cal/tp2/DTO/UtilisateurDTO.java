package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.Utilisateur;

public record UtilisateurDTO(
        long userID,
        String name,
        String email,
        String phoneNumber
) {
    public static UtilisateurDTO fromEntity(Utilisateur utilisateur) {
        return new UtilisateurDTO(
                utilisateur.getUserID(),
                utilisateur.getName(),
                utilisateur.getEmail(),
                utilisateur.getPhoneNumber()
        );
    }

    @Override
    public String toString() {
        return "Utilisateur: " + userID +
                ", Nom: " + name +
                ", Email: " + email +
                ", Téléphone: " + phoneNumber;
    }
}
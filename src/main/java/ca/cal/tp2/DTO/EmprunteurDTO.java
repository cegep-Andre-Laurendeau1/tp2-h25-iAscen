package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.Emprunteur;

public record EmprunteurDTO(
        long userID,
        String name,
        String email,
        String phoneNumber
) {
    public static EmprunteurDTO fromEntity(Emprunteur emprunteur) {
        return new EmprunteurDTO(
                emprunteur.getUserID(),
                emprunteur.getName(),
                emprunteur.getEmail(),
                emprunteur.getPhoneNumber()
        );
    }

    @Override
    public String toString() {
        return "Emprunteur: " + userID() +
                ", Nom: " + name() +
                ", Email: " + email() +
                ", Téléphone: " + phoneNumber();
    }
}
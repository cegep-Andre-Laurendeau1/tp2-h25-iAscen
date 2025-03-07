package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.Prepose;

public record PreposeDTO(
        long userID,
        String name,
        String email,
        String phoneNumber
) {
    public static PreposeDTO fromEntity(Prepose prepose) {
        return new PreposeDTO(
                prepose.getUserID(),
                prepose.getName(),
                prepose.getEmail(),
                prepose.getPhoneNumber()
        );
    }

    @Override
    public String toString() {
        return "Préposé: " + userID() +
                ", Nom: " + name() +
                ", Email: " + email() +
                ", Téléphone: " + phoneNumber();
    }
}
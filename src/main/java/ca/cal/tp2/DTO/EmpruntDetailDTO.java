package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.EmpruntDetail;

import java.util.Date;

public record EmpruntDetailDTO(
        long id,
        long empruntID,
        DocumentDTO document,
        Date dateRetourPrevue,
        Date dateRetourActuelle,
        String status
) {
    public static EmpruntDetailDTO fromEntity(EmpruntDetail detail) {
        return new EmpruntDetailDTO(
                detail.getId(),
                detail.getEmprunt().getId(),
                DocumentDTO.fromEntity(detail.getDocument()),
                detail.getDateRetourPrevue(),
                detail.getDateRetourActuelle(),
                detail.getStatus()
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Document: ").append(document.titre())
                .append(" (ID: ").append(document.documentID()).append(")")
                .append(", Retour prévu: ").append(dateRetourPrevue);

        if (dateRetourActuelle != null) {
            sb.append(", Retourné le: ").append(dateRetourActuelle);
        }

        sb.append(", Statut: ").append(status);

        return sb.toString();
    }
}
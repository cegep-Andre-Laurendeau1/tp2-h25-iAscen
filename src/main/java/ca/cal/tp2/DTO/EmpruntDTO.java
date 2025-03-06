package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.Emprunt;
import ca.cal.tp2.modele.EmpruntDetail;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record EmpruntDTO(
        long id,
        long emprunteurID,
        String emprunteurName,
        Date dateEmprunt,
        String status,
        List<EmpruntDetailDTO> empruntDetails
) {
    public static EmpruntDTO fromEntity(Emprunt emprunt, List<EmpruntDetail> details) {
        List<EmpruntDetailDTO> detailDTOs = details.stream()
                .map(EmpruntDetailDTO::fromEntity)
                .collect(Collectors.toList());

        return new EmpruntDTO(
                emprunt.getId(),
                emprunt.getEmprunteur().getUserID(),
                emprunt.getEmprunteur().getName(),
                emprunt.getDateEmprunt(),
                emprunt.getStatus(),
                detailDTOs
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Emprunt: ").append(id)
                .append(", Emprunteur: ").append(emprunteurName).append(" (ID: ").append(emprunteurID).append(")")
                .append(", Date: ").append(dateEmprunt)
                .append(", Statut: ").append(status)
                .append("\nDétails:\n");

        if (empruntDetails != null) {
            for (EmpruntDetailDTO detail : empruntDetails) {
                sb.append("  - ").append(detail).append("\n");
            }
        }

        return sb.toString();
    }
}
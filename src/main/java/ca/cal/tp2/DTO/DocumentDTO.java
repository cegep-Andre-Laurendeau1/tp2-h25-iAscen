package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.Document;
import ca.cal.tp2.modele.Livre;
import ca.cal.tp2.modele.CD;
import ca.cal.tp2.modele.DVD;

public record DocumentDTO(
        long documentID,
        String titre,
        int nombreExemplaires,
        String type
) {
    public static DocumentDTO fromEntity(Document document) {
        String type = "";
        if (document instanceof Livre) {
            type = "LIVRE";
        } else if (document instanceof CD) {
            type = "CD";
        } else if (document instanceof DVD) {
            type = "DVD";
        }

        return new DocumentDTO(
                document.getDocumentID(),
                document.getTitre(),
                document.getNombreExemplaires(),
                type
        );
    }

    @Override
    public String toString() {
        return "Document: " + documentID +
                ", Titre: " + titre +
                ", Exemplaires: " + nombreExemplaires +
                ", Type: " + type;
    }
}
package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.DVD;

public record DVDDTO(
        long documentID,
        String titre,
        int nombreExemplaires,
        String directeur,
        int duree,
        String rating
) {
    public DVDDTO(DocumentDTO documentDTO, String directeur, int duree, String rating) {
        this(documentDTO.documentID(), documentDTO.titre(), documentDTO.nombreExemplaires(),
                directeur, duree, rating);
    }

    public static DVDDTO fromEntity(DVD dvd) {
        return new DVDDTO(
                dvd.getDocumentID(),
                dvd.getTitre(),
                dvd.getNombreExemplaires(),
                dvd.getDirecteur(),
                dvd.getDuree(),
                dvd.getRating()
        );
    }

    @Override
    public String toString() {
        return "DVD: " + documentID() +
                ", Titre: " + titre() +
                ", Exemplaires: " + nombreExemplaires() +
                ", Directeur: " + directeur +
                ", Durée: " + duree +
                ", Rating: " + rating;
    }
}
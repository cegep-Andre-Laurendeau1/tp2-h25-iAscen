package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.CD;

public record CDDTO(
        long documentID,
        String titre,
        int nombreExemplaires,
        String artiste,
        int duree,
        String genre
) {
    public CDDTO(DocumentDTO documentDTO, String artiste, int duree, String genre) {
        this(documentDTO.documentID(), documentDTO.titre(), documentDTO.nombreExemplaires(),
                artiste, duree, genre);
    }

    public static CDDTO fromEntity(CD cd) {
        return new CDDTO(
                cd.getDocumentID(),
                cd.getTitre(),
                cd.getNombreExemplaires(),
                cd.getArtiste(),
                cd.getDuree(),
                cd.getGenre()
        );
    }

    @Override
    public String toString() {
        return "CD: " + documentID() +
                ", Titre: " + titre() +
                ", Exemplaires: " + nombreExemplaires() +
                ", Artiste: " + artiste +
                ", Durée: " + duree +
                ", Genre: " + genre;
    }
}
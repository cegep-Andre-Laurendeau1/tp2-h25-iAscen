package ca.cal.tp2.DTO;

import ca.cal.tp2.modele.Livre;

public record LivreDTO(
        long documentID,
        String titre,
        int nombreExemplaires,
        String ISBN,
        String auteur,
        String editeur,
        int nombrePages
) {
    public LivreDTO(DocumentDTO documentDTO, String ISBN, String auteur, String editeur, int nombrePages) {
        this(documentDTO.documentID(), documentDTO.titre(), documentDTO.nombreExemplaires(),
                ISBN, auteur, editeur, nombrePages);
    }

    public static LivreDTO fromEntity(Livre livre) {
        return new LivreDTO(
                livre.getDocumentID(),
                livre.getTitre(),
                livre.getNombreExemplaires(),
                livre.getISBN(),
                livre.getAuteur(),
                livre.getEditeur(),
                livre.getNombrePages()
        );
    }

    @Override
    public String toString() {
        return "Livre: " + documentID() +
                ", Titre: " + titre() +
                ", Exemplaires: " + nombreExemplaires() +
                ", ISBN: " + ISBN +
                ", Auteur: " + auteur +
                ", Éditeur: " + editeur +
                ", Pages: " + nombrePages;
    }
}
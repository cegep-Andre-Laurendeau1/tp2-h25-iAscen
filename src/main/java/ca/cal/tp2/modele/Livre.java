package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LIVRES")
@DiscriminatorValue("LIVRE")
@Getter
@Setter
@NoArgsConstructor
public class Livre extends Document {

    @Column(name = "ISBN")
    private String ISBN;

    @Column(name = "AUTEUR")
    private String auteur;

    @Column(name = "EDITEUR")
    private String editeur;

    @Column(name = "NOMBRE_PAGES")
    private int nombrePages;

    public Livre(long documentID, String titre, int nombreExemplaires, String ISBN, String auteur, String editeur, int nombrePages) {
        super(documentID, titre, nombreExemplaires);
        this.ISBN = ISBN;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nombrePages = nombrePages;
    }

    @Override
    public boolean verifieDisponibilite() {
        return nombreExemplaires > 0;
    }
}
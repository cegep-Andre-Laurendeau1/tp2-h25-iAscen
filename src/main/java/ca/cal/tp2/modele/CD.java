package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CDS")
@DiscriminatorValue("CD")
@Getter
@Setter
@NoArgsConstructor
public class CD extends Document {

    @Column(name = "ARTISTE")
    private String artiste;

    @Column(name = "DUREE")
    private int duree;

    @Column(name = "GENRE")
    private String genre;

    public CD(long documentID, String titre, int nombreExemplaires, String artiste, int duree, String genre) {
        super(documentID, titre, nombreExemplaires);
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
    }

    @Override
    public boolean verifieDisponibilite() {
        return nombreExemplaires > 0;
    }
}
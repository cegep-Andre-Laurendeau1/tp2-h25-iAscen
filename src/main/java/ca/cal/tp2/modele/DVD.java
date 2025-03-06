package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DVDS")
@DiscriminatorValue("DVD")
@Getter
@Setter
@NoArgsConstructor
public class DVD extends Document {

    @Column(name = "DIRECTOR")
    private String directeur;

    @Column(name = "DUREE")
    private int duree;

    @Column(name = "RATING")
    private String rating;

    public DVD(long documentID, String titre, int nombreExemplaires, String directeur, int duree, String rating) {
        super(documentID, titre, nombreExemplaires);
        this.directeur = directeur;
        this.duree = duree;
        this.rating = rating;
    }

    @Override
    public boolean verifieDisponibilite() {
        return nombreExemplaires > 0;
    }
}
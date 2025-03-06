package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PREPOSES")
@DiscriminatorValue("PREPOSE")
@Getter
@Setter
@NoArgsConstructor
public class Prepose extends Utilisateur {

    public Prepose(long userID, String name, String email, String phoneNumber) {
        super(userID, name, email, phoneNumber);
    }

    @Override
    public void login() {
        // Implémentation de la méthode login
    }

    public void entreNouveauDocument(Document document) {
    }

    public void collecteAmende(Emprunteur emprunteur, double montant) {
    }

    public void rapportAmendes() {
    }

    public void rapportEmprunts() {
    }

    @Override
    public String toString() {
        return "Prepose{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
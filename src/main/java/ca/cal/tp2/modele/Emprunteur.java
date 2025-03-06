package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "EMPRUNTEURS")
@DiscriminatorValue("EMPRUNTEUR")
@Getter
@Setter
@NoArgsConstructor
public class Emprunteur extends Utilisateur {

    @OneToMany(mappedBy = "emprunteur", cascade = CascadeType.ALL)
    private List<Emprunt> emprunts = new ArrayList<>();

    public Emprunteur(long userID, String name, String email, String phoneNumber) {
        super(userID, name, email, phoneNumber);
    }

    @Override
    public void login() {
    }

    public void emprunte(Document document) {
    }

    public void retourneDocument(Document document) {
    }

    public void rapportHistoriqueEmprunt() {
    }

    public List<Emprunt> getEmprunts() {
        return Collections.unmodifiableList(emprunts);
    }

    public void addEmprunt(Emprunt emprunt) {
        emprunt.setEmprunteur(this);
        emprunts.add(emprunt);
    }

    @Override
    public String toString() {
        return "Emprunteur{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
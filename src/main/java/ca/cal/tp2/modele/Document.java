package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "DOCUMENTS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_DOCUMENT")
@Getter
@Setter
@NoArgsConstructor
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCUMENT_ID")
    protected long documentID;

    @Column(name = "TITRE")
    protected String titre;

    @Column(name = "NOMBRE_EXEMPLAIRES")
    protected int nombreExemplaires;

    @OneToMany(mappedBy = "document")
    private List<EmpruntDetail> empruntsDetails = new ArrayList<>();

    public Document(long documentID, String titre, int nombreExemplaires) {
        this.documentID = documentID;
        this.titre = titre;
        this.nombreExemplaires = nombreExemplaires;
    }

    public List<EmpruntDetail> getEmpruntsDetails() {
        return Collections.unmodifiableList(empruntsDetails);
    }

    public void addEmpruntDetail(EmpruntDetail empruntDetail) {
        empruntsDetails.add(empruntDetail);
    }

    public abstract boolean verifieDisponibilite();
}
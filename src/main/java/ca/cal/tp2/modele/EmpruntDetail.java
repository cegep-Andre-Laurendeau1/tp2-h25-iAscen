package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "EMPRUNT_DETAILS")
@Getter
@Setter
@NoArgsConstructor
public class EmpruntDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_ITEM_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "EMPRUNT_ID")
    private Emprunt emprunt;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @Column(name = "DATE_RETOUR_PREVUE")
    @Temporal(TemporalType.DATE)
    private Date dateRetourPrevue;

    @Column(name = "DATE_RETOUR_ACTUELLE")
    @Temporal(TemporalType.DATE)
    private Date dateRetourActuelle;

    @Column(name = "STATUS")
    private String status;

    public EmpruntDetail(long id, Emprunt emprunt, Document document,
                         Date dateRetourPrevue, Date dateRetourActuelle, String status) {
        this.id = id;
        this.emprunt = emprunt;
        this.document = document;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourActuelle = dateRetourActuelle;
        this.status = status;
    }

    public boolean isEnRetard() {
        return dateRetourActuelle != null && dateRetourActuelle.after(dateRetourPrevue);
    }

    public void calculAmende() {
    }

    public void updateStatus() {
    }
}
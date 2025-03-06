package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "EMPRUNTS")
@Getter
@Setter
@NoArgsConstructor
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BORROW_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "EMPRUNTEUR_ID")
    private Emprunteur emprunteur;

    @Column(name = "DATE_EMPRUNT")
    @Temporal(TemporalType.DATE)
    private Date dateEmprunt;

    @Column(name = "STATUS")
    private String status;

    @OneToMany(mappedBy = "emprunt", cascade = CascadeType.ALL)
    private List<EmpruntDetail> empruntDetails = new ArrayList<>();

    public Emprunt(long id, Emprunteur emprunteur, Date dateEmprunt, String status) {
        this.id = id;
        this.emprunteur = emprunteur;
        this.dateEmprunt = dateEmprunt;
        this.status = status;
    }

    public List<EmpruntDetail> getEmpruntDetails() {
        return Collections.unmodifiableList(empruntDetails);
    }

    public void addEmpruntDetail(EmpruntDetail empruntDetail) {
        empruntDetail.setEmprunt(this);
        empruntDetails.add(empruntDetail);
    }

    public void getItems() {
    }
}
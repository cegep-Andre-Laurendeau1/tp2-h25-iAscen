package ca.cal.tp2.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UTILISATEURS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_UTILISATEUR")
@Getter
@Setter
@NoArgsConstructor
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    protected long userID;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "PHONE_NUMBER")
    protected String phoneNumber;

    public Utilisateur(long userID, String name, String email, String phoneNumber) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public abstract void login();
}
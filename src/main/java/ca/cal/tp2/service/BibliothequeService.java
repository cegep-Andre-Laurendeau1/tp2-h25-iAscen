package ca.cal.tp2.service;

import ca.cal.tp2.DAO.DocumentDAO;
import ca.cal.tp2.DAO.EmpruntDAO;
import ca.cal.tp2.DAO.UtilisateurDAO;
import ca.cal.tp2.DTO.*;
import ca.cal.tp2.modele.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BibliothequeService {

    private final DocumentDAO documentDAO;
    private final EmpruntDAO empruntDAO;
    private final UtilisateurDAO utilisateurDAO;

    public BibliothequeService(DocumentDAO documentDAO, EmpruntDAO empruntDAO, UtilisateurDAO utilisateurDAO) {
        this.documentDAO = documentDAO;
        this.empruntDAO = empruntDAO;
        this.utilisateurDAO = utilisateurDAO;
    }

    public List<UtilisateurDTO> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurDAO.getUtilisateurs();
        return utilisateurs.stream()
                .map(UtilisateurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PreposeDTO> getAllPreposes() {
        List<Prepose> preposes = utilisateurDAO.getPreposes();
        return preposes.stream()
                .map(PreposeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public PreposeDTO ajouterPrepose(PreposeDTO preposeDTO) {
        Prepose prepose = new Prepose();
        prepose.setName(preposeDTO.name());
        prepose.setEmail(preposeDTO.email());
        prepose.setPhoneNumber(preposeDTO.phoneNumber());

        prepose = utilisateurDAO.ajouterPrepose(prepose);

        return PreposeDTO.fromEntity(prepose);
    }

    public UtilisateurDTO getUtilisateurById(long userId) {
        Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(userId);
        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé avec ID: " + userId);
        }
        return UtilisateurDTO.fromEntity(utilisateur);
    }

    public List<DocumentDTO> getAllDocuments() {
        List<Document> documents = documentDAO.getDocuments();
        return documents.stream()
                .map(DocumentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public LivreDTO ajouterLivre(LivreDTO livreDTO) {
        Livre livre = new Livre();
        livre.setTitre(livreDTO.titre());
        livre.setNombreExemplaires(livreDTO.nombreExemplaires());
        livre.setISBN(livreDTO.ISBN());
        livre.setAuteur(livreDTO.auteur());
        livre.setEditeur(livreDTO.editeur());
        livre.setNombrePages(livreDTO.nombrePages());

        livre = documentDAO.ajouterLivre(livre);

        return LivreDTO.fromEntity(livre);
    }

    public CDDTO ajouterCD(CDDTO cdDTO) {
        CD cd = new CD();
        cd.setTitre(cdDTO.titre());
        cd.setNombreExemplaires(cdDTO.nombreExemplaires());
        cd.setArtiste(cdDTO.artiste());
        cd.setDuree(cdDTO.duree());
        cd.setGenre(cdDTO.genre());

        cd = documentDAO.ajouterCD(cd);

        return CDDTO.fromEntity(cd);
    }

    public DVDDTO ajouterDVD(DVDDTO dvdDTO) {
        DVD dvd = new DVD();
        dvd.setTitre(dvdDTO.titre());
        dvd.setNombreExemplaires(dvdDTO.nombreExemplaires());
        dvd.setDirecteur(dvdDTO.directeur());
        dvd.setDuree(dvdDTO.duree());
        dvd.setRating(dvdDTO.rating());

        dvd = documentDAO.ajouterDVD(dvd);

        return DVDDTO.fromEntity(dvd);
    }

    public List<DocumentDTO> rechercherParTitre(String titre) {
        List<Document> documents = documentDAO.rechercherParTitre(titre);
        return documents.stream()
                .map(DocumentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<LivreDTO> rechercherLivresParAuteur(String auteur) {
        List<Livre> livres = documentDAO.rechercherLivresParAuteur(auteur);
        return livres.stream()
                .map(LivreDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CDDTO> rechercherCDsParArtiste(String artiste) {
        List<CD> cds = documentDAO.rechercherCDsParArtiste(artiste);
        return cds.stream()
                .map(CDDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<DVDDTO> rechercherDVDsParDirecteur(String directeur) {
        List<DVD> dvds = documentDAO.rechercherDVDsParDirecteur(directeur);
        return dvds.stream()
                .map(DVDDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public EmprunteurDTO ajouterEmprunteur(EmprunteurDTO emprunteurDTO) {
        Emprunteur emprunteur = new Emprunteur();
        emprunteur.setName(emprunteurDTO.name());
        emprunteur.setEmail(emprunteurDTO.email());
        emprunteur.setPhoneNumber(emprunteurDTO.phoneNumber());

        emprunteur = utilisateurDAO.ajouterEmprunteur(emprunteur);

        return EmprunteurDTO.fromEntity(emprunteur);
    }

    public List<EmprunteurDTO> rechercherEmprunteurParNom(String nom) {
        List<Emprunteur> emprunteurs = utilisateurDAO.rechercherEmprunteurParNom(nom);
        return emprunteurs.stream()
                .map(EmprunteurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public EmpruntDTO emprunterDocument(long emprunteurId, long documentId) {
        Emprunteur emprunteur = utilisateurDAO.getEmprunteurById(emprunteurId);
        if (emprunteur == null) {
            throw new IllegalArgumentException("Emprunteur non trouvé avec ID: " + emprunteurId);
        }

        Document document = documentDAO.getDocumentById(documentId);
        if (document == null) {
            throw new IllegalArgumentException("Document non trouvé avec ID: " + documentId);
        }

        if (!document.verifieDisponibilite()) {
            throw new IllegalStateException("Document non disponible: " + document.getTitre());
        }

        Date dateEmprunt = new Date();
        Emprunt emprunt = empruntDAO.creerEmprunt(emprunteur, dateEmprunt);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateEmprunt);

        if (document instanceof Livre) {
            calendar.add(Calendar.WEEK_OF_YEAR, 3);
        } else if (document instanceof CD) {
            calendar.add(Calendar.WEEK_OF_YEAR, 2);
        } else if (document instanceof DVD) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }

        Date dateRetourPrevue = calendar.getTime();

        empruntDAO.ajouterDocumentAEmprunt(emprunt, document, dateRetourPrevue);

        List<EmpruntDetail> details = empruntDAO.getDetailsEmprunt(emprunt.getId());

        return EmpruntDTO.fromEntity(emprunt, details);
    }

    public List<EmpruntDTO> getEmpruntsParEmprunteur(long emprunteurId) {
        Emprunteur emprunteur = utilisateurDAO.getEmprunteurById(emprunteurId);
        if (emprunteur == null) {
            throw new IllegalArgumentException("Emprunteur non trouvé avec ID: " + emprunteurId);
        }

        List<Emprunt> emprunts = empruntDAO.getEmpruntsParEmprunteur(emprunteurId);
        List<EmpruntDTO> empruntDTOs = new ArrayList<>();

        for (Emprunt emprunt : emprunts) {
            List<EmpruntDetail> details = empruntDAO.getDetailsEmprunt(emprunt.getId());
            empruntDTOs.add(EmpruntDTO.fromEntity(emprunt, details));
        }

        return empruntDTOs;
    }

    public boolean retournerDocument(long empruntDetailId) {
        return empruntDAO.enregistrerRetour(empruntDetailId, new Date());
    }
}
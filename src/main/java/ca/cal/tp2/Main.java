package ca.cal.tp2;

import ca.cal.tp2.DAO.*;
import ca.cal.tp2.DTO.*;
import ca.cal.tp2.modele.Emprunt;
import ca.cal.tp2.modele.Emprunteur;
import ca.cal.tp2.modele.Prepose;
import ca.cal.tp2.modele.Utilisateur;
import ca.cal.tp2.service.BibliothequeService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        // Démarrer le serveur TCP H2
        TcpServer.createTcpServer();

        // Initialiser les DAOs
        DocumentDAO documentDAO = new DocumentDAOImpl();
        EmpruntDAO empruntDAO = new EmpruntDAOImpl();
        UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();

        // Initialiser le service
        BibliothequeService bibliothequeService = new BibliothequeService(documentDAO, empruntDAO, utilisateurDAO);

        System.out.println("========== DÉMONSTRATION DE LA BIBLIOTHÈQUE ==========\n");

        // 0. Gestion des utilisateurs
        System.out.println("0. GESTION DES UTILISATEURS");

        // Ajouter un préposé via le DTO
        PreposeDTO preposeDTO = new PreposeDTO(0, "Pierre Tremblay", "pierre.tremblay@biblio.com", "514-555-1234");
        PreposeDTO preposeAjoute = bibliothequeService.ajouterPrepose(preposeDTO);
        System.out.println("Préposé ajouté via DTO: " + preposeAjoute);

        // Ajouter un autre préposé directement
        Prepose prepose2 = new Prepose();
        prepose2.setName("Jacques Martin");
        prepose2.setEmail("jacques.martin@biblio.com");
        prepose2.setPhoneNumber("514-555-5678");
        prepose2 = utilisateurDAO.ajouterPrepose(prepose2);
        System.out.println("Préposé ajouté directement: " + prepose2.getName() + " (ID: " + prepose2.getUserID() + ")");

        // Obtenir la liste de tous les utilisateurs en utilisant UtilisateurDTO
        System.out.println("\nListe de tous les utilisateurs via DTO:");
        List<UtilisateurDTO> utilisateursDTO = bibliothequeService.getAllUtilisateurs();
        utilisateursDTO.forEach(System.out::println);

        // Obtenir la liste des préposés en utilisant PreposeDTO
        System.out.println("\nListe des préposés via DTO:");
        List<PreposeDTO> preposesDTO = bibliothequeService.getAllPreposes();
        preposesDTO.forEach(System.out::println);

        // Obtenir la liste de tous les utilisateurs directement
        System.out.println("\nListe de tous les utilisateurs (modèles):");
        List<Utilisateur> utilisateurs = utilisateurDAO.getUtilisateurs();
        utilisateurs.forEach(u -> System.out.println("ID: " + u.getUserID() + ", Nom: " + u.getName() + ", Type: " + u.getClass().getSimpleName()));

        // Rechercher un utilisateur par ID avec DTO
        if (!utilisateursDTO.isEmpty()) {
            long utilisateurId = utilisateursDTO.get(0).userID();
            UtilisateurDTO utilisateurTrouveDTO = bibliothequeService.getUtilisateurById(utilisateurId);
            System.out.println("\nUtilisateur trouvé par ID " + utilisateurId + " (via DTO): " + utilisateurTrouveDTO);
        }

        System.out.println();

        // 1. Ajouter des emprunteurs
        System.out.println("1. AJOUT D'EMPRUNTEURS");
        EmprunteurDTO emprunteur1 = bibliothequeService.ajouterEmprunteur(
                new EmprunteurDTO(0, "Jean Dupont", "jean.dupont@email.com", "514-123-4567"));
        EmprunteurDTO emprunteur2 = bibliothequeService.ajouterEmprunteur(
                new EmprunteurDTO(0, "Marie Tremblay", "marie.tremblay@email.com", "514-987-6543"));

        System.out.println("Emprunteur ajouté: " + emprunteur1);
        System.out.println("Emprunteur ajouté: " + emprunteur2);

        // Obtenir la liste des emprunteurs
        System.out.println("\nListe des emprunteurs:");
        List<Emprunteur> emprunteurs = utilisateurDAO.getEmprunteurs();
        emprunteurs.forEach(e -> System.out.println("ID: " + e.getUserID() + ", Nom: " + e.getName() + ", Email: " + e.getEmail()));

        System.out.println();

        // 2. Ajouter des documents
        System.out.println("2. AJOUT DE DOCUMENTS");

        // Ajouter des livres
        LivreDTO livre1 = bibliothequeService.ajouterLivre(
                new LivreDTO(0, "Les Misérables", 3, "978-2-07-036822-8",
                        "Victor Hugo", "Gallimard", 1488));
        LivreDTO livre2 = bibliothequeService.ajouterLivre(
                new LivreDTO(0, "Le Petit Prince", 5, "978-2-07-040389-2",
                        "Antoine de Saint-Exupéry", "Gallimard", 96));
        LivreDTO livre3 = bibliothequeService.ajouterLivre(
                new LivreDTO(0, "Harry Potter à l'école des sorciers", 2, "978-2-07-054417-9",
                        "J.K. Rowling", "Gallimard", 308));

        // Ajouter des CDs
        CDDTO cd1 = bibliothequeService.ajouterCD(
                new CDDTO(0, "Thriller", 4, "Michael Jackson", 42, "Pop"));
        CDDTO cd2 = bibliothequeService.ajouterCD(
                new CDDTO(0, "Back in Black", 3, "AC/DC", 42, "Rock"));

        // Ajouter des DVDs
        DVDDTO dvd1 = bibliothequeService.ajouterDVD(
                new DVDDTO(0, "The Shawshank Redemption", 2, "Frank Darabont", 142, "R"));
        DVDDTO dvd2 = bibliothequeService.ajouterDVD(
                new DVDDTO(0, "Le fabuleux destin d'Amélie Poulain", 1, "Jean-Pierre Jeunet", 122, "R"));

        System.out.println("Livre ajouté: " + livre1);
        System.out.println("Livre ajouté: " + livre2);
        System.out.println("Livre ajouté: " + livre3);
        System.out.println("CD ajouté: " + cd1);
        System.out.println("CD ajouté: " + cd2);
        System.out.println("DVD ajouté: " + dvd1);
        System.out.println("DVD ajouté: " + dvd2);

        // Mettre à jour le nombre d'exemplaires d'un document
        System.out.println("\nMise à jour du stock de documents:");

        // Augmenter le nombre d'exemplaires des Misérables (nouveau stock reçu)
        DocumentDTO avantMiseAJour = bibliothequeService.rechercherParTitre("Misérables").get(0);
        System.out.println("Avant mise à jour - " + avantMiseAJour);

        boolean misAJour = documentDAO.updateNombreExemplaires(livre1.documentID(), 8);
        System.out.println("Mise à jour du nombre d'exemplaires pour 'Les Misérables': " + misAJour);

        DocumentDTO apresMiseAJour = bibliothequeService.rechercherParTitre("Misérables").get(0);
        System.out.println("Après mise à jour - " + apresMiseAJour);
        System.out.println();

        // 3. Rechercher des documents
        System.out.println("3. RECHERCHE DE DOCUMENTS");

        // Recherche par titre
        System.out.println("Recherche par titre 'Potter':");
        List<DocumentDTO> resultatsRechercheTitre = bibliothequeService.rechercherParTitre("Potter");
        resultatsRechercheTitre.forEach(System.out::println);
        System.out.println();

        // Recherche par auteur
        System.out.println("Recherche par auteur 'Hugo':");
        List<LivreDTO> resultatsRechercheAuteur = bibliothequeService.rechercherLivresParAuteur("Hugo");
        resultatsRechercheAuteur.forEach(System.out::println);
        System.out.println();

        // Recherche par artiste
        System.out.println("Recherche par artiste 'Jack':");
        List<CDDTO> resultatsRechercheArtiste = bibliothequeService.rechercherCDsParArtiste("Jack");
        resultatsRechercheArtiste.forEach(System.out::println);
        System.out.println();

        // Recherche par directeur
        System.out.println("Recherche par directeur 'Jeunet':");
        List<DVDDTO> resultatsRechercheDirecteur = bibliothequeService.rechercherDVDsParDirecteur("Jeunet");
        resultatsRechercheDirecteur.forEach(System.out::println);
        System.out.println();

        // 4. Effectuer des emprunts
        System.out.println("4. EMPRUNTS DE DOCUMENTS");

        // Jean Dupont emprunte Le Petit Prince et Thriller
        System.out.println("Jean Dupont emprunte Le Petit Prince:");
        EmpruntDTO emprunt1 = bibliothequeService.emprunterDocument(emprunteur1.userID(), livre2.documentID());
        System.out.println(emprunt1);

        System.out.println("Jean Dupont emprunte Thriller:");
        EmpruntDTO emprunt2 = bibliothequeService.emprunterDocument(emprunteur1.userID(), cd1.documentID());
        System.out.println(emprunt2);

        // Marie Tremblay emprunte Harry Potter et The Shawshank Redemption
        System.out.println("Marie Tremblay emprunte Harry Potter:");
        EmpruntDTO emprunt3 = bibliothequeService.emprunterDocument(emprunteur2.userID(), livre3.documentID());
        System.out.println(emprunt3);

        System.out.println("Marie Tremblay emprunte The Shawshank Redemption:");
        EmpruntDTO emprunt4 = bibliothequeService.emprunterDocument(emprunteur2.userID(), dvd1.documentID());
        System.out.println(emprunt4);
        System.out.println();

        // Afficher tous les emprunts
        System.out.println("Liste de tous les emprunts dans le système:");
        List<Emprunt> tousLesEmprunts = empruntDAO.getEmprunts();
        tousLesEmprunts.forEach(e -> System.out.println("Emprunt ID: " + e.getId() +
                ", Emprunteur: " + e.getEmprunteur().getName() +
                ", Date: " + e.getDateEmprunt() +
                ", Statut: " + e.getStatus()));
        System.out.println();

        // Récupérer un emprunt spécifique par ID
        if (!tousLesEmprunts.isEmpty()) {
            long empruntId = tousLesEmprunts.get(0).getId();
            Emprunt empruntRecupere = empruntDAO.getEmpruntById(empruntId);
            System.out.println("Détails de l'emprunt avec ID " + empruntId + ":");
            System.out.println("  Emprunteur: " + empruntRecupere.getEmprunteur().getName());
            System.out.println("  Date d'emprunt: " + empruntRecupere.getDateEmprunt());
            System.out.println("  Statut: " + empruntRecupere.getStatus());
            System.out.println();

            // Mettre à jour le statut d'un emprunt
            boolean statutMisAJour = empruntDAO.updateStatutEmprunt(empruntId, "EN_COURS");
            System.out.println("Statut de l'emprunt " + empruntId + " mis à jour: " + statutMisAJour);

            // Vérifier que le statut a bien été mis à jour
            Emprunt empruntApresMAJ = empruntDAO.getEmpruntById(empruntId);
            System.out.println("Nouveau statut: " + empruntApresMAJ.getStatus());
            System.out.println();
        }

        // 5. Voir les documents disponibles après les emprunts
        System.out.println("5. DOCUMENTS DISPONIBLES APRÈS EMPRUNTS");
        List<DocumentDTO> documentsApresEmprunts = bibliothequeService.getAllDocuments();
        documentsApresEmprunts.forEach(System.out::println);
        System.out.println();

        // 6. Consulter les emprunts d'un utilisateur
        System.out.println("6. CONSULTATION DES EMPRUNTS PAR UTILISATEUR");

        System.out.println("Emprunts de Jean Dupont:");
        List<EmpruntDTO> empruntsJean = bibliothequeService.getEmpruntsParEmprunteur(emprunteur1.userID());
        empruntsJean.forEach(System.out::println);

        System.out.println("Emprunts de Marie Tremblay:");
        List<EmpruntDTO> empruntsMarie = bibliothequeService.getEmpruntsParEmprunteur(emprunteur2.userID());
        empruntsMarie.forEach(System.out::println);
        System.out.println();

        // 7. Retourner un document
        System.out.println("7. RETOUR D'UN DOCUMENT");

        // Jean retourne Le Petit Prince
        if (empruntsJean.size() > 0 && empruntsJean.get(0).empruntDetails().size() > 0) {
            long empruntDetailId = empruntsJean.get(0).empruntDetails().get(0).id();
            System.out.println("Jean Dupont retourne le document avec ID de détail: " + empruntDetailId);
            boolean retourOk = bibliothequeService.retournerDocument(empruntDetailId);
            System.out.println("Retour effectué: " + retourOk);

            // Vérifier le nombre d'exemplaires mis à jour
            DocumentDTO livreRetourne = bibliothequeService.rechercherParTitre("Petit Prince").get(0);
            System.out.println("Livre après retour: " + livreRetourne);
        }
        System.out.println();

        // 8. Rechercher un emprunteur par nom
        System.out.println("8. RECHERCHE D'EMPRUNTEURS PAR NOM");
        List<EmprunteurDTO> emprunteursParNom = bibliothequeService.rechercherEmprunteurParNom("Trem");
        emprunteursParNom.forEach(System.out::println);

        System.out.println("\n========== FIN DE LA DÉMONSTRATION ==========");

        // Maintenir le serveur actif
        Thread.currentThread().join();
    }
}
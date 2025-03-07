package ca.cal.tp2;

import ca.cal.tp2.DAO.*;
import ca.cal.tp2.DTO.*;
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

        // 1. Ajouter des emprunteurs
        System.out.println("1. AJOUT D'EMPRUNTEURS");
        EmprunteurDTO emprunteur1 = bibliothequeService.ajouterEmprunteur(
                new EmprunteurDTO(0, "Jean Dupont", "jean.dupont@email.com", "514-123-4567"));
        EmprunteurDTO emprunteur2 = bibliothequeService.ajouterEmprunteur(
                new EmprunteurDTO(0, "Marie Tremblay", "marie.tremblay@email.com", "514-987-6543"));

        System.out.println("Emprunteur ajouté: " + emprunteur1);
        System.out.println("Emprunteur ajouté: " + emprunteur2);
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
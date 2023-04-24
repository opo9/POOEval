import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Magasin {

    // Définition des constantes pour les types de produits
    private static final int TYPE_UNIT = 1;
    private static final int TYPE_WEIGHT = 2;
    private static final int TYPE_ELECTRONIC = 3;
    private List<Produit> produits;

    public Magasin() {
        this.produits = new ArrayList<>();
    }

    public void ajouterProduit(Produit produit) {
        this.produits.add(produit);
    }
    
    public List<Produit> getListeProduits() {
        return this.produits;
    }

    public void afficherTousLesProduits() {
        for (Produit produit : this.produits) {
            System.out.println(produit.toString());
        }
    }

    // Définition de la classe Produit
    static class Produit {
        private String nom;
        private String description;
        private int type;
        private double prixUnitaire;
        private double prixKilo;
        private LocalDate dateAchat;
        private boolean garantie;
        private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
        private int id;

        // Constructeur pour les produits vendus à l'unité
        public Produit(String nom, String description, double prixUnitaire) {
            this.id = ID_GENERATOR.getAndIncrement();
           
            if (nom == null || description == null || nom == "" || description == "") {
                throw new IllegalArgumentException("Le nom et la description ne doivent pas être nuls.");
            }
            
            this.nom = nom;
            this.description = description;
            this.type = TYPE_UNIT;
            this.prixUnitaire = prixUnitaire;
        }

        public int getId() {
            return id;
        }

        // Constructeur pour les produits vendus au poids
        public Produit(String nom, String description, double prixKilo, LocalDate dateAchat) {
            this.id = ID_GENERATOR.getAndIncrement();
            if (nom == null || description == null || nom == "" || description == "") {
                throw new IllegalArgumentException("Le nom et la description ne doivent pas être nuls.");
            }
            this.nom = nom;
            this.description = description;
            this.type = TYPE_WEIGHT;
            this.prixKilo = prixKilo;
            this.dateAchat = dateAchat;
        }

        // Constructeur pour les appareils électroniques
        public Produit(String nom, String description, LocalDate dateAchat) {
            this.id = ID_GENERATOR.getAndIncrement();

            if (nom == null || description == null || nom == "" || description == "") {
                throw new IllegalArgumentException("Le nom et la description ne doivent pas être nuls.");
            }
            this.nom = nom;
            this.description = description;
            this.type = TYPE_ELECTRONIC;
            this.dateAchat = dateAchat;
            this.garantie = true;
        }

        // Méthode pour calculer le prix d'un produit vendu à l'unité
        public double calculerPrix(int quantite) {
            return quantite * prixUnitaire;
        }

        // Méthode pour calculer le prix d'un produit vendu au poids
        public double calculerPrix(double poids) {
            return poids * prixKilo;
        }

        // Méthode pour vérifier si un appareil électronique est encore sous garantie
        public boolean estSousGarantie() {
            return garantie && dateAchat.plusMonths(12).isAfter(LocalDate.now());
        }

        // Méthode pour afficher les informations d'un produit
        @Override
        public String toString() {
            String s = id + " " + nom + " - " + description + " : ";
            switch (type) {
                case TYPE_UNIT:
                    s += prixUnitaire + " euros l'unité";
                    break;
                case TYPE_WEIGHT:
                    s += prixKilo + " euros le kilo";
                    break;
                case TYPE_ELECTRONIC:
                    s += "appareil électronique";
                    if (estSousGarantie()) {
                        s += " sous garantie";
                    } else {
                        s += " hors garantie";
                    }
                    break;
            }
            return s;
        }
    }

    // Définition de la classe Panier
    static class Panier {
        private Map<Integer, List<Produit>> produits;

        public Panier() {
            produits = new HashMap<>();
        }

        // Méthode pour ajouter un produit au panier
        public void ajouterProduit(Produit produit) {
            List<Produit> liste = produits.getOrDefault(produit.type, new ArrayList<>());
            liste.add(produit);
            produits.put(produit.type, liste);
}
public void afficherContenu() {
        System.out.println("Contenu du panier :");
        for (int type : produits.keySet()) {
            System.out.println("Type " + type + " :");
            for (Produit produit : produits.get(type)) {
                System.out.println("- " + produit.nom);
            }
        }
    }

    // Méthode pour calculer le prix total du panier
    public double calculerPrixTotal() {
        double prixTotal = 0;
        for (int type : produits.keySet()) {
            for (Produit produit : produits.get(type)) {
                switch (type) {
                    case TYPE_UNIT:
                        prixTotal += produit.calculerPrix(1);
                        break;
                    case TYPE_WEIGHT:
                        prixTotal += produit.calculerPrix(2.5);
                        break;
                    case TYPE_ELECTRONIC:
                        prixTotal += 500; // prix fictif pour les appareils électroniques
                        break;
                }
            }
        }
        return prixTotal;
    }
}

public static void afficherProduits(Produit[] produits) {
    System.out.println("Produits à la vente :");
    for (int i = 0; i < produits.length; i++) {
        System.out.println(produits[i]);
    }
}

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    Magasin magasin = new Magasin();
    // Création de quelques produits de test
    Produit produit1 = new Produit("test", "100% coton, toutes tailles", 15);
    Produit produit2 = new Produit("Tomates", "BIO, origine France", 3.5, LocalDate.now());
    Produit produit3 = new Produit("Smartphone", "Apple iPhone 13, 128 Go, couleur bleu", LocalDate.now());

    magasin.ajouterProduit(produit1);
    magasin.ajouterProduit(produit2);
    magasin.ajouterProduit(produit3);
    magasin.afficherTousLesProduits();

    // Remplissage du panier
    Panier panier = new Panier();
    panier.ajouterProduit(produit1);
    panier.ajouterProduit(produit2);
    panier.ajouterProduit(produit3);

    // Affichage du contenu du panier
    panier.afficherContenu();

    // Calcul du prix total du panier
    double prixTotal = panier.calculerPrixTotal();
    System.out.println("Prix total du panier : " + prixTotal + " euros");

    scanner.close();
}
}
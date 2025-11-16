package ism.mae.views;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ism.mae.entity.Commande;
import ism.mae.entity.LigneCommande;
import ism.mae.entity.Produit;
import ism.mae.service.CommandeService;
import ism.mae.service.ProduitService;

public class BoutiquierView {
    
 @SuppressWarnings("FieldMayBeFinal")   
    private ProduitService produitService;
    private CommandeService commandeService;
    private  Scanner scanner=new Scanner(System.in);

    public BoutiquierView(ProduitService produitService, CommandeService commandeService) {
        this.produitService = produitService;
        this.commandeService = commandeService;
    }
    
    public Commande saisieCommande() {
        Scanner scanner = new Scanner(System.in);
        List<LigneCommande> lignes = new ArrayList<>();
        double montantTotal = 0;
        LocalDate dateCommande;
        while (true) {
            System.out.print("Entrer la date (AAAA-MM-JJ) : ");
            String input = scanner.nextLine();
            try {
                dateCommande = LocalDate.parse(input);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Format invalide ! Veuillez entrer AAAA-MM-JJ");
            }
        }
        String date = dateCommande.toString();

        while (true) {
            System.out.println("Choix du produit à ajouter :");
            List<Produit> produits = produitService.getAllProduits();
            produits.forEach(p ->System.out.println(p.getId() + " - " + p.getName() + " (PU=" + p.getPu() + ")"));
            System.out.print("ID du produit : ");
            int idProd = Integer.parseInt(scanner.nextLine());
            Produit produit = produitService.getProduitById(idProd);
            System.out.print("Quantité : ");
            double qte = Double.parseDouble(scanner.nextLine());
            double pu = produit.getPu();
            double montant = qte * pu;
            montantTotal += montant;
            LigneCommande lc = LigneCommande.builder()
                    .produit(produit)
                    .qte(qte)
                    .pu(pu)
                    .montant(montant)
                    .build();
            lignes.add(lc);
            System.out.println("Produit ajouté ! (Montant = " + montant + ")");
            System.out.print("Ajouter un autre produit ? (o/n) : ");
            String rep = scanner.nextLine();
            if (!rep.equalsIgnoreCase("o")) break;
        }
        return Commande.builder()
                .dateCommande(date)
                .montant(montantTotal)
                .lignes(lignes)
                .build();
    }

    public  void afficheCommandes(List<Commande>commandes){
       System.out.println("Liste des Commandes");
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande trouvée !");
            return;
        }
        for (Commande cmd : commandes) {
            System.out.println("ID Commande: " + cmd.getId() +
                            " | Date: " + cmd.getDateCommande() +
                            " | Montant: " + cmd.getMontant() + " F CFA");
        }
   }

   public void afficheDetailCommande(int id) {
       Commande commande = commandeService.getCommandeById(id);
        if (commande == null) {
            System.out.println("Commande introuvable !");
            return;
        }
        System.out.println("Détails de la Commande ID: " + id);
        System.out.println("Date : " + commande.getDateCommande());
        System.out.println("Montant total : " + commande.getMontant() + " F CFA");
        System.out.println("Détails des produits :");
        for (LigneCommande ligne : commande.getLignes()) {
            System.out.println("---------------------------");
            System.out.println("Produit : " + ligne.getProduit().getName());
            System.out.println("Quantité : " + ligne.getQte());
            System.out.println("Prix unitaire : " + ligne.getPu() + " F CFA");
            System.out.println("Montant : " + ligne.getMontant() + " F CFA");
        }
    }
   
    public  int  menu(){
        System.out.println("=====Menu boutiquier=====");
        System.out.println("1-Enregistrer une Commande");
        System.out.println("2-Lister toutes les Commandes");
        System.out.println("3-Afficher les détails d'une Commande");
        System.out.println("4-Deconnexion");
        System.out.println("Faites votre choix");
        return scanner.nextInt();
    }

   public  void main(){
        int choix;
        do {
           choix= menu();
           scanner.nextLine();
            switch (choix) {
                case 1 -> {        
                    System.out.println("1-Enregistrer une Commande");
                    Commande commande=saisieCommande();
                    if (commande!=null) {
                        commandeService.addCommande(commande);
                    }
                    }
                case 2 -> {
                System.out.println("2-Lister toutes les Commandes");
                List<Commande> commandes=commandeService.getAllCommandes();
                afficheCommandes(commandes);
                }
                case 3 -> {
                    System.out.println("3-Afficher les détails d'une Commande");
                    List<Commande> commandes=commandeService.getAllCommandes();
                    afficheCommandes(commandes);
                    if (commandes.isEmpty()) {
                        break;
                    }
                    System.out.print("\nEntrez l'ID de la commande à afficher : ");
                    int id = Integer.parseInt(scanner.nextLine());
                    afficheDetailCommande(id);
                }
                case 4 -> {
                    System.out.println("A bientot !");
                    break;
                }
                default -> {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                }
            }
        } while (choix!=4);
    }
}
package ism.mae.views;

import java.util.List;
import java.util.Scanner;

import ism.mae.entity.Categorie;
import ism.mae.entity.Produit;
import ism.mae.service.CategorieService;
import ism.mae.service.ProduitService;

public  class RsView {
  @SuppressWarnings("FieldMayBeFinal")   
  private CategorieService categorieService;
  private ProduitService produitService;
  private  Scanner scanner=new Scanner(System.in);

  public RsView(CategorieService categorieService, ProduitService produitService) {
        this.categorieService = categorieService;
        this.produitService = produitService;
    }
    
  public Categorie saisieCategorie(){
    String libelle;
    do {
      System.out.println("Entrer le nom de la categorie");
      libelle=scanner.nextLine();
      } while (libelle.equals(""));
      return Categorie.builder()
        .name(libelle)
        .build();
  }

  public Produit saisieProduit(){
    String name;
    int qteStock,pu;
    int categorieId;
    do {
      System.out.println("Entrer le nom du Produit");
      name=scanner.nextLine();
    } while (name.equals(""));
    do {
      System.out.println("Entrer la Quantite en Stock");
      qteStock=scanner.nextInt();
    } while (qteStock<=0);
    do {
      System.out.println("Entrer le Prix Unitaire");
      pu=scanner.nextInt();
    } while (pu<=0);
    do {
      System.out.println("Liste des Categories");
      List<Categorie>categories=categorieService.getAllCategories();
      for (Categorie categorie : categories) {
      System.out.println(categorie);
      }
      System.out.println("Entrer l'ID de la Categorie");
      categorieId=scanner.nextInt();
    } while (categorieId<=0);
    return Produit.builder()
      .name(name)
      .qteStock(qteStock)
      .pu(pu)
      .categorieId(categorieId)
      .build();
  }

  public  void afficheCategories(List<Categorie>categories){
    System.out.println("Liste des Categories");
    for (Categorie categorie : categories) {
      System.out.println("ID: " + categorie.getId() +" | Nom: " + categorie.getName());
    }
  }
  public void afficheProduits(List<Produit>produits){
    System.out.println("Liste des Produits");
       for (Produit p : produits) {
        System.out.println("Produit : " + p.getName() +" | Catégorie : " + p.getCategorie().getName() +" (ID Catégorie : " + p.getCategorie().getId() + ")");
    }
  }

  public  int  menu(){
    System.out.println("\n=====Menu responsable stock=====");
    System.out.println("1-Ajouter une Categorie");
    System.out.println("2-Lister toutes les Categories");
    System.out.println("3-Ajouter un Produit");
    System.out.println("4-Lister les Produits");
    System.out.println("5- Lister les Produits par Categorie");
    System.out.println("6-Deconnexion");
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
          System.out.println("1-Ajouter une Categorie");
          Categorie categorie=saisieCategorie();
          if (categorieService.getCategorieByName(categorie.getName()).isPresent()) {
            System.out.println("Cette Categorie existe Deja");
          }else{
            categorieService.addCategorie(categorie);
            System.out.println("Catégorie bien ajoutée");
          }
        }
        case 2 -> {
          System.out.println("2-Lister toutes les Categories");
          List<Categorie> categories=categorieService.getAllCategories();
          afficheCategories(categories);
        }
        case 3 -> {
          System.out.println("3-Ajouter des Produits");
          Produit produit= saisieProduit(); 
          if (produitService.getProduitById(produit.getId())!=null) {
            System.out.println("Ce Produit existe Deja");
          }else{ 
            produitService.addProduit(produit);
            System.out.println("Produit bien ajouté");
          }
        }
        case 4 -> {
          System.out.println("4-Lister les Produits");
          List<Produit> produits=produitService.getAllProduits();
          afficheProduits(produits);
        }
        case 5 -> {
          System.out.println("5- Lister les Produits par Categorie");
          List<Categorie>categories=categorieService.getAllCategories();
          for (Categorie categorie : categories) {
            System.out.println(categorie);
          }
          System.out.println("Entrer l'ID de la Categorie");
          int id=scanner.nextInt();
          List<Produit> produitsByCat=produitService.getProduitsByCategory(id);
          afficheProduits(produitsByCat);
        }  
        case 6 -> {System.out.println("A bientot !");
                  break;}
        default -> {System.out.println("Choix invalide. Veuillez réessayer.");}
      }
    } while (choix!=6);
  }
}

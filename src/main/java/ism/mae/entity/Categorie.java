package ism.mae.entity;

import java.util.ArrayList;

import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Builder
@ToString(exclude = {"produits"})
public class Categorie {
       private int id;
       private String name;
       @Setter(AccessLevel.NONE)
       private ArrayList<Produit> produits=new ArrayList<>();
       public void addProduit(Produit produit) {
          this.produits.add(produit);
       }
}

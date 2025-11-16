package ism.mae.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Builder
@ToString
public class Produit {
      private int id;
      private String name;
      private int qteStock;
      private int pu;
      private  int categorieId;
      private Categorie categorie;

}

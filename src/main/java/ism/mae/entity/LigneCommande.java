package ism.mae.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Builder
@ToString(exclude = {"qte","pu"})
public class LigneCommande {
    private int id;
    private Produit produit;
    private double qte;
    private double pu;
    private double montant;
}


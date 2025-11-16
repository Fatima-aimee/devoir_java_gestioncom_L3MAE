package ism.mae.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Commande {
    private int id;
    private String dateCommande;
    private double montant;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private List<LigneCommande> lignes = new ArrayList<>();

    public void addLigne(LigneCommande lc) {
        lignes.add(lc);
    }
}


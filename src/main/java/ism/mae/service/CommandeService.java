package ism.mae.service;

import java.util.List;

import ism.mae.entity.Commande;

public interface CommandeService {
    boolean addCommande(Commande commande);
    Commande getCommandeById(int id);
    List<Commande>getAllCommandes();
}

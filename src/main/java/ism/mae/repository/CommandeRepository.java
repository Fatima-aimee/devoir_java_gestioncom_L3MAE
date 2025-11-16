package ism.mae.repository;

import java.util.List;

import ism.mae.entity.Commande;

public interface CommandeRepository {
    public boolean insertCommande(Commande commande);
     Commande findCommandeById(int id);
     List<Commande> findAllCommandes();
}

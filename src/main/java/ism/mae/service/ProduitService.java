package ism.mae.service;

import java.util.List;

import ism.mae.entity.Produit;

public interface ProduitService {
    boolean addProduit(Produit produit);
    List<Produit> getProduitsByCategory(int id);
    List<Produit> getAllProduits();
    Produit getProduitById(int id);
}

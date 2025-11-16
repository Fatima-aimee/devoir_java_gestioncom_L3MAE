package ism.mae.repository;

import java.util.List;

import ism.mae.entity.Produit;

public interface  ProduitRepository {
    public boolean insertProduit(Produit produit);
    public List<Produit> findProduitsByCategorie(int id);
    public List<Produit> findAllProduits();
    public Produit findProduitById(int id);
    
}

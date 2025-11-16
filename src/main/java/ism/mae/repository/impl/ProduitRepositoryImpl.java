package ism.mae.repository.impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ism.mae.entity.Categorie;
import ism.mae.entity.Produit;
import ism.mae.repository.ProduitRepository;
public class ProduitRepositoryImpl implements ProduitRepository {

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public boolean insertProduit(Produit produit) {
        int result=0;
           try {
                Class.forName("com.mysql.cj.jdbc.Driver");
               try {
                Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale","root","root");
                PreparedStatement ps= conn.prepareStatement("INSERT INTO `produit` (`id`, `name`, `categorie_id`,`qteStock`,`pu`) VALUES (NULL, ?, ?, ?, ?)");
                ps.setString(1, produit.getName());
                ps.setInt(2, produit.getCategorieId());
                ps.setDouble(3, produit.getQteStock());
                ps.setDouble(4, produit.getPu());
                result= ps.executeUpdate();
            } catch (SQLException e) {
              e.printStackTrace();
            }

           } catch (ClassNotFoundException e) {
           e.printStackTrace();
           } 
             return result!=0;
    }
    @Override
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public List<Produit> findProduitsByCategorie(int id) {
            List<Produit> produits=new ArrayList<>();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale","root","root");
                PreparedStatement ps= conn.prepareStatement("""
                    SELECT p.id, p.name, p.qteStock, p.pu, p.categorie_id,c.id AS cat_id, c.name AS cat_name FROM produit p JOIN categorie c ON p.categorie_id = c.id
                    WHERE p.categorie_id = ?""" );   
                    ps.setInt(1, id);                   
                    ResultSet rs= ps.executeQuery();
                       while (rs.next()) {
                        Categorie categorie = Categorie
                            .builder()
                            .id(rs.getInt("cat_id"))
                            .name(rs.getString("cat_name"))
                            .build();
                        Produit produit = Produit
                            .builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .qteStock(rs.getInt("qteStock"))
                            .pu(rs.getInt("pu"))
                            .categorie(categorie) 
                            .build();

                        produits.add(produit);
                    }

            } catch (Exception e) {
                e.printStackTrace(); 
            }

            return produits;
    } 
    @Override
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public Produit findProduitById(int id) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale","root","root");
                PreparedStatement ps= conn.prepareStatement("""
                    SELECT p.id, p.name, p.qteStock, p.pu, p.categorie_id,c.id AS cat_id, c.name AS cat_name FROM produit p JOIN categorie c ON p.categorie_id = c.id
                    WHERE p.id= ? """ );
                    ps.setInt(1, id);
                    ResultSet rs= ps.executeQuery();
                    Produit produit=null;
                    if (rs.next()) {
                        Categorie categorie = Categorie
                            .builder()
                            .id(rs.getInt("cat_id"))
                            .name(rs.getString("cat_name"))
                            .build();
                        produit = Produit
                            .builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .qteStock(rs.getInt("qteStock"))
                            .pu(rs.getInt("pu"))
                            .categorie(categorie) 
                            .build();
                    }
                    return produit;
                      
                } catch (Exception e) {
                    e.printStackTrace(); 
                }
             return  null; 
    } 

    @Override
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public List<Produit> findAllProduits() {
        List<Produit> produits=new ArrayList<>();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try {
                    String sql = """
                        SELECT p.id, p.name, p.qteStock, p.pu, p.categorie_id,c.id AS cat_id, c.name AS cat_name FROM produit p JOIN categorie c ON p.categorie_id = c.id
                    """;

                    Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale","root","root");
                    PreparedStatement ps= conn.prepareStatement(sql);
                    ResultSet rs= ps.executeQuery();
                    while (rs.next()) {
                        Categorie categorie = Categorie
                            .builder()
                            .id(rs.getInt("cat_id"))
                            .name(rs.getString("cat_name"))
                            .build();

                        Produit produit = Produit
                            .builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .qteStock(rs.getInt("qteStock"))
                            .pu(rs.getInt("pu"))
                            .categorie(categorie) 
                            .build();

                        produits.add(produit);
                     }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } 
                       return produits;
    }
    
}
package ism.mae.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ism.mae.entity.Commande;
import ism.mae.entity.LigneCommande;
import ism.mae.entity.Produit;
import ism.mae.repository.CommandeRepository;


public class CommandeRepositoryImpl implements CommandeRepository {

    @Override
    public boolean insertCommande(Commande commande) {
    int result = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:8889/gestion_commerciale", 
                "root", 
                "root"
            );
            PreparedStatement psCmd = conn.prepareStatement(
            "INSERT INTO commande (date_cmd, montant) VALUES (?, ?)",
            Statement.RETURN_GENERATED_KEYS);
            String sqlUpdateStock = "UPDATE produit SET qteStock = ? WHERE id = ?";
            psCmd.setDate(1, Date.valueOf(commande.getDateCommande()));
            psCmd.setDouble(2, commande.getMontant());
            psCmd.executeUpdate();
            ResultSet rs = psCmd.getGeneratedKeys();
            int commandeId = 0;
            if (rs.next()) commandeId = rs.getInt(1);
            PreparedStatement psLigne = conn.prepareStatement(
                "INSERT INTO ligne_commande (commande_id, produit_id, qte, pu, montant_ligne) "
                + "VALUES (?, ?, ?, ?, ?)");

            for (LigneCommande lc : commande.getLignes()) {
                psLigne.setInt(1, commandeId);
                psLigne.setInt(2, lc.getProduit().getId());
                psLigne.setInt(3, (int) lc.getQte());
                psLigne.setInt(4, (int) lc.getPu());
                psLigne.setInt(5, (int) lc.getMontant());
                psLigne.executeUpdate();
            
            Produit p = lc.getProduit();
            int newStock = p.getQteStock() - (int) lc.getQte();
            if (newStock < 0) {
                conn.rollback();
                throw new RuntimeException("Stock insuffisant pour le produit : " + p.getName());
            }
            PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock);
            psStock.setInt(1, newStock);
            psStock.setInt(2, p.getId());
            psStock.executeUpdate();
            p.setQteStock(newStock);
        }
        } catch (SQLException e) {
            System.out.println("Erreur insertCommande SQL: " + e.getMessage());
        }
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur Driver MySQL: " + e.getMessage());
        }
        return result != 0;
    }

    @Override
    public List<Commande> findAllCommandes() {
    List<Commande> commandes = new ArrayList<>();
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale", "root", "root");
        PreparedStatement psCmd = conn.prepareStatement(
            "SELECT * FROM commande"
        );
        ResultSet rsCmd = psCmd.executeQuery();
        while (rsCmd.next()) {
            Commande commande = null;
             int id = rsCmd.getInt("id");

            Commande cmd=Commande.builder()
                    .id(rsCmd.getInt("id"))
                    .dateCommande(rsCmd.getDate("date_cmd").toString())
                    .montant(rsCmd.getDouble("montant"))
                    .build();
            commande = cmd;

            PreparedStatement psLigne = conn.prepareStatement(
                "SELECT lc.*, p.name, p.pu AS pu_produit "
                + "FROM ligne_commande lc "
                + "JOIN produit p ON p.id = lc.produit_id "
                + "WHERE lc.commande_id = ?"
            );
            psLigne.setInt(1, id);

            ResultSet rsL = psLigne.executeQuery();

            while (rsL.next()) {
                Produit p = Produit.builder()
                        .id(rsL.getInt("produit_id"))
                        .name(rsL.getString("name"))
                        .pu(rsL.getInt("pu_produit"))
                        .build();
                LigneCommande lc = LigneCommande.builder()
                        .id(rsL.getInt("id"))
                        .produit(p)
                        .qte(rsL.getInt("qte"))
                        .pu(rsL.getInt("pu"))
                        .montant(rsL.getInt("montant_ligne"))
                        .build();

                commande.addLigne(lc);
            }
                commandes.add(commande);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL findAllCommandes: " + e.getMessage());
        }

    catch (ClassNotFoundException e) {
        System.out.println("Erreur Driver MySQL: " + e.getMessage());
    }
    return commandes;
    }

    @Override
    public Commande findCommandeById(int id) {
    Commande commande = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:8889/gestion_commerciale", "root", "root");
        PreparedStatement psCmd = conn.prepareStatement(
            "SELECT * FROM commande WHERE id = ?"
        );
        psCmd.setInt(1, id);
        ResultSet rsCmd = psCmd.executeQuery();

        if (rsCmd.next()) {
            Commande cmd=Commande.builder()
                    .id(rsCmd.getInt("id"))
                    .dateCommande(rsCmd.getDate("date_cmd").toString())
                    .montant(rsCmd.getDouble("montant"))
                    .build();
            commande = cmd;
            PreparedStatement psLigne = conn.prepareStatement(
                "SELECT lc.*, p.name, p.pu AS pu_produit "
                + "FROM ligne_commande lc "
                + "JOIN produit p ON p.id = lc.produit_id "
                + "WHERE lc.commande_id = ?"
            );
            psLigne.setInt(1, id);

            ResultSet rsL = psLigne.executeQuery();

            while (rsL.next()) {
                Produit p = Produit.builder()
                        .id(rsL.getInt("produit_id"))
                        .name(rsL.getString("name"))
                        .pu(rsL.getInt("pu_produit"))
                        .build();
                LigneCommande lc = LigneCommande.builder()
                        .id(rsL.getInt("id"))
                        .produit(p)
                        .qte(rsL.getDouble("qte"))
                        .pu(rsL.getDouble("pu"))
                        .montant(rsL.getDouble("montant_ligne"))
                        .build();
                commande.addLigne(lc);
            }
        }
    } catch (Exception e) {
        System.out.println("Erreur SQL findCommandeById: " + e.getMessage());
    }
    return commande;
    }
}



package ism.mae.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ism.mae.entity.Categorie;
import ism.mae.repository.CategorieRepository;

public class CategorieRepositoryImpl implements CategorieRepository {

  @Override
  @SuppressWarnings("CallToPrintStackTrace")
  public boolean insertCategorie(Categorie categorie) {
    int result=0;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      try {
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale","root","root");
        PreparedStatement ps= conn.prepareStatement("INSERT INTO `categorie` (`id`, `name`) VALUES (NULL, ?)");
        ps.setString(1, categorie.getName());
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
  @SuppressWarnings("UseSpecificCatch")
  public Optional<Categorie> findCategorieByName(String name) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver"); 
      Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale","root","root");
      PreparedStatement ps= conn.prepareStatement("SELECT * FROM `categorie` WHERE `name` like ?");
      ps.setString(1, name);
      ResultSet rs= ps.executeQuery();//Jeu de Resultat avec des types de BD
      Categorie categorie=null;
      if (rs.next()) {
        categorie =Categorie.builder()
        .id(rs.getInt("id"))
        .name(rs.getString("name"))
        .build();
      }
      return categorie==null ?Optional.empty():Optional.of(categorie) ;
      } catch (Exception e) {
                  System.out.println("Erreur de chargement du Driver");
      }
      return  Optional.empty(); 
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public List<Categorie> findAllCategories() {
    List<Categorie> categories=new ArrayList<>();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      try {
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale","root","root");
        PreparedStatement ps= conn.prepareStatement("SELECT * FROM `categorie`");
        ResultSet rs= ps.executeQuery();
        while (rs.next()) {
          categories.add(
          Categorie.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .build()
          );
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } 
      return categories;
    }
    
}

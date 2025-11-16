package ism.mae.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import ism.mae.entity.RoleEnum;
import ism.mae.entity.User;
import ism.mae.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_commerciale","root","root");
        PreparedStatement ps= conn.prepareStatement("SELECT id,name,role FROM `users` WHERE `login` like ? and password like ?");
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet rs= ps.executeQuery();
        User user=null;
        if (rs.next()) {
          RoleEnum role= rs.getString("role").equals("RS")?RoleEnum.RESPONSABLE_STOCK:RoleEnum.BOUTIQUIER;
          user =User.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .role(role)
            .build();
        }
        return user==null ?Optional.empty():Optional.of(user) ;
        } catch (Exception e) {
                  System.out.println("Erreur de chargement du Driver");}
        return  Optional.empty(); 
    }
    
}

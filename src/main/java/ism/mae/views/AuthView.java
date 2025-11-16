package ism.mae.views;

import java.util.Optional;
import java.util.Scanner;

import ism.mae.entity.User;
import ism.mae.service.UserService;

public class AuthView {
    @SuppressWarnings("FieldMayBeFinal")
     private  Scanner scanner=new Scanner(System.in);
    @SuppressWarnings("FieldMayBeFinal")
     private UserService userService;
     public AuthView(UserService userService) {
        this.userService = userService;
     }
     public User connexion(){
        String login,pwd;
          Optional<User>  user;
        do {
            do {
                System.out.println("Entrer le login");
                login=scanner.nextLine();
            } while ( login.equals(""));
            do {
                System.out.println("Entrer le Mot de Passe");
                pwd=scanner.nextLine();
            } while ( pwd.equals(""));

           user =userService.connection(login, pwd);
            if (user.isEmpty()) {
               System.out.println("Login ou Mot de passe Incorrect");
             }
       } while (user.isEmpty());

       return user.get();
     }
}

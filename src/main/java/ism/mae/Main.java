package ism.mae;


import ism.mae.entity.RoleEnum;
import ism.mae.entity.User;
import ism.mae.repository.UserRepository;
import ism.mae.repository.impl.CategorieRepositoryImpl;
import ism.mae.repository.impl.CommandeRepositoryImpl;
import ism.mae.repository.impl.ProduitRepositoryImpl;
import ism.mae.repository.impl.UserRepositoryImpl;
import ism.mae.service.impl.CategorieServiceImpl;
import ism.mae.service.impl.CommandeServiceImpl;
import ism.mae.service.impl.ProduitServiceImpl;
import ism.mae.service.impl.UserServiceImpl;
import ism.mae.views.AuthView;
import ism.mae.views.BoutiquierView;
import ism.mae.views.RsView;

public class Main {
    public static void main(String[] args) {
          UserRepository userRepository=new UserRepositoryImpl();
          UserServiceImpl userServiceImpl=new UserServiceImpl(userRepository);
         //Connexion
            AuthView authView= new AuthView(userServiceImpl);
            User user= authView.connexion();
            System.out.println("Bienvenue "+user.getName()+"!");
            RoleEnum roleUserConnect=user.getRole();
            CategorieRepositoryImpl categorieRepositoryImpl=new CategorieRepositoryImpl();
            CategorieServiceImpl categorieServiceImpl=new CategorieServiceImpl(categorieRepositoryImpl);
            ProduitRepositoryImpl produitRepositoryImpl=new ProduitRepositoryImpl();
            ProduitServiceImpl produitServiceImpl=new ProduitServiceImpl(produitRepositoryImpl); 
            CommandeRepositoryImpl commandeRepositoryImpl=new CommandeRepositoryImpl();
            CommandeServiceImpl commandeServiceImpl=new CommandeServiceImpl(commandeRepositoryImpl);
            
            switch (roleUserConnect) {
                case RESPONSABLE_STOCK -> {
                    RsView view=new RsView(categorieServiceImpl, produitServiceImpl);
                    view.main();
            }
                case BOUTIQUIER -> {
                    BoutiquierView view=new BoutiquierView(produitServiceImpl, commandeServiceImpl);
                    view.main();
                }
                default -> {
            }
            }
    }
}
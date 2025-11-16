package ism.mae.service.impl;

import java.util.List;

import ism.mae.entity.Commande;
import ism.mae.repository.CommandeRepository;
import ism.mae.service.CommandeService ;  

public class CommandeServiceImpl implements CommandeService {
    @SuppressWarnings("FieldMayBeFinal")
    private CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }
    @Override
    public boolean addCommande(Commande commande) {
        return commandeRepository.insertCommande(commande);
    }

    @Override
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAllCommandes();
    }

    @Override
    public Commande getCommandeById(int id){
        return commandeRepository.findCommandeById(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Fournisseur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lidrissi Hamid
 */
@Stateless
public class FournisseurFacade extends AbstractFacade<Fournisseur> {

    @PersistenceContext(unitName = "stock_hamid_younessPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FournisseurFacade() {
        super(Fournisseur.class);
    }
    
    
      //------------------- code Hamid -----------------
    
    public void createFournisseur(Fournisseur fournisseur ) {
        this.create(fournisseur);
    }
    
    public void editFournisseur(Fournisseur fournisseur) {
        this.edit(fournisseur);
    }
    
    
    public void removeFournisseur(Fournisseur fournisseur) {
         this.remove(fournisseur);
    }

    
    
}

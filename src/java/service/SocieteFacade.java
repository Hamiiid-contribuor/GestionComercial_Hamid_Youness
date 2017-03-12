/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Fournisseur;
import bean.Societe;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lidrissi Hamid
 */
@Stateless
public class SocieteFacade extends AbstractFacade<Societe> {

    @PersistenceContext(unitName = "stock_hamid_younessPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SocieteFacade() {
        super(Societe.class);
    }

    //----------------- code Hamid -----------------
    public List<Societe> findAllSocietes() {
        return this.findAll();
    }

    public void createSociete(Societe societe) {
        this.create(societe);
    }

    public void editSociete(Societe societe) {
        this.edit(societe);
    }

    public void removeSociete(Societe societe) {
        this.remove(societe);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.SecteurActivite;
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
public class SecteurActiviteFacade extends AbstractFacade<SecteurActivite> {

    @PersistenceContext(unitName = "stock_hamid_younessPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecteurActiviteFacade() {
        super(SecteurActivite.class);
    }

    //----------------- code Hamid -----------------
    public List<SecteurActivite> findAllSecteurActivites() {
        return this.findAll();
    }

}

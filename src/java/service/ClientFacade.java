/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Client;
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
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "stock_hamid_younessPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }

    //------------------- code Hamid -----------------
    public void createClient(Client client) {
        this.create(client);
    }

    public void editClient(Client client) {
        this.edit(client);
    }

    public void removeClient(Client client) {
        this.remove(client);
    }

    public List<Client> rechercheClientParSociete(Long idScociete) {

        String requette = "SELECT c FROM Client c WHERE c.societe.id = " + idScociete;
        System.out.println("ha requette --> " + requette);
        List<Client> clients = em.createQuery(requette).getResultList();
        System.out.println("voila la liste des client par societe --->" + clients);
        return clients;
    }

    // test  for webservices 
    public List<Client> listOfClients() {
        return findAll();
    }
}

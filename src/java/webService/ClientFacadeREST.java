/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webService;

import bean.Client;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lidrissi Hamid
 */
@Stateless
@Path("client")
public class ClientFacadeREST extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "stock_hamid_younessPU")
    private EntityManager em;

    public ClientFacadeREST() {
        super(Client.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("findById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client findClientById(@PathParam("id") Long id) {
        return find(id);
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> findAllClient() {
        return findAll();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countClients() {
        return "le nombre de clients --->" + String.valueOf(count());
    }

}

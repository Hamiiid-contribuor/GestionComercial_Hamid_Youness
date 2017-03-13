/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webService;

import bean.Fournisseur;
import bean.SecteurActivite;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lidrissi Hamid
 */
@Stateless
@Path("fournisseur")
public class FournisseurFacadeREST extends AbstractFacade<Fournisseur> {

    @PersistenceContext(unitName = "stock_hamid_younessPU")
    private EntityManager em;

    public FournisseurFacadeREST() {
        super(Fournisseur.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("findById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Fournisseur findFournisseurById(@PathParam("id") Long id) {
        return find(id);
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fournisseur> findAllFournisseur() {
        return findAll();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countFournisseurs() {
        return "le nombre de clients --->" + String.valueOf(count());
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String ajouterFournisseur(Fournisseur fournisseur) {
        super.create(fournisseur);
        return "Fournisseur bien Ajouter ";
    }

}

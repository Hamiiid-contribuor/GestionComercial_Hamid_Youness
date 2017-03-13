/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webService;

import bean.Societe;
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
@Path("societe")
public class SocieteFacadeREST extends AbstractFacade<Societe> {

    @PersistenceContext(unitName = "stock_hamid_younessPU")
    private EntityManager em;

    public SocieteFacadeREST() {
        super(Societe.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //ici on fait appel  a notre methode a travers  la methode http GET ( c'est clair )
    //bon , cette va etre  appllé a travers le path  mentionné (" pathDuProjet/webService/societe/list ")
    //produces mentionne  la form du resultat qu'on veut retourne, ca peut etre du text, xml , json .. ; dans notre cas c'est  json 
    //apres c'est  une methode simple, noublié pas qu'on est dans un ServiceEJB statless donc les CRUD JPA sont accessible en tout public 
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Societe> findAllSociete() {
        return findAll();
    }

    // accessible a travers  l'url  (" pathDuProjet/webService/societe/findById/{votre id } ")
    @GET
    @Path("findById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Societe findSocieteById(@PathParam("id") Long id) {
        return find(id);
    }

    //ci vous voulez  retourne xml  vous changer "MediaType.APPLICATION_JSON" par MediaType.APPLICATION_XML
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countSociete() {
        return "le nombre se societes est ---> " + String.valueOf(count());
    }

}

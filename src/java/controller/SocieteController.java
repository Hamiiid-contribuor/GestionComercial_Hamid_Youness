package controller;

import bean.Client;
import bean.Fournisseur;
import bean.SecteurActivite;
import bean.Societe;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.Message;
import controller.util.MessageManager;
import service.SocieteFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("societeController")
@SessionScoped
public class SocieteController implements Serializable {

    @EJB
    private service.SocieteFacade ejbFacade;
    @EJB
    private service.SecteurActiviteFacade secteurActiviteFacade;

    @EJB
    private service.ClientFacade clientFacade;

    private List<Societe> items = null;
    private Societe selected;

    private Message message;

    private List<SecteurActivite> secteurActivites;
    private int booleanSwitch = 0;

    private List<Client> clients;

    public SocieteController() {
    }

    public Societe getSelected() {
        if (selected == null) {
            selected = new Societe();
        }
        return selected;
    }

    public void setSelected(Societe selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SocieteFacade getFacade() {
        return ejbFacade;
    }

    public Societe prepareCreate() {
        selected = new Societe();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SocieteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SocieteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SocieteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Societe> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Societe getSociete(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Societe> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Societe> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Societe.class)
    public static class SocieteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SocieteController controller = (SocieteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "societeController");
            return controller.getSociete(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Societe) {
                Societe o = (Societe) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Societe.class.getName()});
                return null;
            }
        }

    }

    //---------------------- code Hamid ----------------
    public List<SecteurActivite> getSecteurActivites() {
        if (secteurActivites == null) {
            this.secteurActivites = secteurActiviteFacade.findAllSecteurActivites();

        }
        return secteurActivites;
    }

    public void setSecteurActivites(List<SecteurActivite> secteurActivites) {
        this.secteurActivites = secteurActivites;
    }

    public int getBooleanSwitch() {
        return booleanSwitch;
    }

    public void setBooleanSwitch(int booleanSwitch) {
        this.booleanSwitch = booleanSwitch;
    }

    public List<Client> getClients() {
        if (clients == null) {
            clients = new ArrayList<>();
        }
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void createSociete() {

        validateSocieteParams();
        //lorsque tous les params sont injectés 
        if (message.getResultat() > 0) {

            ejbFacade.createSociete(selected);
            items.add(selected);
            //c'est pour vider les input d'ajout 
            selected = new Societe();
        }
        MessageManager.showMessage(message);

    }

    public void editSociete(Societe societe) {
        this.selected = societe;
    }

    public void cleanView() {
        this.selected = new Societe();
    }

    public void destroySociete(Societe societe) {

        ejbFacade.removeSociete(societe);
        items.remove(societe);
        JsfUtil.addSuccessMessage("Societé bien supprimer");
        

    }

    private void validateSocieteParams() {

        System.out.println("voila la societe  a persité --->" + selected);
        if (getSelected().getNom().equals("")) {
            message = MessageManager.createErrorMessage(-1, "Merci de spécifier le nom du societé");
        } else if (getSelected().getProprietaire().equals("")) {
            message = MessageManager.createErrorMessage(-2, "Merci de spécifier le nom/prenom du proprietaire du societé");
        } else if (getSelected().getAdresse().equals("")) {
            message = MessageManager.createErrorMessage(-3, "Merci de spécifier l'adresse du societé");
        } else if (getSelected().getEmail().equals("")) {
            message = MessageManager.createErrorMessage(-4, "Merci de spécifier l'email du societé");
        } else if (getSelected().getTelephone().equals("")) {
            message = MessageManager.createErrorMessage(-5, "Merci de spécifier le telephone du societé");
        } else {
            message = MessageManager.createInfoMessage(1, "Societé crée avec Succces ");
        }
    }

    public void rechercheClientParSociete(Long idSociete) {
        System.out.println("ha l'id dial societe -->" + idSociete);
        booleanSwitch = 1;
//        selected = societe;
        clients = clientFacade.rechercheClientParSociete(idSociete);
        System.out.println("ha les clients lifelcontroller -->  "+clients);
    }

}

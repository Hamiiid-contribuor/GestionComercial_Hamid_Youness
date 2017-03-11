package controller;

import bean.Client;
import bean.Societe;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.Message;
import controller.util.MessageManager;
import service.ClientFacade;

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

@Named("clientController")
@SessionScoped
public class ClientController implements Serializable {

    @EJB
    private service.ClientFacade ejbFacade;
    @EJB
    private service.SocieteFacade societeFacade;

    private List<Client> items = null;
    private Client selected;

    private List<Societe> societes;

    private Message message;

    public ClientController() {
    }

    public Client getSelected() {
        if (selected == null) {
            selected = new Client();
        }
        return selected;
    }

    public void setSelected(Client selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ClientFacade getFacade() {
        return ejbFacade;
    }

    public Client prepareCreate() {
        selected = new Client();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ClientCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ClientUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ClientDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Client> getItems() {
        if (items == null) {
            items = getFacade().findAll();
            System.out.println("ha lista dial les clients -->" + items);
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

    public Client getClient(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Client> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Client> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Client.class)
    public static class ClientControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClientController controller = (ClientController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clientController");
            return controller.getClient(getKey(value));
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
            if (object instanceof Client) {
                Client o = (Client) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Client.class.getName()});
                return null;
            }
        }

    }

    //---------------------- code Hamid ----------------
    public List<Societe> getSocietes() {
        if (societes == null) {
            societes = societeFacade.findAllSocietes();
        }
        return societes;
    }

    public void setSocietes(List<Societe> societes) {
        this.societes = societes;
    }

    public void createClient() {

        validateClientParams();
        //lorsque tous les params sont injectés 
        if (message.getResultat() > 0) {

            ejbFacade.createClient(selected);
            items.add(selected);
            //c'est pour vider les input d'ajout 
            selected = new Client();
        }
        MessageManager.showMessage(message);

    }

    public void editClient(Client client) {
        this.selected = client;
    }

    public void cleanView() {
        this.selected = new Client();
    }

    public void destroyClient(Client client) {

        ejbFacade.removeClient(client);
        items.remove(client);
        JsfUtil.addSuccessMessage("Client bien supprimer");

    }

    private void validateClientParams() {

        System.out.println("voila le client  a persité --->"+selected);
        if (getSelected().getNom().equals("")) {
            message = MessageManager.createErrorMessage(-1, "Merci de spécifier le nom du client");
        } else if (getSelected().getPrenom().equals("")) {
            message = MessageManager.createErrorMessage(-2, "Merci de spécifier le prenom du client");
        } else if (getSelected().getAdresse().equals("")) {
            message = MessageManager.createErrorMessage(-3, "Merci de spécifier l'adresse du client");
        } else if (getSelected().getEmail().equals("")) {
            message = MessageManager.createErrorMessage(-4, "Merci de spécifier l'email du client");
        } else if (getSelected().getTelephone().equals("")) {
            message = MessageManager.createErrorMessage(-5, "Merci de spécifier le telephone du client");
        } else if (getSelected().getSociete() == null) {
            message = MessageManager.createErrorMessage(-6, "Merci de selectionné la societé auquelle apartient votre client");
        } else {
            message = MessageManager.createInfoMessage(1, "Client crée avec Succces ");
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Lidrissi Hamid
 */
@Entity
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reference;
    private String designation;
    private String description;
    @ManyToOne
    private Categorie categorie;
    @ManyToOne
    private Marque marque;

    private Double prixHT;
    private Float tva;
    private Double prixTTC;

    private Long qteStock;
    private Long seuilAlert;
    @ManyToOne
    private Magazin magazin;

    public Article() {
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categorie getCategorie() {
        if (categorie == null) {
            categorie = new Categorie();
        }
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Marque getMarque() {
        if (marque == null) {
            marque = new Marque();
        }
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(Double prixHT) {
        this.prixHT = prixHT;
    }

    public Float getTva() {
        return tva;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Long getQteStock() {
        return qteStock;
    }

    public void setQteStock(Long qteStock) {
        this.qteStock = qteStock;
    }

    public Long getSeuilAlert() {
        return seuilAlert;
    }

    public void setSeuilAlert(Long seuilAlert) {
        this.seuilAlert = seuilAlert;
    }

    public Magazin getMagazin() {
        if (magazin == null) {
            magazin = new Magazin();
        }
        return magazin;
    }

    public void setMagazin(Magazin magazin) {
        this.magazin = magazin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.reference);
        hash = 29 * hash + Objects.hashCode(this.designation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (!Objects.equals(this.designation, other.designation)) {
            return false;
        }
        if (!Objects.equals(this.reference, other.reference)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Article{" + "reference=" + reference + ", designation=" + designation + ", categorie=" + categorie + ", marque=" + marque + ", prixHT=" + prixHT + ", tva=" + tva + ", prixTTC=" + prixTTC + ", qteStock=" + qteStock + ", seuilAlert=" + seuilAlert + ", magazin=" + magazin + '}';
    }

}

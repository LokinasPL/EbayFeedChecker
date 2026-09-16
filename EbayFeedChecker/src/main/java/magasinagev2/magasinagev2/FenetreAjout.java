/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import java.util.*;
import javax.swing.*;
/**
 *
 * @author loubo
 */
public class FenetreAjout extends Fenetre {
    public FenetreAjout (PagePrincipale interfaceCourant){
        this.pagePrincipale=interfaceCourant;
        this.itemCourant=new Item();
        this.itemCourant.listeArticles=this.pagePrincipale.listeItemObject;
        this.observers = new ArrayList<Observer>();
        this.addObserver(pagePrincipale);
        //initie les composante par défaut de Fenetre
        initComponents();
        //personnalise les composantes pour la fenêtre ajout
        this.setTitreFrame();
        this.setTitreFenetre(jLabelTitre);
        this.setItemNom(jTextFieldNom);
        this.setItemURL(jTextFieldURL);
        this.setItemDesc(jTextAreaDesc);
    }
    
    //personnalise le titre du frame
    @Override
    public void setTitreFrame() {
        setTitle("Ajouter un article à la liste d'articles surveillés");
    }
    
    //personnalise le titre de la fenêtre
    @Override
    public void setTitreFenetre(JLabel titre){
        titre.setText("Ajouter un article à la liste");
    }
    
    //personnalise les champs d'information (ici assure qu'ils sont vides)
    @Override
    public void setItemNom(JTextField nomItem){
        nomItem.setText("");
    }
    @Override
    public void setItemURL(JTextField urlItem){
        urlItem.setText("");
    }
    @Override
    public void setItemDesc(JTextArea descItem){
        descItem.setText("");
    }
    
    @Override
    public void sauvegarderButtonActionPerformed(java.awt.event.ActionEvent evt){
        //entre les nouvelles informations dans l'item en cour de création
        try {
            this.itemCourant.setNomUsage(jTextFieldNom.getText());
            this.itemCourant.setDesc(jTextAreaDesc.getText());
            this.itemCourant.entreURL(jTextFieldURL.getText());
            this.itemCourant.listeArticles=this.pagePrincipale.listeItemObject;
            //ajoute l'item à la liste d'articles d'intérêt
            this.itemCourant.listeArticles.add(itemCourant);
            //notifie la page principale du changement à la liste
            this.notifyObservers(new Object());
            //ferme la fenêtre
            this.dispose();
        }
        catch (invalidURL invurl) {
            String msg = switch (invurl.type) {
                //cas que page illisible
                case "page illisible" -> "La page est illisible.";
                //cas de mauvaise plateforme
                case "mauvaise plateforme" -> "Cette plateforme n'est pas prise en charge.";
                //cas de item existe
                case "item existe" -> "Il existe deja un item dans la liste avec cet url";
                //y'a un autre probleme
                default -> "Cette url est invalide.";
            };
            //popup d'erreur
            JOptionPane.showMessageDialog(this, new String[]{"Cette URL ne peut pas etre utilisee",msg});
        }
    }
    
    //implémentation de l'observer pour avertir la page principale d'un changement dans la liste
    public synchronized void addObserver(Observer o){
        observers.add(o);
    }
    public synchronized void removeObserver(Observer o){
        observers.remove(o);
    }
    protected void notifyObservers(Object event){
        for(Observer observer : observers){
            observer.notify(event);
        }
    }
    
    private ArrayList<Observer> observers;
    private PagePrincipale pagePrincipale;
    private Item itemCourant;
}

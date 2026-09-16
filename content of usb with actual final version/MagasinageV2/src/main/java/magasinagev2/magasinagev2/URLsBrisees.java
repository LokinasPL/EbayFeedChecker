/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author loubo
 */
public class URLsBrisees extends AfficheurDeRubriques {
    public URLsBrisees(Set<Item> itemBrokenURL, ListeArticlesInteret listeItem) {
        this.listeBrokenURL = itemBrokenURL;
        this.listeItemObject = listeItem;
        initComponents();
        this.AffichageMAJ();
    }
    
    //Volee a PagePrincipale
    @Override
    public void notify(Object event){
        //notifie la page quand il y a un changement à la liste
        this.listeItem=this.listeItemObject.getListeItem();
        this.AffichageMAJ();
    }
    
    //Volee a PagePrincipale
    public void AffichageMAJ () {
        //met à jour l'affichage de la liste dans la page
        articles.removeAll();
        jScrollPaneArticles.setViewportView(articles);
        articles.setLayout(new BoxLayout(articles, BoxLayout.Y_AXIS));
        articles.setBackground(new java.awt.Color(240, 236, 235));
        for (Item item : listeBrokenURL){
            JPanel panelRubrique = new javax.swing.JPanel();
            panelRubrique.setBackground(new java.awt.Color(206,235,251));
            Rubrique rubrique = new Rubrique(panelRubrique, item, this);
            articles.add(rubrique.getPanel());
            Component spacer = Box.createRigidArea(new Dimension (500, 10));
            spacer.setBackground(new java.awt.Color(240,236, 235));
            articles.add(spacer);
        }
    }
    
    public void initComponents(){
        jTitre = new javax.swing.JLabel();
        jScrollPaneArticles = new javax.swing.JScrollPane();
        articles = new javax.swing.JPanel();

        jTitre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTitre.setText("Ces items ont des URL maintenant invalides.\nVoulez-vous les modifier ou les supprimer?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPaneArticles)
                    .addComponent(jTitre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTitre, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneArticles, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
    }
    
    // Variables declaration - do not modify
    private Set<Item> listeBrokenURL;
    private javax.swing.JScrollPane jScrollPaneArticles;
    private javax.swing.JLabel jTitre;
    private javax.swing.JPanel articles;
    // End of variables declaration
}

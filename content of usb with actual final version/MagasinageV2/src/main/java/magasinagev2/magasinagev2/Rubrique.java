/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.*;

/**
 *
 * @author loubo
 */
public class Rubrique extends JFrame {
 
    public Rubrique(JPanel nouvArticle, Item itemAssocie, AfficheurDeRubriques pagePrincipale) {
        this.itemAffiche = itemAssocie;
        this.interfaceCourant = pagePrincipale;
        this.jPanelItem = nouvArticle;
        initComponents();
    }
    
    private void modifMouseClicked(java.awt.event.MouseEvent evt) {
        FenetreModif fenetre = new FenetreModif(this, this.interfaceCourant);
        fenetre.setVisible(true);
    }
    
    
    private void suppMouseClicked(java.awt.event.MouseEvent evt) {
        //avertissement et confirmation
        this.suppPopUp = new SupprimerPopUp(this.itemAffiche, this.interfaceCourant, this.interfaceCourant.listeItemObject);
        this.suppPopUp.getdialogWindow().setVisible(true);
    }
    
    public Item getItem(){
        return this.itemAffiche;
    }
    
    public JPanel getPanel(){
        return this.jPanelItem;
    }
    
    private void initComponents() {

        jLabelNomItem = new javax.swing.JLabel();
        jLabelDescItem = new javax.swing.JLabel();
        jLabelPrixItem = new javax.swing.JLabel();
        jLabelURLItem = new javax.swing.JLabel();
        jButtonModifierItem = new javax.swing.JButton();
        jButtonSupprimerItem = new javax.swing.JButton();

        jPanelItem.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        //configure les champs d'information
        jLabelNomItem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        if (this.itemAffiche.getNomUsage().equals("")){
            jLabelNomItem.setText(this.itemAffiche.getNomOfficiel());
        }
        else {
            jLabelNomItem.setText(this.itemAffiche.getNomUsage());
        }
        this.jLabelDescItem.setText(this.itemAffiche.getDesc());
        //si out of stock, ne pas afficher le prix
        if (this.itemAffiche.getUnite()=="OUT OF STOCK") {
            this.jLabelPrixItem.setText(this.itemAffiche.getUnite());
        }
        else {
            DecimalFormat df = new DecimalFormat("0.00");
            this.jLabelPrixItem.setText(df.format(this.itemAffiche.getPrix())+this.itemAffiche.getUnite());
        }
        //change la couleur du prix si le prix est différent de lors de la dernière mise à jour
        switch (this.itemAffiche.getDiffPrix()){
            case "plus" : this.jLabelPrixItem.setForeground(Color.red);
            case "moins" : this.jLabelPrixItem.setForeground(Color.green);
            default : this.jLabelPrixItem.setForeground(Color.black);
        }
         jLabelURLItem.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        this.jLabelURLItem.setText(this.itemAffiche.getURL().getURL()); /* on pourrait rendre l'url clicable*/

        //configure le bouton modifier de l'item
        jButtonModifierItem.setText("Modifier");
        jButtonModifierItem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modifMouseClicked(evt);
            }
        });

        //configure le bouton supprimer de l'item
        jButtonSupprimerItem.setText("Supprimer");
        jButtonSupprimerItem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suppMouseClicked(evt);
            }
        });

        //configure le layout de la rubrique contenant l'article d'intérêt
        javax.swing.GroupLayout jPanelItemLayout = new javax.swing.GroupLayout(jPanelItem);
        jPanelItem.setLayout(jPanelItemLayout);
        jPanelItemLayout.setHorizontalGroup(
            jPanelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelItemLayout.createSequentialGroup()
                        .addGroup(jPanelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelItemLayout.createSequentialGroup()
                                .addComponent(jLabelNomItem, javax.swing.GroupLayout.PREFERRED_SIZE, 874, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelPrixItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabelURLItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonSupprimerItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonModifierItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelItemLayout.createSequentialGroup()
                        .addComponent(jLabelDescItem, javax.swing.GroupLayout.PREFERRED_SIZE, 1113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelItemLayout.setVerticalGroup(
            jPanelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelItemLayout.createSequentialGroup()
                        .addGroup(jPanelItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelNomItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelPrixItem, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelURLItem, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDescItem, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelItemLayout.createSequentialGroup()
                        .addComponent(jButtonModifierItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSupprimerItem)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1129, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }
    
    // Variables declaration                    
    private javax.swing.JButton jButtonModifierItem;
    private javax.swing.JButton jButtonSupprimerItem;
    private javax.swing.JLabel jLabelDescItem;
    private javax.swing.JLabel jLabelPrixItem;
    private javax.swing.JLabel jLabelNomItem;
    private javax.swing.JLabel jLabelURLItem;
    private javax.swing.JPanel jPanelItem;           
    private Item itemAffiche;
    private AfficheurDeRubriques interfaceCourant;
    private SupprimerPopUp suppPopUp;
    // End of variables declaration        
}

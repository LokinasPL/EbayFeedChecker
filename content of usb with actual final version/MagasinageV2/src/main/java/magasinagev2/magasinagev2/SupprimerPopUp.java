/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 *
 * @author loubo
 */
public class SupprimerPopUp extends JDialog {
    public SupprimerPopUp(Item itemtosupp, AfficheurDeRubriques pageprincipale, ListeArticlesInteret liste){
        this.itemToSupp = itemtosupp;
        this.pagePrincipale = pageprincipale;
        this.listeAI = liste;
        this.observers = new ArrayList<Observer>();
        this.addObserver(pagePrincipale);
        initComponents();
    }
    
    private void suppAnnulerButtonActionPerformed (java.awt.event.ActionEvent evt){
        //annule la suppression
        this.dialogWindow.dispose();
    }
    
    private void suppActionButtonActionPerformed (java.awt.event.ActionEvent evt){
        //met le comunique dans la corbeille et le supprime du feed
        this.listeAI.remove(this.itemToSupp);
        this.pagePrincipale.listeItem.remove(this.itemToSupp);//specifically for URLsBrisees
        //notify la MainPage d'un changement dans le feed
        this.notifyObservers(new Object());
        //ferme la fenêtre
        this.dialogWindow.dispose();
    }
    
    private void initComponents(){
        dialogWindow = new javax.swing.JDialog();
        suppActionButton = new javax.swing.JButton();
        suppAnnulerButton = new javax.swing.JButton();
        suppText = new javax.swing.JLabel();
        suppTitle = new javax.swing.JLabel();
        
        //configure la fenêtre du popup
        dialogWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogWindow.setMinimumSize(new Dimension(450,210));
        dialogWindow.getContentPane().setBackground(new java.awt.Color(238, 50, 51));
        
        //configure le contenue de la fenêtre du pop up
        dialogWindow.setTitle("Supprimer un article");
        dialogWindow.setAlwaysOnTop(true);
        suppTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        suppTitle.setForeground(new java.awt.Color(255, 255, 255));
        suppTitle.setText("Souhaitez-vous vraiment supprimer cet article?");
        suppText.setForeground(new java.awt.Color(255, 255, 255));
        suppText.setText("<html>Cette action est irréversible et pourrait entrainer des changements<br>permanents à votre liste d'articles surveillés.<br><br>Souhaitez-vous tout de même supprimer l'article?</html>");
        
        //configure les boutons supprimer et annuler
        suppAnnulerButton.setText("Annuler");
        //suppAnnulerButton.setBackground(new java.awt.Color(108, 116, 118));
        suppAnnulerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suppAnnulerButtonActionPerformed(evt);
            }
        });
        suppActionButton.setText("Supprimer");
        //suppActionButton.setBackground(new java.awt.Color(102, 167, 197));
        suppActionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suppActionButtonActionPerformed(evt);
            }
        });
        
        //configure le layout de tous les éléments
        javax.swing.GroupLayout dialogWindowLayout = new javax.swing.GroupLayout(dialogWindow.getContentPane());
        dialogWindow.getContentPane().setLayout(dialogWindowLayout);
        dialogWindowLayout.setHorizontalGroup(
            dialogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(suppTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dialogWindowLayout.createSequentialGroup()
                        .addComponent(suppText, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dialogWindowLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(suppActionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(suppAnnulerButton)))
                .addContainerGap(5, Short.MAX_VALUE))
        );
        dialogWindowLayout.setVerticalGroup(
            dialogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(suppTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(suppText, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(suppAnnulerButton)
                    .addComponent(suppActionButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }
    
    //permet de notifié la MainPage d'une opération sur le feeRSS
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
    
    public javax.swing.JDialog getdialogWindow() {
        return this.dialogWindow;
    }
    
    private ArrayList<Observer> observers;
    private AfficheurDeRubriques pagePrincipale;
    private javax.swing.JDialog dialogWindow;
    private javax.swing.JButton suppActionButton;
    private javax.swing.JButton suppAnnulerButton;
    private javax.swing.JLabel suppText;
    private javax.swing.JLabel suppTitle;
    private Item itemToSupp;
    private ListeArticlesInteret listeAI;
}

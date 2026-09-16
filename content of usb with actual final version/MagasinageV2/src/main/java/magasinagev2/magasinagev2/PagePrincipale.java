/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 *
 * @author loubo
 */
public class PagePrincipale extends AfficheurDeRubriques implements WindowListener {
    
    public PagePrincipale(ListeArticlesInteret listeCourante) {
        this.listeItemObject = listeCourante;
        this.listeItem=listeCourante.getListeItem();
        initComponents();
        this.AffichageMAJ();
        timer.restart();
    }
    
    @Override
    public void notify(Object event){
        //notifie la page principale quand il y a un changement à la liste
        this.listeItem=this.listeItemObject.getListeItem();
        this.AffichageMAJ();
    }
    
    @Override
    public void AffichageMAJ () {
        //met à jour l'affichage de la liste dans la page principale
        articles.removeAll();
        jScrollPaneArticles.setViewportView(articles);
        articles.setLayout(new BoxLayout(articles, BoxLayout.Y_AXIS));
        articles.setBackground(new java.awt.Color(240, 236, 235));
        for (Item item : listeItemObject.getListeItem()){
            JPanel panelRubrique = new javax.swing.JPanel();
            panelRubrique.setBackground(new java.awt.Color(206,235,251));
            Rubrique rubrique = new Rubrique(panelRubrique, item, this);
            articles.add(rubrique.getPanel());
            Component spacer = Box.createRigidArea(new Dimension (500, 10));
            spacer.setBackground(new java.awt.Color(240,236, 235));
            articles.add(spacer);
        }
    }
 
    private void MAJActionPerformed (java.awt.event.ActionEvent evt) {
        //met à jour les informations de tous les items dans la liste courante
        Set<Item> itemBrokenURL = this.listeItemObject.InfoMAJ();
        if (!itemBrokenURL.isEmpty()) {
            //afficher fenêtre d'url brisées avec choix modif ou supp pour ch
            URLsBrisees urlsbrisees = new URLsBrisees(itemBrokenURL, this.listeItemObject);
            urlsbrisees.setVisible(true);
        }
        //notify la page des changements fait a la liste par la fenetre urlsbrisees
        this.notify(new Object());
        //remet la prochaine MAJ automatique à dans 3h
        timer.restart();
    }
    
    private void AjoutActionPerformed (java.awt.event.ActionEvent evt) {
        //ouvre la fenetre d'ajout
        FenetreAjout fenetreAjout = new FenetreAjout(this);
        fenetreAjout.setVisible(true);
    }
    
    private void CheminMemActionPerformed (java.awt.event.ActionEvent evt) {
        //flag si une fenêtre Ajout ou Modif est ouverte
        boolean flag=false;
        for (Window window : PagePrincipale.getWindows()){
            if(window.isVisible() && !window.equals(this)) flag=true;
        }
        if (flag) {
            //avise qu'un fenêtre ouverte empêche de jouer avec le fichier mémoire
            JDialog dialogWindow = new javax.swing.JDialog();
            JButton flagOKButton = new javax.swing.JButton();
            JLabel flagText = new javax.swing.JLabel();
            JLabel flagTitle = new javax.swing.JLabel();

            dialogWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            dialogWindow.setMinimumSize(new Dimension(450,210));
            dialogWindow.getContentPane().setBackground(new java.awt.Color(238, 50, 51));
            dialogWindow.setAlwaysOnTop(true);

            dialogWindow.setTitle("Impossible de changer le fichier mémoire");
            dialogWindow.setAlwaysOnTop(true);
            flagTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            flagTitle.setForeground(new java.awt.Color(255, 255, 255));
            flagTitle.setText("<html>Une fenêtre empêche le changement du chemin pointant sur<br>le fichier mémoire de la liste d'articles à surveiller<html>");
            flagText.setForeground(new java.awt.Color(255, 255, 255));
            flagText.setText("<html>Assurez-vous de fermer toutes les fenêtres d'ajout et de<br>modification d'articles avant de ré-essayer.</html>");
            flagOKButton.setText("OK");
            flagOKButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dialogWindow.dispose();
                }
            });

            javax.swing.GroupLayout dialogWindowLayout = new javax.swing.GroupLayout(dialogWindow.getContentPane());
            dialogWindow.getContentPane().setLayout(dialogWindowLayout);
            dialogWindowLayout.setHorizontalGroup(
                dialogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogWindowLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(dialogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(flagTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dialogWindowLayout.createSequentialGroup()
                            .addComponent(flagText, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(dialogWindowLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(flagOKButton)))
                    .addContainerGap())
            );
            dialogWindowLayout.setVerticalGroup(
                dialogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dialogWindowLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(flagTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(flagText, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(dialogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(flagOKButton))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            dialogWindow.setVisible(true);
        }
        else {
            //change le chemin vers le fichier mémoire de la liste d'article d'intérêt
            listeItemObject.Fetch(jFileChooserCheminMem.getSelectedFile().getAbsolutePath());
            this.AffichageMAJ();
        }
    }
    
    //sauvegarder la liste tel qu'affichee lors de la fermenture du logiciel
    public void windowClosed(WindowEvent e){
        this.listeItemObject.Write();
    }
    public void windowClosing (WindowEvent e){
        this.listeItemObject.Write();
    }
    //overirdse des autres fct de windowlistener qui ne sont pas utile dans notre cas
    public void windowActivated(WindowEvent e){;}
    public void windowDeactivated(WindowEvent e){;}
    public void windowIconified(WindowEvent e){;}
    public void windowDeiconified(WindowEvent e){;}
    public void windowOpened(WindowEvent e){;}
    
    
    private void initComponents() {

        jFileChooserCheminMem = new javax.swing.JFileChooser();
        jLabelTitre = new javax.swing.JLabel();
        jButtonMAJ = new javax.swing.JButton();
        jButtonAjout = new javax.swing.JButton();
        jScrollPaneArticles = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemCheminMem = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemAjout = new javax.swing.JMenuItem();
        jMenuItemMAJ = new javax.swing.JMenuItem();
        articles = new javax.swing.JPanel();

        jFileChooserCheminMem.setDialogTitle("Sélectionner le fichier mémoire à utiliser");
        jFileChooserCheminMem.setFileFilter(new FileNameExtensionFilter("JSON file", "json"));

        addWindowListener(this);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Magasinage");
        
        //Titre de la page
        jLabelTitre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelTitre.setText("Vos articles surveillés");
        
        //Bouton Mise À Jour
        jButtonMAJ.setText("Mettre à jour");
        jButtonMAJ.addActionListener(MAJActionListener = new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MAJActionPerformed(evt);
            }
        });

        //Bouton Ajout
        jButtonAjout.setText("Ajouter un article");
        jButtonAjout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AjoutActionPerformed(evt);
            }
        });

        //Menu File
        jMenuFile.setText("File");
        //Choisir JSON mémoire
        jMenuItemCheminMem.setText("Choisir le fichier mémoire");
        jMenuItemCheminMem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CheminMemActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemCheminMem);
        jMenuBar1.add(jMenuFile);

        //Menu Edit
        jMenuEdit.setText("Edit");
        //Menu Item Ajout
        jMenuItemAjout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemAjout.setText("Ajout un article");
        jMenuItemAjout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AjoutActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemAjout);
        //Menu Item Mise A Jour
        jMenuItemMAJ.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItemMAJ.setText("Mettre à jour");
        jMenuItemMAJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MAJActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemMAJ);
        jMenuBar1.add(jMenuEdit);

        setJMenuBar(jMenuBar1);
        
        //Vitesse de defilement
        jScrollPaneArticles.getVerticalScrollBar().setUnitIncrement(16);

        //Layout de la page
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneArticles)
                    .addComponent(jLabelTitre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 836, Short.MAX_VALUE)
                        .addComponent(jButtonAjout)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonMAJ)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonMAJ)
                    .addComponent(jButtonAjout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneArticles, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        
        timer = new javax.swing.Timer(10800000, MAJActionListener);//timer qui MAJActionPerformed automatiquement à toutes les 3h
        timer.start();
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButtonAjout;
    private javax.swing.JButton jButtonMAJ;
    private javax.swing.JFileChooser jFileChooserCheminMem;
    private javax.swing.JLabel jLabelTitre;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemAjout;
    private javax.swing.JMenuItem jMenuItemCheminMem;
    private javax.swing.JMenuItem jMenuItemMAJ;
    private javax.swing.JScrollPane jScrollPaneArticles;
    private javax.swing.JPanel articles;
    private java.awt.event.ActionListener MAJActionListener;
    private javax.swing.Timer timer;
    // End of variables declaration
}

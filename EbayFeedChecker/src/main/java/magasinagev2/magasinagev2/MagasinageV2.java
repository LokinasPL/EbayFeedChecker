/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package magasinagev2.magasinagev2;

import java.io.*;

/**
 *
 * @author loubo
 */
public class MagasinageV2 extends javax.swing.JFrame {

    public static void main(String[] args) {
        ListeArticlesInteret listeCourante= new ListeArticlesInteret();
        File fichiermem = new File(System.getProperty("user.dir")+"\\Memoire.json");
        try {
            //cree le fichier mem s'il existe pas deja
            if (fichiermem.createNewFile()){
                FileWriter writer = new FileWriter(fichiermem.getPath());
                writer.write("[\n]");
            }
            listeCourante.Fetch(System.getProperty("user.dir")+"\\Memoire.json");
        }
        catch (Exception e) {
            System.out.println("ERROR :");
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable () {
            public void run() {
                PagePrincipale pagePrincipale = new PagePrincipale(listeCourante);
                pagePrincipale.setVisible(true);
            }
        });
    }
}

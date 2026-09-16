/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author loubo
 */
public abstract class AfficheurDeRubriques extends JFrame implements Observer {
    
    public AfficheurDeRubriques(){
    }
    
    public abstract void AffichageMAJ();
    
    public abstract void notify(Object Event);
    
    public Set<Item> listeItem;
    public ListeArticlesInteret listeItemObject;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import java.util.*;
import org.json.*;
import java.io.*;
import java.nio.file.*;

/**
 *
 * @author loubo
 */
public class ListeArticlesInteret {
        private Set<Item> ListeItem;
	private Set<Item> brokenURL;
        private String PathToFile;
        private LogTime logger;
        
	public ListeArticlesInteret() {
            this.logger = new LogTime();
            this.ListeItem = new HashSet<Item> ();
            this.brokenURL = new HashSet<Item> ();
	}
        
        //get attributes
        public Set<Item> getListeItem(){
            return this.ListeItem;
        }
        public String getPathToFile(){
            return this.PathToFile;
        }
        
        //set attributes
        public void setListeItem(Set<Item> newListe){
            //maybe useless
            this.ListeItem=newListe;
        }
        public void setPathToFile(String newPath){
            this.PathToFile=newPath;
        }
        
	public Set<Item> InfoMAJ() {
            brokenURL.clear();
            //pour chaque Item dans ListeItem, verifie que la page web de l'URL est accessible
            for (Item item : this.ListeItem) {
                if (item.getURL().getisValid()==false){
                    brokenURL.add(item);
                }
                else {
                    try {
                        item.ParsePage(this); //met à jour les info si accessible
                    }
                    catch (invalidURL i) {
                        item.getURL().setisValid(false);
                        brokenURL.add(item); //flag les item dont l'URL est innaccessible
                    }
                }
            }
            //renvoie les item dont l'URL est innaccessible pour que l'appelleur gére les problèmes d'URL
            return this.brokenURL; 
	}
        
        public void add(Item item) throws invalidURL {
            //verifie qu'un item identique n'existe pas deja
            for (Item itemExistant : this.ListeItem){
                if (item.equals(itemExistant)) {
                    item.getURL().setisValid(false);
                    throw new invalidURL("item existe");
                }
            }
            ListeItem.add(item);
        }
        
        public void replace(Item ancienItem, Item newItem) throws invalidURL {
            for (Item itemExistant : this.ListeItem){
                //si on essaye de remplacer l'url d'un item par l'url d'un item qui existe deja
                if (newItem.equals(itemExistant) && !ancienItem.equals(itemExistant)) {
                    newItem.getURL().setisValid(false);
                    throw new invalidURL("item existe");
                }
            }
            this.ListeItem.remove(ancienItem);
            this.ListeItem.add(newItem);
        }
        
        public void remove(Item item){
            ListeItem.remove(item);
        }

	public void Fetch(String chemin) {
            logger.log("fetchmem", "start", "intra");
            this.PathToFile=chemin;
            try{
                //crée un lecteur de fichier pour le fichier json dans chemin
                BufferedReader br = Files.newBufferedReader(Paths.get(chemin));
                String line, jsonData="";
                while ((line = br.readLine()) != null){
                    jsonData = jsonData + line;
                }
                //traduit le fichier en jsonArray
                if (jsonData.length()<=0) {jsonData="[]";} //s'assure de la lisibilite de jsonData si le fichier memoire etait vide
                JSONArray memoireItems = new JSONArray(jsonData);
                ListeItem.clear();
                //pour chaque item dans le jsonArray, crée un Item puis met l'Item dans ListeItem
                for (int i=0; i<memoireItems.length(); i++) {
                    JSONObject article = memoireItems.getJSONObject(i);
                    Item item = new Item();
                    item.listeArticles=this;
                    item.setNomUsage(article.getString("Nom Usage"));
                    item.setURL(article.getString("URL"));
                    item.setDesc(article.getString("Desc"));
                    item.setNomOfficiel(article.getString("Nom Officiel"));
                    item.setPrix(article.getFloat("Prix"));
                    item.setUnite(article.getString("Unite"));
                    item.setDiffPrix(article.getString("Diff Prix"));
                    ListeItem.add(item);
                }
                br.close();
            }
            catch (Exception e){
                System.out.println("ERROR :");
                e.printStackTrace();
            }
            logger.log("fetchmem", "end", "intra");
            
	}
        
	public void Write(String chemin) {
            logger.log("writemem", "start", "intra");
            //chemin doit être un string qui pointe sur un fichier mémoire .json
            try {
                //transforme la liste d'item en json array contenant tous les item en json object individuel
                JSONArray listememoire = new JSONArray();
                for(Item item : this.ListeItem){
                    JSONObject itemJSON = new JSONObject();
                    itemJSON.put("Nom Usage", item.getNomUsage());
                    itemJSON.put("URL", item.getURL().getURL());
                    itemJSON.put("Desc", item.getDesc());
                    itemJSON.put("Nom Officiel", item.getNomOfficiel());
                    itemJSON.put("Prix", item.getPrix());
                    itemJSON.put("Unite", item.getUnite());
                    itemJSON.put("Diff Prix", item.getDiffPrix());
                    listememoire.put(itemJSON);
                }
                //écrit le json array en format json dans le fichier mémoire
                File memoire = new File(chemin);
                BufferedWriter writer = new BufferedWriter(new FileWriter(memoire));
                writer.write(listememoire.toString());
                writer.close();
            }
            catch (Exception e) {
                System.out.println("ERROR :");
                e.printStackTrace();
            }
            
            logger.log("writemem", "end", "intra");
	}
        
        public void Write(){
            this.Write(this.PathToFile);
        }
        
        /*public void Write(Item item) {
		//fr don't know how to implement this...
                //
	}*/
}

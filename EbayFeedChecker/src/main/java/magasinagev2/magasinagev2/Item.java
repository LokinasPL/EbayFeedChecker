/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import org.openqa.selenium.*;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.*;
/**
 *
 * @author loubo
 */
public class Item {
        private String NomOfficiel;
        private URL URL;
	private Float Prix;
        private String Unite;
	private String Desc="";
	private String DiffPrix; //prend la valeur "plus" OU "moins" OU "pareil" seulement
	private String NomUsage="";
        public ListeArticlesInteret listeArticles;
        private WebDriver driver;
        private LogTime logger;
        public Item(){
            this.logger=new LogTime();
        }
        
        //get attributes
        public String getNomOfficiel(){
            return this.NomOfficiel;
        }
        public URL getURL(){
            return this.URL;
        }
        public Float getPrix(){
            return this.Prix;
        }
        public String getUnite(){
            return this.Unite;
        }
        public String getDesc() {
            return this.Desc;
        }
        public String getDiffPrix(){
            return this.DiffPrix;
        }
        public String getNomUsage(){
            return this.NomUsage;
        }
        
        //set attributes
        public void setNomOfficiel(String nomofficiel) {
            this.NomOfficiel=nomofficiel;
	}
        public void setURL(String url){ //pour fetch quand url sort du fichier mem
            this.URL=new URL(url);
        }
        public void entreURL(String link) throws invalidURL { //pour le reste du logiciel quand url sort de l'util
            URL url = new URL(link);
            if (url.getisValid()==true) {
                this.URL=url;;
                this.ParsePage(this.listeArticles);
            }
            else throw new invalidURL(url.getPlateforme()==""? "mauvaise plateforme":"page illisible");
	}
	public void setPrix(Float prix) {
            this.Prix=prix;
	}
	public void setUnite (String unite){
            this.Unite=unite;
        }
	public void setDesc(String desc) {
            this.Desc=desc;
	}
        public void setDiffPrix (String diffprix){
            this.DiffPrix=diffprix;
        }
        public void setNomUsage(String nomusage) {
            this.NomUsage=nomusage;
	}
        
        ///////////////////////////////////////////////////////////////////////////////////////
        @Override
	public int hashCode(){
            int hashcode=0;
            for (int i=0; i<this.URL.getURL().length(); i++ ){
                hashcode += (int)this.URL.getURL().charAt(i);
            }
            return (hashcode);
        }
        @Override
        public boolean equals(Object it){
            Item item = (Item) it;
            return (this.URL.getURL().equals(item.URL.getURL()));
        }
        
        public void ComparePrix(Item ancienItem){
            //compare le prix de l'ancienItem et this, set this.DiffPrix selon le résultat
            if (this.Prix>ancienItem.Prix) this.DiffPrix="plus";
            if (this.Prix<ancienItem.Prix) this.DiffPrix="moins";
            if (this.Prix==ancienItem.Prix) this.DiffPrix="pareil";
        }
        
        public String ComparePrix(Float nouvPrix){
            if (this.Prix==null) return("pareil");
            if (nouvPrix>this.Prix) return("plus");
            if (nouvPrix<this.Prix) return("moins");
            if (nouvPrix==this.Prix) return("pareil");
            else return("probleme");
        }

	public void ParsePage(ListeArticlesInteret listeItem) throws invalidURL {
            //creer le driver pour ce test
            this.driver = new ChromeDriver();
            try {
                this.driver.manage().timeouts().implicitlyWait((long)(Math.random()*4+1), TimeUnit.SECONDS);
                this.driver.manage().timeouts().setScriptTimeout(30, TimeUnit.DAYS);
                this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            }
            catch(Exception e){
                System.out.println("ERROR :");
                e.printStackTrace();
            }
            logger.log("parsepage", "start", "inter");
            try{
                //ouvre le browser et la page web de URL
                this.driver.get(this.URL.getURL());
                //id what website is being used
                String site = this.URL.getPlateforme();
                //si ebay
                if (site=="ebay") {
                    //nom officiel contient nom de l'item sur le site
                    this.NomOfficiel = this.driver.findElement(By.className("x-item-title__mainTitle")).getText();
                    if (this.driver.findElements(By.className("x-price-primary")).size()>0){
                        //ogprix contient qqch comme "C $54.62"
                        String ogprix = this.driver.findElement(By.className("x-price-primary")).getText();
                        //trouve ou le prix commence et les unite se termine
                        int i =0;
                        while (i<ogprix.length() && !Character.isDigit(ogprix.charAt(i))) {i++;}
                        //pris contient qqch comme ["C $","54.62"] ou ["US $","1800.00"]
                        String[] prix = {ogprix.substring(0,i), ogprix.substring(i, ogprix.length())};
                        prix[1]=prix[1].replaceAll(",", "");
                        prix[1]=prix[1].replaceAll(" ", "");
                        //tranforme prix en unite et (float)prix
                        this.Unite = " "+prix[0]+" ";
                        Float nouvPrix = Float.parseFloat(prix[1]);
                        this.DiffPrix = this.ComparePrix(nouvPrix);
                        this.Prix = nouvPrix;
                    }
                    else {
                        this.Unite="OUT OF STOCK";
                    }
                }
                //ferme la fenetre du browser
                this.driver.quit();
            }
            catch (org.openqa.selenium.NoSuchElementException e){
                //page illisible, element pas present
                throw new invalidURL("page illisible");
            }
            logger.log("parsepage", "end", "inter");
	}

}

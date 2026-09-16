/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
/**
 *
 * @author loubo
 */
public class URL {
        private String URL;
	private Boolean isValid;
        private WebDriver driver;
        private String Plateforme="";
        private LogTime logger;

	public URL(String link) {
            this.logger=new LogTime();
            this.URL = link;
            this.isValid = this.isURLValid();
	}
        
        //get attributes
        public String getURL(){
            return this.URL;
        }
        public Boolean getisValid(){
            return this.isValid;
        }
        public String getPlateforme(){
            return this.Plateforme;
        }
        
        //set attributes
        public void setisValid (Boolean validity){
            this.isValid=validity;
        }
        public void setURL(String link){
            this.URL=link;
            this.isValid = this.isURLValid();
        }

	public Boolean isURLValid() {
            Boolean value=false;
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
            logger.log("validateURL", "start", "inter");
            //ouvre page web
            try {
                this.driver.get(this.URL);
                //check si ebay sinon invalide (retrait d'amazon pcq bot detection bs)
                if (!this.driver.findElements(By.id("ebayLogoTitle")).isEmpty() || !this.driver.findElements(By.cssSelector("a#gh-la[href*='ebay']")).isEmpty()) {Plateforme="ebay";}
                else {throw new Exception();}
                //check que y'a un item sur la page
                if (Plateforme.equals("ebay") && !this.driver.findElements(By.className("x-item-title__mainTitle")).isEmpty()) {value=true;}
            }
            catch (Exception e){}//attrape si url pas accessible, si pas ebay et si pas item sur la page
            finally {
                this.driver.quit();
                logger.log("validateURL", "end", "inter");
                return value;
            }
            
	}
}

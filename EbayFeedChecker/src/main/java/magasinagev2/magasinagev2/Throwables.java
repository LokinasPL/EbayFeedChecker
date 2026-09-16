/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

/**
 *
 * @author loubo
 */

class invalidURL extends Exception {  
    //lance quand l'URL est invalide
    public invalidURL(String type){
        this.type = type;
    }
    public String type; //indique le type d'invalidité pour personnaliser le message d'erreur
}
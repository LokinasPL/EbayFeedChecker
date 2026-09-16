/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package magasinagev2.magasinagev2;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
/**
 *
 * @author loubo
 */
public class LogTime {
    public LogTime(){
        try{
            //gets or creates timelogging text file with buffered writer
            Path chemin = Paths.get(System.getProperty("user.dir"),"\\TimeLog.txt");
            //assure que APPEND et pas TRUNCATE_EXISTING au cas ou y'a deja des donnees dans le fichier
            writer = Files.newBufferedWriter(chemin, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
            //wrtie header if file empty
            BufferedReader reader = Files.newBufferedReader(chemin);
            if (reader.readLine()==null) {
                writer.write("Task,Start.End,Type,Time");
                writer.flush();
            }
        }
        catch(Exception e){
            System.out.println("ERROR : ");
            e.printStackTrace();
        }
    }
    
    public void log (String taskName, String se, String type){
        try{
            //get timeATM in millisecond
            timeATM=System.currentTimeMillis();
            //add line to text file with task, start/end, intra/inter and time
            writer.write("\n"+taskName+","+se+","+type+","+timeATM);
            writer.flush();
        }
        catch(Exception e){
            System.out.println("ERROR : ");
            e.printStackTrace();
        }
    }
    
    private long timeATM;
    private BufferedWriter writer;
}

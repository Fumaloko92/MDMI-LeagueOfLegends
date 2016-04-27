/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import org.json.*;
import com.opencsv.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
/**
 *
 * @author lucas
 */
public class FileManager {
    static public void writeItems(String fileName,ArrayList<ArrayList<String>> data){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            CSVWriter writer = new CSVWriter(out);
            String[] t=new String[0];
            for(ArrayList<String> row : data){
                writer.writeNext(row.toArray(t));
            }
            out.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }   
    }
    
   
}

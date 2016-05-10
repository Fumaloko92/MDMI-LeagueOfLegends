/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import org.json.*;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
                boolean found=false;
                for(String str : row){
                    if(!str.isEmpty()){
                        found=true;
                    }
                }
                if(found)
                writer.writeNext(row.toArray(t));
            }
            out.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }   
    }
    
    static public boolean appendItems(String fileName,ArrayList<ArrayList<String>> data,boolean firstRowIsHeader)
    {
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName,true)); 
            CSVWriter writer = new CSVWriter(out);
            String[] t=new String[0];
            for(ArrayList<String> row : data){
                if(!firstRowIsHeader){
                boolean found=false;
                for(String str : row){
                    if(!str.isEmpty()){
                        found=true;
                    }
                }
                if(found)
                    writer.writeNext(row.toArray(t));
                }else
                    firstRowIsHeader=false;
            }
            out.close();
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }  
    }
    
    static public ArrayList<String> readColumnFromFile(String fileName, int indexColumn,boolean firstRowIsHeader)
    {
        try{
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        CSVReader reader = new CSVReader(in);
        String [] nextLine;
        ArrayList<String> result=new ArrayList<String>();
        while ((nextLine = reader.readNext()) != null) {
            if(!firstRowIsHeader)
                result.add(nextLine[indexColumn]);
            else
                firstRowIsHeader=false;
        }
        in.close();
        return result;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    static public ArrayList<ArrayList<String>> readFile(String fileName,boolean firstRowIsHeader)
    {
      try{
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        CSVReader reader = new CSVReader(in);
        String [] nextLine;
        ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
        while ((nextLine = reader.readNext()) != null) {
            if(!firstRowIsHeader){
                 ArrayList<String> row=new ArrayList<String>();
            for(int i=0;i<nextLine.length;i++)
                row.add(nextLine[i]);
                result.add(row);
            }else
                firstRowIsHeader=false;
        }
        in.close();
        return result;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }  
    }
    
   static public void FetchMatchDetails(String toFileName,ArrayList<String> fromFileNames){
       try{
           ArrayList<ArrayList<String>> toWrite=new ArrayList<ArrayList<String>>();
           for(String fileName : fromFileNames){
               
               BufferedReader in = new BufferedReader(new FileReader(fileName));
                CSVReader reader = new CSVReader(in);
                String [] nextLine;
                ArrayList<String> row=new ArrayList<String>();
                int championIndex=0,index=1;
                int countWinning=0,countLosing=0;
                while ((nextLine = reader.readNext()) != null) {
                    
                    for(int i=0;i<nextLine.length;i++)
                        row.add(nextLine[i]);
                championIndex++;
               
                if(championIndex==10){
                    toWrite.add(row);
                    row=new ArrayList<String>();
                    championIndex=0;
                    countWinning=0;
                    countLosing=0;
                }
                index++;
            }      
               in.close();
           }
           BufferedWriter out = new BufferedWriter(new FileWriter(toFileName)); 
            CSVWriter writer = new CSVWriter(out);
            ArrayList<String> header=new ArrayList<String>();
            for(int i=0;i<10;i++)
            {
                header.add("champID_"+(i+1)+"_player");
                header.add("item1_"+(i+1)+"_player");
                header.add("item2_"+(i+1)+"_player");
                header.add("item3_"+(i+1)+"_player");
                header.add("item4_"+(i+1)+"_player");
                header.add("item5_"+(i+1)+"_player");
                header.add("item6_"+(i+1)+"_player");
                header.add("item7_"+(i+1)+"_player");
                header.add("won_"+(i+1)+"_player");
            }
            toWrite.add(0,header);
            String[] t=new String[0];
            for(ArrayList<String> row : toWrite){
                writer.writeNext(row.toArray(t));
            }
            out.close();
       }catch(Exception e){
            System.out.println(e.getMessage());
        }
   }
}

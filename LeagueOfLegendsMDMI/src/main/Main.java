/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import httpRequests.GetPost;
import csv.FileManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author lucas
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
          /*  ArrayList<String> apiKeys=new ArrayList<String>();
            apiKeys.add("de811933-2fe2-4c11-b968-48b8bb2211d0");
            apiKeys.add("64405c49-48eb-4b10-a681-fbb24d3b90ea");
            apiKeys.add("1ace5afc-c103-4987-ad0d-d7f3f493a507");
            apiKeys.add("bd798615-215e-4d4f-9f21-1ea184ad0a46");
            GetPost t=new GetPost(apiKeys);//USE YOUR API KEY*/
            //FileManager.writeItems("champions.csv", t.getAllChampions(2,0));
           //FileManager.writeItems("items.csv", t.getAllItems(2,0));
            //FileManager.writeItems("challengerPlayers.csv",t.getAllChallengerPlayers(2, 1) );
            //FileManager.writeItems("masterPlayers.csv",t.getAllMasterPlayers(2, 1) );
       /*    ArrayList<String> challengerPlayersID = FileManager.readColumnFromFile("challengerPlayers.csv", 7,true);
            ArrayList<String> masterPlayersID = FileManager.readColumnFromFile("masterPlayers.csv", 7, true);
           HashSet<String> playersID = new HashSet<String>();
           for(String id : challengerPlayersID)
               playersID.add(id);
            for(String id : masterPlayersID)
               playersID.add(id);
            try{
            for(String playerID : playersID){
                ArrayList<ArrayList<String>> data;
                do{
                   data= t.getAllMatchesByPlayerID(playerID, 2, 0);
                    if(data==null){
                        t.ResetAPIIndex();
                        Thread.sleep(10000);
                    }
                }while(data==null);
            FileManager.appendItems("matchesV2.csv", data,true);
            }
            }catch(Exception e){
                
            }*/
       /*       ArrayList<String> matchesID= FileManager.readColumnFromFile("matchesV1.csv", 2, false);
              HashSet<String> uniqueMatchesID= new HashSet<String>();
              for(String matchID : matchesID)
                  uniqueMatchesID.add(matchID);
              ArrayList<String> uniqueListMatchesID=new ArrayList<String>();
              for(String matchID : uniqueMatchesID)
                  uniqueListMatchesID.add(matchID);
              
              ArrayList<String> listLuca=new ArrayList(uniqueListMatchesID.subList(0, uniqueListMatchesID.size()));
              ArrayList<String> listAnastasios=new ArrayList(uniqueListMatchesID.subList(uniqueListMatchesID.size()/3, uniqueListMatchesID.size()*2/3));
              ArrayList<String> listMaksims=new ArrayList(uniqueListMatchesID.subList(uniqueListMatchesID.size()*2/3, uniqueListMatchesID.size()-1));
              ArrayList<String> filters=new ArrayList<String>();
              filters.add("championId");        
              filters.add("stats.item0");	
              filters.add("stats.item1");
              filters.add("stats.item2");
              filters.add("stats.item3");
              filters.add("stats.item4");
              filters.add("stats.item5");
              filters.add("stats.item6");
              filters.add("stats.winner");
              //CHANGE THE NAME OF THE FILE AND THE NAME OF THE LIST!!!
              float i=51063;
              listLuca=new ArrayList(listLuca.subList((int)i, listLuca.size()));
            try{
                
            for(String matchID : listLuca){//TOCHANGE
                 ArrayList<ArrayList<String>> data;
                do{
                   data= t.getMatchesInformationsByMatchID(matchID, 3, 0,filters);
                    if(data==null){
                        t.ResetAPIIndex();
                        Thread.sleep(10000);
                    }
                }while(data==null);
                boolean ris;
                ArrayList<ArrayList<String>> d=new ArrayList<ArrayList<String>>();
                for(ArrayList<String> row : data)
                {
                    boolean toAdd=false;
                    for(String field : row)
                        if(!field.isEmpty()){
                            toAdd=true;
                            break;
                        }
                    if(toAdd)
                        d.add(row);
                }
                if(d.size()==11){
                do{
                    
                 ris=FileManager.appendItems("matchDetailedV2Luca.csv", d,true);//TOCHANGE
                 if(!ris)
                     Thread.sleep(2000);
                    
                }while(ris==false);
                }
                System.out.println("Complete at " + i/listLuca.size()*100+"%"+System.lineSeparator()+i+"th match Done!");
               i++;
            }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }*/
            //ArrayList<String> championsID = FileManager.readColumnFromFile("champions.csv", 0, true);
            //ArrayList<String> itemsID = FileManager.readColumnFromFile("items.csv", 20, true);*/
         /* ArrayList<String> fileNames=new ArrayList<String>();
          fileNames.add("MatchDetailedV2Luca.csv");
          
          FileManager.FetchMatchDetails("fetchedMatches.csv",fileNames );*/
       // pickChampion p=new pickChampion();
	}

}
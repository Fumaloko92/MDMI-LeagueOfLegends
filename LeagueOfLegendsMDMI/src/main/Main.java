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
            GetPost t=new GetPost("de811933-2fe2-4c11-b968-48b8bb2211d0");//USE YOUR API KEY
            //FileManager.writeItems("champions.csv", t.getAllChampions(2,0));
           //FileManager.writeItems("items.csv", t.getAllItems(2,0));
            //FileManager.writeItems("challengerPlayers.csv",t.getAllChallengerPlayers(2, 1) );
            //FileManager.writeItems("masterPlayers.csv",t.getAllMasterPlayers(2, 1) );
           // ArrayList<String> challengerPlayersID = FileManager.readColumnFromFile("challengerPlayers.csv", 7,true);
           // ArrayList<String> masterPlayersID = FileManager.readColumnFromFile("masterPlayers.csv", 7, true);
           // ArrayList<String> playersID = challengerPlayersID;
            //for(String id : masterPlayersID)
             //   playersID.add(id);
              ArrayList<String> matchesID= FileManager.readColumnFromFile("matches.csv", 2, false);
              HashSet<String> uniqueMatchesID= new HashSet<String>();
              for(String matchID : matchesID)
                  uniqueMatchesID.add(matchID);
              ArrayList<String> uniqueListMatchesID=new ArrayList<String>();
              for(String matchID : uniqueMatchesID)
                  uniqueListMatchesID.add(matchID);
              
              ArrayList<String> listLuca=new ArrayList(uniqueListMatchesID.subList(0, uniqueListMatchesID.size()/3));
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
              //CHANGE THE NAME OF THE FILE AND THE NAME OF THE LIST!!!
            try{
                int i=0;
            for(String matchID : listLuca){//TOCHANGE
                 ArrayList<ArrayList<String>> data;
                do{
                   data= t.getMatchesInformationsByMatchID(matchID, 1, 0,filters);
                    if(data==null)
                        Thread.sleep(10000);
                }while(data==null);
                FileManager.appendItems("matchDetailedLuca.csv", data,true);//TOCHANGE
                System.out.println(i+"th Match done!");
               i++;
            }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            //ArrayList<String> championsID = FileManager.readColumnFromFile("champions.csv", 0, true);
            //ArrayList<String> itemsID = FileManager.readColumnFromFile("items.csv", 20, true);
	}

}
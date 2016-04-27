/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import httpRequests.GetPost;
import csv.FileManager;

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
            FileManager.writeItems("champions.csv", t.getAllChampions(2,0));
            FileManager.writeItems("items.csv", t.getAllItems(2,0));
	}

}
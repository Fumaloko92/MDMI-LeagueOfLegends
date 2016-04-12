/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import httpRequests.GetPost;
/**
 *
 * @author lucas
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetPost t=new GetPost("");//USE YOUR API KEY
            try {
                t.sendGet("https://euw.api.pvp.net/api/lol/euw/v1.2/champion?api_key=");
            } catch (Exception ex) {
                System.out.println("There were problems with the GET request");
            }
	}

}
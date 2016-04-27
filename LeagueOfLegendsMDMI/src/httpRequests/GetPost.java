/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpRequests;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import org.json.*;
/**
 *
 * @author lucas
 */
public class GetPost {
     private final String USER_AGENT = "";
    private String API_KEY ="";
    
    public GetPost(String api_key){
        API_KEY=api_key;
    }
   
	// HTTP GET request
	private JSONObject sendGet(String url) throws Exception {
		
		url+=API_KEY;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
                

		JSONObject jObj=new JSONObject(response.toString());
                return jObj;    
	}
	
	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());

	}
        
        public ArrayList<ArrayList<String>> getAllChampions(float headersLevel,float dataLevel){
            try{
            String url= "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion?champData=image&api_key=";
            JSONObject obj= sendGet(url);
            obj=(JSONObject)obj.get("data");
            ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
            ArrayList<String> headers=new ArrayList<String>();
            headers.addAll(asSortedList(getHeaders(headersLevel,obj)));
            result=getRows(obj,headers,dataLevel);
            result.add(0,headers);
            
            return result;
            }catch(Exception e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        
        private ArrayList<ArrayList<String>> getRows(JSONObject obj,ArrayList<String> headers,float ignoreLevel){
            ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
            for(String key: obj.keySet())
            {
                if(ignoreLevel<=0)
                {
                    if(obj.get(key).getClass().getName().equals("org.json.JSONObject")){
                    ArrayList<String> row=new ArrayList<String>();
                    for(String header:headers)
                        row.add(getValueFromCompositeKey(header,obj.getJSONObject(key)));            
                    result.add(row);
                   }
                }else{
                    if(obj.get(key).getClass().getName().equals("org.json.JSONObject"))
                       for(ArrayList<String> row : getRows(obj.getJSONObject(key),headers,ignoreLevel-1))
                           result.add(row);
                }
            }
            return result;
        }
        
        private String getValueFromCompositeKey(String key,JSONObject obj){
            try{
            String[] splits=key.split("[.]");
            JSONObject temp=obj;
            for(int i=0;i<splits.length-1;i++)
                temp=temp.getJSONObject(splits[i]);
            return String.valueOf(temp.get(splits[splits.length-1]));
            }catch(Exception e)
            {
                return "";
            }
        }
        private static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
            List<T> list = new ArrayList<T>(c);
            java.util.Collections.sort(list);
            return list;
        }
        
        private HashSet<String> getHeaders(float ignoreLevel,JSONObject obj){
            HashSet<String> header = new HashSet<String>();
            for(String key: obj.keySet())
            {
                if(obj.get(key).getClass().getName().equals("org.json.JSONObject"))
                {
                  for(String k: getHeaders(ignoreLevel-1,obj.getJSONObject(key)))
                      if(ignoreLevel<=1)
                        header.add(key+"."+k);
                  else
                          if(ignoreLevel-1<=1)
                              header.add(k);
                }  
                else
                    if(ignoreLevel<=1)
                    header.add(String.valueOf(key));
                    
            }
            return header;
        }
        
        public ArrayList<ArrayList<String>> getAllItems(float headersLevel,float dataLevel)
        {
            try{
            String url= "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/item?itemListData=all&api_key=";
            JSONObject obj= sendGet(url);
            obj=(JSONObject)obj.get("data");
            ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
            ArrayList<String> headers=new ArrayList<String>();
            headers.addAll(asSortedList(getHeaders(headersLevel,obj)));
            result=getRows(obj,headers,dataLevel);
            result.add(0,headers);
            
            return result;
            }catch(Exception e){
                System.out.println(e.getMessage());
                return null;
            }
           
        }
}

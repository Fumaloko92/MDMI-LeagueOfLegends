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
    private ArrayList<String> API_KEYS;
    private int apiIndex;
    private ArrayList<Boolean> currentAvailability;
    public GetPost(ArrayList<String> api_key){
        apiIndex=0;
        API_KEYS=new ArrayList<String>();
        currentAvailability=new ArrayList<Boolean>();
        for(String s : api_key){
            API_KEYS.add(s);
            currentAvailability.add(Boolean.TRUE);
        }
            
    }
    
    public void ResetAPIIndex(){
        apiIndex=0;
        for(int i=0;i<currentAvailability.size();i++)
            currentAvailability.set(i, Boolean.TRUE);
    }
    // HTTP GET request
    private Boolean OneAPIAvailable(){
        for(Boolean b:currentAvailability)
            if(b)
                return Boolean.TRUE;
        return Boolean.FALSE;
    }
    
    private JSONObject sendGet(String url) throws Exception {
        
        HttpURLConnection con;
        boolean repeat=false;
         int responseCode;
         
        do{
            String s=url;
        s+=API_KEYS.get(apiIndex);
        URL obj = new URL(s);
        con = (HttpURLConnection) obj.openConnection();
        
        // optional default is GET
        con.setRequestMethod("GET");
        
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + s);
        System.out.println("Response Code : " + responseCode);
        if(responseCode==404)
            return new JSONObject("{\n" +
"	\"status\": {\n" +
"		\"message\": \"Not Found\",\n" +
"		\"status_code\": 404\n" +
"	}\n" +
"}");
        if(responseCode==500||responseCode==429){
            currentAvailability.set(apiIndex, Boolean.FALSE);
            if(apiIndex+1<API_KEYS.size()){
        apiIndex++;
            }else{
            apiIndex=0;
            }
            repeat=true;
        }else
            repeat=false;
        
            }while(OneAPIAvailable()&&repeat);
        if(responseCode!=200)
            return null;
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
    private ArrayList<ArrayList<String>> getRows(JSONArray obj,ArrayList<String> headers,float ignoreLevel){
        ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
        for(Object o : obj){
             String className=o.getClass().getName();
            if(ignoreLevel<=0)
            {
                if(className.equals("org.json.JSONObject")){
                    ArrayList<String> row=new ArrayList<String>();
                    for(String header:headers)
                        row.add(getValueFromCompositeKey(header,(JSONObject)o));
                    result.add(row);
                }else{
                        if(ignoreLevel<=0){
                            ArrayList<String> row=new ArrayList<String>();
                            for(String header: headers)
                                row.add(String.valueOf(o));
                            result.add(row);
                        }
                     
                }
            }else{
                 if(className.equals("org.json.JSONObject"))
                    for(ArrayList<String> row : getRows((JSONObject)o,headers,ignoreLevel-1))
                        result.add(row);    
                 else
                     if(className.equals("org.json.JSONArray"))
                         for(ArrayList<String> row : getRows((JSONArray)o,headers,ignoreLevel-1))
                                result.add(row); 
            }
        }
        return result;
    }
    private ArrayList<ArrayList<String>> getRows(JSONObject obj,ArrayList<String> headers,float ignoreLevel){
        ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
        for(String key: obj.keySet())
        {
            String className=obj.get(key).getClass().getName();
            if(ignoreLevel<=0)
            {
                if(className.equals("org.json.JSONObject")||className.equals("org.json.JSONArray")){
                    if(className.equals("org.json.JSONObject")){
                    ArrayList<String> row=new ArrayList<String>();
                    for(String header:headers)
                        row.add(getValueFromCompositeKey(header,obj.getJSONObject(key)));
                    result.add(row);
                    }else{
                       for(ArrayList<String> row : getRows(obj.getJSONArray(key),headers,ignoreLevel-1)){
                           result.add(row); 
                       }
                    }
                }else{
                        if(ignoreLevel<=0){
                            ArrayList<String> row=new ArrayList<String>();
                            for(String header: headers)
                                row.add(getValueFromCompositeKey(header,obj));
                            result.add(row);
                        }
                    } 
            }else{
                if(className.equals("org.json.JSONObject")||className.equals("org.json.JSONArray")){
                    if(className.equals("org.json.JSONObject"))
                    for(ArrayList<String> row : getRows(obj.getJSONObject(key),headers,ignoreLevel-1))
                        result.add(row);            
                    else
                       for(ArrayList<String> row : getRows(obj.getJSONArray(key),headers,ignoreLevel-1))
                        result.add(row);   
                }
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
            String className=obj.get(key).getClass().getName();
            if(className.equals("org.json.JSONObject"))
            {
                for(String k: getHeaders(ignoreLevel-1,obj.getJSONObject(key)))
                    if(ignoreLevel<=1)
                        header.add(key+"."+k);
                    else
                        if(ignoreLevel-1<=1)
                            header.add(k);
            }
            else
                if(className.equals("org.json.JSONArray"))
                {
                    for(Object o : obj.getJSONArray(key))
                    {
                        if(o.getClass().getName().equals("org.json.JSONObject")){
                            for(String k: getHeaders(ignoreLevel-1,(JSONObject)o))
                                if(ignoreLevel<=1)
                                    header.add(key+"."+k);
                                else
                                    if(ignoreLevel-1<=1)
                                        header.add(k);
                        }else{
                            if(ignoreLevel<=1)
                                header.add(String.valueOf(key));
                        }
                    }
                }else
                {
                    if(ignoreLevel<=1)
                        header.add(String.valueOf(key));
                }
            
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
    
    public ArrayList<ArrayList<String>> getAllChallengerPlayers(float headersLevel,float dataLevel)
    {
        try{
            String url= "https://euw.api.pvp.net/api/lol/euw/v2.5/league/challenger?type=RANKED_SOLO_5x5&api_key=";
            JSONObject obj= sendGet(url);
            ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
            ArrayList<String> headers=new ArrayList<String>();
            headers.addAll(asSortedList(getHeaders(headersLevel,obj)));
            result=getRows(obj,headers,dataLevel);
            result.add(0,headers);
            return result;
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }
    
    public ArrayList<ArrayList<String>> getAllMasterPlayers(float headersLevel,float dataLevel)
    {
        try{
            String url= "https://euw.api.pvp.net/api/lol/euw/v2.5/league/master?type=RANKED_SOLO_5x5&api_key=";
            JSONObject obj= sendGet(url);
            ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
            ArrayList<String> headers=new ArrayList<String>();
            headers.addAll(asSortedList(getHeaders(headersLevel,obj)));
            result=getRows(obj,headers,dataLevel);
            result.add(0,headers);
            return result;
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }
    
    public ArrayList<ArrayList<String>> getAllMatchesByPlayerID(String playerID,float headersLevel,float dataLevel)
    {
        try{
            String url= "https://euw.api.pvp.net/api/lol/euw/v2.2/matchlist/by-summoner/"+playerID+"?rankedQueues=RANKED_SOLO_5x5&seasons=PRESEASON2016,SEASON2016&api_key=";
            JSONObject obj= sendGet(url);
            if(obj==null)
                return null;
            ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
            ArrayList<String> headers=new ArrayList<String>();
            headers.addAll(asSortedList(getHeaders(headersLevel,obj)));
            result=getRows(obj,headers,dataLevel);
            result.add(0,headers);
            return result;
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }
    
    public ArrayList<ArrayList<String>> getMatchesInformationsByMatchID(String matchID,float headersLevel,float dataLevel,ArrayList<String> filters)
    {
        try{
            String[] region={"euw","eune","na","br","las","lan","oce","kr","jp","ru","tr"};
            int index=0;
            boolean tryDifferentRegion=false;
            JSONObject obj;
            do{
            String url= "https://"+region[index]+".api.pvp.net/api/lol/"+region[index]+"/v2.2/match/"+matchID+"?api_key=";
            obj= sendGet(url);
            if(obj==null)
                return null;
            if(obj.has("status")){
            if(String.valueOf(obj.getJSONObject("status").get("status_code")).equals("404")){
                tryDifferentRegion=true;
            }
            }else
                tryDifferentRegion=false;
            System.out.println(tryDifferentRegion);
            if(tryDifferentRegion){
                index++;
            }
            if(index>=region.length){
                System.exit(-1);
            }
            }while(tryDifferentRegion);      
            ArrayList<ArrayList<String>> result=new ArrayList<ArrayList<String>>();
            ArrayList<String> headers=new ArrayList<String>();
            if(filters==null){
            headers.addAll(asSortedList(getHeaders(headersLevel,obj)));
            }else
            headers=filters;
            result=getRows(obj,headers,dataLevel);
            result.add(0,headers);
            return result;
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }
}

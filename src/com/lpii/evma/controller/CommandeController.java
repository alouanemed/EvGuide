package com.lpii.evma.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
 
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.adapter.ServiceHandler;
import com.lpii.evma.model.EventPack;

public class CommandeController {
	public static String LastInsertId;
	public static String UnkUUID = "";
	public static String UnkcmdUUID = "";
	public static String CurrEvID = "";

	
	// HTTP POST request
		public void sendPost() throws Exception {
	 
			 
			 
			HttpClient client = new DefaultHttpClient();
			String encoded = URLEncoder.encode(EvmaApp.AddCmdUrl, "UTF-8");

			HttpPost post = new HttpPost(EvmaApp.AddCmdUrl);
	 
			// add header 
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
			urlParameters.add(new BasicNameValuePair("cn", ""));
			
			post.setHeader("Content-Type", "text/xml");
			post.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
			
	 
			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " );
			System.out.println("Post parameters : " + post.getEntity().getContentType());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
	 
			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));
	 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
				if (line.equals("-1")) {
					
				}else{
					LastInsertId = line;
				}
			}
	 
			System.out.println(result.toString());
	 
		
	 
		}
 
	
	public void postData(String pack_id,String pack_order_qty,String event_id) {
	    // Create a new HttpClient and Post Header
		CurrEvID = event_id;
		HttpClient httpclient = getThreadSafeClient();
	    HttpPost httppost = new HttpPost(EvmaApp.AddCmdUrl);
	    Calendar cal = Calendar.getInstance();
    	
    	
    	Date d_n = cal.getTime();;
    	cal.setTime(d_n);
    	int commande_dateTime_month = cal.get(Calendar.MONTH);
    	int commande_dateTime_day = cal.get(Calendar.DAY_OF_MONTH) + 1;
    	int commande_dateTime_year = cal.get(Calendar.YEAR);
    	int commande_dateTime_hour = cal.get(Calendar.HOUR_OF_DAY);
    	int commande_dateTime_min = cal.get(Calendar.MINUTE);
    	System.out.println("mohtn = " + commande_dateTime_month);

	    try {

	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      nameValuePairs.add(new BasicNameValuePair("commande_user_id", EvmaApp.CurrentUsernameID + ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_month", commande_dateTime_month + ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_day", commande_dateTime_day+ ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_year", commande_dateTime_year+ ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_hour", commande_dateTime_hour+ ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_min", commande_dateTime_min + ""));
	      //nameValuePairs.add(new BasicNameValuePair("commande_dateTime_min", commande_dateTime_min + ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_statut", "1"));
	      Long uuidcmd = UUID.randomUUID().getLeastSignificantBits();
	      UnkcmdUUID = "Evcmd"+commande_dateTime_year + "-"+ event_id + uuidcmd +"";
	      nameValuePairs.add(new BasicNameValuePair("commande_code", UnkcmdUUID ));
	      
	      //cmd detail
	      nameValuePairs.add(new BasicNameValuePair("cmddetail_Qty", pack_order_qty));
	      
	      //Billet
 	      for (int i = 0; i < Integer.valueOf(pack_order_qty); i++) {
		      Long uuid = UUID.randomUUID().getLeastSignificantBits();

		      UnkUUID += "EvMa" + uuid + "-"+ event_id +"MAROC";
 	      }
	      System.out.println(UnkUUID);
	      nameValuePairs.add(new BasicNameValuePair("Billet_pack_uuid",  UnkUUID ));  
	      nameValuePairs.add(new BasicNameValuePair("Billet_pack_id", pack_id));
 
	      
	      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	      HttpResponse response = httpclient.execute(httppost);
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	      String line = "";
	      while ((line = rd.readLine()) != null) {
	        System.out.println(line);
	        
	        

	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
  
	public void postDataCmdDetail() {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = getThreadSafeClient();
	    HttpPost httppost = new HttpPost(EvmaApp.AddCmdUrl);
	    Calendar cal = Calendar.getInstance();
    	
    	
    	Date d_n = cal.getTime();;
    	cal.setTime(d_n);
    	int commande_dateTime_month = cal.get(Calendar.MONTH);
    	int commande_dateTime_day = cal.get(Calendar.DAY_OF_MONTH) + 1;
    	int commande_dateTime_year = cal.get(Calendar.YEAR);
    	int commande_dateTime_hour = cal.get(Calendar.HOUR_OF_DAY);
    	int commande_dateTime_min = cal.get(Calendar.MINUTE);
    	System.out.println("mohtn = " + commande_dateTime_month);

	    try {

	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      nameValuePairs.add(new BasicNameValuePair("commande_user_id", EvmaApp.CurrentUsernameID + ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_month", commande_dateTime_month + ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_day", commande_dateTime_day+ ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_year", commande_dateTime_year+ ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_hour", commande_dateTime_hour+ ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_dateTime_min", commande_dateTime_min + ""));
	      //nameValuePairs.add(new BasicNameValuePair("commande_dateTime_min", commande_dateTime_min + ""));
	      nameValuePairs.add(new BasicNameValuePair("commande_statut", "1"));
	      nameValuePairs.add(new BasicNameValuePair("pack_id", "8"));
	      
	      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	      HttpResponse response = httpclient.execute(httppost);
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	      String line = "";
	      while ((line = rd.readLine()) != null) {
	        System.out.println(line);
	        
	        

	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
  
 
	
	public static DefaultHttpClient getThreadSafeClient()  {

	    DefaultHttpClient client = new DefaultHttpClient();
	    ClientConnectionManager mgr = client.getConnectionManager();
	    HttpParams params = client.getParams();
	    client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, 

	            mgr.getSchemeRegistry()), params);
	    return client;
	}
}



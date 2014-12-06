package com.lpii.evma.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.model.Event;
import com.lpii.evma.model.User;

import android.util.Log;

public class UserController {
	HttpPost httppost;
	StringBuffer buffer;	
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;	
	
	private static String TAG = "LoginController";
	
	
	public boolean loginEvuser(String url,String us,String pw){
		try{			
			 
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(url); // make sure the url is correct.
			//httppost.setHeader( "Content-Type", "application/json" );
			System.out.println("ksx");
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username",us));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("password",pw)); 
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = getThreadSafeClient().execute(httppost);
			
			System.out.println("ksx");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String responsex = httpclient.execute(httppost, responseHandler);
			
			System.out.println("ksqqq");
			Log.d(TAG ," Response : "+response  + " == <" +responsex);
			/*
			runOnUiThread(new Runnable() {
			    public void run() {
			    	tv.setText("Response from PHP : " + response);
					dialog.dismiss();
			    }
			});*/
			response.getEntity().consumeContent();
			if(responsex.equalsIgnoreCase("false")){
				EvmaApp.isError = true;
				return false;
			}else{

				
				JSONArray jsonArr;
				JSONArray jsonArrx;
				JSONObject jsonObj;
				System.out.println("response> " + responsex);
				// JSONArray("[{\"full_name\":\"Med\",\"user_id\":\"1\"},{\"full_name\":\"Med AL\",\"user_id\":\"10\"}]");
				 
 				jsonObj = new JSONObject(responsex.toString());

			try{	
				HashMap<String, String> pariticpant = new HashMap<String, String>();
				 
				System.out.println(jsonObj);
 
				 
				System.out.println(jsonObj);

				String user_id = jsonObj.get("user_id").toString();
				String username = jsonObj.get("username").toString();
				String password = jsonObj.get("password").toString();
				String role = jsonObj.get("role").toString();
				String full_name = jsonObj.get("full_name").toString();
				String email = jsonObj.get("email").toString();
				String d_created = jsonObj.get("d_created").toString();
				String d_modified = jsonObj.get("d_modified").toString();
				User m_Us = new User(user_id, username, password, role, full_name, email, d_created, d_modified);
				System.out.println(m_Us);
				EvmaApp.CurrentUser = m_Us;
				EvmaApp.isError = false;
				return true;


			} catch (JSONException e) {
				e.printStackTrace();
			}
				
				//showAlert();
				EvmaApp.isError = true;
				return false;
			}
			
		}catch(Exception e){
			Log.d(TAG ,"Exception : " + e.getMessage() + e);
		}
		
		return false;
	}

	
	public void AddUser(User us,String Action){
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = getThreadSafeClient();
	    HttpPost httppost = null;
	    if (Action.equals("AddUser")) {
	    	httppost = new HttpPost(EvmaApp.AddUserUrl);
		}else{
			httppost = new HttpPost(EvmaApp.EventEditUrl);
		}
	    
	    
    	System.out.println(us.toString());
	    String DateEv = us.getD_created();
	    String DateEvmodified = us.getD_modified();
	    
	    String str = "2014-06-09 10:23:56";
		String[] _dateSplitter = DateEv.split(" ");
		String[] eventDAteTimeTab = str.split("-");
		System.out.println(eventDAteTimeTab);
		String[] _time =_dateSplitter[1].split(":");
		System.out.println(_time);
	    
	    //String[] eventDAteTimeTab = DateEv.split("x");
 	    System.out.println(eventDAteTimeTab.toString()  +"  ;;");
 	    String meridian = null;
	    
		meridian = "am";

		meridian = "pm";
 
	    try {

	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      nameValuePairs.add(new BasicNameValuePair("User_password",us.getPassword()));
	      nameValuePairs.add(new BasicNameValuePair("User_username", us.getUsername()+ ""));
	      nameValuePairs.add(new BasicNameValuePair("User_name_user", us.getFull_name()+ ""));
	      nameValuePairs.add(new BasicNameValuePair("User_email", us.getEmail()+ ""));
	      nameValuePairs.add(new BasicNameValuePair("User_role", us.getRole()+ ""));
	      
	      nameValuePairs.add(new BasicNameValuePair("User_modified_DateTime_month", eventDAteTimeTab[1]));
	      nameValuePairs.add(new BasicNameValuePair("User_modified_DateTime_day", eventDAteTimeTab[0]+ ""));
	      nameValuePairs.add(new BasicNameValuePair("User_modified_DateTime_year", eventDAteTimeTab[2]+ ""));
	      nameValuePairs.add(new BasicNameValuePair("User_modified_DateTime_hour",_time[0]));
	      nameValuePairs.add(new BasicNameValuePair("User_modified_DateTime_min", _time[1]));
	      nameValuePairs.add(new BasicNameValuePair("User_modified_DateTime_meridian", meridian));

	      nameValuePairs.add(new BasicNameValuePair("User_created_DateTime_month", eventDAteTimeTab[1]));
	      nameValuePairs.add(new BasicNameValuePair("User_created_DateTime_day", eventDAteTimeTab[0]+ ""));
	      nameValuePairs.add(new BasicNameValuePair("User_created_DateTime_year", eventDAteTimeTab[2]+ ""));
	      nameValuePairs.add(new BasicNameValuePair("User_created_DateTime_hour",_time[0]));
	      nameValuePairs.add(new BasicNameValuePair("User_created_DateTime_min", _time[1]));
	      nameValuePairs.add(new BasicNameValuePair("User_created_DateTime_meridian", meridian));
	      
	      
	      
	      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	      HttpResponse response = httpclient.execute(httppost);
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	       
	      String line = "";
	      while ((line = rd.readLine()) != null) {
	        System.out.println(line);
	        if (!line.equals("error") && line.length() < 4) {
			    if (Action.equals("AddUser")) {
			    	EvmaApp.CurrentUsernameID = Integer.valueOf(line);
			    	EvmaApp.CurrentUsername = us.getUsername();
			    }
				EvmaApp.isError = false;
			}else{
				EvmaApp.isError = true;
			}
	         
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }		  
}


	private HttpClient getThreadSafeClient() {
		// TODO Auto-generated method stub
		DefaultHttpClient client = new DefaultHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		HttpParams params = client.getParams();
		client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,mgr.getSchemeRegistry()), params);
		return client;
	}
	
 

}

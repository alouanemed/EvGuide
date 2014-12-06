package com.lpii.evma.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lpii.evma.EvmaApp;

public class BilletController {
	
	ArrayList<HashMap<String, String>> qrCodeslist;
	ArrayList<HashMap<String, String>> SinglePackCodeslist;
	 

	ArrayList<HashMap<String, String>> usermyticketpacklist;
	ArrayList<HashMap<String, String>> xxusermyticketpacklist;
	public static int packqrcount;

	
	public  void uploadFile(String filePath, String fileName,String Event_iDx) {
		 	System.out.println("Event_iD  "   +Event_iDx + "  ");
		  InputStream inputStream;
		  try {
		    inputStream = new FileInputStream(new File(filePath));
		    byte[] data;
		    try {
		      data = IOUtils.toByteArray(inputStream);
		 
		      HttpClient httpClient = getThreadSafeClient();
		      HttpPost httpPost = new HttpPost(EvmaApp.SaveBilletPdf_URL);
		      File file = new File(filePath);

		      FileBody fileBody = new FileBody(file);   
 		 
		      MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
		      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		      builder.addPart("uploaded_file", fileBody);
		      builder.addTextBody("CurrentUsername", EvmaApp.CurrentUsername); 
		      builder.addTextBody("Event_iDx", Event_iDx); 

		      HttpEntity mEntity = builder.build();
		      
 
		      httpPost.setEntity(mEntity);
		 
		      HttpResponse httpResponse = httpClient.execute(httpPost);
		      BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

		      String line = "";
		      while ((line = rd.readLine()) != null) {
		        System.out.println(line);
		        
		        

		      }
		  
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  } catch (Exception e1) {
		    e1.printStackTrace();
		  }
		}
	
	public void getTicketsQRName(String evID){
		qrCodeslist = new ArrayList<HashMap<String,String>>();
		usermyticketpacklist = new ArrayList<HashMap<String,String>>();
		xxusermyticketpacklist = new ArrayList<HashMap<String,String>>();
		HttpClient httpclient = getThreadSafeClient();
		HttpPost httppost = new HttpPost(EvmaApp.getBilletIDbyPack_URL);
		String line = "";
		ForfaitController mforfaitcont = new ForfaitController();

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("evID",evID));
			nameValuePairs.add(new BasicNameValuePair("User_id",EvmaApp.CurrentUser.getUser_id()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			EvmaApp.mJsonData = "";
			while ((line = rd.readLine()) != null) {
				if (line != null || line != "null") {
					EvmaApp.mJsonData += line;
				}
			}
			
			JSONArray jsonArr;
 			JSONArray jsonArr2;
			JSONObject jsonObj;
			System.out.println(EvmaApp.mJsonData);
			try {
				jsonArr = new JSONArray(EvmaApp.mJsonData);
				if (jsonArr.length()>=0) {
					 packqrcount = jsonArr.length();
				}
				for (int i = 0; i < jsonArr.length(); i++) {
					HashMap<String, String> billet = new HashMap<String, String>();
					HashMap<String, String> xPack = new HashMap<String, String>();
					
					String billet_id = jsonArr.getString(i);
					System.out.println("splitt this=> " + billet_id);
					String[] Splitter = billet_id.split("-");
					String pack_Title = mforfaitcont.getsinglePack(Splitter[2]);
  					billet.put("billet_id", billet_id);
  					billet.put("pack_Title", pack_Title);
  					billet.put("pack_id", Splitter[2]);
  					xPack.put("pack_Title", pack_Title);
  					int count = 0;
//  					if (usermyticketpacklist.size() == 0) {
//						usermyticketpacklist.add(xPack);
//
// 					}
//					
// 					if (usermyticketpacklist.get(i).get("pack_Title").equals(xPack.get("pack_Title"))) {
//						System.out.println("equaaal");
//						count ++;
//						usermyticketpacklist.get(i).put("count_pack", count +"");
//	  					
//					}else{
//						System.out.println("NOOt equaaal");
//						xPack.put("count_pack", "1");
//						usermyticketpacklist.add(xPack);
//					}
					
					usermyticketpacklist.add(xPack);
   					qrCodeslist.add(billet);
  					

				}
				
				System.out.println("usermyticketpacklist"+usermyticketpacklist);
				Map<String, String> result = new TreeMap<String, String>();

 				for (int i = 0; i < usermyticketpacklist.size(); i++) {
 
					for (Entry<String, String> entry : usermyticketpacklist
							.get(i).entrySet()) {
						String value = entry.getValue();
						String count = result.get(value);
						if (count == null) {
							result.put(value, 1 + "");
 						} else {
							result.put(value, Integer.valueOf(count) + 1 + "");
 						}
					}
   					
				}
 					
				System.out.println("result"  + result  );
				Iterator<String> itx = result.keySet().iterator();
				while(itx.hasNext()){
					String el = itx.next();
					HashMap<String, String> billet = new HashMap<String, String>();
					billet.put("pack_Title",el);
 
					billet.put("pack_Count",result.get(el));
					xxusermyticketpacklist.add(billet);
				}
					
				System.out.println("xxusermyticketpacklist"+xxusermyticketpacklist);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getQRCodeBilletbyPackID(String pack_Title){
		SinglePackCodeslist = new ArrayList<HashMap<String,String>>();

		for (int i = 0; i < qrCodeslist.size(); i++) {
			Iterator<String> itx = qrCodeslist.get(i).keySet().iterator();
 				HashMap<String, String> billet = new HashMap<String, String>();
				String el = itx.next(); 
				System.out.println("qrCodeslist.get(i).get  " +qrCodeslist.get(i).get("pack_Title")  );
				if (qrCodeslist.get(i).get("pack_Title").equals(pack_Title)   ) {
					System.out.println("got it ");
					billet.put("pack_Title", pack_Title);
					billet.put("billet_id", qrCodeslist.get(i).get("billet_id"));
					SinglePackCodeslist.add(billet);
				}
 
		}
	}
	
	public ArrayList<HashMap<String, String>> getQrCodeslist() {
		return qrCodeslist;
	}

	public ArrayList<HashMap<String, String>> getSinglePackCodeslist() {
		return SinglePackCodeslist;
	}

	public void setSinglePackCodeslist(
			ArrayList<HashMap<String, String>> singlePackCodeslist) {
		SinglePackCodeslist = singlePackCodeslist;
	}

	public void setQrCodeslist(ArrayList<HashMap<String, String>> qrCodeslist) {
		this.qrCodeslist = qrCodeslist;
	}

	public ArrayList<HashMap<String, String>> getXxusermyticketpacklist() {
		return xxusermyticketpacklist;
	}

	public void setXxusermyticketpacklist(
			ArrayList<HashMap<String, String>> xxusermyticketpacklist) {
		this.xxusermyticketpacklist = xxusermyticketpacklist;
	}

	public ArrayList<HashMap<String, String>> getUsermyticketpacklist() {
		return usermyticketpacklist;
	}

	public void setUsermyticketpacklist(
			ArrayList<HashMap<String, String>> usermyticketpacklist) {
		this.usermyticketpacklist = usermyticketpacklist;
	}

	public static int getPackqrcount() {
		return packqrcount;
	}

	public static void setPackqrcount(int packqrcount) {
		BilletController.packqrcount = packqrcount;
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

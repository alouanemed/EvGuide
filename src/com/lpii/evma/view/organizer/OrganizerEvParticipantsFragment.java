package com.lpii.evma.view.organizer;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.model.EventPack;

public class OrganizerEvParticipantsFragment extends ListFragment {


	private ProgressDialog pDialog;
	EventsController mEventsController;
	Button mBtngetParticipants;
	ArrayList<HashMap<String, String>> SingleEventParticipantsList;
	ListAdapter adapter; 

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mEventsController = new  EventsController();
		SingleEventParticipantsList = new ArrayList<HashMap<String,String>>();
		
 		if (getActivity() != null) {
 			if (EvmaApp.GlobaleventParticipantList != null) {
 				adapter = new SimpleAdapter(getActivity(),
 						EvmaApp.GlobaleventParticipantList, R.layout.paritcipant_item,
 						new String[] {  
 								EventsController.TAG_user_idx ,
 								EventsController.TAG_full_name }, new int[] {
 								R.id.evAttendeeID,
 								R.id.TVattendeeName});
 				setListAdapter(adapter);

			}else{
				new GetParticipants().execute();
			}
 		}
			
 	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.organizer_event_participants,
				container, false);
				return rootView;
	}
 
	private class GetParticipants extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			EvmaApp.isReqFromOrga = true;
			System.out.println("LastEventID  " + EvmaApp.LastEventID);
			SingleEventParticipantsList.clear();	 
			mEventsController.sendRequestForParitcipantsArray(EvmaApp.LastEventID);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
 			JSONArray jsonArr;
 			JSONArray jsonArr2;
			JSONObject jsonObj;

			try {
				
		        System.out.println("x "+EvmaApp.mJsonData);
		        if (EvmaApp.mJsonData.isEmpty() || EvmaApp.mJsonData.length() <=1) {
					System.out.println("no Packs  !");
				}else{
		        //jsonArr = new JSONArray("[{\"full_name\":\"Med\",\"user_id\":\"1\"},{\"full_name\":\"Med AL\",\"user_id\":\"10\"}]");
			        jsonArr = new JSONArray(EvmaApp.mJsonData);
	  				for (int i = 0; i < jsonArr.length(); i++) {
	                	HashMap<String, String> pariticpant = new HashMap<String, String>();
	                
	                	jsonArr2 = jsonArr.getJSONArray(i);
	    				System.out.println(jsonArr2);
	
	
						String user_id = jsonArr2.getString(0);
						String full_name = jsonArr2.getString(1);
						  
						pariticpant.put(EventsController.TAG_user_idx, user_id);
						pariticpant.put(EventsController.TAG_full_name, full_name);
	
						SingleEventParticipantsList.add(pariticpant);
	                    
	
	                    System.out.println(SingleEventParticipantsList.toString());
	  				}	
	                }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			EvmaApp.GlobaleventParticipantList = SingleEventParticipantsList;
			if (getActivity() != null) {
				adapter = new SimpleAdapter(getActivity(),
						SingleEventParticipantsList, R.layout.paritcipant_item,
						new String[] {  
								EventsController.TAG_user_idx ,
								EventsController.TAG_full_name }, new int[] {
								R.id.evAttendeeID,
								R.id.TVattendeeName});
				setListAdapter(adapter);
			}
			 
				  

		}

	}
}

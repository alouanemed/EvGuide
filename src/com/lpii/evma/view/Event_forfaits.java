package com.lpii.evma.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.controller.cmddetailController;
import com.lpii.evma.model.Billet;
import com.lpii.evma.model.Cmddetail;
import com.lpii.evma.model.EventPack;

public class Event_forfaits extends ListActivity {

	TextView tvEvTitle;
	TextView tvEvDateTime;
	TextView tvEvOrg;
	 

	ArrayAdapter<String> listAdapter;
	ArrayList<HashMap<String, String>> eventList;
	private ProgressDialog pDialog;
	ForfaitController mForfaitController;
	ListView lvx;

	String EvTitle;
	String dateTime;
	String EvID;
	String Organisateur;
	OnItemClickListener myListViewClicked;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mForfaitController = new ForfaitController();
		Intent intEv = getIntent();
		EvID = intEv.getStringExtra("ExtraevID");
		  System.out.println("getting ^packss");

		new GetForfaitsXXL().execute();
		
		

		/*
		 * eventList = new ArrayList<HashMap<String, String>>(); HashMap<String,
		 * String> eventx = new HashMap<String, String>();
		 * eventx.put("forfait_Title", "Student"); eventx.put("forfait_price",
		 * "Gratuit"); eventx.put("forfait_tickets", "10/99");
		 * eventList.add(eventx); eventx = new HashMap<String, String>();
		 * eventx.put("forfait_Title", "VIP"); eventx.put("forfait_price",
		 * "100 DH"); eventx.put("forfait_tickets", "30/99");
		 * eventList.add(eventx);
		 * 
		 * ListAdapter adapter = new SimpleAdapter(this, eventList,
		 * R.layout.event_forfait_list_item, new String[] { "forfait_Title",
		 * "forfait_price", "forfait_tickets" }, new int[] {
		 * R.id.tvforfaitTitle, R.id.tvForfaitType, R.id.tvforfaittickets });
		 * System.out.println(adapter.getCount() + "  ==  " +
		 * adapter.getItem(0));
		 * 
		 * setListAdapter(adapter);
		 */

		myListViewClicked = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(Event_forfaits.this,
						"Clicked at positon = " + position, Toast.LENGTH_SHORT)
						.show();

			}
		};

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		final View v = inflater.inflate(R.layout.event_desc, container,false);
		 

		/*
		 * Intent getEvIntent = getActivity().getIntent(); EvTitle =
		 * getEvIntent.getStringExtra(EventsUI.TAG_NAME); dateTime =
		 * getEvIntent.getStringExtra(EventsUI.TAG_dateTime); EvID =
		 * getEvIntent.getStringExtra(EventsUI.TAG_ID); Organisateur =
		 * getEvIntent.getStringExtra("Organisateur");
		 * 
		 * 
		 * tvEvTitle.setText(EvTitle); tvEvDateTime.setText(dateTime);
		 * tvEvOrg.setText(Organisateur);
		 */
		// mainListView = (ListView) getActivity().findViewById(
		// R.id.ForfaitListView );
		return v;
	}

	private class GetForfaitsXXL extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Event_forfaits.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			mForfaitController.getForfaits();
			  
			return null;
		} 

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
/*
			System.out.println("lksdfh"); 
			if (pDialog.isShowing())
				pDialog.dismiss();
			mForfaitController.getForfaitListx(EvID);
			 
					ListAdapter adapter = new SimpleAdapter(Event_forfaits.this,
							mForfaitController.getSingleforfaitsEvent(),
							R.layout.event_forfait_list_item, new String[] {
									ForfaitController.TAG_pack_Title,
									ForfaitController.TAG_pack_Price,
									ForfaitController.TAG_pack_Qty,
									ForfaitController.TAG_pack_id, }, new int[] {
									R.id.tvforfaitTitle, R.id.tvForfaitType,
									R.id.tvforfaittickets, R.id.tv_event_forf_id });

					setListAdapter(adapter);
*/
		}

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		System.out.println("Click yaay :v");
		
		
		String EvForfaitiD = ((TextView) v.findViewById(R.id.tv_event_forf_id))
				.getText().toString();
		String EvForfaitPrice = ((TextView) v.findViewById(R.id.tvForfaitType))
				.getText().toString();
		String EvForfaitQty = ((TextView) v.findViewById(R.id.tvforfaittickets))
				.getText().toString();
		String EvForfaitTitle = ((TextView) v.findViewById(R.id.tvforfaitTitle))
				.getText().toString();

		EventPack evpk = mForfaitController.getSingleForfaitByID(EvForfaitiD,"getallofit");
		
		
		Intent in = new Intent(Event_forfaits.this, Event_Forfait_cmd.class);
		
		in.putExtra(ForfaitController.TAG_pack_id, EvForfaitiD);
		in.putExtra(ForfaitController.TAG_pack_Title, evpk.getTAG_pack_Title());
		in.putExtra(ForfaitController.TAG_pack_Qty, evpk.getTAG_pack_Qty());
		in.putExtra(ForfaitController.TAG_pack_Price, evpk.getTAG_pack_Price());
		in.putExtra(ForfaitController.TAG_pack_Description, evpk.getTAG_pack_Description());
		
		in.putExtra(EventsController.TAG_ID, EvID);
		
		startActivity(in);

	}
	
	
 

	 


}

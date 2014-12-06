package com.lpii.evma.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.model.Cmddetail;
import com.lpii.evma.model.EventPack;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class Event_Description_fragment extends ListFragment {

	TextView tvEvTitle;
	TextView tvEvDescription;
	TextView tvEvDateTime;
	TextView tvEvDateTimeFin;
	TextView tvEvOrg;
	TextView tvEviD;
	Button btnShowForfaits;
	ArrayAdapter<String> listAdapter;
	ArrayList<HashMap<String, String>> eventList;

	String EvTitle;
	String dateTime;
	String dateTimeFin;
	String Description;
	public static String EvID;
	String Organisateur;
	LinearLayout lynPacks;
	LinearLayout NoPacks;
	public static EventPack evpk;

 
 	ForfaitController mForfaitController;
	ListView lvx;
	OnItemClickListener myListViewClicked;
	int realQty;
	ArrayList<Cmddetail> mCmdTab;

	LinearLayout incPackLy;
	int CountAllPacks;
	Boolean isPackShowd = false;
	// /
	ListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getActivity().setContentView(R.layout.event_desc);
		getActivity().getActionBar().setDisplayShowTitleEnabled(false);
		Event_Forfait_cmd cmd = new Event_Forfait_cmd();
 		if (mForfaitController == null) {
			mForfaitController = new ForfaitController();
		}
		mCmdTab = new ArrayList<Cmddetail>();
		
		 if(getActivity() != null) {
			if ( EvmaApp.GlobaleventPackList== null) {
				System.out.println("it's emtpyyx");
				new GetForfaits().execute();
			} else {
				new GetForfaits().execute();
				
				CountAllPacks = EvmaApp.GlobaleventPackList
						.size();
				System.out.println(CountAllPacks + "  CountAllPacks");
			 
					System.out.println("GetForfaits it's Fullxxx");
					adapter = new SimpleAdapter(getActivity(),
							EvmaApp.GlobaleventPackList,
							R.layout.event_forfait_list_item, new String[] {
									ForfaitController.TAG_pack_Title,
									ForfaitController.TAG_pack_Price,
									ForfaitController.TAG_pack_Solde,
									ForfaitController.TAG_pack_Qty,
									ForfaitController.TAG_pack_id, },
							new int[] { R.id.tvforfaitTitle,
									R.id.tvForfaitType,
									R.id.tvforfaitSoldetickets,
									R.id.tvforfaittickets,
									R.id.tv_event_forf_id });
					setListAdapter(adapter);

				 
			}
			}	 

		
		if (adapter == null) {
			 
			System.out.println("null forafit");
			new GetForfaits().execute();
		}


	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.event_desc, container, false);
		tvEvTitle = (TextView) v.findViewById(R.id.tvEventTitle);
		tvEvDescription = (TextView) v.findViewById(R.id.tvEventDescriptionpage);
		tvEvDateTime = (TextView) v.findViewById(R.id.TveventDDebut);
		tvEvDateTimeFin = (TextView) v.findViewById(R.id.TveventDateFinTV);
		tvEvOrg = (TextView) v.findViewById(R.id._tvOrganisateur);
		tvEviD = (TextView) v.findViewById(R.id.event_desc_id);
		//btnShowForfaits = (Button) v.findViewById(R.id.AssisterForfaitBtn);
		lynPacks = (LinearLayout) v.findViewById(R.id.packshowly);
		NoPacks = (LinearLayout) v.findViewById(R.id.Nopackshowly);
		//incPackLy = (LinearLayout) v.findViewById(R.id.packsIncly);

//		btnShowForfaits.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				/*
//				 * Intent i = new Intent(getActivity(),Event_forfaits.class);
//				 * i.putExtra("ExtraevID", tvEviD.getText().toString());
//				 * startActivity(i);
//				 */
//				if (isPackShowd) {
//					lynPacks.setVisibility(LinearLayout.GONE);
//					isPackShowd = false;
//				} else {
//					isPackShowd = true;
//					lynPacks.setVisibility(LinearLayout.VISIBLE);
//					lynPacks.setLayoutParams(new LinearLayout.LayoutParams(
//							ViewGroup.LayoutParams.MATCH_PARENT,
//							CountAllPacks * 110));
//					adapter = new SimpleAdapter(getActivity(),
//							mForfaitController.getSingleforfaitsEvent(),
//							R.layout.event_forfait_list_item, new String[] {
//									ForfaitController.TAG_pack_Title,
//									ForfaitController.TAG_pack_Price,
//									ForfaitController.TAG_pack_Solde,
//									ForfaitController.TAG_pack_Qty,
//									ForfaitController.TAG_pack_id, },
//							new int[] { R.id.tvforfaitTitle,
//									R.id.tvForfaitType,
//									R.id.tvforfaitSoldetickets,
//									R.id.tvforfaittickets,
//									R.id.tv_event_forf_id });
//					setListAdapter(adapter);
//
//				}
//
//			}
//		});

		 
		if (CountAllPacks != 0) {
			lynPacks.setVisibility(LinearLayout.VISIBLE);
			NoPacks.setVisibility(LinearLayout.GONE);
		}else{
			NoPacks.setVisibility(LinearLayout.VISIBLE);
			lynPacks.setVisibility(LinearLayout.GONE);
			System.out.println("well its empty");
		}
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Roboto-Medium.ttf");
		tvEvTitle.setTypeface(tf);
		tvEvDateTime.setTypeface(tf);
		tvEvOrg.setTypeface(tf);
		tvEvDateTimeFin.setTypeface(tf);
		tvEvDescription.setTypeface(tf);
		
		Intent getEvIntent = getActivity().getIntent();
		EvTitle = getEvIntent.getStringExtra(EventsController.TAG_NAME);
		Description = getEvIntent.getStringExtra(EventsController.TAG_event_Description);
		dateTime = getEvIntent.getStringExtra(EventsController.TAG_dateTime);
		dateTimeFin = getEvIntent.getStringExtra(EventsController.TAG_dateTime_fin);
		Description = getEvIntent.getStringExtra(EventsController.TAG_event_Description);
		EvID = getEvIntent.getStringExtra(EventsController.TAG_ID);
		Organisateur = getEvIntent.getStringExtra("Organisateur");

		System.out.println("desc==>" + EvTitle);
		tvEvTitle.setText(EvTitle);
		tvEvDateTime.setText(dateTime);
		tvEvDateTimeFin.setText(dateTimeFin);
		tvEvDescription.setText(Description);
		tvEvOrg.setText(Organisateur);
		tvEviD.setText(EvID);

		// mainListView = (ListView) getActivity().findViewById(
		// R.id.ForfaitListView );
		return v;
	}

	private class GetForfaits extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			mForfaitController.getForfaits();
			mForfaitController.getForfaitListx(EvID);
 

			/* Get the Ticket and Calculate the sum of tickets
			
			 * try { for (int j = 0; j <
			 * mForfaitController.getSingleforfaitsEvent() .size(); j++) {
			 * System.out.println("get Billet of " +
			 * mForfaitController.getSingleforfaitsEvent()
			 * .get(j).get(ForfaitController.TAG_pack_id));
			 * 
			 * Billet billetx; ArrayList<Billet> mBillets = new
			 * ArrayList<Billet>();
			 * 
			 * 
			 * /* = mForfaitController.getBilletIDofPack(mForfaitController
			 * .getSingleforfaitsEvent() .get(j)
			 * .get((ForfaitController.TAG_pack_id)));
			 */
			/*
			 * Boolean itOverBillet = false; mBillets = mForfaitController
			 * .getBilletIDofPack(mForfaitController
			 * .getSingleforfaitsEvent().get(j)
			 * .get((ForfaitController.TAG_pack_id)));
			 * 
			 * for (int i = 0; i < mBillets.size(); i++) { billetx =
			 * mBillets.get(i);
			 * 
			 * if (billetx != null) { System.out.println(billetx.toString());
			 * cmddetailController mCmddetail = new cmddetailController();
			 * Cmddetail mCmdx = mCmddetail .getCmddetailofBillet(billetx
			 * .getBillet_cmddetail_id()); System.out.println(mCmdx.toString());
			 * mCmdTab.add(mCmdx); int counterQty = Integer.valueOf(mCmdx
			 * .getCmddetail_Qty()); realQty += counterQty; }else{ itOverBillet
			 * = true; } }
			 * 
			 * 
			 * 
			 * 
			 * 
			 * System.out.println("old val of qty " +
			 * mForfaitController.getSingleforfaitsEvent() .get(j)
			 * .get(mForfaitController.TAG_pack_Qty)); mForfaitController
			 * .getSingleforfaitsEvent() .get(j)
			 * .put(mForfaitController.TAG_pack_Qty, realQty + "/" +
			 * mForfaitController .getSingleforfaitsEvent() .get(j)
			 * .get(mForfaitController.TAG_pack_Qty));
			 * 
			 * }
			 * 
			 * } catch (Exception e) { e.printStackTrace(); }
			 */
			return null;
		}/*
		 * protected void onListItemClick(ListView list, View v, int position,
		 * long id) {
		 * 
		 * System.out.println("position " + position);
		 * Toast.makeText(Event_forfaits.this,
		 * getListView().getItemAtPosition(position).toString(),
		 * Toast.LENGTH_LONG).show();
		 * 
		 * System.out.println("clicked");
		 * 
		 * /*
		 * 
		 * String EvForfaitiD = ((TextView)
		 * v.findViewById(R.id.tv_event_forf_id)) .getText().toString(); String
		 * EvForfaitPrice = ((TextView) v.findViewById(R.id.tvForfaitType))
		 * .getText().toString(); String EvForfaitQty = ((TextView)
		 * v.findViewById(R.id.tvforfaittickets)) .getText().toString(); String
		 * EvForfaitTitle = ((TextView) v.findViewById(R.id.tvforfaitTitle))
		 * .getText().toString();
		 * 
		 * Intent in = new Intent(Event_forfaits.this,
		 * EventDescriptionUI.class); in.putExtra(ForfaitController.TAG_pack_id,
		 * EvForfaitiD); in.putExtra(ForfaitController.TAG_pack_Title,
		 * EvForfaitTitle); in.putExtra(ForfaitController.TAG_pack_Qty,
		 * EvForfaitQty); in.putExtra(ForfaitController.TAG_pack_Price,
		 * EvForfaitPrice); in.putExtra(EventsController.TAG_ID, EvID);
		 * 
		 * 
		 * 
		 * startActivity(in);
		 * 
		 * 
		 * }
		 */

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			 
			ArrayList<HashMap<String, String>> m3ForfaitsEvent;
			m3ForfaitsEvent = new ArrayList<HashMap<String, String>>();
			mForfaitController.getSingleforfaitsEvent();
//			for (int i = 0; i < mForfaitController.getSingleforfaitsEvent()
//					.size(); i++) {
//				if (i < 3) {
//					m3ForfaitsEvent.add(mForfaitController
//							.getSingleforfaitsEvent().get(i));
//				}
//			}
			
			if (m3ForfaitsEvent.isEmpty()) {
 				System.out.println("m3ForfaitsEvent isEmpty");
			}
			System.out.println("=======================");
			System.out.println(m3ForfaitsEvent);
			CountAllPacks =  mForfaitController.getSingleforfaitsEvent().size();
			System.out.println(CountAllPacks + "  CountAllPacks");
			EvmaApp.GlobaleventPackList =  mForfaitController.getSingleforfaitsEvent();
			if (CountAllPacks != 0) {
				lynPacks.setVisibility(LinearLayout.VISIBLE);
				NoPacks.setVisibility(LinearLayout.GONE);

				lynPacks.setLayoutParams(new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						CountAllPacks * 90));
				adapter = new SimpleAdapter(getActivity(), mForfaitController.getSingleforfaitsEvent(),
						R.layout.event_forfait_list_item, new String[] {
								ForfaitController.TAG_pack_Title,
								ForfaitController.TAG_pack_Price,
								ForfaitController.TAG_pack_Solde,
								ForfaitController.TAG_pack_Qty,
								ForfaitController.TAG_pack_id, }, new int[] {
								R.id.tvforfaitTitle,
								R.id.tvForfaitType,
								R.id.tvforfaitSoldetickets, 
								R.id.tvforfaittickets,
								R.id.tv_event_forf_id });
				setListAdapter(adapter);
			}else{
				NoPacks.setVisibility(LinearLayout.VISIBLE);
				lynPacks.setVisibility(LinearLayout.GONE);

				System.out.println("well its empty");
			}
		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		System.out.println("Click yaay :v"); 

		String EvForfaitiD = ((TextView) v.findViewById(R.id.tv_event_forf_id))
				.getText().toString();

		String Soldetickets = ((TextView) v
				.findViewById(R.id.tvforfaitSoldetickets)).getText().toString();

		String EvForfaitPrice = ((TextView) v.findViewById(R.id.tvForfaitType))
				.getText().toString();
		String EvForfaitQty = ((TextView) v.findViewById(R.id.tvforfaittickets))
				.getText().toString();
		String EvForfaitTitle = ((TextView) v.findViewById(R.id.tvforfaitTitle))
				.getText().toString();

		Crouton crouton;
		System.out.println("Soldetickets=>" + Soldetickets +  "EvForfaitQty==>" + EvForfaitQty);
		int Soldeticketsx = Integer.valueOf(Soldetickets);
		int EvForfaitQtyx = Integer.valueOf(EvForfaitQty);
		if (Soldetickets.equals(EvForfaitQty) ||  Soldeticketsx > EvForfaitQtyx ) {
			crouton = Crouton.makeText(getActivity(),
					":( Tous les billets de cet forfait sont vendus",
					de.keyboardsurfer.android.widget.crouton.Style.ALERT);
			crouton.show();
			return;

		}
		evpk = mForfaitController.getSingleForfaitByID(EvForfaitiD,
				"getAllofit");

		Intent in = new Intent(getActivity(), Event_Forfait_cmd.class);
		

		in.putExtra(ForfaitController.TAG_pack_id, EvForfaitiD);
		in.putExtra(ForfaitController.TAG_pack_Title, evpk.getTAG_pack_Title());
		in.putExtra(ForfaitController.TAG_pack_Qty, evpk.getTAG_pack_Qty());
		in.putExtra(ForfaitController.TAG_pack_Price, evpk.getTAG_pack_Price());
		in.putExtra(ForfaitController.TAG_pack_Description,
				evpk.getTAG_pack_Description());
		in.putExtra(ForfaitController.TAG_pack_fees, evpk.getTAG_pack_fees());
		in.putExtra(ForfaitController.TAG_pack_Solde, evpk.getTAG_pack_Solde());

		in.putExtra(EventsController.TAG_ID, EvID);
 		startActivity(in);

	}
	
	

}

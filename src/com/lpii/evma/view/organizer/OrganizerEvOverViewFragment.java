package com.lpii.evma.view.organizer;

import java.math.BigDecimal;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.view.MyTicketUpcomingEvents;


import android.support.v4.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class OrganizerEvOverViewFragment extends Fragment{

	TextView tvTitle;
	TextView tvEvdateTime;
	TextView tvOrgaEvTicketSoldex;
	TextView tvOrgaEvTicketPendingx;
	TextView tvOrgaEvTicketdispo;
	TextView mlblVendusValuex;
	TextView TvEvEarningsx;
	TextView TvTicketsPendingValuex;
	TextView mtvTickets_availableVal;
	
	
	EventsController  mEventsController;
	private ProgressDialog pDialog;
	String EvID;
	PieGraph pg;
	View rootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.organizer_ev_overview_fragment, container, false); 
   	 	pg  = (PieGraph)rootView.findViewById(R.id.piegraph);
       
        if (getActivity().getIntent() != null) {
        	Intent intenEv = getActivity().getIntent();
            mEventsController = new EventsController();
            
        	tvTitle = (TextView) rootView.findViewById(R.id.tvOrganizerEvTitle);
        	tvEvdateTime = (TextView) rootView.findViewById(R.id.tvOrganizerEvDate);
        	tvOrgaEvTicketSoldex = (TextView) rootView.findViewById(R.id.tvOrgaEvTicketSolde);
        	tvOrgaEvTicketPendingx = (TextView) rootView.findViewById(R.id.tvOrgaEvTicketPending);
        	tvOrgaEvTicketdispo = (TextView) rootView.findViewById(R.id.tvOrgaEvTicketAvail);
        	TvEvEarningsx = (TextView) rootView.findViewById(R.id.TvEvEarnings);
        	mlblVendusValuex = (TextView) rootView.findViewById(R.id.lblVendusValue);
        	TvTicketsPendingValuex = (TextView) rootView.findViewById(R.id.TvTicketsPendingValue);
        	mtvTickets_availableVal = (TextView) rootView.findViewById(R.id.tvTickets_availableVal); 
            
            String EvTitle = intenEv.getStringExtra(EventsController.TAG_NAME);
        	String EvdateTime = intenEv.getStringExtra(EventsController.TAG_dateTime);
        	EvID = intenEv.getStringExtra(EventsController.TAG_ID);
        	tvTitle.setText(EvTitle);
        	tvEvdateTime.setText(EvdateTime);
        	
		}
        OrganizerEvParticipantsFragment   pariticpants = new OrganizerEvParticipantsFragment();
		getFragmentManager().beginTransaction().replace(R.id.fragment_container_pariticpant, pariticpants).commit();
         new GetSingleEventPacks().execute();
        
		 
        return rootView;
    }
	private class GetSingleEventPacks extends AsyncTask<Void, Void, Void> {

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
			mEventsController.getSingleEvent(EvID);
			  
			return null;
		} 

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (pDialog.isShowing())
				pDialog.dismiss();
			System.out.println("lksdfh"); 
			float pSolde = 0;
			float pQty = 0;
			float pEarnings = 0;
			float pPrice = 0;
			float vrx = 0 ;
			float percnt ;
			float disponible ;
			float pPendingVAL = 9;	        
			float pPending;
			 
			BigDecimal result1x = new BigDecimal(0);
			BigDecimal resultpPending = new BigDecimal(0);
			BigDecimal result1 = new BigDecimal(0);;
			BigDecimal Roundeddisponible = new BigDecimal(0);;
			
			for (int i = 0; i < mEventsController.getSingleEventPAckList().size(); i++) {
				pSolde += Integer.valueOf(mEventsController.getSingleEventPAckList().get(i).get(ForfaitController.TAG_pack_Solde));
				pQty  += Integer.valueOf(mEventsController.getSingleEventPAckList().get(i).get(ForfaitController.TAG_pack_Qty));
				pPrice += Double.valueOf(mEventsController.getSingleEventPAckList().get(i).get(ForfaitController.TAG_pack_Price));
				
			}
			if (mEventsController.getSingleEventPAckList().size() > 0) {
				System.out.println(pSolde +"/"+ pQty);
				
				percnt =(float) ( pSolde / pQty ) * 100;
				result1x =round(percnt,2);
		        
				System.out.println("percnt=>" + result1x  +" =<wsolde " + pSolde);

				//
				vrx = pQty - pSolde; 
				disponible =(float) (( vrx / pQty ) * 100);
		        result1=round(disponible,2);
		        Roundeddisponible = result1;
		        
		        
		        //
		        vrx = pQty - pPendingVAL;
		        pPending = (float) ( pPendingVAL / pQty ) * 100;
				resultpPending =round(pPending,2);
				pEarnings = pPrice * pSolde;
				
				TvEvEarningsx.setText(pEarnings + " MAD");
				tvOrgaEvTicketSoldex.setText( result1x.intValue() +" %");
				tvOrgaEvTicketPendingx.setText(resultpPending.intValue()+" %");
				tvOrgaEvTicketdispo.setText(Roundeddisponible.intValue()+" %");
				
				mlblVendusValuex.setText( (int)pSolde + "");
				TvTicketsPendingValuex.setText((int)pPendingVAL + "");
				mtvTickets_availableVal.setText((int)(pQty - pSolde) + "");
				System.out.println(resultpPending +" || " + Roundeddisponible +" || " +result1x);
			} 
			 
			
			
	         
			PieSlice slice = new PieSlice();
			slice.setColor(Color.parseColor("#99CC00"));
			slice.setValue(result1x.floatValue());
			pg.addSlice(slice);
			slice = new PieSlice();
			slice.setColor(Color.parseColor("#FFBB33"));
			slice.setValue(resultpPending.floatValue());
			pg.addSlice(slice);
			slice = new PieSlice();
			slice.setColor(Color.parseColor("#AA66CC"));
			slice.setValue(Roundeddisponible.floatValue());
			pg.addSlice(slice);	
			
			pg.setOnSliceClickedListener(new OnSliceClickedListener(){

				@Override
				public void onClick(int index) {
					
				}
				
			});
 
		}

	}
 
	
	public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd;
    }

	 


}


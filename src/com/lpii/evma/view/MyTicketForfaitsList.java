package com.lpii.evma.view;
 

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lowagie.text.Image;
import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.BilletController;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.model.BilletAdapter;
import com.lpii.evma.model.BilletPackAdapter;
import com.lpii.evma.model.Event;

public class MyTicketForfaitsList extends ListActivity{

 

	ImageView mimageview_barcode_image;
	Button mbtnDownloadTicket;

	String pack_id;
	ImageView BilletQrCode;
	Bitmap bitmap;
	Image imgQRCODEBilletCode = null;
	String root;
	String fnames[];
	TextView tvPackTitle;  
	// Stirngs
	String Event_id;
	ArrayList<String> qrCodesURL;

	int serverResponseCode = 0;
	ProgressDialog dialog = null;
	BilletController mBilletController;
	Event ev;
 
	String upLoadServerUri = null;

	final String uploadFilePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Evmax/";

	int CurrentIndex =0 ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_ticket_single_pack); 
		tvPackTitle = (TextView) findViewById(R.id.tvEventPacktitle);
 		qrCodesURL = new ArrayList<String>();
 		Intent orderDatasIntent = getIntent();
 		

		mBilletController = new BilletController();
		EvmaApp.CurrBilletController = mBilletController;
 		if (orderDatasIntent != null) {
			Event_id = orderDatasIntent.getStringExtra(EventsController.TAG_ID);
			tvPackTitle.setText( orderDatasIntent.getStringExtra(EventsController.TAG_NAME));
 //			textview_barcode_EvTitle.setText( orderDatasIntent.getStringExtra(EventsController.TAG_NAME));
			 
		} 
		//getQRName();
 		new  getBillets().execute();

	    getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
 
		 
	}
 
	 
		@Override
		public void onListItemClick(ListView list, View v, int position, long id) {
 
			String packTitle = ((TextView) v.findViewById(R.id.tvmyticketpackTitle))
					.getText().toString();
	 
			System.out.println("===> "+ packTitle);
			Intent in = new Intent(MyTicketForfaitsList.this, MyTicketQRCodes.class);
 			//in.putExtra(EventsController.TAG_dateTime, dateTime);
			in.putExtra(ForfaitController.TAG_pack_Title,packTitle);
 			in.putExtra("Organisateur", EventsController.getUsernameOrga());
			 

			startActivity(in);

		}


	private class getBillets extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
            dialog = ProgressDialog.show(MyTicketForfaitsList.this, "", "Tssena...", true);

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			EvmaApp.isReqFromOrga = true;
			System.out.println("LastEventID  " + EvmaApp.LastEventID);
			mBilletController.getTicketsQRName(EvmaApp.LastEventID);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			Typeface tfBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
			ListAdapter adapterx = new BilletPackAdapter(MyTicketForfaitsList.this, mBilletController.getXxusermyticketpacklist(), tfBold, mBilletController);
			setListAdapter(adapterx);
			EvmaApp.CurrBilletController=  mBilletController;
			 
		}

	}
}
  
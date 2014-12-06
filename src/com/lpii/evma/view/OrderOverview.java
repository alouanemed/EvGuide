package com.lpii.evma.view;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;
import com.lpii.evma.controller.CommandeController;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.model.Commande;
import com.lpii.evma.model.EventPack;

import de.keyboardsurfer.android.widget.crouton.Crouton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OrderOverview extends Activity implements OnClickListener {

	TextView TvOrderQty;
	TextView TvOrderUnitPrice;
	TextView tvOrderFees;
	TextView tvOrderttl;
	TextView tvCoupon;
	Button  btnfreeORderCOnfirm;
	Button btnCc;
	Button btnSMS;
	Button btnPaypal;

 	Commande forfaitCmd;
	Date DateNow = Calendar.getInstance().getTime();
	CommandeController mCmdController;
	ForfaitController mForfaitController;

	private ProgressDialog pDialog;
	public static String pack_id, pack_order_qty;
	EventPack evpk;
	public static String ev_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ordermainlayout);

		TvOrderQty = (TextView) findViewById(R.id.TvOrderQty);
		TvOrderUnitPrice = (TextView) findViewById(R.id.TvOrderUnitPrice);
		tvOrderFees = (TextView) findViewById(R.id.tvOrderFees);
		tvOrderttl = (TextView) findViewById(R.id.tvOrderttl);
		tvCoupon = (TextView) findViewById(R.id.tvCoupon);
		btnPaypal = (Button) findViewById(R.id.btnOrderPaypal);
		btnfreeORderCOnfirm = (Button) findViewById(R.id.btnOrderFreeConfirm);
		btnCc = (Button) findViewById(R.id.btnOrderCc);
		btnSMS = (Button) findViewById(R.id.btnOrderSMS);
		btnPaypal.setOnClickListener(this);
		btnfreeORderCOnfirm.setOnClickListener(this);

		Intent intorder = getIntent();
		mForfaitController = new ForfaitController();
		mCmdController = new CommandeController();

		pack_id = intorder.getStringExtra(ForfaitController.TAG_pack_id);
		pack_order_qty = intorder
				.getStringExtra(ForfaitController.TAG_pack_Qty);

		ev_id = intorder
				.getStringExtra(EventsController.TAG_ID);

		evpk = Event_Description_fragment.evpk;
		System.out.println(evpk.toString());

		String pkPrice = evpk.getTAG_pack_Price().replaceAll("\\D+", "");
		System.out.println(pkPrice);

		TvOrderQty.setText(pack_order_qty);
		TvOrderUnitPrice.setText(evpk.getTAG_pack_Price());
		intorder.getStringExtra(ForfaitController.TAG_pack_Description);
		tvOrderFees.setText(evpk.getTAG_pack_fees());
		if (pkPrice.isEmpty()) {
			pkPrice = "0";
		}
		Double Ordertotal = Integer.valueOf(pack_order_qty)
				* Double.valueOf(pkPrice)
				+ Double.valueOf(evpk.getTAG_pack_fees());
		tvOrderttl.setText(Ordertotal + " MAD");
		if (Ordertotal == 0) { 
			btnPaypal.setVisibility(Button.GONE);
			btnCc.setVisibility(Button.GONE);
			btnSMS.setVisibility(Button.GONE);
			btnfreeORderCOnfirm.setVisibility(Button.VISIBLE);
 		}
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		tvCoupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btnPaypal.getId()) {
			System.out.println("datenow ==> " + DateNow.toString() + "");
			Format df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String d_now = df.format(DateNow);

			System.out.println("dat===>" + d_now);

			forfaitCmd = new Commande("", EvmaApp.CurrentUsernameID,d_now + "", "1");
			System.out.println("cmd ==> " + forfaitCmd.toString());
			new AddCommande().execute();
			
		}else if (v.getId() == btnfreeORderCOnfirm.getId()) {
			System.out.println("datenow ==> " + DateNow.toString() + "");
			Format df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String d_now = df.format(DateNow);

			System.out.println("dat===>" + d_now);

			forfaitCmd = new Commande("", EvmaApp.CurrentUsernameID,d_now + "", "1");
			System.out.println("cmd ==> " + forfaitCmd.toString());
			new AddCommande().execute();
		}

	}

	private class AddCommande extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(OrderOverview.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				mCmdController.postData(pack_id, pack_order_qty,evpk.getTAG_pack_event_id());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			}
			System.out.println("donnne");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			Crouton crouton;
			if (EvmaApp.isError) {
				crouton = Crouton.makeText(OrderOverview.this,
						"une erreur est survenue lors la creation du commande",
						de.keyboardsurfer.android.widget.crouton.Style.ALERT);
			} else {

				crouton = Crouton.makeText(OrderOverview.this,
						"Vous y serez! Nous avons Saved une place pour vous.",
						de.keyboardsurfer.android.widget.crouton.Style.CONFIRM);

			}
			crouton.show();
			Intent intx = new Intent(OrderOverview.this,OrderBilletOverview.class);
			intx.putExtra("uuids", CommandeController.UnkUUID);
			intx.putExtra("pack_id",pack_id);
			//evpk
			startActivity(intx);

		}

	}

}

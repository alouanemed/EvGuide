package com.lpii.evma.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.adapter.ServiceHandler;
import com.lpii.evma.model.Billet;
import com.lpii.evma.model.Cmddetail;
import com.lpii.evma.model.EventPack;

public class cmddetailController {

	String str;

	JSONArray BilletCmdDetail = null;
	ArrayList<HashMap<String, String>> forfaitsEventlist;
	ArrayList<HashMap<String, String>> SingleforfaitsEvent;
	ArrayList<HashMap<String, String>> singleforfaitsEvent;
	HashMap<String, EventPack> eventPacksMap;

	String TAG_cmddetail_id = "cmddetail_id";
	String TAG_cmddetail_Qty = "cmddetail_Qty";
	String TAG_cmddetail_commande_id = "cmddetail_commande_id";

	public cmddetailController() {
		str = "test";
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	// get Cmd Detail Object for a pack
	public Cmddetail getCmddetailofBillet(String billetcmddetail_id) {
		System.out.println("mddetail");
		Cmddetail mCmdDetail = null;

		ServiceHandler shxs = new ServiceHandler();

		String SinglePackURL = String.format(EvmaApp.Single_CMD_Detail_URL,
				billetcmddetail_id);

		System.out.println(SinglePackURL + " mddetail uRL");

		String jsonStr = shxs
				.makeServiceCall(SinglePackURL, ServiceHandler.GET);

		Log.d("URL: ", "> " + jsonStr);

		if (jsonStr != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				System.out.println(jsonObj.toString());
				JSONObject jsonObjCmdDet = new JSONObject();
				jsonObjCmdDet = jsonObj.getJSONObject("cmddetail");
				JSONObject jsonObjCmdDetx = jsonObjCmdDet.getJSONObject("Cmddetail");

				System.out.println("===> " + jsonObjCmdDetx.toString());

				HashMap<String, String> BilletVar = new HashMap<String, String>();
				eventPacksMap = new HashMap<String, EventPack>();

				JSONObject mJsonBillet =jsonObjCmdDetx;
				System.out.println(mJsonBillet.toString());
				String cmddetail_id = mJsonBillet.getString(TAG_cmddetail_id);
				String cmd_Qty = mJsonBillet.getString(TAG_cmddetail_Qty);
				String cmddetail_commande_id = mJsonBillet
						.getString(TAG_cmddetail_commande_id);

				mCmdDetail = new Cmddetail(billetcmddetail_id, cmd_Qty,
						cmddetail_commande_id);

				// System.out.println("billet done  ==> " + mBillet.toString());

			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}
		return mCmdDetail;

	}

}

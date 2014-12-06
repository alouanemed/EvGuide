package com.lpii.evma.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import android.app.ProgressDialog;
import android.util.Log;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.adapter.ServiceHandler;
import com.lpii.evma.model.Event;
import com.lpii.evma.model.EventPack;
import com.lpii.evma.view.organizer.Organizer_event_create;

public class EventsController {

	private ProgressDialog pDialog;
	JSONArray events = null;
	JSONArray eventpacks = null;
	ArrayList<HashMap<String, String>> eventList;
	HashMap<String, EventPack> eventPacksMap;
	ArrayList<HashMap<String, String>> SingleEventPAckList;
	ArrayList<HashMap<String, String>> SingleUserEventsPariticpationsC;
	ArrayList<HashMap<String, String>> SingleUserEventsPariticpationsP;

	//
	public static String TAG_ID = "event_id";
	public static final String TAG_NAME = "event_Title";
	public static final String TAG_user_id = "event_user_id";
	public static final String TAG_dateTime = "event_DateTime";
	public static final String TAG_dateTime_fin = "event_DateTime_fin";
	public static final String TAG_dateTime_month = "event_dateTime_month";
	public static final String TAG_dateTime_month_fin = "event_dateTime_month_fin";
	public static final String TAG_event_MinPrice = "event_MinPrice";
	public static final String TAG_dateTime_day = "event_dateTime_day";
	public static final String TAG_dateTime_day_fin = "event_dateTime_day_fin";
	public static final String TAG_visible = "event_visible";
	public static final String TAG_event_Statut = "event_Statut";
	public static final String TAG_event_Description = "event_Description";
	public static final String TAG_event_cover = "event_cover";

	public static final String TAG_pack_Title = "pack_Title";
	public static final String TAG_pack_Description = "pack_Description";
	public static final String TAG_pack_Debut_Vente_Date = "pack_Debut_Vente_Date";
	public static final String TAG_pack_Fin_Vente_Date = "pack_Fin_Vente_Date";
	public static final String TAG_pack_isVisible = "pack_isVisible";
	public static final String TAG_pack_Price = "pack_Price";
	public static final String TAG_pack_Qty = "pack_Qty";
	public static final String TAG_pack_event_id = "pack_event_id";
	public static final String TAG_CurrenUSer = "Organisateur";
	public static final String TAG_full_name = "full_name";
	public static final String TAG_user_idx = "user_id";

	public static String UsernameOrga = EvmaApp.CurrentOrganizer;
	public static JSONObject PackJson;

	public ArrayList<HashMap<String, String>> getSingleUserEventsPariticpationsC() {
		return SingleUserEventsPariticpationsC;
	}

	public void setSingleUserEventsPariticpationsC(
			ArrayList<HashMap<String, String>> singleUserEventsPariticpationsC) {
		this.SingleUserEventsPariticpationsC = singleUserEventsPariticpationsC;
	}

	public ArrayList<HashMap<String, String>> getSingleUserEventsPariticpationsP() {
		return SingleUserEventsPariticpationsP;
	}

	public void setSingleUserEventsPariticpationsP(
			ArrayList<HashMap<String, String>> singleUserEventsPariticpationsP) {
		this.SingleUserEventsPariticpationsP = singleUserEventsPariticpationsP;
	}

	public ArrayList<HashMap<String, String>> getEventList() {

		return eventList;
	}

	public Event getEventById(String ID) {
		Event ev = null;
		System.out.println("ev ID  GlobaleventList" + ID + "   "
				+ EvmaApp.GlobaleventList.size());
		for (int i = 0; i < EvmaApp.GlobaleventList.size(); i++) {

			// System.out.println(EvmaApp.GlobaleventList.get(i));
			if (EvmaApp.GlobaleventList.get(i).get(TAG_ID).equals(ID)) {
				System.out.println("got it");
				int EvIDx = Integer.valueOf(EvmaApp.GlobaleventList.get(i).get(
						TAG_ID));
				int ser_id = Integer.valueOf(EvmaApp.GlobaleventList.get(i)
						.get(TAG_user_id));
				boolean isVisible = Boolean.valueOf(EvmaApp.GlobaleventList
						.get(i).get(TAG_visible));
				String dateTime = EvmaApp.GlobaleventList.get(i).get(
						TAG_dateTime);
				String dateTimeFin = EvmaApp.GlobaleventList.get(i).get(
						TAG_dateTime_fin);
				String event_Statut = EvmaApp.GlobaleventList.get(i).get(
						TAG_event_Statut);
				String event_Description = EvmaApp.GlobaleventList.get(i).get(
						TAG_event_Description);
				String event_cover = EvmaApp.GlobaleventList.get(i).get(
						TAG_event_cover);

				ev = new Event(EvIDx, EvmaApp.GlobaleventList.get(i).get(
						TAG_NAME), ser_id, dateTime, dateTimeFin, event_Statut,
						event_Description, event_cover, isVisible);

				System.out.println(EvmaApp.GlobaleventList.get(i).get(TAG_ID));
			}
		}
		return ev;

	}

	public void getEvents() {
		eventList = new ArrayList<HashMap<String, String>>();
		ServiceHandler sh = new ServiceHandler();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(EvmaApp.EventsUrl,
				ServiceHandler.GET);

		Log.d("Response: ", "> " + jsonStr);
		int i = 0;
		if (jsonStr != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);

				// Getting JSON Array node
				events = jsonObj.getJSONArray("events");

				SingleEventPAckList = new ArrayList<HashMap<String, String>>();

				for (i = 0; i < events.length(); i++) {

					HashMap<String, String> eventx = new HashMap<String, String>();
					eventPacksMap = new HashMap<String, EventPack>();

					JSONObject even = events.getJSONObject(i);
					JSONObject Eventv = even.getJSONObject("Event");
					JSONObject UserJson = even.getJSONObject("user");
					JSONObject EventPacks = even.getJSONObject("Event");

					eventpacks = even.getJSONArray("packEvents");
					System.out.println("===> " + eventpacks.toString());

					for (int j = 0; j < eventpacks.length(); j++) {

						HashMap<String, String> forfaitx = new HashMap<String, String>();

						JSONObject mPack = eventpacks.getJSONObject(j);
						// System.out.println(mPack.toString());

						String pack_Price = mPack.getString(TAG_pack_Price);
						String pack_Qty = mPack.getString(TAG_pack_Qty);
						String pack_event_id = mPack
								.getString(TAG_pack_event_id);
						String pack_id = mPack
								.getString(ForfaitController.TAG_pack_id);
						String pack_Solde = mPack
								.getString(ForfaitController.TAG_pack_Solde);
						String pack_statut = mPack
								.getString(ForfaitController.TAG_pack_Statut_Pack);

						EventPack evpack = new EventPack("", pack_id, "", "",
								"", "", pack_Price, pack_Qty, pack_event_id,
								"", pack_Solde, pack_statut);

						forfaitx.put(TAG_pack_Price, evpack.getTAG_pack_Price());
						forfaitx.put(TAG_pack_Qty, evpack.getTAG_pack_Qty());
						forfaitx.put(TAG_pack_event_id,
								evpack.getTAG_pack_event_id());
						forfaitx.put(ForfaitController.TAG_pack_id,
								evpack.getTAG_pack_id());
						forfaitx.put(ForfaitController.TAG_pack_Solde,
								evpack.getTAG_pack_Solde());
						// System.out.println(forfaitx.toString());
						SingleEventPAckList.add(forfaitx);
						;

					}
					System.out.println("Done getting fofrfait "
							+ SingleEventPAckList.size());
					// System.out.println(SingleEventPAckList);

					int Min = 0;
					for (int j = 0; j < SingleEventPAckList.size(); j++) {
						if (Min == 0
								|| Integer.valueOf(SingleEventPAckList.get(j)
										.get(TAG_pack_Price)) < Min) {
							Min = Integer.valueOf(SingleEventPAckList.get(j)
									.get(TAG_pack_Price));
						}

					}
					SingleEventPAckList.clear();

					String eventID = Eventv.getString(TAG_ID);
					String title = Eventv.getString(TAG_NAME);
					String user_id = Eventv.getString(TAG_user_id);
					String dateTime = Eventv.getString(TAG_dateTime);
					String dateTimeFin = Eventv.getString(TAG_dateTime_fin);
					String event_Statut = Eventv.getString(TAG_event_Statut);
					String event_Description = Eventv
							.getString(TAG_event_Description);
					String visible = Eventv.getString(TAG_visible);
					String event_cover = Eventv.getString(TAG_event_cover);
					UsernameOrga = UserJson.getString("username");

					Event ev = new Event(Integer.parseInt(eventID), title,
							Integer.parseInt(user_id), dateTime, dateTimeFin,
							event_Statut, event_Description, event_cover,
							Boolean.parseBoolean(visible));

					// eventList.add(object);

					eventx.put(TAG_CurrenUSer, EvmaApp.CurrentUsername);
					eventx.put(TAG_ID, ev.getEventID() + "");
					eventx.put(TAG_NAME, ev.getTitle());
					eventx.put(TAG_user_id, ev.getUser_id() + "");
					eventx.put(TAG_dateTime_fin, ev.getDateTimeFin());
					eventx.put(TAG_event_Description, ev.getEvent_Description());
					eventx.put(TAG_dateTime, ev.getDateTime());
					eventx.put(TAG_event_cover, ev.getEvent_cover());
					eventx.put(TAG_event_Statut, ev.getEvent_Statut());

					Date date = null;
					try {
						date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(ev.getDateTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String MinPrice = Min + " MAD";
					String MonthName = new SimpleDateFormat("MMM").format(date);
					String Daynum = new SimpleDateFormat("dd").format(date);
					System.out.println("============");
					System.out.println(MinPrice + "============MinPrice");
					System.out.println("============");
					eventx.put(TAG_dateTime_day, Daynum);
					eventx.put(TAG_dateTime_month, MonthName);
					eventx.put(TAG_event_MinPrice, MinPrice);
					eventx.put("username", UsernameOrga);
					eventList.add(eventx);

					// System.out.println("eventpackmap  " +
					// eventPacksMap.toString());

					// System.out.println(eventList.toString());

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}

		System.out.println("Numner of iteration  : " + i);
	}

	public void getOrgaEvents(String Organisateur) {
		eventList = new ArrayList<HashMap<String, String>>();
		ServiceHandler sh = new ServiceHandler();

		String SinglePackURL = String.format(EvmaApp.User_Events_Url,
				Organisateur);
		System.out.println(SinglePackURL);
		String jsonStr = sh.makeServiceCall(SinglePackURL, ServiceHandler.GET);

		Log.d("Response: ", "> " + jsonStr);

		if (jsonStr != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				JSONObject mUser = jsonObj.getJSONObject("user");
				events = mUser.getJSONArray("UserEvents");

				for (int i = 0; i < events.length(); i++) {

					HashMap<String, String> eventx = new HashMap<String, String>();
					eventPacksMap = new HashMap<String, EventPack>();

					JSONObject Eventv = events.getJSONObject(i);
					// System.out.println(even.toString());
					// JSONObject Eventv = even.getJSONObject("Event");
					// JSONObject UserJson = even.getJSONObject("user");

					String eventID = Eventv.getString(TAG_ID);
					String title = Eventv.getString(TAG_NAME);
					String user_id = Eventv.getString(TAG_user_id);
					String dateTime = Eventv.getString(TAG_dateTime);
					String visible = Eventv.getString(TAG_visible);
					// UsernameOrga = UserJson.getString("username");

					String dateTimeFin = Eventv.getString(TAG_dateTime_fin);
					String event_Statut = Eventv.getString(TAG_event_Statut);
					String event_Description = Eventv
							.getString(TAG_event_Description);
					String event_cover = Eventv.getString(TAG_event_cover);

					Event ev = new Event(Integer.parseInt(eventID), title,
							Integer.parseInt(user_id), dateTime, dateTimeFin,
							event_Statut, event_Description, event_cover,
							Boolean.parseBoolean(visible));

					// eventList.add(object);

					eventx.put(TAG_CurrenUSer, EvmaApp.CurrentUsername);
					eventx.put(TAG_ID, ev.getEventID() + "");
					eventx.put(TAG_NAME, ev.getTitle());
					eventx.put(TAG_dateTime, ev.getDateTime());
					eventx.put(TAG_dateTime_fin, ev.getDateTimeFin());
					eventx.put(TAG_visible, ev.getVisible() + "");
					eventx.put(TAG_event_Description, ev.getEvent_Description() + "");
					eventx.put(TAG_event_cover, ev.getEvent_cover() + "");
					eventx.put("username", UsernameOrga);
					eventList.add(eventx);

					System.out.println(eventList.toString());
					// Phone node is JSON Object
					// /JSONObject phone = c.getJSONObject("");
					// String mobile = phone.getString(TAG_PHONE_MOBILE);
					/*
					 * String home = phone.getString(TAG_PHONE_HOME); String
					 * office = phone.getString(TAG_PHONE_OFFICE);
					 * 
					 * // tmp hashmap for single contact HashMap<String, String>
					 * contact = new HashMap<String, String>();
					 * 
					 * // adding each child node to HashMap key => value
					 * contact.put(TAG_ID, id); contact.put(TAG_NAME, name);
					 * contact.put(TAG_EMAIL, email);
					 * contact.put(TAG_PHONE_MOBILE, mobile);
					 * 
					 * // adding contact to contact list eventList.add(contact);
					 */
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}
	}

	// get User events
	public void getUserMyTickets(String userID) {
		SingleUserEventsPariticpationsC = new ArrayList<HashMap<String, String>>();
		SingleUserEventsPariticpationsP = new ArrayList<HashMap<String, String>>();
		ServiceHandler sh = new ServiceHandler();

		String SinglePackURL = String.format(EvmaApp.User_myTicket_events_Url);
		System.out.println(SinglePackURL);
		String jsonStr = sh.makeServiceCall(SinglePackURL, ServiceHandler.POST);

		Log.d("Response: ", "> " + jsonStr);

		if (jsonStr != null) {
			try {

				JSONArray jsonArr;
				JSONArray jsonArrx;
				JSONObject jsonObj;
				System.out.println("x " + EvmaApp.mJsonData);

				// jsonArr = new
				// JSONArray("[{\"full_name\":\"Med\",\"user_id\":\"1\"},{\"full_name\":\"Med AL\",\"user_id\":\"10\"}]");
				jsonArr = new JSONArray(jsonStr);

				for (int i = 0; i < jsonArr.length(); i++) {

					HashMap<String, String> pariticpant = new HashMap<String, String>();

					jsonArrx = jsonArr.getJSONArray(i);
					System.out.println(jsonArrx);

					String EventTitle = jsonArrx.get(0).toString();
					String EventDateTime = jsonArrx.get(1).toString();
					String EventDateTimeFin = jsonArrx.get(2).toString();
					String Event_id = jsonArrx.get(3).toString();

					Date d_Debut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(EventDateTime);

					String MonthName = new SimpleDateFormat("MMM")
							.format(d_Debut);
					String Daynum = new SimpleDateFormat("dd").format(d_Debut);

					pariticpant.put(EventsController.TAG_ID, Event_id);
					pariticpant.put(EventsController.TAG_dateTime,
							EventDateTime);
					pariticpant.put(EventsController.TAG_NAME, EventTitle);
					pariticpant.put(EventsController.TAG_dateTime_day, Daynum);
					pariticpant.put(EventsController.TAG_dateTime_month,
							MonthName);
					Date d_Fin = null;
					Date DateNow = Calendar.getInstance().getTime();

					try {
						d_Fin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(EventDateTimeFin);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (d_Fin.compareTo(DateNow) > 0) {
						SingleUserEventsPariticpationsC.add(pariticpant);
					} else if (DateNow.compareTo(d_Fin) > 0) {
						SingleUserEventsPariticpationsP.add(pariticpant);
					}

					System.out.println(SingleUserEventsPariticpationsC
							.toString());
					System.out.println(SingleUserEventsPariticpationsP
							.toString());

				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}
	}

	public static String getUsernameOrga() {
		return UsernameOrga;
	}

	public ArrayList<HashMap<String, String>> getSingleEventPAckList() {
		return SingleEventPAckList;
	}

	public void setSingleEventPAckList(
			ArrayList<HashMap<String, String>> singleEventPAckList) {
		SingleEventPAckList = singleEventPAckList;
	}

	public static void setUsernameOrga(String usernameOrga) {
		UsernameOrga = usernameOrga;
	}

	public void getSingleEvent(String EvID) {
		System.out.println("lets get single event packs");
		eventList = new ArrayList<HashMap<String, String>>();
		SingleEventPAckList = new ArrayList<HashMap<String, String>>();
		ServiceHandler sh = new ServiceHandler();

		String SingleEvURL = String.format(EvmaApp.Single_EVENT_URL, EvID);
		System.out.println(SingleEvURL + "   URLL");
		String jsonStr = sh.makeServiceCall(SingleEvURL, ServiceHandler.GET);

		if (jsonStr != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);

				JSONObject jsonObjEvent = jsonObj.getJSONObject("event");
				// events = jsonObj.getJSONArray("event");
				eventpacks = jsonObjEvent.getJSONArray("packEvents");
				System.out.println("===> " + eventpacks.toString());

				// looping through All Contacts
				for (int i = 0; i < eventpacks.length(); i++) {

					HashMap<String, String> forfaitx = new HashMap<String, String>();

					JSONObject mPack = eventpacks.getJSONObject(i);
					// JSONObject UserJson = even.getJSONObject("user");
					System.out.println("===");
					System.out.println(mPack.toString());

					String pack_title = mPack.getString(TAG_pack_Title);
					String pack_Description = mPack
							.getString(TAG_pack_Description);
					String pack_Debut_Vente_Date = mPack
							.getString(TAG_pack_Debut_Vente_Date);
					String pack_Fin_Vente_Date = mPack
							.getString(TAG_pack_Fin_Vente_Date);
					String pack_isVisible = mPack.getString(TAG_pack_isVisible);
					String pack_Price = mPack.getString(TAG_pack_Price);
					String pack_Qty = mPack.getString(TAG_pack_Qty);
					String pack_event_id = mPack.getString(TAG_pack_event_id);
					String pack_id = mPack
							.getString(ForfaitController.TAG_pack_id);
					String pack_fees = mPack
							.getString(ForfaitController.TAG_pack_fees);
					String pack_Solde = mPack
							.getString(ForfaitController.TAG_pack_Solde);
					String pack_statut = mPack
							.getString(ForfaitController.TAG_pack_Statut_Pack);

					EventPack evpack = new EventPack(pack_title, pack_id,
							pack_Description, pack_Debut_Vente_Date,
							pack_Fin_Vente_Date, pack_isVisible, pack_Price,
							pack_Qty, pack_event_id, pack_fees, pack_Solde,
							pack_statut);

					forfaitx.put(TAG_pack_Title, evpack.getTAG_pack_Title());
					forfaitx.put(TAG_pack_Description,
							evpack.getTAG_pack_Description());
					forfaitx.put(TAG_pack_Debut_Vente_Date,
							evpack.getTAG_pack_Debut_Vente_Date());
					forfaitx.put(TAG_pack_Fin_Vente_Date,
							evpack.getTAG_pack_Fin_Vente_Date());
					forfaitx.put(TAG_pack_Description,
							evpack.getTAG_pack_Description());
					forfaitx.put(TAG_pack_isVisible,
							evpack.getTAG_pack_isVisible());
					forfaitx.put(TAG_pack_Price, evpack.getTAG_pack_Price());
					forfaitx.put(TAG_pack_Qty, evpack.getTAG_pack_Qty());
					forfaitx.put(TAG_pack_event_id,
							evpack.getTAG_pack_event_id());
					forfaitx.put(ForfaitController.TAG_pack_id,
							evpack.getTAG_pack_id());
					forfaitx.put(ForfaitController.TAG_pack_fees,
							evpack.getTAG_pack_fees());
					forfaitx.put(ForfaitController.TAG_pack_Solde,
							evpack.getTAG_pack_Solde());

					SingleEventPAckList.add(forfaitx);

					System.out.println(SingleEventPAckList.toString());

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.e("ServiceHandler", "Couldn't get any data from the url");
		}
	}

	public void AddEvent(Event ev, String Action) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = getThreadSafeClient();
		HttpPost httppost = null;
		if (Action.equals("AddEvent")) {
			httppost = new HttpPost(EvmaApp.getEventAddUrl);
		} else {
			httppost = new HttpPost(EvmaApp.EventEditUrl);
		}

		System.out.println(ev.toString());
		String DateEv = ev.getDateTime();
		String[] eventDAteTimeTab = DateEv.split("x");
		System.out.println(eventDAteTimeTab.toString() + "  ;;");
		String evIsString = ev.getVisible() ? "1" : "0";
		String meridian = null;
		if (Integer.valueOf(eventDAteTimeTab[3]) >= 0
				&& Integer.valueOf(eventDAteTimeTab[3]) <= 12) {
			meridian = "am";
		} else if (Integer.valueOf(eventDAteTimeTab[3]) > 12
				&& Integer.valueOf(eventDAteTimeTab[3]) <= 24) {
			meridian = "pm";
		}
		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("event_user_id",
					EvmaApp.CurrentUsernameID + ""));
			nameValuePairs.add(new BasicNameValuePair("event_Title", ev
					.getTitle() + ""));
			nameValuePairs.add(new BasicNameValuePair("event_id", ev
					.getEventID() + ""));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_month",
					eventDAteTimeTab[1]));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_day",
					eventDAteTimeTab[0] + ""));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_year",
					eventDAteTimeTab[2] + ""));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_hour",
					eventDAteTimeTab[3]));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_min",
					eventDAteTimeTab[4]));

			String DateEvFIN = ev.getDateTime();
			eventDAteTimeTab = DateEvFIN.split("x");

			nameValuePairs.add(new BasicNameValuePair(
					"event_DateTime_fin_month", eventDAteTimeTab[1]));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_fin_day",
					eventDAteTimeTab[0] + ""));
			nameValuePairs.add(new BasicNameValuePair(
					"event_DateTime_fin_year", eventDAteTimeTab[2] + ""));
			nameValuePairs.add(new BasicNameValuePair(
					"event_DateTime_fin_hour", eventDAteTimeTab[3]));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_fin_min",
					eventDAteTimeTab[4]));

			nameValuePairs.add(new BasicNameValuePair(
					"event_DateTime_meridian", meridian));
			nameValuePairs.add(new BasicNameValuePair("event_visible",
					evIsString + ""));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				if (!line.equals("error") && line.length() < 4) {
					if (Action.equals("AddEvent")) {
						EvmaApp.LastEventID = line;
					}
					EvmaApp.isError = false;
				} else {
					EvmaApp.isError = true;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void AddEvnetAndUpload(String filePath, String fileName, Event ev,
			String Action) {

		InputStream inputStream;
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		HttpClient httpClient = getThreadSafeClient();
		HttpPost httpPost = null;
		try {
			if (!fileName.isEmpty()) {
				inputStream = new FileInputStream(new File(filePath));
				byte[] data;
				try {
					data = IOUtils.toByteArray(inputStream);

					if (Action.equals("AddEvent")) {
						httpPost = new HttpPost(EvmaApp.getEventAddUrl);
					} else {
						httpPost = new HttpPost(EvmaApp.EventEditUrl);
					}
					File file = new File(filePath);
					FileBody fileBody = new FileBody(file);
					builder.addPart("uploaded_file", fileBody);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// /
			System.out.println("lets send this : " + ev);
			String DateEv = ev.getDateTime();
			String[] eventDAteTimeTab = DateEv.split("x");
			System.out.println(eventDAteTimeTab.toString() + "  ;;");
			String evIsString = ev.getVisible() ? "1" : "0";
			String meridian = null;
			if (Integer.valueOf(eventDAteTimeTab[3]) >= 0
					&& Integer.valueOf(eventDAteTimeTab[3]) <= 12) {
				meridian = "am";
			} else if (Integer.valueOf(eventDAteTimeTab[3]) > 12
					&& Integer.valueOf(eventDAteTimeTab[3]) <= 24) {
				meridian = "pm";
			}

			builder.addTextBody("event_user_id", EvmaApp.CurrentUsernameID + "");
			builder.addTextBody("event_Title", ev.getTitle() + "");
			builder.addTextBody("event_Description", ev.getEvent_Description()
					+ "");
			builder.addTextBody("event_id", ev.getEventID() + "");
			builder.addTextBody("event_DateTime_month", eventDAteTimeTab[1]);
			builder.addTextBody("event_DateTime_day", eventDAteTimeTab[0] + "");
			builder.addTextBody("event_DateTime_year", eventDAteTimeTab[2] + "");
			builder.addTextBody("event_DateTime_hour", eventDAteTimeTab[3]);
			builder.addTextBody("event_DateTime_min", eventDAteTimeTab[4]);

			String DateEvFIN = ev.getDateTimeFin();
			eventDAteTimeTab = DateEvFIN.split("x");

			builder.addTextBody("event_DateTime_fin_month", eventDAteTimeTab[1]);
			builder.addTextBody("CurrentUsername", EvmaApp.CurrentUsername);
			builder.addTextBody("event_DateTime_fin_day", eventDAteTimeTab[0]
					+ "");
			builder.addTextBody("event_DateTime_fin_year", eventDAteTimeTab[2]
					+ "");
			builder.addTextBody("event_DateTime_fin_hour", eventDAteTimeTab[3]);
			builder.addTextBody("event_DateTime_fin_min", eventDAteTimeTab[4]);
			builder.addTextBody("event_DateTime_meridian", meridian);
			builder.addTextBody("event_visible", evIsString + "");
			String evStatut = "";
			if (ev.getEvent_Statut().isEmpty()) {
				evStatut = "1";
			} else {
				evStatut = ev.getEvent_Statut();
			}
			builder.addTextBody("event_Statut", evStatut);

			builder.addTextBody("CurrentUsername", EvmaApp.CurrentUsername);

			HttpEntity mEntity = builder.build();

			httpPost.setEntity(mEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				if (!line.equals("error") && line.length() < 4) {
					if (Action.equals("AddEvent")) {
						EvmaApp.LastEventID = line;
					}
					EvmaApp.isError = false;
				} else {
					EvmaApp.isError = true;
				}

			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// Update existant event

	public void EditEvent(Event ev) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = getThreadSafeClient();
		HttpPost httppost = new HttpPost(EvmaApp.EventEditUrl);

		String DateEv = ev.getDateTime();
		String[] eventDAteTimeTab = DateEv.split("x");
		System.out.println(eventDAteTimeTab.toString() + "  ;;");
		String evIsString = ev.getVisible() ? "1" : "0";
		String meridian = null;
		if (Integer.valueOf(eventDAteTimeTab[3]) >= 0
				&& Integer.valueOf(eventDAteTimeTab[3]) <= 12) {
			meridian = "am";
		} else if (Integer.valueOf(eventDAteTimeTab[3]) > 12
				&& Integer.valueOf(eventDAteTimeTab[3]) <= 24) {
			meridian = "pm";
		}
		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("event_user_id",
					EvmaApp.CurrentUsernameID + ""));
			nameValuePairs.add(new BasicNameValuePair("event_Title", ev
					.getTitle() + ""));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_month",
					eventDAteTimeTab[1]));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_day",
					eventDAteTimeTab[0] + ""));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_year",
					eventDAteTimeTab[2] + ""));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_hour",
					eventDAteTimeTab[3]));
			nameValuePairs.add(new BasicNameValuePair("event_DateTime_min",
					eventDAteTimeTab[4]));
			nameValuePairs.add(new BasicNameValuePair(
					"event_DateTime_meridian", meridian));
			nameValuePairs.add(new BasicNameValuePair("event_visible",
					evIsString + ""));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				if (!line.equals("error")) {
					EvmaApp.LastEventID = line;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getParticipants(Event ev) {

	}

	public void sendRequestForParitcipantsArray(String pack_event_id) {
		HttpClient httpclient = getThreadSafeClient();
		HttpPost httppost = new HttpPost(EvmaApp.getEvParticipantsUrl);
		String line = "";

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("pack_event_id",
					pack_event_id));

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

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static DefaultHttpClient getThreadSafeClient() {

		DefaultHttpClient client = new DefaultHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		HttpParams params = client.getParams();
		client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,

		mgr.getSchemeRegistry()), params);
		return client;
	}

}

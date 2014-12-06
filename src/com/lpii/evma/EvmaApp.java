package com.lpii.evma;
import java.util.ArrayList;
import java.util.HashMap;

import com.lpii.evma.controller.BilletController;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.model.Event;
import com.lpii.evma.model.User;

import android.app.Application;
import android.view.MenuItem;


public class EvmaApp extends Application {
	
	public static final String TAG = "Evma";
	
	public static final String MainDirUrl = "http://192.168.1.20:80/Evma";
	//public static final String MainDirUrl = "http://169.254.205.112:80/Evma";


	public static String LoginUrl = MainDirUrl + "/users/api_loginx";

	public static String EventCoverUrl = MainDirUrl + "/uploads/Events/covers/";

	public static String UserTicketsURL = MainDirUrl + "/uploads/Tickets/%s/";

	public static String AddCmdUrl = MainDirUrl + "/commandes/apiadd";

	public static String AddUserUrl = MainDirUrl + "/users/addapi";

	
	public static String getCmdidUrl = MainDirUrl + "/commandes/apiadd";
	
	public static String getEventAddUrl = MainDirUrl + "/events/apiadd";
	
	public static String EventEditUrl = MainDirUrl + "/events/apiedit";
	
	public static String getEvParticipantsUrl = MainDirUrl + "/events/getParticipantsV2";
	
	public static String EventsUrl = MainDirUrl + "/rest_events.json";
	
	public static String ForfaitAddUrl = MainDirUrl + "/packs/apiadd";
	
	public static String ForfaitEditUrl = MainDirUrl + "/packs/apiedit";
	
	public static String User_Events_Url = MainDirUrl + "/users/view/%s.json";
	
	public static String User_myTicket_events_Url = MainDirUrl + "/users/getEventsAttendedByUserV2";
	
	public static final String Single_EVENT_URL = MainDirUrl + "/events/view/%s.json";
	
	public static final String Single_PACK_URL = MainDirUrl + "/Packs/view/%s.json";
	
	public static final String Single_CMD_Detail_URL = MainDirUrl + "/cmddetails/view/%s.json";
	
	public static final String ALL_PACK_URL = MainDirUrl + "/Packs.json";
	
	public static final String SaveBilletPdf_URL = MainDirUrl + "/billets/SaveBilletPdf";

	public static final String getBilletIDbyPack_URL = MainDirUrl + "/billets/getBilletIDbyPack";

	public static final String 	getUserBilletsSingleEvent_URL = MainDirUrl + "/billets/getUserBilletsSingleEvent";

	
	public static BilletController CurrBilletController;

 	
	public static String CurrentUserEmail  = "flahweb@gmail.com";
	
	public static User CurrentUser  ;



	public static String CurrentUsername = "";
	
	public static String CurrentOrganizer = "adm1";

	public static String helperpicturePath = "";

	public static Event helperNewEv ;
	
	public static Boolean MyTicketsReq = false;

	public static Boolean helperIAMGETTIHPIC = false;

	public static Boolean MyTicketPast = false;

 
	
	public static int CurrentUsernameID = 1;
	
	public static int EventID = 0;
	
	public static String LastEventID = "";
	
	public static Boolean isError = false;
	
	public static Boolean isPacksShowed = false;
	
	public static Boolean isReqFromOrga = false;
	
	public static double DefaultPourcentage  = 0.05;
	
	public static ForfaitController CurrentmForfaitController;

	public static EventsController CurrentmEventsController;
	
	public static Boolean forceGetEvent = false;

	

	public static MenuItem refreshMenuItemx;

	public static String mJsonData ;
	public static String CurrentEvOrganizerPacks = "";

	
	//
	

	public static ArrayList<HashMap<String, String>>  GlobaleUserPariticpationsEventList;
	public static ArrayList<HashMap<String, String>>  GlobaleUserPariticpationsEventListP;
	public static ArrayList<HashMap<String, String>>  GlobaleventList;
	public static ArrayList<HashMap<String, String>>  GlobaleOrgaventList;
	public static ArrayList<HashMap<String, String>>  GlobaleventPackList;
	public static ArrayList<HashMap<String, String>>  GlobaleventParticipantList;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if (helperNewEv == null) {
			helperNewEv = new Event(0, "", this.CurrentUsernameID, "", "", "", "", "", false);
		}
		
		System.out.println("onCreate called" +  helperNewEv.toString());
		if (GlobaleUserPariticpationsEventList != null) {
			GlobaleUserPariticpationsEventList.clear();
		}
		
		if (GlobaleUserPariticpationsEventListP != null) {
			GlobaleUserPariticpationsEventListP.clear();
		}
		
		if (GlobaleventList != null) {
			GlobaleventList.clear();
		}
		
		if (GlobaleventPackList != null) {
			GlobaleventPackList.clear();
		}
		
		if (GlobaleventParticipantList != null) {
			GlobaleventParticipantList.clear();
		}
		
		

		GlobaleUserPariticpationsEventList = null;
		GlobaleUserPariticpationsEventListP = null;
		GlobaleventList = null;
		GlobaleventPackList = null;
		GlobaleventParticipantList = null; 
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		System.out.println("Terminate");
		GlobaleUserPariticpationsEventList.clear();
		GlobaleUserPariticpationsEventList = null;
		
		GlobaleUserPariticpationsEventListP.clear();
		GlobaleUserPariticpationsEventListP = null;
		
		GlobaleventList.clear();
		GlobaleventList = null;
		GlobaleventPackList.clear();
		GlobaleventPackList = null;
		GlobaleventParticipantList.clear();
		GlobaleventParticipantList = null; 
		
	}
	
	
	
	

}

package com.lpii.evma.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable { 

	
	public Event(){
		
	}
	
	private int EventID;
	private String Title; 
	private int user_id;
	private String DateTime;
	private String DateTimeFin;
	private String event_Statut;
	private String event_Description;
	private String event_cover;
	private Boolean visible;
	 
	public Event(int eventID, String title, int user_id, String dateTime,
			String dateTimeFin, String event_Statut, String event_Description,
			String event_cover, Boolean visible) {
		super();
		EventID = eventID;
		Title = title;
		this.user_id = user_id;
		DateTime = dateTime;
		DateTimeFin = dateTimeFin;
		this.event_Statut = event_Statut;
		this.event_Description = event_Description;
		this.event_cover = event_cover;
		this.visible = visible;
	}
	public int getEventID() {
		return EventID;
	}
	public void setEventID(int eventID) {
		EventID = eventID;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	public String getDateTimeFin() {
		return DateTimeFin;
	}
	public void setDateTimeFin(String dateTimeFin) {
		DateTimeFin = dateTimeFin;
	}
	public String getEvent_Statut() {
		return event_Statut;
	}
	public void setEvent_Statut(String event_Statut) {
		this.event_Statut = event_Statut;
	}
	public String getEvent_Description() {
		return event_Description;
	}
	public void setEvent_Description(String event_Description) {
		this.event_Description = event_Description;
	}
	public String getEvent_cover() {
		return event_cover;
	}
	public void setEvent_cover(String event_cover) {
		this.event_cover = event_cover;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	@Override
	public String toString() {
		return "Event [EventID=" + EventID + ", Title=" + Title + ", user_id="
				+ user_id + ", DateTime=" + DateTime + ", DateTimeFin="
				+ DateTimeFin + ", event_Statut=" + event_Statut
				+ ", event_Description=" + event_Description + ", event_cover="
				+ event_cover + ", visible=" + visible + "]";
	}
	 
	
	public String toStringx() {
		return EventID + ","+Title + ","
				+ user_id + "," + DateTime + ","
				+ DateTimeFin + ", event_Statut=" + event_Statut
				+ "," + event_Description + ","
				+ event_cover + "," + visible ;
	}

	public static final Parcelable.Creator<Event> CREATOR = new Creator<Event>() {
		public Event createFromParcel(Parcel source) {
			source.readParcelable(Event.class.getClassLoader());
 			Event mEv = new Event();
			mEv.EventID = Integer.valueOf(source.readString());
			mEv.Title = source.readString();
			mEv.user_id = Integer.valueOf(source.readString());
			mEv.DateTime = source.readString();
			mEv.DateTimeFin = source.readString();
			mEv.event_Statut = source.readString();
			mEv.event_Description = source.readString();
			mEv.event_cover = source.readString();
			mEv.visible = source.readByte() != 0;
 			return mEv;
		}

		public Event[] newArray(int size) {
			return new Event[size];
		}
	};
 
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	 
	 
	
	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		// TODO Auto-generated method stub
		parcel.writeInt(EventID); 
 

		parcel.writeString(Title);
		parcel.writeInt(user_id);
		parcel.writeString(DateTime);
		parcel.writeString(DateTimeFin);
		parcel.writeString(event_Statut);
		parcel.writeString(event_Description);
		parcel.writeString(event_cover);
 		parcel.writeByte((byte) (visible ? 1 : 0));    
 

	}
	 
	
	

}

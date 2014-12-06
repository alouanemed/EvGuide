

package com.lpii.evma.view.organizer;

import java.util.Calendar;

import ua.org.zasadnyy.zvalidations.Field;
import ua.org.zasadnyy.zvalidations.Form;
import ua.org.zasadnyy.zvalidations.TextViewValidationFailedRenderer;
import ua.org.zasadnyy.zvalidations.validations.NotEmpty;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.CroutonValidationFailedRenderer;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.model.Event;
import com.lpii.evma.view.FloatLabelEditText;

public class Organizer_event_create extends Fragment {

	Button btnNextEvent;
	View rootView;	
	LayoutInflater mInflater;
	ViewGroup mContainer;
	TextView etOrgaTime;
	TextView etEventDate;
	TextView etOrgaTimeFIN;
	TextView etEventDateFIN;
	FloatLabelEditText etEventTitle;
	FloatLabelEditText etEventDescription;
	CheckBox CBisVisible;
	private ProgressDialog pDialog;
	EventsController  mEventsController;
	String EvDate,EvTime;
	String EvDateFIN,EvTimeFIN;
	String EvCover;
	Event ev;
	public String EvID;
	 
	ImageView OrgaImg;
    private static int RESULT_LOAD_IMAGE = 99;

	// form for validation of event
	private Form mForm;
    

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		//EvmaApp =  (EvmaApp) getActivity().getApplication();
    
		if (EvmaApp.helperNewEv == null) {
			System.out.println("null Bro !");
			EvmaApp.helperNewEv = new Event();
		}
		System.out.println("x " + EvmaApp.helperNewEv );
		mInflater = inflater;
		mContainer = container;
		rootView = mInflater.inflate(R.layout.organizer_event_create, container, false);
		mEventsController = new EventsController();
		
		btnNextEvent = (Button) rootView.findViewById(R.id.EventCreateFinish);
		
		etEventTitle = (FloatLabelEditText) rootView.findViewById(R.id.etOrgaEventTitle);
		etEventDescription = (FloatLabelEditText) rootView.findViewById(R.id.etOrgaEvDescription); 
		etEventDate = (TextView) rootView.findViewById(R.id.etOrgaDate);
		etEventDateFIN = (TextView) rootView.findViewById(R.id.etOrgaDateFIN);
		etOrgaTime = (TextView) rootView.findViewById(R.id.etOrgaEvTime);
		etOrgaTimeFIN = (TextView) rootView.findViewById(R.id.etOrgaEvTimeFIN);
        OrgaImg = (ImageView) rootView.findViewById(R.id.OrgaimgThumbnail);
		CBisVisible = (CheckBox) rootView.findViewById(R.id.EventIsVisible);

		
		CBisVisible.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
 
				EvmaApp.helperNewEv.setVisible(arg1);
			}
		});
        System.out.println("vmaApp.helperNewEv.getTitle() " + EvmaApp.helperNewEv.getTitle());
        if (!EvmaApp.helperNewEv.getTitle().isEmpty()) {
        	etEventTitle.getEditText().setText(EvmaApp.helperNewEv.getTitle());
		}
        if (!EvmaApp.helperNewEv.getEvent_Description().isEmpty()) {
        	etEventDescription.getEditText().setText(EvmaApp.helperNewEv.getEvent_Description());
		}
        if (!EvmaApp.helperNewEv.getDateTime().isEmpty()) {
        	String[] splitter= EvmaApp.helperNewEv.getDateTime().split("x");
        	System.out.println(splitter[0]  +"  " +  splitter[1]) ;
        	etEventDate.setText(splitter[0]+"/"+splitter[1]+"/"+splitter[2]);
        	etOrgaTime.setText(splitter[3]+":"+splitter[4]);
		}
        if (!EvmaApp.helperNewEv.getDateTimeFin().isEmpty()) {
        	String[] splitter= EvmaApp.helperNewEv.getDateTimeFin().split("x");
        	etEventDateFIN.setText(splitter[0]+"/"+splitter[1]+"/"+splitter[2]);
        	etOrgaTimeFIN.setText(splitter[3]+":"+splitter[4]);
		}
        



        
        if (!EvmaApp.helperpicturePath.isEmpty()) {
        	System.out.println("picturePath == >"  + EvmaApp.helperpicturePath);
        	OrgaImg.setImageBitmap(BitmapFactory.decodeFile(EvmaApp.helperpicturePath));
		}
        OrgaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	System.out.println("LCicked");
            	if (EvDate != null && EvTime != null ) {
                	EvmaApp.helperNewEv.setDateTime(EvDate+EvTime);
				}
            	if (EvDateFIN != null && EvTimeFIN != null ) {
                 	EvmaApp.helperNewEv.setDateTimeFin(EvDateFIN+EvTimeFIN);
				}
            	if (etEventTitle != null && !etEventTitle.getText().toString().isEmpty()   ) {
                 	EvmaApp.helperNewEv.setTitle(etEventTitle.getText().toString());
				}
            	if (etEventDescription != null && !etEventDescription.getText().toString().isEmpty()  ) {
                 	EvmaApp.helperNewEv.setEvent_Description(etEventDescription.getText().toString());
				}
         		Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         		 
                Bundle extras = new Bundle();
                extras.putString("NewEv", EvmaApp.helperNewEv.toStringx());
         		i.putExtras(extras) ;
         		EvmaApp.helperIAMGETTIHPIC = true; 
        		System.out.println("before " + EvmaApp.helperNewEv.toStringx() );
         		getActivity().startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
 		etEventDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                 
                DatePickerDialog mDatePicker;
                
                mDatePicker = new DatePickerDialog(getActivity(), new OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        selectedmonth = selectedmonth + 1;
                        etEventDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        EvDate =  selectedday + "x" + selectedmonth + "x" + selectedyear;
                    }

					 
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Date de l'événement");
                mDatePicker.show();
				
				 
			 
			}
		});

		etEventDateFIN.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentDate = Calendar.getInstance();
		        int mYear = mcurrentDate.get(Calendar.YEAR);
		        int mMonth = mcurrentDate.get(Calendar.MONTH);
		        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
		         
		        DatePickerDialog mDatePicker;
		        
		        mDatePicker = new DatePickerDialog(getActivity(), new OnDateSetListener() {
		            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
		                // TODO Auto-generated method stub
		                selectedmonth = selectedmonth + 1;
		                etEventDateFIN.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
		                EvDateFIN =  selectedday + "x" + selectedmonth + "x" + selectedyear;
		            }
		
					 
		        }, mYear, mMonth, mDay);
		        mDatePicker.setTitle("Date de fin l'événement");
		        mDatePicker.show();
				
				 
			 
			}
		});
		
		//get the time
		etOrgaTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);

				TimePickerDialog mTimePicker;
				mTimePicker = new TimePickerDialog(getActivity(),
						new TimePickerDialog.OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker timePicker,
									int selectedHour, int selectedMinute) {
								etOrgaTime.setText(selectedHour + ":"
										+ selectedMinute);
								EvTime = "x" + selectedHour + "x"
										+ selectedMinute;
							}

						}, hour, minute, true);// Yes 24 hour time
				mTimePicker.setTitle("Horaire de l'événement");
				mTimePicker.show();

			}
		});
		// get the time
		etOrgaTimeFIN.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);

				TimePickerDialog mTimePicker;
				mTimePicker = new TimePickerDialog(getActivity(),
						new TimePickerDialog.OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker timePicker,
									int selectedHour, int selectedMinute) {
								etOrgaTimeFIN.setText(selectedHour + ":"
										+ selectedMinute);
								EvTimeFIN = "x" + selectedHour + "x"
										+ selectedMinute;
							}

						}, hour, minute, true);// Yes 24 hour time
				mTimePicker.setTitle("Horaire de l'événement");
				mTimePicker.show();

			}
		});
				
		
		btnNextEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent in = new Intent(Organizer_event_create.this,Event_forfaits.class);
				//startActivity(in);
				System.out.println("sdxx");
				if (EvmaApp.helperNewEv.getDateTime().isEmpty()) {
					EvmaApp.helperNewEv.setDateTime(EvDate+EvTime);
				}
				if (EvmaApp.helperNewEv.getDateTimeFin().isEmpty()) {
	            	EvmaApp.helperNewEv.setDateTimeFin(EvDateFIN+EvTimeFIN);
				}

				if (EvmaApp.helperNewEv.getTitle().isEmpty()) {
	             	EvmaApp.helperNewEv.setTitle(etEventTitle.getText().toString());
				}
				if (EvmaApp.helperNewEv.getEvent_Description().isEmpty()) {
	             	EvmaApp.helperNewEv.setEvent_Description(etEventDescription.getText().toString());
				}
				
				
 				initValidationForm();
				if (mForm.isValid()) {
					//Store the Data into the database 
					ev = new Event(0, etEventTitle.getText().toString(), EvmaApp.CurrentUsernameID, EvDate+EvTime, EvDateFIN+EvTimeFIN,"1",etEventDescription.getText().toString(),EvCover,CBisVisible.isChecked());
					
					System.out.println("after  "  + EvmaApp.helperNewEv);
					new AddEventAsync().execute();
				}

				 
				/*Update the current fragment with the Pack fragment
				FragmentManager man=getFragmentManager();
                FragmentTransaction tran=man.beginTransaction();
                Organizer_Forfait_create OrgForfait = new Organizer_Forfait_create();
                tran.remove(Organizer_event_create.this);
                tran.add(R.id.fragment_container, OrgForfait);//tran.
                tran.addToBackStack(null);
                tran.commit();
				rootView = mInflater.inflate(R.layout.organizer_pack_create, mContainer, false);*/
				 
				
				
			}
		});
		return rootView;
		
	}
 
	private void initValidationForm() {
        mForm = new Form(getActivity());
        
        mForm.setValidationFailedRenderer(new  CroutonValidationFailedRenderer(getActivity()));
        mForm.addField(Field.using(etEventTitle.getEditText()).validate(NotEmpty.build(getActivity())));   
        
        mForm.getValidationFailedRenderer().clear();
        mForm.setValidationFailedRenderer(new TextViewValidationFailedRenderer(getActivity()));
    }


	private class AddEventAsync extends AsyncTask<Void, Void, Void> {
	
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
			try {
				String pathSpliiter[] = EvmaApp.helperpicturePath.split("/");
				System.out.println(pathSpliiter[4] + " pathSpliiter[4]"   + pathSpliiter[3]);
				String filename = pathSpliiter[4];
				System.out.println( " filen===><"  + filename );
				mEventsController.AddEvnetAndUpload(EvmaApp.helperpicturePath,filename,EvmaApp.helperNewEv,"AddEvent");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
			}
			
			System.out.println("doInBackground donnne");
			 
			return null;
		}
	
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			System.out.println("done adding evetn ID " + EvmaApp.LastEventID); 
			
			if (Integer.parseInt(EvmaApp.LastEventID) > 0 || Integer.parseInt(EvmaApp.LastEventID) <9999  ) {
				Intent in = new Intent(getActivity(), OrganizerMainActivity.class);
				in.putExtra(EventsController.TAG_NAME, ev.getTitle());
				in.putExtra(EventsController.TAG_dateTime, etEventDate.getText()  +" "+etOrgaTime.getText() + "");
				in.putExtra(EventsController.TAG_dateTime_fin, etEventDateFIN.getText()  +" "+etOrgaTimeFIN.getText() + "");
				in.putExtra(EventsController.TAG_ID, EvmaApp.LastEventID);
				in.putExtra("type", "AddEvent");
				in.putExtra("Organisateur", EventsController.getUsernameOrga());
				 EvmaApp.helperNewEv.setEventID( Integer.valueOf(EvmaApp.LastEventID)); 
				
				startActivity(in);
			} 
	
		}
	
	}
	
 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("called");
 
        System.out.println("called" + EvmaApp.helperNewEv.toString());
	}
	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
	}

	  
	public String getEvID() {
		return EvID;
	}


	public void setEvID(String evID) {
		EvID = evID;
	}

}


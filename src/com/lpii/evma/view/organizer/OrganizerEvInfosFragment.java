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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;
import com.lpii.evma.controller.CroutonValidationFailedRenderer;
import com.lpii.evma.controller.EventsController;
import com.lpii.evma.model.Event;
import com.lpii.evma.view.FloatLabelEditText;
import com.squareup.picasso.Picasso;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class OrganizerEvInfosFragment extends Fragment{
	

	FloatLabelEditText et_evTitle;
	TextView et_OrgaData;
	TextView et_OrgaEvTime;
	TextView et_OrgaDateFIN;
	TextView et_OrgaEvTimeFIN;
	CheckBox cb_EventIsVisible;
	FloatLabelEditText etEventDescription;
	EventsController mEventsController; 
	private ProgressDialog pDialog;
	Button btn_EventCreateFinish;
	Event ev = null;
	String Evet_OrgaEvTime;
	String Evet_OrgaEvDate;
	String Evet_OrgaEvTimeFin;
	String Evet_OrgaEvDateFin;
	String EvCover;
	ImageView OrgaImg;
	private Form mForm;
    private static int RESULT_LOAD_IMAGE = 99;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.organizer_event_create, container, false);
        
        et_evTitle = (FloatLabelEditText) rootView.findViewById(R.id.etOrgaEventTitle);
        et_OrgaData = (TextView) rootView.findViewById(R.id.etOrgaDate);
        et_OrgaEvTime = (TextView) rootView.findViewById(R.id.etOrgaEvTime);
        et_OrgaDateFIN = (TextView) rootView.findViewById(R.id.etOrgaDateFIN);
		etEventDescription = (FloatLabelEditText) rootView.findViewById(R.id.etOrgaEvDescription); 
        et_OrgaEvTimeFIN = (TextView) rootView.findViewById(R.id.etOrgaEvTimeFIN);
        cb_EventIsVisible = (CheckBox) rootView.findViewById(R.id.EventIsVisible);
        btn_EventCreateFinish = (Button) rootView.findViewById(R.id.EventCreateFinish);
        OrgaImg = (ImageView) rootView.findViewById(R.id.OrgaimgThumbnail);
        cb_EventIsVisible.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
 
				EvmaApp.helperNewEv.setVisible(arg1);
			}
		});
        
        OrgaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	System.out.println("LCicked");
            	if (Evet_OrgaEvDate != null && Evet_OrgaEvTime != null ) {
                	EvmaApp.helperNewEv.setDateTime(Evet_OrgaEvDate+Evet_OrgaEvTime);
				}
            	if (Evet_OrgaEvDateFin != null && Evet_OrgaEvTimeFin != null ) {
                 	EvmaApp.helperNewEv.setDateTimeFin(Evet_OrgaEvDateFin+Evet_OrgaEvTimeFin);
				}
            	if (et_evTitle != null && !et_evTitle.getText().toString().isEmpty()   ) {
                 	EvmaApp.helperNewEv.setTitle(et_evTitle.getText().toString());
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
        OrgaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	System.out.println("LCicked");
        		Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        if (EvmaApp.helperNewEv == null) {
			System.out.println("EvmaApp.helperNewEv nullll");
		}
        System.out.println("vmaApp.helperNewEv.getTitle() " + EvmaApp.helperNewEv.getTitle());
        if (!EvmaApp.helperNewEv.getTitle().isEmpty()) {
        	et_evTitle.getEditText().setText(EvmaApp.helperNewEv.getTitle());
		}
        if (!EvmaApp.helperNewEv.getEvent_Description().isEmpty()) {
        	etEventDescription.getEditText().setText(EvmaApp.helperNewEv.getEvent_Description());
		}
        if (!EvmaApp.helperNewEv.getDateTime().isEmpty()) {
        	String[] splitter= EvmaApp.helperNewEv.getDateTime().split("x");
        	System.out.println(splitter[0]  +"  " +  splitter[1]) ;
        	et_OrgaData.setText(splitter[0]+"/"+splitter[1]+"/"+splitter[2]);
        	et_OrgaEvTime.setText(splitter[3]+":"+splitter[4]);
		}
        if (!EvmaApp.helperNewEv.getDateTimeFin().isEmpty()) {
        	String[] splitter= EvmaApp.helperNewEv.getDateTimeFin().split("x");
        	et_OrgaDateFIN.setText(splitter[0]+"/"+splitter[1]+"/"+splitter[2]);
        	et_OrgaEvTimeFIN.setText(splitter[3]+":"+splitter[4]);
		}
        
        if (!EvmaApp.helperpicturePath.isEmpty()) {
        	System.out.println("picturePath == >"  + EvmaApp.helperpicturePath);
        	OrgaImg.setImageBitmap(BitmapFactory.decodeFile(EvmaApp.helperpicturePath));
 
		}

        //	get the time
		et_OrgaEvTime.setOnClickListener(new OnClickListener() {

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
								et_OrgaEvTime.setText(selectedHour + ":"
										+ selectedMinute);
								Evet_OrgaEvTime = "x" + selectedHour + "x"
										+ selectedMinute;
							}

						}, hour, minute, true);// Yes 24 hour time
				mTimePicker.setTitle("Horraire de l'evenement");
				mTimePicker.show();

			}
		});

		// get the time
		et_OrgaData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentDate = Calendar.getInstance();
				int mYear = mcurrentDate.get(Calendar.YEAR);
				int mMonth = mcurrentDate.get(Calendar.MONTH);
				int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog mDatePicker;

				mDatePicker = new DatePickerDialog(getActivity(),
						new OnDateSetListener() {

							public void onDateSet(DatePicker datepicker,
									int selectedyear, int selectedmonth,
									int selectedday) {
								// TODO Auto-generated method stub
								selectedmonth = selectedmonth + 1;
								et_OrgaData.setText("" + selectedday + "/"
										+ selectedmonth + "/" + selectedyear);
								Evet_OrgaEvDate = selectedday + "x"
										+ selectedmonth + "x" + selectedyear;
							}

						}, mYear, mMonth, mDay);
				mDatePicker.setTitle("Date de l'événement");
				mDatePicker.show();

			}
		});

		// get the time
		et_OrgaEvTimeFIN.setOnClickListener(new OnClickListener() {

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
								et_OrgaEvTimeFIN.setText(selectedHour + ":"
										+ selectedMinute);
								Evet_OrgaEvTimeFin = "x" + selectedHour + "x"
										+ selectedMinute;
							}

						}, hour, minute, true);// Yes 24 hour time
				mTimePicker.setTitle("Horraire de l'evenement");
				mTimePicker.show();

			}
		});

		// get the time
		et_OrgaDateFIN.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentDate = Calendar.getInstance();
				int mYear = mcurrentDate.get(Calendar.YEAR);
				int mMonth = mcurrentDate.get(Calendar.MONTH);
				int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog mDatePicker;

				mDatePicker = new DatePickerDialog(getActivity(),
						new OnDateSetListener() {

							public void onDateSet(DatePicker datepicker,
									int selectedyear, int selectedmonth,
									int selectedday) {
								// TODO Auto-generated method stub
								selectedmonth = selectedmonth + 1;
								et_OrgaDateFIN.setText("" + selectedday + "/"
										+ selectedmonth + "/" + selectedyear);
								Evet_OrgaEvDateFin = selectedday + "x"
										+ selectedmonth + "x" + selectedyear;
							}

						}, mYear, mMonth, mDay);
				mDatePicker.setTitle("Date de l'événement");
				mDatePicker.show();

			}
		});
  		
        if (getActivity().getIntent() != null) {
        	Intent intenEv = getActivity().getIntent();
        	System.out.println("Intet : " + intenEv.toString());
        	String evDate = intenEv.getStringExtra(EventsController.TAG_dateTime);
        	String CoverURL = intenEv.getStringExtra(EventsController.TAG_event_cover);
        	String urldisplay = EvmaApp.EventCoverUrl +CoverURL;
        	System.out.println("urldisplay" + urldisplay);
    		Picasso.with(getActivity()).load(urldisplay).placeholder(R.drawable.defaultcoverx).into(OrgaImg);

        	//Date Debut
        	String SplitThaDateTime[] = evDate.split(" ");
        	System.out.println(SplitThaDateTime[0] + " //// "  + SplitThaDateTime[1]);
        	String[] SplitDate;
        	if (getActivity().getIntent().getStringExtra("type").equals("AddEvent")) {
        		SplitDate = SplitThaDateTime[0].split("/");
			}else{
				SplitDate = SplitThaDateTime[0].split("-");
			}
        	int selectedday = Integer.valueOf(SplitDate[2]);
        	int selectedmonth= Integer.valueOf(SplitDate[1]);
        	int selectedyear = Integer.valueOf(SplitDate[0]);
        	
        	String[] splittime = SplitThaDateTime[1].split(":");
        	int selectedHour = Integer.valueOf(splittime[0]);
        	int selectedMinute = Integer.valueOf(splittime[1]);
        	Evet_OrgaEvTime = "x"  +selectedHour + "x" + selectedMinute ;
        	Evet_OrgaEvDate =  selectedday + "x" + selectedmonth + "x" + selectedyear;
        	et_OrgaEvTime.setText(SplitThaDateTime[1]);
        	//FIN
        	String evDateFIN = intenEv.getStringExtra(EventsController.TAG_dateTime_fin);
        	
        	//Date Debut
        	SplitThaDateTime = evDateFIN.split(" ");
        	System.out.println("evDateFIN " + evDateFIN);
        	System.out.println(SplitThaDateTime[0] + " //// "  + SplitThaDateTime[1]);
        	
        	if (getActivity().getIntent().getStringExtra("type").equals("AddEvent")) {
        		SplitDate = SplitThaDateTime[0].split("/");
			}else{
				SplitDate = SplitThaDateTime[0].split("-");
			}
        	selectedday = Integer.valueOf(SplitDate[2]);
        	selectedmonth= Integer.valueOf(SplitDate[1]);
        	selectedyear = Integer.valueOf(SplitDate[0]);
        	
        	splittime = SplitThaDateTime[1].split(":");
        	selectedHour = Integer.valueOf(splittime[0]);
        	selectedMinute = Integer.valueOf(splittime[1]);
        	Evet_OrgaEvTimeFin = "x"  +selectedHour + "x" + selectedMinute ;
        	Evet_OrgaEvDateFin =  selectedday + "x" + selectedmonth + "x" + selectedyear;
        	
        	et_evTitle.getEditText().setText(intenEv.getStringExtra(EventsController.TAG_NAME));
        	et_OrgaData.setText(SplitThaDateTime[0]);
        	etEventDescription.getEditText().setText(intenEv.getStringExtra(EventsController.TAG_event_Description));
        	et_OrgaEvTimeFIN.setText(SplitThaDateTime[1]);
        	Boolean isCheckedEv  =Boolean.valueOf(intenEv.getStringExtra(EventsController.TAG_visible));
        	System.out.println(isCheckedEv  +"  ischeked");
        	cb_EventIsVisible.setChecked(isCheckedEv);
        }
        mEventsController = new EventsController();
        btn_EventCreateFinish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("sdxx");
				if (EvmaApp.helperNewEv.getDateTime().isEmpty()) {
					EvmaApp.helperNewEv.setDateTime(Evet_OrgaEvDate+Evet_OrgaEvTime);
				}
				if (EvmaApp.helperNewEv.getDateTimeFin().isEmpty()) {
	            	EvmaApp.helperNewEv.setDateTimeFin(Evet_OrgaEvDateFin+Evet_OrgaEvTimeFin);
				}

				if (EvmaApp.helperNewEv.getTitle().isEmpty()) {
	             	EvmaApp.helperNewEv.setTitle(et_evTitle.getText().toString());
				}
				if (EvmaApp.helperNewEv.getEvent_Description().isEmpty()) {
	             	EvmaApp.helperNewEv.setEvent_Description(etEventDescription.getText().toString());
				}
				
				
 				initValidationForm();
				if (mForm.isValid()) {
					//Store the Data into the database .
					//ev = new Event(EvmaApp.helperNewEv.getEventID(), et_evTitle.getText().toString(), EvmaApp.CurrentUsernameID, Evet_OrgaEvDate+Evet_OrgaEvTime, Evet_OrgaEvDateFin+Evet_OrgaEvTimeFin,"1",etEventDescription.getText().toString(),EvCover,cb_EventIsVisible.isChecked());
					
					System.out.println("after  "  + EvmaApp.helperNewEv);
					new UpdateEventAsync().execute();
				} 
				
 			}
		});
        
        return rootView;
    }
	private void initValidationForm() {
        mForm = new Form(getActivity());
        
        mForm.setValidationFailedRenderer(new  CroutonValidationFailedRenderer(getActivity()));
        mForm.addField(Field.using(et_evTitle.getEditText()).validate(NotEmpty.build(getActivity())));   
        
        mForm.getValidationFailedRenderer().clear();
        mForm.setValidationFailedRenderer(new TextViewValidationFailedRenderer(getActivity()));
    }
	private class UpdateEventAsync extends AsyncTask<Void, Void, Void> {
	
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
				String filename ="";
				if (!EvmaApp.helperpicturePath.isEmpty()) {
					String pathSpliiter[] = EvmaApp.helperpicturePath.split("/");
					System.out.println(pathSpliiter[4] + " pathSpliiter[4]"   + pathSpliiter[3]);
					filename = pathSpliiter[4];
					System.out.println( " filen===><"  + filename );
				}
				EvmaApp.helperNewEv.setEventID(Integer.valueOf(EvmaApp.LastEventID));
				mEventsController.AddEvnetAndUpload(EvmaApp.helperpicturePath,filename,EvmaApp.helperNewEv,"UpdateEvent");
				
				 
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
			System.out.println("done "  ); 
			Crouton crouton ;
			if (EvmaApp.isError) {
				crouton = Crouton.makeText(getActivity(), "une erreur est survenue lors la mise jour", de.keyboardsurfer.android.widget.crouton.Style.ALERT);
			}else{
				crouton = Crouton.makeText(getActivity(), "L'événement a été mise jour avec succès", de.keyboardsurfer.android.widget.crouton.Style.CONFIRM);
				
			}
		    crouton.show();
			 
	
		}
	
	}
	 
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode  "  + resultCode);
         	if (requestCode == MainEv.RESULT_LOAD_IMAGE && resultCode ==  getActivity().RESULT_OK && null != data) {
 
                System.out.println("onActivityResult wwwxxxresultCode  "  + resultCode);
            	Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
     
                Cursor cursor =  getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
     
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
    			EvmaApp.helperpicturePath = picturePath;
    			System.out.println("helperpicturePath" + EvmaApp.helperpicturePath);
            	OrgaImg.setImageBitmap(BitmapFactory.decodeFile(EvmaApp.helperpicturePath));

        }
	 }
	 
	 

}



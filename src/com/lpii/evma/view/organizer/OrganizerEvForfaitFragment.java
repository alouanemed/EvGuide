package com.lpii.evma.view.organizer;
 

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.org.zasadnyy.zvalidations.Field;
import ua.org.zasadnyy.zvalidations.Form;
import ua.org.zasadnyy.zvalidations.TextViewValidationFailedRenderer;
import ua.org.zasadnyy.zvalidations.validations.NotEmpty;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.R;
import com.lpii.evma.controller.CroutonValidationFailedRenderer;
import com.lpii.evma.controller.ForfaitController;
import com.lpii.evma.model.EventPack;
import com.lpii.evma.model.ForfaitAdapter;
import com.lpii.evma.view.FloatLabelEditText;

import de.keyboardsurfer.android.widget.crouton.Crouton;
 

public class OrganizerEvForfaitFragment extends ListFragment{

	ForfaitController mForfaitController;
	EventPack evPack;
	private ProgressDialog pDialog;
	static LinearLayout lynPacks;
	static LinearLayout emptyForfaitsx;
	static LinearLayout lyPackForm1;
	static LinearLayout lyPackForm2;
	static LinearLayout lyPackForm4;
	static LinearLayout lyPackForm3;
	static LinearLayout lyPackFormDIVIDER1;
	static LinearLayout lyPackFormDIVIDER2;
	Button btn_ShowFreeForm;
	Button btn_ShowPaidForm;
	Button btn_AddForfait;
	static Button btn_Annuler;
	static Button btnPackCreateFinishx;
	Boolean isShowed = false;
	 
	FloatLabelEditText etpack_Title;

	 
	FloatLabelEditText et_Pack_pricex;
	FloatLabelEditText et_Pack_Description;
	TextView et_Debut_Vente_Date;
	TextView et_Debut_Vente_Time;
	TextView et_Fin_Vente_Date;
	TextView et_Fin_Vente_Time;
	TextView tvPack_full_price;
	TextView tvPack_ticket_price;
	TextView tvPack_ticket_fees;
	CheckBox CBisVisible;
	CheckBox CBisDisponible;
	RadioButton rbev_PackOrganizerPay;
	RadioButton rbev_PackCustomerPay;
	FloatLabelEditText etpack_Qty;
	FloatLabelEditText etpack_fees;
	//EditText etpack_Statut;
	
	String EvDebutDate;
	String EvFinDate;
	String EvFinTime;
	String EvDebutTime;
	private String PackAction;
	String PackID ="";
	String Statut_Pack = "";
	double finalePrice = 0;
	double GenuinePrice = 0;
	double mFees = 0;
	Boolean isOrgaPay =  false;
	String forfaitDisponible;
	
	
	// form for validation of pack
	private Form mForm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getActivity().setContentView(R.layout.event_desc);
		 
		mForfaitController = new ForfaitController(); 
		
		new GetForfaits().execute();
		 

	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.organizer_pack_create, container, false);
        btn_ShowFreeForm = (Button) rootView.findViewById(R.id.btnShowFreeForm);
        btn_ShowPaidForm = (Button) rootView.findViewById(R.id.btnShowPaidForm);
        btn_Annuler = (Button) rootView.findViewById(R.id.btnPackCreateCancel);
        btnPackCreateFinishx = (Button) rootView.findViewById(R.id.btnPackCreateFinish);
        btn_AddForfait = (Button) rootView.findViewById(R.id.NoEmptyAddForfait);
        lynPacks = (LinearLayout) rootView.findViewById(R.id.Orgapackshowly);
        emptyForfaitsx = (LinearLayout) rootView.findViewById(R.id.emptyForfaits);
        lyPackForm1 = (LinearLayout) rootView.findViewById(R.id.lyPackForm1);
        lyPackForm2 = (LinearLayout) rootView.findViewById(R.id.lyPackForm2);
        lyPackForm3 = (LinearLayout) rootView.findViewById(R.id.lyPackForm3);
        lyPackForm4 = (LinearLayout) rootView.findViewById(R.id.lyPackForm4);
        lyPackFormDIVIDER1 = (LinearLayout) rootView.findViewById(R.id.lyPackFormDIVIDER1);
        lyPackFormDIVIDER2 = (LinearLayout) rootView.findViewById(R.id.lyPackFormDIVIDER2);
        lyPackForm1.setVisibility(LinearLayout.GONE);
        lyPackForm2.setVisibility(LinearLayout.GONE);
        lyPackForm3.setVisibility(LinearLayout.GONE);
        lyPackForm4.setVisibility(LinearLayout.GONE);
        lyPackFormDIVIDER1.setVisibility(LinearLayout.GONE);
        lyPackFormDIVIDER2.setVisibility(LinearLayout.GONE);
        btnPackCreateFinishx.setVisibility(Button.GONE);
    	etpack_Title =(FloatLabelEditText) rootView.findViewById(R.id.etPack_Title);
    	et_Pack_pricex =(FloatLabelEditText) rootView.findViewById(R.id.etPack_price);

    	
    	et_Debut_Vente_Date =(TextView) rootView.findViewById(R.id.EtPackd_Vente_Date); 
    	et_Fin_Vente_Date =(TextView) rootView.findViewById(R.id.EtPack_F_Vente_Date);
    	et_Fin_Vente_Time =(TextView) rootView.findViewById(R.id.EtPack_F_Vente_Time); 
    	et_Debut_Vente_Time =(TextView) rootView.findViewById(R.id.EtPackd_Vente_Time);
    	tvPack_full_price =(TextView) rootView.findViewById(R.id.tvPack_full_price);
    	tvPack_ticket_fees =(TextView) rootView.findViewById(R.id.tvPack_ticket_fees);
    	tvPack_ticket_price =(TextView) rootView.findViewById(R.id.tvPack_ticket_pric);
    	
    	CBisVisible =(CheckBox) rootView.findViewById(R.id.EventPackIsVisible);
    	CBisDisponible =(CheckBox) rootView.findViewById(R.id.EventPackStatut); 
    	etpack_Qty =(FloatLabelEditText) rootView.findViewById(R.id.etPack_Qty); 
    	etpack_fees  =(FloatLabelEditText) rootView.findViewById(R.id.etPack_Fees);
    	et_Pack_Description  =(FloatLabelEditText) rootView.findViewById(R.id.etPack_Description);
    	rbev_PackOrganizerPay  =(RadioButton) rootView.findViewById(R.id.EventPackOrganizerPay);
    	rbev_PackCustomerPay  =(RadioButton) rootView.findViewById(R.id.EventPackClientPay);
    	
    	et_Pack_pricex.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
    	et_Pack_pricex.getEditText().setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});

    	etpack_fees.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
    	etpack_fees.getEditText().setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});

    	etpack_Qty.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
    	etpack_Qty.getEditText().setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
    	
    	btn_Annuler.setVisibility(Button.GONE);
        emptyForfaitsx.setVisibility(LinearLayout.VISIBLE);
    	if (!EvmaApp.isPacksShowed) {
    		ShowForfaits();
    		EvmaApp.isPacksShowed = true;
    		 
		}else{
			emptyForfaitsx.setVisibility(LinearLayout.GONE);
		}
    	
    	//Annuler la cr"ation du pack
    	btn_Annuler.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (	mForfaitController.getSingleforfaitsEvent().size() != 0) {
					ShowForfaits();
				}else{
					emptyForfaitsx.setVisibility(LinearLayout.VISIBLE);
				}
				btn_ShowFreeForm.setVisibility(Button.GONE);
				btn_ShowPaidForm.setVisibility(Button.GONE);
				btn_Annuler.setVisibility(Button.GONE);
			    lyPackForm1.setVisibility(LinearLayout.GONE);
		        lyPackForm2.setVisibility(LinearLayout.GONE);
		        lyPackForm3.setVisibility(LinearLayout.GONE);
		        lyPackForm4.setVisibility(LinearLayout.GONE);
		        lyPackFormDIVIDER2.setVisibility(LinearLayout.GONE);
		        lyPackFormDIVIDER1.setVisibility(LinearLayout.GONE);
			}
		});
    	
    	//
    	et_Debut_Vente_Date.setOnClickListener(new OnClickListener() {
			
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
                        et_Debut_Vente_Date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        EvDebutDate =  selectedday + "x" + selectedmonth + "x" + selectedyear;
                    }
					 
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Date de l'événement");
                mDatePicker.show();
				
				 
			 
			}
			});
		
    	
    	//
    	et_Fin_Vente_Date.setOnClickListener(new OnClickListener() {
			
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
                        et_Fin_Vente_Date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                        EvFinDate =  selectedday + "x" + selectedmonth + "x" + selectedyear;
                    }
					 
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Date de l'événement");
                mDatePicker.show();
				
				 
			 
			}
			});
		
		//get the time
		et_Fin_Vente_Time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentTime = Calendar.getInstance();
	            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	            int minute = mcurrentTime.get(Calendar.MINUTE);
	            
	            
	            TimePickerDialog mTimePicker;
	            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
	                @Override
	                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
	                	et_Fin_Vente_Date.setText( selectedHour + ":" + selectedMinute   );
	                	EvFinTime = "x"  +selectedHour + "x" + selectedMinute ;
	                }

					 
	            }, hour, minute, true);//Yes 24 hour time
	            mTimePicker.setTitle("Horaire de l'événement");
	            mTimePicker.show();
 
			}
		});
		
		//get the time
		et_Debut_Vente_Time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentTime = Calendar.getInstance();
	            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	            int minute = mcurrentTime.get(Calendar.MINUTE);
	            
	            
	            TimePickerDialog mTimePicker;
	            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
	                @Override
	                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
	                	et_Debut_Vente_Time.setText( selectedHour + ":" + selectedMinute   );
	                	EvDebutTime = "x"  +selectedHour + "x" + selectedMinute ;
	                }

					 
	            }, hour, minute, true);//Yes 24 hour time
	            mTimePicker.setTitle("Début de la vente de billets  ");
	            mTimePicker.show();

				 
			}
		});
		
		//get the time
		et_Fin_Vente_Time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("get the thing");
				Calendar mcurrentTime = Calendar.getInstance();
	            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	            int minute = mcurrentTime.get(Calendar.MINUTE);
	            
	            
	            TimePickerDialog mTimePicker;
	            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
	                @Override
	                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
	                	et_Fin_Vente_Time.setText( selectedHour + ":" + selectedMinute   );
	                	EvFinTime = "x"  +selectedHour + "x" + selectedMinute ;
	                }

					 
	            }, hour, minute, true);//Yes 24 hour time
	            mTimePicker.setTitle("Fin de la vente de billets");
	            mTimePicker.show();

				
				 
			 
			}
		});
		
		rootView.setFocusableInTouchMode(true);
		rootView.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if( arg1 == KeyEvent.KEYCODE_BACK )
		        {
		            System.out.println("Clciked...;");
		            if (	mForfaitController.getSingleforfaitsEvent().size() != 0) {
						ShowForfaits();
					}else{
						emptyForfaitsx.setVisibility(LinearLayout.VISIBLE);
					}
					btn_ShowFreeForm.setVisibility(Button.GONE);
					btn_ShowPaidForm.setVisibility(Button.GONE);
					btn_Annuler.setVisibility(Button.GONE);
				    lyPackForm1.setVisibility(LinearLayout.GONE);
			        lyPackForm2.setVisibility(LinearLayout.GONE);
			        lyPackForm3.setVisibility(LinearLayout.GONE);
			        lyPackForm4.setVisibility(LinearLayout.GONE);
			        lyPackFormDIVIDER2.setVisibility(LinearLayout.GONE);
			        lyPackFormDIVIDER1.setVisibility(LinearLayout.GONE);
					return true;
		        }
		        return false;			}
		});
		initValidationForm();
    	btnPackCreateFinishx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("lets add this thing to databse");
				 if (mForm.isValid()) {

					String isVisible = CBisVisible.isChecked()? "1":"0";
					forfaitDisponible = CBisDisponible.isChecked()? ForfaitController.STATUT_DISPONIBLE:"null";
					evPack = new EventPack(etpack_Title.getText().toString(), PackID, et_Pack_Description.getText().toString(), EvDebutDate+EvDebutTime, EvFinDate+EvFinTime, isVisible,  "" +GenuinePrice, etpack_Qty.getText().toString(), EvmaApp.LastEventID, mFees + "", "0",forfaitDisponible);
					System.out.println(evPack.toString());
					System.out.println("Action : " + PackAction);
					if (PackAction.equals("Update")) {
						new AddForfaitAsync().execute();
						
					}else{
						new AddForfaitAsync().execute();
					}
				}
				
				 
			}
		});
    	
    	btn_AddForfait.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ClearFields();
				PackAction = "Add";
				PackID = "";
				et_Pack_pricex.getEditText().setText("0");
				etpack_fees.getEditText().setText("0");
				et_Pack_pricex.setVisibility(EditText.GONE);
				tvPack_full_price.setVisibility(EditText.GONE);
				etpack_fees.setVisibility(EditText.GONE);
				rbev_PackCustomerPay.setVisibility(RadioButton.GONE);
				rbev_PackOrganizerPay.setVisibility(RadioButton.GONE);
			    lyPackForm1.setVisibility(LinearLayout.VISIBLE);
		        lyPackForm2.setVisibility(LinearLayout.VISIBLE);
		        lyPackForm3.setVisibility(LinearLayout.VISIBLE);
		        lyPackForm4.setVisibility(LinearLayout.VISIBLE);
		        lyPackFormDIVIDER2.setVisibility(LinearLayout.VISIBLE);
		        lyPackFormDIVIDER1.setVisibility(LinearLayout.VISIBLE);
		        btnPackCreateFinishx.setVisibility(Button.VISIBLE);
		        emptyForfaitsx.setVisibility(LinearLayout.GONE);
		        lynPacks.setVisibility(LinearLayout.GONE);
		        btn_Annuler.setVisibility(Button.VISIBLE); 
		        btn_ShowFreeForm.setVisibility(Button.VISIBLE);
		        btn_ShowPaidForm.setVisibility(Button.VISIBLE);
				
			}
		});
    	
		btn_ShowFreeForm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("Clicked");
				et_Pack_pricex.getEditText().setText("0");
				et_Pack_pricex.setVisibility(EditText.VISIBLE);
				et_Pack_pricex.setEnabled(false);
				tvPack_full_price.setVisibility(EditText.VISIBLE);
				tvPack_full_price.setText("Total acheteur: " + 0 + " MAD");
				finalePrice = 0;
				etpack_fees.getEditText().setText("0");
				etpack_fees.setVisibility(EditText.GONE);
				rbev_PackCustomerPay.setVisibility(RadioButton.GONE);
				rbev_PackOrganizerPay.setVisibility(RadioButton.GONE);
				
		        

			}
		});
		
		btn_ShowPaidForm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("Clicked");
				mShowPaidForm();
			}
		});
         
		rbev_PackOrganizerPay.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				isOrgaPay = true;
				System.out.println("check");
				mFees = Math.ceil(Double.valueOf(et_Pack_pricex.getText()) * EvmaApp.DefaultPourcentage);
				System.out.println(" fees  " + mFees);
				finalePrice = Double.valueOf(et_Pack_pricex.getText())  + mFees;
				GenuinePrice  = Double.valueOf(et_Pack_pricex.getText()) - mFees;
				tvPack_full_price.setText("Total acheteur: " + finalePrice + " MAD");
				tvPack_ticket_fees.setText("Frais de service : " + mFees + " MAD");
				tvPack_ticket_price.setText("Prix de Billet: " + Double.valueOf(et_Pack_pricex.getText()) + " MAD");
				etpack_fees.getEditText().setText(mFees + "");
				
			}
		});
		
		rbev_PackCustomerPay.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				isOrgaPay = false;
				System.out.println("rbev_PackCustomerPay");
				mFees = Math.ceil(Double.valueOf(et_Pack_pricex.getText()) * EvmaApp.DefaultPourcentage);
				System.out.println(" fees  " + mFees);
				finalePrice = Double.valueOf(et_Pack_pricex.getText())  - mFees;
				GenuinePrice  = Double.valueOf(et_Pack_pricex.getText());
				tvPack_full_price.setText("Total acheteur: " + finalePrice + " MAD");
				tvPack_ticket_fees.setText("Frais de service : " + mFees + " MAD");
				tvPack_ticket_price.setText("Prix de Billet: " + Double.valueOf(et_Pack_pricex.getText()) + " MAD");
				etpack_fees.getEditText().setText(mFees + "");
				
			}
		});
		
		et_Pack_pricex.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				if (et_Pack_pricex.getEditText().getText().toString().isEmpty()) {
					et_Pack_pricex.getEditText().setText("0");
				}
				if (isOrgaPay) {
					finalePrice = Double.valueOf(et_Pack_pricex.getText())  - mFees;
				}else{
					finalePrice = Double.valueOf(et_Pack_pricex.getText())  + mFees;
				}
				mFees = Math.ceil(Double.valueOf(et_Pack_pricex.getText()) * EvmaApp.DefaultPourcentage);
				System.out.println(" fees  " + mFees);
				tvPack_full_price.setText("Total acheteur: " + finalePrice + " MAD");
				tvPack_ticket_fees.setText("Frais de service : " + mFees + " MAD");
				tvPack_ticket_price.setText("Prix de Billet: " + Double.valueOf(et_Pack_pricex.getText()) + " MAD");
				etpack_fees.getEditText().setText(mFees + "");
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				/*mFees = Math.ceil(Double.valueOf(et_Pack_pricex.getText()) * EvmaApp.DefaultPourcentage);
				System.out.println(" fees  " + mFees);
				finalePrice = Double.valueOf(et_Pack_pricex.getText())  + mFees;
				tvPack_full_price.setText("Total acheteur: " + finalePrice + " MAD");
				etpack_fees.getEditText().setText(finalePrice + "");*/
				
			}
		});
		
        return rootView;
    }
	
	private void initValidationForm() {
        mForm = new Form(getActivity());
        
        mForm.setValidationFailedRenderer(new  CroutonValidationFailedRenderer(getActivity()));

        mForm.addField(Field.using(etpack_Title.getEditText()).validate(NotEmpty.build(getActivity()))); 
        mForm.addField(Field.using(et_Pack_Description.getEditText()).validate(NotEmpty.build(getActivity())));
        mForm.addField(Field.using(et_Pack_pricex.getEditText()).validate(NotEmpty.build(getActivity())));
        mForm.addField(Field.using(etpack_fees.getEditText()).validate(NotEmpty.build(getActivity()))); 
        mForm.addField(Field.using(etpack_Qty.getEditText()).validate(NotEmpty.build(getActivity()))); 
        
        mForm.getValidationFailedRenderer().clear();
        mForm.setValidationFailedRenderer(new TextViewValidationFailedRenderer(getActivity()));
    }
	
	public void mShowPaidForm(){
		et_Pack_pricex.getEditText().setText(finalePrice + "");
		etpack_fees.getEditText().setText("");
		etpack_fees.setVisibility(EditText.GONE);
		et_Pack_pricex.setVisibility(EditText.VISIBLE);
		tvPack_full_price.setVisibility(EditText.VISIBLE);
		rbev_PackCustomerPay.setVisibility(RadioButton.VISIBLE);
		rbev_PackOrganizerPay.setVisibility(RadioButton.VISIBLE);

	}
	public void ShowForfaits(){
		System.out.println("Clicked");
	    lyPackForm1.setVisibility(LinearLayout.GONE);
        lyPackForm2.setVisibility(LinearLayout.GONE);
        lyPackForm3.setVisibility(LinearLayout.GONE);
        lyPackFormDIVIDER2.setVisibility(LinearLayout.GONE);
        lyPackFormDIVIDER1.setVisibility(LinearLayout.GONE);
        lyPackForm4.setVisibility(LinearLayout.GONE);
        btnPackCreateFinishx.setVisibility(Button.GONE);
        emptyForfaitsx.setVisibility(LinearLayout.GONE);
        lynPacks.setVisibility(LinearLayout.VISIBLE);
		btn_Annuler.setVisibility(Button.GONE);
		lynPacks.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        
		
	}
	public void ClearFields(){
		et_Debut_Vente_Date.setText("");
		et_Debut_Vente_Time.setText("");
		et_Fin_Vente_Date.setText("");
		et_Pack_Description.getEditText().setText("");
		tvPack_full_price.setText("");
		et_Pack_pricex.getEditText().setText("");
		etpack_fees.getEditText().setText("");
		etpack_Qty.getEditText().setText("");
		//etpack_Statut.setText("");
		etpack_Title.getEditText().setText("");
		
	}
	public static  void showAddForfaitForms(View view){
		System.out.println("Clicked");

		btn_Annuler.setVisibility(Button.VISIBLE);
	    lyPackForm1.setVisibility(LinearLayout.VISIBLE);
        lyPackForm2.setVisibility(LinearLayout.VISIBLE);
        lyPackForm3.setVisibility(LinearLayout.VISIBLE);
        lyPackForm4.setVisibility(LinearLayout.VISIBLE);
        lyPackFormDIVIDER2.setVisibility(LinearLayout.VISIBLE);
        lyPackFormDIVIDER1.setVisibility(LinearLayout.VISIBLE);
        btnPackCreateFinishx.setVisibility(Button.VISIBLE);
        emptyForfaitsx.setVisibility(LinearLayout.GONE);
	}
	
	private class GetForfaits extends AsyncTask<Void, Void, Void> {

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
			EvmaApp.isReqFromOrga = true;
			mForfaitController.getForfaits();
			mForfaitController.getForfaitListx(EvmaApp.LastEventID);
 
			return null;
		} 

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			System.out.println("the size : " + mForfaitController.getSingleforfaitsEvent().size());
			if (	mForfaitController.getSingleforfaitsEvent().size() != 0) {
				EvmaApp.isPacksShowed = true;
				System.out.println("it's 3amer");
				isShowed = true;
				emptyForfaitsx.setVisibility(LinearLayout.GONE);
				lynPacks.setVisibility(LinearLayout.VISIBLE);
				lynPacks.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
				Typeface tfBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
				ListAdapter adapterx = new ForfaitAdapter(getActivity(), mForfaitController.getSingleforfaitsEvent(), tfBold, mForfaitController);
				
				/*ListAdapter adapter = new SimpleAdapter(getActivity(),
						mForfaitController.getSingleforfaitsEvent(),
						R.layout.event_forfait_list_item, new String[] {
								ForfaitController.TAG_pack_Title,
								ForfaitController.TAG_pack_Price,
								ForfaitController.TAG_pack_Solde,
								ForfaitController.TAG_pack_Qty,
								ForfaitController.TAG_pack_id,
								ForfaitController.TAG_pack_Statut_Pack,}, new int[] {
								R.id.tvforfaitTitle, R.id.tvForfaitType,R.id.tvforfaitSoldetickets,
								R.id.tvforfaittickets, R.id.tv_event_forf_id,R.id.pack_Statut_Pack });*/

				setListAdapter(adapterx);	
				System.out.println(adapterx.getItem(0) + " ======xx");
				
				
			}else{ 
				System.out.println("khaouiiiiiiiii");
				EvmaApp.isPacksShowed = false;
				if (!isShowed) {
					emptyForfaitsx.setVisibility(LinearLayout.VISIBLE);
					isShowed = false;
				}
				lynPacks.setVisibility(LinearLayout.GONE);
				
			}
			 

		}

	}
	 
	private class AddForfaitAsync extends AsyncTask<Void, Void, Void> {
	
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
				if (PackAction.equals("Update")) {
					mForfaitController.AddForfait(evPack,"editPack");
				}else{
					mForfaitController.AddForfait(evPack, "addPack");
				}				
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
				if (PackAction.equals("Update")) {
					crouton = Crouton.makeText(getActivity(), "Le forfait a été mise jour avec succès", de.keyboardsurfer.android.widget.crouton.Style.CONFIRM);
				}else{
					crouton = Crouton.makeText(getActivity(), "Le forfait a été ajouté avec succès", de.keyboardsurfer.android.widget.crouton.Style.CONFIRM);
				}
				
				
			}
		    crouton.show();
			btn_ShowFreeForm.setVisibility(Button.GONE);
	        btn_ShowPaidForm.setVisibility(Button.GONE);
	        ClearFields();
			new GetForfaits().execute();
			ShowForfaits();
	
		}
	
	}
	
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		System.out.println("Click yaay :v");
		PackAction = "Update";
		
		Statut_Pack = ((TextView) v.findViewById(R.id.tv_event_forf_id))
				.getText().toString();
		
		String EvForfaitiD = ((TextView) v.findViewById(R.id.tv_event_forf_id))
				.getText().toString();
		String EvForfaitPrice = ((TextView) v.findViewById(R.id.tvForfaitType))
				.getText().toString();
		
		PackID = EvForfaitiD;
		
		evPack = mForfaitController.getSingleForfaitByID(EvForfaitiD,"WithoutString");
		System.out.println(evPack.toString());
		lynPacks.setVisibility(LinearLayout.GONE);
		btn_ShowFreeForm.setVisibility(Button.VISIBLE);
        btn_ShowPaidForm.setVisibility(Button.VISIBLE);
		
		lyPackForm1.setVisibility(LinearLayout.VISIBLE);
        lyPackForm2.setVisibility(LinearLayout.VISIBLE);
        lyPackForm3.setVisibility(LinearLayout.VISIBLE);
        lyPackForm4.setVisibility(LinearLayout.VISIBLE);
        lyPackFormDIVIDER2.setVisibility(LinearLayout.VISIBLE);
        lyPackFormDIVIDER1.setVisibility(LinearLayout.VISIBLE);
        
        //split the date and time
        String[] splitDateDebut = evPack.getTAG_pack_Debut_Vente_Date().split(" ");
        String PackDateDebut = splitDateDebut[0];
        String PackTimeDebut = splitDateDebut[1];
        
        String[] splitDateFin = evPack.getTAG_pack_Fin_Vente_Date().split(" ");
        String PackDateFin = splitDateFin[0];
        String PackTimeFin = splitDateFin[1];
        btnPackCreateFinishx.setVisibility(Button.VISIBLE);
        btn_Annuler.setVisibility(Button.VISIBLE);
        String RealEvTitle = evPack.getTAG_pack_Title().replace("(Expired)", "");
        etpack_Title.getEditText().setText(RealEvTitle);
        et_Pack_Description.getEditText().setText(evPack.getTAG_pack_Description());
        et_Debut_Vente_Date.setText(PackDateDebut);
        et_Fin_Vente_Date.setText(PackDateFin);
        et_Debut_Vente_Time.setText(PackTimeDebut);
        et_Fin_Vente_Time.setText(PackTimeFin);
        etpack_Qty.getEditText().setText(evPack.getTAG_pack_Qty());
        CBisVisible.setChecked(Boolean.valueOf(evPack.getTAG_pack_isVisible()));
        
        //Split the data 
      //Debut
    	String[] SplitDateDebut = PackDateDebut.split("-");
    	int selectedday = Integer.valueOf(SplitDateDebut[2]);
    	int selectedmonth= Integer.valueOf(SplitDateDebut[1]);
    	int selectedyear = Integer.valueOf(SplitDateDebut[0]);
    	EvDebutDate =  selectedday + "x" + selectedmonth + "x" + selectedyear;

    	String[] splittime = PackTimeDebut.split(":");
    	int selectedHour = Integer.valueOf(splittime[0]);
    	int selectedMinute = Integer.valueOf(splittime[1]);
    	EvDebutTime = "x"  +selectedHour + "x" + selectedMinute ;
    	
    	
    	//Fin
    	SplitDateDebut = PackDateDebut.split("-");
    	selectedday = Integer.valueOf(SplitDateDebut[2]);
    	selectedmonth= Integer.valueOf(SplitDateDebut[1]);
    	selectedyear = Integer.valueOf(SplitDateDebut[0]);
    	EvFinDate =  selectedday + "x" + selectedmonth + "x" + selectedyear;

    	splittime = PackTimeDebut.split(":");
    	selectedHour = Integer.valueOf(splittime[0]);
    	selectedMinute = Integer.valueOf(splittime[1]);
    	EvFinTime = "x"  +selectedHour + "x" + selectedMinute ;
    	
    	Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(evPack.getTAG_pack_Price());
        Double j = null;
        if (m.find()) {
            j = Double.valueOf(m.group(1));
        }
        System.out.println(EvForfaitPrice);
		if (EvForfaitPrice.equals("Gratuit")) {
			System.out.println("null Bor ! ");
			freeformshow();
		}else{
			mShowPaidForm();
	        et_Pack_pricex.getEditText().setText(j+ "");
			etpack_fees.getEditText().setText(evPack.getTAG_pack_fees());
			tvPack_full_price.setText("Total acheteur: " + j + " MAD");
		}
		
		if (!etpack_fees.getEditText().getText().toString().equals("0")) {

			//mFees = Math.ceil(Double.valueOf(et_Pack_pricex.getText()) * EvmaApp.DefaultPourcentage);
			double totalTicketPrice = Double.valueOf(evPack.getTAG_pack_fees())+j ;
			double mPr = totalTicketPrice * EvmaApp.DefaultPourcentage;
			if (mPr == Double.valueOf(evPack.getTAG_pack_fees())) {
				System.out.println("equaaal");
				rbev_PackOrganizerPay.setChecked(true);
			}else{
				System.out.println("customer pay!");
				rbev_PackCustomerPay.setChecked(true);

			}
			

		}
		
		
		//evpk = mForfaitController.getSingleForfaitByID(EvForfaitiD);
		
		//Let's Add the modified data to database
		 
	}


	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return super.getView();
	}
	
	
	
	 public void freeformshow(){

			et_Pack_pricex.getEditText().setText("0");
			et_Pack_pricex.setVisibility(EditText.GONE);
			et_Pack_pricex.setEnabled(false);
			tvPack_full_price.setVisibility(EditText.VISIBLE);
			tvPack_full_price.setText("Total acheteur: 0 MAD");
			etpack_fees.getEditText().setText("0");
			etpack_fees.setVisibility(EditText.GONE);
			rbev_PackCustomerPay.setVisibility(RadioButton.GONE);
			rbev_PackOrganizerPay.setVisibility(RadioButton.GONE);
	 }
	

	 

}
package com.lpii.evma.view;

import com.lpii.evma.EvmaApp;
import com.lpii.evma.MainEv;
import com.lpii.evma.R;
import com.lpii.evma.model.User;
import com.lpii.evma.view.organizer.OrganizerEvSettingsFragment;
import com.lpii.evma.view.organizer.OrganizerMainActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeStart extends Activity implements OnClickListener{

	Button btnUser;
	Button btnOrga;
	Button btnUserHx;
	Button btnUserLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnUser = (Button) findViewById(R.id.btnUserHome);
		btnOrga = (Button) findViewById(R.id.btnOrganiHome);
		btnUserHx = (Button) findViewById(R.id.btnUserHx);
		btnUserLogin = (Button) findViewById(R.id.btnUserLogin);
		System.out.println("sdfsdf");
		btnUser.setOnClickListener(this);
		btnOrga.setOnClickListener(this);
		btnUserHx.setOnClickListener(this);
		btnUserLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnUserHome) {
			EvmaApp.CurrentUsername = "Ahmedx";// adm1
			EvmaApp.CurrentUserEmail = "flahweb@gmail.com";//fidaghdour@synergie-media.com
			EvmaApp.CurrentOrganizer = "";
			EvmaApp.CurrentUsernameID = 11;
			User us = new User("11", "Ahmedx", "", "Utilisateur", "Ahmed Saadaoui", "flahweb@gmail.com", "", "");
			EvmaApp.CurrentUser = us;
			
			Intent intUser = new Intent(HomeStart.this,MainEv.class);
			startActivity(intUser);
		}else if (v.getId() == R.id.btnOrganiHome) {
			EvmaApp.CurrentOrganizer = "adm1";
			EvmaApp.CurrentUsername = "";
			EvmaApp.CurrentUsernameID = 1;

			User us = new User("1", "adm1", "", "Organisateur", "Ahmed Saadaoui", "flahweb@gmail.com", "", "");
			EvmaApp.CurrentUser = us;
			Intent intUser = new Intent(HomeStart.this,MainEv.class);
			startActivity(intUser);
		}else if (v.getId() == R.id.btnUserHx) {
			Intent intUser = new Intent(HomeStart.this,SignUp.class);
			startActivity(intUser);
		}else if (v.getId() == R.id.btnUserLogin) {
			Intent intUser = new Intent(HomeStart.this,LoginEv.class);
			startActivity(intUser);
		}
		 
		
		
		
	}
	
	

}

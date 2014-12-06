package com.lpii.evma.view.organizer;

import com.lpii.evma.R;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OrganizerEvSettingsFragment extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.organizer_ev_infos_fragment, container, false);
         
        return rootView;
    }
}

package com.example.ToDo_Docket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class add_taskFragment extends Fragment  {

   String[] status={"High Priority","Low Priority","Important","Other"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_add_task, container, false);
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter(getContext(),R.layout.spinner_cust_layout,status);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_cust_drpdon_lyt);
        Spinner spinner = view.findViewById(R.id.add_task_spinner);
        spinner.setAdapter(arrayAdapter);





        return view;
    }


}

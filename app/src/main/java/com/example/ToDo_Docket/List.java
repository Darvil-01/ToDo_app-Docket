package com.example.ToDo_Docket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.ToDo_Docket.Adaptors.List_adaptor;
import com.example.ToDo_Docket.DbHelpers.List_DPHelper;
import com.example.ToDo_Docket.Models.List_model;
import com.example.ToDo_Docket.Services.changeTableName_Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class List extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    // ArrayList<List_model> List = new ArrayList<>();
     String clist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("List");
        ActionBar actionBar = getSupportActionBar();

        showData();  /// to display when app is open


    }








    //---------------------------------------------------------------------------------------------

   /*-------------------    The following code within show data comes in floating button...  ------------

       1) showPopUp function is inbuild function to show popup menu on fab...
       2) onMenuItemClick is inbulid function to perform when item is selected in popup menu...
       3) getDate funtion is defin by me to get Seleced date from calender...using datePicker....
       4) insertDate is define by me to insert date/day in List table in database...
    */

    public void showPopUp(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // This activity implements OnMenuItemClickListener

        popup.setOnMenuItemClickListener(List.this);
        popup.inflate(R.menu.list_fabmenu);
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.today:

                insertData("Today");
                break;
            case R.id.tommarow:
                insertData("Tomorrow");
                break;
            case R.id.sel_date:
                getDate();
                break;
            case R.id.daily:

                insertData("Daily");
                break;
            default:
                break;

        }
        return true;
    }

    //

    public void getDate() {
        int curr_date, curr_month, curr_year;

        Calendar calendar = Calendar.getInstance();  //accessing current date

        curr_year = calendar.get(Calendar.YEAR); //get current/instant year
        curr_date = calendar.get(Calendar.DATE); //get current date
        curr_month = calendar.get(Calendar.MONTH); // get current month

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    if (dayOfMonth == curr_date && month==curr_month) {
                        insertData("Today");
                    } else if (dayOfMonth == curr_date + 1&&month==curr_month ) {
                        insertData("Tomorrow");
                    } else {

                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy"); //dateFormat will store date in string
                        insertData(dateFormat.format(calendar.getTime()));

                    }
                }

        }, curr_year, curr_month, curr_date);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();

    }

    public void insertData(String day) {

        final List_DPHelper helper = new List_DPHelper(this);

        int isinserted = helper.insertList( day );

        if (isinserted == 1)
            Toast.makeText(this, day + "  is inserted", Toast.LENGTH_SHORT).show();
        else if (isinserted == 2)
            Toast.makeText(this, day + " Already Existes", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, day + " is not inserted", Toast.LENGTH_SHORT).show();

        showData();

    }

    public void showData() {
        recyclerView = findViewById(R.id.list_recyview);
        ConstraintLayout constraintLayout=findViewById(R.id.list_enter_schedual);
        List_DPHelper helper = new List_DPHelper(this);

        ArrayList<List_model> list = helper.getList();
        if(!list.isEmpty()){
            constraintLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        List_adaptor list_adaptor = new List_adaptor(list, this);
        recyclerView.setAdapter(list_adaptor);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    // End of FAB operation....!!!!


    // Flowing function is to define menu in ActionBar.  All the inBuild

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addtask:
                showPopUp(findViewById(R.id.listfab));
                return true;
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
                return true;

            default:

                return false;
        }

    }


}

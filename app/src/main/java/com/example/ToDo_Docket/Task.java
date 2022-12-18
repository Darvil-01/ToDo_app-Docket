package com.example.ToDo_Docket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ToDo_Docket.Adaptors.Task_adaptor;
import com.example.ToDo_Docket.DbHelpers.Task_DbHelper;
import com.example.ToDo_Docket.Models.Task_model;
import com.example.ToDo_Docket.Services.notification_discp;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Task extends AppCompatActivity {
    LinearLayout linearLayout;
    String discp,duedate, duetime,dcator ;
    String tbname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tbname=getIntent().getStringExtra("title");
        setTitle("Task of " + tbname);


//        SQLiteDatabase db=openOrCreateDatabase(dbname,MODE_PRIVATE,null);
//        createtb(db,tbname);
        Task_DbHelper helper=new Task_DbHelper(this,tbname);
        helper.create(tbname,this);


        ImageButton taskfab=findViewById(R.id.taskfab);
        taskfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFab(tbname,0,0);

            }
        });

        ImageButton tsk_bt_hp=findViewById(R.id.tsk_bt_hp);    // can't use onckick attribute bcz we have pass tbname;
        tsk_bt_hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList(tsk_bt_hp,tbname);
            }
        });

        ImageButton tsk_bt_other=findViewById(R.id.tsk_bt_other);
        tsk_bt_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList(tsk_bt_other,tbname);
            }
        });

        ImageButton tsk_bt_lp=findViewById(R.id.tsk_bt_lp);
        tsk_bt_lp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList(tsk_bt_lp,tbname);
            }
        });

        ImageButton tsk_bt_imp=findViewById(R.id.tsk_bt_imp);
        tsk_bt_imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList(tsk_bt_imp,tbname);
            }
        });





        //------------------------------ BottomSheet code finshed -------------------------//

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_manu, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addtask:
                String title=getSupportActionBar().getTitle().toString();
                String table=title.substring(8);

                onClickFab(table,0,0);

                break;
            case R.id.settings:
                Toast.makeText(this, "Multiple selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // -------------------------------  Implementing bottomSheet  ------------------------

    public void onClickFab(String tbname,int id,int stat) {

       // Task_DbHelper helper=new Task_DbHelper();


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Task.this); // initialising bottomsheet
        bottomSheetDialog.setContentView(R.layout.bottomsheet_layout);  //setting desired layout



        EditText get_task_discp = bottomSheetDialog.findViewById(R.id.editTask_name1);
        EditText get_task_dd = bottomSheetDialog.findViewById(R.id.editText_task_dueDate1);
        EditText get_task_dt = bottomSheetDialog.findViewById(R.id.editText_task_dueTime1);
        discp= String.valueOf(get_task_discp.getText());
        Calendar calendar1 = Calendar.getInstance();

        // -------------------------  code for  Picking Date from Calender --------------------------


        bottomSheetDialog.findViewById(R.id.sel_duedate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar calendar = Calendar.getInstance();
                int curr_year = calendar.get(Calendar.YEAR);
                int curr_month = calendar.get(Calendar.MONTH);
                int curr_date = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Task.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd / MM / yyyy");
                        get_task_dd.setText(dateFormat.format(calendar.getTime()));
                        duedate= String.valueOf(get_task_dd.getText());

                        //setting selectd date,month,year in calender1 for notification

                        calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        calendar1.set(Calendar.MONTH,month);
                        calendar1.set(Calendar.YEAR,year);

                    }
                }, curr_year, curr_month, curr_date);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()); //disable all past days in datepicker
                datePickerDialog.show();

            }

        });

/// ---------------------------- code < date picking > ended here ------------------   //////


        // -------------------------  code for  Picking Date from Clock --------------------------




        bottomSheetDialog.findViewById(R.id.sel_duetime).setOnClickListener(new View.OnClickListener() {
            int hour, min;     // onClick on selecting time in bottomSheet

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Task.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        min = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, hourOfDay, minute);
                        get_task_dt.setText(DateFormat.format("HH:mm", calendar));
                        duetime= String.valueOf(get_task_dt.getText());

                        calendar1.set(Calendar.HOUR_OF_DAY,hour-1);        //setting for 1hr before notification
                        calendar1.set(Calendar.MINUTE,min);                //calender1 get set to (seleced hr-1)hr
                        calendar1.set(Calendar.SECOND,0);
                        calendar1.set(Calendar.MILLISECOND,0);


                    }
                }, hour, min, true);

                timePickerDialog.updateTime(hour, min); // for pointing the previously selected date

                timePickerDialog.show();
            }
        });


        /// ---------------------------- code < date picking > ended here ------------------   //////

        String[] status={"High Priority","Low Priority","Important","Other"};

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter(Task.this,R.layout.spinner_cust_layout,status);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_cust_drpdon_lyt);
        Spinner spinner = bottomSheetDialog.findViewById(R.id.add_task_spinner);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               dcator=(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dcator="Other";
            }
        });


        //------------------------------ getting filled data after clicking on 'Done' --------------//

        bottomSheetDialog.findViewById(R.id.addtask_done).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                
                if((discp!=null|| !discp.trim().equals(""))&& duetime!=null && dcator!=null && duedate!=null) {
                    Task_DbHelper helper = new Task_DbHelper(Task.this, tbname);
                    discp = String.valueOf(get_task_discp.getText());

                    if (id == 0) {
                        boolean done = helper.insert(discp, duedate, duetime, dcator, tbname);
                        if (done) {
                            Toast.makeText(Task.this, "New task inserted ", Toast.LENGTH_SHORT).show();
                            showTask(tbname, dcator);

                        } else
                            Toast.makeText(Task.this, "Task not inserted", Toast.LENGTH_SHORT).show();
                    } else {

                        boolean done = helper.updateTask(discp, duedate, duetime, dcator, tbname, id, stat);
                        if (done) {
                            Toast.makeText(Task.this, "Task Updated ", Toast.LENGTH_SHORT).show();
                            showTask(tbname, dcator);

                        } else
                            Toast.makeText(Task.this, "Task not updated", Toast.LENGTH_SHORT).show();

                        Toast.makeText(Task.this, discp, Toast.LENGTH_SHORT).show();

                    }
                    setAlarm(calendar1,id);

                    bottomSheetDialog.hide();
                }else
                    Toast.makeText(Task.this, "Enter the value properly", Toast.LENGTH_SHORT).show();

            }
        });

        ///// ----------------------- Ended ---------------------------------------//

        bottomSheetDialog.show();


    }

    public void showList(View view, String tbname) {
        switch (view.getId()){
            case R.id.tsk_bt_hp: linearLayout=findViewById(R.id.tsk_ll_hp);
                                 if(linearLayout.getVisibility()==View.GONE) {
                                     linearLayout.setVisibility(View.VISIBLE);
                                     ImageButton imageButton = findViewById(R.id.tsk_bt_hp);
                                     imageButton.setImageResource(R.drawable.ic_drop_up);
                                     showTask(tbname,"High Priority");

                                 }else{
                                     linearLayout.setVisibility(View.GONE);
                                     ImageButton imageButton = findViewById(R.id.tsk_bt_hp);
                                     imageButton.setImageResource(R.drawable.ic_drop_down);
                                 }
                                 break;
             case R.id.tsk_bt_lp: linearLayout=findViewById(R.id.tsk_ll_lp);
                                 if(linearLayout.getVisibility()==View.GONE) {
                                     linearLayout.setVisibility(View.VISIBLE);
                                     ImageButton imageButton = findViewById(R.id.tsk_bt_lp);
                                     imageButton.setImageResource(R.drawable.ic_drop_up);
                                     showTask(tbname,"Low Priority");
                                 }else{
                                     linearLayout.setVisibility(View.GONE);
                                     ImageButton imageButton = findViewById(R.id.tsk_bt_lp);
                                     imageButton.setImageResource(R.drawable.ic_drop_down);
                                 }
                                 break;
            case R.id.tsk_bt_imp: linearLayout=findViewById(R.id.tsk_ll_imp);
                                  if(linearLayout.getVisibility()==View.GONE) {
                                      linearLayout.setVisibility(View.VISIBLE);
                                       ImageButton imageButton = findViewById(R.id.tsk_bt_imp);
                                      imageButton.setImageResource(R.drawable.ic_drop_up);
                                      showTask(tbname,"Important");
                                  }else{
                                         linearLayout.setVisibility(View.GONE);
                                         ImageButton imageButton = findViewById(R.id.tsk_bt_imp);
                                         imageButton.setImageResource(R.drawable.ic_drop_down);
                                       }
                                  break;
            case R.id.tsk_bt_other: linearLayout=findViewById(R.id.tsk_ll_other);
                                    if(linearLayout.getVisibility()==View.GONE) {
                                        linearLayout.setVisibility(View.VISIBLE);
                                        ImageButton imageButton = findViewById(R.id.tsk_bt_other);
                                        imageButton.setImageResource(R.drawable.ic_drop_up);
                                        showTask(tbname,"Other");
                                        }else{
                                        linearLayout.setVisibility(View.GONE);
                                        ImageButton imageButton = findViewById(R.id.tsk_bt_other);
                                          imageButton.setImageResource(R.drawable.ic_drop_down);
                                        }
                                         break;

        }
    }


    public void showTask(String tbname,String catr){
        Task_DbHelper helper=new Task_DbHelper(Task.this,tbname);
        switch(catr) {
            case "High Priority":


                               ArrayList<Task_model> hp_list = helper.getList(tbname, catr);


                               if(!hp_list.isEmpty()){
                               RecyclerView recyclerView=findViewById(R.id.task_rv_hp);
                               Task_adaptor adaptor = new Task_adaptor(hp_list, Task.this,tbname,recyclerView);
                               recyclerView.setVisibility(View.VISIBLE);
                               TextView textView=findViewById(R.id.hp_notext);
                               textView.setVisibility(View.GONE);


                               recyclerView.setAdapter(adaptor);


                               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Task.this);
                               recyclerView.setLayoutManager(linearLayoutManager);

                               new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
                               }
                               else Toast.makeText(this, "Empty list", Toast.LENGTH_SHORT).show();





                                break;


            case "Low Priority":


                               ArrayList<Task_model> lp_list =helper.getList( tbname, catr);

                               if(!lp_list.isEmpty()){
                               RecyclerView recyclerView=findViewById(R.id.task_rv_lp);
                               recyclerView.setVisibility(View.VISIBLE);
                               TextView textView=findViewById(R.id.lp_notext);
                               textView.setVisibility(View.GONE);

                               Task_adaptor adaptor = new Task_adaptor(lp_list, Task.this,tbname,recyclerView);
                               recyclerView.setAdapter(adaptor);

                               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Task.this);
                               recyclerView.setLayoutManager(linearLayoutManager);

                               new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
                               }
                               else Toast.makeText(this, "Empty list", Toast.LENGTH_SHORT).show();
                                 break;

            case "Important":

                ArrayList<Task_model> imp_list =helper.getList(tbname, catr);

                if(!imp_list.isEmpty()){
                    RecyclerView recyclerView=findViewById(R.id.task_rv_imp);
                    recyclerView.setVisibility(View.VISIBLE);
                    TextView textView=findViewById(R.id.imp_notext);
                    textView.setVisibility(View.GONE);

                    Task_adaptor adaptor = new Task_adaptor(imp_list, Task.this,tbname,recyclerView);
                    recyclerView.setAdapter(adaptor);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Task.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
                }
                else Toast.makeText(this, "Empty list", Toast.LENGTH_SHORT).show();
                break;

            case "Other":

                              ArrayList<Task_model> other_list =helper.getList(tbname, catr);

                               if(!other_list.isEmpty()){
                               RecyclerView recyclerView=findViewById(R.id.task_rv_other);
                               recyclerView.setVisibility(View.VISIBLE);
                               TextView textView=findViewById(R.id.other_notext);
                               textView.setVisibility(View.GONE);

                               Task_adaptor adaptor = new Task_adaptor(other_list, Task.this,tbname,recyclerView);
                               recyclerView.setAdapter(adaptor);

                               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Task.this);
                               recyclerView.setLayoutManager(linearLayoutManager);
                                   new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
                               }
                               else Toast.makeText(this, "Empty list", Toast.LENGTH_SHORT).show();
                                 break;
        }
    }


    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            String title=getSupportActionBar().getTitle().toString();
            String table=title.substring(8);

            Task_DbHelper helper=new Task_DbHelper(Task.this,table);
            ArrayList<Task_model> list=helper.getALLList(table);

            int pos = viewHolder.getAdapterPosition();
            Task_model model = list.get(pos);
            model.getTask_id();
            switch (direction) {
                case ItemTouchHelper.LEFT:

                                          if (helper.deleteTask(model.getTask_id())) {
                                          Toast.makeText(Task.this, "Deleted ", Toast.LENGTH_SHORT).show();
                                           list.remove(viewHolder.getAdapterPosition());
                                           showTask(table,model.getCatgri());
                                           break;
                                          }

                case ItemTouchHelper.RIGHT:    onClickFab(table,model.getTask_id(),model.getStatus());

            }
        }
    };

    public void setAlarm(Calendar calendar,int id){

        if(id==0) {
            int i = new Random().nextInt(50);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, notification_discp.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        }else{
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
           Intent intent = new Intent(this, notification_discp.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


        }

    }


}
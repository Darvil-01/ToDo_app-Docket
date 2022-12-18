package com.example.ToDo_Docket.Adaptors;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.ToDo_Docket.DbHelpers.List_DPHelper;
import com.example.ToDo_Docket.DbHelpers.Task_DbHelper;
import com.example.ToDo_Docket.Models.List_model;
import com.example.ToDo_Docket.R;
import com.example.ToDo_Docket.Task;

import java.util.ArrayList;


public class List_adaptor extends RecyclerView.Adapter<List_adaptor.viewHolder>{
   ArrayList<List_model> list;
    Context context;
    String clist;

    public List_adaptor(ArrayList<List_model> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cardview,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

      List_model model=list.get(position);
      holder.textView.setText(model.getList_text());

      holder.delbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              new AlertDialog.Builder(context)
                      .setTitle("Delete")
                      .setMessage("Are you sure to Delete "+holder.textView.getText())
                      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              List_DPHelper list_helper=new List_DPHelper(context);
                              Task_DbHelper task_helper=new Task_DbHelper(context,null);



                              if( list_helper.deleteList(model.getList_text())>0) {
                                  task_helper.deleteTable(model.getList_text());
                                  Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                  list.remove(holder.getAdapterPosition());
                                  notifyItemRemoved(holder.getAdapterPosition());

                              }
                              else
                                  Toast.makeText(context, model.getList_id()+"Not able to Deleted", Toast.LENGTH_SHORT).show();
                          }
                      })
                      .setNegativeButton("No", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                          }
                      }).setIcon(R.drawable.ic_warning).show();



          }
      });

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(context, Task.class);
              intent.putExtra("title",holder.textView.getText());
              context.startActivity(intent);
          }
      });
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends ViewHolder {
       TextView textView;
       ImageView delbutton;

       public viewHolder(@NonNull View itemView) {
           super(itemView);
           textView=itemView.findViewById(R.id.list_textview);
           delbutton=itemView.findViewById(R.id.delButton);
       }
   }
}

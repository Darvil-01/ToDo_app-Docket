package com.example.ToDo_Docket.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ToDo_Docket.DbHelpers.Task_DbHelper;
import com.example.ToDo_Docket.Models.Task_model;
import com.example.ToDo_Docket.R;

import java.util.ArrayList;

public class Task_adaptor extends RecyclerView.Adapter<Task_adaptor.viewHolder>  {

    ArrayList<Task_model> list;
    Context context;
    String tbname;
    RecyclerView recyclerView;

    public Task_adaptor(ArrayList<Task_model> list, Context context,String tbname,RecyclerView recyclerView) {
        this.list = list;
        this.context = context;
        this.tbname=tbname;
        this.recyclerView=recyclerView;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_cardview,parent,false);


        return new viewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Task_model model=list.get(position);
        holder.duetime.setText(model.getTime());
        holder.duedate.setText(model.getDate());
        holder.checkBox.setText(model.getTask_discp());
        holder.checkBox.setChecked(setbol(model.getStatus()));


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Task_DbHelper helper = new Task_DbHelper(context, tbname);
                    helper.changeStatus(model.getTask_id(), 1);
                    Toast.makeText(context, "Status changed", Toast.LENGTH_SHORT).show();

                } else {
                    Task_DbHelper helper = new Task_DbHelper(context, tbname);
                    helper.changeStatus(model.getTask_id(), 0);
                }
            }
        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(context, "long press", Toast.LENGTH_SHORT).show();
//                new AlertDialog.Builder(context)
//                        .setTitle("Action")
//                        //.setMessage("Are you sure to Delete "+holder.textView.getText())
//                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                Task_DbHelper helper=new Task_DbHelper(context,tbname);
//
//
//
//                                if( helper.deleteTask(model.getTask_id())) {
//                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
//                                    list.remove(holder.getAdapterPosition());
//                                    notifyItemRemoved(holder.getAdapterPosition());
//
//                                }
//                                else
//                                    Toast.makeText(context, model.getTask_id()+"Not able to Deleted", Toast.LENGTH_SHORT).show();
//
//                                dialog.cancel();
//                            }
//
//                        })
//                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Task task=new Task();
//                               task.onClickFab(tbname);
//                                dialog.cancel();
//                            }
//                        }).setIcon(R.drawable.ic_warning).show();
//                return true;
//            }
//        });

   }





    public boolean setbol(int n){
        return n!=0;
    }




    @Override
    public int getItemCount() {
        return list.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView duedate;
        TextView duetime;
        CardView cardView;

        public viewHolder(View itemView){
            super(itemView);

            checkBox=itemView.findViewById(R.id.task_checkBox);
            duedate=itemView.findViewById(R.id.task_dueDate);
            duetime=itemView.findViewById(R.id.task_dueTime);
            cardView=itemView.findViewById(R.id.cardview);
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//            menu.add(this.getAdapterPosition(),121,0,"Delete");
//            menu.add(this.getAdapterPosition(),122,0,"Edit");
//        }
    }










}

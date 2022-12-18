package com.example.ToDo_Docket.Models;

public class Task_model {
    String task_discp;

    public Task_model(String task_discp, String date, String time, String catgri) {
        this.task_discp = task_discp;
        this.date = date;
        this.time = time;
        this.catgri = catgri;
    }

    String date,time,catgri;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    int task_id;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   int status;

    public String getTask_discp() {
        return task_discp;
    }

    public void setTask_discp(String task_discp) {
        this.task_discp = task_discp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCatgri() {
        return catgri;
    }

    public void setCatgri(String catgri) {
        this.catgri = catgri;
    }
}

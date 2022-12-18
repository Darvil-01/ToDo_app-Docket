package com.example.ToDo_Docket.Models;

public class List_model {

    String list_text;
    int list_id;
    public String getList_text() {
        return list_text;
    }

    public void setList_text(String list_text) {
        this.list_text = list_text;
    }





    public List_model(String list_text, int list_id) {
        this.list_text = list_text;
        this.list_id = list_id;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }


}

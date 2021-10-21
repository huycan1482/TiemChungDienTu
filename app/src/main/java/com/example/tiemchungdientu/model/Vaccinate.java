package com.example.tiemchungdientu.model;

import com.example.tiemchungdientu.MyInterface.OnDataChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Vaccinate extends Model {
    private String id;
    private String name;
    private String current_number;
    private String total_number;
    private String date;
    private String description;
    private String created_at;

    public Vaccinate(String id, String name, String current_number, String total_number, String date, String description, String created_at) {
        this.id = id;
        this.name = name;
        this.current_number = current_number;
        this.total_number = total_number;
        this.date = date;
        this.description = description;
        this.created_at = created_at;
    }

    public Vaccinate () {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrent_number() {
        return current_number;
    }

    public void setCurrent_number(String current_number) {
        this.current_number = current_number;
    }

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    private OnDataChangeListener mOnDataChangeListener;
    //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
    DatabaseReference MY_REF = this.DATABASE.getReference("vaccinate");

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public void getAll() {
        //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)

        //truy suất và lắng nghe sự thay đổi dữ liệu
        MY_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(mOnDataChangeListener != null){
                    mOnDataChangeListener.onDataChange(dataSnapshot);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

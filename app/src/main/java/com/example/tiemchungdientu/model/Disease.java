package com.example.tiemchungdientu.model;

import com.example.tiemchungdientu.MyInterface.OnDataChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Disease extends Model {
    private String id;
    private String name;
    private String date;
    private String description;
    private String create_at;

    public Disease(String id, String name, String date, String description, String create_at) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.create_at = create_at;
    }

    public Disease () {

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

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    private OnDataChangeListener mOnDataChangeListener;
    //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
    DatabaseReference MY_REF = this.DATABASE.getReference("diseases");

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

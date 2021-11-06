package com.example.tiemchungdientu.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tiemchungdientu.MyInterface.OnDataChangeListener;
import com.google.android.gms.common.api.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Baby extends Model {
    private String id;
    private String name;
    private String birth;
    private String gender;
    private String parent_id;
    private String created_at;

    public Baby(String name, String birth, String gender, String parent_id) {
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.parent_id = parent_id;
    }

    public Baby(String id, String name, String birth, String gender, String parent_id, String created_at) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.parent_id = parent_id;
        this.created_at = created_at;
    }

    public Baby() {

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

    public String getBirth() {
        return birth;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }


    private OnDataChangeListener mOnDataChangeListener;
    //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
    DatabaseReference MY_REF = this.DATABASE.getReference("babies");

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
    }

    public void getAll() {
        //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)

        //truy suất và lắng nghe sự thay đổi dữ liệu
        MY_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mOnDataChangeListener != null) {
                    mOnDataChangeListener.onDataChange(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void store() {
        String keyID = this.DATABASE.getReference().push().getKey();
        Baby baby = new Baby(this.name, this.birth, this.gender, this.parent_id);
        this.id = keyID;
        baby.id = keyID;
        baby.created_at = String.valueOf(Calendar.getInstance().getTimeInMillis());
        this.DATABASE.getReference().child("babies").child(keyID).setValue(baby);
    }


    public interface findByIdResponse {
        public void response(Object data);
    }

    public void findById(String id, findByIdResponse response) {
        if (MY_REF.child(id) != null) {

            Baby baby = new Baby();

            MY_REF.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals("id")) baby.setId(child.getValue().toString());
                        if (child.getKey().equals("name")) baby.setName(child.getValue().toString());
                        if (child.getKey().equals("birth")) baby.setBirth(child.getValue().toString());
                        if (child.getKey().equals("gender")) baby.setGender(child.getValue().toString());
                        if (child.getKey().equals("parent_id")) baby.setParent_id(child.getValue().toString());
                        if (child.getKey().equals("created_at")) baby.setCreated_at(child.getValue().toString());

                    }

                    response.response(baby);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    public void update() {
        Map<String, Object> childUpdates = new HashMap<>();
        Baby baby = new Baby(this.id, this.name, this.birth, this.gender, this.parent_id, this.created_at);
        childUpdates.put(this.id, baby);
        MY_REF.child(this.id).child("name").setValue(this.name);
        MY_REF.child(this.id).child("birth").setValue(this.birth);
        MY_REF.child(this.id).child("gender").setValue(this.gender);

//        FirebaseDatabase.getInstance().getReference().child("babies").updateChildren(childUpdates);
    }

    public void destroy() {
        MY_REF.child(this.id).removeValue();
    }
}

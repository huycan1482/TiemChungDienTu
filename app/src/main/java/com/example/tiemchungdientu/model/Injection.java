package com.example.tiemchungdientu.model;

import android.util.Log;

import com.example.tiemchungdientu.MyInterface.OnDataChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Injection extends Model {
    private String id;
    private String baby_id;
    private String parent_id;
    private String vaccinate_name;
    private String current_number;
    private String total_number;
    private String created_at;
    private String is_injected;
    private String note;
    private String injected_date;

    public Injection( String baby_id, String parent_id, String vaccinate_name, String current_number, String total_number, String is_injected, String note, String injected_date) {
        this.baby_id = baby_id;
        this.parent_id = parent_id;
        this.vaccinate_name = vaccinate_name;
        this.current_number = current_number;
        this.total_number = total_number;
        this.is_injected = is_injected;
        this.note = note;
        this.injected_date = injected_date;

    }

    public Injection(String id, String baby_id, String parent_id, String vaccinate_name, String current_number, String total_number, String created_at, String is_injected, String note, String injected_date) {
        this.id = id;
        this.baby_id = baby_id;
        this.parent_id = parent_id;
        this.vaccinate_name = vaccinate_name;
        this.current_number = current_number;
        this.total_number = total_number;
        this.created_at = created_at;
        this.is_injected = is_injected;
        this.note = note;
        this.injected_date = injected_date;
    }

    public Injection () {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getVaccinate_name() {
        return vaccinate_name;
    }

    public void setVaccinate_name(String vaccinate_id) {
        this.vaccinate_name = vaccinate_id;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIs_injected() {
        return is_injected;
    }

    public void setIs_injected(String is_injected) {
        this.is_injected = is_injected;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInjected_date() {
        return injected_date;
    }

    public void setInjected_date(String injected_date) {
        this.injected_date = injected_date;
    }


    private OnDataChangeListener mOnDataChangeListener;
    //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
    DatabaseReference MY_REF = this.DATABASE.getReference("injections");

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

    public void store() {
        String keyID = this.DATABASE.getReference().push().getKey();
        Injection injection = new Injection(this.baby_id, this.parent_id, this.vaccinate_name, this.current_number, this.total_number, this.is_injected, this.note, this.injected_date);
        injection.id = keyID;
        injection.created_at = String.valueOf(Calendar.getInstance().getTimeInMillis());
        this.DATABASE.getReference().child("injections").child(keyID).setValue(injection);
    }


    public interface findByIdResponse {
        public void response(Object data);
    }

    public void findById(String id, Injection.findByIdResponse response) {
        if (MY_REF.child(id) != null) {

            Injection injection = new Injection();

            MY_REF.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals("id")) injection.setId(child.getValue().toString());
                        if (child.getKey().equals("baby_id")) injection.setBaby_id(child.getValue().toString());
                        if (child.getKey().equals("parent_id")) injection.setParent_id(child.getValue().toString());
                        if (child.getKey().equals("vaccinate_name")) injection.setVaccinate_name(child.getValue().toString());
                        if (child.getKey().equals("current_number")) injection.setCurrent_number(child.getValue().toString());
                        if (child.getKey().equals("total_number")) injection.setTotal_number(child.getValue().toString());
                        if (child.getKey().equals("is_injected")) injection.setIs_injected(child.getValue().toString());
                        if (child.getKey().equals("note")) injection.setNote(child.getValue().toString());
                        if (child.getKey().equals("created_at")) injection.setCreated_at(child.getValue().toString());
                        if (child.getKey().equals("injected_date")) injection.setInjected_date(child.getValue().toString());
                    }

                    response.response(injection);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    public void update() {
        Map<String, Object> injectionUpdates = new HashMap<>();
        Injection injection = new Injection(this.id, this.baby_id, this.parent_id, this.vaccinate_name, this.current_number, this.total_number, this.is_injected, this.note, this.injected_date, this.created_at);
        injectionUpdates.put(this.id, injection);
//        FirebaseDatabase.getInstance().getReference().child("injections").updateChildren(injectionUpdates);
        MY_REF.child(this.id).child("is_injected").setValue(this.is_injected);
        MY_REF.child(this.id).child("vaccinate_name").setValue(this.vaccinate_name);
        MY_REF.child(this.id).child("injected_date").setValue(this.injected_date);
        MY_REF.child(this.id).child("note").setValue(this.note);

    }
//
//    public void destroy() {
//        MY_REF.child(this.id).removeValue();
//    }
}

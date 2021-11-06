package com.example.tiemchungdientu.model;

import android.util.Log;

import com.example.tiemchungdientu.MyInterface.OnDataChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User extends Model {
    private String id;
    private String name;
    private String phone;
    private String password;

    public User(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public User () {

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    private OnDataChangeListener mOnDataChangeListener;
    //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
    DatabaseReference MY_REF = this.DATABASE.getReference("users");

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
    }

    public interface checkUserLoginResponse {
        public void response(Object data);
    }

    public void checkUserLogin(String phone, String password,checkUserLoginResponse response) {
        User user = new User();
        MY_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    if (child.child("phone").getValue().toString().equals(phone) && child.child("password").getValue().toString().equals(password)) {
                        user.setId(child.child("id").getValue().toString());
                        user.setName(child.child("name").getValue().toString());
                        user.setPassword(child.child("password").getValue().toString());
                        user.setPhone(child.child("phone").getValue().toString());

                    }
                }
                response.response(user);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public interface findByIdResponse {
        public void response(Object data);
    }

    public void findById(String id, User.findByIdResponse response) {
        if (MY_REF.child(id) != null) {

            User user = new User();

            MY_REF.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals("id")) user.setId(child.getValue().toString());
                        if (child.getKey().equals("name")) user.setName(child.getValue().toString());
                        if (child.getKey().equals("phone")) user.setPhone(child.getValue().toString());
                        if (child.getKey().equals("password")) user.setPassword(child.getValue().toString());

                    }

                    response.response(user);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    public void store() {
        String keyID = this.DATABASE.getReference().push().getKey();
        User user = new User(this.name, this.phone, this.password);
        this.id = keyID;
        user.id = keyID;
        this.DATABASE.getReference().child("users").child(keyID).setValue(user);
    }

    public void update() {
        Map<String, Object> childUpdates = new HashMap<>();
        User user = new User(this.name, this.phone, this.password);
        childUpdates.put(this.id, user);
        MY_REF.child(this.id).child("name").setValue(this.name);
        MY_REF.child(this.id).child("phone").setValue(this.phone);
        MY_REF.child(this.id).child("password").setValue(this.password);

//        FirebaseDatabase.getInstance().getReference().child("babies").updateChildren(childUpdates);
    }
}

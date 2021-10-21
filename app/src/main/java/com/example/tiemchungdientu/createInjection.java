package com.example.tiemchungdientu;


import com.example.tiemchungdientu.MyInterface.OnDataChangeListener;
import com.example.tiemchungdientu.model.Injection;
import com.example.tiemchungdientu.model.Vaccinate;
import com.google.firebase.database.DataSnapshot;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class createInjection implements OnDataChangeListener {

    String mBabyId;
    String mBabyBirth;

    public createInjection (String babyId, String babyBirth) {
        mBabyId = babyId;
        mBabyBirth = babyBirth;
        Vaccinate vaccine = new Vaccinate();
        vaccine.setOnDataChangeListener(this);
        vaccine.getAll();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        for (DataSnapshot data : dataSnapshot.getChildren()) {

            if (!data.getKey().equals("0")) {
//                Vaccinate vaccinate = new Vaccinate();
//                vaccinate.setId(data.child("id").getValue().toString());
//                vaccinate.setName(data.child("name").getValue().toString());
//                vaccinate.setCreated_at(data.child("created_at").getValue().toString());
//                vaccinate.setCurrent_number(data.child("current_number").getValue().toString());
//                vaccinate.setDescription(data.child("decription").getValue().toString());
//                vaccinate.setTotal_number(data.child("total_number").getValue().toString());
//                vaccinate.setDate(data.child("date").getValue().toString());

                Injection injection =  new Injection();
                injection.setBaby_id(mBabyId);
                injection.setCurrent_number(data.child("current_number").getValue().toString());
                injection.setParent_id("1");
                injection.setTotal_number(data.child("total_number").getValue().toString());
                injection.setVaccinate_name(data.child("name").getValue().toString());
                injection.setIs_injected("0");
                injection.setNote(" ");
                injection.setInjected_date("0");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Calendar c1 = Calendar.getInstance();

                Date date = Date.valueOf(mBabyBirth);

                c1.setTime(date);

                c1.add(Calendar.MONTH, Integer.parseInt(data.child("date").getValue().toString()));

                injection.setInjected_date(dateFormat.format(c1.getTime()));

                injection.store();

            }

        }

    }

}

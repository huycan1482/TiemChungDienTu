package com.example.tiemchungdientu.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiemchungdientu.MyApplication;
import com.example.tiemchungdientu.MyInterface.OnDataChangeListener;
import com.example.tiemchungdientu.MyInterface.OnItemAdapterClickListener;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.model.Baby;
import com.google.firebase.database.DataSnapshot;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BabyViewHolder> implements OnDataChangeListener {
    public ArrayList<Baby> mBabies;

    private OnItemAdapterClickListener mOnItemAdapterClickListener;

    public void setOnItemAdapterClickListener(OnItemAdapterClickListener onItemAdapterClickListener){
        mOnItemAdapterClickListener = onItemAdapterClickListener;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mBabies.clear();
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            if (!data.getKey().equals("0")) {
                Baby baby = new Baby();

                baby.setId(data.child("id").getValue().toString());
                baby.setName(data.child("name").getValue().toString());
                baby.setBirth(data.child("birth").getValue().toString());
                baby.setParent_id(data.child("parent_id").getValue().toString());
                baby.setGender(data.child("gender").getValue().toString());
                baby.setCreated_at(data.child("created_at").getValue().toString());

                if (data.child("parent_id").getValue().toString().equals(MyApplication.mUserId)) {
                    mBabies.add(baby);
                }

            }

        }
        notifyDataSetChanged();
    }

    class BabyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mID;
        public TextView mName;
        public TextView mDate;
        public ImageView mImage;

        public BabyViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.baby_name);
            mDate = itemView.findViewById(R.id.baby_date);
            mID = itemView.findViewById(R.id.baby_id);
            mImage = itemView.findViewById(R.id.baby_image);

            itemView.setOnClickListener(this);
        }


        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onClick(View view) {
            if (mOnItemAdapterClickListener != null) {
                Bundle bundle = new Bundle();
                bundle.putString("babyId", mID.getText().toString());
                mOnItemAdapterClickListener.onItemAdapterClick(view, bundle);
            }
        }

//        public void bindView (String name, String date, String id) {
//            mName.setText(name);
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//            Calendar c1 = Calendar.getInstance();
//
//            Date date1 = Date.valueOf(date);
//
//            c1.setTime(date1);
//
//            mDate.setText(dateFormat.format(c1.getTime()));
//            mID.setText(id);
//        }
    }

    public HomeAdapter () {
        mBabies = new ArrayList<>();
        Baby baby = new Baby();
        baby.setOnDataChangeListener(this);
        baby.getAll();
    }


    @NonNull
    @Override
    public BabyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //        kh???i t???o viewholder ????? ????? d??? li???u v??o view
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.babies_item, parent, false);
        //        n???p d??? li???u cho RecyclerView l?? wordlist_item
        return new BabyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BabyViewHolder holder, int position) {
//        holder.bindView( mBabies.get(position).getName(), mBabies.get(position).getBirth(), mBabies.get(position).getId());

        holder.mImage.setImageResource( ( mBabies.get(position).getGender().toString().equals("1")) ? R.drawable.baby_boy : R.drawable.babygirl);
        holder.mName.setText(mBabies.get(position).getName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Calendar c1 = Calendar.getInstance();

        Date date1 = Date.valueOf(mBabies.get(position).getBirth());

        c1.setTime(date1);

        holder.mDate.setText(dateFormat.format(c1.getTime()));
        holder.mID.setText(mBabies.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return mBabies.size();
    }
}

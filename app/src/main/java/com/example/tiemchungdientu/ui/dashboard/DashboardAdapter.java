package com.example.tiemchungdientu.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.example.tiemchungdientu.model.Injection;
import com.example.tiemchungdientu.model.Vaccinate;
import com.example.tiemchungdientu.ui.home.HomeAdapter;
import com.example.tiemchungdientu.ui.notInjected.NotInjectedAdapter;
import com.google.firebase.database.DataSnapshot;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.VaccinateViewHolder> implements OnDataChangeListener {
    public ArrayList<Vaccinate> mVaccines;

    private OnItemAdapterClickListener mOnItemAdapterClickListener;

    public void setOnItemAdapterClickListener(OnItemAdapterClickListener onItemAdapterClickListener){
        mOnItemAdapterClickListener = onItemAdapterClickListener;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mVaccines.clear();
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            if (!data.getKey().equals("0")) {
                Vaccinate vaccinate = new Vaccinate();

                vaccinate.setDate(data.child("date").getValue().toString());
                vaccinate.setDescription(data.child("description").getValue().toString());
                vaccinate.setCreated_at(data.child("created_at").getValue().toString());
                vaccinate.setName(data.child("name").getValue().toString());
                vaccinate.setId(data.child("id").getValue().toString());
                vaccinate.setCurrent_number(data.child("current_number").getValue().toString());
                vaccinate.setTotal_number(data.child("total_number").getValue().toString());

                mVaccines.add(vaccinate);

            }

        }
        notifyDataSetChanged();
    }

    class VaccinateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mID;
        public TextView mName;
        public TextView mDate;
        public ImageView mImage;

        public VaccinateViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.notInjectedName);
            mDate = itemView.findViewById(R.id.notInjectedDate);
            mID = itemView.findViewById(R.id.notInjectedId);
            mImage = itemView.findViewById(R.id.notInjected_image);

            Collections.sort(mVaccines, new Comparator<Vaccinate>() {
                @Override
                public int compare(Vaccinate vaccinate1, Vaccinate vaccinate2) {
                    if (Long.parseLong(vaccinate1.getDate()) < Long.parseLong(vaccinate2.getDate())) {
                        return 1;
                    } else {
                        if (Long.parseLong(vaccinate1.getDate()) == Long.parseLong(vaccinate2.getDate())) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                }
            });
//            S???p x???p th??ng gi???m ?????n

            itemView.setOnClickListener(this);
        }


        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onClick(View view) {
            if (mOnItemAdapterClickListener != null) {
                Bundle bundle = new Bundle();
                bundle.putString("injectionId", mID.getText().toString());
                mOnItemAdapterClickListener.onItemAdapterClick(view, bundle);
            }
        }

    }

    public DashboardAdapter () {
        mVaccines = new ArrayList<>();
        Vaccinate vaccinate = new Vaccinate();
        vaccinate.setOnDataChangeListener(this);
        vaccinate.getAll();
    }


    @NonNull
    @Override
    public DashboardAdapter.VaccinateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //        kh???i t???o viewholder ????? ????? d??? li???u v??o view
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_injected_item, parent, false);
        //        n???p d??? li???u cho RecyclerView l?? wordlist_item
        return new DashboardAdapter.VaccinateViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.VaccinateViewHolder holder, int position) {
        holder.mID.setText(mVaccines.get(position).getId());
        holder.mName.setText(mVaccines.get(position).getName());
        holder.mDate.setText("Th???i gian ti??m: " + mVaccines.get(position).getDate() + " th??ng");
//        holder.mImage.setImageResource( mInjections.get(position).getIs_injected().equals("1") ? R.drawable.needle_green : R.drawable.needle_red);
    }

    @Override
    public int getItemCount() {
        return mVaccines.size();
    }
}

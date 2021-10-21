package com.example.tiemchungdientu.ui.injected;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiemchungdientu.Converters;
import com.example.tiemchungdientu.MyInterface.OnDataChangeListener;
import com.example.tiemchungdientu.MyInterface.OnItemAdapterClickListener;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.model.Injection;
import com.example.tiemchungdientu.ui.notInjected.NotInjectedAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class InjectedAdapter extends RecyclerView.Adapter<InjectedAdapter.InjectedViewHolder> implements OnDataChangeListener {
    public ArrayList<Injection> mInjections;

    private OnItemAdapterClickListener mOnItemAdapterClickListener;

    private String mCurrentBaby;

    public void setOnItemAdapterClickListener(OnItemAdapterClickListener onItemAdapterClickListener){
        mOnItemAdapterClickListener = onItemAdapterClickListener;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mInjections.clear();
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            long dateStamp = Converters.stringToDatestamp(data.child("injected_date").getValue().toString());

            Calendar now = Calendar.getInstance();
            now.set(Calendar.HOUR_OF_DAY, 0);
            now.clear(Calendar.MINUTE);
            now.clear(Calendar.SECOND);
            now.clear(Calendar.MILLISECOND);

            if (data.child("baby_id").getValue().toString().equals(mCurrentBaby)
                    && (dateStamp < now.getTimeInMillis()) ) {
                Injection injection = new Injection();

                injection.setId(data.child("id").getValue().toString());
                injection.setVaccinate_name(data.child("vaccinate_name").getValue().toString());
                Calendar finalDate = Converters.stringToCalendar(data.child("injected_date").getValue().toString());
                injection.setInjected_date(Converters.calendarToString_VI(finalDate));
                injection.setCreated_at(data.child("created_at").getValue().toString());
                injection.setIs_injected(data.child("is_injected").getValue().toString());
                mInjections.add(injection);
            }
        }

        notifyDataSetChanged();
    }

    class InjectedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mID;
        public TextView mName;
        public TextView mDate;
        public ImageView mImage;

        public InjectedViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.injectedName);
            mDate = itemView.findViewById(R.id.injectedDate);
            mID = itemView.findViewById(R.id.injectedId);
            mImage = itemView.findViewById(R.id.injected_image);

            Collections.sort(mInjections, new Comparator<Injection>() {
                @Override
                public int compare(Injection injection1, Injection injection2) {
                    if (Long.parseLong(injection1.getCreated_at()) < Long.parseLong(injection2.getCreated_at())) {
                        return 1;
                    } else {
                        if (Long.parseLong(injection1.getCreated_at()) == Long.parseLong(injection2.getCreated_at())) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                }
            });

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

    public InjectedAdapter (String currentBaby) {
        mCurrentBaby = currentBaby;
        mInjections = new ArrayList<>();
        Injection injection = new Injection();
        injection.setOnDataChangeListener(this);
        injection.getAll();
    }


    @NonNull
    @Override
    public InjectedAdapter.InjectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //        khởi tạo viewholder để đổ dữ liệu vào view
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.injected_item, parent, false);
        //        nạp dữ liệu cho RecyclerView là wordlist_item
        return new InjectedAdapter.InjectedViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InjectedAdapter.InjectedViewHolder holder, int position) {
        holder.mID.setText(mInjections.get(position).getId());
        holder.mName.setText(mInjections.get(position).getVaccinate_name());
        holder.mDate.setText("Thời gian tiêm: " + mInjections.get(position).getInjected_date());
        holder.mImage.setImageResource( mInjections.get(position).getIs_injected().equals("1") ? R.drawable.needle_green : R.drawable.needle_red);
    }

    @Override
    public int getItemCount() {
        return mInjections.size();
    }
}

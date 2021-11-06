package com.example.tiemchungdientu.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiemchungdientu.MyInterface.Navigate;
import com.example.tiemchungdientu.MyInterface.OnItemAdapterClickListener;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements OnItemAdapterClickListener, View.OnClickListener {

    private HomeAdapter listBabyAdapter;
    Navigate mNavigate;

    ImageButton mAddBabyBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView babies = view.findViewById(R.id.list_babies);

        mAddBabyBtn = view.findViewById(R.id.addBabyBtn);
        mAddBabyBtn.setOnClickListener(this);

        listBabyAdapter = new HomeAdapter();
        babies.setAdapter(listBabyAdapter);

        babies.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if(getContext() instanceof Navigate){
            mNavigate = (Navigate) getContext();
        }

        listBabyAdapter.setOnItemAdapterClickListener(this);

        return view;
    }

    @Override
    public void onItemAdapterClick(View v, Bundle bundle) {
        navigateTo(R.id.action_navigation_home_to_navigation_updateBaby, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

    public void navigateTo(int actionId, Bundle bundle){
        mNavigate.navigate(actionId, bundle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBabyBtn:
                navigateTo( R.id.action_navigation_home_to_navigation_createBaby ,new Bundle());
                break;
            default:
                break;
        }

    }
}
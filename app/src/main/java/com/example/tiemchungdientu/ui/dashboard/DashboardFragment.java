package com.example.tiemchungdientu.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiemchungdientu.MyInterface.Navigate;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.databinding.FragmentDashboardBinding;
import com.example.tiemchungdientu.ui.notInjected.NotInjectedAdapter;
import com.example.tiemchungdientu.ui.updateBaby.UpdateBabyFragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private DashboardAdapter dashboardAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_not_injected, container, false);
        RecyclerView notInjected = view.findViewById(R.id.list_notInjected);

        dashboardAdapter = new DashboardAdapter();
        notInjected.setAdapter(dashboardAdapter);

        notInjected.setLayoutManager(new LinearLayoutManager(view.getContext()));

//        notInjectedAdapter.setOnItemAdapterClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
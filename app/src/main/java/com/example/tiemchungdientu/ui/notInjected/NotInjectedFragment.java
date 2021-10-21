package com.example.tiemchungdientu.ui.notInjected;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiemchungdientu.MyInterface.Navigate;
import com.example.tiemchungdientu.MyInterface.OnItemAdapterClickListener;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.ui.updateBaby.UpdateBabyFragment;

public class NotInjectedFragment extends Fragment implements OnItemAdapterClickListener, View.OnClickListener {

    private NotInjectedAdapter notInjectedAdapter;
    Navigate mNavigate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        View view = inflater.inflate(R.layout.fragment_not_injected, container, false);
        RecyclerView notInjected = view.findViewById(R.id.list_notInjected);

        notInjectedAdapter = new NotInjectedAdapter( bundle.getString(UpdateBabyFragment.CURRENT_BABY_ID));
        notInjected.setAdapter(notInjectedAdapter);

        notInjected.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if(getContext() instanceof Navigate){
            mNavigate = (Navigate) getContext();
        }

        notInjectedAdapter.setOnItemAdapterClickListener(this);

        return view;
    }

    @Override
    public void onItemAdapterClick(View v, Bundle bundle) {
        navigateTo(R.id.action_navigation_notInjected_to_navigation_changeInjected, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
package com.example.tiemchungdientu.ui.injected;

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
import com.example.tiemchungdientu.ui.notInjected.NotInjectedAdapter;
import com.example.tiemchungdientu.ui.updateBaby.UpdateBabyFragment;


public class InjectedFragment extends Fragment implements OnItemAdapterClickListener, View.OnClickListener  {

    private InjectedAdapter injectedAdapter;
    Navigate mNavigate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        View view = inflater.inflate(R.layout.fragment_injected, container, false);
        RecyclerView notInjected = view.findViewById(R.id.list_notInjected);

        injectedAdapter = new InjectedAdapter( bundle.getString(UpdateBabyFragment.CURRENT_BABY_ID));
        notInjected.setAdapter(injectedAdapter);

        notInjected.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if(getContext() instanceof Navigate){
            mNavigate = (Navigate) getContext();
        }

        injectedAdapter.setOnItemAdapterClickListener(this);

        return view;
    }

    @Override
    public void onItemAdapterClick(View v, Bundle bundle) {
        navigateTo(R.id.action_navigation_injected_to_navigation_changeInjected, bundle);
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
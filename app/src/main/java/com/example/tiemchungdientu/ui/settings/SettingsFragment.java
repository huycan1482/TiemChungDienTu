package com.example.tiemchungdientu.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tiemchungdientu.LoginActivity;
import com.example.tiemchungdientu.MainActivity;
import com.example.tiemchungdientu.MyInterface.Navigate;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.model.Baby;
import com.example.tiemchungdientu.model.User;
//import com.example.tiemchungdientu.databinding.FragmentSettingsBinding;

import java.util.Calendar;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private User mCurrentUser = new User();

    private TextView mUpdateUserPhone;
    private TextView mUpdateUserPassword;

    private Button mLogoutBtn;
    private Button mUpdateUserBtn;

    Navigate mNavigate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        if(getContext() instanceof Navigate){
            mNavigate = (Navigate) getContext();
        }

        mUpdateUserPhone = view.findViewById(R.id.updateUserPhone);
        mUpdateUserPassword = view.findViewById(R.id.updateUserPassword);

        mLogoutBtn = view.findViewById(R.id.logoutBtn);
        mUpdateUserBtn = view.findViewById(R.id.updateUserBtn);

        mLogoutBtn.setOnClickListener(this);
        mUpdateUserBtn.setOnClickListener(this);


        SharedPreferences preferences = getContext().getSharedPreferences("HUY", getContext().MODE_PRIVATE);
        String userId = preferences.getString("USER_ID", null);

        mCurrentUser.findById(userId, new User.findByIdResponse() {
            @Override
            public void response(Object data) {
                mCurrentUser = (User) data;
//                mUpdateUserPassword.setText(mCurrentUser.getPassword());
                mUpdateUserPhone.setText(mCurrentUser.getPhone());
            }
        });


        return view;
    }

    public void navigateTo(int actionId, Bundle bundle){
        mNavigate.navigate(actionId, bundle);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logoutBtn) {
            SharedPreferences preferences = getContext().getSharedPreferences("HUY", getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USER_ID", null);
            editor.commit();

            Intent intent = new Intent();

            intent.setClass(view.getContext(), LoginActivity.class);

            startActivity(intent);
        }

        if (view.getId() == R.id.updateUserBtn) {
            if (!mUpdateUserPassword.getText().equals("")) {
                mCurrentUser.setPassword(mUpdateUserPassword.getText().toString());
            }

            mCurrentUser.setPhone(mUpdateUserPhone.getText().toString());

            mCurrentUser.update();

            displayToast("Sửa tài khoản thành công");

            navigateTo(R.id.action_navigation_setting_to_navigation_home, new Bundle());

        }
    }

    public void displayToast(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}
package com.example.tiemchungdientu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tiemchungdientu.MyInterface.Navigate;
import com.example.tiemchungdientu.databinding.ActivityMainBinding;
import com.example.tiemchungdientu.model.Baby;
import com.example.tiemchungdientu.model.User;

import java.lang.annotation.Native;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Navigate {
    private ActivityMainBinding binding;

    private static User sCurrentUser = new User();

    NavController navController;
    AppBarConfiguration mAppBarConfiguration;

    private TextView mUserPhone;
    private TextView mUserPass;
    private TextView mSignUp;

    private TextView mNotification;

    private Button mLoginBtn;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        SharedPreferences preferences = getSharedPreferences("HUY", MODE_PRIVATE);
        String userId = preferences.getString("USER_ID", null);
        if(userId != null){
            startActivity(new Intent(this, MainActivity.class));
            MyApplication.mUserId = userId;
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        mUserPhone = findViewById(R.id.userPhone);
        mUserPass = findViewById(R.id.userPass);
        mSignUp = findViewById(R.id.txtSingup);

        mNotification = findViewById(R.id.notification);

        mLoginBtn = findViewById(R.id.loginBtn);

        mSignUp.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);

//        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginBtn) {
            User user = new User();
            user.checkUserLogin(mUserPhone.getText().toString(), mUserPass.getText().toString(), new User.checkUserLoginResponse() {
                @Override
                public void response(Object data) {
                    sCurrentUser = (User) data;

                    if (sCurrentUser.getName() == null) {

                        mNotification.setText("Nhập sai tài khoản hoặc mật khẩu");

                    } else {

                        SharedPreferences preferences = getSharedPreferences("HUY", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("USER_ID", sCurrentUser.getId());
                        editor.commit();

                        MyApplication.mUserId = sCurrentUser.getId();


                        Intent intent = new Intent();

                        intent.setClass(view.getContext(), MainActivity.class);

                        startActivity(intent);

                        finish();
                    }
                }
            });
        }

        if (view.getId() == R.id.txtSingup) {
            Intent intent = new Intent();

            intent.setClass(this, RegisterActivity.class);

            startActivity(intent);
        }
    }

    @Override
    public void navigate(int actionId, Bundle bundle) {

    }
}
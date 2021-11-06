package com.example.tiemchungdientu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiemchungdientu.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSignup;

    private TextView mUserName;
    private TextView mPass;
    private TextView mTextView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mUserName = findViewById(R.id.username);
        mPass = findViewById(R.id.pass);
        mTextView6 = findViewById(R.id.textView6);

        mSignup = findViewById(R.id.btnSignup);

        mSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignup) {
            if (!mUserName.getText().toString().equals("") && !mPass.getText().toString().equals("")) {

                User user = new User();
                user.setName(mUserName.getText().toString());
                user.setPhone(mUserName.getText().toString());
                user.setPassword(mPass.getText().toString());
                user.store();

                finish();
            } else {
                mTextView6.setText("Yêu cầu nhập đủ thông tin");
            }
        }
    }
}

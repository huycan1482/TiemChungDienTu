package com.example.tiemchungdientu.ui.createBaby;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tiemchungdientu.Converters;
import com.example.tiemchungdientu.MyInterface.Navigate;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.model.Baby;
import com.example.tiemchungdientu.createInjection;

import java.util.Calendar;


public class CreateBabyFragment extends Fragment implements View.OnClickListener {
    private EditText mCreateBabyBirth;
    private EditText mCreateBabyName;

    private Button mCreateBabyBtn;

    private RadioButton mCreateBabyFemale;
    private RadioButton mCreateBabyMale;
    private RadioGroup mCreateBabyGender;

    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;

    Navigate mNavigate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_baby, container, false);

        if(getContext() instanceof Navigate){
            mNavigate = (Navigate) getContext();
        }

        mCreateBabyName = view.findViewById(R.id.createBabyName);
        mCreateBabyBirth = view.findViewById(R.id.createBabyBirth);

        mCreateBabyBtn = view.findViewById(R.id.createBtnTest);

        mCreateBabyFemale = view.findViewById(R.id.createBabyFemale);
        mCreateBabyMale = view.findViewById(R.id.createBabyMale);
//        mCreateBabyFemale.setOnCheckedChangeListener(listenerRadio);

        mCreateBabyBtn.setOnClickListener(this);

        mCreateBabyBirth.setOnClickListener(this);

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mCreateBabyBirth.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);


        return view;
    }

    public void navigateTo(int actionId, Bundle bundle){
        mNavigate.navigate(actionId, bundle);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.createBabyBirth){
            showDatePickerDialog();
        }

        if(view.getId() == R.id.createBtnTest) {

            Baby baby = new Baby();

            baby.setName(mCreateBabyName.getText().toString());
            baby.setBirth(Converters.string_VNToString_US(mCreateBabyBirth.getText().toString()));

            String[] splitsDate = mCreateBabyBirth.getText().toString().split("/");
            baby.setBirth(splitsDate[2] + "-" + splitsDate[1] + "-" + splitsDate[0]);
            baby.setParent_id("0");

            if (mCreateBabyFemale.isChecked()) {
                baby.setGender("0");
            }

            if (mCreateBabyMale.isChecked()) {
                baby.setGender("1");
            }

            baby.store();

            createInjection test = new createInjection(baby.getId(), baby.getBirth());

            displayToast("Thêm thành công");

            navigateTo(R.id.action_navigation_createBaby_to_navigation_home, new Bundle());

        }
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker v, int y, int m, int d) {
                        mCreateBabyBirth.setText(d + "/" + (m + 1) + "/" + y);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void displayToast(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
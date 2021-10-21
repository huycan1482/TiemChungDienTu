package com.example.tiemchungdientu.ui.updateBaby;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tiemchungdientu.Converters;
import com.example.tiemchungdientu.MyInterface.Navigate;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.model.Baby;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateBabyFragment extends Fragment implements View.OnClickListener {

    private static Baby sCurrentBaby = new Baby();

    private EditText mUpdateBabyBirth;
    private EditText mUpdateBabyName;

    private ImageView mUpdateBabyImage;

    private Button mUpdateBabyBtn;
    private Button mDeleteBabyBtn;

    private FloatingActionButton mFloatingBtnInjectedBaby;
    private FloatingActionButton mFloatingBtnNotInjectedBaby;

    private RadioButton mUpdateBabyFemale;
    private RadioButton mUpdateBabyMale;
    private RadioGroup mUpdateBabyGender;

    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;

    Navigate mNavigate;

    private String mCurrentBaby;

    public static final String CURRENT_BABY_ID = "CURRENT_BABY_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_baby, container, false);

        if(getContext() instanceof Navigate){
            mNavigate = (Navigate) getContext();
        }

        mUpdateBabyBirth = view.findViewById(R.id.updateBabyBirth);
        mUpdateBabyName = view.findViewById(R.id.updateBabyName);
        mUpdateBabyFemale = view.findViewById(R.id.updateBabyFemale);
        mUpdateBabyMale = view.findViewById(R.id.updateBabyMale);
        mUpdateBabyImage = view.findViewById(R.id.updateBabyImage);

        mUpdateBabyBtn = view.findViewById(R.id.updateBabyBtn);
        mDeleteBabyBtn = view.findViewById(R.id.deleteBabyBtn);

        mFloatingBtnInjectedBaby = view.findViewById(R.id.FloatingBtnInjectedBaby);
        mFloatingBtnNotInjectedBaby = view.findViewById(R.id.FloatingBtnNotInjectedBaby);

        Bundle bundle = this.getArguments();

        mCurrentBaby = bundle.getString("babyId");

        sCurrentBaby.findById(bundle.getString("babyId"), new Baby.findByIdResponse() {
            @Override
            public void response(Object data) {
                sCurrentBaby = (Baby) data;

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                Calendar c1 = Calendar.getInstance();

                Date date1 = Date.valueOf(sCurrentBaby.getBirth());

                c1.setTime(date1);

                mUpdateBabyBirth.setText(dateFormat.format(c1.getTime()));

                mUpdateBabyName.setText(sCurrentBaby.getName());
                if (sCurrentBaby.getGender().equals("1")) {
                    mUpdateBabyMale.setChecked(true);
                    mUpdateBabyImage.setImageResource(R.drawable.baby_boy);
                } else {
                    mUpdateBabyFemale.setChecked(true);
                    mUpdateBabyImage.setImageResource(R.drawable.babygirl);
                }

            }
        });

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        mUpdateBabyBtn.setOnClickListener(this);
        mDeleteBabyBtn.setOnClickListener(this);

        mFloatingBtnInjectedBaby.setOnClickListener(this);
        mFloatingBtnNotInjectedBaby.setOnClickListener(this);

        mUpdateBabyBirth.setOnClickListener(this);

        return view;
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker v, int y, int m, int d) {
                        mUpdateBabyBirth.setText(d + "/" + (m + 1) + "/" + y);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void navigateTo(int actionId, Bundle bundle){
        mNavigate.navigate(actionId, bundle);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.updateBabyBirth) {
            showDatePickerDialog();
        }

        if(view.getId() == R.id.updateBabyBtn) {
            sCurrentBaby.setName(mUpdateBabyName.getText().toString());
            sCurrentBaby.setBirth(Converters.string_VNToString_US(mUpdateBabyBirth.getText().toString()));

            if (mUpdateBabyFemale.isChecked()) {
                sCurrentBaby.setGender("0");
            } else {
                sCurrentBaby.setGender("1");
            }

            sCurrentBaby.update();

            displayToast("Sửa thành công");

            navigateTo(R.id.action_navigation_updateBaby_to_navigation_home, new Bundle());

        }

        if(view.getId() == R.id.deleteBabyBtn) {
            sCurrentBaby.destroy();

            displayToast("Xóa thành công");

            navigateTo(R.id.action_navigation_updateBaby_to_navigation_home, new Bundle());
        }

        if (view.getId() == R.id.FloatingBtnInjectedBaby) {
            displayToast("Danh sách các mũi quá ngày tiêm");
            Bundle bundle = new Bundle();
            bundle.putString(CURRENT_BABY_ID, mCurrentBaby);
            navigateTo(R.id.action_navigation_updateBaby_to_navigation_injected, bundle);
        }

        if (view.getId() == R.id.FloatingBtnNotInjectedBaby) {
            displayToast("Danh sách các mũi chưa đến ngày tiêm");
            Bundle bundle = new Bundle();
            bundle.putString(CURRENT_BABY_ID, mCurrentBaby);
            navigateTo(R.id.action_navigation_updateBaby_to_navigation_notInjected, bundle);

        }

    }

    public void displayToast(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
package com.example.tiemchungdientu.ui.changeInjected;

import static com.example.tiemchungdientu.R.drawable.needle_green;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tiemchungdientu.Converters;
import com.example.tiemchungdientu.MyInterface.Navigate;
import com.example.tiemchungdientu.R;
import com.example.tiemchungdientu.model.Baby;
import com.example.tiemchungdientu.model.Injection;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ChangeInjectedFragment extends Fragment implements View.OnClickListener {

    private static Injection sCurrentInjection = new Injection();

    private String mCurrentInjectionId;

    private EditText mVaccinateName;
    private EditText mVaccinateDate;
    private EditText mVaccinateNote;

    private RadioButton mVaccinateInjected;
    private RadioButton mVaccinateNotInjected;

    private ImageView mVaccinateImage;

    private Button mUpdateVaccinateBtn;
    private Button mCancelVaccinateBtn;

    Navigate mNavigate;

    public static final String CURRENT_BABY_ID = "CURRENT_BABY_ID";

    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_injected, container, false);

        if(getContext() instanceof Navigate){
            mNavigate = (Navigate) getContext();
        }

        Bundle bundle = this.getArguments();

        mCurrentInjectionId = bundle.getString("injectionId");

        mVaccinateName = view.findViewById(R.id.updateVaccineName);
        mVaccinateDate = view.findViewById(R.id.updateVaccineDate);
        mVaccinateNote = view.findViewById(R.id.updateVaccinateNote);
        mVaccinateInjected = view.findViewById(R.id.updateVaccinateInjected);
        mVaccinateNotInjected = view.findViewById(R.id.updateVaccinateNotInjected);
        mVaccinateImage = view.findViewById(R.id.updateVaccinateImage);

        mUpdateVaccinateBtn = view.findViewById(R.id.updateVaccinateBtn);
        mCancelVaccinateBtn = view.findViewById(R.id.cancelVaccinateBtn);

        mUpdateVaccinateBtn.setOnClickListener(this);
        mCancelVaccinateBtn.setOnClickListener(this);

        sCurrentInjection.findById(bundle.getString("injectionId"), new Injection.findByIdResponse() {
            @Override
            public void response(Object data) {
                sCurrentInjection = (Injection) data;

                mVaccinateName.setText(sCurrentInjection.getVaccinate_name());
                mVaccinateNote.setText(sCurrentInjection.getNote());
                mVaccinateDate.setText(Converters.calendarToString_VI(Converters.stringToCalendar(sCurrentInjection.getInjected_date())));

                if (sCurrentInjection.getIs_injected().equals("1")) {
                    mVaccinateInjected.setChecked(true);
                    mVaccinateImage.setImageResource(R.drawable.needle_green);
                } else {
                    mVaccinateNotInjected.setChecked(true);
                    mVaccinateImage.setImageResource(R.drawable.needle_red);
                }
            }
        });

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        return view;
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker v, int y, int m, int d) {
                        mVaccinateDate.setText(d + "/" + (m + 1) + "/" + y);
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

        if (view.getId() == R.id.updateVaccinateBtn) {

            sCurrentInjection.setVaccinate_name(mVaccinateName.getText().toString());
            sCurrentInjection.setInjected_date(Converters.string_VNToString_US(mVaccinateDate.getText().toString()));
            sCurrentInjection.setNote(mVaccinateNote.getText().toString());

            if (mVaccinateInjected.isChecked()) {
                sCurrentInjection.setIs_injected("1");
            }

            if (mVaccinateNotInjected.isChecked()) {
                sCurrentInjection.setIs_injected("0");
            }

            sCurrentInjection.update();

            displayToast("Sửa thành công");

            Bundle bundle = new Bundle();
            bundle.putString(CURRENT_BABY_ID, sCurrentInjection.getBaby_id());
            navigateTo(R.id.action_navigation_changeInjected_to_navigation_injected, bundle);
        }

        if (view.getId() == R.id.cancelVaccinateBtn) {
            displayToast("Hủy thành công");

            Bundle bundle = new Bundle();
            bundle.putString(CURRENT_BABY_ID, sCurrentInjection.getBaby_id());
            navigateTo(R.id.action_navigation_changeInjected_to_navigation_notInjected, bundle);
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}
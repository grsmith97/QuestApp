package com.example.questv3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class Create extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "Create";
    private TextInputLayout title, description;
    private TextView date;
    int icon;
    QuestBase mData;
    DatabaseReference databaseQuests;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        title = findViewById(R.id.editTitle);
        description = findViewById(R.id.editDescription);
        date = findViewById(R.id.date2);
        mData = QuestBase.getInstance();
        icon = R.mipmap.ic_launcher_round;

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        databaseQuests = FirebaseDatabase.getInstance().getReference("questLists");

        Calendar c = Calendar.getInstance();
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        date.setText("Whenever");

        Button button = findViewById(R.id.btnChooseDate);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    public void submitQuest(View view) {
        if(title.getEditText().getText().toString().isEmpty()) {
            title.setError("Required Field");
        }
        else {
            Log.d(TAG, "submitQuest: clicked");
            String qTitle, qDesc, qDate;
            qTitle = title.getEditText().getText().toString();
            qDesc = description.getEditText().getText().toString();
            qDate = date.getText().toString();
            QuestItem sending = new QuestItem(qTitle, qDesc, qDate, icon);
            mData.add(0,sending);

            if(mData.getIdentification() != null){
                Log.d(TAG, "submitQuest: Updating firebase with ID: " + mData.getIdentification());
//                databaseQuests.child(mData.getIdentification()).setValue(mData.getData());
                String temp = "123456789";
                databaseQuests.child(temp).setValue(mData.getData());
            }

            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("created_quest", sending); //don't worry about this
            startActivity(intent);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        date.setText(currentDateString);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        switch (text){
            case "Red":
                icon = R.drawable.ic_ball_red;
                break;
            case "Blue":
                icon = R.drawable.ic_ball_blue;
                break;
            case "Green":
                icon = R.drawable.ic_ball_green;
                break;
            case "Yellow":
                icon = R.drawable.ic_ball_yellow;
                break;
            case "Orange":
                icon = R.drawable.ic_ball_orange;
                break;
            case "Purple":
                icon = R.drawable.ic_ball_purple;
                break;
            default:
                icon = R.mipmap.ic_launcher_round;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

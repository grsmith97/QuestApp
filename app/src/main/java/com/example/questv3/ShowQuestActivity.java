package com.example.questv3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.questv3.MainActivity.getBallColor;

public class ShowQuestActivity extends AppCompatActivity {
    private static final String TAG = "ShowQuestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_quest);

        final TextView title,description,date;
        title = findViewById(R.id.questTitle);
        description = findViewById(R.id.questDescription);
        date = findViewById(R.id.questDate);
        Button shareButton = findViewById(R.id.buttonShare);
        final EditText shareName = findViewById(R.id.textShareName);

        Log.d(TAG, "onCreate: started.");
        if(getIntent().hasExtra("selected_quest")){
            QuestItem quest = getIntent().getParcelableExtra("selected_quest");
            title.setText(quest.getTitle());
            description.setText(quest.getContent());
            date.setText(quest.getDate());
            Log.d(TAG, "onCreate: " + quest.toString());
        }
    }
}

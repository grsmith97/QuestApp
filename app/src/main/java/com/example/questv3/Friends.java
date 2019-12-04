package com.example.questv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Friends extends AppCompatActivity implements FriendAdapter.OnFriendListener{

    RecyclerView friendRecyclerView;
    FriendAdapter friendAdapter;
    ArrayList<FriendItem> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        friendRecyclerView = findViewById(R.id.friend_rv);
        mData = new ArrayList<>();
        addFakeFriends();

        friendAdapter = new FriendAdapter(this, mData,this);
        friendRecyclerView.setAdapter(friendAdapter);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void addFakeFriends(){
        for(int i = 0; i<30; i++){
            mData.add(new FriendItem("Friend " +i,"email" +i+"@gmail.com"));
        }
    }

    @Override
    public void onFriendClick(int position) {
        String temp = mData.get(position).getfName();
        Toast.makeText(this, "Friend: "+temp, Toast.LENGTH_SHORT).show();
    }
}

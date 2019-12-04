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
    ArrayList<String> names;

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
//        for(int i = 0; i<30; i++){
//            mData.add(new FriendItem(names.get(i),"email" +i+"@gmail.com"));
//        }
        mData.add(new FriendItem("Adam Driver","example@gmail.com"));
        mData.add(new FriendItem("Adam Sandler","example@gmail.com"));
        mData.add(new FriendItem("Benedict Cucumber Patch","example@gmail.com"));
        mData.add(new FriendItem("Bryce Dallas Howard","example@gmail.com"));
        mData.add(new FriendItem("Chris Hemsworth","example@gmail.com"));
        mData.add(new FriendItem("Chris Pratt","example@gmail.com"));
        mData.add(new FriendItem("Dwayne 'The Rock' Johnson","example@gmail.com"));
        mData.add(new FriendItem("Emilia Clarke","example@gmail.com"));
        mData.add(new FriendItem("Evangeline Lilly","example@gmail.com"));
        mData.add(new FriendItem("Jennifer Lawrence","example@gmail.com"));
        mData.add(new FriendItem("Kate Beckinsale","example@gmail.com"));
        mData.add(new FriendItem("Kristen Bell","example@gmail.com"));
        mData.add(new FriendItem("Margot Robbie","example@gmail.com"));
        mData.add(new FriendItem("Natalie Portman","example@gmail.com"));
        mData.add(new FriendItem("Olivia Wilde","example@gmail.com"));
        mData.add(new FriendItem("Peter Dinklage","example@gmail.com"));
        mData.add(new FriendItem("Robert Downey Jr.","example@gmail.com"));
        mData.add(new FriendItem("Ryan Reynolds","example@gmail.com"));
        mData.add(new FriendItem("Samuel L. Jackson","example@gmail.com"));
        mData.add(new FriendItem("Scarlett Johansson","example@gmail.com"));
    }

    public void openDialog(String pass){
        FriendDialog friendDialog = new FriendDialog();
        friendDialog.show(getSupportFragmentManager(),pass);
    }

    @Override
    public void onFriendClick(int position) {
        String temp = mData.get(position).getfName();
//        Toast.makeText(this, "Friend: "+temp, Toast.LENGTH_SHORT).show();
        openDialog(temp);
    }
}

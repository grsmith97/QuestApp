package com.example.questv3;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuestAdapter.OnQuestListener {

    RecyclerView questRecyclerView;
    QuestAdapter questAdapter;
    List<QuestItem> mData;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,Create.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // initial view
        questRecyclerView = findViewById(R.id.news_rv);
        mData = new ArrayList<>();

        // adapter initial setup
        questAdapter = new QuestAdapter(this,mData,this);
        questRecyclerView.setAdapter(questAdapter);
        questRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // item touch
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(questRecyclerView);

        if(getIntent().hasExtra("created_quest")){
            QuestItem quest = getIntent().getParcelableExtra("created_quest");
            mData.add(quest);
        }

        // fill list, this is temporary for testing
        // later will get data from firebase api
        mData.add(new QuestItem("Get Groceries","Milk, eggs, bread, lettuce, meat, cheese, rice, seasonings","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Do Laundry","It's been 5 weeks now. Time to finally get it over with...","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Meet with Taylor","She's got time between class, let's hang out.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Work Out","25 reps 3 sets Pull Ups\n25 reps 3 sets Push Ups\n25 reps 3 sets Crunches\n25 reps 3 sets Pike Press\n25 reps 3 sets Squats","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Study","Test coming up in CS403 on Wednesday","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Clean Room","No girl is gonna wanna come over to this mess. Better get to it.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Call the Apt Office","The AC broke and it's pretty inconvenient. Call them ASAP.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Dentist Appt","Thursday at 4:30pm. Get there early to fill out paperwork.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Fix Computer","Computer has been acting up since I called it names and hurt its feelings.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Get Groceries","Milk, eggs, bread, lettuce, meat, cheese, rice, seasonings","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Do Laundry","It's been 5 weeks now. Time to finally get it over with...","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Meet with Taylor","She's got time between class, let's hang out.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Work Out","25 reps 3 sets Pull Ups\n25 reps 3 sets Push Ups\n25 reps 3 sets Crunches\n25 reps 3 sets Pike Press\n25 reps 3 sets Squats","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Study","Test coming up in CS403 on Wednesday","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Clean Room","No girl is gonna wanna come over to this mess. Better get to it.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Call the Apt Office","The AC broke and it's pretty inconvenient. Call them ASAP.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Dentist Appt","Thursday at 4:30pm. Get there early to fill out paperwork.","20 October 2019",R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Fix Computer","Computer has been acting up since I called it names and hurt its feelings.","20 October 2019",R.mipmap.ic_launcher_round));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,ItemTouchHelper.START | ItemTouchHelper.END) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(mData,fromPosition,toPosition);
            questRecyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int fromPosition = viewHolder.getAdapterPosition();
            mData.remove(fromPosition);
            questAdapter.notifyItemRemoved(fromPosition);
        }
    };

    @Override
    public void onQuestClick(int position) {
        Log.d(TAG, "onQuestClick: clicked");
        mData.get(position);
        Intent intent = new Intent(this, ShowQuestActivity.class);
        intent.putExtra("selected_quest",mData.get(position));
        startActivity(intent);
    }
}

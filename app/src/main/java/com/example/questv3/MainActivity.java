package com.example.questv3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuestAdapter.OnQuestListener {

    RecyclerView questRecyclerView;
    QuestAdapter questAdapter;
    QuestBase mData;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
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
                Intent intent = new Intent(MainActivity.this, Create.class);
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
        mData = QuestBase.getInstance();

        // adapter initial setup
        questAdapter = new QuestAdapter(this,mData.getData(),this);
        questRecyclerView.setAdapter(questAdapter);
        questRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // item touch
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(questRecyclerView);

        //Google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
//    }

    private void updateUI(GoogleSignInAccount account) {
        if(account != null) {
            Log.d(TAG, "updateUI: Login success.");
            mData.setIdentification(account.getId());
            setUserViewData();
            Log.d(TAG, "updateUI: ID: "+ mData.getIdentification());
            invalidateOptionsMenu();
            mData.setSignedIn(true);
        }
        else {
            Log.d(TAG, "updateUI: Login failure.");
            resetUserViewData();
            invalidateOptionsMenu();
            mData.setSignedIn(false);
        }
    }

    private void setUserViewData(){
        TextView name, email;
        ImageView imageView;

        name = findViewById(R.id.navName);
        email = findViewById(R.id.navEmail);
        imageView = findViewById(R.id.navImageView);

        name.setText(mData.getUserName());
        email.setText(mData.getUserEmail());
//        imageView.setImageURI(null);
//        imageView.setImageURI(mData.getUserPhoto());
        if(mData.getUserPhoto() != null){
            Glide.with(imageView.getContext()).load(mData.getUserPhoto()).into(imageView);
        }
    }

    private void resetUserViewData(){
        TextView name, email;
        ImageView imageView;

        name = findViewById(R.id.navName);
        email = findViewById(R.id.navEmail);
        imageView = findViewById(R.id.navImageView);

        name.setText("Sign In");
        email.setText("Using Google Sign In");
        imageView.setImageResource(R.mipmap.ic_launcher_round);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this,"Signed Out",Toast.LENGTH_SHORT).show();
                    }
                });
        updateUI(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            mData.setUserEmail(account.getEmail());
            mData.setUserName(account.getDisplayName());
            mData.setUserPhoto(account.getPhotoUrl());
            Log.d(TAG, "handleSignInResult: URI: " + mData.getUserPhoto());
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
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
//        optionsMenu = menu;

        menu.findItem(R.id.action_sign_in).setVisible(!mData.isSignedIn());
        menu.findItem(R.id.action_sign_out).setVisible(mData.isSignedIn());

        return true;
    }

    public void addFakeQuests(){
        mData.add(new QuestItem("Get Groceries","Milk, eggs, bread, lettuce, meat, cheese, rice, seasonings","Whenever", R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Do Laundry","It's been 5 weeks now. Time to finally get it over with...","Whenever", R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Move that old couch","Need some help moving that old couch Dad gave me","Whenever", R.drawable.ic_ball_blue));
        mData.add(new QuestItem("Work Out","25 reps 3 sets Pull Ups\n25 reps 3 sets Push Ups\n25 reps 3 sets Crunches\n25 reps 3 sets Pike Press\n25 reps 3 sets Squats","Today", R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Study","Test coming up in CS403 on Wednesday","Dec 8, 2019", R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Clean Room","Having a party soon. Need this place cleaned.","Dec 8, 2019", R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("Call the Apt Office","The AC broke and it's pretty inconvenient. Call them ASAP.","Dec 12, 2019", R.mipmap.ic_launcher_round));
        mData.add(new QuestItem("PAY RENT","Rent is due on the 15th!!","Dec 15, 2019", R.drawable.ic_ball_red));
        mData.add(new QuestItem("Fix Computer","Computer has been acting up since I called it names and hurt its feelings.","Dec 16, 2019", R.mipmap.ic_launcher_round));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            signOut();
        }
        else if(id == R.id.action_sign_in){
            signIn();
        }
        else if(id == R.id.action_fake_quests){
            addFakeQuests();
            item.setVisible(false);
            questRecyclerView.getAdapter().notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            intent = new Intent(this,QuestLog.class);
            startActivity(intent);
        } else if (id == R.id.nav_tools) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            intent = new Intent(this, Friends.class);
            startActivity(intent);
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
            mData.swap(fromPosition,toPosition);
            questRecyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            final QuestItem temp;

            switch (direction){
                case ItemTouchHelper.START:
                    temp = mData.get(position);
                    mData.remove(position);
                    questAdapter.notifyItemRemoved(position);
                    Snackbar.make(questRecyclerView,"Quest Removed",Snackbar.LENGTH_LONG)
                            .setAction("Undo?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mData.add(temp);
                            questAdapter.notifyItemInserted(position);
                        }
                    }).show();
                    break;
                case ItemTouchHelper.END:
                    temp = mData.get(position);
                    mData.addLog(temp);
                    mData.remove(position);
                    questAdapter.notifyItemRemoved(position);
                    break;
            }
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
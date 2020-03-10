package com.adampc.chatanon.ui.homescreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.adampc.vihaan.whatsappclone.R;
import com.adampc.chatanon.ui.chatscreen.ChatActivity;
import com.adampc.chatanon.ui.common.adapters.ViewPagerTabAdapter;
import com.adampc.chatanon.ui.models.User;
import com.adampc.chatanon.ui.userListScreen.UserListActivity;
import com.adampc.chatanon.ui.welcomeScreen.WelcomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        initData();
        initViews();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            User user = bundle.getParcelable(ChatActivity.EXTRAS_USER);
            if(user != null)
            {
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(ChatActivity.EXTRAS_USER, user);
                startActivity(intent);
            }
        }
    }

    private void initViews() {
        initAppBar();
        initNewMessageFloatingButton();
        initViewPager();
        initTabLayout();
    }

    private void initAppBar() {
        ActionBar actionBar = getSupportActionBar();
        if(null!=actionBar){
            //set no box shadow
            actionBar.setElevation(0);
        }
    }

    private void initNewMessageFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("Chats");
        tabNames.add("Status");
        tabNames.add("Calls");
        ViewPagerTabAdapter viewPagerTabAdapter = new ViewPagerTabAdapter(getSupportFragmentManager(), getFragments(), tabNames);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(viewPagerTabAdapter);
    }

    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabMenu);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
    }


    private List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<Fragment>();
        mFragments.add(UserChatsFragment.newInstance());
        mFragments.add(StatusFragment.newInstance());
        mFragments.add(CallsFragment.newInstance());
        return mFragments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
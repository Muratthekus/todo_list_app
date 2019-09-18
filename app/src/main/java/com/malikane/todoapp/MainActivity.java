package com.malikane.todoapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DEFAULT OPTION
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentHome()).commit();
        BottomNavigationView bottomNav=findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected=null;
                    switch (item.getItemId()){
                        case R.id.bottom_home: selected=new FragmentHome(); break;
                        case R.id.bottom_Add: selected=new FragmentAddScreen();break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selected).commit();
                    return true;
                }
            };
}

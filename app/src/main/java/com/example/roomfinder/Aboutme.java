package com.example.roomfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class Aboutme extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        drawerLayout=findViewById(R.id.drawer_layout);
    }
    public void Clickmenu(View view){
        displayactivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        displayactivity.closeDrawer(drawerLayout);
    }
    public void Clickhome(View view){
        displayactivity.redirectActivity(this,displayactivity.class);
    }
    public void ClickDashboard(View view){
        displayactivity.redirectActivity(this,Dashboard.class);
    }
    public void ClickAboutme(View view){
       recreate();
    }
    public void ClickLogout(View view){
        displayactivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        displayactivity.closeDrawer(drawerLayout);
    }
}
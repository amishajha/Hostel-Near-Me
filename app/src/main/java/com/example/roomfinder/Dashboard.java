package com.example.roomfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
ImageButton imageButton,imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        drawerLayout=findViewById(R.id.drawer_layout);
        imageButton=findViewById(R.id.imageButton);
        imageButton2=findViewById(R.id.imageButton2);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent add = new Intent(getApplicationContext(), com.example.roomfinder.Addpost.class);
                startActivity(add);
                finish();


            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent add = new Intent(getApplicationContext(), com.example.roomfinder.displayactivity.class);
                startActivity(add);
                finish();


            }
        });

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
       recreate();
    }
    public void ClickAboutme(View view){
        displayactivity.redirectActivity(this,Aboutme.class);
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
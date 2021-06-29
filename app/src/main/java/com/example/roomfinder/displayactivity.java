package com.example.roomfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class displayactivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    ImageView post;
    private DatabaseReference mdatabase;
    private FirebaseUser cUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayactivity);
FirebaseDatabase database=FirebaseDatabase.getInstance();
        drawerLayout=findViewById(R.id.drawer_layout);

        post=(ImageView)findViewById(R.id.addpost);
     recyclerView=(RecyclerView)findViewById(R.id.postlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cUser = FirebaseAuth.getInstance().getCurrentUser();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeActivity = new Intent(getApplicationContext(),Addpost.class);
                startActivity(homeActivity);
                finish();

            }
        });

        FirebaseRecyclerOptions<Post>options= new FirebaseRecyclerOptions.Builder<Post>().setQuery(mdatabase,Post.class).build();
        FirebaseRecyclerAdapter<Post,Postviewholder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Post, Postviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Postviewholder postviewholder, int i, @NonNull Post post) {
               postviewholder.setitem(this,post.getHomename(),post.getContacts(),post.getRooms(),post.getArea(),post.getDistricts(),post.getImages(),post.getPrices());

           }

            @NonNull
            @Override
            public Postviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.postrow,parent,false);
                return new Postviewholder(view);
            }
        };
    firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }



    public void Clickmenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
    drawerLayout.openDrawer(GravityCompat.START);

    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

   public static void closeDrawer(DrawerLayout drawerLayout) {
    if(drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    }
    public void Clickhome(View view){
        recreate();
    }
    public void ClickDashboard(View view){
        redirectActivity(this,Dashboard.class);
    }
    public void ClickAboutme(View view){
        redirectActivity(this,Aboutme.class);
    }
public void ClickLogout(View view){
        logout(this);
}

 public static void logout(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
        activity.finishAffinity();
        System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

  public static void redirectActivity(Activity activity,Class aclass){

        Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
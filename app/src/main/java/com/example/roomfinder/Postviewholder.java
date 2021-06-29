package com.example.roomfinder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

public class Postviewholder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView homename, contact, rent,room, Area, district;

    public Postviewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void setitem(FirebaseRecyclerAdapter<Post, Postviewholder> activity, String Homename, String contacts, String rooms, String area, String districts, String images, String prices) {
        imageView = itemView.findViewById(R.id.housepic);
        homename = itemView.findViewById(R.id.housename);
        contact=itemView.findViewById(R.id.phone);
        rent=itemView.findViewById(R.id.rent);
        room=itemView.findViewById(R.id.Rooms);
        Area =itemView.findViewById(R.id.Areaname);
        district= itemView.findViewById(R.id.disname);

        homename.setText(Homename);
        contact.setText(contacts);
        rent.setText(prices);
        room.setText(rooms);
        Area.setText(area);
        district.setText(districts);

        Picasso.get().load(images).placeholder(R.drawable.home).into(imageView);





    }

}
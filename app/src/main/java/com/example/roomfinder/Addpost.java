package com.example.roomfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Addpost extends AppCompatActivity {
    String saveCurrentDate, saveCurrentTime;
    FirebaseAuth mAuth;
    private String randomKey;
    String  nameHome,contactNo,room, price, district, disc,Area;
    EditText home,dist,area,rent,contact,rooms,desc;
    Button submit;
    ImageButton upload;
    private Uri ImageUri ;
    private DatabaseReference postDataRef;
    private DatabaseReference databaseReference;
    private String downloadUri;
    private ProgressDialog pd;
    private static final int gallery_request=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_addpost);
        home=findViewById(R.id.homeName);
        dist=findViewById(R.id.College);
        area=findViewById(R.id.Area);
        rent=findViewById(R.id.rentRange);
        contact=findViewById(R.id.phnNo);
        rooms=findViewById(R.id.room);

        submit=findViewById(R.id.button_post);
        upload=findViewById(R.id.pic);
        pd = new ProgressDialog(this);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                GalleryIntent.setType("image/*");
                startActivityForResult(GalleryIntent,gallery_request);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collectData();
            }
        });



    }


    private void collectData() {
        pd.setMessage("Posting");
        pd.show();
        nameHome= home.getText().toString();
        contactNo= contact.getText().toString();
         room= rooms.getText().toString();
        price=rent.getText().toString();
       Area= area.getText().toString();
 district= dist.getText().toString();



        // localArea= subArea.getText().toString();

        // area=SelectDistrict.toString();

        if(ImageUri==null){
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(contactNo))
        {
            Toast.makeText(this, "Please give your Contact No, its mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(room))
        {
            Toast.makeText(this, "Please provide all the information", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(price))
        {
            Toast.makeText(this, "Please provide all the information", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Area))
        {
            Toast.makeText(this, "Please provide all the information", Toast.LENGTH_SHORT).show();
        }

        /*else if(TextUtils.isEmpty(localArea))
        {
            Toast.makeText(this, "Please provide all the information", Toast.LENGTH_SHORT).show();
        }*/
        else {
            storeData();

        }
    }

    private void storeData() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH: mm: ss a");
        saveCurrentTime= currentTime.format(calendar.getTime());

        randomKey= saveCurrentDate+ saveCurrentTime;

        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        StorageReference filepath=mStorage.child("room_photos").child(ImageUri.getLastPathSegment());
        final UploadTask uploadTask= filepath.putFile(ImageUri);

        filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();

                Toast.makeText(Addpost.this, "  uploaded Successfully...", Toast.LENGTH_SHORT).show();

                DatabaseReference addpost=databaseReference.push();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadUri = filepath.getDownloadUrl().toString();

                        addpost.child("Homename").setValue(nameHome);
                        addpost.child("contacts").setValue(contactNo);
                        addpost.child("rooms").setValue(room);
                        addpost.child("prices").setValue(price);
                        addpost.child("districts").setValue(district);
                        addpost.child("area").setValue(Area);

                  //      addpost.child("images").setValue(downloadUri);

                        return filepath.getDownloadUrl();


                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadUri=task.getResult().toString();
                            Log.d("testm","on complete "+downloadUri);
                            addpost.child("images").setValue(downloadUri);
                            Toast.makeText(Addpost.this, "Done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Addpost.this,displayactivity.class));

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        String message = e.toString();
                        Toast.makeText(Addpost.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gallery_request&&resultCode==RESULT_OK){
             ImageUri=data.getData();
            upload.setImageURI(ImageUri);


        }
    }
}
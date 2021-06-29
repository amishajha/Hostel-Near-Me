package com.example.roomfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    FirebaseAuth mAuth;
    private Button btn;
   private EditText email;
   private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth=FirebaseAuth.getInstance();
        btn= (Button)findViewById(R.id.sub);
        email =findViewById(R.id.mail);
        progressBar=(ProgressBar)findViewById(R.id.regProgressBar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                final String mail = email.getText().toString();
                if( mail.isEmpty() ) {

                    Toast.makeText(getApplicationContext(),"please provide email",Toast.LENGTH_SHORT).show();
                    // something goes wrong : all fields must be filled
                    // we need to display an error message


                    progressBar.setVisibility(View.INVISIBLE);


                }else {
                    mAuth.sendPasswordResetEmail(mail)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    }

                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                    updateui();
                }

            }
        });
    }



    private void updateui() {
        Intent forgotactivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(forgotactivity);
        finish();
    }
}
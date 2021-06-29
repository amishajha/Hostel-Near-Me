package com.example.roomfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private TextView register,Forgotpassword;
private EditText useremail,userpassword;
private CardView loginbtn;
private  ProgressBar  loginProgress;
    private Intent DisplayActivity;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        useremail=(EditText)findViewById(R.id.loginemail);
        userpassword=(EditText)findViewById(R.id.loginpass); 
         loginbtn=(CardView)findViewById(R.id.loginBtn);
        register=(TextView)findViewById(R.id.Register);
        loginProgress=(ProgressBar)findViewById(R.id.login_progress);
        Forgotpassword=(TextView)findViewById(R.id.Forgotpassword) ;
       DisplayActivity = new Intent(this,com.example.roomfinder.displayactivity.class);

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Intent intent = new Intent(MainActivity.this, displayactivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        mAuth.addAuthStateListener(authStateListener);
       register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(), com.example.roomfinder.RegisterActivity.class);
                startActivity(registerActivity);
                finish();


            }
        });
   ;


        Forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(), com.example.roomfinder.ForgotPassword.class);
                startActivity(registerActivity);
                finish();


            }
        });


        loginProgress.setVisibility(View.INVISIBLE);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgress.setVisibility(View.VISIBLE);
               loginbtn.setVisibility(View.INVISIBLE);

                final String mail = useremail.getText().toString();
                final String password = userpassword.getText().toString();

                if (mail.isEmpty() || password.isEmpty()) {
                    showMessage("Please Verify All Field");
                   loginbtn.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }
                else
                {


                    signIn(mail,password);
                }
            }
        });

    }

    private void signIn(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {

                    loginProgress.setVisibility(View.INVISIBLE);
                 loginbtn.setVisibility(View.VISIBLE);
                    updateUI();

                }
                else {
                    showMessage(task.getException().getMessage());
                 loginbtn.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }


            }
        });
    }

    private void updateUI() {
startActivity(DisplayActivity);
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }





}
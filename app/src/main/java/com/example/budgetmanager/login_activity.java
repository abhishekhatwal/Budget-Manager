package com.example.budgetmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_activity extends AppCompatActivity {
    EditText loginpass,loginemail;
    TextView logintext;
    Button loginbutton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        loginemail=findViewById(R.id.loginemail);
        loginpass=findViewById(R.id.loginpass);
        loginbutton=findViewById(R.id.loginbutton);
        logintext=findViewById(R.id.logintext);

        auth=FirebaseAuth.getInstance();

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login_activity.this,registration_activity.class);
                startActivity(intent);
                finish();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String emailstr=loginemail.getText().toString();
              String passstr=loginpass.getText().toString();

              if(TextUtils.isEmpty(emailstr) && TextUtils.isEmpty(passstr))
                  Toast.makeText(getApplicationContext(),"both field require",Toast.LENGTH_SHORT).show();
              else
              {
                  auth.signInWithEmailAndPassword(emailstr,passstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful())
                          {
                              Intent intent=new Intent(login_activity.this,Mainbody.class);
                              startActivity(intent);
                              finish();
                          }
                          else
                              Toast.makeText(getApplicationContext(),"incorrect username or password",Toast.LENGTH_SHORT).show();
                      }
                  });
              }
            }
        });
    }
}
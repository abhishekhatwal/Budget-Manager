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

public class registration_activity extends AppCompatActivity {
    EditText registerpass,registeremail;
    TextView registertext;
    Button registerbutton;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_activity);

        registeremail=findViewById(R.id.registeremail);
        registerpass=findViewById(R.id.registerpass);
        registerbutton=findViewById(R.id.registerbutton);
        registertext=findViewById(R.id.registertext);

        auth=FirebaseAuth.getInstance();

        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registration_activity.this,login_activity.class);
                startActivity(intent);
                finish();
            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailstrreg=registeremail.getText().toString();
                String passstrreg=registerpass.getText().toString();

                if(TextUtils.isEmpty(emailstrreg) && TextUtils.isEmpty(passstrreg))
                    Toast.makeText(getApplicationContext(),"both field require",Toast.LENGTH_SHORT).show();
                else
                {
                    auth.createUserWithEmailAndPassword(emailstrreg,passstrreg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent=new Intent(registration_activity.this,Mainbody.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}
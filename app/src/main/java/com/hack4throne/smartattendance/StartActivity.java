package com.hack4throne.smartattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);



        final FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mFirebaseAuth.getCurrentUser()==null)
                        {
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                        }
                        else if(mFirebaseAuth.getCurrentUser().getEmail().equals("admin@starks.com"))
                        {
                            startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                            finish();;
                        }
                        else
                        {
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                        // Do some stuff
                    }
                });
            }
        };
        thread.start();






    }
}

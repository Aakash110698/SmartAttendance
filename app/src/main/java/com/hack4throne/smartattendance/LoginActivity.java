package com.hack4throne.smartattendance;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class LoginActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.login_signup)
        { getSupportActionBar().setTitle("SignUp");

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.content,new SignupFragment()).commit();

            return true;
        }


        if(item.getItemId() == R.id.login_login)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            getSupportActionBar().setTitle("LogIn");

            fragmentManager.beginTransaction().replace(R.id.content,new LoginFragment()).commit();
            return  true;
        }
        return true;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.loginactivity,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login ");





        if(savedInstanceState==null)
               {
                   FragmentManager fragmentManager = getSupportFragmentManager();

                   fragmentManager.beginTransaction().replace(R.id.content,new LoginFragment()).commit();
               }













    }
}

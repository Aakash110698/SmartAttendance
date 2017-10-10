package com.hack4throne.smartattendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StaffDetailsActivity extends AppCompatActivity {






    TextInputLayout mAddress,mBlood,mDepartment,mEmail,mName,mphno;
    Button mMakeChanges;
    ProgressDialog mProgress;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("StaffDetails").child(getUid());



        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading.....");
        mProgress.setTitle("Please wait....");

        mAddress = (TextInputLayout)findViewById(R.id.staff_details_address);
        mBlood = (TextInputLayout)findViewById(R.id.staff_details_blood);
        mDepartment= (TextInputLayout)findViewById(R.id.staff_details_dept);
        mEmail = (TextInputLayout)findViewById(R.id.staff_details_email);
        mName = (TextInputLayout)findViewById(R.id.staff_details_name);
       mphno = (TextInputLayout)findViewById(R.id.staff_details_phno);


        mMakeChanges  =  (Button)findViewById(R.id.staff_details_makeChanges);




        mMakeChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mAddress.getEditText().getText().toString();
                String blood = mBlood.getEditText().getText().toString();
                String department = mDepartment.getEditText().getText().toString();
                String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                mEmail.getEditText().setText(mail);
                final String name = mName.getEditText().getText().toString();
                String phno = mphno.getEditText().getText().toString();


                if(address.equals("") || blood.equals("") || department.equals("") || mail.equals("") || name.equals("") || phno.equals(""))
                {
                    Toast.makeText(StaffDetailsActivity.this, "Some information are missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                else

                {
                    HashMap<String,String > details = new HashMap<String, String>();
                    details.put("address",address);
                    details.put("bloodgroup",blood);
                    details.put("department",department);
                    details.put("email",mail);
                    details.put("phoneno",phno);
                    details.put("uid",getUid());
                    details.put("name",name);


                    mDatabase.setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                mProgress.dismiss();
                                FirebaseDatabase.getInstance().getReference().child("StaffName").child(name).setValue(getUid());
                                Toast.makeText(StaffDetailsActivity.this, "Super", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StaffDetailsActivity.this,MainActivity.class));

                            }
                            else {
                                mProgress.dismiss();
                                Toast.makeText(StaffDetailsActivity.this, "Wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }



            }
        });




    }



    public String getUid()
    {
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();


        String uid="";
        try
        {
             uid = mFirebaseAuth.getCurrentUser().getUid();
        }
        catch (NullPointerException e)
        {
            Toast.makeText(this, "Error while getting uid", Toast.LENGTH_SHORT).show();
        }

        return uid;

    }
}

package com.hack4throne.smartattendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StaffAssignActivity extends AppCompatActivity {



    String classid,UId;
    AutoCompleteTextView Subjectname;
    EditText Staffname;
    Button uploadnow;
    ProgressDialog mProgress;
    String subjects[] = {"Technical English","Mathematics","Engineering Physics","Engineering Chemistry","Computer Programming","Engineering Graphics","Technical English","Mathematics","Engineering Physics","Engineering Chemistry","Digital Principles and System Design","Programming and Data Structures I","Transforms and Partial Differential Equations","Programming and Data Structure II","Database Management Systems","Computer Architecture","Analog and Digital Communication","Environmental Science and Engineering","Probability and Queueing Theory","Computer Networks","Operating Systems","Design and Analysis of Algorithms","Microprocessor and Microcontroller","Software Engineering","Discrete Mathematics","Internet Programming","Object Oriented Analysis and Design","Theory of Computation","Computer Graphics","Distributed Systems","Mobile Computing","Compiler Design","Digital Signal Processing","Artificial Intelligence","Cryptography and Network Security","Graph Theory and Applications","Grid and Cloud Computing","Resource Management Techniques","Multi – Core Architectures and Programming","C# and .Net programming","Total Quality Management","Data Warehousing and Data Mining","Network Analysis and Management","Software Testing","Ad hoc and Sensor Networks","Cyber Forensics","Advanced Database Systems","Bio Informatics","Service Oriented Architecture","Digital Image Processing","Embedded and Real Time Systems","Game Programming","Information Retrieval","Data Analytics","Human Computer Interaction","Nano Computing","Knowledge Management","Social Network Analysis","Software Project Management","Professional Ethics in Engineering","Natural Language Processing","Soft Computing"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_assign);


        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading.....");
        mProgress.setTitle("Please wait....");


        classid = getIntent().getStringExtra("classid");

        final String[] spol = classid.split("-");

        Subjectname = (AutoCompleteTextView) findViewById(R.id.subjectname1);
        Staffname =(EditText)findViewById(R.id.staffname1);
        uploadnow = (Button)findViewById(R.id.uploadnow);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,subjects);

        Subjectname.setThreshold(1);
        Subjectname.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        Subjectname.setTextColor(Color.BLACK);






        final String StName  = Staffname.getText().toString();
        final String SbName = Subjectname.getText().toString();

     //   getUidOfStaffs(StName);

       final String intentdata = getIntent().getStringExtra("classid");


        uploadnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mProgress.show();;

                DatabaseReference databaseReferences = FirebaseDatabase.getInstance().getReference().child("Classes").child(classid).child("Subjects").child(Subjectname.getText().toString());


                HashMap<String,String> mape = new HashMap<String, String>();
                mape.put("Name",Subjectname.getText().toString());
                mape.put("NoOfPeriod","0");
                mape.put("StaffName",Staffname.getText().toString());

                databaseReferences.setValue(mape).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                         //   Toast.makeText(StaffAssignActivity.this, "Done ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("StaffName").child(Staffname.getText().toString());


                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        UId = dataSnapshot.getValue().toString();

                      DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("StaffsActivity").child(UId);
                        HashMap<String,String> map = new HashMap<String, String>();


                        map.put("dept",spol[0]);
                        map.put("section",spol[4]);
                        map.put("year",spol[2]);
                        map.put("semester",spol[3]);
                        map.put("subjectname",Subjectname.getText().toString());
                        map.put("periodgone","0");
                        map.put("name",Staffname.getText().toString());
                        map.put("strength",getIntent().getStringExtra("strength"));
                        map.put("classid",classid);


                        db.child(Subjectname.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    mProgress.dismiss();
                                    startActivity(new Intent(StaffAssignActivity.this,AdminActivity.class));
                                    finish();

                                    Toast.makeText(StaffAssignActivity.this, "Super", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    mProgress.dismiss();
                                    Toast.makeText(StaffAssignActivity.this, "Failed ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });




                       // Toast.makeText(StaffAssignActivity.this, "Fuckofffff", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


//              Log.d("UFfdsfj",UId);



            }
        });






    }



}

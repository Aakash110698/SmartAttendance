package com.hack4throne.smartattendance;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TakeAttendance extends AppCompatActivity {


    RecyclerViewAdapter rcAdapter;
    Button bu;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.send)
        {

             HashMap<String,String> answ = rcAdapter.values;

          String date =   android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date()).toString();
             answ.put("DATE",date);


            for(int i=1;i<=60;i++)
            {
                Log.d("Value of " + i,answ.get(i+""));
            }
            Log.d(answ.get("DATE"),"gfd");




           final HashMap<String,String> answs = answ;
            Log.d("Rjdshfj",getIntent().getStringExtra("classid"));
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(getIntent().getStringExtra("classid")).child("Subjects").child(getIntent().getStringExtra("subjectname"));
            databaseReference.child("Attendance").child("1").setValue(answs);
            databaseReference.child("Attendance").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren())
                    {
                        DatabaseReference df  = FirebaseDatabase.getInstance().getReference();
                        Query last = df.child("Classes").child(getIntent().getStringExtra("classid")).child("Subjects").child(getIntent().getStringExtra("subjectname")).child("Attendance").orderByKey().limitToLast(1);
                        last.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String count = dataSnapshot.getKey();
                                Log.d("Flkdslnsdlkf",count + dataSnapshot.toString());
                                int value = Integer.parseInt(count);
                                value++;
                                count = String.valueOf(value);
                                databaseReference.child("Attendance").child(value + "").setValue(answs);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else {
                        databaseReference.child("Attendance").child("1").setValue(answs);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });






        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }
        ProgressDialog pro;
    private GridLayoutManager lLayout;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);


        pro = new ProgressDialog(this);
        pro.setMessage("Loadin");
        pro.setTitle("Wait");
        lLayout = new GridLayoutManager(getApplicationContext(), 5);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recyclerview_attendance);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        bu = (Button)findViewById(R.id.sends);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.show();
                HashMap<String,String> answ = rcAdapter.values;

                String date =   android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date()).toString();
                answ.put("DATE",date);



                final HashMap<String,String> packes = answ;

                final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Classes").child(getIntent().getStringExtra("classid")).child("Subjects").child(getIntent().getStringExtra("subjectname")).child("Attendance");
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.hasChildren())
                        {

                            database.child("1").setValue(packes).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    final DatabaseReference staffactivitys = FirebaseDatabase.getInstance().getReference().child("StaffsActivity").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("subjectname"));

                                    staffactivitys.child("periodgone").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String period = dataSnapshot.getValue().toString();

                                            int count = Integer.parseInt(period);
                                            count++;
                                            staffactivitys.child("periodgone").setValue(count + "" );

                                            FirebaseDatabase.getInstance().getReference().child("Classes").child(getIntent().getStringExtra("classid")).child("Subjects").child(getIntent().getStringExtra("subjectname")).child("NoOfPeriod").setValue(count+"");



                                            Log.d("Period count " , period);

                                            pro.dismiss();


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    pro.dismiss();
                                }
                            });
                        }
                        else {

                            final DatabaseReference staffactivitys = FirebaseDatabase.getInstance().getReference().child("StaffsActivity").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("subjectname"));

                            staffactivitys.child("periodgone").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String period = dataSnapshot.getValue().toString();

                                    int count = Integer.parseInt(period);
                                    count++;



                                    database.child(count +"").setValue(packes);


                                    staffactivitys.child("periodgone").setValue(count + "" );

                                    FirebaseDatabase.getInstance().getReference().child("Classes").child(getIntent().getStringExtra("classid")).child("Subjects").child(getIntent().getStringExtra("subjectname")).child("NoOfPeriod").setValue(count+"");



                                    Log.d("Period count " , period);

                                    pro.dismiss();


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });




                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        String[] spol = getIntent().getStringExtra("classid").split("-");

        rcAdapter = new RecyclerViewAdapter(getApplicationContext(), Integer.parseInt(getIntent().getStringExtra("strength")),spol[0].concat(spol[4]));


        rView.setAdapter(rcAdapter);



    }
}

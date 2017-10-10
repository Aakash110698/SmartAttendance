package com.hack4throne.smartattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

public class DateChecker extends AppCompatActivity {


    Button datepic;
  //  EditText date;
    String date;
    ArrayList<String> mAbsent = new ArrayList<>();
    ListView mListview;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_checker);
        mListview = (ListView)findViewById(R.id.list_view);

        final ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mAbsent);
        mListview.setAdapter(adaptor);


        datepic = (Button)findViewById(R.id.date_picker_button);



        datepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH);
               // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(DateChecker.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                               Log.d("Hello date",dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);



                                if(monthOfYear+1<10 && dayOfMonth<10)
                                {
                                    date = year + "-0" + (monthOfYear + 1 )+"-0"+dayOfMonth;
                                }
                                else if(monthOfYear+1<10 && dayOfMonth>10)

                                {
                                    date = year + "-0" + (monthOfYear + 1 )+"-"+dayOfMonth;
                                }
                                else if(monthOfYear+1>10 && dayOfMonth<10)
                                {
                                    date = year + "-" + (monthOfYear + 1 )+"-0"+dayOfMonth;
                                }
                                else
                                {
                                    date = year + "-" + (monthOfYear + 1 )+"-"+dayOfMonth;
                                }


                                DatabaseReference mDatabaseReferance = FirebaseDatabase.getInstance().getReference().child("Classes").child(getIntent().getStringExtra("classid")).child("Subjects").child(getIntent().getStringExtra("subjectname")).child("Attendance");


                                mDatabaseReferance.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        int x=0;

                                        for(DataSnapshot i:dataSnapshot.getChildren())
                                        {
                                            String datew = i.child("DATE").getValue().toString();
                                            if(datew.equals(date))
                                            {x++;
                                                mAbsent.add(x+"");
                                                adaptor.notifyDataSetChanged();
                                                for(DataSnapshot j:i.getChildren())
                                                {
                                                    if(j.getValue().equals("A"))
                                                    {
                                                        mAbsent.add(j.getKey());
                                                        adaptor.notifyDataSetChanged();


                                                    }
                                                }

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });





                                Log.d("date ",date);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });




    }




}

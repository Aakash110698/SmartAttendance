package com.hack4throne.smartattendance;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateClassFragment extends Fragment {



    Spinner dept,year,mClass,semester;
    EditText batch,total;
    Button CreateClass;

    ProgressDialog mProgress;

    public CreateClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_class, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mProgress = new ProgressDialog(getActivity());
        mProgress.setMessage("Loading.....");
        mProgress.setTitle("Please wait....");


        dept = (Spinner)view.findViewById(R.id.spinner_dept);
        year = (Spinner)view.findViewById(R.id.spinner_year);
        mClass = (Spinner)view.findViewById(R.id.spinner_class);
        semester = (Spinner)view.findViewById(R.id.spinner_semester);
        batch = (EditText) view.findViewById(R.id.batch);
        total = (EditText) view.findViewById(R.id.spinner_strength);
        CreateClass = (Button)view.findViewById(R.id.button_create_class);



        CreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.show();
                String department = dept.getSelectedItem().toString();
                String mYear = year.getSelectedItem().toString();
                String Class = mClass.getSelectedItem().toString();
                String mSemester = semester.getSelectedItem().toString();
                String mBatch = batch.getText().toString();
                String totals = total.getText().toString();



                String classid = department.concat("-").concat(mBatch).concat("-").concat(mYear).concat("-").concat(mSemester).concat("-").concat(Class);


                DatabaseReference mDatabse  = FirebaseDatabase.getInstance().getReference().child("ViewClass").child(classid);


                HashMap<String,String> packet = new HashMap<String, String>();
                packet.put("classid",classid);
                packet.put("totalstrength",String.valueOf(totals));
                mDatabse.setValue(packet).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Successfully created", Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                        }
                        else
                        {
                            mProgress.dismiss();
                            Toast.makeText(getActivity(), "Failed" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });











            }
        });





    }

}

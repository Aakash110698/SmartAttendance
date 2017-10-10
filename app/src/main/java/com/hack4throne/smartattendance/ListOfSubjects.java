package com.hack4throne.smartattendance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListOfSubjects extends Fragment {
    RecyclerView recyclerview_list;

    public ListOfSubjects() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("StaffsActivity").child(getUid());

        FirebaseRecyclerAdapter<StaffSubject,SubjectViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<StaffSubject, SubjectViewHolder>(
                StaffSubject.class,
                R.layout.subject_single_layout,
                SubjectViewHolder.class,
                databaseReference


        ) {
            @Override
            protected void populateViewHolder(SubjectViewHolder viewHolder, final StaffSubject model, int position) {


                viewHolder.setClassid(model.getClassid());
                viewHolder.setTotalStrength(model.getStrength());
                viewHolder.setPeriodgone(model.getPeriodgone());
                viewHolder.setSubject(model.getSubjectname());

                viewHolder.mView.findViewById(R.id.check_date).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                Intent intent = new Intent(getActivity(),DateChecker.class);
                        intent.putExtra("classid",model.getClassid());
                        intent.putExtra("subjectname",model.getSubjectname());
                        intent.putExtra("periodgone",model.getPeriodgone());
                        intent.putExtra("strength",model.getStrength());
                        startActivity(intent);

                    }
                });


                viewHolder.mView.findViewById(R.id.take_attendance).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),TakeAttendance.class);
                        intent.putExtra("classid",model.getClassid());
                        intent.putExtra("subjectname",model.getSubjectname());
                        intent.putExtra("periodgone",model.getPeriodgone());
                        intent.putExtra("strength",model.getStrength());

                        startActivity(intent);
                    }
                });



            }
        };

        recyclerview_list.setAdapter(firebaseRecyclerAdapter);




    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview_list = (RecyclerView)view.findViewById(R.id.recyclerview_list);


        recyclerview_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerview_list.setHasFixedSize(true);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_of_subjects, container, false);
    }




    public static class SubjectViewHolder extends RecyclerView.ViewHolder{

        View mView;


        public SubjectViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void  setClassid(String classid)
        {
            TextView classids = (TextView)mView.findViewById(R.id.classsid);
            classids.setText(classid);

        }
        public void setTotalStrength(String totalStrength)
        {
            TextView totalstr = (TextView)mView.findViewById(R.id.totalstrength);
            totalstr.setText(totalStrength);
        }
        public void setSubject(String subject)
        {
            TextView mSubject = (TextView)mView.findViewById(R.id.subjectname);
            mSubject.setText(subject);
        }
        public void setPeriodgone(String periodgone)
        {
            TextView mPeriod = (TextView) mView.findViewById(R.id.periodgone);
            mPeriod.setText(periodgone);
        }

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
            Toast.makeText(getActivity(), "Error while getting uid", Toast.LENGTH_SHORT).show();
        }

        return uid;

    }

}

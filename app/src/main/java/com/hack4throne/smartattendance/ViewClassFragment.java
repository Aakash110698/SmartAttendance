package com.hack4throne.smartattendance;


import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewClassFragment extends Fragment {


    RecyclerView recylcerview;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        recylcerview = (RecyclerView)view.findViewById(R.id.viewClass_Fragment);
        recylcerview.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public ViewClassFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ViewClass");
        FirebaseRecyclerAdapter<ClassClass,ClassViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ClassClass, ClassViewHolder>(
                ClassClass.class,
                R.layout.class_view,
                ClassViewHolder.class,
                mDatabase


        ) {
            @Override
            protected void populateViewHolder(ClassViewHolder viewHolder, final ClassClass model, int position) {


                viewHolder.setClassid(model.getClassid());
                viewHolder.setTotalStrength(model.getTotalstrength());


               viewHolder.mView.findViewById(R.id.staffassign).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {


                       Intent intent = new Intent(getActivity(),StaffAssignActivity.class);
                       intent.putExtra("classid",model.getClassid());
                       intent.putExtra("strength",model.getTotalstrength());
                       startActivity(intent);
                       Toast.makeText(getActivity(),"Assign",Toast.LENGTH_LONG).show();

                   }
               });





            }
        };



        recylcerview.setAdapter(firebaseRecyclerAdapter);






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_class, container, false);
    }



    public static class ClassViewHolder extends RecyclerView.ViewHolder{

        View mView;


        public ClassViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
       public void  setClassid(String classid)
       {
           TextView classids = (TextView)mView.findViewById(R.id.classid);
           classids.setText(classid);

       }
       public void setTotalStrength(String totalStrength)
       {
           TextView totalstr = (TextView)mView.findViewById(R.id.strength);
           totalstr.setText(totalStrength);
       }

    }

}

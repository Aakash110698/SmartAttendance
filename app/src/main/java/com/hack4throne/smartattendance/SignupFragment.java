package com.hack4throne.smartattendance;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    TextInputLayout mEmail;
    TextInputLayout mPassword;
    Button mSignup;
    FirebaseAuth mFirebseAuth;

    ProgressDialog mProgress;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgress = new ProgressDialog(getActivity());
        mProgress.setMessage("Loading.....");
        mProgress.setTitle("Please wait....");


        mEmail = (TextInputLayout)view.findViewById(R.id.signup_username);
        mPassword = (TextInputLayout)view.findViewById(R.id.signup_password);
        mSignup = (Button)view.findViewById(R.id.signup_button);
        mFirebseAuth  = FirebaseAuth.getInstance();


        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.show();
                String emailid = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();


                if(emailid.equals("") || password.equals(""))
                {
                    mProgress.dismiss();
                    return;
                }

                mFirebseAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            mProgress.dismiss();
                            startActivity(new Intent(getActivity(),StaffDetailsActivity.class));
                            getActivity().finish();

                        }
                        else
                        {
                            mProgress.dismiss();
                            Log.d("tahsdf" , task.getException().getLocalizedMessage());
                            Toast.makeText(getActivity(), "Check the email-id and password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });












    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

}

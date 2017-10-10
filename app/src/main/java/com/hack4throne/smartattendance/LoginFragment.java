package com.hack4throne.smartattendance;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {




    TextView needAccount;
    TextView forgotPassword;
    TextInputLayout mUsername,mPassword;
    Button mButton;


    ProgressDialog mProgress;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        forgotPassword = (TextView)view.findViewById(R.id.forgot_password);
        mUsername = (TextInputLayout)view.findViewById(R.id.login_username);
        mPassword = (TextInputLayout)view.findViewById(R.id.login_password);


        mProgress = new ProgressDialog(getActivity());
        mProgress.setTitle("Please wait.....");
        mProgress.setMessage("Loading.......");


        mButton = (Button)view.findViewById(R.id.login_button);



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.show();
                String username = mUsername.getEditText().getText().toString();



                String password = mPassword.getEditText().getText().toString();


                if(username.equals("") || password.equals(""))
                {
                    Toast.makeText(getActivity(),"Please fill all details",Toast.LENGTH_SHORT).show();;
                    return;
                }
                else
                {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {


                                if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@starks.com"))
                                {
                                    mProgress.dismiss();
                                    Toast.makeText(getActivity(), "Logging you in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(),AdminActivity.class));
                                    getActivity().finish();
                                    mProgress.dismiss();
                                    return;
                                }
                                else{
                                    mProgress.dismiss();
                                    Toast.makeText(getActivity(), "Logging you in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(),MainActivity.class));
                                    getActivity().finish();
                                    return;


                                }

                            }





                            else
                            {
                                mProgress.dismiss();
                                Toast.makeText(getActivity(), "Failed to login !!!! Check the credential", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });






    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

}

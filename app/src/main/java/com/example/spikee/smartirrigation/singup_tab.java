package com.example.spikee.smartirrigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class singup_tab extends Fragment implements View.OnClickListener {
    private Button b;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText cnfpass;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.signup_tab,container,false);
        b=v.findViewById(R.id.submit);
        email=v.findViewById(R.id.emailEt);
        password=v.findViewById(R.id.password);
        cnfpass=v.findViewById(R.id.cnfpassword);
        progressDialog=new ProgressDialog(getActivity());

        firebaseAuth = FirebaseAuth.getInstance();//For initialising firebase instance

        if(firebaseAuth.getCurrentUser()!=null)
        {
//            Intent in=new Intent(getActivity(),Switch.class);
//            startActivity(in);
        }

        b.setOnClickListener(this);
        return v;
    }
    private void registerUser() {
        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cnf=cnfpass.getText().toString().trim();

        if(!(pass.equals(cnf)))
        {
            Toast.makeText(getActivity(), "Password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(em)) {
            //email is empty
            Toast.makeText(getActivity(), "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            //password is empty
            Toast.makeText(getActivity(), "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.setMessage("Registering User....");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {//method that will be executed on completion of registration process
                    if (task.isSuccessful())//Which means user has regostered successfully
                    {
                        Toast.makeText(getActivity(), "Registered SuccessFully", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity(), Switch.class));
                    } else {
                        Toast.makeText(getActivity(), "Failed to Register", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });


        }
    }
    @Override
    public void onClick(View v) {
        if(v==b)
        {
            registerUser();
        }

    }
}

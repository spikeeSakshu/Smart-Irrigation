package com.example.spikee.smartirrigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class singup_tab extends Fragment implements View.OnClickListener {
    private Button b;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText phoneEt;
    private TextInputEditText cnfpass;
    private TextInputEditText entOtp;
    private ProgressDialog progressDialog;
    private TextView clickOTP;
    private String otp, phoneNumber, verificationCode,em,pass,cnf;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCall;
    private FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.signup_tab, container, false);
        b = v.findViewById(R.id.submit);
        email = v.findViewById(R.id.emailEt);
        password = v.findViewById(R.id.password);
        cnfpass = v.findViewById(R.id.cnfpassword);
        clickOTP = v.findViewById(R.id.clickOTP);
        entOtp = v.findViewById(R.id.entOtp);
        progressDialog = new ProgressDialog(getActivity());
        phoneEt=v.findViewById(R.id.phoneEt);


//        if (firebaseAuth.getCurrentUser() != null) {
////            Intent in=new Intent(getActivity(),Switch.class);
////            startActivity(in);
//        }
        StartFirebaseLogin();

        b.setOnClickListener(this);
        clickOTP.setOnClickListener(this);
        return v;
    }

    private void registerUser() {
         em = email.getText().toString().trim();
        pass = password.getText().toString().trim();
        cnf = cnfpass.getText().toString().trim();

        if (!(pass.equals(cnf))) {
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
            otp = entOtp.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,otp);
            SigninWithPhone(credential);
        }
// else {
//            progressDialog.setMessage("Registering User....");
//            progressDialog.show();
//

//        }
    }

    @Override
    public void onClick(View v) {
        if (v == b) {
            registerUser();
        }
        if (v == clickOTP) {
            phoneNumber = phoneEt.getText().toString();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    getActivity(),
                    mCall);
        }

    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            startActivity(new Intent(getActivity(), Select.class));
//                            getActivity().finish();
                            createUser();
                        } else {
                            Toast.makeText(getActivity(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void StartFirebaseLogin() {
        firebaseAuth = FirebaseAuth.getInstance();//For initialising firebase instance
        mCall=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(getActivity(), "Verification Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getActivity(), "Verification Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.i("Verification Code",s);
                verificationCode=s;
                Toast.makeText(getActivity(), "Code Sent", Toast.LENGTH_SHORT).show();
            }
        };

    }
    private void createUser(){
        progressDialog.setMessage("Registering User....");
         progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {//method that will be executed on completion of registration process
                if (task.isSuccessful())//Which means user has regostered successfully
                {
                    Toast.makeText(getActivity(), "Registered SuccessFully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Select.class));
                } else {
                    Toast.makeText(getActivity(), "Failed to Register", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}

package com.one.shop.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.one.shop.R;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText et_name, et_phone, et_email, et_password, et_confirm;
    Button btn_next;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm = (EditText) findViewById(R.id.et_confirm);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkinputs();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void checkinputs() {

        if (!TextUtils.isEmpty(et_email.getText().toString())) {
            if (!TextUtils.isEmpty(et_password.getText().toString())) {
                if (!TextUtils.isEmpty(et_confirm.getText().toString())) {
                    if ((et_confirm.getText().toString().equals(et_password.getText().toString()))) {
                        CreateAccount();
                    } else {
                        et_confirm.setError("Doesn't Match with Password");
                        Toast.makeText(getApplicationContext(), "Enter same password in both fields", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    et_confirm.setError("Empty Field!");
                    Toast.makeText(getApplicationContext(), "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                et_password.setError("Password Cannot Be Empty");
                Toast.makeText(getApplicationContext(), "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            }
        } else {
            et_email.setError("Email Cannot Be Empty");
            Toast.makeText(getApplicationContext(), "Please Fill Your Email", Toast.LENGTH_SHORT).show();
        }

    }

    private void CreateAccount() {
        final String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        progress = new ProgressDialog(RegisterActivity.this);
        progress.setTitle("Creating Account...");
        progress.setMessage("Taking you to next page...");

        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("name", et_name.getText().toString());
                    editor.putString("phone", et_phone.getText().toString());
                    editor.putString("email", et_email.getText().toString());
                    editor.commit();
                    Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void SignInActivity(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

}

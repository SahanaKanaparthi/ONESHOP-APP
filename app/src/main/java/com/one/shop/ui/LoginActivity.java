package com.one.shop.ui;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.one.shop.R;
import com.one.shop.data.DatabaseHelper;
import com.one.shop.data.Item;

public class LoginActivity extends AppCompatActivity {

    private EditText et_login_email, et_login_password;
    Button btn_login;
    ProgressDialog progress;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseHelper helper;

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {

            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new DatabaseHelper(LoginActivity.this);
        et_login_email = findViewById(R.id.et_login_email);
        et_login_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.btn_login);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        if (helper.getItemsCount() == 0) {

            new DatasavingTask().execute();

        }


    }

    private void check() {
        if (!TextUtils.isEmpty(et_login_email.getText().toString())) {
            if (!TextUtils.isEmpty(et_login_password.getText().toString())) {
                signIn();
            } else {
                et_login_password.setError("Password Cannot Be Empty");
            }
        } else {
            et_login_email.setError("Email Cannot Be Empty");
        }
    }

    private void signIn() {

        progress = new ProgressDialog(this);
        progress.setTitle("Signing In...");
        progress.setMessage("Please Wait!");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.closeOptionsMenu();
        firebaseAuth.signInWithEmailAndPassword(et_login_email.getText().toString(), et_login_password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
//                            progress.cancel();
                            String error = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void RegisterMethod(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }


    private class DatasavingTask extends AsyncTask<String, Void, String> {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setTitle("Loading");
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            helper.insertItem(new Item("Broccoli Crown", "2.99", "$2.99 /lb", "About $1.79 each", Item.VEGETABLES,"broccoli"));
            helper.insertItem(new Item("Asparagus", "4.99", "$4.99 /lb", "About $4.99 each", Item.VEGETABLES,"asparagus"));
            helper.insertItem(new Item("Red Bell Pepper", "3.99", "$3.99 /lb", "About $2.00 each", Item.VEGETABLES,"redbellpepper"));
            helper.insertItem(new Item("Red Onion", "1.99", "$1.99 /lb", "About $1.59 each", Item.VEGETABLES,"redonion"));
            helper.insertItem(new Item("Sweet Potato (Yam)", "1.69", "$1.69 /lb", "About $1.18 each", Item.VEGETABLES,"sweetpotato"));
            helper.insertItem(new Item("Iceberg Lettuce", "2.50", "$2.50 /each", "", Item.VEGETABLES,"iceberglttuce"));

            helper.insertItem(new Item("Loco Coffee Co. Coffee + Coconut Water, Original, Cold Brew", "2.99", "$2.99", "12oz", Item.BEVERAGES, "lococoffee"));
            helper.insertItem(new Item("Vita Coco Coconut Water, Pineapple", "2.99", "$2.99", "16.9 fl oz", Item.BEVERAGES, "vitacoco"));
            helper.insertItem(new Item("Chock Full O'Nuts French Roast Ground Coffee", "10.99", "$10.99", "1.63 lb", Item.BEVERAGES, "chockfull"));
            helper.insertItem(new Item("Cheribundi Juice, Tart Cherry", "7.99", "$7.99", "32 oz", Item.BEVERAGES, "cheribundi"));
            helper.insertItem(new Item("Fever-Tree Club Soda, Premium, Liquid", "2.50", "$2.50", "16.9 fl oz", Item.BEVERAGES, "fevertreeclub"));
            helper.insertItem(new Item("Fever-Tree Tonic Water, Elderflower", "2.50", "$2.50", "16.9 oz", Item.BEVERAGES, "fevertree"));

            return "successful";
        }

        @Override
        protected void onPostExecute(String s) {

            mProgressDialog.dismiss();

        }
    }
}

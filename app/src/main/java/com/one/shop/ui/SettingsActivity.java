package com.one.shop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.one.shop.R;

public class SettingsActivity extends AppCompatActivity {

    private EditText settings_et_name;
    private EditText settings_et_email;
    private EditText settings_et_phone;
    private EditText settings_et_address;

    private Button settings_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        settings_et_name = findViewById(R.id.settings_et_name);
        settings_et_email = findViewById(R.id.settings_et_email);
        settings_et_phone = findViewById(R.id.settings_et_phone);
        settings_et_address = findViewById(R.id.settings_et_address);
        settings_save = findViewById(R.id.settings_save);

        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);

        settings_et_email.setText(sharedPref.getString("email", ""));
        settings_et_name.setText(sharedPref.getString("name", ""));
        settings_et_phone.setText(sharedPref.getString("phone", ""));
        settings_et_address.setText(sharedPref.getString("address", ""));

        settings_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = settings_et_name.getText().toString();
                String phone = settings_et_phone.getText().toString();
                String email = settings_et_email.getText().toString();
                String address = settings_et_address.getText().toString();

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("name", name);
                editor.putString("phone", phone);
                editor.putString("email", email);
                editor.putString("address", address);
                editor.commit();

                Toast.makeText(getApplicationContext(), "Personal details updated",
                        Toast.LENGTH_SHORT).show();
                finish();


            }
        });
    }
}
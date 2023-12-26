package com.example.project136.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project136.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
//        EditText editTextQMK = findViewById(R.id.editTextQMK);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonClose =findViewById(R.id.buttonClose);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username.equals("admin") && password.equals("admin")) {
//                    AlertDialog.Builder builder =  new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("Login Access");
//                    builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                } else {
                    AlertDialog.Builder builder =  new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Login Fail");
                    builder.setMessage("User account or password incorrect");
                    builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Do you want exit?");
                builder.setMessage("Select Option");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
//        EditText editTextQMK = findViewById(R.id.editTextQMK);
//        editTextQMK.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//            }
//            AlertDialog.Builder builder =  new AlertDialog.Builder(LoginActivity.this);
//            builder.setTitle("Do you want exit?");
//            builder.setMessage("Select Option");
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
    }
}
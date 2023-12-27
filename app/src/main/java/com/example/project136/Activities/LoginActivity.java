package com.example.project136.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project136.R;
import com.example.project136.models.DBHandler;
import com.example.project136.models.User;

import java.util.Map;

//public class LoginActivity extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        EditText editTextUsername = findViewById(R.id.editTextUsername);
//        EditText editTextPassword = findViewById(R.id.editTextPassword);
////        EditText editTextQMK = findViewById(R.id.editTextQMK);
//        Button buttonLogin = findViewById(R.id.buttonLogin);
//        Button buttonClose =findViewById(R.id.buttonClose);
//
//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = editTextUsername.getText().toString();
//                String password = editTextPassword.getText().toString();
//
//                if (username.equals("admin") && password.equals("admin")) {
////                    AlertDialog.Builder builder =  new AlertDialog.Builder(MainActivity.this);
////                    builder.setTitle("Login Access");
////                    builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialogInterface, int i) {
////                            dialogInterface.dismiss();
////                        }
////                    });
////                    AlertDialog dialog = builder.create();
////                    dialog.show();
//                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent1);
//                } else {
//                    AlertDialog.Builder builder =  new AlertDialog.Builder(LoginActivity.this);
//                    builder.setTitle("Login Fail");
//                    builder.setMessage("User account or password incorrect");
//                    builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//        });
//        buttonClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder =  new AlertDialog.Builder(LoginActivity.this);
//                builder.setTitle("Do you want exit?");
//                builder.setMessage("Select Option");
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        onBackPressed();
//                        finish();
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
////        EditText editTextQMK = findViewById(R.id.editTextQMK);
////        editTextQMK.setOnClickListener(new View.OnClickListener() {
////            public void onClick(View view) {
////            }
////            AlertDialog.Builder builder =  new AlertDialog.Builder(LoginActivity.this);
////            builder.setTitle("Do you want exit?");
////            builder.setMessage("Select Option");
////            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialogInterface, int i) {
////
////                }
////            });
//    }
//}
public class LoginActivity extends AppCompatActivity {
    EditText etLoginUser, etLoginPass;
    TextView tvNavigateSignIn, tvForgetPass;
    Button btnLogin,btnExit;
    CheckBox cbRememberMe;
    RelativeLayout contain2;
    String tenThongTinDangNhap = "login";

    @Override
    protected void onPause() {
        super.onPause();
        saveLogInState();
    }
    public void saveLogInState()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(tenThongTinDangNhap, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", etLoginUser.getText().toString());
        editor.putString("password", etLoginPass.getText().toString());
        editor.putBoolean("save", cbRememberMe.isChecked());
        editor.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(tenThongTinDangNhap, MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        boolean save = sharedPreferences.getBoolean("save", false);
        if(save)
        {
            etLoginUser.setText(email);
            etLoginPass.setText(password);
            cbRememberMe.setChecked(save);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Ánh xạ
        contain2 = findViewById(R.id.LLogIn);
        etLoginUser = findViewById(R.id.etUserLogin);
        etLoginPass = findViewById(R.id.etPassWordLogin);
        btnLogin = findViewById(R.id.btnLogIn);
        btnExit = findViewById(R.id.btnExit);
        tvNavigateSignIn = findViewById(R.id.tvNavigateSignIn);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        tvForgetPass = findViewById(R.id.tvForgetPass);

        contain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
         //Handle event navigate home screen
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginUser.getText().toString();
                String password = etLoginPass.getText().toString();
                DBHandler dbHandler = DBHandler.getInstance(LoginActivity.this);
                Map<String, String> userData = dbHandler.checkLogin(email, User.getInstance().hashPassword(password));

                if (userData != null) {
                    int id = Integer.parseInt(userData.get("id"));
                    String firstname = userData.get("firstname");
                    String lastname = userData.get("lastname");
                    String userEmail = userData.get("email");
                    String userPassword = userData.get("password");

                    User.getInstance().initialize(id, firstname, lastname, userEmail, userPassword);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        // Handle event navigate sign up
        tvNavigateSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, signUpActivity.class);
                startActivity(intent);
            }
        });

        // Handle forget password
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FogetPassActivity.class);
                startActivity(intent);
            }
        });
    }
    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
package com.example.project136.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project136.R;
import com.example.project136.models.DBHandler;
import com.example.project136.models.User;

public class changePassword extends AppCompatActivity {
    EditText etOldPass, etNewPass, etConPass;
    Button btnUpdateNewPass, btnCancelUpdateNewPass;
    LinearLayout contain4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        etOldPass = findViewById(R.id.etGiveOldPassword);
        etNewPass = findViewById(R.id.etGiveNewPassword);
        etConPass = findViewById(R.id.etGiveConfirmNewPassword);
        btnUpdateNewPass = findViewById(R.id.btnUpdatePass);
        btnCancelUpdateNewPass = findViewById(R.id.btnCancelUpdatePass);
        RelativeLayout contain4 = findViewById(R.id.LLChangePass);
        contain4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
        btnCancelUpdateNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdateNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = etOldPass.getText().toString();
                String newPass = etNewPass.getText().toString();
                String conPass = etConPass.getText().toString();
                if (TextUtils.isEmpty(oldPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(conPass)) {
                    Toast.makeText(changePassword.this, "Please enter complete information!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPass.equals(conPass)) {
                    Toast.makeText(changePassword.this, "Password and confirm password do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check syntax password
                if(!(User.getInstance().isPasswordValid(newPass)))
                {
                    Toast.makeText(changePassword.this, "Password must be at least 5 characters, contain uppercase letters and numbers!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(DBHandler.getInstance(changePassword.this).checkPassword(User.getInstance().hashPassword(oldPass)))
                {
                    if(DBHandler.getInstance(changePassword.this).updatePassword(User.getInstance().hashPassword(newPass)))
                    {
                        Toast.makeText(changePassword.this, "Update password successfully!", Toast.LENGTH_SHORT).show();
                        User.getInstance().setPassword(User.getInstance().hashPassword(newPass));
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(changePassword.this, "The old password is incorrect!", Toast.LENGTH_SHORT).show();
                }
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

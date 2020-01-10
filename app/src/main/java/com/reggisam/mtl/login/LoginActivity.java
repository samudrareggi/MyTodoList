package com.reggisam.mtl.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.reggisam.mtl.ListActivity;
import com.reggisam.mtl.R;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_register;
    EditText email_et, password_et;
    String email_txt, password_txt;
    private FirebaseAuth mAuth;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_register = findViewById(R.id.btReg);
        btn_login = findViewById(R.id.btLog);
        checkBox = findViewById(R.id.checkBox);
        password_et = findViewById(R.id.et_pass);
        email_et = findViewById(R.id.et_email);
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_txt = email_et.getText().toString();
                password_txt = password_et.getText().toString();
                if (TextUtils.isEmpty(email_txt)) {
                    Toast.makeText(getApplicationContext(), "Masukan Email !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password_txt)) {
                    Toast.makeText(getApplicationContext(), "Masukan Password !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email_txt, password_txt)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Login sukses, masuk ke Main Activity
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Jika Login gagal, memberikan pesan
                                    Toast.makeText(LoginActivity.this, "Proses Login gagal : " + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                if (checkBox.isChecked()){
                    mEditor.putString(getString(R.string.checkbox),"True");
                    mEditor.commit();

                    mEditor.putString(getString(R.string.cbEmail),email_txt);
                    mEditor.commit();

                    mEditor.putString(getString(R.string.cbPassword),password_txt);
                    mEditor.commit();
                }else {
                    mEditor.putString(getString(R.string.checkbox),"False");
                    mEditor.commit();

                    mEditor.putString(getString(R.string.cbEmail),"");
                    mEditor.commit();

                    mEditor.putString(getString(R.string.cbPassword),"");
                    mEditor.commit();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        checkSharedPreferences();
    }

    private void checkSharedPreferences(){
        String checkbox = mPreferences.getString(getString(R.string.checkbox), "False");
        String cbEmail = mPreferences.getString(getString(R.string.cbEmail), "");
        String cbPassword = mPreferences.getString(getString(R.string.cbPassword), "");

        email_et.setText(cbEmail);
        password_et.setText(cbPassword);

        if (checkbox.equals("True")){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }
    }
}

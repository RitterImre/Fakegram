package com.mobilalk.fakegram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText username;
    private EditText fullName;
    private EditText email;
    private EditText password;
    private Button signup;
    private TextView loginUserText;

    private DatabaseReference dbReference;
    private FirebaseFirestore dbFirestore;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        iniSignup();
    }

    private void iniSignup() {
        username = findViewById(R.id.username);
        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.sign_up_button);
        loginUserText = findViewById(R.id.already_a_user_login);

        dbReference = FirebaseDatabase.getInstance(getString(R.string.firebase_url)).getReference();
        dbFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //region Already an user? Login
        loginUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
        //endregion

        //region Signup button action
        signup.setOnClickListener(view -> {
            String usernameText = username.getText().toString();
            String fullNameText = fullName.getText().toString();
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();

            if (TextUtils.isEmpty(usernameText)) {
                Toast.makeText(SignupActivity.this, R.string.info_empty_username, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(fullNameText)) {
                Toast.makeText(SignupActivity.this, R.string.info_empty_fullname, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(emailText)) {
                Toast.makeText(SignupActivity.this, R.string.info_empty_email, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(passwordText)) {
                Toast.makeText(SignupActivity.this, R.string.info_empty_password, Toast.LENGTH_SHORT).show();
            } else if (passwordText.length() < 6) {
                Toast.makeText(SignupActivity.this, R.string.info_empty_short_password, Toast.LENGTH_SHORT).show();
            } else {
                signupUser(usernameText, fullNameText, emailText, passwordText);
            }
        });
        //endregion
    }

    private void signupUser(String username, String fullName, String email, String password) {
        progressDialog.setMessage("Please wait!");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map = new HashMap<>();

                map.put("fullname", fullName);
                map.put("email", email);
                map.put("username", username);
                map.put("id", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                map.put("bio", "");
                map.put("profileImage", "default");

                dbReference.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "Update the profile " +
                                "for better expereince", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
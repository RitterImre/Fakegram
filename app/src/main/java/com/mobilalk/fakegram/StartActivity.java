package com.mobilalk.fakegram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private ImageView iconImage;                            // The application icon
    private LinearLayout start_layout;                      // It has a Logo, Login and Registration
    private Button signup;                                  // Register button to register new users
    private Button login;                                   // Login button to log in to the application

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        iconImage = findViewById(R.id.icon_image);
        start_layout = findViewById(R.id.start_layout);
        signup = findViewById(R.id.sign_up);
        login = findViewById(R.id.login);

        iniMain();
    }

    private void iniMain() {
        //region Start Animation

        start_layout.animate().alpha(0f).setDuration(1);    // Set linear layout opacity to 0f

        // Logo - login, sign up animation
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -100);
        animation.setDuration(2000);
        animation.setAnimationListener(new StartAnimationListener());

        start_layout.setAnimation(animation);
        //endregion

        //region Signup button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, SignupActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        //endregion

        //region Login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        //endregion
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }
    //endregion

    //region Animation listener for Start Animation
    private class StartAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            iconImage.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            iconImage.clearAnimation();
            iconImage.setVisibility(View.INVISIBLE);
            start_layout.animate().alpha(1f).setDuration(1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
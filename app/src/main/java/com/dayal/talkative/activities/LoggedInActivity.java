package com.dayal.talkative.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dayal.talkative.R;
import com.dayal.talkative.util.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.StringTokenizer;

public class LoggedInActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    private ProgressGenerator progressGenerator;
    private EditText userEmail;
    private EditText userPwd;
    private ActionProcessButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        progressGenerator = new ProgressGenerator(this);

        userEmail = (EditText)findViewById(R.id.email_in);
        userPwd = (EditText)findViewById(R.id.password_in);
        loginBtn = (ActionProcessButton)findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uEmail = userEmail.getText().toString();
                String uPwd = userPwd.getText().toString();
                if (!uEmail.equals("") || !uPwd.equals("")){
                    progressGenerator.start(loginBtn);
                    loginBtn.setEnabled(false);
                     userEmail.setEnabled(false);
                     userPwd.setEnabled(false);
                    ParseUser.logInInBackground(uEmail, uPwd, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e == null){
                                // TODO
                            }

                        }
                    });

                }else{
                    // TODO fields empty
                }
            }
        });
    }
    @Override
    public void onComplete() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SignupActivity.this,LoggedInActivity.class));
//            }
//        },2000);
//
//        });
        startActivity(new Intent(LoggedInActivity.this,ChatActivity.class));
    }
}

package com.dayal.talkative.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dayal.talkative.R;
import com.dayal.talkative.util.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    private ProgressGenerator progressGenerator;
    private EditText userName;
    private EditText email;
    private EditText password;
    private ActionProcessButton signupBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressGenerator = new ProgressGenerator(this);

        userName = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        signupBtn= (ActionProcessButton) findViewById(R.id.signup_btn);
        textView = (TextView) findViewById(R.id.textView);

        
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        final String uName = userName.getText().toString();
        final String uEmail = email.getText().toString();
        final String pwd = password.getText().toString();

        if (uName.equals("") || uEmail.equals("") || pwd.equals("")){
            Toast.makeText(SignupActivity.this,"Some fields are empty !!",Toast.LENGTH_SHORT);
        }else{
            ParseUser user = new ParseUser();
            //set core properties
            user.setUsername(uName);
            user.setEmail(uEmail);
            user.setPassword(pwd);

//             set custom properties
//            user.put("key","value");

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){                      // if no error
                    progressGenerator.start(signupBtn);
                        signupBtn.setEnabled(false);
                        userName.setEnabled(false);
                        email.setEnabled(false);
                        password.setEnabled(false);
                           logUserIn(uEmail,pwd);

                    }
                }
            });

        }
    }

    private void logUserIn(String email, String pwd) {
        ParseUser.logInInBackground(email, pwd, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
//                   if (e == null)
//                    Toast.makeText(SignupActivity.this, "Logged in !", Toast.LENGTH_SHORT).show();
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
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            textView.setText("Start Chatting");
             textView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     startActivity(new Intent(SignupActivity.this,ChatActivity.class));
                 }
             });
    }
}

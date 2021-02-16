package com.example.cs360mod3adampomerantz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import Model.GoalWeight;
import Model.Login;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    Button buttonLogin;
    public Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
    //requests permissions to send text notification
       ActivityCompat.requestPermissions(this, new String[]{SEND_SMS, READ_SMS, READ_PHONE_NUMBERS, READ_PHONE_STATE}, 100);

//checks to see if the user has entered a username
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //calls function when user enters username
                EnableButtonIfReady();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); //checks to see if the user has entered a password
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //calls function when user enters password
                EnableButtonIfReady();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
           public void onClick(View v){
//checks if the users login is already in the DB and if its not then it adds the new credentials to the DB
                String username = editTextUsername.getText().toString().toUpperCase();
                String password = editTextPassword.getText().toString();

                WeightTrackingDatabase weightTrackingDb =  new WeightTrackingDatabase(MainActivity.this);

                Login login = weightTrackingDb.getLogin(username, password);


               if(login != null){
                   id = login.getId();
                   GoalWeight goal = null;
                   try {
                       goal = weightTrackingDb.getGoal(id);
                   }
                   catch (Exception ex){ startActivity(new Intent(MainActivity.this, GoalActivity.class).putExtra("Uid", id)); }
                   
                   if(goal != null && goal.getGoal() > 0 && goal.getGoal() != null) {
                       startActivity(new Intent(MainActivity.this, HomeActivity.class).putExtra("Uid", id));
                   }
                   else{
                       startActivity(new Intent(MainActivity.this, GoalActivity.class).putExtra("Uid", id));
                   }
                }
                else {
                   boolean addLogin = weightTrackingDb.addLogin(username, password);

                   if (addLogin == true){
                       Login getLoginId = weightTrackingDb.getLogin(username, password);

                       id = getLoginId.getId();
                       Toast.makeText(MainActivity.this, "login created", Toast.LENGTH_LONG);

                       startActivity(new Intent(MainActivity.this, GoalActivity.class).putExtra("Uid", id));

                   }
                   else{
                       Toast.makeText(MainActivity.this, "login creation failed", Toast.LENGTH_LONG);
                   }
                }

           }
        });

    }


    public void EnableButtonIfReady() {
        //checks to make sure the username and password are at least 1 character long
        boolean isReady = false;
        if(    editTextUsername.getText().toString().length() > 0 &&
                editTextPassword.getText().toString().length() > 0)
        {
            isReady = true;
        }
        buttonLogin.setEnabled(isReady); //enables login button
    }
}


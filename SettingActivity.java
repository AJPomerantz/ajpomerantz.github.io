package com.example.cs360mod3adampomerantz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import Model.GoalWeight;
import Model.Login;

public class SettingActivity extends AppCompatActivity {
    private EditText goal;
    private EditText pass;
    public Integer id;
    private String NewPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);
        goal = findViewById(R.id.goalWeight);
        pass = findViewById(R.id.changePassword);

        //gets the users id
        Intent intent = getIntent();
        id = intent.getIntExtra("Uid", -1);
    }
    public void Logout(View view)
    {
        //redirects user back to login screen
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
    }
    public void Home(View view)
    {
        //redirects user back to home screen
        Intent intent = getIntent();
        id = intent.getIntExtra("Uid", -1);
        startActivity(new Intent(SettingActivity.this, OldHomeActivity.class).putExtra("Uid", id));
    }
    public void ChangeGoal(View v)
    {
        //changes the users goal
        try {
            float newGoal = Float.parseFloat(String.valueOf(goal.getText()));
            if (newGoal > 90) {
                WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(SettingActivity.this);
                GoalWeight goalWeight = new GoalWeight();
                goalWeight.GoalWeight(id, newGoal);
                weightTrackingDb.updateGoalWeight(goalWeight);
            }
        }
        catch (Exception ex){

        }
    }
    public void ChangePass(View v){
        try {
            NewPass = String.valueOf(pass.getText());
            WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(SettingActivity.this);
            Login login = new Login();
            login.Login(id, NewPass);
            weightTrackingDb.updateLogin(login);
        }
        catch (Exception ex){

        }
    }
}

package com.example.cs360mod3adampomerantz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class GoalActivity extends AppCompatActivity {
    private EditText editGoalWeight;
    private Button buttonSubmitGoal;
    public Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_page);
        editGoalWeight = findViewById(R.id.editGoalWeight);
        buttonSubmitGoal = findViewById(R.id.buttonSubmitGoal);


//checks to see ig GoalWeight has been changed
        editGoalWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EnableGoalButtonIfReady();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
//enables goal weight button as long as the goal is more than 90 pounds
    public void EnableGoalButtonIfReady(){
        boolean isReady = false;
        float goal = Float.parseFloat(editGoalWeight.getText().toString());
        if ( goal > 90.0){
            isReady = true;
        }
        buttonSubmitGoal.setEnabled(isReady);
    }
//sets the users goal weight and inserts it into th database
    public void GoalSet(View view)
    {
        Intent intent = getIntent();
        id = intent.getIntExtra("Uid", -1);

        float goal = Float.parseFloat(editGoalWeight.getText().toString());
        WeightTrackingDatabase weightTrackingDb =  new WeightTrackingDatabase(GoalActivity.this);

        Boolean goalWeight = weightTrackingDb.addGoalWeight(goal, id);

        if(goalWeight){
            startActivity(new Intent(GoalActivity.this, HomeActivity.class).putExtra("Uid", id));
        }

    }
}


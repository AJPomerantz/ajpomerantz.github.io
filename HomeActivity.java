package com.example.cs360mod3adampomerantz;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

import Model.DailyWeight;
import Model.GoalWeight;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;

public class HomeActivity extends AppCompatActivity {
    public Integer id;
    boolean update = false;
    private EditText weight;
    private TextView day;
    private TextView goalTrack;
    public boolean[] goalReached = {false};
    public float goalCheck;
    private Button buttonAddRow;
    public int rowCount;
    public int dayN = 1;
    public int weightN = 1;
    public float currentWeight;
    public float weightRemaining;
    public List<DailyWeight> dailyWeightList;
    @Override
    protected void onCreate(Bundle newInstanceState) {
        super.onCreate(newInstanceState);
        setContentView(R.layout.home_page);
        goalTrack = findViewById(R.id.goalTrack);
        buttonAddRow = findViewById(R.id.buttonAddRow);

        Intent intent = getIntent();
        id = intent.getIntExtra("Uid", -1);
        WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(HomeActivity.this);
        rowCount = weightTrackingDb.getRowCount(id);
        init(); //call init function which builds table
        GoalText(); //sets goal text as top of home page




    }
    public void init(){
        //building out table
        TableLayout ll = findViewById(R.id.weightTrackerTableTest);
        String tempWeight;
        WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(HomeActivity.this);
        dailyWeightList = weightTrackingDb.getDailyWeights(id);
        for (int i = 0; i <rowCount; i++) {
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER_HORIZONTAL);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            if ( (i & 1) == 0 ){
                for (int x = 0; x < 5; x++) {
                    day = new TextView(this);
                    day.setText("Day " + dayN);
                    day.setGravity(Gravity.CENTER_HORIZONTAL);
                    day.setPadding(3,1,3,1);
                    row.addView(day);
                    dayN += 1;
                }
            }
            else {

                for (int x = 0; x < 5; x++) {
                    weight = new EditText(this);
                    weight.setGravity(Gravity.CENTER_HORIZONTAL);
                    weight.setInputType(InputType.TYPE_CLASS_NUMBER);
                    weight.setId(weightN);
                    for ( DailyWeight y : dailyWeightList) {
                        if (y.getDay() == weightN) {
                            try {// attempts to update table with users daily weights if they are in the DB
                                tempWeight = String.valueOf(y.getDaily());
                                weight.setText(tempWeight);
                                if (goalCheck == dailyWeightList.get(weightN).getDaily()) {
                                    goalReached[0] = true;
                                    GoalReached();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    applyTextWatcher(weight, weightN);

                    row.addView(weight);
                    weightN += 1;
                }
            }
            ll.addView(row, i);
        }
    }
    protected  void applyTextWatcher(final EditText et, final int finalDayN){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    if (Float.parseFloat(et.getText().toString()) > 90) {
                        update = true;
                    }
                } catch (Exception ex) {

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    float pWeight = Float.parseFloat(et.getText().toString());
                    WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(HomeActivity.this);
                    GoalWeight goalWeight = weightTrackingDb.getGoal(id);
                    float goalCheck = goalWeight.getGoal();
                    if (pWeight == goalCheck) {
                        goalReached[0] = true;
                        GoalReached();
                    }


                    if (pWeight >= 90 && update == false) {
                        DailyWeight dailyWeight = new DailyWeight();
                        dailyWeight.DailyWeight(id, finalDayN, pWeight);
                        weightTrackingDb.addDailyWeight(dailyWeight);
                    }
                    if (pWeight >= 90 && update == true) {
                        DailyWeight dailyWeight = new DailyWeight();
                        dailyWeight.DailyWeight(id, finalDayN, pWeight);
                        weightTrackingDb.updateDailyWeight(dailyWeight);
                        update = false;
                    }
                    dailyWeightList = weightTrackingDb.getDailyWeights(id);
                    GoalText();
                } catch (Exception ex) {
                    WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(HomeActivity.this);
                    weightTrackingDb.deleteDailyWeight(id, finalDayN);

                }
            }
        });
    }
    public void GoalReached() {
// when the user reaches there goal it sends them an SMS if they granted all the permissions and then sets the goal to 0. If the user denied permissions then it just sets there goal to 0
            SmsManager smsManager = SmsManager.getDefault();
            if (ActivityCompat.checkSelfPermission(this, READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, READ_PHONE_NUMBERS) ==
                            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                String mPhoneNumber = tMgr.getLine1Number();
                String Message = "You have reached your goal weight of: " + goalCheck + "!";
                smsManager.sendTextMessage(mPhoneNumber, null, Message, null, null);
            }
            goalReached[0] = false;
            GoalWeight newGoal = new GoalWeight();
            newGoal.GoalWeight(id,0f);
            WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(HomeActivity.this);
            weightTrackingDb.updateGoalWeight(newGoal);
            return;
    }
    public void AddRow(View view){
        //adds 2 to row count and rebuilds page
        rowCount += 2;
        GoalWeight rc = new GoalWeight();
        rc.RowCount(id,rowCount);
        WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(HomeActivity.this);
        weightTrackingDb.updateRowCount(rc);
        finish();
        startActivity(getIntent());
    }
    public void Setting(View view)
    {
        //redirects user to setting page
        startActivity(new Intent(HomeActivity.this, SettingActivity.class).putExtra("Uid", id));
    }
    public void Logout(View view)
    {
        //logs user out and redirects to login page
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

    public void GoalText(){
        WeightTrackingDatabase weightTrackingDb = new WeightTrackingDatabase(HomeActivity.this);
        GoalWeight goalWeight = weightTrackingDb.getGoal(id);
        goalCheck = goalWeight.getGoal();
        for (DailyWeight x: dailyWeightList) {
            currentWeight = x.getDaily();
        }
        if(goalCheck == 0.0){
            goalTrack.setText("Congratulations you reached your goal!" + "\r\n" + "Click the settings button and set another goal!"); //setting goal reached text
        }
        if(currentWeight > goalCheck && goalCheck != 0.0){
            weightRemaining = currentWeight - goalCheck;
        }
        else{
            weightRemaining = goalCheck - currentWeight;
        }
        if(goalCheck != 0.0) {
            goalTrack.setText("Goal Weight Is: " + goalCheck + "\r\n" + "You are " + weightRemaining + " away from your goal!"); //setting text with goal and remaining weight
        }
    }


}

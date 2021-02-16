## Welcome to Adam Pomerantz's ePortfolio

To demonstrate my skills as a software engineer I have provided three artifacts which show my skills in software design and engineering, algorithms and data structures, and databases. The three artifacts come from a weight tacking application I created with Android Studio. The application tracks the users daily weight to help them reach a goal they have set for themselves. 

1. **Software Design and Engineering**
Below is the applications main page layout code. This page contains a scrollable table which gets dynamically created when the user connects to the main page. The text view below is used to display the users goal weight and how close they are to meeting there goal. This is also dynamically added and will be showed in the next artifact. There are three buttons one which allows the user to add additional days to the table, it contains 30 days by default. The other two buttons are for navigation, logout and settings.
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:visibility="visible"
    tools:context=".HomeActivity">

    <TextView
        android:id="@+id/textView"
        android:layout_width="251dp"
        android:layout_height="48dp"
        android:layout_marginTop="4dp"
        android:text="@string/weightTrack"
        android:textAppearance="@style/TextAppearance.AppCompat.Display1"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ScrollView
        android:layout_width="371dp"
        android:layout_height="546dp"
        app:layout_constraintBottom_toTopOf="@+id/buttonSetting"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/goalTrack">
        <HorizontalScrollView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:fillViewport="true">

            <TableLayout
                android:id="@+id/weightTrackerTableTest"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="88dp"
                android:nestedScrollingEnabled="true"
                android:padding="@dimen/activity_horizontal_margin"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                android:gravity="center">


            </TableLayout>
        </HorizontalScrollView>
    </ScrollView>
    <Button
        android:id="@+id/buttonAddRow"
        android:layout_width="91dp"
        android:layout_height="58dp"
        android:layout_marginStart="32dp"
        android:layout_marginBottom="4dp"
        android:text="Add Row"
        android:onClick="AddRow"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

    <Button
        android:id="@+id/buttonSetting"
        android:layout_width="91dp"
        android:layout_height="58dp"
        android:layout_marginBottom="4dp"
        android:onClick="Setting"
        android:text="Settings"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/buttonLogOut"
        app:layout_constraintHorizontal_bias="0.471"
        app:layout_constraintStart_toEndOf="@+id/buttonHome" />

    <Button
        android:id="@+id/buttonLogOut"
        android:layout_width="91dp"
        android:layout_height="58dp"
        android:layout_marginEnd="32dp"
        android:layout_marginBottom="4dp"
        android:onClick="Logout"
        android:text="Log Out"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <TextView
        android:id="@+id/goalTrack"
        android:layout_width="315dp"
        android:layout_height="64dp"
        android:layout_marginBottom="570dp"
        android:textAlignment="center"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="@+id/weightTracker"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
2. **Algorithms and Data Structures**
This artifact builds on the previous one by implementing its functionality. The functions below are what build the the layout page and fill it with useful information.

The init function is what builds the weight tracking table.
```
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
    ```
    ```
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

```

3. **Databases**

Whenever you commit to this repository, GitHub Pages will run [Jekyll](https://jekyllrb.com/) to rebuild the pages in your site, from the content in your Markdown files.

### Markdown

Markdown is a lightweight and easy-to-use syntax for styling your writing. It includes conventions for

```markdown
Syntax highlighted code block

# Header 1
## Header 2
### Header 3

- Bulleted
- List

1. Numbered
2. List

**Bold** and _Italic_ and `Code` text

[Link](url) and ![Image](src)
```

For more details see [GitHub Flavored Markdown](https://guides.github.com/features/mastering-markdown/).

### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/AJPomerantz/ajpomerantz.github.io/settings). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://docs.github.com/categories/github-pages-basics/) or [contact support](https://github.com/contact) and we’ll help you sort it out.

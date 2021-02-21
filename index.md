## Welcome to Adam Pomerantz's ePortfolio

To demonstrate my skills as a software engineer, I have provided three artifacts which show my skills in software design and engineering, algorithms and data structures, and databases. The three artifacts come from a weight tacking application I created with Android Studio. The application tracks the user’s daily weight to help them reach a goal they have set for themselves. I selected this item because I believe it does the best job of showcasing my skills from front end to back end. I originally created the application in late 2019, since then I have drastically improved as a software engineer. 

Here is the code review I made before I made any enhancements.
video: https://www.youtube.com/embed/42ac-EtVP6A

**Software Design and Engineering**

The components I have been currently working on to display my Software Design/Engineering skills are the main page and main activity. The main page is the xml file, and the main activity is the java. The main page is a table of days where the user can enter their weights. I improved the artifact by removing copy and pasted code from both the java and xml file. I also improved it by adding a way for the user to add new rows and made the table scrollable. I met my enhancement plan from Module One and went a bit further by implementing the scroll ability. From on the artifact, I learned that I have improved a lot of a programmer in the last year. I ended up entirely gutting the java and xml files to completely enhance the application. One challenge I faced was dynamically adding the on text changed event listeners dynamically. I also have not touched Android Studio since I last worked on the application a year ago, so it took me a bit to become reacquainted with it.

Below is the applications main page layout code. This page contains a scrollable table which gets dynamically created when the user connects to the main page. The text view below is used to display the users goal weight and how close they are to meeting their goal. This is also dynamically added and will be showed in the next artifact. There are three buttons one which allows the user to add additional days to the table, it contains 30 days by default. The other two buttons are for navigation, logout, and settings.

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


**Algorithms and Data Structures**

The components I have been currently working on to display my algorithms and data structure skills is the main activity. The main activity is the java file behind the main page xml file. I enhanced the algorithms by making each row, from the table, which is displayed on the main page, to be dynamically added. I also added the code which allows the add rows button to function. Finally, I created a new algorithm which calculates how much weight they have left until they reach their goal. If they meet their goal it gives the user praise and tells them to set another goal. I exceeded the enhancement plan I set up in module one. I crated a way to dynamically create the table and add additional days. I surpassed the plan by adding the dynamic text which tells the user how much weight they have left until they reach their goal and congratulates them if they reach it. I learned a lot about myself as a programmer and the progress I have made. When I initially made the application, I could not figure out how to dynamically add rows to a table, but it came very easily this time around. A challenge I faced was coming up with a good way to update the remaining weight text when the user enters a new weight. I had to make sure it would only update if they updated or add a new value to the last day which has a weight. The enhancements I am making to the application are drastically improving the applications useability and functionality.

This artifact builds on the previous one by implementing its functionality. The functions below are what build the layout page and fill it with useful information.

The init function is what builds the weight tracking table. This function calls the apply text watcher and goal reached functions.


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
The apply text watcher function adds a text watcher to each edit text and either adds, updates or deletes based on what the user enters.

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
 ``` 
 
The goal reached function get called when the user reaches their goal. It would send them an SMS, if they accepted the permissions when they first opened the app. It also sets their new goal to 0 so they can go to the settings and set another goal.
 
 
 ```
    
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

**Databases**

For the database enhancements I added a new column and enhanced its overall reliability. The column I added tracks how many rows the table has. The user can add rows to increase how long they track their weight for. I also made the following enhancements: I added a foreign key on the goal weight table id column which references the logins tables id column, I also added a similar foreign key on the daily weight tables user id field which references the login tables user id. These foreign keys will help prevent bad data from being inserted into the database. Finally, I created an update method which allows the row count number to be updated. I was able to exceed the enhancement plans I made in module one. Originally, I was only going to add support for the table row count, but I ended up doing much more than that. I did not face many challenges, but I was able to see how much I have improved since I originally created this application.

Below is the database creation and calls. There are three tables total, login table, goal weight table and daily weight table. The login table stores the user’s credentials and their id. The goal weight table stores the users id, goal weight and row count. 

```
    private static final class LoginTable {
        private static final String TABLE = "Login";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }

    private static final class GoalWeightTable {
        private static final String TABLE = "GoalWeight";
        private static final String COL_ID = "_id";
        private static final String COL_GOALWEIGHT = "goal";
        private static final String COL_ROWCOUNT = "rowcount";
    }
    private static final class DailyWeightTable {
        private static final String TABLE = "DailyWeight";
        private static final String COL_ID = "_id";
        private static final String COL_DAY = "daynumber";
        private static final String COL_DAILYWEIGHT = "daily";
    }

    @Override //creates tables
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + LoginTable.TABLE + " (" +
                LoginTable.COL_ID + " integer primary key autoincrement, " +
                LoginTable.COL_USERNAME + " text NOT NULL UNIQUE, " +
                LoginTable.COL_PASSWORD + " text not null)");

        db.execSQL("create table " + GoalWeightTable.TABLE + " (" +
                GoalWeightTable.COL_ID + " integer primary key, " +
                GoalWeightTable.COL_GOALWEIGHT + " REAL, " +
                GoalWeightTable.COL_ROWCOUNT + " integer, "+
                "FOREIGN KEY(" + GoalWeightTable.COL_ID + ") REFERENCES " + LoginTable.TABLE +"(" + LoginTable.COL_ID + ") )");

        db.execSQL("create table " + DailyWeightTable.TABLE + " (" +
                DailyWeightTable.COL_ID + " integer, " +
                DailyWeightTable.COL_DAY + " integer, " +
                DailyWeightTable.COL_DAILYWEIGHT + " REAL, " +
                "FOREIGN KEY(" + DailyWeightTable.COL_ID + ") REFERENCES " + LoginTable.TABLE +"(" + LoginTable.COL_ID + ") )");


    }

    @Override //when upgraded tabled get dropped and recreated
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + LoginTable.TABLE);
        db.execSQL("drop table if exists " + GoalWeightTable.TABLE);
        db.execSQL("drop table if exists " + DailyWeightTable.TABLE);
        onCreate(db);
    }

    @Override
    public  void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                db.execSQL("pragma foreign_keys = on;");
            } else {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
    }

    public List<DailyWeight> getDailyWeights (int userId){
        List<DailyWeight> dailyWeights = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT " + DailyWeightTable.COL_DAILYWEIGHT + ", " + DailyWeightTable.COL_DAY + " FROM " + DailyWeightTable.TABLE +
                     " WHERE " + DailyWeightTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { Float.toString(userId) });
        if(cursor.moveToFirst()){
            do {
                float daily = cursor.getFloat(0);
                int day = cursor.getInt(1);
                DailyWeight dailyweight = new DailyWeight(day, daily);
                dailyWeights.add(dailyweight);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  dailyWeights;
    }
//adds the users daily weight that they record
    public void addDailyWeight(DailyWeight dailyWeight){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DailyWeightTable.COL_DAY, dailyWeight.getDay());
        values.put(DailyWeightTable.COL_DAILYWEIGHT, dailyWeight.getDaily());
        values.put(DailyWeightTable.COL_ID, dailyWeight.getId());
        db.insert(DailyWeightTable.TABLE, null, values);
        db.close();
    }
// updates the users daily weight if they change it
    public void updateDailyWeight (DailyWeight dailyWeight){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] args = {String.valueOf(dailyWeight.getId()), String.valueOf(dailyWeight.getDay())} ;
        values.put(DailyWeightTable.COL_DAY, dailyWeight.getDay());
        values.put(DailyWeightTable.COL_DAILYWEIGHT, dailyWeight.getDaily());
        values.put(DailyWeightTable.COL_ID, dailyWeight.getId());
        db.update(DailyWeightTable.TABLE, values,
                DailyWeightTable.COL_ID + " =?" + " AND " + DailyWeightTable.COL_DAY + " =?", args);
        db.close();
    }
    //deletes the users daily weight
    public  void deleteDailyWeight (int id, int day){
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = {String.valueOf(id), String.valueOf(day)};
        db.delete(DailyWeightTable.TABLE,
                DailyWeightTable.COL_ID + " =?" + " AND " + DailyWeightTable.COL_DAY + " =?", whereArgs);
        db.close();
    }
//adds the users new goal weight
    public boolean addGoalWeight(float goalWeight, int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GoalWeightTable.COL_GOALWEIGHT, goalWeight);
        values.put(GoalWeightTable.COL_ID, id);
        values.put(GoalWeightTable.COL_ROWCOUNT, 12);
        long insert = db.insert(GoalWeightTable.TABLE, null, values);
        db.close();
        if (insert == -1){
            return false;
        }
        else  {
            return true;
        }

    }
    //updates the users goal weight
    public void updateGoalWeight (GoalWeight goalWeight){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(goalWeight.getId())};
        ContentValues values = new ContentValues();;
        values.put(GoalWeightTable.COL_GOALWEIGHT, goalWeight.getGoal());
        db.update(GoalWeightTable.TABLE, values,
                DailyWeightTable.COL_ID + " =? ", args);
        db.close();
    }
    //gets the users goal weight
    public GoalWeight getGoal(int id) {
        GoalWeight goalWeight = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + GoalWeightTable.TABLE +
                " where " + GoalWeightTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { Float.toString(id) });

        if (cursor.moveToFirst()) {
            goalWeight = new GoalWeight();
            goalWeight.setGoal(cursor.getFloat(1));
        }
        cursor.close();
        db.close();
        return goalWeight;
    }
    //gets the users row count
    public int getRowCount(int id) {
        int rowCount = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select " + GoalWeightTable.COL_ROWCOUNT + " from " + GoalWeightTable.TABLE +
                " where " + GoalWeightTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { Float.toString(id) });

        if (cursor.moveToFirst()) {
            rowCount = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return rowCount;
    }
    //updates the users row count
    public void updateRowCount (GoalWeight goalWeight){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(goalWeight.getId())};
        ContentValues values = new ContentValues();;
        values.put(GoalWeightTable.COL_ROWCOUNT, goalWeight.getRowCount());
        db.update(GoalWeightTable.TABLE, values,
                DailyWeightTable.COL_ID + " =? ", args);
        db.close();
    }
//checks the users login credentials
    public Login getLogin(String username, String password) {
        Login login = null;
        username = username.toUpperCase();
        String sql = "select " + LoginTable.COL_ID + " from " + LoginTable.TABLE +
                " where " + LoginTable.COL_USERNAME + " = ?" +
                " and " + LoginTable.COL_PASSWORD + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(sql, new String[]{username, password});

            if (cursor.moveToFirst()) {

                int userId = cursor.getInt(0);
                login = new Login(userId);
            }
            else{

            }
            cursor.close();
            db.close();
            return login;
    }
// adds a new login to the database
    public boolean addLogin(String username, String password){
        username = username.toUpperCase();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LoginTable.COL_USERNAME, username);
        values.put(LoginTable.COL_PASSWORD, password);
       long insert = db.insert(LoginTable.TABLE, null, values);
        db.close();
       if(insert == -1){
           return false;
       }
       else{
           return true;
       }
    }
//updates the users password
    public void updateLogin (Login login){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();;
        String[] args = {String.valueOf(login.getId())};
        values.put(LoginTable.COL_PASSWORD, login.getPassword());
        db.update(LoginTable.TABLE, values,
                DailyWeightTable.COL_ID + " =? ", args);
        db.close();
    }
}
```

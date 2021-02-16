package com.example.cs360mod3adampomerantz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import Model.DailyWeight;
import Model.GoalWeight;
import Model.Login;


public class WeightTrackingDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeightTracking.db";
    private static  final int VERSION = 10;


   // this class is used for all database calls


    public WeightTrackingDatabase(Context context){
        super (context, DATABASE_NAME, null, VERSION);
    }



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


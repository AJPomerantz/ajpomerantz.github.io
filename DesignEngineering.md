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

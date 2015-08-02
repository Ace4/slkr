package neongarage.slakr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Aaron on 1/28/2015.
 */
public class AddAssignmentActivity extends ActionBarActivity{
    private EditText assignmentName;
    private EditText assignmentGrade;
    private Spinner assignmentSpinner;
    private EditText assignmentWeight;
    private Spinner assignmentCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AddAssignmentFragment())
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void finishActivity(View view){
        Intent returnIntent = new Intent();
        assignmentName   = (EditText)findViewById(R.id.assignmentName);
        assignmentGrade  =  (EditText)findViewById(R.id.assignmentGrade);
        assignmentWeight = (EditText) findViewById(R.id.assignmentWeight);
        assignmentSpinner = (Spinner)findViewById(R.id.assignmentSpinner);
        assignmentCompleted = (Spinner) findViewById(R.id.assignmentCompleted);
        returnIntent.putExtra("item_name", assignmentName.getText().toString());
        returnIntent.putExtra("item_grade", assignmentGrade.getText().toString());
        returnIntent.putExtra("item_weight", assignmentWeight.getText().toString());
        returnIntent.putExtra("item_completed", assignmentCompleted.getSelectedItem().toString());
        returnIntent.putExtra("item_type", assignmentSpinner.getSelectedItem().toString());
        String date = new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(new Date());
        returnIntent.putExtra("item_date", date);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}

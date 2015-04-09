package neongarage.slakr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Aaron on 1/28/2015.
 */
public class AssignmentListActivity extends ActionBarActivity{
    static final int NEW_ASSIGNMENT_REQUEST = 2;
    private MySQLiteHelper db;
    private String assignmentName;
    private float assignmentGrade;
    private String assignmentType;
    private float assignmentWeight;
    Course c;
    private Course assignmentCourse;
    private int click_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AssignmentListFragment(), "course_detail")
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addAssignment(View view) {
        Intent intent = new Intent(this,AddAssignmentActivity.class);
        startActivityForResult(intent, NEW_ASSIGNMENT_REQUEST);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        if (requestCode == NEW_ASSIGNMENT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                assignmentName = data.getStringExtra("item_name");
                assignmentGrade  = Float.valueOf(data.getStringExtra("item_grade"));
                assignmentType = data.getStringExtra("item_type");
                assignmentWeight = Float.valueOf(data.getStringExtra("item_weight"));
              AssignmentListFragment fragment = (AssignmentListFragment) getSupportFragmentManager().findFragmentByTag("course_detail");
                Assignment newAssignment = new Assignment(assignmentName, assignmentType, assignmentGrade, assignmentWeight, null);
                fragment.addAssignment(newAssignment);
                db.addAssignment(newAssignment, c.getDept(), c.getNum());
            }
        }
    }
}
















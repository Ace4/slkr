package neongarage.slakr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

/**
 * Created by Aaron on 1/22/2015.
 */
public class CourseListActivity extends ActionBarActivity {
    static final int NEW_COURSE_REQUEST = 1;
    private String courseDept;
    private String courseNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CourseListFragment(), "course_list")
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


    public void addCourse(View view) {
        Intent intent = new Intent(this, AddCourseActivity.class);
        startActivityForResult(intent, NEW_COURSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == NEW_COURSE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                courseDept = data.getStringExtra("course_dept");
                courseNum  = data.getStringExtra("course_num");
                Course newCourse = new Course(courseDept, courseNum);
                CourseListFragment fragment = (CourseListFragment) getSupportFragmentManager().findFragmentByTag("course_list");
               fragment.addCourse(newCourse);
            }
        }
    }
}
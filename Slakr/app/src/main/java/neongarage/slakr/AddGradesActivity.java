package neongarage.slakr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Aaron on 1/28/2015.
 */
/**
 * Created by Aaron on 2/26/2015.
 */
public class AddGradesActivity extends ActionBarActivity{
    private EditText courseDept;
    private EditText courseNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AddGradesFragment())
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

/*    public void finishActivity(View view){
        Intent returnIntent = new Intent();
        courseDept   = (EditText)findViewById(R.id.courseDept);
        courseNum   =  (EditText)findViewById(R.id.courseNum);
        returnIntent.putExtra("course_dept", courseDept.getText().toString());
        returnIntent.putExtra("course_num", courseNum.getText().toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    } */
}

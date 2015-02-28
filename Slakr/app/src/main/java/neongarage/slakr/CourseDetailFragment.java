package neongarage.slakr;

/**
 * Created by Aaron on 1/22/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CourseDetailFragment extends ListFragment {
    private ListView assignmentListView;
    private String[] assignmentArray ;
    private String itemGrade;
    private AssignmentAdapter assignmentAdapter;
    private long id;
    private static int NEW_GRADE_REQUEST = 1;
    //The data to show
    List<Map<String, String>> coursesList = new ArrayList<Map<String, String>>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_course_detail, container, false);
       return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        assignmentArray = new String[10];
        for(int i=0; i < assignmentArray.length; i++){
            assignmentArray[i] = "Assignment " + i;
        }

        assignmentAdapter = new AssignmentAdapter(getActivity(), R.layout.row_course_details, assignmentArray);
        setListAdapter(assignmentAdapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long click_id) {
        id = click_id;
        Log.i("FragmentList", "Item clicked: " + id);
        TextView assignment = (TextView) v.findViewById(R.id.assignmentTextView);
        Intent intent = new Intent(getActivity(), AddGradesActivity.class);
        intent.putExtra("click_id", id);
        startActivityForResult(intent, NEW_GRADE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        long clickId = -1;
        if (requestCode == NEW_GRADE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                itemGrade = data.getStringExtra("item_grade");
                clickId = data.getLongExtra("click_id", -1);
                Log.i("FragmentList.onResult", "Item grade: " + itemGrade + " ID: " + id);
                addGrade(itemGrade, (long)id);
            }
        }
    }

    private void addGrade(String grade, long id){
        Log.i("FragmentList.addGrade", "Item grade: " + itemGrade + "ID: " + id);
        String date = new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(new Date());
        View v = this.getView();
        ListView lv =  (ListView)view.findViewById(android.R.id.list);
        TextView gradeView = (TextView) lv.getChildAt((int)id - lv.getFirstVisiblePosition()).findViewById(R.id.detail_grade);
        gradeView.setText(grade + '%');
        TextView mdate = (TextView) lv.getChildAt((int)id - lv.getFirstVisiblePosition()).findViewById(R.id.date_updated);
        mdate.setText(date);
        assignmentAdapter.notifyDataSetChanged();
    }

    private void initList() {
        // We populate the courses
        coursesList.add(createCourse("course", "CS311"));
        coursesList.add(createCourse("course", "CS411"));
    }

    private HashMap<String, String> createCourse(String key, String name) {

        HashMap<String, String> course = new HashMap<String, String>();
        course.put(key, name);
        return course;
    }

    public void addCourseItem(){

        initList();
        // We get the ListView component from the layout
        // This is a simple adapter that accepts as parameter
        // Context
        // Data list
        // The row layout that is used during the row creation
        // The keys used to retrieve the data
        // The View id used to show the data. The key number and the view id must match
        //CourseAdapter courseAdp = new CourseAdapter(getActivity(),new String [10] );
        //ListView listView = (ListView) getView().findViewById(R.id.course_list_view);
        //setListAdapter(courseAdp);
        String[] stringArray;
        stringArray = new String[10];
        for(int i=0; i < stringArray.length; i++){
            stringArray[i] = "String " + i;
        }
        //   ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArray);
    }
}
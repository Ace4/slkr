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
    private List<String> assignmentArray = new ArrayList<String>();
    private List<String> dateArray = new ArrayList<String>();
    private List<String> gradeArray = new ArrayList<String>();
    private List<String> typeArray = new ArrayList<String>();
    private String itemGrade;
    private String itemName;
    private AssignmentAdapter assignmentAdapter;
    private long id;
    private static int UPDATE_GRADE_REQUEST = 1;
    private static int NEW_ASSIGNMENT_REQUEST = 2;
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

        for(int i=0; i < 3; i++){
            assignmentArray.add("Assignment " + i);
            gradeArray.add("100%");
            String date = new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(new Date());
            dateArray.add(date);
            typeArray.add("HW");
        }

        assignmentAdapter = new AssignmentAdapter(getActivity(), R.layout.row_course_details, assignmentArray, gradeArray, dateArray, typeArray);
        setListAdapter(assignmentAdapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long click_id) {
        id = click_id;
        Log.i("FragmentList", "Item clicked: " + id);
        TextView assignment = (TextView) v.findViewById(R.id.assignment_name);
        Intent intent = new Intent(getActivity(), AddGradesActivity.class);
        intent.putExtra("click_id", id);
        startActivityForResult(intent, UPDATE_GRADE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        long clickId = -1;
        if (requestCode == UPDATE_GRADE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                itemGrade = data.getStringExtra("item_grade");
                clickId = data.getLongExtra("click_id", -1);
                Log.i("FragmentList.onResult", "Item grade: " + itemGrade + " ID: " + id);
                addGrade(itemGrade, (long)id);
            }
        }
        if (requestCode == NEW_ASSIGNMENT_REQUEST){
            //check if assignment activity was successful
            if (resultCode == Activity.RESULT_OK){
                itemGrade = data.getStringExtra("item_grade");
                itemName = data.getStringExtra("item_name");
                //TODO add a new item to the list view but now go to bed
                assignmentArray.add(itemName);
                assignmentAdapter = new AssignmentAdapter(getActivity(), R.layout.row_course_details, assignmentArray, gradeArray, dateArray, typeArray);
                assignmentAdapter.notifyDataSetChanged();
            }
        }

    }

    public void addAssignment(String name, String grade, String type){
        Log.i("GragmentList.addAssit", "Item Grade: " + name);
        assignmentArray.add(name);
        gradeArray.add(grade);
        String date = new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(new Date());
        dateArray.add(date);
        typeArray.add(type);
        assignmentAdapter = new AssignmentAdapter(getActivity(), R.layout.row_course_details, assignmentArray, gradeArray, dateArray, typeArray);
        assignmentAdapter.notifyDataSetChanged();

    }
    private void addGrade(String grade, long id){
        Log.i("FragmentList.addGrade", "Item grade: " + itemGrade + "ID: " + id);

        View v = this.getView();
        ListView lv =  (ListView)view.findViewById(android.R.id.list);
        TextView gradeView = (TextView) lv.getChildAt((int)id - lv.getFirstVisiblePosition()).findViewById(R.id.assignment_grade);
        gradeView.setText(grade + '%');
        String date = new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(new Date());
        TextView mdate = (TextView) lv.getChildAt((int)id - lv.getFirstVisiblePosition()).findViewById(R.id.assignment_date);
        mdate.setText(date);
        assignmentAdapter.notifyDataSetChanged();
    }


    private HashMap<String, String> createCourse(String key, String name) {

        HashMap<String, String> course = new HashMap<String, String>();
        course.put(key, name);
        return course;
    }

    public void addCourseItem(){

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
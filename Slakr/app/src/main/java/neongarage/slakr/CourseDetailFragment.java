package neongarage.slakr;

/**
 * Created by Aaron on 1/22/2015.
 */
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDetailFragment extends ListFragment {
    private ListView assignmentListView;
    private String[] assignmentArray ;
    private AssignmentAdapter assignmentAdapter;
    //The data to show
    List<Map<String, String>> coursesList = new ArrayList<Map<String, String>>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course_list, container, false);
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
        //ListView courseView = (ListView) getView().findViewById(R.id.courseList);
        //courseView.setAdapter(courseAdapter);
        //courseAdapter = new CourseAdapter(getActivity(), new String[10]);
        setListAdapter(assignmentAdapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
        TextView t = (TextView) v.findViewById(R.id.assignmentTextView);
        t.setText(assignmentArray[(int)id] + " completed");
    }



    private void initList() {
        // We populate the courses
        coursesList.add(createCourse("course", "CS311"));
        coursesList.add(createCourse("course", "CS411"));
    }

    private HashMap<String, String> createCourse(String key, String name) {

        HashMap<String, String> planet = new HashMap<String, String>();
        planet.put(key, name);
        return planet;
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
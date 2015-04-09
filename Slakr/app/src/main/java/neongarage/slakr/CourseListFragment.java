package neongarage.slakr;

/**
 * Created by Aaron on 1/22/2015.
 */
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseListFragment extends ListFragment {
    private ListView courseListView;
    private CourseAdapter courseAdapter;
    //The data to show
    List<Course> courses = new ArrayList<Course>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course_list, container, false);
        return view;
    }

    public void onSaveInstanceState(Bundle savedState) {

        super.onSaveInstanceState(savedState);

/*        List<Course> values = courseAdapter.getValues();
        String [] list = new String[values.size()];
        list = values.toArray(list);
        savedState.putParcelable("courses", values); */
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            String[] values = savedInstanceState.getStringArray("courses");
            if (values != null) {
              /*  for(int i=0; i < values.length; i++){
                    courses.add(values[i]);
                }*/
            }
        }
        else {
            for (int i = 0; i < 3; i++) {
                Course newCourse = new Course("CS ", Integer.toString(i));
                courses.add(newCourse);
            }
        }

        courseAdapter = new CourseAdapter(getActivity(), R.layout.row_add_course, courses);
        setListAdapter(courseAdapter);
     }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
        Intent intent = new Intent(getActivity(), AssignmentListActivity.class);
        startActivity(intent);
    }

    private HashMap<String, String> createCourse(String key, String name) {

        HashMap<String, String> planet = new HashMap<String, String>();
        planet.put(key, name);
        return planet;
    }

    public void addCourse(Course newCourse){

        courses.add(newCourse);
        courseAdapter = new CourseAdapter(getActivity(), R.layout.row_add_course, courses);
        courseAdapter.notifyDataSetChanged();
    }
}
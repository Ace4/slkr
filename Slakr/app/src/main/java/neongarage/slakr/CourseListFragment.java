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
    private MySQLiteHelper db;
    private CourseAdapter courseAdapter;
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new MySQLiteHelper(getActivity());
        courses = db.getAllCourses();
        courseAdapter = new CourseAdapter(getActivity(), R.layout.row_add_course, courses);
        setListAdapter(courseAdapter);
     }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
        Intent intent = new Intent(getActivity(), CourseSummaryActivity.class);
        intent.putExtra("course", courses.get(position));
        startActivity(intent);
    }

    private HashMap<String, String> createCourse(String key, String name) {

        HashMap<String, String> planet = new HashMap<String, String>();
        planet.put(key, name);
        return planet;
    }

    public void addCourse(Course newCourse){
        courses.add(newCourse);
        db.addCourse(newCourse);
        courseAdapter = new CourseAdapter(getActivity(), R.layout.row_add_course, courses);
        courseAdapter.notifyDataSetChanged();
    }
}
package neongarage.slakr;

/**
 * Created by Aaron on 1/22/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCourseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_course, container, false);
    }

    //The data to show
    List<Map<String, String>> coursesList = new ArrayList<Map<String, String>>();

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
        SimpleAdapter simpleAdpt = new SimpleAdapter(getActivity(), coursesList, android.R.layout.simple_list_item_1, new String[] {"course"}, new int[] {android.R.id.text1});
        ListView listView = (ListView) getView().findViewById(R.id.course_list_view);
        listView.setAdapter(simpleAdpt);

    }
}
package neongarage.slakr.Assignment;

/**
 * Created by Aaron on 1/22/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import neongarage.slakr.AddGradesActivity;
import neongarage.slakr.Course.Course;
import neongarage.slakr.Course.CourseAdapter;
import neongarage.slakr.MySQLiteHelper;
import neongarage.slakr.R;

public class AssignmentListFragment extends Fragment {
    private RecyclerView mAssignmentRecyclerView;
    private AssignmentAdapter assignmentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Assignment> assignments = new ArrayList<Assignment>();
    private MySQLiteHelper db;

    private String itemGrade;
    private String itemName;
    private long id;
    private static int UPDATE_GRADE_REQUEST = 1;
    private static int NEW_ASSIGNMENT_REQUEST = 2;
    private Course c;
    //The data to show
    List<Map<String, String>> coursesList = new ArrayList<Map<String, String>>();
    private View view;

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_assignment_list, container, false);


        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        c = (Course) getActivity().getIntent().getParcelableExtra("course");
        db = new MySQLiteHelper(getActivity());
        assignments = db.getAssignmentsForCourse(c);

        mAssignmentRecyclerView =  (RecyclerView) view.findViewById(R.id.my_assignment_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        assignmentAdapter = new AssignmentAdapter(assignments);
        mAssignmentRecyclerView.setAdapter(assignmentAdapter);
       return view;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mAssignmentRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mAssignmentRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
            default:
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
        }

        mAssignmentRecyclerView.setLayoutManager(mLayoutManager);
        mAssignmentRecyclerView.scrollToPosition(scrollPosition);
        mAssignmentRecyclerView.getItemAnimator();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        SeekBar gradeSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        final TextView newGrade = (TextView) view.findViewById(R.id.desired_grade);
        gradeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }@Override
             public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                newGrade.setText(progress + "%");
            }
        });


    }

 /*   @Override
    public void onListItemClick(ListView l, View v, int position, long click_id) {
        id = click_id;
        Log.i("FragmentList", "Item clicked: " + id);
        TextView assignment = (TextView) v.findViewById(R.id.assignment_name);
        Intent intent = new Intent(getActivity(), AddGradesActivity.class);
        intent.putExtra("click_id", id);
        startActivityForResult(intent, UPDATE_GRADE_REQUEST);
    }
*/

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
                //assignmentArray.add(itemName);
                //assignmentAdapter = new AssignmentAdapter(getActivity(), R.layout.row_add_assignment, assignments);
                //assignmentAdapter.notifyDataSetChanged();
            }
        }

    }

    public void addAssignment(Assignment assignment, Course c){
        Log.i("FragmentList.addAssit", "Item Grade: " + assignment.getName());
        String date = new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(new Date());
        db.addAssignment(assignment, c.getDept(), c.getNum());
        assignments.add(assignment);
        assignmentAdapter.notifyItemInserted(assignments.size()-1);
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
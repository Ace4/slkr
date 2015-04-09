package neongarage.slakr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 1/27/2015.
 */
public class AssignmentAdapter extends ArrayAdapter {
    List<Assignment> assignments;
    Activity context;
    int layoutId;
    private LayoutInflater inflater;
    private TextView mMockTextView;

    public AssignmentAdapter(Activity context, int resource, List<Assignment> assignments) {
        super(context, resource, assignments);
        this.layoutId = resource;
        this.context = context;
        this.assignments = assignments;
        inflater = context.getWindow().getLayoutInflater();
    }

    @Override
    public int getCount() {
        int count = assignments.size();
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        return assignments;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            Assignment current = assignments.get(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layoutId, null);
            TextView title = (TextView) v.findViewById(R.id.assignment_name);
            title.setText(current.getName());
            TextView grade = (TextView) v.findViewById(R.id.assignment_grade);
            grade.setText(Float.toString(current.getGrade()) + '%');

            TextView date = (TextView) v.findViewById(R.id.assignment_date);
            date.setText(current.getDateModified());

            TextView type = (TextView) v.findViewById(R.id.assignment_type);
            type.setText(current.getType());

            TextView weight = (TextView) v.findViewById(R.id.assignment_weight);
            weight.setText("Weight: " + current.getWeight() + "%");

            SeekBar gradeSeekBar = (SeekBar) v.findViewById(R.id.assignment_grade_seekBar);
            gradeSeekBar.setProgress(Math.round(current.getGrade()));
            final TextView newGrade = (TextView) v.findViewById(R.id.assignment_grade);
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
        return v;
    }

}

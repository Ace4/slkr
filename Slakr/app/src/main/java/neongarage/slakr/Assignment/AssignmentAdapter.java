package neongarage.slakr.Assignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import neongarage.slakr.AddGradesActivity;
import neongarage.slakr.Course.Course;
import neongarage.slakr.Course.CourseSummaryActivity;
import neongarage.slakr.R;

/**
 * Created by Aaron on 1/27/2015.
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder>{

    List<Assignment> assignments;
    Context context;
    int layoutId;
    private List<Assignment> mDataset;
    private LayoutInflater inflater;
    private TextView mMockTextView;


// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView mTextView;
    TextView title;
    TextView grade;
    TextView date;
    TextView type;
    TextView weight;
    TextView newGrade;
    SeekBar gradeSeekBar;
    Switch assignmentSwitch;

    public ViewHolder(View v) {
        super(v);
        title = (TextView) v.findViewById(R.id.assignment_name);
        grade = (TextView) v.findViewById(R.id.assignment_grade);
        date = (TextView) v.findViewById(R.id.assignment_date);
        type = (TextView) v.findViewById(R.id.assignment_type);
        weight = (TextView) v.findViewById(R.id.assignment_weight);
        assignmentSwitch = (Switch) v.findViewById(R.id.completed_switch);
        gradeSeekBar = (SeekBar) v.findViewById(R.id.assignment_grade_seekBar);
        newGrade = (TextView) v.findViewById(R.id.assignment_grade);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public AssignmentAdapter(List<Assignment> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.setPadding(10,10,10,0);
        v.setElevation(10);
        v.animate();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mTextView.setText(mDataset[position])
        holder.title.setText(mDataset.get(position).getName());
        holder.grade.setText(Float.toString(mDataset.get(position).getGrade()) + '%');
        holder.date.setText(mDataset.get(position).getDateModified());
        holder.type.setText(mDataset.get(position).getType());
        holder.weight.setText("Weight: " + mDataset.get(position).getWeight() + "%");
        holder.assignmentSwitch.setChecked(mDataset.get(position).getCompleted());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FragmentList", "Item clicked: " + position);
                TextView assignment = (TextView) v.findViewById(R.id.assignment_name);
                Intent intent = new Intent(context, AddGradesActivity.class);
                intent.putExtra("click_id", position);
                //TODO: Figure out how to make this a start activity for intent
                context.startActivity(intent);

/*                Log.i("FragmentList", "Item clicked: " + position);
                Intent detailIntent = new Intent(context, CourseSummaryActivity.class);
                detailIntent.putExtra("course", mDataset.get(position));
                context.startActivity(detailIntent);*/
            }
        });
        final Assignment temp = mDataset.get(position);
        holder.assignmentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                temp.setCompleted(isChecked);
            }

        });
        mDataset.get(position).setCompleted(temp.getCompleted());

        holder.gradeSeekBar.setProgress(Math.round(mDataset.get(position).getGrade()));
        final TextView finalGrade = holder.newGrade;
        holder.gradeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                finalGrade.setText(progress + "%");
            }
        });

    }

                // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
 /*   List<Assignment> assignments;
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

            v = inflater.inflate(layoutId, parent, false);

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

            Switch assignmentSwitch = (Switch) v.findViewById(R.id.completed_switch);
            assignmentSwitch.setChecked(current.getCompleted());
            final Assignment temp = current;
            assignmentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                    temp.setCompleted(isChecked);
            }
        });
            current.setCompleted(temp.getCompleted());
            SeekBar gradeSeekBar = (SeekBar) v.findViewById(R.id.assignment_grade_seekBar);
            gradeSeekBar.setProgress(Math.round(current.getGrade()));
            final TextView newGrade = (TextView) v.findViewById(R.id.assignment_grade);
            gradeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                    progress = progressValue;
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
*/
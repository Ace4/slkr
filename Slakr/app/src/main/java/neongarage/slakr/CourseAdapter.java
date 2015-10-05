package neongarage.slakr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 1/27/2015.
 * Refactored to recycler view 10/5/2015
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>
{
    private List<Course> mDataset;
    Context context;
    int layoutId;
    private LayoutInflater inflater;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        private TextView title;
        private TextView dept;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.courseTitle);
            dept = (TextView) v.findViewById(R.id.courseDept);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CourseAdapter(List<Course> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
      context = parent.getContext();
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_card, parent, false);
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
        holder.dept.setText(mDataset.get(position).getDept());
        holder.title.setText(mDataset.get(position).getDept() + mDataset.get(position).getNum());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FragmentList", "Item clicked: " + position);
                Intent detailIntent = new Intent(context, CourseSummaryActivity.class);
                detailIntent.putExtra("course", mDataset.get(position));
                context.startActivity(detailIntent);
            }
         });
    }

                 // Return the size of your dataset (invoked by the layout manager)
        @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

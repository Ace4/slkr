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
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 1/27/2015.
 */
public class AssignmentAdapter extends ArrayAdapter {
    List<String> items;
    List<String> grades;
    List<String> dates;
    List<String> types;
    Activity context;
    int layoutId;
    private LayoutInflater inflater;
    private TextView mMockTextView;

    public AssignmentAdapter(Activity context, int resource, List<String> items, List<String> grades, List<String> dates, List<String> types) {
        super(context, resource, items);
        this.layoutId = resource;
        this.context = context;
        this.items = items;
        this.grades = grades;
        this.dates = dates;
        this.types = types;
        inflater = context.getWindow().getLayoutInflater();
    }

    @Override
    public int getCount() {
        int count = items.size();
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        return items;
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
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layoutId, null);
            TextView title = (TextView) v.findViewById(R.id.assignment_name);
            title.setText(items.get(position));
            TextView grade = (TextView) v.findViewById(R.id.assignment_grade);
            grade.setText(grades.get(position));
            TextView date = (TextView) v.findViewById(R.id.assignment_date);
            date.setText(dates.get(position));
            TextView type = (TextView) v.findViewById(R.id.assignment_type);
            type.setText(types.get(position));
        }
        return v;
    }

}

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
public class CourseAdapter extends ArrayAdapter {
    List<String> items;
    Activity context;
    int layoutId;
    private LayoutInflater inflater;
    private TextView mMockTextView;

    public CourseAdapter(Activity context, int resource, List<String> items) {
        super(context, resource, items);
        this.layoutId = resource;
        this.context = context;
        this.items = items;
        inflater = context.getWindow().getLayoutInflater();
    }

    @Override
    public int getCount() {
        int count = items.size();
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
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
            TextView title = (TextView) v.findViewById(R.id.courseTitle);
            title.setText(items.get(position));
            String phrase = items.get(position);
            String delims = "[ ]+";
            String[] token = phrase.split(delims);
            TextView dept = (TextView) v.findViewById(R.id.courseDept);
            dept.setText(token[0]);
        }
        return v;
    }

}

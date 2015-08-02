package neongarage.slakr;

/**
 * Created by Aaron on 1/22/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddAssignmentFragment extends Fragment {
    private View view;
    private TextView weightTextView;
    private SeekBar weight;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_assignment, container, false);
        setSpinnerContent(view);
        setCompletedContent(view);

/*        weightTextView = (TextView) view.findViewById(R.id.WeighTextView);
        weight = (SeekBar) view.findViewById(R.id.assignmentWeightSeekBar);
        // Initialize the textview with '0'.
        weightTextView.setText("Weight: " + weight.getProgress() + "/" + weight.getMax());

        weight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }@Override
            public void onStartTrackingTouch(SeekBar seekBar) {
             }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                weightTextView.setText("Weight: " + progress + "/" + seekBar.getMax());
            }
        }); */
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setSpinnerContent(View view){
        Spinner spinner = (Spinner) view.findViewById(R.id.assignmentSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.assignment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    private void setCompletedContent(View view){
        Spinner spinner = (Spinner) view.findViewById(R.id.assignmentCompleted);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.completed_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
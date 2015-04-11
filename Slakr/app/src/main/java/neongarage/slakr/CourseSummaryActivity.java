package neongarage.slakr;

/**
 * Created by Aaron on 4/10/2015.
 */
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseSummaryActivity extends ActionBarActivity {

    private MySQLiteHelper db;
    private Course mCourse;
    private List<Assignment> mAssignments;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_summary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new MySQLiteHelper(this);
        mCourse = getIntent().getParcelableExtra("course");
        mAssignments = db.getAssignmentsForCourse(mCourse);

        Button pie = (Button) findViewById(R.id.pie_chart);
        Button bar = (Button) findViewById(R.id.bar_chart);
        Button line = (Button) findViewById(R.id.line_graph);
        Button course_list = (Button) findViewById(R.id.course_list);
        pie.getBackground().setColorFilter(0xFFff3d00, PorterDuff.Mode.MULTIPLY);
        pie.setTextColor(Color.WHITE);
        bar.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        bar.setTextColor(Color.WHITE);
        line.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        line.setTextColor(Color.WHITE);
        course_list.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        course_list.setTextColor(Color.WHITE);

        addChart();
        setCurrentGrade();
    }


    public void  setCurrentGrade() {
        float weight;
        double total_weight = 0;
        float grade;
        double total_grade = 0;
        float final_grade = 0;
        for (int i = 0; i < mAssignments.size(); i++) {
            weight = mAssignments.get(i).getWeight();
            grade = mAssignments.get(i).getGrade();
            total_weight = weight * .01;
            total_grade =  grade * .01;
            final_grade += total_grade * total_weight;
        }

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        final_grade = final_grade * 100;
        TextView current_grade = (TextView) findViewById(R.id.current_grade);
        current_grade.setText("Current grade: " + Float.valueOf(twoDForm.format(final_grade)));

    }
    public void course_list_activity(View view) {
        Intent intent = new Intent(this, AssignmentListActivity.class);
        Course course = getIntent().getParcelableExtra("course");
        intent.putExtra("course", course);
        startActivity(intent);
    }
    private void addChart(){
        SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
        renderer.setColor(Color.CYAN);
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
        XYMultipleSeriesDataset xyMultipleSeriesDataset = new XYMultipleSeriesDataset();

        // Pie Chart Section Value
        List<Float> distribution = new ArrayList<Float>();
        for(int i=0; i < mAssignments.size(); i++){
            distribution.add(mAssignments.get(i).getWeight());
        }

        int[] colors = { Color.rgb(228,26,28), Color.rgb(55,126,184), Color.rgb(77,175,74), Color.rgb(152,78,163), Color.rgb(255,127,0),
                Color.rgb(255,255,51), Color.rgb( 166,86,40)  };


        SimpleSeriesRenderer seriesRenderer;
        for(int i = 0 ;i<distribution.size();i++){
            seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i%7]);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
            xySeriesRenderer.setColor(colors[i%7]);
        }

        // Pie Chart Section Names
        List<String> code = new ArrayList<>();
        for(int i = 0 ;i<mAssignments.size();i++){
            code.add(mAssignments.get(i).getName() + ' ' + mAssignments.get(i).getWeight()+'%');
        }

        CategorySeries distributionSeries = new CategorySeries(" Course Distribution");

        for(int i=0 ;i < distribution.size();i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code.get(i), distribution.get(i));
        }


        defaultRenderer.setLabelsTextSize(30);
        defaultRenderer.setShowAxes(true);
        defaultRenderer.setShowLegend(false);
        defaultRenderer.setLegendTextSize(40);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setPanEnabled(false);
        GraphicalView mChartView = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mChartView.setLayoutParams(params);
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart_layout);
        layout.addView(mChartView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
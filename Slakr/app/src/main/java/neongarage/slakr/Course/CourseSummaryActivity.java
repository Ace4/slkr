package neongarage.slakr.Course;

/**
 * Created by Aaron on 4/10/2015.
 */
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import neongarage.slakr.Assignment.Assignment;
import neongarage.slakr.Assignment.AssignmentListActivity;
import neongarage.slakr.MySQLiteHelper;
import neongarage.slakr.R;

public class CourseSummaryActivity extends ActionBarActivity {

    private MySQLiteHelper db;
    private Course mCourse;
    private List<Assignment> mAssignments;
    private  GraphicalView mChartView;
    Button pie;
    Button bar;
    Button line ;
    Button course_list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_summary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new MySQLiteHelper(this);
        mCourse = getIntent().getParcelableExtra("course");
        mAssignments = db.getAssignmentsForCourse(mCourse);

        pie = (Button) findViewById(R.id.pie_chart);
        bar = (Button) findViewById(R.id.bar_chart);
        line = (Button) findViewById(R.id.line_graph);
        course_list = (Button) findViewById(R.id.course_list);
        pie.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        pie.setTextColor(Color.WHITE);
        bar.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        bar.setTextColor(Color.WHITE);
        line.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        line.setTextColor(Color.WHITE);
        course_list.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        course_list.setTextColor(Color.WHITE);

        SeekBar gradeSeekBar = (SeekBar) findViewById(R.id.desired_grade_seekbar);
        final TextView desiredGrade = (TextView) findViewById(R.id.desired_grade);
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

                desiredGrade.setText("Desired Grade: " +progress +'%');
            }
        });
        setCurrentGrade();
    }


    public void  setCurrentGrade() {
        float weight;
        double total_weight = 0;
        float grade;
        double total_grade = 0;
        double final_grade = 0;
        for (int i = 0; i < mAssignments.size(); i++) {
            total_weight += mAssignments.get(i).getWeight() *.01;
        }


        for (int i = 0; i < mAssignments.size(); i++) {
            grade = mAssignments.get(i).getGrade();
            weight = mAssignments.get(i).getWeight();
            final_grade += (grade * .01) * (weight *.01) / total_weight;

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

    public void addBarChart(View view) {

        pie.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        bar.getBackground().setColorFilter(0xFFff3d00, PorterDuff.Mode.MULTIPLY);
        line.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);

        List<String> names = new ArrayList<String>();
        // Creating an XYSeries for Income
        XYSeries earnedSeries = new XYSeries("Earned");
        XYSeries totalSeries = new XYSeries("Total");
        // Adding data to Income and Expense Series
        float maxWeight = 0;
         for (int i = 0; i < mAssignments.size(); i++) {
            earnedSeries.add(i, (mAssignments.get(i).getGrade() * mAssignments.get(i).getWeight() * .01));
            totalSeries.add(i, mAssignments.get(i).getWeight());
            if(mAssignments.get(i).getWeight() > maxWeight){
            maxWeight = mAssignments.get(i).getWeight();
            }
         }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(earnedSeries);
        dataset.addSeries(totalSeries);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer earnedRenderer = new XYSeriesRenderer();
        earnedRenderer.setColor(Color.rgb(228,26,28));
        earnedRenderer.setFillPoints(true);
        earnedRenderer.setLineWidth(2);
        earnedRenderer.setDisplayChartValues(true);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setXLabels(0);
        for(int i = 0; i<mAssignments.size(); i++){
            multiRenderer.addXTextLabel(i, mAssignments.get(i).getName());

        }

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer totalRenderer = new XYSeriesRenderer();
        totalRenderer.setColor(Color.rgb(55,126,184));
        totalRenderer.setFillPoints(true);
        totalRenderer.setLineWidth(2);
        totalRenderer.setDisplayChartValues(true);


        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(earnedRenderer);
        multiRenderer.addSeriesRenderer(totalRenderer);
        multiRenderer.setBarSpacing(.2);
        multiRenderer.setMarginsColor(Color.WHITE);
        multiRenderer.setYAxisMin(0);
        multiRenderer.setYAxisMax(maxWeight);
        multiRenderer.setXAxisMin(-1);
        multiRenderer.setLabelsTextSize(20);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        if(mChartView != null){
            layout.removeView(mChartView);
        }

        mChartView = ChartFactory.getBarChartView(this, dataset, multiRenderer, BarChart.Type.DEFAULT);
        mChartView.setLayoutParams(params);

        layout.addView(mChartView);
    }

    public void addLineChart(View view) {

        pie.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        bar.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        line.getBackground().setColorFilter(0xFFff3d00, PorterDuff.Mode.MULTIPLY);

        List<String> names = new ArrayList<String>();
        // Creating an XYSeries for Income
        XYSeries earnedSeries = new XYSeries("Earned");
        XYSeries passingLine = new XYSeries("70 %");
        // Adding data to Income and Expense Series
        for (int i = 0; i < mAssignments.size(); i++) {
            earnedSeries.add(i, (mAssignments.get(i).getGrade()));
            passingLine.add(i, 70);
        }


        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(earnedSeries);
        dataset.addSeries(passingLine);
        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.rgb(55,126,184));
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(5);
        incomeRenderer.setDisplayChartValues(true);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer passingRenderer = new XYSeriesRenderer();
        passingRenderer.setColor(Color.rgb(228,26,28));
        passingRenderer.setFillPoints(true);
        passingRenderer.setLineWidth(5);
        passingRenderer.setDisplayChartValues(true);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);

        for(int i = 0; i<mAssignments.size(); i++){
            multiRenderer.addXTextLabel(i, mAssignments.get(i).getName());

        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(passingRenderer);
        multiRenderer.setBarSpacing(.2);
        multiRenderer.setMarginsColor(Color.WHITE);
        multiRenderer.setLegendTextSize(20);
        multiRenderer.setYAxisMin(0);
        multiRenderer.setYAxisMax(100);

        // Creating an intent to plot bar chart using dataset and multipleRenderer

        LinearLayout layout = (LinearLayout) findViewById(R.id.chart_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        if(mChartView != null){
            layout.removeView(mChartView);
        }
        mChartView = ChartFactory.getLineChartView(this, dataset, multiRenderer);
        mChartView.setLayoutParams(params);
        layout.addView(mChartView);

    }

    public void addPieChart(View view){


        pie.getBackground().setColorFilter(0xFFff3d00, PorterDuff.Mode.MULTIPLY);
        bar.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        line.getBackground().setColorFilter(0xFF37474f, PorterDuff.Mode.MULTIPLY);
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        // Pie Chart Section Value
        List<Double> distribution = new ArrayList<Double>();
        double weighted_grade;
        double points_missed = 0;
        double total_weight = 0;
        for(int i=0; i < mAssignments.size(); i++){
            weighted_grade =  mAssignments.get(i).getWeight() * mAssignments.get(i).getGrade() *.01;
            total_weight  +=  mAssignments.get(i).getWeight();
            points_missed += mAssignments.get(i).getWeight() - weighted_grade;
            distribution.add(weighted_grade);
        }
        distribution.add(points_missed);

        int[] colors = { Color.rgb(228,26,28), Color.rgb(55,126,184), Color.rgb(77,175,74), Color.rgb(152,78,163), Color.rgb(255,127,0),
                Color.rgb(255,255,51), Color.rgb( 166,86,40)  };



        for(int i = 0 ;i<distribution.size()-1;i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i%7]);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
        seriesRenderer.setColor(Color.GRAY);
        // Adding a renderer for a slice
        defaultRenderer.addSeriesRenderer(seriesRenderer);


        // Pie Chart Section Names
        List<String> code = new ArrayList<>();
        double total_grade_earned = 0;
        for(int i = 0 ;i<mAssignments.size();i++){
            double temp = mAssignments.get(i).getWeight() * mAssignments.get(i).getGrade() *.01;
            code.add(mAssignments.get(i).getType() + ' ' + temp + '/' + mAssignments.get(i).getWeight());
   //         code.add(mAssignments.get(i).getName() + ' ' + mAssignments.get(i).getWeight());
        }
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        code.add("Points missed: " +  Double.valueOf(twoDForm.format(points_missed)) + '/' + total_weight );

        CategorySeries distributionSeries = new CategorySeries("Course Distribution");

        for(int i=0 ;i < mAssignments.size()+1;i++){
            // Adding a slice with its values and name to the Pie Chart
                distributionSeries.add(code.get(i), distribution.get(i));

        }

        defaultRenderer.setLabelsTextSize(18);
        defaultRenderer.setShowAxes(true);
        defaultRenderer.setFitLegend(true);
        defaultRenderer.setShowLegend(true);
        defaultRenderer.setLegendTextSize(18);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setPanEnabled(false);


        LinearLayout layout = (LinearLayout) findViewById(R.id.chart_layout);
        if(mChartView != null) {
            layout.removeView(mChartView);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mChartView = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);
        mChartView.setLayoutParams(params);
        layout.addView(mChartView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
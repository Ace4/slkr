package neongarage.slakr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sean on 4/8/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    // Table names
    private static final String TABLE_ASSIGNMENTS = "assignments";
    private static final String TABLE_COURSES = "courses";

    // Assignment column names
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DATE = "dateModified";
    private static final String COLUMN_GRADE = "grade";
    private static final String COLUMN_WEIGHT = "weight";

    // Course column names
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_NUMBER = "number";

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SLKR_DB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create courses table
        String CREATE_COURSES_TABLE = "CREATE TABLE courses ( " + "department TEXT, "
                + "number TEXT, " + "PRIMARY KEY (department, number))";

        // create course table
        db.execSQL(CREATE_COURSES_TABLE);
        Log.d("SQLite: onCreate", "Created courses table");

        // SQL statement to create assignments table
        String CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE assignments ( " + "name TEXT, "
                + "type TEXT, " + "dateModified TEXT, " + "grade REAL, " + "weight REAL, " +
                "department TEXT, " + "number TEXT, " +
                "FOREIGN KEY(department) REFERENCES courses(department), " +
                "FOREIGN KEY(number) REFERENCES courses(number))";

        // create assignments table
        db.execSQL(CREATE_ASSIGNMENTS_TABLE);
        Log.d("SQLite: onCreate", "Created assignments table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older courses table if exists
        db.execSQL("DROP TABLE IF EXISTS courses");

        // Drop older assignments table if exists
        db.execSQL("DROP TABLE IF EXISTS assignments");

        // create fresh tables
        this.onCreate(db);
    }

    public void addCourse(Course c) {
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_DEPARTMENT, c.getDept());
        values.put(COLUMN_NUMBER, c.getNum());

        // insert
        db.insert(TABLE_COURSES, null, values);
        Log.d("SQLite: addCourse", c.getDept() + c.getNum());

        db.close();
    }

    public void addAssignment(Assignment a, String department, String courseNumber) {
        // for logging
        Log.d("addAssignment", a.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, a.getName());
        values.put(COLUMN_TYPE, a.getType());
        values.put(COLUMN_DATE, a.getDateModified());
        values.put(COLUMN_GRADE, a.getGrade());
        values.put(COLUMN_WEIGHT, a.getWeight());
        values.put(COLUMN_DEPARTMENT, department);
        values.put(COLUMN_NUMBER, courseNumber);

        // insert
        db.insert(TABLE_ASSIGNMENTS, null, values);
        Log.d("SQLite: addAssignment", a.getName());

        db.close();
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new LinkedList<Course>();

        // build the query
        String query = "SELECT * FROM " + TABLE_COURSES;

        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // go through each row and add to list
        Course c = null;
        if (cursor.moveToFirst()) {
            do {
                c = new Course();
                c.setDept(cursor.getString(0));
                c.setNum(cursor.getString(1));
                courses.add(c);
            } while (cursor.moveToNext());

        }
        return courses;
    }

    public List<Assignment> getAssignmentsForCourse(Course c) {
        List<Assignment> assignments = new LinkedList<Assignment>();

        // build the query
        String query = "SELECT * FROM " + TABLE_ASSIGNMENTS + " WHERE " + COLUMN_NUMBER + " = '" + c.getNum() +
                "' AND " + COLUMN_DEPARTMENT + " = '" + c.getDept() + "'";

        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // go through each row and add to list
        Assignment a = null;
        if (cursor.moveToFirst()) {
            do {
                a = new Assignment();
                a.setName(cursor.getString(0));
                a.setType(cursor.getString(1));
                a.setDateModified(cursor.getString(2));
                a.setGrade(Float.parseFloat(cursor.getString(3)));
                a.setWeight(Float.parseFloat(cursor.getString(4)));

                assignments.add(a);
            } while (cursor.moveToNext());

        }
        return assignments;
    }

    // delete single course
    public void deleteCourse(Course c) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete course
        db.delete(TABLE_COURSES, COLUMN_DEPARTMENT + "=" + c.getDept() + " and " + COLUMN_NUMBER + "=" + c.getNum(), null);
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        onUpgrade(db, 1, 1);
    }
}
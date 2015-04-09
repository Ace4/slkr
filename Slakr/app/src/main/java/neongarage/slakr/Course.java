package neongarage.slakr;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aaron on 4/8/2015.
 */
public class Course implements Parcelable {
    String department;
    String number;

    public Course() {};

    public Course(String department, String number) {
        this.department = department;
        this.number = number;
    }

    public String getDept() {
        return department;
    }

    public String getNum() {
        return number;
    }

    public void setDept(String department) {
        this.department = department;
    }

    public void setNum(String number) {
        this.number = number;
    }

    protected Course(Parcel in) {
        department = in.readString();
        number = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(department);
        dest.writeString(number);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
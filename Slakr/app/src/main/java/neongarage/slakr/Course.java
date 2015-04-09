package neongarage.slakr;

/**
 * Created by Aaron on 4/8/2015.
 */
public class Course {
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
}

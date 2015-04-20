package neongarage.slakr;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aaron on 4/8/2015.
 */
public class Assignment implements Parcelable {
    String name;
    String type;
    String dateModified;
    float grade;
    float weight;
    int completed; // 1 for completed, 0 for not completed

    public Assignment() {};

    /** Assignment(String name, String type, float grade, float weight, String dateModified) **/
    public Assignment(String name, String type, float grade, float weight, String dateModified, int completed){
        this.name = name;
        this.grade = grade;
        this.weight = weight;
        this.type = type;

        this.dateModified = dateModified;
        this.completed = completed;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public void setDateModified(String dateModified){
        this.dateModified = dateModified;
    }

    public String getDateModified(){
        return dateModified;
    }

    public void setGrade(float grade){
        this.grade = grade;
    }

    public float getGrade(){
        return grade;
    }

    public void setWeight(float weight){
        this.weight = weight;
    }

    public float getWeight(){
        return weight;
    }

    public void setCompleted(int completed) {this.completed = completed;}

    public int getCompleted() { return completed; }




    protected Assignment(Parcel in) {
        name = in.readString();
        type = in.readString();
        dateModified = in.readString();
        grade = in.readFloat();
        weight = in.readFloat();
        completed = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(dateModified);
        dest.writeFloat(grade);
        dest.writeFloat(weight);
        dest.writeInt(completed);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Assignment> CREATOR = new Parcelable.Creator<Assignment>() {
        @Override
        public Assignment createFromParcel(Parcel in) {
            return new Assignment(in);
        }

        @Override
        public Assignment[] newArray(int size) {
            return new Assignment[size];
        }
    };
}
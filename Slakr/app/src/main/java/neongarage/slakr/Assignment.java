package neongarage.slakr;

/**
 * Created by Aaron on 4/8/2015.
 */
public class Assignment {
    String name;
    String type;
    String dateModified;
    float grade;
    float weight;


    /** Assignment(String name, String type, float grade, float weight, String dateModified) **/
    public Assignment(String name, String type, float grade, float weight, String dateModified){
        this.name = name;
        this.grade = grade;
        this.weight = weight;
        this.type = type;

        this.dateModified = dateModified;
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

}


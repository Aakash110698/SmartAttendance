package com.hack4throne.smartattendance;

/**
 * Created by malavan on 27/08/17.
 */

public class StaffSubject {

    String classid,subjectname,periodgone,strength;


    public StaffSubject() {
    }

    public StaffSubject(String classid, String subjectname, String periodgone, String strength) {

        this.classid = classid;
        this.subjectname = subjectname;
        this.periodgone = periodgone;
        this.strength = strength;
    }

    public String getClassid() {

        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getPeriodgone() {
        return periodgone;
    }

    public void setPeriodgone(String periodgone) {
        this.periodgone = periodgone;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
}

package com.hack4throne.smartattendance;

/**
 * Created by malavan on 26/08/17.
 */

public class ClassClass {
String classid,totalstrength;


    public ClassClass() {
    }

    public ClassClass(String classid, String totalstrength) {


        this.classid = classid;
        this.totalstrength = totalstrength;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTotalstrength() {
        return totalstrength;
    }

    public void setTotalstrength(String totalstrength) {
        this.totalstrength = totalstrength;
    }
}

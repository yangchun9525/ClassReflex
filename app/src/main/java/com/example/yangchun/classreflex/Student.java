package com.example.yangchun.classreflex;

import android.util.Log;

/**
 * Created by yangchun on 2016-12-12.
 */

public class Student {
    private final String name;
    private int grade = 1;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    private void goToSchool(String time) {
        Log.i("yc-goToSchool",name + " go to school!" +",time:"+ time);
    }

    private void goToSchool() {
        Log.i("yc-goToSchool",name + " go to school!");
    }
}

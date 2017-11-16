package com.maple27.fzuyibao.presenter.util;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maple27 on 2017/11/1.
 */

public class ActivityController {
    public static List<AppCompatActivity> activityList = new ArrayList<>();
    public static void addActivity(AppCompatActivity appCompatActivity){
        activityList.add(appCompatActivity);
    }
    public static void removeActivity(AppCompatActivity appCompatActivity){
        activityList.remove(appCompatActivity);
    }
    public static void finishAll(){
        for(AppCompatActivity activity : activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
package com.bootdo.eight.domain;

import java.util.List;

/**
 * Created by god on 2017/8/5.
 */
public class Dayunliunian {
    private int year;
    private String tgdz;
    private String shishen;
    private int age;
    private List<Dayunliunian> subData;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTgdz() {
        return tgdz;
    }

    public void setTgdz(String tgdz) {
        this.tgdz = tgdz;
    }

    public String getShishen() {
        return shishen;
    }

    public void setShishen(String shishen) {
        this.shishen = shishen;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Dayunliunian> getSubData() {
        return subData;
    }

    public void setSubData(List<Dayunliunian> subData) {
        this.subData = subData;
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        strb.append("年份:").append(year).append(",年龄:").append(age).append(",天干地支:").append(tgdz).append(",十神:").append(shishen);
//        if (subData != null){
//            strb.append("子数据:");
//            strb.append(subData);
//        }
        strb.append("|");
        return strb.toString();
    }
}

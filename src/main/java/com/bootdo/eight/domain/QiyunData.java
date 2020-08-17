package com.bootdo.eight.domain;

import java.time.LocalDateTime;

/**
 * Created by god on 2020/1/6.
 */
public class QiyunData {
    private LocalDateTime latestSolarTerm;
    private int srcDayNums;
    private int srcHourNums;
    private int srcMinutesNums;
    private int yearNums;
    private int monthNums;
    private int dayNums;
    private int hourNums;
    private LocalDateTime qiyunDateTime;

    public LocalDateTime getLatestSolarTerm() {
        return latestSolarTerm;
    }

    public void setLatestSolarTerm(LocalDateTime latestSolarTerm) {
        this.latestSolarTerm = latestSolarTerm;
    }

    public int getSrcDayNums() {
        return srcDayNums;
    }

    public void setSrcDayNums(int srcDayNums) {
        this.srcDayNums = srcDayNums;
    }

    public int getSrcHourNums() {
        return srcHourNums;
    }

    public void setSrcHourNums(int srcHourNums) {
        this.srcHourNums = srcHourNums;
    }

    public int getSrcMinutesNums() {
        return srcMinutesNums;
    }

    public void setSrcMinutesNums(int srcMinutesNums) {
        this.srcMinutesNums = srcMinutesNums;
    }

    public int getYearNums() {
        return yearNums;
    }

    public void setYearNums(int yearNums) {
        this.yearNums = yearNums;
    }

    public int getMonthNums() {
        return monthNums;
    }

    public void setMonthNums(int monthNums) {
        this.monthNums = monthNums;
    }

    public int getDayNums() {
        return dayNums;
    }

    public void setDayNums(int dayNums) {
        this.dayNums = dayNums;
    }

    public int getHourNums() {
        return hourNums;
    }

    public void setHourNums(int hourNums) {
        this.hourNums = hourNums;
    }

    public LocalDateTime getQiyunDateTime() {
        return qiyunDateTime;
    }

    public void setQiyunDateTime(LocalDateTime qiyunDateTime) {
        this.qiyunDateTime = qiyunDateTime;
    }
}

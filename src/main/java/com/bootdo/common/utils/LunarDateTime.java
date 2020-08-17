package com.bootdo.common.utils;

import com.alibaba.fastjson.JSON;

/**
 * Created by god on 2017/10/31.
 */
public class LunarDateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public LunarDateTime(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public LunarDate toLunarDate() {
        LunarDate lunarDate = new LunarDate(this.year, this.month, this.day);
        return lunarDate;
    }

    public static class LunarDate {
        private int year;
        private int month;
        private int day;

        private LunarDate(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }

    public static void main(String[] args) {
        LunarDateTime lunarDateTime = new LunarDateTime(2019,1,23,12,1,1);
        System.out.println(JSON.toJSONString(lunarDateTime));
    }
}

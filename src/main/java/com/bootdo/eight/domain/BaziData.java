package com.bootdo.eight.domain;

import com.alibaba.fastjson.JSON;
import com.bootdo.common.utils.LunarDateTime;

import java.util.List;

/**
 * Created by god on 2017/8/8.
 */
public class BaziData {
    private String shishen;
    private String tgdz;
    private String canggan;
    private String cangshishen;
    private String wuxing;
    private QiyunData qiyunData;
    private LunarDateTime lunarDateTime;
    private List<Dayunliunian> dayunliunian;
    private String minggua;
    private String wuxingCount;

    public String getShishen() {
        return shishen;
    }

    public void setShishen(String shishen) {
        this.shishen = shishen;
    }

    public String getTgdz() {
        return tgdz;
    }

    public void setTgdz(String tgdz) {
        this.tgdz = tgdz;
    }

    public String getCanggan() {
        return canggan;
    }

    public void setCanggan(String canggan) {
        this.canggan = canggan;
    }

    public String getCangshishen() {
        return cangshishen;
    }

    public void setCangshishen(String cangshishen) {
        this.cangshishen = cangshishen;
    }

    public String getWuxing() {
        return wuxing;
    }

    public void setWuxing(String wuxing) {
        this.wuxing = wuxing;
    }

    public QiyunData getQiyunData() {
        return qiyunData;
    }

    public void setQiyunData(QiyunData qiyunData) {
        this.qiyunData = qiyunData;
    }

    public List<Dayunliunian> getDayunliunian() {
        return dayunliunian;
    }

    public void setDayunliunian(List<Dayunliunian> dayunliunian) {
        this.dayunliunian = dayunliunian;
    }

    public String getMinggua() {
        return minggua;
    }

    public void setMinggua(String minggua) {
        this.minggua = minggua;
    }

    public String getWuxingCount() {
        return wuxingCount;
    }

    public void setWuxingCount(String wuxingCount) {
        this.wuxingCount = wuxingCount;
    }

    public String getLunarDateTime() {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d", lunarDateTime.getYear(), lunarDateTime.getMonth(), lunarDateTime.getDay(), lunarDateTime.getHour(), lunarDateTime.getMinute(), lunarDateTime.getSecond());
    }

    public void setLunarDateTime(LunarDateTime lunarDateTime) {
        this.lunarDateTime = lunarDateTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static void main(String[] args) {
        BaziData baziData = new BaziData();
        baziData.setLunarDateTime(new LunarDateTime(1980,4,19,17,47,44));
        System.out.println(baziData);
    }
}

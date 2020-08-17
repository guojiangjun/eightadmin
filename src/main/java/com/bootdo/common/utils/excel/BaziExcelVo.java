package com.bootdo.common.utils.excel;

import com.alibaba.fastjson.JSONObject;

public class BaziExcelVo {
    //
    @ExcelColumn(value = "No.", col = 2)
    private Integer No;
    @ExcelColumn(value = "八字UID", col = 3)
    private String baziid;
    @ExcelColumn(value = "账户", col = 1)
    private Integer userid;
    //
    @ExcelColumn(value = "命主", col = 4)
    private String baziname;
    //
    @ExcelColumn(value = "性别", col = 5)

    private String male;
    //
    @ExcelColumn(value = "原始输入生辰 ", col = 7)
    private String solardatetime;
    //
    @ExcelColumn(value = "农历生辰 ", col = 11)
    private String lunardatetime;
    @ExcelColumn(value = "备注 ", col = 15)
    private String remark;

    private String baziConfig;

    @ExcelColumn(value = "历法 ", col = 6)
    private String type;
    @ExcelColumn(value = "公历生辰 ", col = 10)
    private String dateTime;
    @ExcelColumn(value = "出生地 ", col = 12)
    private String birthCity;
    @ExcelColumn(value = "校正经纬度 ", col = 16)
    private String zoneJingdu;
    @ExcelColumn(value = "真太阳时", col = 8)
    private String isTaiyang;
    @ExcelColumn(value = "平太阳时 ", col = 14)
    private String normalTaiyang;
    @ExcelColumn(value = "真太阳时  ", col = 13)
    private String realTaiyang;
    @ExcelColumn(value = "经度 ", col = 17)
    private String jingdu;
    @ExcelColumn(value = "纬度 ", col = 18)
    private String weidu;
    @ExcelColumn(value = "夏令时", col = 9)
    private String isXialingshi;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getBaziname() {
        return baziname;
    }

    public void setBaziname(String baziname) {
        this.baziname = baziname;
    }

    public String getMale() {
        return male.equals("0")?"男":"女";
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getSolardatetime() {
        return solardatetime;
    }

    public void setSolardatetime(String solardatetime) {
        this.solardatetime = solardatetime;
    }

    public String getLunardatetime() {
        return lunardatetime;
    }

    public void setLunardatetime(String lunardatetime) {
        this.lunardatetime = lunardatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBaziid() {
        return baziid;
    }

    public void setBaziId(String baziid) {
        this.baziid = baziid;
    }

    public String getBaziConfig() {
        return baziConfig;
    }

    public void setBaziConfig(String baziConfig) {
        this.baziConfig = baziConfig;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getZoneJingdu() {
        return zoneJingdu;
    }

    public void setZoneJingdu(String zoneJingdu) {
        this.zoneJingdu = zoneJingdu;
    }

    public String getTaiyang() {
        return isTaiyang;
    }

    public void setTaiyang(String taiyang) {
        isTaiyang = taiyang;
    }

    public String getNormalTaiyang() {
        return normalTaiyang;
    }

    public void setNormalTaiyang(String normalTaiyang) {
        this.normalTaiyang = normalTaiyang;
    }

    public String getRealTaiyang() {
        return realTaiyang;
    }

    public void setRealTaiyang(String realTaiyang) {
        this.realTaiyang = realTaiyang;
    }

    public String getJingdu() {
        return jingdu;
    }

    public void setJingdu(String jingdu) {
        this.jingdu = jingdu;
    }

    public String getWeidu() {
        return weidu;
    }

    public void setWeidu(String weidu) {
        this.weidu = weidu;
    }

    public String getXialingshi() {
        return isXialingshi;
    }

    public void setXialingshi(String xialingshi) {
        isXialingshi = xialingshi;
    }

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }
}

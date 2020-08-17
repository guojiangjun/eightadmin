package com.bootdo.common.utils;


import com.bootdo.eight.domain.BaziData;
import com.bootdo.eight.domain.Dayunliunian;
import com.bootdo.eight.domain.QiyunData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by god on 2017/8/5.
 */
public class BaziUtil {
    private static final String _DELIMITER = "_";
    //[日元][天干]
    private static final String[][] SHI_SHEN_DATA = new String[][]{
            {"比肩", "劫财", "食神", "伤官", "偏财", "正财", "七杀", "正官", "偏印", "正印"},
            {"劫财", "比肩", "伤官", "食神", "正财", "偏财", "正官", "七杀", "正印", "偏印"},
            {"偏印", "正印", "比肩", "劫财", "食神", "伤官", "偏财", "正财", "七杀", "正官"},
            {"正印", "偏印", "劫财", "比肩", "伤官", "食神", "正财", "偏财", "正官", "七杀"},
            {"七杀", "正官", "偏印", "正印", "比肩", "劫财", "食神", "伤官", "偏财", "正财"},
            {"正官", "七杀", "正印", "偏印", "劫财", "比肩", "伤官", "食神", "正财", "偏财"},
            {"偏财", "正财", "七杀", "正官", "偏印", "正印", "比肩", "劫财", "食神", "伤官"},
            {"正财", "偏财", "正官", "七杀", "正印", "偏印", "劫财", "比肩", "伤官", "食神"},
            {"食神", "伤官", "偏财", "正财", "七杀", "正官", "偏印", "正印", "比肩", "劫财"},
            {"伤官", "食神", "正财", "偏财", "正官", "七杀", "正印", "偏印", "劫财", "比肩"}
    };

    //    private static final String[] CANG_GAN_DATA = new String[]
//            {"癸", "己_癸_辛", "甲_丙_戊", "乙", "戊_乙_癸", "丙_庚_戊", "丁_己", "己_丁_乙", "庚_壬_戊", "辛", "戊_辛_丁", "壬_甲"};
    private static final String[] CANG_GAN_DATA = new String[]
            {"癸", "辛_己_癸", "丙_甲_戊", "乙", "乙_戊_癸", "戊_丙_庚", "己_丁", "乙_己_丁", "戊_庚_壬", "辛", "丁_戊_辛", "甲_壬"};
    private final static String[] minggua = new String[]{"东四命一白坎", "西四命二黑坤", "东四命三碧震", "东四命四绿巽", "西四命余数2坤卦,西四命余数8艮卦", "西四命六白乾", "西四命七赤兑", "西四命八白艮", "东四命九紫离"};
    private final static String[] wuxing = new String[]{"金", "木", "水", "火", "土"};
    private final static String[] tianganWuxing = new String[]{"木", "木", "火", "火", "土", "土", "金", "金", "水", "水"};
    private final static String[] dizhiWuxing = new String[]{"水", "土", "木", "木", "土", "火", "火", "土", "金", "金", "土", "水"};
    private final static String[] TGDZ_60 = new String[]{
            "甲子", "乙丑", "丙寅", "丁卯", "戊辰", "己巳", "庚午", "辛未", "壬申", "癸酉",
            "甲戌", "乙亥", "丙子", "丁丑", "戊寅", "己卯", "庚辰", "辛巳", "壬午", "癸未",
            "甲申", "乙酉", "丙戌", "丁亥", "戊子", "己丑", "庚寅", "辛卯", "壬辰", "癸巳",
            "甲午", "乙未", "丙申", "丁酉", "戊戌", "己亥", "庚子", "辛丑", "壬寅", "癸卯",
            "甲辰", "乙巳", "丙午", "丁未", "戊申", "己酉", "庚戌", "辛亥", "壬子", "癸丑",
            "甲寅", "乙卯", "丙辰", "丁巳", "戊午", "己未", "庚申", "辛酉", "壬戌", "癸亥"};

    private static final String SHI_SHEN_DAY = "日元";

    private static final Map<Integer, List<LocalDateTime>> solarTermMap = new HashMap<>();

    static {
        String classPath = BaziUtil.class.getResource("/").getPath();
        File file = new File(String.format("%sjieqi.txt", classPath.substring(0, classPath.length() - 8)));
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String data;
            int i = 0;
            while ((data = bufferedReader.readLine()) != null) {
                if (!data.trim().isEmpty()) {
                    i++;
                    if (i % 2 == 0) {
                        continue;
                    }
                    String[] firstSplit = data.split("#")[0].split("\\*");
                    solarTermMap.putIfAbsent(Integer.parseInt(firstSplit[0]), new ArrayList<>());
                    String[] secondSplit = firstSplit[1].split("_");
                    LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(secondSplit[0]),
                            Integer.parseInt(secondSplit[1]), Integer.parseInt(secondSplit[2])
                            , Integer.parseInt(secondSplit[3]), Integer.parseInt(secondSplit[4]));
                    solarTermMap.get(Integer.parseInt(firstSplit[0])).add(localDateTime);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
           // MainLogger.serverLog.error("read file error!", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static BaziData getBaziData(LocalDateTime solarDateTime, boolean male) {
        BaziData baziData = new BaziData();
        try {
            //天干地支
            LunarDateTime lunarDateTime = LunarCalendarUtil.solarToLunar(solarDateTime);
            baziData.setLunarDateTime(lunarDateTime);
            String yearTGName = LunarCalendarUtil.getYearTianGanName(lunarDateTime.getYear());
            String yearDZName = LunarCalendarUtil.getYearDiZhiName(lunarDateTime.getYear());
            String monthTGName = LunarCalendarUtil.getMonthTianGanName(lunarDateTime.getYear(), BaziUtil.getMonth(solarDateTime));
            String monthDZName = LunarCalendarUtil.getMonthDiZhiName(BaziUtil.getMonth(solarDateTime));
            String dayTGName = LunarCalendarUtil.getDayTianGanName(solarDateTime.getYear(), solarDateTime.getMonthValue(), solarDateTime.getDayOfMonth());
            String dayDZName = LunarCalendarUtil.getDayDiZhiName(solarDateTime.getYear(), solarDateTime.getMonthValue(), solarDateTime.getDayOfMonth());
            String hourTGName = LunarCalendarUtil.getHourTianGanName(solarDateTime.getYear(), solarDateTime.getMonthValue(), solarDateTime.getDayOfMonth()
                    , solarDateTime.getHour());
            String hourDZName = LunarCalendarUtil.getHourDiZhiName(solarDateTime.getHour());
            baziData.setTgdz(String.format("%s%s,%s%s,%s%s,%s%s", yearTGName, yearDZName,
                    monthTGName, monthDZName, dayTGName, dayDZName, hourTGName, hourDZName));
            //十神
            baziData.setShishen(String.format("%s,%s,%s,%s", BaziUtil.getShishenName(yearTGName, dayTGName), BaziUtil.getShishenName(monthTGName, dayTGName), SHI_SHEN_DAY, BaziUtil.getShishenName(hourTGName, dayTGName)));
            //藏干
            String yearCanggan = getCangganName(yearDZName);
            String monthCanggan = getCangganName(monthDZName);
            String dayCanggan = getCangganName(dayDZName);
            String hourCanggan = getCangganName(hourDZName);
            baziData.setCanggan(String.format("%s,%s,%s,%s", yearCanggan, monthCanggan, dayCanggan, hourCanggan));
            //藏干十神
            String yearCangShishen;
            StringBuilder yearStrb = new StringBuilder();
            for (String cangganName : yearCanggan.split(_DELIMITER)) {
                yearStrb.append(getShishenName(cangganName,
                        dayTGName)).append(_DELIMITER);
            }
            yearCangShishen = yearStrb.substring(0, yearStrb.length() - 1);

            String monthCangShishen;
            StringBuilder monthStrb = new StringBuilder();
            for (String cangganName : monthCanggan.split(_DELIMITER)) {
                monthStrb.append(getShishenName(cangganName,
                        dayTGName)).append(_DELIMITER);
            }
            monthCangShishen = monthStrb.substring(0, monthStrb.length() - 1);

            String dayCangShishen;
            StringBuilder dayStrb = new StringBuilder();
            for (String cangganName : dayCanggan.split(_DELIMITER)) {
                dayStrb.append(getShishenName(cangganName,
                        dayTGName)).append(_DELIMITER);
            }
            dayCangShishen = dayStrb.substring(0, dayStrb.length() - 1);

            String hourCangShishen;
            StringBuilder hourStrb = new StringBuilder();
            for (String cangganName : hourCanggan.split(_DELIMITER)) {
                hourStrb.append(getShishenName(cangganName,
                        dayTGName)).append(_DELIMITER);
            }
            hourCangShishen = hourStrb.substring(0, hourStrb.length() - 1);

            baziData.setCangshishen(String.format("%s,%s,%s,%s", yearCangShishen, monthCangShishen, dayCangShishen, hourCangShishen));
            //大运流年
            QiyunData qiyunData = getQiyunDateTime(solarDateTime, lunarDateTime, male);
            baziData.setQiyunData(qiyunData);
            baziData.setDayunliunian(getDayunliunianDatas(solarDateTime, lunarDateTime, male, qiyunData.getQiyunDateTime()));
            //命卦
            baziData.setMinggua(getMingguaName(lunarDateTime.getYear(), male));
            //五行
            Map<String, Integer> wuxingCountMap = new LinkedHashMap<>();
            for (String wuxingName : wuxing) {
                wuxingCountMap.put(wuxingName, 0);
            }
            String yearTgWuxing = getTianganWuxingName(yearTGName);
            wuxingCountMap.put(yearTgWuxing, wuxingCountMap.get(yearTgWuxing) + 1);
            String yearDzWuxing = getDizhiWuxingName(yearDZName);
            wuxingCountMap.put(yearDzWuxing, wuxingCountMap.get(yearDzWuxing) + 1);
            String monthTgWuxing = getTianganWuxingName(monthTGName);
            wuxingCountMap.put(monthTgWuxing, wuxingCountMap.get(monthTgWuxing) + 1);
            String monthDzWuxing = getDizhiWuxingName(monthDZName);
            wuxingCountMap.put(monthDzWuxing, wuxingCountMap.get(monthDzWuxing) + 1);
            String dayTgWuxing = getTianganWuxingName(dayTGName);
            wuxingCountMap.put(dayTgWuxing, wuxingCountMap.get(dayTgWuxing) + 1);
            String dayDzWuxing = getDizhiWuxingName(dayDZName);
            wuxingCountMap.put(dayDzWuxing, wuxingCountMap.get(dayDzWuxing) + 1);
            String hourTgWuxing = getTianganWuxingName(hourTGName);
            wuxingCountMap.put(hourTgWuxing, wuxingCountMap.get(hourTgWuxing) + 1);
            String hourDzWuxing = getDizhiWuxingName(hourDZName);
            wuxingCountMap.put(hourDzWuxing, wuxingCountMap.get(hourDzWuxing) + 1);

            baziData.setWuxing(String.format("%s%s,%s%s,%s%s,%s%s", yearTgWuxing, yearDzWuxing, monthTgWuxing, monthDzWuxing, dayTgWuxing, dayDzWuxing, hourTgWuxing, hourDzWuxing));
            StringBuilder countStrb = new StringBuilder();
            for (Map.Entry<String, Integer> countEntry : wuxingCountMap.entrySet()) {
                countStrb.append(countEntry.getKey()).append(_DELIMITER).append(countEntry.getValue()).append(",");
            }
            baziData.setWuxingCount(countStrb.substring(0, countStrb.length() - 1));
        } catch (Exception e) {
            //MainLogger.serverLog.error("get baziData error!", e);
        }
        return baziData;
    }

    private static String getShishenName(String tiangan, String dayTiangan) {
        //noinspection ConstantConditions
        return SHI_SHEN_DATA[GanIndexMap.getByName(dayTiangan).ordinal()][GanIndexMap.getByName(tiangan).ordinal()];
    }

    private static String getCangganName(String dizhi) {
        //noinspection ConstantConditions
        return CANG_GAN_DATA[ZhiIndexMap.getByName(dizhi).ordinal()];
    }

    private static String getTianganWuxingName(String tiangan) {
        //noinspection ConstantConditions
        return tianganWuxing[GanIndexMap.getByName(tiangan).ordinal()];
    }

    private static String getDizhiWuxingName(String dizhi) {
        //noinspection ConstantConditions
        return dizhiWuxing[ZhiIndexMap.getByName(dizhi).ordinal()];
    }

    private static String getMingguaName(int year, boolean male) {
        if (year < 2000) {
            if (male) {
                int index = (100 - year % 100) % 9;
                if (index == 0) {
                    index = 9;
                }
                //如果余数为5要做特殊处理
                if (index == 5) {
                    return minggua[index - 1].split(",")[0];
                } else {
                    return minggua[index - 1];
                }
            } else {
                int index = (year % 100 - 4) % 9;
                if (index == 0) {
                    index = 9;
                }
                //如果余数为5要做特殊处理
                if (index == 5) {
                    return minggua[index - 1].split(",")[1];
                } else {
                    return minggua[index - 1];
                }
            }
        } else {
            if (male) {
                int index = (100 - year % 100 + 8) % 9;
                if (index == 0) {
                    index = 9;
                }
                //如果余数为5要做特殊处理
                if (index == 5) {
                    return minggua[index - 1].split(",")[0];
                } else {
                    return minggua[index - 1];
                }
            } else {
                int index = (year % 100 + 6) % 9;
                if (index == 0) {
                    index = 9;
                }
                //如果余数为5要做特殊处理
                if (index == 5) {
                    return minggua[index - 1].split(",")[1];
                } else {
                    return minggua[index - 1];
                }
            }
        }
    }

    //计算命理学上的月份
    private static int getMonth(LocalDateTime solarDateTime) {
        List<LocalDateTime> solarTermDatas = solarTermMap.get(solarDateTime.getYear());
        LunarDateTime.LunarDate lunarDate = null;

        LocalDateTime temp = null;
        for (LocalDateTime solarTermData : solarTermDatas) {
            if (solarTermData.isAfter(solarDateTime)) {
                try {
                    temp = solarTermData;
//                    lunarDate = LunarCalendarUtil.solarToLunar(solarTermData).toLunarDate();
                } catch (Exception e) {
                   // MainLogger.serverLog.error("solarToLunar error!", e);
                }
                break;
            }
        }
        int month;
        if (temp == null) {
            month = 11;
        } else {
            int temp3 = temp.getMonthValue() - 1 - 1;
            if (temp3 <= 0) {
                temp3 += 12;
            }
            month = temp3;
        }
//        //如果找不到比当前日期大的节气日期说明到了节气数据表末尾
//        if (lunarDate == null) {
//            try {
//                lunarDate = LunarCalendarUtil.solarToLunar(solarTermDatas.get(solarTermDatas.size() - 1)).toLunarDate();
//            } catch (Exception e) {
//                MainLogger.serverLog.error("solarToLunar error!", e);
//            }
//            assert lunarDate != null;
//            if (lunarDate.getDay() > 15) {
//                month = lunarDate.getMonth() + 1;
//            } else {
//                month = lunarDate.getMonth();
//            }
//        } else {
//            if (lunarDate.getDay() > 15) {
//                month = lunarDate.getMonth();
//            } else {
//                month = lunarDate.getMonth() - 1;
//            }
//        }
//        if (month == 0) {
//            month = 12;
//        }
//        if (month == 13) {
//            month = 1;
//        }
        return month;
    }

    private static List<Dayunliunian> getDayunliunianDatas(LocalDateTime solarDateTime, LunarDateTime lunarDateTime, boolean male, LocalDateTime qiyunDateTime) {
        String dayTGName = LunarCalendarUtil.getDayTianGanName(solarDateTime.getYear(), solarDateTime.getMonthValue(), solarDateTime.getDayOfMonth());
        //判断年份的天干阴阳
        boolean yangYear = false;
        assert lunarDateTime != null;
        //noinspection ConstantConditions
        if (GanIndexMap.getByName(LunarCalendarUtil.getYearTianGanName(lunarDateTime.getYear())).ordinal() % 2 == 0) {
            yangYear = true;
        }
        //计算大运推算顺向还是逆向
        boolean forward = false;
        if ((yangYear && male) || (!yangYear && !male)) {
            forward = true;
        }
        //连排80年大运流年
        List<Dayunliunian> dayunliunianList = new ArrayList<>();
        int monthTianganIndex = LunarCalendarUtil.getTianganIndex(LunarCalendarUtil.getMonthTianGanName(lunarDateTime.getYear(), getMonth(solarDateTime)));
        int monthDizhiIndex = LunarCalendarUtil.getDizhiIndex(LunarCalendarUtil.getMonthDiZhiName(getMonth(solarDateTime)));
        for (int i = 0; i < 8; i++) {
            Dayunliunian dayunliunian = new Dayunliunian();
            try {
                dayunliunian.setAge(getAge(solarDateTime, qiyunDateTime) + i * 10);
            } catch (Exception e) {
              //  MainLogger.serverLog.error("getAge error", e);
            }
            if (forward) {
                monthTianganIndex++;
                monthDizhiIndex++;
            } else {
                monthTianganIndex--;
                monthDizhiIndex--;
            }
            if (monthTianganIndex == 10) {
                monthTianganIndex = 0;
            }
            if (monthTianganIndex == -1) {
                monthTianganIndex = 9;
            }
            if (monthDizhiIndex == 12) {
                monthDizhiIndex = 0;
            }
            if (monthDizhiIndex == -1) {
                monthDizhiIndex = 11;
            }
            String tianganName = LunarCalendarUtil.getTianGanName(monthTianganIndex);
            String dizhiName = LunarCalendarUtil.getDiZhiName(monthDizhiIndex);
            dayunliunian.setTgdz(String.format("%s%s", tianganName, dizhiName));

            String tianganShishenName = getShishenName(tianganName, dayTGName);
            String[] cangganArr = getCangganName(dizhiName).split(_DELIMITER);
            String temp = cangganArr[cangganArr.length - 1];
            if (cangganArr.length == 3) {
                temp = cangganArr[1];
            }
            String dizhiShishenName = getShishenName(temp, dayTGName);
            dayunliunian.setShishen(String.format("%s%s", tianganShishenName, dizhiShishenName));
            dayunliunian.setYear(qiyunDateTime.getYear() + i * 10);

            List<Dayunliunian> subDayunliunianList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Dayunliunian subDayunliunian = new Dayunliunian();
                subDayunliunian.setYear(qiyunDateTime.getYear() + i * 10 + j);
                try {
                    subDayunliunian.setAge(getAge(solarDateTime, qiyunDateTime) + i * 10 + j);
                } catch (Exception e) {
                  //  MainLogger.serverLog.error("getAge error", e);
                }
                String[] tgdzName = getSubDayunliunianTGDZ(subDayunliunian.getYear());
                String subTianganName = tgdzName[0];
                String subDizhiName = tgdzName[1];
                subDayunliunian.setTgdz(String.format("%s%s", subTianganName, subDizhiName));
                String subTianganShishenName = getShishenName(subTianganName,
                        dayTGName);
                String[] subCangganArr = getCangganName(subDizhiName).split(_DELIMITER);
                String temp1 = subCangganArr[subCangganArr.length - 1];
                if (subCangganArr.length == 3) {
                    temp1 = subCangganArr[1];
                }
                String subDizhiShishenName = getShishenName(temp1, dayTGName);
                subDayunliunian.setShishen(String.format("%s%s", subTianganShishenName, subDizhiShishenName));

                subDayunliunianList.add(subDayunliunian);
            }
            dayunliunian.setSubData(subDayunliunianList);
            dayunliunianList.add(dayunliunian);
        }
        return dayunliunianList;
    }

    //算出相差的时辰数
    private static int minutesBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (int) ((endDate.atZone(ZoneId.systemDefault()).toEpochSecond() - startDate.atZone(ZoneId.systemDefault()).toEpochSecond()) / (60));
    }

    //由出生日期获得年龄
    private static int getAge(LocalDateTime birthDateTime, LocalDateTime calDateTime) throws Exception {
        if (calDateTime.isBefore(birthDateTime)) {
            throw new IllegalArgumentException(
                    "The birthDateTime is before calDateTime.It's unbelievable!");
        }
        int yearCal = calDateTime.getYear();
        int monthCal = calDateTime.getMonthValue();
        int dayCal = calDateTime.getDayOfMonth();

        int yearBirth = birthDateTime.getYear();
        int monthBirth = birthDateTime.getMonthValue();
        int dayBirth = birthDateTime.getDayOfMonth();

        int age = yearCal - yearBirth + 1;

//        if (monthCal <= monthBirth) {
//            if (monthCal == monthBirth) {
//                if (dayCal < dayBirth) age--;
//            } else {
//                age--;
//            }
//        }
        return age;
    }

    private static String[] getSubDayunliunianTGDZ(int year) {
        String[] tgdz = new String[2];
        int index = year % 60 - 4;
        if (index < 0) {
            index += 60;
        }
        tgdz[0] = String.valueOf(TGDZ_60[index].charAt(0));
        tgdz[1] = String.valueOf(TGDZ_60[index].charAt(1));
        return tgdz;
    }

    private static QiyunData getQiyunDateTime(LocalDateTime solarDateTime, LunarDateTime lunarDateTime, boolean male) {
        QiyunData qiyunData = new QiyunData();

        //判断年份的天干阴阳
        boolean yangYear = false;
        assert lunarDateTime != null;
        //noinspection ConstantConditions
        if (GanIndexMap.getByName(LunarCalendarUtil.getYearTianGanName(lunarDateTime.getYear())).ordinal() % 2 == 0) {
            yangYear = true;
        }
        //计算大运推算顺向还是逆向
        boolean forward = false;
        if ((yangYear && male) || (!yangYear && !male)) {
            forward = true;
        }
        //计算起运年份
        List<LocalDateTime> solarTermDatas = new ArrayList<>();
        solarTermDatas.addAll(solarTermMap.get(lunarDateTime.getYear() - 1));
        solarTermDatas.addAll(solarTermMap.get(lunarDateTime.getYear()));
        solarTermDatas.addAll(solarTermMap.get(lunarDateTime.getYear() + 1));
        LocalDateTime latestSolarTerm = null;
        for (int i = 0; i < solarTermDatas.size(); i++) {
            if (solarTermDatas.get(i).isAfter(solarDateTime)) {
                if (forward) {
                    latestSolarTerm = solarTermDatas.get(i);
                    break;
                } else {
                    while (i-- > 0) {
                        if (solarTermDatas.get(i).isBefore(solarDateTime)) {
                            latestSolarTerm = solarTermDatas.get(i);
                            i = 0;
                        }
                    }
                    break;
                }
            }
        }
        //计算相差的分钟数
        int minutes;
        assert latestSolarTerm != null;
        if (latestSolarTerm.isAfter(solarDateTime)) {
            solarDateTime = solarDateTime.withSecond(0);
            minutes = minutesBetween(solarDateTime, latestSolarTerm);
        } else {
            solarDateTime = solarDateTime.withSecond(0);
            minutes = minutesBetween(latestSolarTerm, solarDateTime);
        }
        int d = minutes / (24 * 60);
        int h = minutes % (24 * 60) / 60;
        int m = minutes - 24 * 60 * d - 60 * h;
//        double h = minutes % (24 * 60) / 60 + (double) (minutes % (24 * 60) % 60) / 60;

        int yearNum = d / 3;
        int monthNum = d % 3 * 4;
        int dayNum = h * 5;
//        int hourNum = m * 5 / 60;
        double temp = m * 2 / 24.0;
        int tempDay = (int) Math.floor(temp);
        dayNum += tempDay;
        double temp1 = temp - tempDay;
        int hourNum = (int) Math.floor(temp1 * 24);
//        int dayNum = (int) Math.floor(h * 5);
//        if (dayNum >= 30) {
//            monthNum += dayNum / 30;
//            dayNum = dayNum % 30;
//        }

        LocalDateTime qiyunDateTime = solarDateTime.plusYears(yearNum).plusMonths(monthNum).plusDays(dayNum).plusHours(hourNum);

        qiyunData.setLatestSolarTerm(latestSolarTerm);
        qiyunData.setSrcDayNums(d);
        qiyunData.setSrcHourNums(h);
        qiyunData.setSrcMinutesNums(m);
        qiyunData.setYearNums(yearNum);
        qiyunData.setMonthNums(monthNum);
        qiyunData.setDayNums(dayNum);
        qiyunData.setHourNums(hourNum);
        qiyunData.setQiyunDateTime(qiyunDateTime);
        return qiyunData;
    }

    public static void main(String[] args) {
        //2004 11 7 2 3 2 4 12 5
//        LocalDateTime solarDateTime = LocalDateTime.of(1984, 10, 1, 12, 0, 0);
        LocalDateTime solarDateTime = LocalDateTime.of(1980, 8, 8, 8, 8, 0);
        System.out.println(getBaziData(solarDateTime, true));
    }

    private enum GanIndexMap {
        JIA("甲"), YI("乙"), BING("丙"), DING("丁"), WU("戊"), JI("己"), GENG("庚"), XIN("辛"), REN("壬"), GUI("癸");
        private String name;

        GanIndexMap(String name) {
            this.name = name;
        }

        static GanIndexMap getByName(String name) {
            for (GanIndexMap indexMap : GanIndexMap.values()) {
                if (indexMap.getName().equals(name)) {
                    return indexMap;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }
    }

    private enum ZhiIndexMap {
        ZI("子"), CHOU("丑"), YAN("寅"), MOU("卯"), CHEN("辰"), SHI("巳"), WU("午"), WEI("未"), SHEN("申"), YOU("酉"), XU("戌"), HAI("亥");
        private String name;

        ZhiIndexMap(String name) {
            this.name = name;
        }

        static ZhiIndexMap getByName(String name) {
            for (ZhiIndexMap indexMap : ZhiIndexMap.values()) {
                if (indexMap.getName().equals(name)) {
                    return indexMap;
                }
            }
            return null;
        }


        public String getName() {
            return name;
        }
    }

}

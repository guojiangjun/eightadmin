package com.bootdo.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Created by god on 2017/8/4.
 */
public class LunarCalendarUtil {
    // 计算阴历日期参照1900年到2049年
    private final static int[] LUNAR_INFO = {
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
            0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
            0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
            0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
            0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
            0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
            0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
            0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
            0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
            0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
            0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
            0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0
    };

    private final static String[][] tgdz = new String[][]{
            {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"}//10天干
            , {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"}};//12地支

    private final static String[][] monthtg = new String[][]{
            {"丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁"},
            {"戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己"},
            {"庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛"},
            {"壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"},
            {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙"}};
    private final static String[] monthdz = new String[]{
            "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥", "子", "丑"};//12地支

    private final static String[][] hourtg = new String[][]{
            {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙"},
            {"丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁"},
            {"戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己"},
            {"庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛"},
            {"壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"},};
    private final static String[] hourdz = new String[]{
            "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};//12地支
    //12生肖，（注：12生肖对应12地支，即子鼠，丑牛,寅虎依此类推）
//    private final static String[] animalYear =
//            new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    private final static int START_YEAR = 1804;//定义起始年，1804年为甲子年属鼠

    // 允许输入的最小年份
    private final static int MIN_YEAR = 1900;
    // 允许输入的最大年份
    private final static int MAX_YEAR = 2049;
    // 阳历日期计算起点
    private final static LocalDate START_DATE = LocalDate.of(1900, Month.JANUARY, 30);

    /**
     * 计算阴历 {@code year}年闰哪个月 1-12 , 没闰传回 0
     *
     * @param year 阴历年
     * @return (int)月份
     */
    private static int getLeapMonth(int year) {
        return LUNAR_INFO[year - 1900] & 0xf;
    }

    /**
     * 计算阴历{@code year}年闰月多少天
     *
     * @param year 阴历年
     * @return (int)天数
     */
    private static int getLeapMonthDays(int year) {
        if (getLeapMonth(year) != 0) {
            if ((LUNAR_INFO[year - 1900] & 0xf0000) == 0) {
                return 29;
            } else {
                return 30;
            }
        } else {
            return 0;
        }
    }

    /**
     * 计算阴历{@code lunarYeay}年{@code month}月的天数
     *
     * @param lunarYeay 阴历年
     * @param month     阴历月
     * @return (int)该月天数
     */
    private static int getMonthDays(int lunarYeay, int month) throws Exception {
        if ((month > 31) || (month < 0)) {
            throw (new Exception("月份有错！"));
        }
        // 0X0FFFF[0000 {1111 1111 1111} 1111]中间12位代表12个月，1为大月，0为小月
        int bit = 1 << (16 - month);
        if (((LUNAR_INFO[lunarYeay - 1900] & 0x0FFFF) & bit) == 0) {
            return 29;
        } else {
            return 30;
        }
    }

    /**
     * 计算阴历{@code year}年的总天数
     *
     * @param year 阴历年
     * @return (int)总天数
     */
    private static int getYearDays(int year) {
        int sum = 29 * 12;
        for (int i = 0x8000; i >= 0x8; i >>= 1) {
            if ((LUNAR_INFO[year - 1900] & 0xfff0 & i) != 0) {
                sum++;
            }
        }
        return sum + getLeapMonthDays(year);
    }

    /**
     * 计算两个阳历日期相差的天数。
     *
     * @param startDate 开始时间
     * @param endDate   截至时间
     * @return (int)天数
     */
    private static int daysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay());
    }

    /**
     * 检查阴历日期是否合法
     *
     * @param lunarYear     阴历年
     * @param lunarMonth    阴历月
     * @param lunarDay      阴历日
     * @param leapMonthFlag 闰月标志
     */
    private static void checkLunarDate(int lunarYear, int lunarMonth, int lunarDay, boolean leapMonthFlag) throws Exception {
        if ((lunarYear < MIN_YEAR) || (lunarYear > MAX_YEAR)) {
            throw (new Exception("非法农历年份！"));
        }
        if ((lunarMonth < 1) || (lunarMonth > 12)) {
            throw (new Exception("非法农历月份！"));
        }
        if ((lunarDay < 1) || (lunarDay > 30)) { // 中国的月最多30天
            throw (new Exception("非法农历天数！"));
        }

        int leap = getLeapMonth(lunarYear);// 计算该年应该闰哪个月
        if (leapMonthFlag && (lunarMonth != leap)) {
            throw (new Exception("非法闰月！"));
        }
    }

    /**
     * 获取当前年份与起始年之间的差值
     **/
    private static int subtractYear(int year) {
        int jiaziYear = START_YEAR;
        if (year < jiaziYear) {//如果年份小于起始的甲子年(startYear = 1804),则起始甲子年往前偏移
            jiaziYear = jiaziYear - (60 + 60 * ((jiaziYear - year) / 60));//60年一个周期
        }
        return year - jiaziYear;
    }

    /**
     * 阴历转换为阳历
     *
     * @param lunarDate     阴历日期,格式YYYYMMDD
     * @param leapMonthFlag 是否为闰月
     * @return 阳历日期, 格式：YYYYMMDD
     */
    public static LocalDate lunarToSolar(LocalDate lunarDate, boolean leapMonthFlag) throws Exception {
        int lunarYear = lunarDate.getYear();
        int lunarMonth = lunarDate.getMonthValue();
        int lunarDay = lunarDate.getDayOfMonth();

        checkLunarDate(lunarYear, lunarMonth, lunarDay, leapMonthFlag);

        int offset = 0;

        for (int i = MIN_YEAR; i < lunarYear; i++) {
            int yearDaysCount = getYearDays(i); // 求阴历某年天数
            offset += yearDaysCount;
        }
        //计算该年闰几月
        int leapMonth = getLeapMonth(lunarYear);

        if (leapMonthFlag & leapMonth != lunarMonth) {
            throw (new Exception("您输入的闰月标志有误！"));
        }

        //当年没有闰月或月份早于闰月或和闰月同名的月份
        if (leapMonth == 0 || (lunarMonth < leapMonth) || (lunarMonth == leapMonth && !leapMonthFlag)) {
            for (int i = 1; i < lunarMonth; i++) {
                int tempMonthDaysCount = getMonthDays(lunarYear, i);
                offset += tempMonthDaysCount;
            }

            // 检查日期是否大于最大天
            if (lunarDay > getMonthDays(lunarYear, lunarMonth)) {
                throw (new Exception("不合法的农历日期！"));
            }
            offset += lunarDay; // 加上当月的天数
        } else {//当年有闰月，且月份晚于或等于闰月
            for (int i = 1; i < lunarMonth; i++) {
                int tempMonthDaysCount = getMonthDays(lunarYear, i);
                offset += tempMonthDaysCount;
            }
            if (lunarMonth > leapMonth) {
                int temp = getLeapMonthDays(lunarYear); // 计算闰月天数
                offset += temp; // 加上闰月天数

                if (lunarDay > getMonthDays(lunarYear, lunarMonth)) {
                    throw (new Exception("不合法的农历日期！"));
                }
                offset += lunarDay;
            } else {    // 如果需要计算的是闰月，则应首先加上与闰月对应的普通月的天数
                // 计算月为闰月
                int temp = getMonthDays(lunarYear, lunarMonth); // 计算非闰月天数
                offset += temp;

                if (lunarDay > getLeapMonthDays(lunarYear)) {
                    throw (new Exception("不合法的农历日期！"));
                }
                offset += lunarDay;
            }
        }

        return START_DATE.plusDays(offset);
    }

    /**
     * 阳历日期转换为阴历日期
     *
     * @param solarDateTime 阳历日期,格式YYYYMMDD
     * @return 阴历日期
     */
    public static LunarDateTime solarToLunar(LocalDateTime solarDateTime) throws Exception {
        int i;
        int temp = 0;
        int lunarYear;
        int lunarMonth; //农历月份
        int lunarDay; //农历当月第几天

        int offset = daysBetween(START_DATE, solarDateTime.toLocalDate());

        for (i = MIN_YEAR; i <= MAX_YEAR; i++) {
            temp = getYearDays(i);  //求当年农历年天数
            if (offset - temp < 1) {
                break;
            } else {
                offset -= temp;
            }
        }
        lunarYear = i;

        int leapMonth = getLeapMonth(lunarYear);//计算该年闰哪个月
        //设定当年是否有闰月
        boolean isLeapYear = leapMonth > 0;

        for (i = 1; i <= 12; i++) {
            if (i == leapMonth + 1 && isLeapYear) {
                temp = getLeapMonthDays(lunarYear);
                isLeapYear = false;
                i--;
            } else {
                temp = getMonthDays(lunarYear, i);
            }
            offset -= temp;
            if (offset <= 0) {
                break;
            }
        }

        offset += temp;
        lunarMonth = i;
        lunarDay = offset;

        return new LunarDateTime(lunarYear, lunarMonth, lunarDay, solarDateTime.getHour(), solarDateTime.getMinute(), solarDateTime.getSecond());
//        return LocalDateTime.of(LocalDate.of(lunarYear, lunarMonth, lunarDay), solarDateTime.toLocalTime());
    }

    /**
     * 获取该年的天干名称
     **/
    public static String getTianGanName(int index) {
        return tgdz[0][index];
    }

    /**
     * 获取该年的地支名称
     **/
    public static String getDiZhiName(int index) {
        return tgdz[1][index];
    }

    /**
     * 获取该年的天干名称
     **/
    public static String getYearTianGanName(int year) {
        return tgdz[0][subtractYear(year) % 10];
    }

    /**
     * 获取该年的地支名称
     **/
    public static String getYearDiZhiName(int year) {
        return tgdz[1][subtractYear(year) % 12];
    }

    /**
     * 获取该年的天干、地支名称
     *
     * @param year 年份
     * @return string
     */
    public static String getYearTGDZName(int year) {
        return getYearTianGanName(year) + getYearDiZhiName(year);
    }

//    /**
//     * 获取该年的生肖名称
//     *
//     * @param year 年份
//     * @return string
//     */
//    public static String getYearZodiacName(int year) {
//        return animalYear[subtractYear(year) % 12];
//    }

    /**
     * 获取该月的天干名称
     **/
    public static String getMonthTianGanName(int year, int month) {
        assert month > 0;
        //noinspection ConstantConditions
        return monthtg[TgMap.getByName(getYearTianGanName(year)).getIndex()][month - 1];
    }

    /**
     * 获取该月的地支名称
     **/
    public static String getMonthDiZhiName(int month) {
        return monthdz[month - 1];
    }

    /**
     * 获取该月的天干、地支名称
     *
     * @param year 年份
     * @return string
     */
    public static String getMonthTGDZName(int year, int month) {
        return getMonthTianGanName(year, month) + getMonthDiZhiName(month);
    }

    /**
     * 获取该日的天干名称
     **/
    public static String getDayTianGanName(int year, int month, int day) {
        //G = 4C + [C / 4] + 5y + [y / 4] + [3 * (M + 1) / 5] + d - 3
        //其中 C 是世纪数减一，y 是年份后两位，M 是月份，d 是日数。1月和2月按上一年的13月和14月来算。奇数月i=0，偶数月i=6。
        int c = year / 100;
        int y = year % 100;
        int m = month;
        if (month == 1 || month == 2) {
            y--;
            m = 12 + month;
        }
        int index = (4 * c + c / 4 + 5 * y + y / 4 + 3 * (m + 1) / 5 + day - 3) % 10;
        if (index == 0) {
            index = 9;
        } else {
            index -= 1;
        }
        return tgdz[0][index];
    }

    /**
     * 获取该日的地支名称
     **/
    public static String getDayDiZhiName(int year, int month, int day) {
        //Z = 8C + [C / 4] + 5y + [y / 4] + [3 * (M + 1) / 5] + d + 7 + i
        //其中 C 是世纪数减一，y 是年份后两位，M 是月份，d 是日数。1月和2月按上一年的13月和14月来算。奇数月i=0，偶数月i=6。
        int c = year / 100;
        int y = year % 100;
        int m = month;
        if (month == 1 || month == 2) {
            y--;
            m = 12 + month;
        }
        int i = 0;
        if (month % 2 == 0) {
            i = 6;
        }

        int index = (8 * c + c / 4 + 5 * y + y / 4 + 3 * (m + 1) / 5 + day + 7 + i) % 12;
        if (index == 0) {
            index = 11;
        } else {
            index -= 1;
        }
        return tgdz[1][index];
    }

    /**
     * 获取该日的天干、地支名称
     *
     * @param year 年份
     * @return string
     */
    public static String getDayTGDZName(int year, int month, int day) {
        return String.format("%s%s", getDayTianGanName(year, month, day), getDayDiZhiName(year, month, day));
    }

    /**
     * 获取该时的天干名称
     **/
    public static String getHourTianGanName(int year, int month, int day, int hour) {
        //noinspection ConstantConditions
        int index = TgMap.getByName(getDayTianGanName(year, month, day)).getIndex();
        if (hour == 23) {
            hour = 0;
            index = (index + 1) % 5;
        }

        return hourtg[index][(hour + 1) / 2];
    }

    /**
     * 获取该时的地支名称
     **/
    public static String getHourDiZhiName(int hour) {
        if (hour == 23)
            hour = 0;
        return hourdz[(hour + 1) / 2];
    }

    /**
     * 获取该时的天干、地支名称
     *
     * @param year 年份
     * @return string
     */
    public static String getHourTGDZName(int year, int month, int day, int hour) {
        return getHourTianGanName(year, month, day, hour) + getHourDiZhiName(hour);
    }

    public static int getTianganIndex(String tianganName) {
        for (int i = 0; i < tgdz[0].length; i++) {
            if (tgdz[0][i].equals(tianganName)) {
                return i;
            }
        }
        return -1;
    }

    public static int getDizhiIndex(String dizhiName) {
        for (int i = 0; i < tgdz[1].length; i++) {
            if (tgdz[1][i].equals(dizhiName)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        LocalDateTime solarDateTime = LocalDateTime.of(1989, 4, 5, 0, 0, 0);
        try {
            System.out.println(BaziUtil.getBaziData(solarDateTime, true));
//            System.out.println(LocalDate.of(2000, 2, 29));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum TgMap {
        JIA(0, "甲"), JI(0, "己"), YI(1, "乙"), GENG(1, "庚"), BING(2, "丙"), XIN(2, "辛"), DING(3, "丁"), REN(3, "壬"), WU(4, "戊"), GUI(4, "癸");
        private int index;
        private String name;

        TgMap(int index, String name) {
            this.index = index;
            this.name = name;
        }

        static TgMap getByName(String name) {
            for (TgMap tgMap : TgMap.values()) {
                if (tgMap.getName().equals(name)) {
                    return tgMap;
                }
            }
            return null;
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }
    }
}

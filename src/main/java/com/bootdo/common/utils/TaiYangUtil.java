package com.bootdo.common.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by god on 2020/1/4.
 */
public class TaiYangUtil {
    private static Map<String, String[]> jingweiMap = new LinkedHashMap<>();
    private static Map<String, String[]> zhenMap = new HashMap<>();

    static {
        String classPath = TaiYangUtil.class.getResource("/").getPath();
        File jingweiFile = new File(String.format("%sjingwei.txt", classPath.substring(0, classPath.length() - 8)));
        File zhenFile = new File(String.format("%szhen.txt", classPath.substring(0, classPath.length() - 8)));
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(jingweiFile));
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                if (!data.trim().isEmpty()) {
                    data = data.substring(0, data.length() - 1);

                    String cityName = data.split(" ")[0].split(":")[1];
                    String jingdu = "-1";
                    String weidu = "-1";
                    if (data.split(" ")[1].split(":").length > 1) {
                        jingdu = data.split(" ")[1].split(":")[1];
                    }
                    if (data.split(" ")[1].split(":").length > 1) {
                        weidu = data.split(" ")[2].split(":")[1];
                    }
                    jingweiMap.put(cityName, new String[]{jingdu, weidu});
                }
            }

            bufferedReader = new BufferedReader(new FileReader(zhenFile));
            while ((data = bufferedReader.readLine()) != null) {
                if (!data.trim().isEmpty()) {
                    String[] dataArr = data.split("\\s+");
                    for (String dataItem : dataArr) {
                        String monnth = dataItem.split("月")[0];
                        String day = dataItem.split("月")[1].split("日")[0];
                        String flag = String.valueOf(dataItem.split("日")[1].split("分")[0].charAt(0));
                        String mintue = dataItem.split("日")[1].split("分")[0].substring(1);
                        String second = dataItem.split("分")[1].split("秒")[0];
                        zhenMap.put(monnth + "_" + day, new String[]{flag, mintue, second});
                    }

                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            //MainLogger.serverLog.error("read file error!", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static Object[] calcTaiyangshi(LocalDateTime time, double zoneJingdu, String cityName) {
        String[] jingwei = jingweiMap.get(cityName);
        if (jingwei == null) {
            for (Map.Entry<String, String[]> entry : jingweiMap.entrySet()) {
                if (entry.getKey().startsWith(cityName) || entry.getKey().endsWith(cityName)) {
                    jingwei = entry.getValue();
                    break;
                }
            }
        }

        if (jingwei != null && !jingwei[0].equals("-1")) {
            double temp1 = Double.parseDouble(jingwei[0]) - zoneJingdu;
            double temp2 = temp1 * 4;
            double temp3 = temp2 - (int) temp2;
            double temp4 = temp3 * 60;
            int minutes = (int) temp2;
            int seconds = (int) Math.round(temp4);
            LocalDateTime normalTaiyang = time.plusMinutes(minutes).plusSeconds(seconds);
            int month = time.getMonthValue();
            int day = time.getDayOfMonth();
            String zhenKey = String.format("%d_%02d", month, day);
            String[] zhenArr = zhenMap.get(zhenKey);
            if (zhenArr == null) {
                return null;
            }
            LocalDateTime realTaiyang = null;
            if (zhenArr[0].equals("-")) {
                realTaiyang = normalTaiyang.minusMinutes(Integer.parseInt(zhenArr[1])).minusSeconds(Integer.parseInt(zhenArr[2]));
            } else if (zhenArr[0].equals("+")) {
                realTaiyang = normalTaiyang.plusMinutes(Integer.parseInt(zhenArr[1])).plusSeconds(Integer.parseInt(zhenArr[2]));
            }

            return new Object[]{jingwei[0], jingwei[1], normalTaiyang, realTaiyang};
        }
        return null;
    }

    public static void main(String[] args) {
        double temp1 = 116.3980 - 120;
        LocalDateTime time = LocalDateTime.of(1980, 6, 1, 18, 0, 0);
        System.out.println(calcTaiyangshi(time, 120, "北京")[0]);
    }
}

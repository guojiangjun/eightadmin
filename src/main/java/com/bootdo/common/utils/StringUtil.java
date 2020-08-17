package com.bootdo.common.utils;

/**
 * Created by god on 2017/7/6.
 */
public class StringUtil {

    public static final String MESSAGE_REGR = ".*[_]+.*";

    public static boolean isEmpty(String param) {
        if (param == null || param.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean hasIlleageSymbol(String input, String regr) {
        if (input.trim().matches(regr))
            return true;
        return false;
    }


    public static void main(String[] args) {
        //		System.out.println(hasIlleageSymbol("123?233_",MESSAGE_REGR));
//		System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());
//		System.out.println(LocalDateTime.now().compareTo(LocalDateTime.MIN));
//		System.out.println(Charset.defaultCharset().name());
//        for (int i=0;i<= 1000;i++){
//            System.out.println(new Random().nextInt(100));
//        }
        System.out.println("0".equals("0"));
    }
}

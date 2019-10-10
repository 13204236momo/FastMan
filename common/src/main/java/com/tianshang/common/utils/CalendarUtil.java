package com.tianshang.common.utils;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/5/31 0031.
 */

public class CalendarUtil {

    /**
     * 获取当前年份
     * @return
     */
    public static int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     * @return
     */
    public static int getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日
     * @return
     */
    public static int getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日
     * @return
     */
    public static int getCurrentDayOfWeekInt(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getCurrentDayOfWeekString(){
        String day = "";
        Calendar calendar = Calendar.getInstance();

        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:
                day = "周日";
                break;
            case 2:
                day = "周一";
                break;
            case 3:
                day = "周二";
                break;
            case 4:
                day = "周三";
                break;
            case 5:
                day = "周四";
                break;
            case 6:
                day = "周五";
                break;
            case 7:
                day = "周六";
                break;
                default:
                break;
        }
        return day;
    }

}

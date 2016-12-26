package com.dabo.xunuo.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 获取当前时间距离date的天数
     *
     * @param date
     * @return
     */
    public static int getRemainDays(Date date) {
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        targetCalendar.set(Calendar.MILLISECOND, 0);
        targetCalendar.set(Calendar.SECOND, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(new Date());
        currentCalendar.set(Calendar.MILLISECOND, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);

        long timeInterval = targetCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();

        int day = (int) (timeInterval / (1000 * 60 * 60 * 24));

        return day;
    }

    /**
     * 获取当前时间距离date的天数
     *
     * @param timeStr
     * @return
     */
    public static int getRemainDays(long timeStr) {
        return getRemainDays(new Date(timeStr));
    }

    /**
     * 获取时间的日期
     *
     * @param timeStr
     * @return
     */
    public static String getDateStrWithoutTime(long timeStr) {
        if (timeStr == 0) {
            return "";
        }
        Date date = new Date(timeStr);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 获得时间的字符串表示
     *
     * @param timeStr
     * @return
     */
    public static String getStr(long timeStr) {
        Date date = new Date(timeStr);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 是否是闰年
     */
    public static boolean isLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        boolean b = ((GregorianCalendar) calendar).isLeapYear(year);
        return b;
    }

    /**
     * 是否有31天
     */
    public static boolean has31Day(int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return true;
        }
        return false;
    }

    /**
     * 是否有30天
     */
    public static boolean has30Day(int month) {
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return true;
        }
        return false;
    }
}

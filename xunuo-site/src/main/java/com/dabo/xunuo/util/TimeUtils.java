package com.dabo.xunuo.util;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 获取当前时间距离date的天数
     * @param date
     * @return
     */
    public static int getRemainDays(Date date){
        Calendar targetCalendar=Calendar.getInstance();
        targetCalendar.setTime(date);
        targetCalendar.set(Calendar.MILLISECOND,0);
        targetCalendar.set(Calendar.SECOND,0);
        targetCalendar.set(Calendar.MINUTE,0);
        targetCalendar.set(Calendar.HOUR_OF_DAY,0);

        Calendar currentCalendar=Calendar.getInstance();
        currentCalendar.setTime(new Date());
        currentCalendar.set(Calendar.MILLISECOND,0);
        currentCalendar.set(Calendar.SECOND,0);
        currentCalendar.set(Calendar.MINUTE,0);
        currentCalendar.set(Calendar.HOUR_OF_DAY,0);

        long timeInterval = targetCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();

        int day= (int) (timeInterval/(1000 * 60 * 60 * 24));

        return day;
    }
    /**
     * 获取当前时间距离date的天数
     * @param timeStr
     * @return
     */
    public static int getRemainDays(long timeStr){
       return getRemainDays(new Date(timeStr));
    }

    /**
     * 获取时间的日期
     * @param timeStr
     * @return
     */
    public static String getDateStrWithoutTime(long timeStr){
        Date date=new Date(timeStr);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}

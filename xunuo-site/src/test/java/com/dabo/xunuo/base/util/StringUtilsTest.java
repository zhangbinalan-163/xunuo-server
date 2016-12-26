package com.dabo.xunuo.base.util;

import com.dabo.xunuo.base.common.Constants;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 工具方法测试类
 * Created by zhangbin on 16/8/2.
 */
public class StringUtilsTest {

    @Test
    public void md5() {
        String md5Str = StringUtils.md5(Constants.APP_KEY + "20110901");
        System.out.println(md5Str);
    }

    @Test
    public void testJson() throws UnsupportedEncodingException {
        int currentYear = 2016;
        int currentMonth = 11;
        int currentDayOfMonth = 31;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 9, 30);

        int eventMonth = calendar.get(Calendar.MONTH);
        int eventDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        int nextTriggerYear = currentYear;
        int nextTriggerMonth = currentMonth;
        int nextTriggerDayOfMonth = eventDayOfMonth;

        if (currentMonth == 11) {
            if(eventDayOfMonth < currentDayOfMonth){
                nextTriggerYear = currentYear + 1;
                nextTriggerMonth = 0;
            }
        }else{
            if (eventDayOfMonth < currentDayOfMonth) {
                nextTriggerMonth = currentMonth + 1;
            }
        }
        if (eventDayOfMonth == 31) {
            if (TimeUtils.has30Day(nextTriggerMonth + 1)) {
                nextTriggerDayOfMonth = 30;
            } else if (nextTriggerMonth == 1) {
                if (TimeUtils.isLeapYear(nextTriggerYear)) {
                    nextTriggerDayOfMonth = 29;
                } else {
                    nextTriggerDayOfMonth = 28;
                }
            }
        } else if (eventDayOfMonth == 30) {
            if (nextTriggerMonth == 1) {
                if (TimeUtils.isLeapYear(nextTriggerYear)) {
                    nextTriggerDayOfMonth = 29;
                } else {
                    nextTriggerDayOfMonth = 28;
                }
            }
        }
        calendar.set(nextTriggerYear, nextTriggerMonth, nextTriggerDayOfMonth);
        System.out.println(calendar.getTime());
    }
}

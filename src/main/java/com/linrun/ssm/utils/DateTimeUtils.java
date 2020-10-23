package com.linrun.ssm.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具包
 *
 * @author 骆焕
 * @createDate 2019-11-14
 */
public class DateTimeUtils {

   public static SimpleDateFormat YYYYMMDD =new SimpleDateFormat("yyyy-MM-dd" );

    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public static String[] MONTHS = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
   public static String[] DAYS = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"} ;
    /**
     * java.util.Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转java.util.Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * java.util.Date转String
     *
     * @param format
     * @return
     */
    public static String Date2Str(Date date, String format) {
        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * String转java.util.Date
     *
     * @param format
     * @return
     */
    public static Date Str2Date(String strDate, String format){
        if (StringUtils.isEmpty(strDate))
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 日期串转时间戳
     * @param dateStr
     * @param format
     * @return
     */
    public static Long dateStr2Stamp(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr))
            return null;

        Long stamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            stamp = date.getTime();
        } catch (ParseException e) {
        }

        return stamp;
    }

    /**
     * 时间戳转日期
     * @param stamp
     * @return
     */
    public static Date stamp2Date(long stamp) {
        if (stamp <=0)
            return null;

        return new Date(stamp);
    }

    public static Boolean verificationDate(String year, String month, String day) throws ParseException {
        boolean isVerificationDate = false;
        String[]monthArr = month.split(",");
        String[]dayArr = day.split(",");
        for (int i = 0;i<monthArr.length;i++){
            String newMonth = new String();
            if(monthArr[i].equals("1")){
                 newMonth = MONTHS[i];
                for (int j = 0;j<dayArr.length;j++){
                    String newDay = new String();
                    if(dayArr[j].equals("1")){
                        newDay = DAYS[j];
                        StringBuffer sb = new StringBuffer();
                        sb.append(year);
                        sb.append("-");
                        sb.append(newMonth);
                        sb.append("-");
                        sb.append(newDay);
                        if(compareDate(sb.toString())){
                            isVerificationDate =  true;
                        }else {
                            isVerificationDate =  false;
                        }
                        if (!isVerificationDate){
                            return isVerificationDate;
                        }
                    }
                }
                if (!isVerificationDate){
                    return isVerificationDate;
                }
            }

        }
        return isVerificationDate;
    }
    public static Boolean compareDate(String data) throws ParseException {
        Date date = new Date();
        Date newDate = YYYYMMDD.parse(data);
        return date.before(addDays(newDate,1));
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        Date newDay = cal.getTime();
        return newDay;
    }
}

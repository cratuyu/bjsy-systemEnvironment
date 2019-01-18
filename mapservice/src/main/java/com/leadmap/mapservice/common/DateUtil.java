package com.leadmap.mapservice.common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/11/3 16:47
 */
public class DateUtil {

    /**
     * 字符串转为时间
     *
     * @param source
     * @param dateFormat
     * @return
     */
    public static Date string2Date(String source, DateFormat dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.getFormatString());
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取过去第几天的日期(- 操作) 或者 未来 第几天的日期( + 操作)
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取今天整点日期
     *
     * @param past
     * @return
     */
    public static String getTodayDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past +1);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today)+" 00:00:00";
        return result;
    }

    /**
     *
     * @param past
     * @return
     */
    public static String getDateYYmmdd(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取当前时间的时分秒
     *
     * @param
     * @return
     */
    public static String getTodayDateHHmmss() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss");
        System.out.println("当前时间：" + sdf.format(date));
        String result = sdf.format(date);
        return result;
    }

    /**
     * 根据年月获取这个月有多少天
     * @param date
     * @return
     */
    public static int getdayOfMonth(String date){
        Integer year = Integer.valueOf(date.split("-")[0]);
        Integer month = Integer.valueOf(date.split("-")[1]);
        Calendar c = Calendar.getInstance();
        //输入类型为int类型
        c.set(year, month, 0);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth;
    }

    /**
     * 获取未来 第几天的日期( + 操作)
     *
     * @param past
     * @return
     */
    public static String getWillDate(String time,int past) {
        String year = time.split("-")[0];
        String month = time.split("-")[1];
        String date = year+"-"+month+"-"+ past + " 00:00:00";
        return date;
    }

    /**
     * 获取要显示的日期
     * @param time
     * @param past
     * @return
     */
    public static String getUpdateDate(String time,int past) {
        String year = time.split("-")[0];
        String month = time.split("-")[1];
        if(past < 10) {
            String date = year + "-" + month + "-0" + past;
            return date;
        }else{
            String date = year + "-" + month + "-" + past;
            return date;
        }
    }

    public static void main(String[] args) {
        System.out.println(getWillDate("2018-11-8",1));
        System.out.println(getTodayDate(1));
        System.out.println(getPastDate(0));
        System.out.println(getPastDate(1));
        System.out.println(getDateYYmmdd(1));
        System.out.println(getTodayDate(8));
        System.out.println(getTodayDate(7));
        System.out.println(getdayOfMonth("2018-8-3"));
    }
}

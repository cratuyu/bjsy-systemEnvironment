package com.leadmap.erawl.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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



    public static void main(String[] args) {
        System.out.println(string2Date("2018-11-12 17:23",DateFormat.DateFull));
    }
}

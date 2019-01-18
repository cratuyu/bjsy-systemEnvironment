package com.leadmap.erawl.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/9 11:37
 */
public class Util {
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 创建分页请求.
     */
    public static PageRequest buildPageRequest(ServletRequest request) {
        String strPageNumber = request.getParameter("pageNumber");
        String strPageSize = request.getParameter("pageSize");
        String sortType = request.getParameter("sortType");
        if (StringUtils.isNotBlank(strPageNumber) && StringUtils.isNotBlank(strPageSize)) {
            int pageNumber = Integer.parseInt(strPageNumber);
            int pagzSize = Integer.parseInt(strPageSize);

            Sort sort = null;
            if ("auto".equals(sortType)) {
                sort = new Sort(Sort.Direction.DESC, "id");
            } else if ("title".equals(sortType)) {
                sort = new Sort(Sort.Direction.ASC, "title");
            }

            return new PageRequest(pageNumber - 1, pagzSize, sort);
        } else {
            return null;
        }
    }

    /**
     * 创建分页请求.
     */
    public static PageRequest buildPageRequest(String strPageNumber, String strPageSize, String sortType) {
        if (StringUtils.isNotBlank(strPageNumber) && StringUtils.isNotBlank(strPageSize)) {
            int pageNumber = Integer.parseInt(strPageNumber);
            int pagzSize = Integer.parseInt(strPageSize);

            Sort sort = null;
            if ("auto".equals(sortType)) {
                sort = new Sort(Sort.Direction.DESC, "id");
            } else if ("title".equals(sortType)) {
                sort = new Sort(Sort.Direction.ASC, "title");
            }

            return new PageRequest(pageNumber - 1, pagzSize, sort);
        } else {
            return null;
        }
    }

    /**
     * 检查两个时间点是否超过指定的毫秒数
     *
     * @param newDate
     * @param oldDate
     * @param milliseconds
     * @return
     */
    public static boolean checkDateIsMoreThanSpecialMilliseconds(Date newDate, Date oldDate, long milliseconds) {
        long cha = newDate.getTime() - oldDate.getTime();
        return cha > milliseconds;
    }

    public static long getPageCount(long recordCount, String strPageSize) {
        if (StringUtils.isBlank(strPageSize)) {
            return 0;
        } else {
            long pageSize = Long.parseLong(strPageSize);
            long pageCount = recordCount / pageSize;
            if (recordCount % pageSize > 0) {
                pageCount += 1;
            }
            return pageCount;
        }
    }

    /**
     * 获取date中的文本日期
     *
     * @param date
     * @return
     */
    public static String getStrDayFromDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        return format;
    }

    /**
     * 获取小时整点
     * 如:2018-08-17 01:00:00
     *
     * @param date
     * @return
     */
    public static Date getIntegralPointHour(Date date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
            return df.parse(df.format(date));
        } catch (ParseException ex) {
            return date;
        }
    }

    /**
     * 返回2018-08-17 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getIntegralPointDay(Date date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(df.format(date));
        } catch (ParseException ex) {
            return date;
        }
    }

    public static <T> Specification<T> getDaySpecification(Date queryDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = Util.getStrDayFromDate(queryDate);
            Date date = dateFormat.parse(strDate);
            Specification<T> spec = new Specification<T>() {
                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), date));

                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    Date nextDay = c.getTime();
                    predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            return spec;
        } catch (ParseException ex) {
            return null;
        }
    }

    public static <T> Specification<T> getHourSpecification(Date queryDate) {
        Date date = getIntegralPointHour(queryDate);
        Specification<T> spec = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), date));

                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.HOUR_OF_DAY, 1);
                Date nextDay = c.getTime();
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return spec;
    }

    public static Date getPreDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        Date preDay = c.getTime();
        return preDay;
    }

    public static Date getPre48H(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, -48);
        Date preDay = c.getTime();
        return preDay;
    }


    /**
     * 判断是否为6位整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (str.length() != 6){
                return false;
            }
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     *  将date类型转化为yyyyMMdd格式的字符串
     *
     */
    public static String dateToStringYMDHMS(Date date)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String result = "";
        if(date != null) {
            result = sdf.format(date);
        }
        return result;
    }

    /**
     * 获取今天整点日期
     *
     * @param
     * @return
     */
    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        String result = format.format(today)+":00";
        return result;
    }

}

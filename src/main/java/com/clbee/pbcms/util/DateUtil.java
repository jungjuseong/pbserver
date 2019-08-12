package com.clbee.pbcms.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
    // ���ϻ��
    public final static int SUN = 1;
    public final static int MON = 2;
    public final static int TUE = 3;
    public final static int WEN = 4;
    public final static int THU = 5;
    public final static int FRI = 6;
    public final static int SAT = 7;
    public final static int HOLIDAY = 9;

    // �� ����������
    public final static int[] endDay = {
                                           31, 28, 31, 30, 31, 30, 31, 31, 30,
                                           31, 30, 31
                                       };
    private static final String[] day = { "��", "��", "ȭ", "��", "��", "��", "��" };


    /**
     * YYYYMMDD ����� ��¥ String�� Calendar�� ������� ��ȯ
     *
     * @param date_Y4MD ��¥
     * @return calendar
     */
    public static Calendar getCalendar(String date)
    {
        Calendar calendar = Calendar.getInstance();
        long iDate = 0;

        try
        {
            iDate = Long.parseLong(date);
        }
        catch(Exception e){}

        int year = (int)(iDate / 10000L);
        int month = (int)((iDate % 10000L) / 100);
        int day = (int)(iDate % 100);
        calendar.set(year, month - 1, day);

        return calendar;
    }







    /**
     * ���˿� ��� �ش� ��¥ ���� �����´�.
     * @author Administrator
     * @param format(yyyy, MM, dd, hh, mm, ss, SSS)
     * @return
     * @date   2007. 05. 17
     */
    public static String getDate(String format) {
        SimpleDateFormat sfmt = new SimpleDateFormat(format);

        return sfmt.format(new Date());
    }

    /**
     * �⺻���� ��¥ ���ڿ��� �����´�.(yyyy-MM-dd)
     * @author Administrator
     * @return
     * @date   2007. 05. 17
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }






    /**
     * �⺻���� ��¥ ���ڿ��� �����´�.(yyyy-MM-dd)
     * @author Administrator
     * @return
     * @date   2007. 05. 17
     */
    public static String getDateTime() {
        return getDate("yyyy-MM-dd hh:mm:ss");
    }

    /**
     * �⺻���� �ð� ���ڿ��� �����´�.(hh:MM:ss)
     * @author Administrator
     * @return
     * @date   2007. 05. 17
     */
    public static String getTime() {
        return getDate("hh:mm:ss");
    }

    public static String getHour() {
        return getDate("hh");
    }

    public static String getMinute() {
        return getDate("mm");
    }





    /**
     * ��¥�� �ش��ϴ� ������ ����
     *
     * @param date ��¥
     * @return 1:�Ͽ���, 2:�����, ... , 7:�����
     */
    public static int getDayOfWeekNo(String date) {
        return getCalendar(date).get(Calendar.DAY_OF_WEEK);
    }

    /**
     * ��¥�� �ش��ϴ� ������ ����
     *
     * @param date ��¥
     * @return 1:�Ͽ���, 2:�����, ... , 7:�����
     */
    public static String getDayOfWeek(String date) {

        int dayNo = getDayOfWeekNo(date);

        String week = day[dayNo];

        return week;
    }





    /**
     * ������ ��¥���� ������ �� �� ��ŭ ���ϰų� �� ��¥(Date) �� ��ȯ�Ѵ�.
     *
     * @param strDate ��¥ (��: 2004-03-15,  2004-03-15 12:45:59)
     * @param int ���ϰų� ������ �ϴ� �� ��
     * @return ������ ��¥���� ������ �� �� ��ŭ ���ϰų� �� ��¥(Date) Object
     * @exception Exception
     */
    public static Date dateDiff(String strDate, int days) throws Exception {
        SimpleDateFormat sdf = null;
        Calendar calendar = new GregorianCalendar();
        Date date = null;

        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;

        String deli = "";

        //���� delimeter�� ���������� �ľ�
        if(strDate.indexOf(".")>-1) {
            deli = ".";
        } else if(strDate.indexOf("-")>-1) {
            deli = "-";
        }

        strDate = DateUtil.getParseDateString(strDate, "-");

        if(strDate.length() == 10 ) {
            sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA);
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.KOREA);
        }

        try {
            Date tmpDate = sdf.parse(strDate);
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            year = Integer.parseInt(strDate.substring(0,4));
            month = Integer.parseInt(strDate.substring(5,7));
            day = Integer.parseInt(strDate.substring(8,10));
            hour = Integer.parseInt(strDate.substring(11,13));
            minute = Integer.parseInt(strDate.substring(14,16));
            second = Integer.parseInt(strDate.substring(17,19));

        } catch(Exception e) {

            hour = 0;
            minute = 0;
            second = 0;
            //    throw new Exception(e.getMessage()+"Here");
        }

        calendar.set(year, month-1, day + days, hour, minute, second);

        try {
            date = calendar.getTime();
        }catch(Exception e) {}

        return date;
    }

    /**
     * �Է��� ��¥(��� yyyy-MM-dd)�������� +,- �� ���� ��¥�� ǥ���Ѵ�.
     *
     * @param strDate ���س�¥.(null�� ��� ���� ��¥�� �ȴ�.)
     * @param days   ����,�Ǵ� ������ ��
     * @param format  ��� ��� ��¥ ����
     * @return  +,- �� ���� ��¥�� ǥ���ϴ� ���ڿ�.
     * @exception Exception
     */
    public static String dateDiff(String strDate, int days, String format) throws Exception
    {
        Date dt = dateDiff(strDate, days);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String sDateNTime = sdf.format(dt);

        return sDateNTime;
    }

    /**
     * sDateNTimeForm ����� ���� �ð� ���ڿ��� ��´�.
     * @param sDateNTimeForm ����ð� Formatting ���ڿ�(�⵵:yyyy ��:MM ��:dd ��:HH ��:mm ��:ss)<br>
     * example - yyyyMMddHHmmss, yyyy�� MM�� dd�� HH�� mm�� ss��
     * @return ���� �ð� ���ڿ�
     */
    public static String getDateNTimeByForm(String sDateNTimeForm)
    {
        Calendar Today = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(sDateNTimeForm);
        String sDateNTime = sdf.format(Today.getTime() );

        return sDateNTime;
    }


    /**
     * �ش���� ������ ���� ����
     * @param year ����
     * @param month ��
     * @return ������ ����
     * @author Sungju Kang(sonaki@kftc.or.kr)
     * @since 2004-03-10
     */
    public static int getLastDayOfMonth(int year,
                                        int month) {
        if (month == 2) {
            if ((((year % 4) == 0) && ((year % 100) != 0)) ||
                    ((year % 400) == 0)) {
                return 29;
            } else {
                return 28;
            }
        } else {
            return endDay[month - 1];
        }
    }

    /**
     * �ش���� ������ ���� ����
     * @param date_Y4M ����(YYYYMM)
     * @return ������ ����
     * @author Sungju Kang(sonaki@kftc.or.kr)
     * @since 2004-03-10
     */
    public static int getLastDayOfMonth(String dateY4M) {
        return getLastDayOfMonth(Integer.parseInt(dateY4M.substring(0, 4)),
                                 Integer.parseInt(dateY4M.substring(4, 6)));
    }





    /**
     * SimpleDateFormat �� ����(�ѱ�����)
     * @param datePattern pattern
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getDateFormat(String datePattern) {
        SimpleDateFormat format = new SimpleDateFormat(datePattern,
                                                       new Locale("ko", "KOREA"));

        return format;
    }

    public static SimpleDateFormat getDateFormat() {
        return getDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat getDateTimeFormat() {
        return getDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat getDateTimeMilliFormat() {
        return getDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    }

    public static SimpleDateFormat getTimeFormat() {
        return getDateFormat("HH:mm:ss");
    }

    public static SimpleDateFormat getTimeMilliFormat() {
        return getDateFormat("HH:mm:ss SSS");
    }




    /**
     *
     * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
     *
     * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
     * @return formatted string representation of current day and time with  your pattern.
     */
    public static int getNumberByPattern(String pattern) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern,
                                                                              java.util.Locale.KOREA);
        String dateString = formatter.format(new java.util.Date());

        return Integer.parseInt(dateString);
    }

    /**
     * ���糯¥�� �⵵�� ���ϴ� Method
     *
     * @param
     * @exception
     * @author
     */
    public static int getYear() {
        return getNumberByPattern("yyyy");
    }

    /**
     * ���糯¥�� ���� ���ϴ� Method
     *
     * @param
     * @exception
     * @author
     */
    public static int getMonth() {
        return getNumberByPattern("MM");
    }

    /**
    * ���糯¥�� ���ڸ� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
    public static int getDay() {
        return getNumberByPattern("dd");
    }

    /**
    * ���� ������ ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
    public static String getDayOfWeek() {
        Calendar c = Calendar.getInstance();

        return day[c.get(java.util.Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * �Էµ� ���ڿ��� Ư�� �������� ��ȯ�Ͽ� ����
     *
     * @param dateTime
     * @param pattern
     * @return
     * @date   2007. 05. 18
     */
    public static String getParseDateString(String dateTime,
                                            String pattern) {
        if (dateTime != null && !"".equals(dateTime)) {

            dateTime = dateTime.replaceAll("[.]", "");
            dateTime = dateTime.replaceAll("[-]", "");
            dateTime = dateTime.replaceAll("[/]", "");

            if(dateTime.length()>=8) {
                String year = dateTime.substring(0, 4);
                String month = dateTime.substring(4, 6);
                String day = dateTime.substring(6, 8);

                return year + pattern + month + pattern + day;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
       * �־��� �⵵�� ���������� ���ϴ� Method
       *
       * @param int year
       * @exception
       * @author
       */
    public static boolean checkEmbolism(int year) {
        int remain = 0;
        int remain_1 = 0;
        int remain_2 = 0;

        remain = year % 4;
        remain_1 = year % 100;
        remain_2 = year % 400;

        // the ramain is 0 when year is divided by 4;
        if (remain == 0) {
            // the ramain is 0 when year is divided by 100;
            if (remain_1 == 0) {
                // the remain is 0 when year is divided by 400;
                if (remain_2 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * �־��� ��,���� �ϼ��� ���ϴ� Method
     *
     * @param    String year, String month
     * @exception
     * @author
     */
    public static int getMonthDate(String year,
                                   String month) {
        int[] dateMonth = new int[12];

        dateMonth[0] = 31;
        dateMonth[1] = 28;
        dateMonth[2] = 31;
        dateMonth[3] = 30;
        dateMonth[4] = 31;
        dateMonth[5] = 30;
        dateMonth[6] = 31;
        dateMonth[7] = 31;
        dateMonth[8] = 30;
        dateMonth[9] = 31;
        dateMonth[10] = 30;
        dateMonth[11] = 31;

        if (checkEmbolism(Integer.parseInt(year))) {
            dateMonth[1] = 29;
        }

        int day = dateMonth[Integer.parseInt(month) - 1];

        return day;
    }
}

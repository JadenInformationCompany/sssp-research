package com.szhis.frsoft.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.szhis.frsoft.common.FRUtils;
import com.szhis.frsoft.common.exception.Exceptions;

public class DateUtils {
	private static final String DateUitls_dateFmt_error = "DateUitls.dateFmt.error";
	public static final String DATE_FORMAT_SHORT_DEFAULT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_LONG_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	
	/** 存放不同的日期模板格式的DateFormat的Map */
	private static final ThreadLocal<Map<String, SimpleDateFormat>> dateFormatThreadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>(){
		@Override
		protected Map<String, SimpleDateFormat> initialValue() {
			return new HashMap<String, SimpleDateFormat>(2);
		}		
	};
	
	private static final ThreadLocal<Calendar> calendarThreadLocal = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			return Calendar.getInstance();
		}
	};
	
	public static final Date DEFAULT_BEGIN_DATE = DateUtils.parse("2013-12-08", DATE_FORMAT_SHORT_DEFAULT); 
	public static final Date DEFAULT_END_DATE = DateUtils.parse("9999-12-31", DATE_FORMAT_SHORT_DEFAULT);
	public static final Date DEFAULT_END_DATETIME = DateUtils.parse("9999-12-31 23:59:59", DATE_FORMAT_LONG_DEFAULT);
	
	private static Calendar getCalendar() {
		return calendarThreadLocal.get();
	}
	 
	/**
	 * 返回一个ThreadLocal的DateFormat,同一种日期格式每个线程只会new一次DateFormat
	 * 
	 * @param pattern
	 * @return
	 */
	private static SimpleDateFormat getDateFormat(final String pattern) {
		Map<String, SimpleDateFormat> dateFormatMap = dateFormatThreadLocal.get();
		SimpleDateFormat dateFormat = dateFormatMap.get(pattern);
		if(dateFormat == null) {
			dateFormat = new SimpleDateFormat(pattern);
			dateFormatMap.put(pattern, dateFormat);
		}
		return dateFormat;
	}
    
    private static Date inc(Date date, int dateType, int interval) {  
    	if(date == null)
    		return null;
        Calendar calendar = getCalendar();  
        calendar.setTime(date);  
        calendar.add(dateType, interval);  
        return calendar.getTime();   
    } 
    
    
    /** 
     * 获取日期中的某个部分整数，如获取日期中的年份、月份、多少号，几点钟，多少分，多少秒。
     * @param date 日期 
     * @param dateType 日期格式 
     * @return 数值 
     */  
    private static int getDatePartNumber(Date date, int dateType) {
    	if(date == null)
    		return 0; 
        Calendar calendar = getCalendar();  
        calendar.setTime(date);  
        return calendar.get(dateType);  
    }  
  
    private static Date updateMonthAndDay(Date date, int month, int day) {
    	if(date == null)
    		return null;
        Calendar calendar = getCalendar();  
        calendar.setTime(date);  
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();    	
    }  
    
    private static Date updateDay(Date date, int day) {
    	if(date == null)
    		return null;
        Calendar calendar = getCalendar();  
        calendar.setTime(date);  
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();    	
    }
    
    /**
     * 
     * @param date
     * @param pattern 可省略参数，默认格式yyyy-MM-dd HH:mm:ss
     * @return
     */    
    public static String format(Date date, String... pattern) {
    	String format;
    	if(pattern.length == 0)
    		format = DATE_FORMAT_LONG_DEFAULT;
    	else {
    		format = pattern[0];
    	}
    	return getDateFormat(format).format(date);    	
    }
    
    public static void checkDateFmt(String pattern) {
    	try {
        	format(new Date(), pattern);      		
    	} catch(IllegalArgumentException e) {
    		throw new IllegalArgumentException(FRUtils.getMessage(DateUitls_dateFmt_error) + ";" + e.getMessage());
    	}
    }    
    /**
     * 
     * @param date
     * @param pattern 可省略参数，默认格式
     * @return
     */
    public static String formatDate(Date date) {
    	return format(date, DATE_FORMAT_SHORT_DEFAULT);   	
    }
    
    public static String dateToStr(Date date) {
    	return formatDate(date);
    }
    
    public static String dateTimeToStr(Date date) {
    	return format(date, DATE_FORMAT_LONG_DEFAULT);
    }
    
    public static Date parse(String dateStr, String... pattern) {
    	String sPattern;
    	if(pattern.length == 0) {
    		int stlen = dateStr.length();
        	if(stlen == 10)
        		sPattern = DateUtils.DATE_FORMAT_SHORT_DEFAULT;
        	else if(stlen < 10) {
        		String[] strArr = dateStr.split("-");
        		if(strArr.length != 3) {
        			throw new IllegalArgumentException(FRUtils.getMessage(DateUitls_dateFmt_error));
        		}      		
        		if(strArr[1].length() == 1) {
        			strArr[1] = "0" + strArr[1];
        		}
        		if(strArr[2].length() == 1) {
        			strArr[2] = "0" + strArr[2];
        		}  
        		dateStr = strArr[0] + "-" + strArr[1] + "-" + strArr[2];
        		sPattern = DateUtils.DATE_FORMAT_SHORT_DEFAULT;
        	} else
        		sPattern = DateUtils.DATE_FORMAT_LONG_DEFAULT;    		
    	} else
    		sPattern = pattern[0];    	
        try {
			return getDateFormat(sPattern).parse(dateStr);
		} catch (ParseException e) {
			throw Exceptions.unchecked(e);
		}
    }	
    
    public static Date strToDate(String str) {    	
    	if((str != null) && (!str.equals(""))){
        	return parse(str, DATE_FORMAT_SHORT_DEFAULT);     		
    	}
    	return null;    	
    }
    
    public static Date strToDateTime(String str) {
    	if((str != null) && (!str.equals(""))){
        	if (str.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
				str = str + " 00:00:00";
			}   			
			return parse(str, DATE_FORMAT_LONG_DEFAULT);     		
    	}
    	return null;
    }    
    
  //返回当前日期，不带时间
    public static Date getDate(){
		return getDate(new Date());
    }
    
    public static Date getDate(Date date){
        Calendar calendar = getCalendar();  		
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);		
		return calendar.getTime();
    }
    
    public static Date getTime(){
		return getTime(new Date());
    }    
    
    public static Date getTime(Date date){
        Calendar calendar = getCalendar();  		
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, 0);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DATE, 0);
		return calendar.getTime();
    }

    
    public static Date incYear(Date date, int years) {  
        return inc(date, Calendar.YEAR, years);  
    } 
    
    public static Date incMonth(Date date, int months) {  
        return inc(date, Calendar.MONTH, months);  
    } 
    
    public static Date incDay(Date date, int days) {
    	return inc(date, Calendar.DAY_OF_MONTH, days);   	  
    }      
    
    public static Date incHour(Date date, int hours) {
    	return inc(date, Calendar.HOUR_OF_DAY, hours);   	  
    }   
    
    public static Date incMinute(Date date, int minutes) {
    	return inc(date, Calendar.MINUTE, minutes);   	  
    }   
    
    public static Date incSecond(Date date, int seconds) {
    	return inc(date, Calendar.SECOND, seconds);   	  
    }     
    
    public static Date today(){
    	return new Date();
    }
    
    public static Date yesterday(){
    	return incDay(new Date(), -1);
    }   
    
    public static Date tomorrow(){
    	return incDay(new Date(), 1);
    }  

    public static int yearOf(Date date) {
    	return getDatePartNumber(date, Calendar.YEAR);
    }
    
    public static int monthOf(Date date) {
    	return getDatePartNumber(date, Calendar.MONTH) + 1;
    }    
    
    public static int dayOf(Date date) {
    	return getDatePartNumber(date, Calendar.DATE);
    }   
    
    public static int hourOf(Date date) {
    	return getDatePartNumber(date, Calendar.HOUR_OF_DAY);
    }    
    
    public static int minuteOf(Date date) {
    	return getDatePartNumber(date, Calendar.MINUTE);
    }     
    
    public static int secondOf(Date date) {
    	return getDatePartNumber(date, Calendar.SECOND);
    }     
    
   //返回周几，该日期是该星期的第几天, 返回值为 1..7，其中 1 表示星期一，而 7 表示星期日
    public static int dayOfTheWeek(Date date) {
    	int i = getDatePartNumber(date, Calendar.DAY_OF_WEEK);	
    	i = i == 1 ? 7 : i-1;
    	 return i;
    }
    
	public static String getChineseWeek(Date date) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
        Calendar calendar = getCalendar();  		
		calendar.setTime(date); 		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayOfWeek - 1];

	}
	
	/**
	 * setFirstDayOfWeek用于把星期几定位一周的第一天，JAVA默认是以星期天作为一周的开始。
	 * 不同区域对于一周的定义不同，有些地区把星期天作为一周的开始，星期一在星期天的后面
	 * 中国是以星期一作为开始，星期天作为结束，所以如果推算一周星期天，一种算法需要往前推算
	 * 一种算法需要往后推算
	 * 但注意setFirstDayOfWeek不会影响get(Calendar.DAY_OF_WEEK)返回，如果当前是星期一
	 * get(Calendar.DAY_OF_WEEK)永远返回2。setFirstDayOfWeek影响DAY_OF_WEEK的计算，
	 * 比如获取某个日期星期天，如果一周的第一天是星期一，则算星期天会往后的日期推算，
	 * 如果设置一周的第一天是星期天，当前是星期一，则JAVA算星期天会往前日期推算。
	 */ 
    public static Date getWeekDate(Date date, int weekInterval, int weekDay) {
        Calendar calendar = getCalendar();  		
		calendar.setTime(date); 
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.add(Calendar.DATE, weekInterval*7);
		weekDay = weekDay == 7 ? Calendar.SUNDAY : weekDay + 1;
		calendar.set(Calendar.DAY_OF_WEEK, weekDay);
		return calendar.getTime();
    }
    
    public static Date thisWeekDate(Date date, int weekDay) {
		return getWeekDate(date, 0, weekDay);    	
    }
    
    public static Date thisWeekDate(int weekDay) {
    	return getWeekDate(new Date(), 0, weekDay);
    }
    
    public static Boolean isSameWeek(Date date1, Date date2) {
        Calendar calendar = getCalendar();  		
		calendar.setTime(date1);	
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		calendar.setTime(date2);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, weekDay);
		return daysBetween(date1, calendar.getTime()) == 0;
    }
    
    public static Date startOfTheYear(Date date) {   	
        return updateMonthAndDay(date, Calendar.JANUARY, 1);   
    }    
    
    public static Date endOfTheYear(Date date) {   	 
        return updateMonthAndDay(date, Calendar.DECEMBER, 31);   	
    }     
    
    //季度第一天
	public Date startOfTheQuarter(Date date) {
    	if(date == null)
    		return null;
        Calendar calendar = getCalendar();  		
		calendar.setTime(date);
		int mon = calendar.get(Calendar.MONTH);
		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH)
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
		else if (mon >= Calendar.APRIL && mon <= Calendar.JUNE)
			calendar.set(Calendar.MONTH, Calendar.APRIL);
		else if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) 
			calendar.set(Calendar.MONTH, Calendar.JULY);
		else
			calendar.set(Calendar.MONTH, Calendar.OCTOBER);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}    
	
	//季度最后一天
	public Date endOfTheQuarter(Date date) {
    	if(date == null)
    		return null;
        Calendar calendar = getCalendar();  
		int mon = calendar.get(Calendar.MONTH);
		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {
			calendar.set(Calendar.MONTH, Calendar.MARCH);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
		}
		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {
			calendar.set(Calendar.MONTH, Calendar.JUNE);
			calendar.set(Calendar.DAY_OF_MONTH, 30);
		}
		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {
			calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 30);
		}
		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
		}
		return calendar.getTime();
	}	
	
    public static Date startOfTheMonth(Date date) {
    	return updateDay(date, 1);   	
    }   	
    
    public static Date endOfTheMonth(Date date) {
    	if(date == null)
    		return null;
        Calendar calendar = getCalendar();  
        calendar.setTime(date);  
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();		
    }   	
    
    public static int yearsBetween(Date date1, Date date2) {
    	return Math.abs(yearOf(date1) - yearOf(date2));
    }
    
    public static int yearsBetween(Date date) {
    	return yearsBetween(new Date(), date);
    }
    
    public static int monthsBetween(Date date1, Date date2) {
    	int month1, year1, month2, year2;
    	//注意这里getCalendar是线程单例
    	Calendar calendar = getCalendar();
    	
    	calendar.setTime(date1);
    	month1 = calendar.get(Calendar.MONTH);
    	year1 = calendar.get(Calendar.YEAR);
    	
    	calendar.setTime(date2);
    	month2 = calendar.get(Calendar.MONTH);
    	year2 = calendar.get(Calendar.YEAR);
    	
		int months = (year1 - year2) * 12 + month1 - month2;    	
    	return Math.abs(months);
    }
    
    public static int monthsBetween(Date date) {
    	return monthsBetween(new Date(), date);
    }    
    
    public static int daysBetween(Date date1, Date date2) {
    	Date d1 = getDate(date1);
    	Date d2 = getDate(date2);
    	long time = Math.abs(d1.getTime() - d2.getTime()); 
    	return (int) (time / (24 * 60 * 60 * 1000)); 
    }
    
    public static int daysBetween(Date date) {
    	return daysBetween(new Date(), date); 
    }
    
    
    public static int hoursBetween(Date date1, Date date2) {
    	long time = Math.abs(date1.getTime() - date2.getTime()); 
    	return (int) (time / (60 * 60 * 1000)); 
    }
    
    public static int hoursBetween(Date date) {
    	return hoursBetween(new Date(), date); 
    }
    
    public static int minutesBetween(Date date1, Date date2) {
    	long time = Math.abs(date1.getTime() - date2.getTime()); 
    	return (int) (time / (60 * 1000));
    }
    
    public static int minutesBetween(Date date) {
    	return minutesBetween(new Date(), date); 
    }    
    
    
    public static int secondsBetween(Date date1, Date date2) {
    	long time = Math.abs(date1.getTime() - date2.getTime());; 
    	return (int) (time / (1000)); 
    }
    
    public static int secondsBetween(Date date) {
    	return secondsBetween(new Date(), date); 
    }
    
    public static boolean isToday(Date date) {
    	Date d1 = getDate(new Date());
    	Date d2 = getDate(date);   
    	return (d1.getTime() - d2.getTime()) == 0;
    }
    
    public static boolean isLeapYear(Date date) {
    	int year = yearOf(date);
    	return (year%4 == 0 && year%100 !=0)||(year%400 == 0);    	
    }
    

	/**
	 * 计算医学年龄
	 * @param dateOfBirth
	 * @return
	 */
	public static String getMedicalAge(Date dateOfBirth) {
		Date now = new Date();
		Integer nowYear = DateUtils.yearOf(now);
		Integer nowMonth = DateUtils.monthOf(now);
		Integer nowDay = DateUtils.dayOf(now);
		
		Integer birthYear = DateUtils.yearOf(dateOfBirth);
		Integer birthMonth = DateUtils.monthOf(dateOfBirth);
		Integer birthDay = DateUtils.dayOf(dateOfBirth);
		
		Integer yxnlDay;
		Integer yxnlMonth;
		Integer yxnlYear;
		
		if(nowDay.intValue()>=birthDay.intValue()) {
			yxnlDay = nowDay - birthDay;
		}else {
			nowDay += 30;
			yxnlDay = nowDay - birthDay.intValue();
			nowMonth -= 1;
		}
		
		if(nowMonth>=birthMonth) {
			yxnlMonth = nowMonth - birthMonth;
		}else {
			nowMonth += 12;
			yxnlMonth = nowMonth - birthMonth;
			nowYear -= 1;
		}
		
		yxnlYear = nowYear - birthYear;
		//                     00Y 00M 00D 00H
		return String.format("%03dY%02dM%02dD%02dH", yxnlYear, yxnlMonth, yxnlDay, 0);		
    }

}

package luckyweb.seagull.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateLib {
	/**
	 * 将'yyyyMMddHHmmss' 转成 yyyy-mm-dd hh:mm:ss
	 * 
	 * @param source
	 * @return
	 */
	public static String format(String source) throws Exception {
		if (source != null && source.length() == 14) {
			return source.substring(0, 4) + "-" + source.substring(4, 6) + "-"
					+ source.substring(6, 8) + " " + source.substring(8, 10)
					+ ":" + source.substring(10, 12) + ":"
					+ source.substring(12, 14);
		} else {
			throw new Exception("传入的字符为" + source
					+ "不能正常转化为yyyy-mm-dd hh:mm:ss形式");
		}
	}

	/**
	 * 将'yyyy-MM-dd'转成'yyyyMMdd'
	 * 
	 * @param source
	 * @return
	 */
	public static String format1(String source) {
		return source.substring(0, 4) + source.substring(5, 7)
				+ source.substring(8, 10);
	}

	public static String format2(String source) {
		return source.substring(2, 4) + source.substring(5, 7)
				+ source.substring(8, 10);
	}

	/**
	 * 取昨天的时间，并以format的形式返回
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String yd_format(String format) {
		String tar;
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DATE);
		date--;
		c.set(Calendar.DATE, date);
		Date yes_day = new Date(c.getTimeInMillis());
		tar = (new SimpleDateFormat(format)).format(yes_day);
		return tar;
	}

	/**
	 * 取前天日期，并以format传入的形式返回
	 * 
	 * @param format
	 * @return
	 */
	public static String befor_yd_format(String format) {
		String tar;
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DATE);
		date = date - 2;
		c.set(Calendar.DATE, date);
		Date yes_day = new Date(c.getTimeInMillis());
		tar = (new SimpleDateFormat(format)).format(yes_day);
		return tar;
	}

	/**
	 * 取前N天日期,并以format传入的形式返回
	 * 
	 * @param format
	 * @return
	 */
	public static String befor_Nd_format(String format, int n) {
		String tar;
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DATE);
		date = date - n;
		c.set(Calendar.DATE, date);
		Date yes_day = new Date(c.getTimeInMillis());
		tar = (new SimpleDateFormat(format)).format(yes_day);
		return tar;
	}

	/**
	 * 取某一天第二天的日期
	 * 
	 * @param yd
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String to_format(String yd, String format) throws Exception {
		yd = yd.replaceAll("-", "");
		String ad;
		SimpleDateFormat sf = new SimpleDateFormat();
		sf.applyPattern(format);
		java.util.Date y_date = sf.parse(yd);
		long myTime = (y_date.getTime() / 1000) + 1 * 60 * 60 * 24;// -1表示前一天，+1表示后一天，依次类推
		y_date.setTime(myTime * 1000);
		ad = sf.format(y_date);
		return ad;
	}

	public static String today(String format) {
		java.util.Date date = new java.util.Date();
		String tar = new SimpleDateFormat(format).format(date);
		return tar;
	}

	public static String tomorrow(String today, String format) throws Exception {
		String ad;
		SimpleDateFormat sf = new SimpleDateFormat();
		sf.applyPattern(format);
		java.util.Date y_date = sf.parse(today);
		long myTime = (y_date.getTime() / 1000) + 1 * 60 * 60 * 24;// -1表示前一天，+1表示后一天，依次类推
		y_date.setTime(myTime * 1000);
		ad = sf.format(y_date);
		return ad;
	}

	/**
	 * 获得某个月的第一天
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static java.util.Date firstDayOfMonth(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 获得某周的一天
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static String weekday(String date) {
		java.util.Date nowDate = java.sql.Date.valueOf(date);
		java.text.SimpleDateFormat bartDateFormat = new java.text.SimpleDateFormat(
				"EEE");
		String week = bartDateFormat.format(nowDate).trim();
		return week;
	}

	/**
	 * 比较时间
	 * 
	 * @param DATE1
	 *            开始时间
	 * @param DATE2
	 * @return 
	 */
	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date dt1 = df.parse(DATE1);
			java.util.Date dt2 = df.parse(DATE2);
			if (dt1.getTime() >= dt2.getTime()) {
				//System.out.println("以第【1】个时间为准");
				return 1;
			} 
				//System.out.println("以第【2】个时间为准");
				return 2;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		//System.out.print(weekday("2012-12-10"));
		
		System.out.println( "12345678".substring(5,8));
		
		String now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date().getTime()); 
		System.out.println("2015-05-10 09:00:00");
		System.out.println(now);
		int res=DateLib.compare_date("2015-05-10 09:00:00", now);
		System.out.println(res);
		
	}

}

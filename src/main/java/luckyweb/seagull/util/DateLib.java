package luckyweb.seagull.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
public class DateLib {
	/**
	 * 将'yyyyMMddHHmmss' 转成 yyyy-mm-dd hh:mm:ss
	 * 
	 * @param source
	 * @return
	 */
	public static String format(String source) throws Exception {
		int sourcelength=14;
		if (source != null && source.length() == sourcelength) {
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
	public static String ydFormat(String format) {
		String tar;
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DATE);
		date--;
		c.set(Calendar.DATE, date);
		Date yesDay = new Date(c.getTimeInMillis());
		tar = (new SimpleDateFormat(format)).format(yesDay);
		return tar;
	}

	/**
	 * 取前天日期，并以format传入的形式返回
	 * 
	 * @param format
	 * @return
	 */
	public static String beforYdFormat(String format) {
		String tar;
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DATE);
		date = date - 2;
		c.set(Calendar.DATE, date);
		Date yesDay = new Date(c.getTimeInMillis());
		tar = (new SimpleDateFormat(format)).format(yesDay);
		return tar;
	}

	/**
	 * 取前N天日期,并以format传入的形式返回
	 * 
	 * @param format
	 * @return
	 */
	public static String beforNdFormat(String format, int n) {
		String tar;
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DATE);
		date = date - n;
		c.set(Calendar.DATE, date);
		Date yesDay = new Date(c.getTimeInMillis());
		tar = (new SimpleDateFormat(format)).format(yesDay);
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
	public static String toFormat(String yd, String format) throws Exception {
		yd = yd.replaceAll("-", "");
		String ad;
		SimpleDateFormat sf = new SimpleDateFormat();
		sf.applyPattern(format);
		java.util.Date yDate = sf.parse(yd);
		// -1表示前一天，+1表示后一天，依次类推
		long myTime = (yDate.getTime() / 1000) + 1 * 60 * 60 * 24;
		yDate.setTime(myTime * 1000);
		ad = sf.format(yDate);
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
		java.util.Date yDate = sf.parse(today);
		// -1表示前一天，+1表示后一天，依次类推
		long myTime = (yDate.getTime() / 1000) + 1 * 60 * 60 * 24;
		yDate.setTime(myTime * 1000);
		ad = sf.format(yDate);
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
	public static int compareDate(String date1, String date2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date dt1 = df.parse(date1);
			java.util.Date dt2 = df.parse(date2);
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

	/**
	 * 计算入参日期到今天的间隔天数
	 * @param beforedate
	 * @return
	 * @throws ParseException 
	 */
	public static int getDays(String beforedatestr) throws ParseException {
		if(null==beforedatestr){
			return 0;
		}
		String todaystr = DateLib.today("yyyy-MM-dd");  //第二个日期
		//算两个日期间隔多少天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = format.parse(todaystr);
		Date beforedate = format.parse(beforedatestr);
		int days = (int) ((today.getTime() - beforedate.getTime()) / (1000*3600*24));
		return days;
	}
	
	public static void main(String[] args) {
	}

}

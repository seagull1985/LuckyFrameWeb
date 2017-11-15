package luckyweb.seagull.util;

import java.sql.*;
import java.text.*;
import java.util.Calendar;


public class DateUtil {
	
	/**
	 * ���������ת����yyyy.MM.dd��ʽ���ַ�
	 * @param d �������
	 * @return yyyy.MM.dd��ʽ���ַ��������Ϊnull����ô���ؿ��ַ�""
	 */
	public static String format(Date d) {
		String str;
		if (d != null) {
			str = (new SimpleDateFormat("yyyy-MM-dd")).format(d);
		} else {
			str = "";
		}
		return str;
	}

	public static String format(Date d,String pattern) {
		String str;
		if (d != null) {
			str = (new SimpleDateFormat(pattern)).format(d);
		} else {
			str = "";
		}
		return str;
	}

	
	public static Date format(String str,String pattern) {
		Date date;
		try {
			date = new Date((new SimpleDateFormat(pattern)).parse(str).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			date = null;
		}
		return date;
	}
	/**
	 * ���ַ����͵�����ת��Ϊ������
	 * @param str yyyy.MM.dd����yyyy-mm-dd��ʽ���ַ�
	 * @return �ַ��Ӧ�����ڣ�����ַ��ǺϷ������ڣ�����null
	 */
	public static Date format(String str) {
		Date d;
		if (str != null) {		    
			try {
				d = Date.valueOf(str.replace('.','-')); 
			} catch (Exception e) {
			    d = null;
			}
		} else {
			d = null;
		}
		return d;
	}

	/**
	 * ʱ��������ת����yyyy.MM.dd HH:mm:ss��ʽ���ַ�
	 * @param t ʱ������
	 * @return yyyy.MM.dd HH:mm:ss��ʽ���ַ����ʱ���Ϊnull����ô���ؿ��ַ�""
	 */
	public static String format(Timestamp t) {
		String str;
		if (t != null) { 
			str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(t);
		} else {
			str = "";
		}
		return str;
	}
	
	/**
	 * ȡ�õ�ǰʱ�䣬java.Timestamp��ʽ
	 * @return ��ǰʱ��
	 */
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * ȡ�õ�ǰ���ڣ�java.sql.Date��ʽ
	 * @return ��������
	 */
	public static Date today() {
		return new Date(System.currentTimeMillis());
	}
	//昨天
	public static Date yesterday() {
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DATE);
		date--;
		c.set(Calendar.DATE,date);
		return new Date(c.getTimeInMillis());
	}
	//前天
	public static Date beforeYesterday() {
		Calendar c = Calendar.getInstance();
		int date = c.get(Calendar.DATE);
		date=date-2;
		c.set(Calendar.DATE,date);
		return new Date(c.getTimeInMillis());
	}
	/**
	 * 返回两个日期相差的天数
	 * */
	public static long dateDiff(String d1, String d2){
		Date da1 = format(d1);
		Date da2 = format(d2);
		return dateDiff(da1,da2);
	}
	/**
	 * 返回两个日期相差的天数
	 * */
	public static long dateDiff(Date d1, Date d2){
		long n1 = d1.getTime();
		long n2 = d2.getTime();
		
		long diff = Math.abs(n1 - n2);
		diff /= 3600 * 1000 * 24;
		return diff;
	}
	
	/*
	 * 判断是否为当前月,返回真表示在最终历史库
	 */
	public static boolean isHistory(Date copitime)throws Exception{
		Calendar cal = Calendar.getInstance();
		boolean bol=false;
		Date d=today();
		long currenttime = d.getTime();
		long histime=copitime.getTime();
		cal.setTimeInMillis(currenttime);
		int f_year=cal.get(Calendar.YEAR);
		int f_month=cal.get(Calendar.MONTH);
		if(histime>currenttime)
			throw new Exception("日期选择有误。");
		cal.setTimeInMillis(histime);
		int s_year=cal.get(Calendar.YEAR);
		int s_month=cal.get(Calendar.MONTH);
		if(f_year!=s_year){
			return true;
		}
		if(f_month!=s_month)
			bol=true;
		return bol;
	}
	//判断是否为当前日
	public static boolean isBakDate(Date d) {
		Date today = Date.valueOf(DateUtil.today().toString());
		if (d.getTime() < today.getTime()) {
			return true;
		} else {
			return false;
		}

	}
	
	public static String format(Timestamp t, String pattern) {
		String str;
		if (t != null) { 
			str = (new SimpleDateFormat(pattern)).format(t);
		} else {
			str = "";
		}
		return str;
	}
	/*根据传进来的日期字符串，获取上一个年月yyyyMM*/
	public static String prMonth(String d)throws Exception
	{		
		java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(d);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE,1);
		int mn=c.get(Calendar.MONTH)-1;
		c.set(Calendar.MONTH, mn);
		return new SimpleDateFormat("yyyyMM").format(c.getTime());
	}
	/*
	 *  以当前时间按pattern指定的格式，格式化时间为字符串返回
	 * */
	public static String getCurrentTimeCharPattern(String pattern)throws Exception{
		return new SimpleDateFormat(pattern).format(new java.util.Date());
	}
	
	
	/**
	 * 计算两个日期相隔的小时数
	 * 
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public static final long diffHour(java.util.Date startdate,
			java.util.Date enddate) {

		long diff = (startdate.getTime() - enddate.getTime()) / (3600 * 1000l);
		return diff;

	}
	
	
	public static void main(String args[])throws Exception{
		

		
		
//	       BigDecimal b1 = new BigDecimal("14.145"); 
//	       BigDecimal b2 = new BigDecimal(1); 
//		   System.out.println(b1.divide(b2,2,BigDecimal.ROUND_HALF_UP));
			  
		   
//		Date today = Date.valueOf(DateLib.today().toString());
//		Calendar c = Calendar.getInstance();
//		int date = c.get(Calendar.DATE);
//		date=date-7;
//		c.set(Calendar.DATE,date);
//		System.out.println( new Date(c.getTimeInMillis()));
//		
//		int j=c.get(Calendar.DATE);
//		j=j-45;
//		c.set(Calendar.DATE,j);
//		System.out.println( new Date(c.getTimeInMillis()));
		//System.out.println(MD5Util.MD5Encode("20001001423011010111110yskjop120897technology2hdkf0sdfd"));
		
	}
}
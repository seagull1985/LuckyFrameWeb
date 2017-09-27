package luckyweb.seagull.util;


import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class StrLib {
	 private static final byte SPACE = 0x20, ZERO = 0x30;
	 public static final String CHARSET = "ISO-8859-1";

	
	private static StringTokenizer sto;
	private static StringBuffer buf;
	private static SimpleDateFormat format;
	public static String format(String str) {
		return (str == null ? "" : str);
	}


	public static String formatHtml(String str) {
		if (str == null) return "";
		str = str.replaceAll("<","&lt;");
		str = str.replaceAll(">","&gt;");
		str = str.replaceAll("  ","&nbsp;&nbsp;");
		str = str.replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;");
		str = str.replaceAll("\n","<br>");
		return str;
	}


	public static String formatSql(String str) {
		return str.replaceAll("'","''");
	}
	

	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}
	

	public static String toChinese(String str) {
		if (str == null) return null;
		try {
			return new String(str.getBytes("ISO8859_1"), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}


	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}
	

	public static String arrayToString(String[] arr,String separator) {
		int i,length;
		String str = null;
		if (arr != null) {
			length = arr.length;
			for (i = 0; i < length; i ++) {
				if (StrLib.isEmpty(arr[i])) continue;
				if (str == null) {
					str = arr[i];
				} else {
					str += separator + arr[i];
				}
			}
		}
		return str;
	}
	public static String conFo(String str)
	{
		if(str.equals("")){return "";}
		 sto=new StringTokenizer(str,"-");
		 buf=new StringBuffer();
		while(sto.hasMoreElements())
		{
			buf.append(sto.nextElement());
		}
		return buf.toString();

	}
	public static String getCurrenttime(String formatString)
	{
		format=new SimpleDateFormat(formatString);
		return format.format(new Date());
	}
	public static String getyestoday(String formatString)
	{
		Date today=new Date();
		format=new SimpleDateFormat(formatString);
		Date yestoday=new Date(today.getTime()-24*3600*1000);
		return format.format(yestoday);
	}
//	public static String convertDou(double b)
//	{
//	    String te=String.valueOf(b);
//	    if(te.substring(te.indexOf('.')).length()<=2)
//	    {
//	    	te+="0";
//	    }
//	    return te;
//	}
	public static String convertTime(String str)
	{
	    if(str==null)return "";
	    if(str.trim().length()<8)return str;
	    return (str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8)).trim();
	}
	/**
	 * 随机产生一个6位的数字组合，作为密码
	 * @return
	 */
	public static String generatePwd() {
		StringBuffer pwd = new StringBuffer(6);
		java.util.Random r = new java.util.Random();
		for (int i = 0;i < 6; i ++) {
			pwd.append(r.nextInt(10));
		}
		return pwd.toString();
	}
	
	/**
	 * 格式化一个字符串为指定的长度，长度不足的在右边补空格 rab = Right Append Blank
	 * 
	 * @param src
	 * @param length
	 * @return
	 */
	public static final String rab(final String srcStr, final int length) {
		try {
			byte[] src, res;
			if (srcStr == null) {
				res = new byte[length];
				Arrays.fill(res, SPACE);
				return new String(res, CHARSET);
			} else {
				src = srcStr.getBytes(CHARSET);
				final int len = src.length;
				if (len > length) {
					return new String(src, 0, length, CHARSET);
				} else if (len == length) {
					return srcStr;
				} else {
					res = new byte[length];
					Arrays.fill(res, SPACE);
					System.arraycopy(src, 0, res, 0, len);
					return new String(res, CHARSET);
				}
			}
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * 格式化一个字符串为指定的长度，长度不足的在左边补零 liz = Left Insert Zero
	 * 
	 * @param src
	 * @param length
	 * @return
	 */
	public static final String liz(final String srcStr, final int length) {
		try {
			byte[] src, res;
			if (srcStr == null) {
				res = new byte[length];
				Arrays.fill(res, ZERO);
				return new String(res, CHARSET);
			} else {
				src = srcStr.getBytes(CHARSET);
				final int len = src.length;
				if (len > length) {
					return new String(src, 0, length, CHARSET);
				} else if (len == length) {
					return srcStr;
				} else {
					res = new byte[length];
					Arrays.fill(res, ZERO);
					System.arraycopy(src, 0, res, length - len, len);
					return new String(res, CHARSET);
				}
			}
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}
	
	 /**
	  * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
	  * @param list列表对象
	  * @return java.lang.String
	  */
	 public static String listToJson(List<?> list) {
	  StringBuilder json = new StringBuilder();
	  json.append("[");
	  if (list != null && list.size() > 0) {
	   for (Object obj : list) {
	    json.append(objectToJson(obj));
	    json.append(",");
	   }
	   json.setCharAt(json.length() - 1, ']');
	  } else {
	   json.append("]");
	  }
	  return json.toString();
	 }

	 /**
	  * @param object 任意对象
	  * @return java.lang.String
	  */
	 public static String objectToJson(Object object) {
	  StringBuilder json = new StringBuilder();
	  if (object == null) {
	      json.append("\"\"");
	  } else if (object instanceof Timestamp) {
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
		  String str = df.format(object);
	      json.append("\"").append(str.replace("\"", "&quot;")).append("\"");
	  } else if (boolean.class.isAssignableFrom(object.getClass())) {
		  Boolean B = new Boolean((boolean)object);
	      json.append("\"").append(B.toString().replace("\"", "&quot;")).append("\"");
	  } else if (object instanceof String || object instanceof Integer || object instanceof Boolean) {
		  String str=object.toString().replace("\"", "&quot;");
		  str=str.replace("\r\n","<br/>");
		  str=str.replace("\n","&#13;&#10;");
	      json.append("\"").append(str).append("\"");
	  } else {
	      json.append(beanToJson(object));
	  }
	  return json.toString();
	 }
	
	 /**
	  * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串
	  * @param bean bean对象
	  * @return String
	  */
	 private static String beanToJson(Object bean) {
	  StringBuilder json = new StringBuilder();
	  json.append("{");
	  PropertyDescriptor[] props = null;
	  try {
	   props = Introspector.getBeanInfo(bean.getClass(), Object.class)
	     .getPropertyDescriptors();
	  } catch (IntrospectionException e) {
	  }
	  if (props != null) {
	   for (int i = 0; i < props.length; i++) {
	    try {
	     String name = objectToJson(props[i].getName());
	     String value = objectToJson(props[i].getReadMethod()
	       .invoke(bean));
	     json.append(name);
	     json.append(":");
	     json.append(value);
	     json.append(",");
	    } catch (Exception e) {
	    }
	   }
	   json.setCharAt(json.length() - 1, '}');
	  } else {
	   json.append("}");
	  }
	  return json.toString();
	 }
	 
}

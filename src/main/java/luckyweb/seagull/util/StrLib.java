package luckyweb.seagull.util;


import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
public class StrLib {
	 private static final byte SPACE = 0x20, ZERO = 0x30;
	 public static final String CHARSET = "ISO-8859-1";

	
	private static StringTokenizer sto;
	private static StringBuffer buf;
	private SimpleDateFormat format;
	
	public String format(String str) {
		return (str == null ? "" : str);
	}


	public static String formatHtml(String str) {
		if (str == null){
			return "";
		}
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
		if (str == null){
			return null;
		}
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
				if (StrLib.isEmpty(arr[i])){
					continue;
				}
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
		if("".equals(str)){return "";}
		 sto=new StringTokenizer(str,"-");
		 buf=new StringBuffer();
		while(sto.hasMoreElements())
		{
			buf.append(sto.nextElement());
		}
		return buf.toString();

	}
	
	public static String convertTime(String str)
	{
	    if(str==null){
	    	return "";
	    }
	    int trimMaxLength=8;
	    if(str.trim().length()<trimMaxLength){
	    	return str;
	    }
	    return (str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8)).trim();
	}
	/**
	 * 随机产生一个6位的数字组合，作为密码
	 * @return
	 */
	public static String generatePwd() {
		StringBuffer pwd = new StringBuffer(6);
		java.util.Random r = new java.util.Random();
		 int Length=6;
		for (int i = 0;i < Length; i ++) {
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
	 public static JSONArray listToJson(List<?> list) {
		 StringBuilder jsonarr = new StringBuilder();
		 jsonarr.append("[");
		 //fastjson在转化list>string的过程中，如果List中的对象中还有实体对象，存在bug，所以循环转换
		 for(Object obj:list){
			 String jsonObject = JSON.toJSONString(obj,SerializerFeature.WriteMapNullValue, 
					 SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullNumberAsZero,
					 SerializerFeature.WriteNullBooleanAsFalse);
			 jsonarr.append(jsonObject).append(",");
		 }
		 jsonarr.append("]");
		 JSONArray recordJson = JSONArray.parseArray(jsonarr.toString());
		 return recordJson;
	}
	 
	 /**
	  * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
	  * @param list列表对象
	  * @return java.lang.String
	  * @deprecated
	  */
	 public static String listToJsonOld(List<?> list) {
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
	  * @deprecated
	  */
	 public static String objectToJson(Object object) {
	  StringBuilder json = new StringBuilder();
	  if (object == null) {
		  json.append("\"\"");		  
	  } else if (object.getClass().isArray()) {		  
	      json.append("\"").append(JSON.toJSONString(object).replace("\"", "&quot;")).append("\"");
	  } else if (object instanceof Timestamp) {
		//定义格式，不显示毫秒
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String str = df.format(object);
	      json.append("\"").append(str.replace("\"", "&quot;")).append("\"");
	  } else if (boolean.class.isAssignableFrom(object.getClass())) {
		  Boolean tempB = new Boolean((boolean)object);
	      json.append("\"").append(tempB.toString().replace("\"", "&quot;")).append("\"");
	  } else if (object instanceof String || object instanceof Integer || object instanceof Boolean || object instanceof Long) {
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
	  * @deprecated
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

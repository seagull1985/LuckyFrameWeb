package luckyweb.seagull.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @category 字符串的工具类
 * @author chen
 * 
 */
public class StringUtility {

	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return 空返回true，反之false
	 */
	public static boolean isNull(String s) {
		return (s == null || s.trim().length() <= 0);
	}

	public static String trimToEmpty(String s) {
		return isNull(s) ? "" : s.trim();
	}

	public static String leftString(String str, int len) {
		try {
			if (str != null) {
				byte[] b = str.getBytes("GBK");
				if (b.length > len) {
					str = new String(b, 0, len, "GBK");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 填充字符串到定长 str-被处理串，length-总长，fill-填充字符，filllef-左边填充
	 */
	public static String fillString(String str, int length, char fill, boolean fillLeft) {
		StringBuffer buffer = new StringBuffer(50);
		if (str == null) {
			str = "";
		}
		int len = str.getBytes().length;
		if (fillLeft) {
			for (int i = 0; i < length - len; i++) {
				buffer.append(fill);
			}
			buffer.append(str);
		} else {
			buffer.append(str);
			for (int i = 0; i < length - len; i++) {
				buffer.append(fill);
			}
		}
		return buffer.toString();
	}

	/**
	 * 字符串替换。
	 * 
	 * <pre>
	 * replace(null, *, *)        = null
	 * replace(&quot;&quot;, *, *)          = &quot;&quot;
	 * replace(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 * replace(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 * replace(&quot;aba&quot;, &quot;a&quot;, null)  = &quot;aba&quot;
	 * replace(&quot;aba&quot;, &quot;a&quot;, &quot;&quot;)    = &quot;aba&quot;
	 * replace(&quot;aba&quot;, &quot;a&quot;, &quot;z&quot;)   = &quot;zbz&quot;
	 * replace(&quot;aa&quot;, &quot;bb&quot;, &quot;aa中国a中国a&quot;)=”bb中国a中国a”
	 * replace(&quot;aa&quot;, &quot;bb&quot;, null)=null
	 * replace(&quot;aa&quot;, null, &quot;aa中国a中国a&quot;)=”aa中国a中国a”
	 * replace(null, &quot;bb&quot;, &quot;aa中国a中国a&quot;)=”aa中国a中国a”
	 * replace(&quot;aa&quot;, &quot;bb&quot;, &quot;&quot;)=””
	 * replace(&quot;aa&quot;, &quot;bb&quot;, &quot; &quot;)=” “
	 * replace(&quot;aa&quot;, &quot;bb&quot;, &quot;aa中国a中国a&quot;)=”bb中国a中国a”
	 * replace(&quot;&quot;, &quot;bb&quot;, &quot;aa中国a中国a&quot;)=”aa中国a中国a”
	 * replace(&quot; &quot;, &quot;bb&quot;, &quot;aa中 国a中 国a&quot;)=”aa中bb国a中bb国a”
	 * replace(&quot;aa&quot;, &quot;&quot;, &quot;aa中国a中国a&quot;)=”中国a中国a”
	 * replace(&quot;aa&quot;, &quot; &quot;, &quot;aa中国a中国a&quot;)=” 中国a中国a”
	 * replace(null, null, &quot;aa中国a中国a&quot;) = “aa中国a中国a”
	 * </pre>
	 * 
	 * @see #replace(String text, String repl, String with, int max)
	 * @param text
	 *            需要被替换或查找的字符串，有可能为null.
	 * @param repl
	 *            需要被替换的字符串。
	 * @param with
	 *            替换的字符串信息。
	 * @return 返回被替换后的字符串信息。
	 */
	public static String replace(String repl, String with, String text) {
		return replace(repl, with, text, -1);
	}

	/**
	 * 以指定的次数来对字符串进行替换操作。
	 * 
	 * <pre>
	 * replace(null, *, *, *)         = null
	 * replace(&quot;&quot;, *, *, *)           = &quot;&quot;
	 * replace(&quot;abaa&quot;, null, null, 1) = &quot;abaa&quot;
	 * replace(&quot;abaa&quot;, null, null, 1) = &quot;abaa&quot;
	 * replace(&quot;abaa&quot;, &quot;a&quot;, null, 1)  = &quot;abaa&quot;
	 * replace(&quot;abaa&quot;, &quot;a&quot;, &quot;&quot;, 1)    = &quot;abaa&quot;
	 * replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 0)   = &quot;abaa&quot;
	 * replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 1)   = &quot;zbaa&quot;
	 * replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 2)   = &quot;zbza&quot;
	 * replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, -1)  = &quot;zbzz&quot;
	 * </pre>
	 * 
	 * @param text
	 *            需要被替换或查找的字符串，有可能为null.
	 * @param repl
	 *            需要被替换的字符串。
	 * @param with
	 *            替换的字符串信息.
	 * @param max
	 *            需要被替换的最大次数，如果数值小于0那么将会全部替换。
	 * @return 返回被替换后的字符串信息。
	 */
	public static String replace(String repl, String with, String text, int max) {
		if ((text == null) || (repl == null) || (with == null) || (repl.length() == 0) || (max == 0)) {
			return text;
		}
		StringBuffer objBuffer = new StringBuffer(text.length());
		int iStart = 0;
		int iEnd = 0;
		while ((iEnd = text.indexOf(repl, iStart)) != -1) {
			objBuffer.append(text.substring(iStart, iEnd));
			objBuffer.append(with);
			iStart = iEnd + repl.length();
			if (--max == 0) {
				break;
			}
		}
		objBuffer.append(text.substring(iStart));
		return objBuffer.toString();
	}

	/**
	 * <p>
	 * 检查string是否是空白或者string为null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank(&quot;&quot;)        = true
	 * StringUtils.isBlank(&quot; &quot;)       = true
	 * StringUtils.isBlank(&quot;bob&quot;)     = false
	 * StringUtils.isBlank(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            需要检查的string, 可能为null
	 * @return <code>true</code> 如果string为null或者string中数据为空白
	 * @since 2.0
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 是否string不为空白，string的长度不为0，string不为null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank(&quot;&quot;)        = false
	 * StringUtils.isNotBlank(&quot; &quot;)       = false
	 * StringUtils.isNotBlank(&quot;bob&quot;)     = true
	 * StringUtils.isNotBlank(&quot;  bob  &quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            需要检查的string, 可能为null
	 * @return <code>true</code> 如果string不为null并且 string不为空白，string的长度不为0.
	 * @since 2.0
	 */
	public static boolean isNotBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return true;
			}
		}
		return false;
	}

	// Splitting
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Splits the provided text into an array, using whitespace as the
	 * separator. Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator. For more control over the split
	 * use the StrTokenizer class.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.split(null)       = null
	 * StringUtils.split(&quot;&quot;)         = []
	 * StringUtils.split(&quot;abc def&quot;)  = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtils.split(&quot;abc  def&quot;) = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtils.split(&quot; abc &quot;)    = [&quot;abc&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	/**
	 * <p>
	 * 根据指定的分割符separator把字符串分割为字符串数组. 也可以选择使用 StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * 分割符separator不作为数组元素中的数据返回. 邻近的多个分割符separators 被认为是一个分割符separator.
	 * </p>
	 * 
	 * <p>
	 * 输入的字符串为<code>null</code>返回<code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.split(null, *)         = null
	 * StringUtils.split(&quot;&quot;, *)           = []
	 * StringUtils.split(&quot;a.b.c&quot;, '.')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.split(&quot;a..b.c&quot;, '.')   = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.split(&quot;a:b:c&quot;, '.')    = [&quot;a:b:c&quot;]
	 * StringUtils.split(&quot;a\tb\nc&quot;, null) = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.split(&quot;a b c&quot;, ' ')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            需要处理的字符串, 可能为null
	 * @param separatorChar
	 *            作为分隔符的字符, 如果为<code>null</code>就以whitespace作为分隔符
	 * @return 处理后的数组, <code>null</code>如果输入的字符串为null
	 * @since 2.0
	 */
	public static ArrayList split(String str, char separatorChar) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new ArrayList();
		}
		ArrayList list = new ArrayList();
		int i = 0, start = 0;
		boolean match = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match) {
					list.add(str.substring(start, i));
					match = false;
				}
				start = ++i;
				continue;
			}
			match = true;
			i++;
		}
		if (match) {
			list.add(str.substring(start, i));
		}
		return list;
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separator specified. This is an
	 * alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator. For more control over the split
	 * use the StrTokenizer class.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.split(null, *)         = null
	 * StringUtils.split(&quot;&quot;, *)           = []
	 * StringUtils.split(&quot;a.b.c&quot;, '.')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.split(&quot;a..b.c&quot;, '.')   = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.split(&quot;a:b:c&quot;, '.')    = [&quot;a:b:c&quot;]
	 * StringUtils.split(&quot;a\tb\nc&quot;, null) = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.split(&quot;a b c&quot;, ' ')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChar
	 *            the character used as the delimiter, <code>null</code> splits
	 *            on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 * @since 2.0
	 */
	public static String[] splitByChar(String str, char separatorChar) {
		return splitWorker(str, separatorChar, false);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separators specified. This is an
	 * alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator. For more control over the split
	 * use the StrTokenizer class.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.split(null, *)         = null
	 * StringUtils.split(&quot;&quot;, *)           = []
	 * StringUtils.split(&quot;abc def&quot;, null) = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtils.split(&quot;abc def&quot;, &quot; &quot;)  = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtils.split(&quot;abc  def&quot;, &quot; &quot;) = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtils.split(&quot;ab:cd:ef&quot;, &quot;:&quot;) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	/**
	 * <p>
	 * Splits the provided text into an array with a maximum length, separators
	 * specified.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <p>
	 * If more than <code>max</code> delimited substrings are found, the last
	 * returned string includes all characters after the first
	 * <code>max - 1</code> returned strings (including separator characters).
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.split(null, *, *)            = null
	 * StringUtils.split(&quot;&quot;, *, *)              = []
	 * StringUtils.split(&quot;ab de fg&quot;, null, 0)   = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.split(&quot;ab   de fg&quot;, null, 0) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 0)    = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 2)    = [&quot;ab&quot;, &quot;cd:ef&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @param max
	 *            the maximum number of elements to include in the array. A zero
	 *            or negative value implies no limit
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str, String separatorChars, int max) {
		return splitWorker(str, separatorChars, max, false);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separator string specified.
	 * </p>
	 * 
	 * <p>
	 * The separator(s) will not be included in the returned String array.
	 * Adjacent separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separator splits on whitespace.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.splitByWholeSeparator(null, *)               = null
	 * StringUtils.splitByWholeSeparator(&quot;&quot;, *)                 = []
	 * StringUtils.splitByWholeSeparator(&quot;ab de fg&quot;, null)      = [&quot;ab&quot;, &quot;de&quot;, &quot;fg&quot;]
	 * StringUtils.splitByWholeSeparator(&quot;ab   de fg&quot;, null)    = [&quot;ab&quot;, &quot;de&quot;, &quot;fg&quot;]
	 * StringUtils.splitByWholeSeparator(&quot;ab:cd:ef&quot;, &quot;:&quot;)       = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitByWholeSeparator(&quot;ab-!-cd-!-ef&quot;, &quot;-!-&quot;) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separator
	 *            String containing the String to be used as a delimiter,
	 *            <code>null</code> splits on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String was
	 *         input
	 */
	public static String[] splitByWholeSeparator(String str, String separator) {
		return splitByWholeSeparator(str, separator, -1);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separator string specified.
	 * Returns a maximum of <code>max</code> substrings.
	 * </p>
	 * 
	 * <p>
	 * The separator(s) will not be included in the returned String array.
	 * Adjacent separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separator splits on whitespace.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.splitByWholeSeparator(null, *, *)               = null
	 * StringUtils.splitByWholeSeparator(&quot;&quot;, *, *)                 = []
	 * StringUtils.splitByWholeSeparator(&quot;ab de fg&quot;, null, 0)      = [&quot;ab&quot;, &quot;de&quot;, &quot;fg&quot;]
	 * StringUtils.splitByWholeSeparator(&quot;ab   de fg&quot;, null, 0)    = [&quot;ab&quot;, &quot;de&quot;, &quot;fg&quot;]
	 * StringUtils.splitByWholeSeparator(&quot;ab:cd:ef&quot;, &quot;:&quot;, 2)       = [&quot;ab&quot;, &quot;cd:ef&quot;]
	 * StringUtils.splitByWholeSeparator(&quot;ab-!-cd-!-ef&quot;, &quot;-!-&quot;, 5) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitByWholeSeparator(&quot;ab-!-cd-!-ef&quot;, &quot;-!-&quot;, 2) = [&quot;ab&quot;, &quot;cd-!-ef&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separator
	 *            String containing the String to be used as a delimiter,
	 *            <code>null</code> splits on whitespace
	 * @param max
	 *            the maximum number of elements to include in the returned
	 *            array. A zero or negative value implies no limit.
	 * @return an array of parsed Strings, <code>null</code> if null String was
	 *         input
	 */
	public static String[] splitByWholeSeparator(String str, String separator, int max) {
		if (str == null) {
			return null;
		}

		int len = str.length();

		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}

		if ((separator == null) || ("".equals(separator))) {
			// Split on whitespace.
			return split(str, null, max);
		}

		int separatorLength = separator.length();

		ArrayList substrings = new ArrayList();
		int numberOfSubstrings = 0;
		int beg = 0;
		int end = 0;
		while (end < len) {
			end = str.indexOf(separator, beg);

			if (end > -1) {
				if (end > beg) {
					numberOfSubstrings += 1;

					if (numberOfSubstrings == max) {
						end = len;
						substrings.add(str.substring(beg));
					} else {
						// The following is OK, because String.substring( beg,
						// end ) excludes
						// the character at the position 'end'.
						substrings.add(str.substring(beg, end));

						// Set the starting point for the next search.
						// The following is equivalent to beg = end +
						// (separatorLength - 1) + 1,
						// which is the right calculation:
						beg = end + separatorLength;
					}
				} else {
					// We found a consecutive occurrence of the separator, so
					// skip it.
					beg = end + separatorLength;
				}
			} else {
				// String.substring( beg ) goes from 'beg' to the end of the
				// String.
				substrings.add(str.substring(beg));
				end = len;
			}
		}

		return (String[]) substrings.toArray(new String[substrings.size()]);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Splits the provided text into an array, using whitespace as the
	 * separator, preserving all tokens, including empty tokens created by
	 * adjacent separators. This is an alternative to using StringTokenizer.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as separators for empty tokens. For more control
	 * over the split use the StrTokenizer class.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.splitPreserveAllTokens(null)       = null
	 * StringUtils.splitPreserveAllTokens(&quot;&quot;)         = []
	 * StringUtils.splitPreserveAllTokens(&quot;abc def&quot;)  = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;abc  def&quot;) = [&quot;abc&quot;, &quot;&quot;, &quot;def&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot; abc &quot;)    = [&quot;&quot;, &quot;abc&quot;, &quot;&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be <code>null</code>
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 * @since 2.1
	 */
	public static String[] splitPreserveAllTokens(String str) {
		return splitWorker(str, null, -1, true);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separator specified, preserving
	 * all tokens, including empty tokens created by adjacent separators. This
	 * is an alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as separators for empty tokens. For more control
	 * over the split use the StrTokenizer class.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.splitPreserveAllTokens(null, *)         = null
	 * StringUtils.splitPreserveAllTokens(&quot;&quot;, *)           = []
	 * StringUtils.splitPreserveAllTokens(&quot;a.b.c&quot;, '.')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;a..b.c&quot;, '.')   = [&quot;a&quot;, &quot;&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;a:b:c&quot;, '.')    = [&quot;a:b:c&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;a\tb\nc&quot;, null) = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;a b c&quot;, ' ')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;a b c &quot;, ' ')   = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;, &quot;&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;a b c  &quot;, ' ')   = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;, &quot;&quot;, &quot;&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot; a b c&quot;, ' ')   = [&quot;&quot;, a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;  a b c&quot;, ' ')  = [&quot;&quot;, &quot;&quot;, a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot; a b c &quot;, ' ')  = [&quot;&quot;, a&quot;, &quot;b&quot;, &quot;c&quot;, &quot;&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be <code>null</code>
	 * @param separatorChar
	 *            the character used as the delimiter, <code>null</code> splits
	 *            on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 * @since 2.1
	 */
	public static String[] splitPreserveAllTokens(String str, char separatorChar) {
		return splitWorker(str, separatorChar, true);
	}

	/**
	 * Performs the logic for the <code>split</code> and
	 * <code>splitPreserveAllTokens</code> methods that do not return a maximum
	 * array length.
	 * 
	 * @param str
	 *            the String to parse, may be <code>null</code>
	 * @param separatorChar
	 *            the separate character
	 * @param preserveAllTokens
	 *            if <code>true</code>, adjacent separators are treated as empty
	 *            token separators; if <code>false</code>, adjacent separators
	 *            are treated as one separator.
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		List list = new ArrayList();
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match || preserveAllTokens) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				start = ++i;
				continue;
			} else {
				lastMatch = false;
			}
			match = true;
			i++;
		}
		boolean existed = match || (preserveAllTokens && lastMatch);
		if (existed) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separators specified, preserving
	 * all tokens, including empty tokens created by adjacent separators. This
	 * is an alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as separators for empty tokens. For more control
	 * over the split use the StrTokenizer class.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.splitPreserveAllTokens(null, *)           = null
	 * StringUtils.splitPreserveAllTokens(&quot;&quot;, *)             = []
	 * StringUtils.splitPreserveAllTokens(&quot;abc def&quot;, null)   = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;abc def&quot;, &quot; &quot;)    = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;abc  def&quot;, &quot; &quot;)   = [&quot;abc&quot;, &quot;&quot;, def&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab:cd:ef&quot;, &quot;:&quot;)   = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab:cd:ef:&quot;, &quot;:&quot;)  = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;, &quot;&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab:cd:ef::&quot;, &quot;:&quot;) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;, &quot;&quot;, &quot;&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab::cd:ef&quot;, &quot;:&quot;)  = [&quot;ab&quot;, &quot;&quot;, cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;:cd:ef&quot;, &quot;:&quot;)     = [&quot;&quot;, cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;::cd:ef&quot;, &quot;:&quot;)    = [&quot;&quot;, &quot;&quot;, cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;:cd:ef:&quot;, &quot;:&quot;)    = [&quot;&quot;, cd&quot;, &quot;ef&quot;, &quot;&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be <code>null</code>
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 * @since 2.1
	 */
	public static String[] splitPreserveAllTokens(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, true);
	}

	/**
	 * <p>
	 * Splits the provided text into an array with a maximum length, separators
	 * specified, preserving all tokens, including empty tokens created by
	 * adjacent separators.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as separators for empty tokens. Adjacent
	 * separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <p>
	 * If more than <code>max</code> delimited substrings are found, the last
	 * returned string includes all characters after the first
	 * <code>max - 1</code> returned strings (including separator characters).
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.splitPreserveAllTokens(null, *, *)            = null
	 * StringUtils.splitPreserveAllTokens(&quot;&quot;, *, *)              = []
	 * StringUtils.splitPreserveAllTokens(&quot;ab de fg&quot;, null, 0)   = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab   de fg&quot;, null, 0) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab:cd:ef&quot;, &quot;:&quot;, 0)    = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab:cd:ef&quot;, &quot;:&quot;, 2)    = [&quot;ab&quot;, &quot;cd:ef&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab   de fg&quot;, null, 2) = [&quot;ab&quot;, &quot;  de fg&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab   de fg&quot;, null, 3) = [&quot;ab&quot;, &quot;&quot;, &quot; de fg&quot;]
	 * StringUtils.splitPreserveAllTokens(&quot;ab   de fg&quot;, null, 4) = [&quot;ab&quot;, &quot;&quot;, &quot;&quot;, &quot;de fg&quot;]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be <code>null</code>
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @param max
	 *            the maximum number of elements to include in the array. A zero
	 *            or negative value implies no limit
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 * @since 2.1
	 */
	public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
		return splitWorker(str, separatorChars, max, true);
	}

	/**
	 * Performs the logic for the <code>split</code> and
	 * <code>splitPreserveAllTokens</code> methods that return a maximum array
	 * length.
	 * 
	 * @param str
	 *            the String to parse, may be <code>null</code>
	 * @param separatorChars
	 *            the separate character
	 * @param max
	 *            the maximum number of elements to include in the array. A zero
	 *            or negative value implies no limit.
	 * @param preserveAllTokens
	 *            if <code>true</code>, adjacent separators are treated as empty
	 *            token separators; if <code>false</code>, adjacent separators
	 *            are treated as one separator.
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)
		// Direct code is quicker than StringTokenizer.
		// Also, StringTokenizer uses isSpace() not isWhitespace()

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		List list = new ArrayList();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			// Null separator means use whitespace
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// Optimise 1 character case
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		} else {
			// standard case
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		boolean existed = match || (preserveAllTokens && lastMatch);
		if (existed) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String toHtmlEncode(String str) {
		str = str.replaceAll("\\n", "<br>");
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\'", "&apos;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("\n", "<br>");
		str = str.replaceAll(" ", "&nbsp;");
		str = str.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		return str;
	}

	public static String toUtf8Encode(String str) {
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&apos;", "\'");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("<br>", "\n");
		str = str.replaceAll("<BR>", "\n");
		str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "\t");
		return str;
	}

	/**
	 * 从源字符串(source)中取前len个字符，如果len小于源字符串的长度，则在返回值后加上字符串concat。
	 * 
	 * <pre>
	 * String strSource = "a中华人民共和国";
	 * PubFunctions.getFixedLengthString(strSource,10,"...")= "a中华人民共..."
	 * PubFunctions.getFixedLengthString(strSource,10,null)= “a中华人民共”
	 * PubFunctions.getFixedLengthString(strSource,10,"")=”a中华人民共”
	 * PubFunctions.getFixedLengthString(strSource,10," ")=”a中华人民共”
	 * PubFunctions.getFixedLengthString(strSource,-1,"")=””
	 * PubFunctions.getFixedLengthString(strSource,0,"")=””
	 * PubFunctions.getFixedLengthString(strSource,0,null)=””
	 * PubFunctions.getFixedLengthString(strSource,-1,null)=””
	 * </pre>
	 *
	 * @param source
	 *            源字符串信息
	 * @param len
	 *            需要截取的字符个数
	 * @param concat
	 *            需要填补的字符串
	 * @return 返回获取的字符串信息。
	 */
	public static String getFixedLengthString(String source, int len, String concat) {
		String strRet = "";
		if (source == null || concat == null) {
			return "";
		}
		if (len <= 0) {
			return strRet;
		}
		for (int i = 1; i <= source.length(); i++) {
			strRet = source.substring(0, i);
			if (strRet.getBytes().length >= len) {
				break;
			}
		}
		if ((source != null) && (strRet != null) && (strRet.getBytes().length < source.getBytes().length)) {
			strRet += concat;
		}
		return strRet;
	}
}
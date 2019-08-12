package com.clbee.pbcms.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.CookieGenerator;

import com.clbee.pbcms.util.base64.Base64;



public class StringUtil {
	
	protected static Logger LOG = LoggerFactory.getLogger(StringUtil.class);

	public static boolean isNull(String str) {
		if (str != null) {
			str = str.trim();
		}

		return ((str == null) || ("".equals(str)));
	}

	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}

		int size = str.length();

		if (size == 0) {
			return false;
		}
		for (int i = 0; i < size; ++i) {
			if (!(Character.isLetter(str.charAt(i)))) {
				return false;
			}
		}

		return true;
	}

	public static boolean isAlphaNumeric(String str) {
		if (str == null) {
			return false;
		}

		int size = str.length();

		if (size == 0) {
			return false;
		}
		for (int i = 0; i < size; ++i) {
			if (!(Character.isLetterOrDigit(str.charAt(i)))) {
				return false;
			}
		}

		return true;
	}

	public static String integer2string(int integer) {
		return "" + integer;
	}

	public static String long2string(long longdata) {
		return String.valueOf(longdata);
	}

	public static String float2string(float floatdata) {
		return String.valueOf(floatdata);
	}

	public static String double2string(double doubledata) {
		return String.valueOf(doubledata);
	}

	public static int string2integer(String str) {
		if (isNull(str)) {
			return 0;
		}

		return Integer.parseInt(str);
	}

	public static float string2float(String str) {
		if (isNull(str)) {
			return 0.0F;
		}

		return Float.parseFloat(str);
	}

	public static double string2double(String str) {
		if (isNull(str)) {
			return 0.0D;
		}

		return Double.parseDouble(str);
	}

	public static long string2long(String str) {
		if (isNull(str)) {
			return 0L;
		}

		return Long.parseLong(str);
	}

	public static int string2integer(String str, int defaultValue) {
		if (isNull(str)) {
			return defaultValue;
		}

		return Integer.parseInt(str);
	}

	public static float string2float(String str, float defaultValue) {
		if (isNull(str)) {
			return defaultValue;
		}

		return Float.parseFloat(str);
	}

	public static double string2double(String str, double defaultValue) {
		if (isNull(str)) {
			return defaultValue;
		}

		return Double.parseDouble(str);
	}

	public static long string2long(String str, long defaultValue) {
		if (isNull(str)) {
			return defaultValue;
		}

		return Long.parseLong(str);
	}

	public static String null2void(String str) {
		if (isNull(str)) {
			str = "";
		}

		return str;
	}

	public static String null2string(String str, String defaultValue) {
		if (isNull(str)) {
			return defaultValue;
		}

		return str;
	}

	public static boolean equals(String source, String target) {
		return null2void(source).equals(null2void(target));
	}

	public static String toSubString(String str, int beginIndex, int endIndex) {
		if (equals(str, ""))
			return str;
		if (str.length() < beginIndex)
			return "";
		if (str.length() < endIndex) {
			return str.substring(beginIndex);
		}
		return str.substring(beginIndex, endIndex);
	}

	public static String toSubString(String source, int beginIndex) {
		if (equals(source, ""))
			return source;
		if (source.length() < beginIndex) {
			return "";
		}
		return source.substring(beginIndex);
	}

	public static int search(String source, String target) {
		int result = 0;
		String strCheck = new String(source);
		for (int i = 0; i < source.length();) {
			int loc = strCheck.indexOf(target);
			if (loc == -1) {
				break;
			}
			++result;
			i = loc + target.length();
			strCheck = strCheck.substring(i);
		}

		return result;
	}

	public static String trim(String str) {
		return str.trim();
	}

	public static String ltrim(String str) {
		int index = 0;

		while (' ' == str.charAt(index++))
			;
		if (index > 0) {
			str = str.substring(index - 1);
		}
		return str;
	}

	public static String rtrim(String str) {
		int index = str.length();

		while (' ' == str.charAt(--index))
			;
		if (index < str.length()) {
			str = str.substring(0, index + 1);
		}
		return str;
	}

	public static String concat(String str1, String str2) {
		StringBuffer sb = new StringBuffer(str1);
		sb.append(str2);

		return sb.toString();
	}

	public static String lPad(String str, int len, char pad) {
		return lPad(str, len, pad, false);
	}

	public static String lPad(String str, int len, char pad, boolean isTrim) {
		if (isNull(str)) {
			return null;
		}

		if (isTrim) {
			str = str.trim();
		}

		for (int i = str.length(); i < len; ++i) {
			str = pad + str;
		}

		return str;
	}

	public static String rPad(String str, int len, char pad) {
		return rPad(str, len, pad, false);
	}

	public static String rPad(String str, int len, char pad, boolean isTrim) {
		if (isNull(str)) {
			return null;
		}

		if (isTrim) {
			str = str.trim();
		}

		for (int i = str.length(); i < len; ++i) {
			str = str + pad;
		}

		return str;
	}

	public static String alignLeft(String str, int length) {
		return alignLeft(str, length, false);
	}

	public static String alignLeft(String str, int length, boolean isEllipsis) {
		if (str.length() <= length) {
			StringBuffer temp = new StringBuffer(str);
			for (int i = 0; i < length - str.length(); ++i) {
				temp.append(' ');
			}
			return temp.toString();
		}
		if (isEllipsis) {
			StringBuffer temp = new StringBuffer(length);
			temp.append(str.substring(0, length - 3));
			temp.append("...");

			return temp.toString();
		}
		return str.substring(0, length);
	}

	public static String alignRight(String str, int length) {
		return alignRight(str, length, false);
	}

	public static String alignRight(String str, int length, boolean isEllipsis) {
		if (str.length() <= length) {
			StringBuffer temp = new StringBuffer(length);
			for (int i = 0; i < length - str.length(); ++i) {
				temp.append(' ');
			}
			temp.append(str);
			return temp.toString();
		}
		if (isEllipsis) {
			StringBuffer temp = new StringBuffer(length);
			temp.append(str.substring(0, length - 3));
			temp.append("...");
			return temp.toString();
		}
		return str.substring(0, length);
	}

	public static String alignCenter(String str, int length) {
		return alignCenter(str, length, false);
	}

	public static String alignCenter(String str, int length, boolean isEllipsis) {
		if (str.length() <= length) {
			StringBuffer temp = new StringBuffer(length);
			int leftMargin = (length - str.length()) / 2;
			int rightMargin;
			if (leftMargin * 2 == length - str.length())
				rightMargin = leftMargin;
			else {
				rightMargin = leftMargin + 1;
			}

			for (int i = 0; i < leftMargin; ++i) {
				temp.append(' ');
			}

			temp.append(str);

			for (int i = 0; i < rightMargin; ++i) {
				temp.append(' ');
			}

			return temp.toString();
		}
		if (isEllipsis) {
			StringBuffer temp = new StringBuffer(length);
			temp.append(str.substring(0, length - 3));
			temp.append("...");
			return temp.toString();
		}
		return str.substring(0, length);
	}

	public static String capitalize(String str) {
		return ((!(isNull(str))) ? str.substring(0, 1).toUpperCase()
				+ str.substring(1).toLowerCase() : str);
	}

	public static boolean isPatternMatch(String str, String pattern)
			throws Exception {
		Matcher matcher = Pattern.compile(pattern).matcher(str);
		LOG.debug("" + matcher);

		return matcher.matches();
	}

	public static String toEng(String kor) throws UnsupportedEncodingException {
		if (isNull(kor)) {
			return null;
		}

		return new String(kor.getBytes("KSC5601"), "8859_1");
	}

	public static String toKor(String en) throws UnsupportedEncodingException {
		if (isNull(en)) {
			return null;
		}

		return new String(en.getBytes("8859_1"), "euc-kr");
	}

	public static int countOf(String str, String charToFind) {
		int findLength = charToFind.length();
		int count = 0;

		for (int idx = str.indexOf(charToFind); idx >= 0; idx = str.indexOf(
				charToFind, idx + findLength)) {
			++count;
		}

		return count;
	}

	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			LOG.error("Exception: " + e);

			return password;
		}

		md.reset();

		md.update(unencodedPassword);

		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; ++i) {
			if ((encodedPassword[i] & 0xFF) < 16) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
		}

		return buf.toString();
	}

	public static String encodeString(String str, String charSet) {
		try {
			return new String(Base64.encodeBytesToBytes(str.getBytes(charSet)),
					charSet);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
	}

	public static String encodeString(String str) {
		return encodeString(str, "UTF-8");
	}

	public static String decodeString(String str, String charSet) {
		try {
			return new String(Base64.decode(str.getBytes(charSet)), charSet);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
	}

	public static String decodeString(String str) {
		return decodeString(str, "UTF-8");
	}

	public static String swapFirstLetterCase(String str) {
		if (str == null || str.length() ==0) return str;
		
		StringBuffer sbuf = new StringBuffer(str);
		sbuf.deleteCharAt(0);
		if (Character.isLowerCase(str.substring(0, 1).toCharArray()[0]))
			sbuf.insert(0, str.substring(0, 1).toUpperCase());
		else {
			sbuf.insert(0, str.substring(0, 1).toLowerCase());
		}
		return sbuf.toString();
	}

	public static String trim(String origString, String trimString) {
		int startPosit = origString.indexOf(trimString);
		if (startPosit != -1) {
			int endPosit = trimString.length() + startPosit;
			return origString.substring(0, startPosit)
					+ origString.substring(endPosit);
		}

		return origString;
	}

	public static String getLastString(String origStr, String strToken) {
		StringTokenizer str = new StringTokenizer(origStr, strToken);
		String lastStr = "";
		while (str.hasMoreTokens()) {
			lastStr = str.nextToken();
		}
		return lastStr;
	}

	public static String[] getStringArray(String str, String strToken) {
		if (str.indexOf(strToken) != -1) {
			StringTokenizer st = new StringTokenizer(str, strToken);
			String[] stringArray = new String[st.countTokens()];
			for (int i = 0; st.hasMoreTokens(); ++i) {
				stringArray[i] = st.nextToken();
			}
			return stringArray;
		}
		return new String[] { str };
	}
	

	public static boolean isPatternMatching(String str, String pattern)
			throws Exception {
		if (pattern.indexOf(42) >= 0) {
			pattern = pattern.replaceAll("\\*", ".*");
		}

		pattern = "^" + pattern + "$";

		return Pattern.matches(pattern, str);
	}

	public static boolean containsMaxSequence(String str, String maxSeqNumber) {
		int occurence = 1;
		int max = string2integer(maxSeqNumber);
		if (str == null) {
			return false;
		}

		int sz = str.length();
		for (int i = 0; i < sz - 1; ++i) {
			if (str.charAt(i) == str.charAt(i + 1)) {
				++occurence;

				if (occurence == max)
					return true;
			} else {
				occurence = 1;
			}
		}
		return false;
	}

	public static boolean containsInvalidChars(String str, char[] invalidChars) {
		if ((str == null) || (invalidChars == null)) {
			return false;
		}
		int strSize = str.length();
		int validSize = invalidChars.length;
		for (int i = 0; i < strSize; ++i) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; ++j) {
				if (invalidChars[j] == ch) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsInvalidChars(String str, String invalidChars) {
		if ((str == null) || (invalidChars == null)) {
			return true;
		}
		return containsInvalidChars(str, invalidChars.toCharArray());
	}


	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	public static String fillString(String originalStr, char ch, int cipers) {
		int originalStrLength = originalStr.length();

		if (cipers < originalStrLength) {
			return null;
		}
		int difference = cipers - originalStrLength;

		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < difference; ++i) {
			strBuf.append(ch);
		}
		strBuf.append(originalStr);
		return strBuf.toString();
	}

	public static final boolean isEmptyTrimmed(String foo) {
		return ((foo == null) || (foo.trim().length() == 0));
	}

	public static List getTokens(String lst, String separator) {
		List tokens = new ArrayList();

		if (lst != null) {
			StringTokenizer st = new StringTokenizer(lst, separator);
			while (st.hasMoreTokens()) {
				try {
					String en = st.nextToken().trim();
					tokens.add(en);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return tokens;
	}

	public static List getTokens(String lst) {
		return getTokens(lst, ",");
	}

	public static String convertToCamelCase(String targetString, char posChar) {
		StringBuffer result = new StringBuffer();
		boolean nextUpper = false;
		String allLower = targetString.toLowerCase();

		for (int i = 0; i < allLower.length(); ++i) {
			char currentChar = allLower.charAt(i);
			if (currentChar == posChar) {
				nextUpper = true;
			} else {
				if (nextUpper) {
					currentChar = Character.toUpperCase(currentChar);
					nextUpper = false;
				}
				result.append(currentChar);
			}
		}
		return result.toString();
	}

	public static String convertToCamelCase(String underScore) {
		return convertToCamelCase(underScore, '_');
	}

	public static String convertToUnderScore(String camelCase) {
		String result = "";
		for (int i = 0; i < camelCase.length(); ++i) {
			char currentChar = camelCase.charAt(i);

			if ((i > 0) && (Character.isUpperCase(currentChar))) {
				result = result.concat("_");
			}
			result = result.concat(Character.toString(currentChar)
					.toLowerCase());
		}

		return result;
	}

	public static String chunkString(String str, int bytes) {
		boolean ellipsis = bytes > 10;
		boolean breaked = false;
		if (ellipsis) {
			bytes -= "[...]".length();
		}
		if (str == null) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		char ch;
		int added = 0;
		int len = str.length();
		for (int i = 0; i < len; i ++) {
			ch = str.charAt(i);
			added += ((ch > 127) ? 2 : 1);
			if (added > bytes) {
				breaked = true;
				break;
			}
			buf.append(ch); 
		}
		if (ellipsis && breaked) {
			buf.append("[...]");
		}

		return buf.toString();
	}

	/**
     * 크로스 사이트 스크립팅을 막기 위해 string 치환을 한다.
     * @param oriStr
     * @return
     */
    public static String replaceScriptToPlain(String oriStr) {

        String replaceStr = "";

        replaceStr = StringUtil.replaceString(oriStr, "&", "&amp;");
        replaceStr = StringUtil.replaceString(replaceStr, "<", "&lt;");
        replaceStr = StringUtil.replaceString(replaceStr, ">", "&gt;");
        replaceStr = StringUtil.replaceString(replaceStr, "`", "&#39;");
        replaceStr = StringUtil.replaceString(replaceStr, "--", "&#45;&#45;");
        replaceStr = StringUtil.replaceString(replaceStr, "\\", "&#92;");
        replaceStr = StringUtil.replaceString(replaceStr, "\"", "&quot;");

        return replaceStr;
    }

    /**
     * strOrg에서 strFrom을 strTo문자열로 바꾸는 루틴
     *
     * @param String strOrg 원문자열이다.
     * @param String strFrom 원문자열에서 바뀌어야 할 문자열이다.
     * @param String strTo 원문자열에서 바꿀 문자열이다.
     * @return String 바뀌어진 값을 넘겨준다.
     *
     */
     public static String replaceString(String strOrg, String strFrom, String strTo)
     {
         int last=0, next=0;
         String strResult = "";

         while (true) {
             next = strOrg.indexOf(strFrom, last);
             if (next >= 0 ) {
                 strResult += strOrg.substring(last, next) + strTo;
                 last = next + strFrom.length();
             } else {
                 strResult += strOrg.substring(last);
                 break;
             }
         }
         return strResult;
     }

    /**
     * 스트링안에 특정 캐릭터가 몇개가 있는지를 반환하는 메소드.
     *
     * @param in
     *            검색할 스트링.
     * @param lookFor
     *            찾아보려고하는 캐릭터.
     * @return 존재하는갯수.
     */
    private static int charCount(String in, char lookFor) {
        String search = new String(in);
        int index = search.indexOf(lookFor);
        int count = 0;

        while (index > 0) {
            count++;
            search = search.substring(index + 1);
            index = search.indexOf(lookFor);
        }

        return count;
    }

    /**
     * 스트링이 숫자로만 이루어져있는지를 점검하는 메소드
     *
     * @param text
     *            검사할 스트링.
     * @return 숫자인 문자로만 되어있으면 true 아니면 false를 반환한다.
     */
    public final static boolean isNumeric(String text) {
        for (int i = 0; i < text.length(); i++)
            if (!Character.isLetterOrDigit(text.charAt(i))) {
                return (false);
            }

        return (true);
    }

    /**
     * 정규식을 이용한 split 함수
     * @param s
     * @param delimiter
     * @return
     */
    public static String[] split(String s, String delimiter) {

        String[] arr = s.split(delimiter);

        return arr;
    }

    public static String split(String s, String delimiter, int index) {

        if("".equals(s) || s==null) return "";

        String[] arr = split(s, delimiter);

        String result = "";

        try {
            result = arr[index];
        } catch (Exception e) {
            return "";
        }

        return result;
    }


    /**
     * 문자코드 변환 ( 8859_1 --> KSC5601 )
     *
     * @param str
     *            변환할 문자열
     * @return 변환된 문자열
     */
    public static String asciiToKsc(String str) {
        try {
            if (str == null) {
                return null;
            }

            return new String(str.getBytes("8859_1"), "KSC5601");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 문자코드 변환 ( 8859_1 --> KSC5601 )
     *
     * @param str
     *            변환할 문자열
     * @return 변환된 문자열
     */
    public static String kscToAscii(String str) {
        try {
            if (str == null) {
                return null;
            }

            return new String(str.getBytes("KSC5601"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 문자가 null일 경우 다른 문자로 대체
     *
     * @param str
     *            문자
     * @param val
     *            Null일 경우 대체될 문자
     * @author sybaek
     * @since 2000.6.16
     */
    public static String nvl(Object obj, String val) {

        String ret = (obj!=null) ? obj.toString().trim() : "";

        if ( "".equals(ret) ) {
            return val;
        } else {
            return ret;
        }
    }

    /**
     * 문자가 null일 경우 다른 문자로 대체
     *
     * @param str
     *            문자
     * @param val
     *            Null일 경우 대체될 문자
     * @author sybaek
     * @since 2000.6.16
     */
    public static String nvl(String str, String val) {

        if (str == null || "".equals(str.trim())) {
            return val;
        } else {
            return str;
        }
    }

    /**
     * 문자 취환 Returns a String with all occurrences of the String from replaced
     * by the String to.
     *
     * @param in
     *            작업할 문자를 포함하는 문자열
     * @param from
     *            찾는 문자열
     * @param to
     *            취환할 문자열
     * @return The new String
     */
    public static String replace(String in, String from, String to) {
        StringBuffer sb = new StringBuffer(in.length() * 2);
        String posString = in.toLowerCase();
        String cmpString = from.toLowerCase();
        int i = 0;
        boolean done = false;

        while ((i < in.length()) && !done) {
            int start = posString.indexOf(cmpString, i);

            if (start == -1) {
                done = true;
            } else {
                sb.append(in.substring(i, start) + to);
                i = start + from.length();
            }
        }

        if (i < in.length()) {
            sb.append(in.substring(i));
        }

        return sb.toString();
    }

    /**
     * String 중 특정한 문자가 몇번 들어가 있는지 return
     *
     * @param str
     *            대상 문자열
     * @param find
     *            찾고자 하는 문자열
     */
    public static int cntInStr(String str, String find) {
        int i = 0;
        int pos = 0;

        while (true) {
            pos = str.indexOf(find, pos);

            if (pos == -1) {
                break;
            }

            i++;
            pos++;
        }

        return i;
    }

    /**
     * 유니코드 2.0한글을 유니코드 1.2한글보충영역으로 전환
     *
     * @param uni20
     *            대상 문자열
     */
    public static String uni20ToUni12(String uni20)
        throws UnsupportedEncodingException {
        if (uni20 == null) {
            return null;
        }

        int len = uni20.length();
        char[] out = new char[len];

        //for 시작
        for (int i = 0; i < len; i++) {
            char c = uni20.charAt(i);

            if ((c < 0xac00) || (0xd7a3 < c)) {
                out[i] = c;
            } else { // 유니코드 2.0 한글 영역

                try {
                    byte[] ksc = String.valueOf(c).getBytes("KSC5601");

                    if (ksc.length != 2) {
                        out[i] = '\ufffd';
                        System.err.println(
                            "Warning: Some of Unicode 2.0 hangul character was ignored.");
                    } else {
                        out[i] = (char) ((0x3400 +
                            (((ksc[0] & 0xff) - 0xb0) * 94) + (ksc[1] & 0xff)) -
                            0xa1);
                    }
                } catch (java.io.UnsupportedEncodingException ex) {
                    //throw new SQLException( ex.getMessage() );
                } //try 끝
            } //2번째 if문 끝
        }

        //for 끝
        return new String(out);
    }

    /**
     * 유니코드 1.2 한글을 유니코드 2.0 한글영역으로 전환
     *
     * @param uni12
     *            대상 문자열
     */
    public static String uni12ToUni20(String uni12) {
        if (uni12 == null) {
            return null;
        }

        int len = uni12.length();
        char[] out = new char[len];
        byte[] ksc = new byte[2];

        for (int i = 0; i < len; i++) {
            char c = uni12.charAt(i);

            if ((c < 0x3400) || (0x4dff < c)) {
                out[i] = c;
            } else if (0x3d2e <= c) // 유니코드 1.2 한글 보충 영역 A, B
             {
                System.err.println(
                    "Warning: Some of Unicode 1.2 hangul character was ignored.");
                out[i] = '\ufffd';
            } else {
                try {
                    ksc[0] = (byte) (((c - 0x3400) / 94) + 0xb0);
                    ksc[1] = (byte) (((c - 0x3400) % 94) + 0xa1);
                    out[i] = new String(ksc, "KSC5601").charAt(0);
                } catch (java.io.UnsupportedEncodingException ex) {
                    throw new InternalError(
                        "Fatal Error: KSC5601 encoding is not supported.");
                }
            }
        }

        return new String(out);
    }

    /**
     * varchar2 타입의 자료를 이용해 sql문을 만들기 쉽게 하기 위해 varchar2 string에 '문자가 자료에 포함될 경우
     * ''로 변환한다.
     *
     * @param str
     *            변환할 문자열
     */
    public static String getQuotedStr(String str) {
        //  널일경우
        if (str == null) {
            return "";
        }

        //  '문자가 없는 스트링의 경우
        if (str.indexOf('\'') < 0) {
            return str;
        }

        //  '문자가 있는 스트링의 경우 '문자를 ''로 바꾸어준다.
        StringBuffer strbuf = new StringBuffer(str);

        for (int i = 0; i < strbuf.length();) {
            if (strbuf.charAt(i) == '\'') {
                ;
                strbuf.replace(i, i + 1, new String("''"));
                i = i + 2;
            } else {
                i++;
            }
        }

        //  바뀐 스트링을 리턴
        return new String(strbuf);
    }

    /**
     * 문자열을 숫자를 포함하고 있을 경우 첫숫자의 위치를 return
     *
     * @param sVal
     *            원문 문자열
     * @return int index of fist number string
     */
    public static int indexOfFirstNumber(String sVal) {
        String s = "";
        int iPos = 0;
        boolean b = false;

        for (int i = 0; i < sVal.length(); i++) {
            s = sVal.substring(i, i + 1);

            try {
                iPos = Integer.parseInt(s);
                b = true;
            } catch (Exception e) {
                b = false;
            }

            if (b) {
                iPos = i;

                break;
            }
        }

        return iPos;
    }


    /**
     * 하나의 문자열(String)을 숫자로 변환 int return
     *
     * @param strNo
     *      문자열 한개
     * @return intNo (숫자)
     */
    public static int parseInt(String s) {
        try
        {
            if(s == null)
                return 0;
            else
                return Integer.parseInt(s);
        }
        catch(Exception e)
        {
        }
        return 0;
    }

    /**
     * 하나의 문자열(String)을 숫자로 변환 int return
     *
     * @param strNo
     *      문자열 한개
     * @return intNo (숫자)
     */
    public static int parseInt(Object s) {
        try
        {
            if(s == null)
                return 0;
            else
                return ((Number)s).intValue();
        }
        catch(Exception e)
        {
        }
        return 0;
    }

    /**
     * 주민번호에서 출생년 추출하기 생년(YYYY) return
     *
     * @param jumin
     *      주민번호13자리
     * @return year
     */
    public static String birthYearFromSsn(String jumin) {

        String year = "";

        if ((jumin.substring(6,7).equals("1"))||(jumin.substring(6,7).equals("2")))
        {
           year = ("19"+jumin.substring(0,2));
        }
        else if ((jumin.substring(6,7).equals("3")) ||(jumin.substring(6,7).equals("4")))
        {
            year = ("20"+jumin.substring(0,2));
        } else {
            year = ("19"+jumin.substring(0,2));
        }
        return year;
    }

    /**
     * 주민번호에서 출생월 추출하기 생월(MM) return
     *
     * @param jumin
     *      주민번호13자리
     * @return month
     */
    public static String birthMonthFromSssn(String jumin) {

        String month = "";
         month=jumin.substring(2,4);
        return month;
    }

    /**
     * 주민번호에서 출생일 추출하기 생일(DD) return
     *
     * @param jumin
     *      주민번호13자리
     * @return date
     */
    public static String birthDateFromSsn(String jumin) {

        String date = "";
         date=jumin.substring(4,6);
        return date;
    }

    /**
<<<<<<< StringUtil.java

     * 언어별로 날짜 형식을 달리 표현한다.

     * @param String 날짜형식

     * @Param locale 지역정보

     **/



    public static String getDateFormat(String userformat,String language, Date currentdate)

    {

        SimpleDateFormat convertformat;

        String ReturnVal;

        ReturnVal = "";



        try

        {

        if(language == "zh")

              convertformat = new SimpleDateFormat(userformat,Locale.CHINA);

        else if(language == "en")

              convertformat =  new SimpleDateFormat(userformat,Locale.US);

        else if(language == "ko")

              convertformat =  new SimpleDateFormat(userformat,Locale.KOREA);

        else

              convertformat =  new SimpleDateFormat(userformat,Locale.US);



        ReturnVal = convertformat.format(currentdate);

        }

        catch(Exception ex)

        {

              ReturnVal = currentdate.toString();

        }

        finally

        {

        }



        return ReturnVal;

    }



    /**
=======

     * 언어별로 날짜 형식을 달리 표현한다.

     * @param String 날짜형식

     * @Param locale 지역정보

     **/



    public static String getUserDateFormat(String userformat,String language, Date currentdate)

    {

        SimpleDateFormat convertformat;

        String ReturnVal;

        ReturnVal = "";



        try

        {

        if(language == "zh")

              convertformat = new SimpleDateFormat(userformat,Locale.CHINA);

        else if(language == "en")

              convertformat =  new SimpleDateFormat(userformat,Locale.US);

        else if(language == "ko")

              convertformat =  new SimpleDateFormat(userformat,Locale.KOREA);

        else

              convertformat =  new SimpleDateFormat(userformat,Locale.US);



        ReturnVal = convertformat.format(currentdate);

        }

        catch(Exception ex)

        {

              ReturnVal = currentdate.toString();

        }

        finally

        {

        }



        return ReturnVal;

    }



    /**
     * 숫자형의 값을 콤마가 포함된 화폐단위로 리턴
     *
     * @param double dblNumber 바뀌어야할 문자열 값이다.
     * @return String 바뀌어진 값을 넘겨준다.
     */
    public static String addComma(Object obj) {

        String str = "";

        if(obj!=null) {
            str = obj+"";

            if(str.length() <= 3) {
                return str;
            } else {
                StringBuffer sb=new StringBuffer(str);
                int index=-3;

                for(int i=str.trim().length(); i>3; i -= 3)
                {
                    sb.insert(i+index,",");
                }

                String chgStr = sb.toString();

                return chgStr;
            }
        } else {
        	LOG.error("DATA가 NULL이군요...");
            return "0";
        }
    }



    /**
     * 주민번호에서 6자리-7자리로 표현한다
     *
     * @param jumin
     *      주민번호13자리
     * @return strJumin xxxxxx-xxxxxxx
     */
    public static String addHyphen(String str) {
        String strJumin = "";

        if (str.length() == 13){
            strJumin = str.substring(0,6)+"-"+str.substring(6,13);
        } else {
            strJumin = str;
        }

        return strJumin;
    }




    /**
     * 영문, 한글 혼용 문자열을 일정한 길이에서 잘라주는 함수
     *
     * @param str        문자열
     * @param limit    자르고자 하는 길이
     * @return        원하는 길이대로 잘라진 문자열
     * @exception Execption error occurs
     * by jamesk
     */
    public static String cutStrLen (String str, int limit)  {

        int len = str.length();

        if (str == null || str.getBytes().length < limit ) return str ;

        int cnt=0, index=0;

        while (index < len && cnt < limit) {
            if (str.charAt(index++) < 256) // 1바이트 문자라면...
            	cnt += 1;     // 길이 1 증가
            else // 2바이트 문자라면...
                cnt += 2;  // 길이 2 증가
        }

        if (index < len) {
            str = str.substring(0, index) + "...";
        }

        return str ;
    }

    public static String cutUniStrLen (String str, int limit)  {

        int len = str.length();

        if (str == null || str.getBytes().length < limit ) return str ;

        int cnt=0, index=0;

        while (index < len && cnt < limit) {
            if (str.charAt(index++) < 256) // 1바이트 문자라면...
            	cnt += 2;     // 길이 1 증가
            else // 2바이트 문자라면...
                cnt += 2;  // 길이 2 증가
        }

        if (index < len) {
            str = str.substring(0, index) + "...";
        }

        return str ;
    }

    /**
     * 한글문자열의 실제길이를 얻는다.
     *
     * @param buf 길이를 구할 문자열
     * @return 문자열의 실제길이
     * @since 1.0
     */
    public static int getHangulLength(String buf)
    {

        int datelen=0;
        for (int i=0; i<buf.length(); i++)
        {
            char nn;
            try
            {
                   nn = buf.charAt(i);
                   Character.UnicodeBlock uniTmp = Character.UnicodeBlock.of(nn);

                if( uniTmp == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO ||
                    uniTmp == Character.UnicodeBlock.HANGUL_JAMO ||
                    uniTmp == Character.UnicodeBlock.HANGUL_SYLLABLES ||
                    uniTmp == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  ||
                    uniTmp == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS ||
                    uniTmp == Character.UnicodeBlock.LATIN_1_SUPPLEMENT ||
                    uniTmp == Character.UnicodeBlock.SPECIALS ||
                    uniTmp == Character.UnicodeBlock.GREEK
                )
                   {
                    datelen ++;
                   }
                datelen ++;
            }
            catch (Exception e)
            {
                   System.out.println("In getHangulLength() Exception : "+e);
              }
        }

        return datelen;
    } // End : int getHangulLength()


    /**
     * Byte로 변환해서 s_offset에서 e_offset만큼의 문자열을 얻는다.
     * @param str 문자열
     * @param s_offset 얻을 문자열의 시작위치
     * @param e_offset 얻을 문자열의 마지막위치
     * @return s_offset에서 e_offset만큼의 문자열
     * @since 1.0
     */
    public static String byteSubString(String str, int s_offset, int e_offset)
    {

        byte b[] = str.getBytes();

        byte f[] = new byte[e_offset-s_offset];
        int m=0;
        for(int i=s_offset;i<e_offset; i++)
        {
            f[m++] = b[i];
        }

        return new String(f);
    }


    /**
     * 한글이 포함된 문자열에서 s_offset에서 e_offset만큼의 문자열을 얻는다.
     * @param str 한글이 포함된 문자열
     * @param s_offset 얻을 문자열의 시작위치
     * @param e_offset 얻을 문자열의 마지막위치
     * @return s_offset에서 e_offset만큼의 문자열
     * @since 1.0
     */
    public static String hangulSubString(String str, int s_offset, int e_offset)
    {
        String  sCnvStr = new String();

        int datelen=0;
        for(int i=0; i<str.length(); i++)
        {
            char nn;

            try
            {
                nn = str.charAt(i);

                Character.UnicodeBlock uniTmp = Character.UnicodeBlock.of(nn);

                //System.out.println("nn=[" + nn + "][" + uniTmp + "]");

                if( uniTmp == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO ||
                    uniTmp == Character.UnicodeBlock.HANGUL_JAMO ||
                    uniTmp == Character.UnicodeBlock.HANGUL_SYLLABLES ||
                    uniTmp == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  ||
                    uniTmp == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS ||
                    uniTmp == Character.UnicodeBlock.LATIN_1_SUPPLEMENT ||
                    uniTmp == Character.UnicodeBlock.SPECIALS ||
                    uniTmp == Character.UnicodeBlock.GREEK
                )
                {
                    // 2Byte문자
                    datelen ++;
                }
                else if(uniTmp == Character.UnicodeBlock.BASIC_LATIN)
                {
                    // 1Byte문자
                }
                else
                {
                    System.out.println("Unknown Charset:"+uniTmp);
                }
                datelen ++;

                if(datelen > s_offset && datelen <= e_offset)
                    sCnvStr += str.substring(i,i+1);
            }
            catch (Exception e)
            {
                System.out.println("In hangulSubString() Exception : "+e);
            }
        }

        return sCnvStr;
    } // End : String hangulSubString()


	/**
	 * @param szText 대상문자열
	 * @param szKey 시작위치로할키워드
	 * @param nLength 자를길이
	 * @param nPrev 키워드위치에서얼마나이전길이만큼포함할것인가
	 * @param isNotag 태그를없앨것인가
	 * @param isAdddot 긴문자일경우"..."을추가할것인가
	 * @return
	 *
	[예]
	"가나다라" 에서 2바이트까지 자르고 싶을경우 strCut("가나다라", null, 2, 0, true, true); 처럼 하시면 됩니다.
	 => 결과 : "가"
	"가나다라" 에서 "다"라는 키워드 기준에서 2바이트까지 자르고싶을경우 strCut("가나다라", "다", 2, 0, true, true); 처럼 하시면 됩니다.
	 => 결과 : "다"
	"가나다라" 에서 "라"라는 키워드 기준으로 그 이전의 4바이트까지 포함하여 6바이트까지 자르고 싶을 경우 strCut("가나다라", "라", 6, 4, true, true); 처럼 하시면 됩니다.
	 => 결과 : "나다라"
	"가나다라" 에서 3바이트를 자를 경우
	 => 결과 : "가"
	"가a나다라" 에서 3바이트를 자를 경우
	 => 결과 : "가a"
	"가나다라" 에서 "나" 키워드 기준으로 이전 1바이트 포함하여 4바이트까지 자를 경우
	 => 결과 : "나"
	 */
	public static String strCut(String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot){  // 문자열 자르기

	    String r_val = szText;
	    int oF = 0, oL = 0, rF = 0, rL = 0;
	    int nLengthPrev = 0;
	    Pattern p = Pattern.compile("<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE);  // 태그제거 패턴

	    if(isNotag) {r_val = p.matcher(r_val).replaceAll("");}  // 태그 제거
	    r_val = r_val.replaceAll("&amp;", "&");
	    r_val = r_val.replaceAll("(!/|\r|\n|&nbsp;)", "");  // 공백제거

	    try {
	      byte[] bytes = r_val.getBytes("UTF-8");     // 바이트로 보관

	      if(szKey != null && !szKey.equals("")) {
	        nLengthPrev = (r_val.indexOf(szKey) == -1)? 0: r_val.indexOf(szKey);  // 일단 위치찾고
	        nLengthPrev = r_val.substring(0, nLengthPrev).getBytes("MS949").length;  // 위치까지길이를 byte로 다시 구한다
	        nLengthPrev = (nLengthPrev-nPrev >= 0)? nLengthPrev-nPrev:0;    // 좀 앞부분부터 가져오도록한다.
	      }

	      // x부터 y길이만큼 잘라낸다. 한글안깨지게.
	      int j = 0;

	      if(nLengthPrev > 0) while(j < bytes.length) {
	        if((bytes[j] & 0x80) != 0) {
	          oF+=2; rF+=3; if(oF+2 > nLengthPrev) {break;} j+=3;
	        } else {if(oF+1 > nLengthPrev) {break;} ++oF; ++rF; ++j;}
	      }

	      j = rF;

	      while(j < bytes.length) {
	        if((bytes[j] & 0x80) != 0) {
	          if(oL+2 > nLength) {break;} oL+=2; rL+=3; j+=3;
	        } else {if(oL+1 > nLength) {break;} ++oL; ++rL; ++j;}
	      }

	      r_val = new String(bytes, rF, rL, "UTF-8");  // charset 옵션

	      if(isAdddot && rF+rL+3 <= bytes.length) {r_val+="...";}  // ...을 붙일지말지 옵션
	    } catch(UnsupportedEncodingException e){ e.printStackTrace(); }

	    return r_val;
	}


    /**
     * URL객체에 해당하는 페이지의 출력문자열을 구한다.<br>
     * @param   uURL 웹페이지 URL 객체
     * @return  웹페이지의 소스문자열
     */
    public static String getURLPageString(URL uURL) throws Exception
    {
        String sLine = "";
        String sDisplayString = "";
        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new InputStreamReader(uURL.openStream() ) );

            while( (sLine = br.readLine() ) != null )
            {
                sDisplayString += sLine;
            }

        }
        catch(Exception e) { throw e; }
        finally { try { if( br != null ) br.close(); } catch(Exception e) {} }

        return sDisplayString;
    }

    /**
     * 소숫점 이하 자리를 지정한 숫자만큼 표시해주며, 반올림이 아닌 절사를 한다.
     * @param obj 숫자형의 객체
     * @param cipher 자릿수
     * @return String
     * 명준민 추가 - 2007.03.08
     */
    public static String jeolsa(Object obj, int cipher) {

        String ret = (obj!=null) ? obj.toString() : "0";

        if(ret.indexOf(".")==-1) {
            ret = ret + ".000000000";
        }

        String[] tmpArr = StringUtil.split(ret, "[.]");

        String str   =  tmpArr[0]; //소숫점 이상
        String remain = tmpArr[1]; //소숫점 이하만 가져온다.

        if(remain.length()<cipher) {
            for(int i=0 ; i<cipher-remain.length() ; i++) {
                remain += "0";
            }
        } else {
            remain = remain.substring(0, cipher);
        }

        ret = str + "." + remain;

        return ret;
    }


    /**
     * HTML 태그 제거
     */
    public static String escapeHtml(Object obj) {

        String result = "";

        if(obj==null) {
            return "";
        } else {
            result = obj.toString();

            result = StringEscapeUtils.escapeHtml(result);
        }

        return result;
    }

    /**
     * 줄바꿈(\n) 문자를 <br>로 변환 (press release)
     */
    public static String pressCrToBr(Object obj) {

        if(obj==null) {
            return "";
        }

        String msg = obj.toString();

        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < msg.length(); i++)
        {
        	if(i < msg.length() - 1){
	            if(msg.charAt(i)=='\n' && msg.charAt(i+1)=='\n')
	            {

	                sb.append(msg.charAt(i));
	                sb.append("<p>");
	            }
	            else
	            {
	                sb.append(msg.charAt(i));
	            }
        	}
        }

        return sb.toString();
    }

    /**
     * 줄바꿈(\n) 문자를 <br>로 변환
     */
    public static String crToBr(Object obj) {

        if(obj==null) {
            return "";
        }

        String msg = obj.toString();

        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '\n')
            {
                sb.append(msg.charAt(i));
                sb.append("<br>");
            }
            else
            {
                sb.append(msg.charAt(i));
            }
        }

        return sb.toString();
    }

    /**
     * 현재 상태의 language 정보를 얻는다.
     * @return
     */
    public static String getLanguage(HttpServletRequest request) {

        String result = "";

        Locale locale = RequestContextUtils.getLocale(request);

        result = locale.getLanguage();

        return result;
    }


    /**
     * CMS에서 생성한 쿠키값(IWAUTH)의 존재유무를 파악
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String hostIp = request.getRequestURL().toString();

        String iwauth = "";

        //로컬 테스트 중에는 작동하지 않는다.
        if(hostIp.indexOf("127.0.0.1")>-1 || hostIp.indexOf("localhost")>-1) {
            iwauth = "test mode...";
            CookieGenerator cg = new CookieGenerator();
            cg.setCookieMaxAge(-1);
            cg.setCookieName("IWAUTH");
            cg.addCookie(response, CookieUtil.getEncodedString(iwauth));
        } else {
            CookieUtil cutil = new CookieUtil(request, response);

            iwauth = StringUtil.nvl(cutil.get("IWAUTH"), "");
        }

        return iwauth;
    }




    /**
     * String 값이 있는지 체크
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
   		return (str != null && str.length() > 0);
    }

    public static boolean isEmpty(String str) {
    	return (str == null || str.length() == 0);
    }

    /**
     * 검색 Keyword 하이라이트
     * - 문자열에 포함된 keyword 를 찾아서 원래의 문자에 HTML tag를 적용 시킨다.
     */
    public static String markKeyword(String str, String keyword, String startTag, String endTag) {
        keyword =
            replace(
                replace(replace(keyword, "[", "\\["), ")", "\\)"),
                "(", "\\(");

        Pattern p = Pattern.compile( keyword , Pattern.CASE_INSENSITIVE );
        Matcher m = p.matcher( str );
        int start = 0;
        int lastEnd = 0;
        StringBuffer sbuf = new StringBuffer();
        while( m.find() ) {
            start = m.start();
            sbuf.append( str.substring(lastEnd, start) )
                .append(startTag + m.group() + endTag );
            lastEnd = m.end();
        }
        return sbuf.append(str.substring(lastEnd)).toString() ;
    }

    public static String getDirLanguage(String locale) {
    	String dirLanguage = "";

		if (locale.equals("ko")) {
			dirLanguage = "kr";
		} else if (locale.equals("zh")) {
			dirLanguage = "cn";
		} else {
			dirLanguage = "en";
		}

		return dirLanguage;
    }


    /**
     * 지정된 문자열의 처음 위치를 찾는다.
     * @param fullStr
     * @param findStr - 찾을 문자열
     * @return
     */
    public static int indexOf(String fullStr, String findStr) {
    	if (isEmpty(fullStr)) {
    		return -1;
    	}

    	return fullStr.indexOf(findStr);
    }

    /**
     * 지정된 문자열의 처음 위치를 찾는다.
     * @param fullStr
     * @param findStr - 찾을 문자열
     * @param fromIndex - 시작 위치
     * @return
     */
    public static int indexOf(String fullStr, String findStr, int fromIndex) {

    	if (isEmpty(fullStr) || isEmpty(findStr)) {
    		return -1;
    	}

    	return fullStr.indexOf(findStr, fromIndex);
    }

    /**
     * Return a substring of original string
     * @param fullStr
     * @param beginIndex - 찾기 시작하는 위치
     * @return
     */
    public static String substring(String fullStr, int beginIndex) {
    	if (isEmpty(fullStr)) {
    		return "";
    	}

    	return fullStr.substring(beginIndex);
    }

    /**
     * Return a substring of original string
     * @param fullStr
     * @param beginIndex - 시작 위치
     * @param endIndex - 끝 위치
     * @return
     */
    public static String substring(String fullStr, int beginIndex, int endIndex) {
    	if (isEmpty(fullStr)) {
    		return "";
    	}

    	return fullStr.substring(beginIndex, endIndex);
    }
    
	public static String filterString(String strVal, boolean boFlag){

		String strReturn = "";	
		
        if(isNullStr(strVal).equals("")) return strReturn;
		
		strReturn = strVal;
				
    	if(boFlag){
    		return charsReplace(strReturn);
    	}else{
    		return strReturn;
    	}
	}    

	/**
     * null --> 공백으로
     * @param text
     * @param splitword
     * @return
     */
	public static String isNullStr(String text)
	{
		if(text == null){
			text = "";
		}
		return text;
	}

	public static String charsReplaceRevrt(String strVal){
		
		String strReturn = "";
		
		if(isNullStr(strVal).equals("")) return strReturn;

		strReturn = strVal;
			
//		strReturn = StringUtils.replace(strReturn, "&#59;", ";");
		strReturn = StringUtils.replace(strReturn, "&#39;", "'");
		strReturn = StringUtils.replace(strReturn, "&lt;", "<");
		strReturn = StringUtils.replace(strReturn, "&gt;", ">");	
		
		strReturn = StringUtils.replace(strReturn, "&#39;","%27");
//		strReturn = StringUtils.replace(strReturn, "&#35;", "#");
		//strReturn = StringUtils.replace(strReturn, "&#64;", "@");
		//strReturn = StringUtils.replace(strReturn, "&#43;", "+");
		//strReturn = StringUtils.replace(strReturn, "&#45;", "-");
		strReturn = StringUtils.replace(strReturn, "&#37;", "%");
				
		return strReturn;
	}	
	
	public static String charsReplace(String strVal){
		
		String strReturn = "";
		
		if(isNullStr(strVal).equals("")) return strReturn;

		strReturn = strVal;

		strReturn = StringUtils.replace(strReturn, "--", "");			
//		strReturn = StringUtils.replace(strReturn, ";", "&#59;");
		strReturn = StringUtils.replace(strReturn, "'", "&#39;");
		strReturn = StringUtils.replace(strReturn, "<", "&lt;");
		strReturn = StringUtils.replace(strReturn, ">", "&gt;");	
		
		strReturn = StringUtils.replace(strReturn, "%27","&#39;");
		//strReturn = StringUtils.replace(strReturn, "#", "&#35;");
		//strReturn = StringUtils.replace(strReturn, "@", "&#64;");
		//strReturn = StringUtils.replace(strReturn, "+", "&#43;");
		//strReturn = StringUtils.replace(strReturn, "-", "&#45;");
		strReturn = StringUtils.replace(strReturn, "%", "&#37;");
				
		return strReturn;
	}		
	
    /**
     * 문자열 split
     * @param text
     * @param splitword
     * @return
     */
	public static String [] StringSplit(String text, String splitword)
	{
		StringTokenizer oToken = new StringTokenizer(text, splitword);
		
		String [] strarrReturn = new String[oToken.countTokens()];
		
		for(int i=0; i<strarrReturn.length; i++)
		{
			strarrReturn[i] = oToken.nextToken();
		}
		
		return strarrReturn;
	} 		
}

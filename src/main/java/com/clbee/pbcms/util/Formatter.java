package com.clbee.pbcms.util;

/**
 * <p>Title: giro INTERNET GIRO</p>
 * <p>Description: Format 관련 Utility</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: giro</p>
 * @author Sungju Kang(sonaki@giro.or.kr)
 * @version 1.0
 */

import java.text.NumberFormat;

public class Formatter
{
	public final static int FILL_LEFT = 1;        // 좌측채움
	public final static int FILL_RIGHT = 2;       // 우측채움

	public final static char WILDCARD_CHAR = '*'; // 와일드카드 문자
	public final static char FILL_ZERO = '0';     // '0'으로 채움
	public final static char FILL_SPACE = ' ';    // SPACE로 채움

	/**
	 * 숫자문자열에 콤마(,) 찍음
	 * @param src 숫자문자열
	 * @return 콤마가 포함된 숫자 문자열
	 */
	public static String commaMoney(String src)
	{
		try
		{
			return commaMoney(Double.parseDouble(src));
		}
		catch(Exception e)
		{
			return "";
		}
	}

	/**
	 * 숫자에 콤마(,) 찍음
	 * @param src 콤마찍을 숫자
	 * @return 콤마가 포함된 숫자 문자열
	 */
	public static String commaMoney(double src)
	{
		return NumberFormat.getInstance().format(src);
	}

	/**
	 * 문자열의 특정부분(from~to)을 특정문자료 교환하여 숨김
	 * @param src 특정문자열
	 * @param from 숨길부분(from)
	 * @param to 숨길부분(to)
	 * @param hideCharacter 숨길 특정문자
	 * @return 특정부분이 숨겨진 문자열
	 */
	public static String hide(String src, int from, int to, char hideCharacter)
	{
		if(src == null || from < 0 || to > src.length())
			return src;

		StringBuffer hideString = new StringBuffer();
		String part1 = src.substring(0, from);
//    String part2 = src.substring(to, src.length());  2003-03-17 강성주
		String part2 = src.substring(to);

		for(int i = from; i < to; i++)
			hideString.append(hideCharacter);

		return part1 + hideString.toString() + part2;
	}

	/**
	 * 같은 문자열(와일드카드이용가능)인지 비교(길이비교안함)
	 * @param target1 첫번째문자열
	 * @param target2 두번째문자열
	 * @return 와일드 카드를 포함하여 같은 문자열인지 여부(true:같은문자열/false:다른문자열)
	 */
	public static boolean isSameString(String target1, String target2)
	{
		return isSameString(target1, target2, false);
	}

	/**
	 * 같은 문자열(와일드카드이용가능)인지 비교
	 * @param target1 첫번째문자열
	 * @param target2 두번째문자열
	 * @param isLengthCheck 길이비교도 할것인지 여부
	 * @return 와일드 카드를 포함하여 같은 문자열인지 여부(true:같은문자열/false:다른문자열)
	 */
	public static boolean isSameString(String target1, String target2, boolean isLengthCheck)
	{
		boolean result = true;

		if(target1 != target2)
		{
			int len1 = target1.length();
			int len2 = target2.length();

			if(!isLengthCheck || len1 == len2)
			{
				if(!target1.equals(target2))
				{
					for(int i = 0; i < len1 && i < len2; i++)
					{
						if(target1.charAt(i) != target2.charAt(i) &&
							 target1.charAt(i) != WILDCARD_CHAR &&
							 target2.charAt(i) != WILDCARD_CHAR)
						{
							result = false;
							break;
						}
					}
				}
			}
			else
				result = false;
		}

		return result;
	}

	/**
	 * 해당문자열이 숫자인지 알아봄
	 * @param data 해당문자열
	 * @return 숫자여부(true:숫자/false:그외의경우)
	 */
	public static boolean isNumeric(String data)
	{
		for(int i = 0; i < data.length(); i++)
			if(data.charAt(i) < '0' || data.charAt(i) > '9')
				return false;
		return true;
	}

	/**
	 * random number 생성
	 * @param max random number의 최대값
	 * @return 0~max-1 까지의 random 값
	 */
	public static int getRandomNumber(int max)
	{
		return (int)Math.round((Math.random() * max) - 0.5f);
	}

	public static String replace(String src, String findStr, String replaceStr)
	{
		if(src == null || findStr == null)
			return src;

		StringBuffer result = new StringBuffer();
		int findStrLen = findStr.length();
		int srcLen = src.length();
		int i;

		for(i = 0; i <= srcLen - findStrLen; i++)
		{
			String str = src.substring(i, i + findStrLen);
			if(str.equals(findStr))
			{
				result.append(replaceStr);
				i += findStrLen - 1;
			}
			else
				result.append(str.substring(0, 1));
		}

		if(i < srcLen)
			result.append(src.substring(i, srcLen));

		return result.toString();
	}

	public static String substring(String src, int length)
	{
		if(src != null && src.length() > length)
			return src.substring(0, length);
		else
			return src;
	}
	
	public static String nvl(String str, String replaceStr)
	{
		if(str == null || str.equals(""))
			return replaceStr;
		else
			return str;
	}

	public static String nvl(String str)
	{
		return nvl(str, "0");
	}
	
	/**
	 * 문자열로 된 숫자에 대한 FIX작업(ex)000-1000 -> -1000)
	 * @param strNum 문자열 숫자
	 * @return FIX된 문자열
	 */
	public static String strNumberFix(String strNum) 
	{
		if(strNum != null) {
			int idx = strNum.indexOf("-");
			if(idx > 0)
				strNum = strNum.substring(idx);
		}
		return strNum;
	}
	
}

package com.clbee.pbcms.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharSetUtils;
import org.apache.commons.lang.WordUtils;
 
public class StringUtils
{

    public StringUtils() { }

    public static boolean isEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str)
    {
        return str != null && str.length() > 0;
    }

    public static boolean isBlank(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return true;
        for(int i = 0; i < strLen; i++)
            if(!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }

    public static boolean isNotBlank(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return false;
        for(int i = 0; i < strLen; i++)
            if(!Character.isWhitespace(str.charAt(i)))
                return true;

        return false;
    }   

    public static String clean(String str)
    {
        return str != null ? str.trim() : "";
    }

    public static String trim(String str)
    {
        return str != null ? str.trim() : null;
    }

    public static String trimToNull(String str)
    {
        String ts = trim(str);
        return ts != null && ts.length() != 0 ? ts : null;
    }

    public static String trimToEmpty(String str)
    {
        return str != null ? str.trim() : "";
    }

    public static String strip(String str)
    {
        return strip(str, null);
    }

    public static String stripToNull(String str)
    {
        if(str == null)
        {
            return null;
        } else
        {
            str = strip(str, null);
            return str.length() != 0 ? str : null;
        }
    }

    public static String stripToEmpty(String str)
    {
        return str != null ? strip(str, null) : "";
    }

    public static String strip(String str, String stripChars)
    {
        if(str == null || str.length() == 0)
        {
            return str;
        } else
        {
            str = stripStart(str, stripChars);
            return stripEnd(str, stripChars);
        }
    }

    public static String stripStart(String str, String stripChars)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        int start = 0;
        if(stripChars == null)
        {
            for(; start != strLen && Character.isWhitespace(str.charAt(start)); start++);
        } else
        {
            if(stripChars.length() == 0)
                return str;
            for(; start != strLen && stripChars.indexOf(str.charAt(start)) != -1; start++);
        }
        return str.substring(start);
    }

    public static String stripEnd(String str, String stripChars)
    {
        int end;
        if(str == null || (end = str.length()) == 0)
            return str;
        if(stripChars == null)
        {
            for(; end != 0 && Character.isWhitespace(str.charAt(end - 1)); end--);
        } else
        {
            if(stripChars.length() == 0)
                return str;
            for(; end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1; end--);
        }
        return str.substring(0, end);
    }

    public static String[] stripAll(String strs[])
    {
        return stripAll(strs, null);
    }

    public static String[] stripAll(String strs[], String stripChars)
    {
        int strsLen;
        if(strs == null || (strsLen = strs.length) == 0)
            return strs;
        String newArr[] = new String[strsLen];
        for(int i = 0; i < strsLen; i++)
            newArr[i] = strip(strs[i], stripChars);

        return newArr;
    }

    public static boolean equals(String str1, String str2)
    {
        return str1 != null ? str1.equals(str2) : str2 == null;
    }

    public static boolean equalsIgnoreCase(String str1, String str2)
    {
        return str1 != null ? str1.equalsIgnoreCase(str2) : str2 == null;
    }

    public static int indexOf(String str, char searchChar)
    {
        if(str == null || str.length() == 0)
            return -1;
        else
            return str.indexOf(searchChar);
    }

    public static int indexOf(String str, char searchChar, int startPos)
    {
        if(str == null || str.length() == 0)
            return -1;
        else
            return str.indexOf(searchChar, startPos);
    }

    public static int indexOf(String str, String searchStr)
    {
        if(str == null || searchStr == null)
            return -1;
        else
            return str.indexOf(searchStr);
    }

    public static int indexOf(String str, String searchStr, int startPos)
    {
        if(str == null || searchStr == null)
            return -1;
        if(searchStr.length() == 0 && startPos >= str.length())
            return str.length();
        else
            return str.indexOf(searchStr, startPos);
    }

    public static int lastIndexOf(String str, char searchChar)
    {
        if(str == null || str.length() == 0)
            return -1;
        else
            return str.lastIndexOf(searchChar);
    }

    public static int lastIndexOf(String str, char searchChar, int startPos)
    {
        if(str == null || str.length() == 0)
            return -1;
        else
            return str.lastIndexOf(searchChar, startPos);
    }

    public static int lastIndexOf(String str, String searchStr)
    {
        if(str == null || searchStr == null)
            return -1;
        else
            return str.lastIndexOf(searchStr);
    }

    public static int lastIndexOf(String str, String searchStr, int startPos)
    {
        if(str == null || searchStr == null)
            return -1;
        else
            return str.lastIndexOf(searchStr, startPos);
    }

    public static boolean contains(String str, char searchChar)
    {
        if(str == null || str.length() == 0)
            return false;
        else
            return str.indexOf(searchChar) >= 0;
    }

    public static boolean contains(String str, String searchStr)
    {
        if(str == null || searchStr == null)
            return false;
        else
            return str.indexOf(searchStr) >= 0;
    }

    public static int indexOfAny(String str, char searchChars[])
    {
        if(str == null || str.length() == 0 || searchChars == null || searchChars.length == 0)
            return -1;
        for(int i = 0; i < str.length(); i++)
        {
            char ch = str.charAt(i);
            for(int j = 0; j < searchChars.length; j++)
                if(searchChars[j] == ch)
                    return i;

        }

        return -1;
    }

    public static int indexOfAny(String str, String searchChars)
    {
        if(str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0)
            return -1;
        else
            return indexOfAny(str, searchChars.toCharArray());
    }

    public static int indexOfAnyBut(String str, char searchChars[])
    {
        if(str == null || str.length() == 0 || searchChars == null || searchChars.length == 0)
            return -1;
        int i = 0;
label0:
        do
        {
label1:
            {
                if(i >= str.length())
                    break label0;
                char ch = str.charAt(i);
                for(int j = 0; j < searchChars.length; j++)
                    if(searchChars[j] == ch)
                        break label1;

                return i;
            }
            i++;
        } while(true);
        return -1;
    }

    public static int indexOfAnyBut(String str, String searchChars)
    {
        if(str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0)
            return -1;
        for(int i = 0; i < str.length(); i++)
            if(searchChars.indexOf(str.charAt(i)) < 0)
                return i;

        return -1;
    }

    public static boolean containsOnly(String str, char valid[])
    {
        if(valid == null || str == null)
            return false;
        if(str.length() == 0)
            return true;
        if(valid.length == 0)
            return false;
        else
            return indexOfAnyBut(str, valid) == -1;
    }

    public static boolean containsOnly(String str, String validChars)
    {
        if(str == null || validChars == null)
            return false;
        else
            return containsOnly(str, validChars.toCharArray());
    }

    public static boolean containsNone(String str, char invalidChars[])
    {
        if(str == null || invalidChars == null)
            return true;
        int strSize = str.length();
        int validSize = invalidChars.length;
        for(int i = 0; i < strSize; i++)
        {
            char ch = str.charAt(i);
            for(int j = 0; j < validSize; j++)
                if(invalidChars[j] == ch)
                    return false;

        }

        return true;
    }

    public static boolean containsNone(String str, String invalidChars)
    {
        if(str == null || invalidChars == null)
            return true;
        else
            return containsNone(str, invalidChars.toCharArray());
    }

    public static int indexOfAny(String str, String searchStrs[])
    {
        if(str == null || searchStrs == null)
            return -1;
        int sz = searchStrs.length;
        int ret = 0x7fffffff;
        int tmp = 0;
        for(int i = 0; i < sz; i++)
        {
            String search = searchStrs[i];
            if(search == null)
                continue;
            tmp = str.indexOf(search);
            if(tmp != -1 && tmp < ret)
                ret = tmp;
        }

        return ret != 0x7fffffff ? ret : -1;
    }

    public static int lastIndexOfAny(String str, String searchStrs[])
    {
        if(str == null || searchStrs == null)
            return -1;
        int sz = searchStrs.length;
        int ret = -1;
        int tmp = 0;
        for(int i = 0; i < sz; i++)
        {
            String search = searchStrs[i];
            if(search == null)
                continue;
            tmp = str.lastIndexOf(search);
            if(tmp > ret)
                ret = tmp;
        }

        return ret;
    }

    public static String substring(String str, int start)
    {
        if(str == null)
            return null;
        if(start < 0)
            start = str.length() + start;
        if(start < 0)
            start = 0;
        if(start > str.length())
            return "";
        else
            return str.substring(start);
    }

    public static String substring(String str, int start, int end)
    {
        if(str == null)
            return null;
        if(end < 0)
            end = str.length() + end;
        if(start < 0)
            start = str.length() + start;
        if(end > str.length())
            end = str.length();
        if(start > end)
            return "";
        if(start < 0)
            start = 0;
        if(end < 0)
            end = 0;
        return str.substring(start, end);
    }

    public static String left(String str, int len)
    {
        if(str == null)
            return null;
        if(len < 0)
            return "";
        if(str.length() <= len)
            return str;
        else
            return str.substring(0, len);
    }

    public static String right(String str, int len)
    {
        if(str == null)
            return null;
        if(len < 0)
            return "";
        if(str.length() <= len)
            return str;
        else
            return str.substring(str.length() - len);
    }

    public static String mid(String str, int pos, int len)
    {
        if(str == null)
            return null;
        if(len < 0 || pos > str.length())
            return "";
        if(pos < 0)
            pos = 0;
        if(str.length() <= pos + len)
            return str.substring(pos);
        else
            return str.substring(pos, pos + len);
    }

    public static String substringBefore(String str, String separator)
    {
        if(str == null || separator == null || str.length() == 0)
            return str;
        if(separator.length() == 0)
            return "";
        int pos = str.indexOf(separator);
        if(pos == -1)
            return str;
        else
            return str.substring(0, pos);
    }

    public static String substringAfter(String str, String separator)
    {
        if(str == null || str.length() == 0)
            return str;
        if(separator == null)
            return "";
        int pos = str.indexOf(separator);
        if(pos == -1)
            return "";
        else
            return str.substring(pos + separator.length());
    }

    public static String substringBeforeLast(String str, String separator)
    {
        if(str == null || separator == null || str.length() == 0 || separator.length() == 0)
            return str;
        int pos = str.lastIndexOf(separator);
        if(pos == -1)
            return str;
        else
            return str.substring(0, pos);
    }

    public static String substringAfterLast(String str, String separator)
    {
        if(str == null || str.length() == 0)
            return str;
        if(separator == null || separator.length() == 0)
            return "";
        int pos = str.lastIndexOf(separator);
        if(pos == -1 || pos == str.length() - separator.length())
            return "";
        else
            return str.substring(pos + separator.length());
    }

    public static String substringBetween(String str, String tag)
    {
        return substringBetween(str, tag, tag);
    }

    public static String substringBetween(String str, String open, String close)
    {
        if(str == null || open == null || close == null)
            return null;
        int start = str.indexOf(open);
        if(start != -1)
        {
            int end = str.indexOf(close, start + open.length());
            if(end != -1)
                return str.substring(start + open.length(), end);
        }
        return null;
    }

  

    public static String getNestedString(String str, String tag)
    {
        return substringBetween(str, tag, tag);
    }

    

    public static String getNestedString(String str, String open, String close)
    {
        return substringBetween(str, open, close);
    }

    public static String[] split(String str)
    {
        return split(str, null, -1);
    }

    public static String[] split(String str, char separatorChar)
    {
        if(str == null)
            return null;
        int len = str.length();
        if(len == 0)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        List list = new ArrayList();
        int i = 0;
        int start = 0;
        boolean match = false;
        while(i < len) 
            if(str.charAt(i) == separatorChar)
            {
                if(match)
                {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
            } else
            {
                match = true;
                i++;
            }
        if(match)
            list.add(str.substring(start, i));
        return (String[])list.toArray(new String[list.size()]);
    }

    public static String[] split(String str, String separatorChars)
    {
        return split(str, separatorChars, -1);
    }

    public static String[] split(String str, String separatorChars, int max)
    {
        if(str == null)
            return null;
        int len = str.length();
        if(len == 0)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        List list = new ArrayList();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;
        if(separatorChars == null)
            while(i < len) 
                if(Character.isWhitespace(str.charAt(i)))
                {
                    if(match)
                    {
                        if(sizePlus1++ == max)
                            i = len;
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                } else
                {
                    match = true;
                    i++;
                }
        else
        if(separatorChars.length() == 1)
        {
            char sep = separatorChars.charAt(0);
            while(i < len) 
                if(str.charAt(i) == sep)
                {
                    if(match)
                    {
                        if(sizePlus1++ == max)
                            i = len;
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                } else
                {
                    match = true;
                    i++;
                }
        } else
        {
            while(i < len) 
                if(separatorChars.indexOf(str.charAt(i)) >= 0)
                {
                    if(match)
                    {
                        if(sizePlus1++ == max)
                            i = len;
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                } else
                {
                    match = true;
                    i++;
                }
        }
        if(match)
            list.add(str.substring(start, i));
        return (String[])list.toArray(new String[list.size()]);
    }   

    public static String concatenate(Object array[])
    {
        return join(array, ((String) (null)));
    }

    public static String join(Object array[])
    {
        return join(array, ((String) (null)));
    }

    public static String join(Object array[], char separator)
    {
        if(array == null)
            return null;
        int arraySize = array.length;
        int bufSize = arraySize != 0 ? ((array[0] != null ? array[0].toString().length() : 16) + 1) * arraySize : 0;
        StringBuffer buf = new StringBuffer(bufSize);
        for(int i = 0; i < arraySize; i++)
        {
            if(i > 0)
                buf.append(separator);
            if(array[i] != null)
                buf.append(array[i]);
        }

        return buf.toString();
    }

    public static String join(Object array[], String separator)
    {
        if(array == null)
            return null;
        if(separator == null)
            separator = "";
        int arraySize = array.length;
        int bufSize = arraySize != 0 ? arraySize * ((array[0] != null ? array[0].toString().length() : 16) + (separator == null ? 0 : separator.length())) : 0;
        StringBuffer buf = new StringBuffer(bufSize);
        for(int i = 0; i < arraySize; i++)
        {
            if(separator != null && i > 0)
                buf.append(separator);
            if(array[i] != null)
                buf.append(array[i]);
        }

        return buf.toString();
    }

    public static String join(Iterator iterator, char separator)
    {
        if(iterator == null)
            return null;
        StringBuffer buf = new StringBuffer(256);
        do
        {
            if(!iterator.hasNext())
                break;
            Object obj = iterator.next();
            if(obj != null)
                buf.append(obj);
            if(iterator.hasNext())
                buf.append(separator);
        } while(true);
        return buf.toString();
    }

    public static String join(Iterator iterator, String separator)
    {
        if(iterator == null)
            return null;
        StringBuffer buf = new StringBuffer(256);
        do
        {
            if(!iterator.hasNext())
                break;
            Object obj = iterator.next();
            if(obj != null)
                buf.append(obj);
            if(separator != null && iterator.hasNext())
                buf.append(separator);
        } while(true);
        return buf.toString();
    }

    public static String deleteSpaces(String str)
    {
        if(str == null)
            return null;
        else
            return CharSetUtils.delete(str, " \t\r\n\b");
    }

    public static String deleteWhitespace(String str)
    {
        if(str == null)
            return null;
        int sz = str.length();
        StringBuffer buffer = new StringBuffer(sz);
        for(int i = 0; i < sz; i++)
            if(!Character.isWhitespace(str.charAt(i)))
                buffer.append(str.charAt(i));

        return buffer.toString();
    }

    public static String replaceOnce(String text, String repl, String with)
    {
        return replace(text, repl, with, 1);
    }

    public static String replace(String text, String repl, String with)
    {
        return replace(text, repl, with, -1);
    }

    public static String replace(String text, String repl, String with, int max)
    {
        if(text == null || repl == null || with == null || repl.length() == 0 || max == 0)
            return text;
        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        int end = 0;
        do
        {
            if((end = text.indexOf(repl, start)) == -1)
                break;
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();
        } while(--max != 0);
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String replaceChars(String str, char searchChar, char replaceChar)
    {
        if(str == null)
            return null;
        else
            return str.replace(searchChar, replaceChar);
    }

    public static String replaceChars(String str, String searchChars, String replaceChars)
    {
        if(str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0)
            return str;
        char chars[] = str.toCharArray();
        int len = chars.length;
        boolean modified = false;
        int i = 0;
        for(int isize = searchChars.length(); i < isize; i++)
        {
            char searchChar = searchChars.charAt(i);
            if(replaceChars == null || i >= replaceChars.length())
            {
                int pos = 0;
                for(int j = 0; j < len; j++)
                    if(chars[j] != searchChar)
                        chars[pos++] = chars[j];
                    else
                        modified = true;

                len = pos;
                continue;
            }
            for(int j = 0; j < len; j++)
                if(chars[j] == searchChar)
                {
                    chars[j] = replaceChars.charAt(i);
                    modified = true;
                }

        }

        if(!modified)
            return str;
        else
            return new String(chars, 0, len);
    }    

    public static String overlayString(String text, String overlay, int start, int end)
    {
        return (new StringBuffer(((start + overlay.length() + text.length()) - end) + 1)).append(text.substring(0, start)).append(overlay).append(text.substring(end)).toString();
    }

    public static String overlay(String str, String overlay, int start, int end)
    {
        if(str == null)
            return null;
        if(overlay == null)
            overlay = "";
        int len = str.length();
        if(start < 0)
            start = 0;
        if(start > len)
            start = len;
        if(end < 0)
            end = 0;
        if(end > len)
            end = len;
        if(start > end)
        {
            int temp = start;
            start = end;
            end = temp;
        }
        return (new StringBuffer(((len + start) - end) + overlay.length() + 1)).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
    }

    public static String chomp(String str)
    {
        if(str == null || str.length() == 0)
            return str;
        if(str.length() == 1)
        {
            char ch = str.charAt(0);
            if(ch == '\r' || ch == '\n')
                return "";
            else
                return str;
        }
        int lastIdx = str.length() - 1;
        char last = str.charAt(lastIdx);
        if(last == '\n')
        {
            if(str.charAt(lastIdx - 1) == '\r')
                lastIdx--;
        } else
        if(last != '\r')
            lastIdx++;
        return str.substring(0, lastIdx);
    }

    public static String chomp(String str, String separator)
    {
        if(str == null || str.length() == 0 || separator == null)
            return str;
        if(str.endsWith(separator))
            return str.substring(0, str.length() - separator.length());
        else
            return str;
    }


    public static String chompLast(String str)
    {
        return chompLast(str, "\n");
    }

   

    public static String chompLast(String str, String sep)
    {
        if(str.length() == 0)
            return str;
        String sub = str.substring(str.length() - sep.length());
        if(sep.equals(sub))
            return str.substring(0, str.length() - sep.length());
        else
            return str;
    }

    
    public static String getChomp(String str, String sep)
    {
        int idx = str.lastIndexOf(sep);
        if(idx == str.length() - sep.length())
            return sep;
        if(idx != -1)
            return str.substring(idx);
        else
            return "";
    }

    public static String prechomp(String str, String sep)
    {
        int idx = str.indexOf(sep);
        if(idx != -1)
            return str.substring(idx + sep.length());
        else
            return str;
    }

    
    public static String getPrechomp(String str, String sep)
    {
        int idx = str.indexOf(sep);
        if(idx != -1)
            return str.substring(0, idx + sep.length());
        else
            return "";
    }

    public static String chop(String str)
    {
        if(str == null)
            return null;
        int strLen = str.length();
        if(strLen < 2)
            return "";
        int lastIdx = strLen - 1;
        String ret = str.substring(0, lastIdx);
        char last = str.charAt(lastIdx);
        if(last == '\n' && ret.charAt(lastIdx - 1) == '\r')
            return ret.substring(0, lastIdx - 1);
        else
            return ret;
    }
	
    public static String chopNewline(String str)
    {
        int lastIdx = str.length() - 1;
        if(lastIdx <= 0)
            return "";
        char last = str.charAt(lastIdx);
        if(last == '\n')
        {
            if(str.charAt(lastIdx - 1) == '\r')
                lastIdx--;
        } else
        {
            lastIdx++;
        }
        return str.substring(0, lastIdx);
    }

    public static String escape(String str)
    {
    	
//    	return EncodingUtil.encodeURIComponent(str);
    	
    	
    	StringBuffer sb = new StringBuffer();
	    String ncStr = "*+-./0123456789@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
	    char c;
	       
	    for(int i=0;i<str.length();i++){
	    	c = str.charAt(i);
	    	if(c>0x7f){
	    		sb.append("%u"); 
	    		sb.append(Integer.toHexString((int)c).toUpperCase());
	    	}
	    	else if(ncStr.indexOf((int)c)==-1){
	    		sb.append('%');
	    		if(c<=0xf)
	    			sb.append('0');
                 	sb.append(Integer.toHexString((int)c).toUpperCase());
	    	}
	    	else 
                 sb.append(c);
	    }
	       
	       return sb.toString();
    }
    
    
    public static String unescape(String str)
    {   
    	
//    	return EncodingUtil.decodeURIComponent(str);
    	
    	StringBuffer sb = new StringBuffer();
    	char[]	arrStr	= str.toCharArray();
    	
    	for(int i=0; i<arrStr.length; i++){
    		if(arrStr[i]=='%'){
    			String hex;
    			if(arrStr[i+1]=='u'){ //유니코드
    				hex = str.substring(i+2, i+6);
    				i += 5;
    			}else{ //ascii
    				hex = str.substring(i+1, i+3);
    				i += 2;
    			}
    			try{
    				sb.append(Character.toChars(Integer.parseInt(hex,16)));
    			}catch(NumberFormatException e){
    				sb.append("%");
    				i -= (hex.length()>2 ? 5 : 2);
    			}
    		}else{
    			sb.append(arrStr[i]);
    		}
    	}
    	
    	return sb.toString();
    }
    
   
    public static String repeat(String str, int repeat)
    {
        if(str == null)
            return null;
        if(repeat <= 0)
            return "";
        int inputLength = str.length();
        if(repeat == 1 || inputLength == 0)
            return str;
        if(inputLength == 1 && repeat <= 8192)
            return padding(repeat, str.charAt(0));
        int outputLength = inputLength * repeat;
        switch(inputLength)
        {
        case 1: // '\001'
            char ch = str.charAt(0);
            char output1[] = new char[outputLength];
            for(int i = repeat - 1; i >= 0; i--)
                output1[i] = ch;

            return new String(output1);

        case 2: // '\002'
            char ch0 = str.charAt(0);
            char ch1 = str.charAt(1);
            char output2[] = new char[outputLength];
            for(int i = repeat * 2 - 2; i >= 0; i--)
            {
                output2[i] = ch0;
                output2[i + 1] = ch1;
                i--;
            }

            return new String(output2);
        }
        StringBuffer buf = new StringBuffer(outputLength);
        for(int i = 0; i < repeat; i++)
            buf.append(str);

        return buf.toString();
    }

    private static String padding(int repeat, char padChar)
    {
        String pad = PADDING[padChar];
        if(pad == null)
            pad = String.valueOf(padChar);
        for(; pad.length() < repeat; pad = pad.concat(pad));
        PADDING[padChar] = pad;
        return pad.substring(0, repeat);
    }

    public static String rightPad(String str, int size)
    {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar)
    {
        if(str == null)
            return null;
        int pads = size - str.length();
        if(pads <= 0)
            return str;
        if(pads > 8192)
            return rightPad(str, size, String.valueOf(padChar));
        else
            return str.concat(padding(pads, padChar));
    }

    public static String rightPad(String str, int size, String padStr)
    {
        if(str == null)
            return null;
        if(padStr == null || padStr.length() == 0)
            padStr = " ";
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if(pads <= 0)
            return str;
        if(padLen == 1 && pads <= 8192)
            return rightPad(str, size, padStr.charAt(0));
        if(pads == padLen)
            return str.concat(padStr);
        if(pads < padLen)
            return str.concat(padStr.substring(0, pads));
        char padding[] = new char[pads];
        char padChars[] = padStr.toCharArray();
        for(int i = 0; i < pads; i++)
            padding[i] = padChars[i % padLen];

        return str.concat(new String(padding));
    }

    public static String leftPad(String str, int size)
    {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar)
    {
        if(str == null)
            return null;
        int pads = size - str.length();
        if(pads <= 0)
            return str;
        if(pads > 8192)
            return leftPad(str, size, String.valueOf(padChar));
        else
            return padding(pads, padChar).concat(str);
    }

    public static String leftPad(String str, int size, String padStr)
    {
        if(str == null)
            return null;
        if(padStr == null || padStr.length() == 0)
            padStr = " ";
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if(pads <= 0)
            return str;
        if(padLen == 1 && pads <= 8192)
            return leftPad(str, size, padStr.charAt(0));
        if(pads == padLen)
            return padStr.concat(str);
        if(pads < padLen)
            return padStr.substring(0, pads).concat(str);
        char padding[] = new char[pads];
        char padChars[] = padStr.toCharArray();
        for(int i = 0; i < pads; i++)
            padding[i] = padChars[i % padLen];

        return (new String(padding)).concat(str);
    }

    public static String center(String str, int size)
    {
        return center(str, size, ' ');
    }

    public static String center(String str, int size, char padChar)
    {
        if(str == null || size <= 0)
            return str;
        int strLen = str.length();
        int pads = size - strLen;
        if(pads <= 0)
        {
            return str;
        } else
        {
            str = leftPad(str, strLen + pads / 2, padChar);
            str = rightPad(str, size, padChar);
            return str;
        }
    }

    public static String center(String str, int size, String padStr)
    {
        if(str == null || size <= 0)
            return str;
        if(padStr == null || padStr.length() == 0)
            padStr = " ";
        int strLen = str.length();
        int pads = size - strLen;
        if(pads <= 0)
        {
            return str;
        } else
        {
            str = leftPad(str, strLen + pads / 2, padStr);
            str = rightPad(str, size, padStr);
            return str;
        }
    }

    public static String upperCase(String str)
    {
        if(str == null)
            return null;
        else
            return str.toUpperCase();
    }

    public static String lowerCase(String str)
    {
        if(str == null)
            return null;
        else
            return str.toLowerCase();
    }

    public static String capitalize(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        else
            return (new StringBuffer(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }
	
    public static String capitalise(String str)
    {
        return capitalize(str);
    }

    public static String uncapitalize(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        else
            return (new StringBuffer(strLen)).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }    

    public static String uncapitalise(String str)
    {
        return uncapitalize(str);
    }

    public static String swapCase(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        StringBuffer buffer = new StringBuffer(strLen);
        char ch = '\0';
        for(int i = 0; i < strLen; i++)
        {
            ch = str.charAt(i);
            if(Character.isUpperCase(ch))
                ch = Character.toLowerCase(ch);
            else
            if(Character.isTitleCase(ch))
                ch = Character.toLowerCase(ch);
            else
            if(Character.isLowerCase(ch))
                ch = Character.toUpperCase(ch);
            buffer.append(ch);
        }

        return buffer.toString();
    }   

    public static String capitaliseAllWords(String str)
    {
        return WordUtils.capitalize(str);
    }

    public static int countMatches(String str, String sub)
    {
        if(str == null || str.length() == 0 || sub == null || sub.length() == 0)
            return 0;
        int count = 0;
        for(int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length())
            count++;

        return count;
    }

    public static boolean isAlpha(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isLetter(str.charAt(i)))
                return false;

        return true;
    }

    public static boolean isAlphaSpace(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isLetter(str.charAt(i)) && str.charAt(i) != ' ')
                return false;

        return true;
    }

    public static boolean isAlphanumeric(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isLetterOrDigit(str.charAt(i)))
                return false;

        return true;
    }

    public static boolean isAlphanumericSpace(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isLetterOrDigit(str.charAt(i)) && str.charAt(i) != ' ')
                return false;

        return true;
    }

    public static boolean isNumeric(String str)
    {
        if(isBlank(str))
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isDigit(str.charAt(i)))
                return false;

        return true;
    }

    public static boolean isNumericSpace(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isDigit(str.charAt(i)) && str.charAt(i) != ' ')
                return false;

        return true;
    }

    public static boolean isWhitespace(String str)
    {
        if(str == null)
            return false;
        int sz = str.length();
        for(int i = 0; i < sz; i++)
            if(!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }

    public static String defaultString(String str)
    {
        return str != null ? str : "";
    }

    public static String defaultString(String str, String defaultStr)
    {
        return str != null ? str : defaultStr;
    }

    public static String reverse(String str)
    {
        if(str == null)
            return null;
        else
            return (new StringBuffer(str)).reverse().toString();
    }

    public static String reverseDelimited(String str, char separatorChar)
    {
        if(str == null)
        {
            return null;
        } else
        {
            String strs[] = split(str, separatorChar);
            ArrayUtils.reverse(strs);
            return join(strs, separatorChar);
        }
    }    

    public static String reverseDelimitedString(String str, String separatorChars)
    {
        if(str == null)
            return null;
        String strs[] = split(str, separatorChars);
        ArrayUtils.reverse(strs);
        if(separatorChars == null)
            return join(strs, ' ');
        else
            return join(strs, separatorChars);
    }

    public static String abbreviate(String str, int maxWidth)
    {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth)
    {
        if(str == null)
            return null;
        if(maxWidth < 4)
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        if(str.length() <= maxWidth)
            return str;
        if(offset > str.length())
            offset = str.length();
        if(str.length() - offset < maxWidth - 3)
            offset = str.length() - (maxWidth - 3);
        if(offset <= 4)
            return str.substring(0, maxWidth - 3) + "...";
        if(maxWidth < 7)
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        if(offset + (maxWidth - 3) < str.length())
            return "..." + abbreviate(str.substring(offset), maxWidth - 3);
        else
            return "..." + str.substring(str.length() - (maxWidth - 3));
    }

    public static String difference(String str1, String str2)
    {
        if(str1 == null)
            return str2;
        if(str2 == null)
            return str1;
        int at = indexOfDifference(str1, str2);
        if(at == -1)
            return "";
        else
            return str2.substring(at);
    }

    public static int indexOfDifference(String str1, String str2)
    {
        if(str1 == str2)
            return -1;
        if(str1 == null || str2 == null)
            return 0;
        int i;
        for(i = 0; i < str1.length() && i < str2.length() && str1.charAt(i) == str2.charAt(i); i++);
        if(i < str2.length() || i < str1.length())
            return i;
        else
            return -1;
    }

    public static int getLevenshteinDistance(String s, String t)
    {
        if(s == null || t == null)
            throw new IllegalArgumentException("Strings must not be null");
        int n = s.length();
        int m = t.length();
        if(n == 0)
            return m;
        if(m == 0)
            return n;
        int d[][] = new int[n + 1][m + 1];
        for(int i = 0; i <= n; i++)
            d[i][0] = i;

        for(int j = 0; j <= m; j++)
            d[0][j] = j;

        for(int i = 1; i <= n; i++)
        {
            char s_i = s.charAt(i - 1);
            for(int j = 1; j <= m; j++)
            {
                char t_j = t.charAt(j - 1);
                int cost;
                if(s_i == t_j)
                    cost = 0;
                else
                    cost = 1;
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
            }

        }

        return d[n][m];
    }

    private static int min(int a, int b, int c)
    {
        if(b < a)
            a = b;
        if(c < a)
            a = c;
        return a;
    }
    
	/**
	 * 토큰으로 분리한 값 들을 배열로 리턴하는 메소드
	 * 
	 * @param str
	 * @param token
	 * @return String[]
	 */
	public static String[] getTokens(String str, String token) {
		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, token);
		String[] tokens = new String[st.countTokens()];
		for (int i = 0 ; st.hasMoreTokens() ; i++) {
			tokens[i] = st.nextToken();
		}
		return tokens;
	}	

    public static final String EMPTY = "";
    private static final int PAD_LIMIT = 8192;
    private static final String PADDING[];

    static 
    {
        PADDING = new String[65535];
        PADDING[32] = "                                                                ";
    }
    
    public static int getBytesLength(String str) {
    	if(str == null) return 0;
    	else {
    		return str.getBytes().length;
    	}
    		
    }
    
    public static String noDataConvert(String imgUrl, String noImgUrl){
    	String reUrl = "";
    	
    	if(imgUrl == null || isBlank(imgUrl)){
    		reUrl = noImgUrl;
    	}else{
    		reUrl = imgUrl;
    	}
    	
    	return reUrl;
    }
    
    // 날짜가 있는 스트링에서 년월 가져오기
    // 뉴스레터
    public static String getDayOfString(String str, String dis){
    	String reStr = "";
    	String year = "";
    	String month = "";
    	
    	if(str == null || isBlank(str)){
    		return reStr;
    	}
    	
    	String temp = getOnlyNum(str);
    	
    	if(temp == null || temp.length() < 1){
    		return reStr;
    	}
    	
    	if(temp.length() == 6){
    		year = temp.substring(0,4);
    		month = temp.substring(4,6);
    	}else if(temp.length() == 5){
    		year = temp.substring(0,4);
    		month = "0"+temp.substring(4,5);
    	}else if(temp.length() > 6){
    		year = temp.substring(0,4);
    		month = temp.substring(4,6);
    		
    	}else if(temp.length() > 2){
    		String first = temp.substring(0,1);
    		if(first.equals("0") || first.equals("1")){
    			year = "20" + temp.substring(0,2);
    			month = temp.substring(2,4);
    		}
    	}
    	
    	if(month.compareTo("12") > 0){
			month = "0" + month.substring(0,1);
		}
    	
    	reStr = year+dis+month;
    	
    	return reStr;
    }
    
    public static String setTableImgWidth(String content){
    	StringBuffer buffer = new StringBuffer();
    	try{
	    	if(content == null){
	    		return "";
	    	}
	    	
	    	if(StringUtils.contains(content, "<IMG") || StringUtils.contains(content, "<TABLE")){
				
				String[] t = getTokens(content, "<");
				
				for(int i=0; i< t.length; i++){
					String temp = "<" + t[i];
					int num = 0;
					
					if(temp.indexOf("<TABLE") > -1 || temp.indexOf("<IMG") > -1){
						int st = temp.indexOf("width=");
						if(st > -1){
							int end = 0;
							String entStr = temp.substring(st);
							
							for(int k=(temp.indexOf("width=\"") > -1)?7:6; k<entStr.length();k++){
								if(!isNumeric(entStr.substring(k,k+1))){
									end = (temp.indexOf("width=\"") > -1)?k+1:k;
									break;
								}
							}
							
							if(st > -1 && end > 0){
								String tempStr = temp.substring(st,st+end);
								num = Integer.parseInt(getOnlyNum(tempStr));
								if(temp.indexOf("<TABLE") > -1 || num > 205)
									temp = temp.replace(tempStr, "width=\"100%\" ");
							}
						}
						
						if(temp.indexOf("<TABLE") > -1 || num > 205){
							st = temp.indexOf("height=");
							if(st > -1){
								int end = 0;
								String entStr = temp.substring(st);
								
								for(int k=(temp.indexOf("height=\"") > -1)?8:7; k<entStr.length();k++){
									if(!isNumeric(entStr.substring(k,k+1))){
										end = (temp.indexOf("height=\"") > -1)?k+1:k;
										break;
									}
								}
								if(st > -1 && end > 0){
									String tempStr = temp.substring(st,st+end);
									temp = temp.replace(tempStr, "height=\"100%\" ");
									temp = temp.replace("height=\"100%\" ", " ");
									
								}
							}
						}
					}
				buffer.append(temp);
				}
			}
	    	
	    	if(buffer == null || buffer.length() < 1)
	    		return content;
	    	
	    	System.out.println("적용 내용 S ----------------" );
	    	System.out.println(buffer.toString());
	    	System.out.println("적용 내용 E ----------------" );
	    	
    	}catch(Exception e){
    		System.out.println("변환 에러 : "+ e.toString());
    	}
    	return buffer.toString();
    }
    
    public static String getOnlyNum(String str){
    	if(str == null){
    		return "";
    	}
    	
    	for(int i = 0; i < str.length(); i++){
    		String temp = str.substring(i,i+1);
    		if(!isNumeric(temp)){
    			str = str.replace(temp, "");
    			i--;
    		}
    	}
    	return str;
    }
    
    //테스트
    static public void main(String[] args){
    	try{
    		/*
    		String temp = "";
	    	File file = new File("C:\\t.txt");
	    	FileReader rf = new FileReader(file);
	    
	    	while(true){
	    		int data = rf.read();
	    		
	    		if(data == -1) break;
	    		
	    		char ch = (char)data;
	    		temp += ch;
	    	}
	    	
	    	String tem = "<HTML><HEAD>";
	    		tem+="<META name=GENERATOR content=\"MSHTML 8.00.7600.16490\"></HEAD>";
	    		tem+="<BODY style=\"FONT-FAMILY: gulim; FONT-SIZE: 9pt; \">권무석 - 서울시 무형문화재 제23호 숙종이래 11대에 이르기까지 궁장명인의 가계를 잇고 있는 권무석 장인은 활을 보면 그것이 얼마나 명품인지, 심지어 국내 어느 장인이 제작했는지도 알 수 있을 만큼 '활'의 세계에서 권무석 장인을 빼놓고는 이야기를 할 수 없습니다. <BR><BR><A href=\"http://www.visitseoul.net/visit2006/article/article_view.jsp?seq=868&page=1&strCH=theme|rcity|bukchonc\" target=_blank target_blank><FONT color=brown><B>[북촌한옥마을 사이트 바로가기]</B></FONT></A></BODY></HTML>";
	    	//System.out.println(setTableImgWidth(temp));
	    	*/
    		
    		System.out.println(getDayOfString("1","-"));
    	}catch(Exception e){
    		System.out.println(e.toString());
    	}
    }
    
    public static String getRandomData(int max) {
		String rndValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();

		StringBuffer sTemp = new StringBuffer();

		for (int i = 1; i <= max; i++) {
			int nRnd = random.nextInt(36);
			if(nRnd % 2 == 0){
				sTemp.append(rndValue.substring(nRnd, nRnd + 1));
			}else{
				sTemp.append(rndValue.substring(nRnd, nRnd + 1).toLowerCase());
			}
		}

		return sTemp.toString();
	}
    
    // 수정
    public static String getRndData(int max) {
		String rndValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();

		StringBuffer sTemp = new StringBuffer();

		for (int i = 1; i <= max; i++) {
			int nRnd = random.nextInt(36);
			int nR = random.nextInt(9);
			if(nR == 0)nR = 10;
			if(nRnd % nR == 0){
				sTemp.append(rndValue.substring(nRnd, nRnd + 1));
			}else{
				sTemp.append(rndValue.substring(nRnd, nRnd + 1).toLowerCase());
			}
		}

		return sTemp.toString();
	}
    
    //SPLIT 함수 추가
    public static String getSplitFirst(String str){
    	
    	String[] result = split(str,"|");
    	String res ="";
    	if(result.length>1){
    		res = result[0];
    	}else{
    		res = str;
    	}
    	return res;
    	
    }
    
 /*   //이미지 사이즈 비율 계산 추가
    public static int getImageSize(String imgFilePath,int setWidth,int setHeight,String mode)throws Exception{
    	String IMG_FILEPATH_400_300 = imgFilePath;
    	String storePathRoot = DefaultConfig.getString("fileupload.storePathRoot");
		File file = new File(storePathRoot+IMG_FILEPATH_400_300);
		
		int result = 0;
		
		// 이미지 비율별 처리  
		int origin_width = setWidth; //145
		int origin_height = setHeight; //96
		
		float float_width = (float)setWidth;
		float float_height = (float)setHeight;
		
		if (file.isFile()) {
			ImageTool image = ImageTool.getImageTool(file);
			origin_width = image.getWidth();
			origin_height = image.getHeight();
		}

		int width = 0;
		int height = 0;
		float ratio = 0;
		
		int set_width = 0;
		int set_height = 0;					

		if (origin_width > origin_height) {
			ratio = (float)(float_width / origin_width);
			height = Math.round(ratio*origin_height);

			set_width = setWidth;
			set_height = height;
			
			if(height > setHeight){
				set_height = setHeight;
				ratio = (float)(float_height/ height);
				width = Math.round(ratio * setWidth);
				set_width = width;
			}

		} else if (origin_width < origin_height) {
			ratio = (float)(float_height / origin_height);
			width = Math.round(ratio * origin_width);

			set_width = width;
			set_height = setHeight; 
			
			if(width > setWidth){
				set_width = setWidth;
				ratio = (float)(float_width / width);
				height = Math.round(ratio * setHeight);
				set_height = height;
			}
		} else {
			set_width = setHeight;
			set_height = setHeight;
		}
		
		int paddingTop = 0; 
		int heightPx = setHeight;
		
		paddingTop = (int)(Math.round(float_height /2)-Math.round(set_height*1.0/2));
		heightPx = setHeight-(int)(Math.round(float_height /2)-Math.round(set_height*1.0/2));
		
		if(mode.equalsIgnoreCase("width")){
			result = set_width;
		}else if(mode.equalsIgnoreCase("height")){
			result = set_height;
		}else if(mode.equalsIgnoreCase("paddingTop")){
			result = paddingTop;
		}else if(mode.equalsIgnoreCase("heightPx")){
			result = heightPx;
		}
		
		return result;
		
    }*/
    
    //SPLIT 함수 추가
    public static String getSplit(String str){
    	
    	String[] result = split(str,"|");
    	String res ="";
    	if(result.length>1){
    		for(int i = 0; i < result.length; i++){
    		res = result[i];
    		}
    	}else{
    		res = str;
    	}
    	return res;
    	
    }

}
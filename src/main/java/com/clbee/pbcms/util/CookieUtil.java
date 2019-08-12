package com.clbee.pbcms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 쿠키 처리 유틸
 *
 * @author 명준민
 * @date   2008. 06. 17
 * @desc   쿠키 처리 유틸
 */
public class CookieUtil {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    private static final String DEFAULT_ENCODING = "UTF-8";

    public CookieUtil(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 모든 쿠키 획득
     * @author Administrator
     * @return
     * @date   2007. 06. 05
     */
    public Cookie[] getAll() {
        return request.getCookies();
    }

    /**
     * key값으로 해당 쿠키를 얻는다.
     */
    public Cookie getCookie(String name) {
        Cookie all[] = getAll();
        if(all == null)
            return null;
        for(int i = 0; i < all.length; i++)
        {
            Cookie cookie = all[i];
            if(cookie.getName().equals(name))
                return cookie;
        }

        return null;
    }

    /**
     * Key를 이용하여 값을 얻는다.
     * @author Administrator
     * @return
     * @throws UnsupportedEncodingException
     * @date   2007. 06. 05
     */
    public String get(String key) throws UnsupportedEncodingException {

        String value = "";

        Cookie c = getCookie(key);

        if(c!=null) value = URLDecoder.decode(c.getValue(), DEFAULT_ENCODING);

        return value;
    }

    /**
     * 쿠키 생성
     * @throws UnsupportedEncodingException
     */
    public void add(String name, String value) throws UnsupportedEncodingException {
        add(name, value, 0);
    }

    /**
     * 쿠키 생성
     * @throws UnsupportedEncodingException
     */
    public void add(String name, String value, Object maxAge) throws UnsupportedEncodingException {
        Cookie c = create(name, value, maxAge);

        if(c == null) {
            return;
        } else {
            response.addCookie(c);
            return;
        }
    }


    //실질적인 쿠키생성
    private Cookie create(String name, String value, Object maxAge) throws UnsupportedEncodingException {

        int expiry = 0;

        if(maxAge instanceof Number) {
            expiry = ((Number)maxAge).intValue();
        } else {
            try {
                expiry = Integer.parseInt(String.valueOf(maxAge));
            } catch(NumberFormatException nfe) {

            }
        }

        Cookie c = new Cookie(name, URLEncoder.encode(value, DEFAULT_ENCODING));
        c.setMaxAge(expiry);
        c.setDomain(request.getContextPath());
        c.setPath("/");

        return c;
    }

    public static String getEncodedString(String value) throws UnsupportedEncodingException {
    	return getEncodedString(value, DEFAULT_ENCODING);
    }

    public static String getEncodedString(String value, String enc) throws UnsupportedEncodingException {
    	return URLEncoder.encode(value, enc);
    }

}

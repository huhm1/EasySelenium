package utils;

import java.util.Set;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.Cookie;

public class Cookies {
	public static CookieStore copyCookies(Set<Cookie> cookies) {
		CookieStore cookieStore = new BasicCookieStore();
		for (Cookie c : cookies) {

			cookieStore.addCookie(convertSeleniumCookie(c));
		}

		return cookieStore;

	}

	private static BasicClientCookie convertSeleniumCookie(Cookie cookie) {
		if (cookie == null) {
			return null;
		}

		BasicClientCookie basicCookie = null;

		// Copy all Selenium Cookie parameters to a new Basic Client Cookie
		basicCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
		basicCookie.setDomain(cookie.getDomain());
		basicCookie.setPath(cookie.getPath());
		basicCookie.setSecure(cookie.isSecure());
		basicCookie.setExpiryDate(cookie.getExpiry());
		basicCookie.setAttribute("httponly", Boolean.toString(cookie.isHttpOnly()));

		// return the Basic Client Cookie
		return basicCookie;
	}

}

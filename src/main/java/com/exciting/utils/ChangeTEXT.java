package com.exciting.utils;


public class ChangeTEXT {
	public static String ToHTML(String str) {

		String returnStr = str;

		returnStr = returnStr.replaceAll(">", "&gt;");

		returnStr = returnStr.replaceAll("<", "&lt;");

		returnStr = returnStr.replaceAll("\r\n", "<br>");

		returnStr = returnStr.replaceAll("\n", "<br>");

		// returnStr = returnStr.replaceAll("", "&quot;");

		returnStr = returnStr.replaceAll(" ", "&nbsp;");

		returnStr = returnStr.replaceAll("&", "&amp;");

		return returnStr;

	}

	public static String ToJAVA(String str) {

		String returnStr = str;

		returnStr = returnStr.replaceAll("<br>", "\r\n");

		returnStr = returnStr.replaceAll("&gt;", ">");

		returnStr = returnStr.replaceAll("&lt;", "<");

//	    returnStr = returnStr.replaceAll("&quot;", "");

		returnStr = returnStr.replaceAll("&nbsp;", " ");

		returnStr = returnStr.replaceAll("&amp;", "&");

		return returnStr;

	}

	public static String ToTextarea(String str) {

		String returnStr = str;

		returnStr = returnStr.replaceAll("&gt;", ">");

		returnStr = returnStr.replaceAll("&lt;", "<");

//	    returnStr = returnStr.replaceAll("&quot;", "");

		returnStr = returnStr.replaceAll("&nbsp;", " ");

		returnStr = returnStr.replaceAll("&amp;", "&");

		return returnStr;

	}
}

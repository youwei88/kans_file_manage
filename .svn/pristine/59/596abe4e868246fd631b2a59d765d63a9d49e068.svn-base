/*
 */
package common.util;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StringUtils.class);

	/**
	 * 
	 * @param firstStr
	 * @param secondStr
	 * @return
	 */
	public static boolean compareStrSplitByComma(String firstStr,
			String secondStr) {
		if (firstStr == null || secondStr == null) {
			return false;
		}
		String[] firstArray = firstStr.split(",");
		String[] secondArray = secondStr.split(",");
		Map secondStrMap = new HashMap();
		if (firstArray.length != secondArray.length) {
			return false;
		}
		for (int i = 0; i < secondArray.length; i++) {
			secondStrMap.put(secondArray[i], secondArray[i]);
		}
		for (int i = 0; i < firstArray.length; i++) {
			if (!secondStrMap.containsKey(firstArray[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param doubleValue
	 * @return String
	 */
	public static String doubleToCurrency(double doubleValue) {
		Object[] args = { new Double(doubleValue) };
		return MessageFormat.format(
				"{0,number,��,#,###,###,###,###,###,##0.00}", args);
	}

	/**
	 * 
	 * @param stringValue
	 * @param srcEncoding
	 * @param destEncoding
	 * @return String
	 */
	public static String encodeString(String stringValue, String srcEncoding,
			String destEncoding) {
		
		if (stringValue == null || srcEncoding == null || destEncoding == null) {
			return null;
		}
		String value = null;
		try {
			value = new String(stringValue.getBytes(srcEncoding), destEncoding);
		} catch (UnsupportedEncodingException ex) {
			value = stringValue;
		}
		return value;
	}

	/**
	 * �ж��Ƿ�ָ���ַ�Ϊ���ַ�(null���߳���Ϊ0
	 * 
	 * @param stringValue
	 * @return boolean
	 */
	public static boolean isEmptyString(String stringValue) {
		if (stringValue == null || stringValue.trim().length() < 1
				|| stringValue.trim().equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param stringValue
	 * @return boolean
	 */
	public static boolean isEmpty(String stringValue) {
		if (stringValue == null || stringValue.trim().length() < 1
				|| stringValue.trim().equalsIgnoreCase("null")) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param stringValue
	 * @return boolean
	 */
	public static boolean isNotEmpty(String stringValue) {
		if (stringValue == null || stringValue.trim().length() < 1
				|| stringValue.trim().equalsIgnoreCase("null")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNumber(String str) {
		if (StringUtils.isEmptyString(str)) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			if (ch < '0' || ch > '9') {
				return false;

			}
		}
		return true;
	}
	
	public static boolean isLong(String str){
		try{
			new Long(str);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean isDouble(String str) {
	    try {
	        Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	  }

	/**
	 * 
	 * @param stringValue
	 * @return String[]
	 */
	public static String[] getNumStringArray(String stringValue) {
		if (stringValue == null) {
			return null;
		}
		ArrayList list = new ArrayList();
		Pattern p = Pattern.compile("([0-9])+");
		Matcher m = p.matcher(stringValue);
		while (m.find()) {
			list.add(m.group());
		}
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 
	 * @param stringValue
	 * @return int
	 */
	public static int stringToInt(String stringValue) {
		return stringToInt(stringValue, -1);
	}

	/**
	 * 
	 * @param stringValue
	 * @param defaultValue
	 * @return int
	 */
	public static int stringToInt(String stringValue, int defaultValue) {
		int intValue = defaultValue;
		if (stringValue != null) {
			try {
				intValue = Integer.parseInt(stringValue);
			} catch (NumberFormatException ex) {
				intValue = defaultValue;
			}
		}
		return intValue;

	}

	/**
	 * 
	 * @param stringValue
	 * @return String
	 */
	public static String toGBKString(String stringValue) {
		return encodeString(stringValue, "ISO8859_1", "GBK");

	}
	
	public static String getMenuId(String menuId,int status){
		String id="";
		boolean f=true;
		for (int i = 0; i < menuId.length(); i=i+2) {
			String mid=menuId.substring(i, i+2);
			if(status==3){
				if(mid.equals("00")){
					id+=mid;
				}
			}else if(status==4){
				if(!mid.equals("00")){
					id+=mid;
				}
			}else if(mid.equals("00")&& f){
				if(status==1){
					id+="01";
				}else if(status==2){
					id+="99";
				}
				f=false;
			}else{
				id+=mid;
			}
		}
		return id;
	}

	/**
	 * 
	 */
	public StringUtils() {
	}


}
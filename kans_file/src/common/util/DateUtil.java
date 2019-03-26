package common.util;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DateUtil.class);

	public DateUtil() {
		
	}
	
	/**
	 * @param time
	 * @param format
	 * @return
	 */
	public static String parseFormatLongToStr(long time,String format) {
		SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(format);
		return long2DateStr(time, FORMAT_DATE_TIME);
	}
	
	/**
	 * @param time
	 * @param
	 * @return
	 */
	public static String long2DateStr(long time, SimpleDateFormat t) {
		java.sql.Date date = new java.sql.Date(time);
		if (date != null) {
			return t.format(date);
		} else {
			return "";
		}
	}
	
	/**
	 * @param time
	 * @param format
	 * @return
	 */
	public static Date parseFormatLongToDate(long time,String format){
		if(StringUtils.isEmpty(format)){
			format="yyyy-MM-dd HH:dd:ss";
		}
		SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(format);
		return parseDateString(long2DateStr(time, FORMAT_DATE_TIME),format);
	}

	/**
	 * @param str
	 *            锟斤拷锟斤拷锟街凤拷
	 * @param format
	 *            锟斤拷式
	 * @return String
	 */
	public static Timestamp parseFormatString(String str, String format) {
		if (str == null || str.equals(""))
			return null;
		try {
			DateFormat df = new SimpleDateFormat(format);
			Date dt = df.parse(str);
			Timestamp timestamp = new Timestamp(dt.getTime());
			return timestamp;
		} catch (Exception pe) {
		}
		Timestamp timestamp3;
		try {
			Timestamp timestamp1 = Timestamp.valueOf(str);
			return timestamp1;
		} catch (Exception e) {
			timestamp3 = null;
		}
		return timestamp3;
	}

	/**
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date parseDateString(String str, String format) {
		if (str == null || str.equals(""))
			return null;
		Date dt = null;
		try {
			DateFormat df = new SimpleDateFormat(format);
			dt = df.parse(str);

		} catch (Exception pe) {
		}

		return dt;
	}

	public static boolean isBeforeOrEqual(Date date1, Date date2) {
		if (date1.compareTo(date2) > 0) {
			return false;
		} else {
			return true;
		}
	}


	/**
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp parseLongString(String str) {
		if (str == null || str.equals(""))
			return null;
		try {
			long time = Long.parseLong(str);
			Timestamp timestamp = new Timestamp(time);
			return timestamp;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * 
	 * @param dt
	 * @param format
	 * @return
	 */
	public static String formatDate(Date dt, String format) {
		if ((dt == null) || format == null) {
			return "";
		}
		String strDate = "";
		String s1;
		try {
			SimpleDateFormat DATA_FORMAT = new SimpleDateFormat(format);
			strDate = DATA_FORMAT.format(dt);
			String s = strDate;
			return s;
		} catch (Exception e) {
			s1 = null;
		}
		return s1;
	}

	/**
	 * 锟斤拷锟斤拷时锟斤拷 锟斤拷指锟斤拷锟斤拷锟节碉拷锟斤拷 时锟斤拷
	 * 
	 * @param date
	 *            锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param field
	 *            锟皆讹拷锟街讹拷锟斤拷
	 * @param amount
	 *            锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param format
	 *            锟街凤拷锟绞�
	 * @return String
	 */
	public static String getAdjustDate(Date date, int field, int amount, String format) {
		if ((date == null) || format == null) {
			return "";
		}
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		can.add(field, amount);
		Date newDate = can.getTime();
		String dateStr = formatDate(newDate, format);
		return dateStr;
	}

	/**
	 * 锟斤拷锟斤拷时锟斤拷 锟斤拷指锟斤拷锟斤拷锟节碉拷锟斤拷 时锟斤拷
	 * 
	 * @param date
	 *            锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param field
	 *            锟皆讹拷锟街讹拷锟斤拷
	 * @param amount
	 *            锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param
	 *            锟街凤拷锟绞�
	 * @return String
	 */
	public static Date getAdjustDate(Date date, int field, int amount) {
		if ((date == null)) {
			return null;
		}
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		can.add(field, amount);
		Date newDate = can.getTime();
		return newDate;
	}

	/**
	 * 取2锟斤拷时锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param date1
	 * @param date2
	 * @param isAbs
	 *            true:取锟斤拷锟街� false:锟斤拷值锟斤拷锟斤拷锟角革拷锟斤拷
	 * @return
	 * @throws Exception
	 */
	public static long getBetweenDiffDay(Date date1, Date date2, boolean isAbs) throws Exception {
		long date1Value = date1.getTime();
		long date2Value = date2.getTime();
		long diff = (date1Value - date2Value) / (24 * 3600 * 1000);
		if (isAbs) {
			return Math.abs(diff);
		} else {
			return diff;
		}

	}

    //鏃ユ湡杞湀浠�
    public static long getBetweenDiffMonth(Date date1, Date date2, boolean isAbs) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String str1 = sdf.format(date1);
        String str2 = sdf.format(date2);
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(str1));
        aft.setTime(sdf.parse(str2));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        long diff=Math.abs(month + result);

        if (isAbs) {
            return diff;
        } else {
            return diff;
        }

    }

    /*public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String str1 = "2012-02";
        String str2 = "2010-01";
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(str1));
        aft.setTime(sdf.parse(str2));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        System.out.println(Math.abs(month + result));
    }*/

	public static long getBetweenDiffHour(Date date1, Date date2, boolean isAbs) throws Exception {
		long date1Value = date1.getTime();
		long date2Value = date2.getTime();
		long diff = (date1Value - date2Value) / (3600 * 1000);
		if (isAbs) {
			return Math.abs(diff);
		} else {
			return diff;
		}
	}

	/**
	 * 锟矫碉拷指锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟铰碉拷锟斤拷锟揭伙拷锟�,锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷末,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getMonthEnd(Date date) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		// 说锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷末,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末
		if (can.get(Calendar.DAY_OF_MONTH) == can.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			can.add(Calendar.MONTH, 1);
		}
		can.set(Calendar.DAY_OF_MONTH, can.getActualMaximum(Calendar.DAY_OF_MONTH));
		can.set(Calendar.HOUR_OF_DAY, 23);
		can.set(Calendar.MINUTE, 59);
		can.set(Calendar.SECOND, 59);

		return can.getTime();
	}

	/**
	 * 锟矫碉拷指锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟铰的碉拷一锟斤拷锟�0锟斤拷0锟斤拷0锟斤拷
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getCurrentMonthBegin(Date date) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		// 说锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷末,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末

		can.set(Calendar.DAY_OF_MONTH, can.getActualMinimum(Calendar.DAY_OF_MONTH));
		can.set(Calendar.HOUR_OF_DAY, 00);
		can.set(Calendar.MINUTE, 00);
		can.set(Calendar.SECOND, 00);

		return can.getTime();
	}

	/**
	 * 锟矫碉拷指锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟铰的碉拷锟斤拷锟斤拷锟斤拷23锟斤拷59锟斤拷59锟斤拷
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getCurrentMonthEnd(Date date) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		// 说锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷末,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末

		can.set(Calendar.DAY_OF_MONTH, can.getActualMaximum(Calendar.DAY_OF_MONTH));
		can.set(Calendar.HOUR_OF_DAY, 23);
		can.set(Calendar.MINUTE, 59);
		can.set(Calendar.SECOND, 59);

		return can.getTime();
	}

	/**
	 * 锟矫碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟铰碉拷锟斤拷锟揭伙拷锟�, 锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷末,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末
	 * 
	 * @return Date
	 */
	public static Date getMonthEnd() {
		Date date = new Date();
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		// 说锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷末,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末
		if (can.get(Calendar.DAY_OF_MONTH) == can.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			can.add(Calendar.MONTH, 1);
		}
		can.set(Calendar.DAY_OF_MONTH, can.getActualMaximum(Calendar.DAY_OF_MONTH));
		can.set(Calendar.HOUR_OF_DAY, 23);
		can.set(Calendar.MINUTE, 59);
		can.set(Calendar.SECOND, 59);

		return can.getTime();
	}

	/**
	 * 锟矫碉拷指锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟杰的碉拷锟斤拷锟揭伙拷锟�,锟斤拷锟斤拷锟斤拷时锟斤拷锟窖撅拷锟斤拷锟斤拷末,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getWeekEnd(Date date) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		// 说锟斤拷锟斤拷锟斤拷锟绞憋拷锟斤拷丫锟斤拷锟斤拷锟侥�,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末
		if (1 == can.get(Calendar.DAY_OF_WEEK)) {
			can.add(Calendar.WEEK_OF_MONTH, 1);
		}
		can.set(Calendar.DAY_OF_WEEK, can.getActualMaximum(Calendar.DAY_OF_WEEK));
		can.set(Calendar.HOUR_OF_DAY, 23);
		can.set(Calendar.MINUTE, 59);
		can.set(Calendar.SECOND, 59);
		can.add(Calendar.DAY_OF_MONTH, 1);
		return can.getTime();
	}

	/**
	 * 锟矫碉拷指锟斤拷锟斤拷锟节第讹拷锟斤拷慕锟斤拷锟绞憋拷锟�
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getTomorrowEnd(Date date) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		can.set(Calendar.HOUR_OF_DAY, 23);
		can.set(Calendar.MINUTE, 59);
		can.set(Calendar.SECOND, 59);
		can.add(Calendar.DAY_OF_MONTH, 1);
		return can.getTime();
	}

	/**
	 * 锟斤拷锟街革拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷时锟斤拷
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYesterdayEnd(Date date) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		can.set(Calendar.HOUR_OF_DAY, 23);
		can.set(Calendar.MINUTE, 59);
		can.set(Calendar.SECOND, 59);
		can.add(Calendar.DAY_OF_MONTH, -1);
		return can.getTime();
	}

	/**
	 * 锟斤拷锟斤拷锟截讹拷时锟斤拷锟叫∈逼拷锟斤拷锟斤拷锟斤拷锟斤拷锟狡拷锟斤拷锟斤拷锟�
	 * 
	 * @param date
	 *            指锟斤拷锟斤拷锟斤拷
	 * @param hourOffset
	 *            小时偏锟斤拷锟斤拷 锟斤拷锟斤拷为锟斤拷锟斤拷小时锟斤拷锟斤拷锟斤拷为锟斤拷去小时
	 * @param dateOffset
	 *            锟斤拷锟斤拷偏锟斤拷锟斤拷 锟斤拷锟斤拷为锟斤拷锟斤拷锟斤拷 锟斤拷锟斤拷锟斤拷为锟斤拷去锟斤拷
	 * @param startOrEnd
	 *            0为锟斤拷始时锟斤拷锟斤拷锟叫∈逼拷锟斤拷锟斤拷锟斤拷锟�2008-01-10 00:00:00
	 * 			  1为锟斤拷锟斤拷时锟斤拷锟斤拷锟叫∈逼拷锟斤拷锟斤拷锟斤拷锟�2008-01-10 23:59:59
	 * 			  2为指锟斤拷时锟斤拷锟狡拷锟斤拷锟绞憋拷锟�   锟斤拷偏锟斤拷锟斤拷为-1 指锟斤拷时锟斤拷为2008-01-10 00:00:00 锟斤拷锟斤拷时锟斤拷为锟斤拷2008-01-09 23:00:00
	 * @return
	 */
	public static Date getAppointDateByOffset(Date date, int hourOffset, int dateOffset, int startOrEnd) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		int hourOfDay = hourOffset;
		int minute = 0;
		int second = 0;
		if (startOrEnd == 1) {
			hourOfDay = 23;
			minute = 59;
			second = 59;
		} else if (startOrEnd == 2) {
			hourOfDay = hourOffset;
			minute = 0;
			second = 0;
		}

		can.set(Calendar.HOUR_OF_DAY, hourOfDay);
		can.set(Calendar.MINUTE, minute);
		can.set(Calendar.SECOND, second);
		can.add(Calendar.DAY_OF_MONTH, dateOffset);
		return can.getTime();
	}
	
	public static Date getAppointMinByOffset(Date date, int minOffset) {
		Calendar can = Calendar.getInstance();
		can.setTime(date);
//		int hourOfDay = hourOffset;
		int minute = minOffset;
//		int second = 0;
		

//		can.set(Calendar.HOUR_OF_DAY, hourOfDay);
		can.set(Calendar.MINUTE, minute);
//		can.set(Calendar.SECOND, second);
//		can.add(Calendar.DAY_OF_MONTH, dateOffset);
		return can.getTime();
	}
	
	/**
	 * 杩斿洖褰撴棩鐨勫紑濮嬫椂闂存垨缁撴潫鏃堕棿
	 * @param type 1涓哄紑濮嬫椂闂� 2涓虹粨鏉熸椂闂�
	 * @return
	 */
	public static Date getToday(Date date,int type){
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		int hourOfDay = 0;
		int minute = 0;
		int second = 0;
		if (type == 2) {
			hourOfDay = 23;
			minute = 59;
			second = 59;
		} else if (type == 1) {
			hourOfDay = 0;
			minute = 0;
			second = 0;
		}
		can.set(Calendar.HOUR_OF_DAY, hourOfDay);
		can.set(Calendar.MINUTE, minute);
		can.set(Calendar.SECOND, second);
		return can.getTime();
	}

	/**
	 * 锟矫碉拷锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟杰的碉拷锟斤拷锟揭伙拷锟�,锟斤拷锟斤拷锟斤拷时锟斤拷锟窖撅拷锟斤拷锟斤拷末,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末
	 * 
	 * @return Date
	 */
	public static Date getWeekEnd() {
		Date date = new Date();
		Calendar can = Calendar.getInstance();
		can.setTime(date);
		// 说锟斤拷锟斤拷锟斤拷锟绞憋拷锟斤拷丫锟斤拷锟斤拷锟侥�,锟斤拷锟斤拷值应锟斤拷锟斤拷锟铰革拷锟斤拷末
		if (1 == can.get(Calendar.DAY_OF_WEEK)) {
			can.add(Calendar.WEEK_OF_MONTH, 1);
		}
		can.set(Calendar.DAY_OF_WEEK, can.getActualMaximum(Calendar.DAY_OF_WEEK));
		can.set(Calendar.HOUR_OF_DAY, 23);
		can.set(Calendar.MINUTE, 59);
		can.set(Calendar.SECOND, 59);
		can.add(Calendar.DAY_OF_MONTH, 1);
		return can.getTime();
	}

	/**
	 * 锟斤拷dt锟斤拷式锟斤拷锟斤拷指锟斤拷锟斤拷式
	 * 
	 * @param dt
	 *            时锟斤拷
	 * @param format
	 *            锟斤拷式
	 * @return String
	 */
	public static String formatTime(Timestamp dt, String format) {
		if ((dt == null) || format == null) {
			return "";
		}
		String strDate = "";
		String s1;
		try {
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(format);
			strDate = DATE_FORMAT.format(dt);
			String s = strDate;
			return s;
		} catch (Exception e) {
			s1 = null;
		}
		return s1;
	}

	public static Date getDay(Date d, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int td = c.get(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, td + n);
		Date towDay = c.getTime();
		return towDay;
	}
	
	public static void main(String[] args) {
		List<String> l= DateUtil.getPreMonth(new Date(), 7);
		for (String string : l) {
			System.out.println(string);
		}
	}
	
	public static List<String> getPreMonth(Date d,int count){
		List<String> list=new ArrayList<String>();
		for(int i=1;i<count;i++){
			Date dd=getAppointDateByOffset(getCurrentMonthBegin(d), 0, -1, 2);
			d=dd;
			list.add(formatDate(dd, "yyyy-MM"));
		}
		return list;
	}
	
	public static List<String> getPreMonth(Date d,int count,String format){
		List<String> list=new ArrayList<String>();
		for(int i=1;i<count;i++){
			Date dd=getAppointDateByOffset(getCurrentMonthBegin(d), 0, -1, 2);
			d=dd;
			list.add(formatDate(dd, format));
		}
		return list;
	}
	
	public static String StringDate(String strDate){
		String dateTime= DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		String strDt=null;
		String str=dateTime.split("-")[2];
		Integer time=(Integer.parseInt(str))-Integer.parseInt(strDate);
		strDt=dateTime.split("-")[0]+"-"+dateTime.split("-")[1]+"-"+time;
		return strDt;
	}

    //浠ュ綋鏃ユ椂闂翠负涓嬫爣锛岃绠椾箣鍓嶆垨鑰呬箣鍚庣殑鏃ユ湡
    public static Integer calcDate(Integer number){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, number);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(mFormat.format(calendar.getTime()));
    }

    //杩斿洖褰撳墠鏃ユ湡鐨刬nteger
    public static Integer formatDate(String fmt){
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        Date date = new Date();
        return Integer.parseInt(sdf.format(date));
    }
}

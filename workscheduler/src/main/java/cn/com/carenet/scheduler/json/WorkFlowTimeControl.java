package cn.com.carenet.scheduler.json;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * use this object to transfer JSON to cron expression and time stamp.
 * @author Sherard Lee
 * @since 12/May/2017
 */
public class WorkFlowTimeControl {
	final static Logger LOG = LoggerFactory.getLogger(WorkFlowTimeControl.class);
	final protected static String TYPE_CONTROL_MARK = "type";
	final protected static String TYPE_HANDLE = "0";
	final protected static String TYPE_SEC = "1";
	final protected static String TYPE_DAY = "2";
	final protected static String TYPE_WEEK = "3";
	final protected static String TYPE_MONTH = "4";

	final protected static String REPEAT_MARK = "repeat";
	final protected static String ISREPEAT_MARK = "1";
	final protected static String MINUTE_MARK = "minute";
	final protected static String SECOND_MARK = "second";
	final protected static String HOUROFDAY_MARK = "day_hour";
	final protected static String MINUTEOFHOUR_MARK = "day_minute";
	final protected static String DAYOFWEEK_MARK = "week";
	final protected static String DAYOFMONTH_MARK = "day";

	private long timestampExpression;
	private String cronExpression;
	private boolean repeat = false;

	private WorkFlowTimeControl() {

	}
	
	/**
	 * give me JSONObject to generate the cron expression and time stamp.
	 * @param subJSONObject
	 * @return
	 */
	public static WorkFlowTimeControl newInstance(JSONObject subJSONObject) {

		return new WorkFlowTimeControl().generateTime(subJSONObject);

	}

	/**
	 * generate time stamp and cron expression by using the json object like:
	 * {"repeat": "1","type": "3","minute": "123","second": "123","day": "30","week": "2","day_hour": "23","day_minute": "59"}
	 * @param subJSONObject
	 * @return
	 */
	private WorkFlowTimeControl generateTime(JSONObject subJSONObject) {

		if (subJSONObject.containsKey(REPEAT_MARK))
			if (subJSONObject.getString(REPEAT_MARK).equals(ISREPEAT_MARK))
				this.repeat = true;
		try {
			String typeControl = subJSONObject.getString(TYPE_CONTROL_MARK);
			int minute = 0, second = 0, hourOfDay = 0, minuteOfHour = 0, dayOfWeek = 0, dayOfMonth = 0;

			if (subJSONObject.containsKey(MINUTE_MARK) && (subJSONObject.getString(MINUTE_MARK) != null
					&& !subJSONObject.getString(MINUTE_MARK).trim().equals("")))
				minute = Math.abs(Integer.parseInt(subJSONObject.getString(MINUTE_MARK)));

			if (subJSONObject.containsKey(SECOND_MARK) && (subJSONObject.getString(SECOND_MARK) != null
					&& !subJSONObject.getString(SECOND_MARK).trim().equals("")))
				second = Math.abs(Integer.parseInt(subJSONObject.getString(SECOND_MARK)));

			if (subJSONObject.containsKey(HOUROFDAY_MARK) && (subJSONObject.getString(HOUROFDAY_MARK) != null
					&& !subJSONObject.getString(HOUROFDAY_MARK).trim().equals("")))
				hourOfDay = Math.abs(Integer.parseInt(subJSONObject.getString(HOUROFDAY_MARK)));

			if (subJSONObject.containsKey(MINUTEOFHOUR_MARK) && (subJSONObject.getString(MINUTEOFHOUR_MARK) != null
					&& !subJSONObject.getString(MINUTEOFHOUR_MARK).trim().equals("")))
				minuteOfHour = Math.abs(Integer.parseInt(subJSONObject.getString(MINUTEOFHOUR_MARK)));

			if (subJSONObject.containsKey(DAYOFWEEK_MARK) && (subJSONObject.getString(DAYOFWEEK_MARK) != null
					&& !subJSONObject.getString(DAYOFWEEK_MARK).trim().equals("")))
				dayOfWeek = Math.abs(Integer.parseInt(subJSONObject.getString(DAYOFWEEK_MARK)));

			if (subJSONObject.containsKey(DAYOFMONTH_MARK) && (subJSONObject.getString(DAYOFMONTH_MARK) != null
					&& !subJSONObject.getString(DAYOFMONTH_MARK).trim().equals("")))
				dayOfMonth = Math.abs(Integer.parseInt(subJSONObject.getString(DAYOFMONTH_MARK)));

			// transfer some wrong express of time
			minute = (minute + second / 60) % 60;
			second = second % 60;
			if (dayOfWeek != 0)
				dayOfWeek = (dayOfWeek + (hourOfDay + minuteOfHour / 60) / 24) % 7 + 1;
			if (dayOfMonth != 0) {
				dayOfMonth = (dayOfMonth + (hourOfDay + minuteOfHour / 60) / 24) % 31;
				if (dayOfMonth == 0)
					dayOfMonth = 31;
			}
			hourOfDay = (hourOfDay + minuteOfHour / 60) % 24;
			minuteOfHour = minuteOfHour % 60;

			Date date = new Date();
			Calendar now = Calendar.getInstance();
			Calendar calendar = Calendar.getInstance();
			if(typeControl!=null)
				if(!typeControl.trim().equals(""))
			switch (typeControl) {
			case TYPE_HANDLE:
				timestampExpression = 0;
				cronExpression = "* * * * * ?";
				break;

			case TYPE_SEC:
				calendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE),
						now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE) + minute,
						now.get(Calendar.SECOND) + second);
				date = calendar.getTime();
				timestampExpression = date.getTime();
				if(minute!=0)
				cronExpression = String.format("%d 0/%d * * * ?", second, minute);
				else
					cronExpression = String.format("0/%d * * * * ?", second);
				break;

			case TYPE_DAY:
				calendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), hourOfDay,
						minuteOfHour, 0);
				date = calendar.getTime();

				if (date.getTime() < System.currentTimeMillis())
					timestampExpression = date.getTime() + 24 * 60 * 60 * 1000;
				else
					timestampExpression = date.getTime();

				cronExpression = String.format("0 %d %d * * ?", minuteOfHour, hourOfDay);

				break;

			case TYPE_WEEK:
				calendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), hourOfDay,
						minuteOfHour, 0);
				date = calendar.getTime();

				int dayOfWeekNow = now.get(Calendar.DAY_OF_WEEK) - 1;
				long daytime;
				if (dayOfWeek < dayOfWeekNow) {
					daytime = dayOfWeek - dayOfWeekNow + 7;
					timestampExpression = date.getTime() + daytime * 24 * 60 * 60 * 1000;
				} else {
					daytime = dayOfWeek - dayOfWeekNow;
					timestampExpression = date.getTime() + daytime * 24 * 60 * 60 * 1000;
					if (timestampExpression < System.currentTimeMillis())
						timestampExpression = timestampExpression + 7 * 24 * 60 * 60 * 1000;
				}

				if (dayOfWeek != 0)
					cronExpression = String.format("0 %d %d ? * %d", minuteOfHour, hourOfDay, dayOfWeek);
				else
					cronExpression = "* * * * * ?";
				break;

			case TYPE_MONTH:
				calendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), hourOfDay,
						minuteOfHour, 0);
				date = calendar.getTime();
				int dayOfMonthNow = now.get(Calendar.DAY_OF_MONTH);
				long maxDateMonth = now.getActualMaximum(Calendar.DATE);
				if (dayOfMonth < dayOfMonthNow) {
					daytime = dayOfMonth - dayOfMonthNow + maxDateMonth;
					timestampExpression = date.getTime() + daytime * 24 * 60 * 60 * 1000;
				} else {
					daytime = dayOfMonth - dayOfMonthNow;
					timestampExpression = date.getTime() + daytime * 24 * 60 * 60 * 1000;
					if (timestampExpression < System.currentTimeMillis())
						timestampExpression = timestampExpression + maxDateMonth * 24 * 60 * 60 * 1000;
				}

				if (dayOfMonth != 0)
					cronExpression = String.format("0 %d %d %d * ?", minuteOfHour, hourOfDay, dayOfMonth);
				else
					cronExpression = "* * * * * ?";

				break;

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return this;
	}

	public long getTimestampExpression() {
		return timestampExpression;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public boolean isRepeat() {
		return repeat;
	}
}

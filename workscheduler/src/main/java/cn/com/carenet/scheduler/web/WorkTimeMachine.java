package cn.com.carenet.scheduler.web;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.carenet.scheduler.web.entity.DataRepository;

public class WorkTimeMachine {

	final protected static String TYPE_HANDLE = "0";
	final protected static String TYPE_SEC = "1";
	final protected static String TYPE_DAY = "2";
	final protected static String TYPE_WEEK = "3";
	final protected static String TYPE_MONTH = "4";
	final protected static String ISREPEAT_MARK = "1";

	private long timestampExpression;
	private String cronExpression;
	private boolean repeat = false;

	public WorkTimeMachine(Map<String, DataRepository> dataRepositories) {
		generateTime(dataRepositories);
	}

	/**
	 * generate time stamp and cron expression by using the json object like:
	 * {"repeat": "1","type": "3","minute": "123","second": "123","day":
	 * "30","week": "2","day_hour": "23","day_minute": "59"}
	 * 
	 * @param subJSONObject
	 * @return
	 */
	private void generateTime(Map<String, DataRepository> dataRepositories) {
		for (Entry<String, DataRepository> dataRepositoryEntry : dataRepositories.entrySet()) {
			DataRepository dataRepository = dataRepositoryEntry.getValue();
			String moudleName = dataRepository.getWf_name();
			if (DataRepository.WF_NAME_START.equals(moudleName)) {
				String type = dataRepository.getType();
				if (type != null) {
					if (ISREPEAT_MARK.equals(dataRepository.getRepeat())) {
						this.repeat = true;
					}
					int minute = 0, second = 0, hourOfDay = 0, minuteOfHour = 0, dayOfWeek = 0, dayOfMonth = 0;
					Integer minuteStr = dataRepository.getMinute();
					Integer secondStr = dataRepository.getSecond();
					Integer hourOfDayStr =dataRepository.getDay_hour();
					Integer minuteOfHourStr =dataRepository.getDay_minute();
					// dayOfWeek
					Integer dayOfWeekStr =dataRepository.getWeek();
					// dayOfMonth
					Integer dayOfMonthStr =dataRepository.getDay();
					if(minuteStr!=null){
						minute = minuteStr;
					}
					if(secondStr!=null){
						second = secondStr;
					}
					if(hourOfDayStr!=null){
						hourOfDay = hourOfDayStr;
					}
					if(minuteOfHourStr!=null){
						minuteOfHour = minuteOfHourStr;
					}
					if(dayOfWeekStr!=null){
						dayOfWeek = dayOfWeekStr;
					}
					if(dayOfMonthStr!=null){
						dayOfMonth = dayOfMonthStr;
					}
					
					Date date = new Date();
					Calendar now = Calendar.getInstance();
					Calendar calendar = Calendar.getInstance();
					switch (type) {
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
						if (minute != 0)
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
				}
				
			} else {
				continue;
			}
		}
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

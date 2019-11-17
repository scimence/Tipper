package com.sc.tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTool
{
	
	/** 判断当前时间是否在时间区间内 */
	public static boolean nowInTimeRegion(String startTime, String endTime)
	{
		long nowMill = System.currentTimeMillis();
		
		long startMill = ToMillion(getNowDate() + " " + startTime);
		if(nowMill < startMill) return false;
		
		long endMill = ToMillion(getNowDate() + " " + endTime);
		if(nowMill > endMill) return false;
		
		return true;
	}

	/** 获取当前日期时间 */
	public static String getNowDateTime()
	{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = formatter.format(new Date());
		return str;
	}
	
	/** 判断今天是否为周末 */
	public static boolean isWeekend()
	{
		@SuppressWarnings("deprecation")
		int day = new Date().getDay();
		return (day==6 || day==0);
	}
	
	/** 获取当前日期 */
	public static String getNowDate()
	{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String str = formatter.format(new Date());
		return str;
	}
	
	/** 获取当前时间 */
	public static String getNowTime()
	{
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String str = formatter.format(new Date());
		return str;
	}
	
	
	/** 将日期时间字符串转化为毫秒值 */
	@SuppressWarnings("deprecation")
	public static Long ToMillion(String time)
	{
		try
		{
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
			Date date = formatter.parse(time);
			
			long million = date.getTime();
			return million;
		}
		catch(Exception ex)
		{
				return 0L;
		}
	}

	/** 判断是否为交易时间 */
	public static boolean IsTradeTime()
	{
		return (!isWeekend() && (nowInTimeRegion("09:30:00", "11:30:00") || nowInTimeRegion("13:00:00", "15:00:00")));
	}
}

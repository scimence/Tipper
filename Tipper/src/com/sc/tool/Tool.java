package com.sc.tool;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sc.update.WebTool;

/** 通用工具函数类 */
public class Tool
{
	/** 字符串转float */
	public static float ToFloat(String num)
	{
		float f = 0;
		
		try
		{
			f = Float.parseFloat(num);
		}catch(Exception ex)
		{}
		
		return f;
	}
	
	/** 获取变动比例 */
	public static float GetRateF(float num, float base)
	{
		float rate = (num-base) / base * 100;
		return rate;
	}
	
	/** 获取变动比例 */
	public static String GetRate(float num, float base)
	{
		float rate = (num-base) / base * 100;
		DecimalFormat decimalFormat=new DecimalFormat("0.00");
	    String Str=decimalFormat.format(rate);
	    
	    return Str + "%";
	}
	
	/** 获取变动比例 */
	public static String Rate(float num)
	{
		float rate = num;
		DecimalFormat decimalFormat=new DecimalFormat("0.00");
	    String Str=decimalFormat.format(rate);
	    
	    return Str + "%";
	}

	/** 将数值格式化 */
	public static String F2(float num)
	{
		DecimalFormat decimalFormat= new DecimalFormat("0.00");	
		if(num < 1) decimalFormat= new DecimalFormat("0.000");
		
	    String Str=decimalFormat.format(num);
	    
	    return Str;
	}
	
	/** 将数值格式化 */
	public static String F2(String num)
	{
		float value = Float.parseFloat(num);
		return F2(value);
	}

	/** 获取实时数据信息 */
	public static String getRealData(String Id)
	{
		// http://hq.sinajs.cn/list=sz000980
		// http://hq.sinajs.cn/list=sh600079,sh601988,sh601939
		// ,3.090,3.110,3.080,3.090,3.070,3.070,3.080,2404400,7402749.000,355800,3.070,221500,3.060,158400,3.050,101600,3.040,172400,3.030,59100,3.080,136000,3.090,155900,3.100,152400,3.110,139900,3.120,2019-10-25,09:52:39,00";
		
		if (Id.startsWith("0") || Id.startsWith("3"))
		{
			Id = "sz" + Id;
		}
		else if (Id.startsWith("6")) 
		{
			Id = "sh" + Id;
		}
		
		String url = "http://hq.sinajs.cn/list=" + Id;
		// String data = Http.Get(url);
		String data = WebTool.GetString(url);
		
		return data;
	}
	
	private static boolean isClearProcess = false;
	/** 清除缓存数据 */
	public static void ClearTmpData()
	{
		isClearProcess = true;
		
		realList.clear();
		avgList.clear();
		
		isClearProcess = false;
	}
	
	private static HashMap<String, String> realList = new HashMap<String, String>();
	
	/** 获取实时市价 */
	public static String getRealValue(String Id)
	{
		if(isClearProcess) return "";
		
		if (TimeTool.IsTradeTime() || !realList.containsKey(Id))
		{
			String nowValue = "";
			
			String data = Tool.getRealData(Id);
			if (data.equals("")) nowValue = "0";
			
			String[] A = data.split(",");
			
			if (A.length > 3) nowValue = A[3];
			if (nowValue.equals("")) nowValue = "0";
			
			if(!realList.containsKey(Id) && !nowValue.equals("0")) realList.put(Id, nowValue);
			return nowValue;
		}
		else 
		{
			return realList.get(Id);
		}
	}
	

	private static HashMap<String, String> avgList = new HashMap<String, String>();
	
	/** 获取均价数据信息 */
	public static String[] getAvgData(String Id)
	{
		if(isClearProcess) return new String[0];
		
		// http://hq.sinajs.cn/list=sz000980
		// http://hq.sinajs.cn/list=sh600079,sh601988,sh601939
		// ,3.090,3.110,3.080,3.090,3.070,3.070,3.080,2404400,7402749.000,355800,3.070,221500,3.060,158400,3.050,101600,3.040,172400,3.030,59100,3.080,136000,3.090,155900,3.100,152400,3.110,139900,3.120,2019-10-25,09:52:39,00";
		
//		if (Id.startsWith("0"))
//			Id = "sz" + Id;
//		else if (Id.startsWith("6")) Id = "sh" + Id;
//		
//		String url = "http://hq.sinajs.cn/list=" + Id;
//		// String data = Http.Get(url);
//		String data = WebTool.GetString(url);
//		
//		return data;
		
		
		String data = "";
		if(avgList.containsKey(Id))
		{
			data = avgList.get(Id);
		}
		else
		{
//			if(Id.equals("000980")) data = "众泰汽车;3.36";
//			else if(Id.equals("002068")) data = "黑猫股份;4.76";
//			else if(Id.equals("300323")) data = "华灿光电;5.31";
//			else if(Id.equals("300605")) data = "恒锋信息;13.27";
//			else if(Id.equals("600605")) data = "汇通能源;11.10";
//			else if(Id.equals("002591")) data = "恒大高新;8.44";
//			else if(Id.equals("300371")) data = "汇中股份;12.43";
//			else if(Id.equals("603920")) data = "世运电路;16.02";
//			else if(Id.equals("002298")) data = "中电兴发;7.51";
//			else if(Id.equals("002724")) data = "海洋王;6.45";
//			else if(Id.equals("002530")) data = "金财互联;9.90";
//			else if(Id.equals("002799")) data = "环球印物;14.89";
//			else if(Id.equals("603819")) data = "神力股份;14.43";
//			else if(Id.equals("300275")) data = "梅安森;10.20";
//			else if(Id.equals("300592")) data = "华凯创意;11.21";
//			else if(Id.equals("300360")) data = "炬华科技;9.63";
			

//			查询：
//			http://132.232.124.176:8002/pages/webInfo.aspx?TAB=TipperData&KEY=000980
//			{"日期":"2019-11-17_17:23:14_909913","标签":"000980","信息":"众泰汽车;3.36;2.58"}
			
			String url = "http://132.232.124.176:8002/pages/webInfo.aspx?TAB=TipperData&KEY=" + Id;
			data = WebTool.GetString(url);
			
			String start = "\"信息\":\"";
			String end = "\"}";
			if(data.contains(start) && data.contains(end))
			{
				int startI = data.indexOf(start) + start.length();
				int endI = data.indexOf(end);
				data = data.substring(startI, endI);
				
				avgList.put(Id, data);	// 记录数据信息至缓存
			}
		}
		
		
		String[] A = data.split(";");
		
		return A;
	}	
	
	/** 将datas中的数据转化为当个字符串 */
	public static String ToSingleString(List<String> datas)
	{
		StringBuilder buf = new StringBuilder();
		for(String data : datas)
		{
			buf.append(data+";");
		}
		String tmp = buf.toString();
		if(tmp.length() > 0)tmp = tmp.substring(0, tmp.length()-1);
		return tmp;
	}
	
	/** 将单个字符串转化为list */
	public static ArrayList<String> ToStringArrayList(String singleStr)
	{
		String[] A = singleStr.split(";");
		ArrayList<String> list = new ArrayList<String>();
		for(String value : A)
		{
			list.add(value);
		}
		return list;
	}
}

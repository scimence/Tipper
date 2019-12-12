package com.sc.valueTool;

import java.util.ArrayList;
import java.util.List;

/** 数值变动趋势 */
public class Trend
{	
	boolean isUp = true;	// 是否为变大趋势
	List<Float> values = new ArrayList<Float>();
	public int startIndex = 0;
	
	private Trend(List<Float> values, boolean isUp, int startIndex)
	{
		this.values = values;
		this.isUp = isUp;
		this.startIndex = startIndex;
	}
	
	/** 获取趋势最小值 */
	public float Min()
	{
		if(isUp) return values.get(0);
		else return values.get(values.size()-1);
	}
	
	/** 获取趋势最大值 */
	public float Max()
	{
		if(!isUp) return values.get(0);
		else return values.get(values.size()-1);
	}
	
	/** 将数据解析为Trend数据 */
	public static List<Trend> ToTrend(List<Float> values)
	{
		List<Trend> trends = new ArrayList<Trend>();
		
		List<Float> valueData = null;	// 单个趋势，所有数据
		float pre = 0;					// 前一个数值
		boolean confimTrend = false;	// 是否已确定趋势
		Trend trend = null;
		boolean isUp = true;	
		
		for(int i=0; i<values.size(); i++)
		{
			float value = values.get(i);
			
			if(valueData == null)
			{
				valueData = new ArrayList<Float>();
				valueData.add(value);
				pre = value;
				
				confimTrend = false;
				continue;
			}
			
			if(!confimTrend)
			{
				if(value > pre)
				{
					isUp = true;
					confimTrend = true;
				}
				else if(value < pre)
				{
					isUp = false;
					confimTrend = true;
				}
				
				valueData.add(value);
				pre = value;
			}
			else
			{
				if(value == pre || (isUp && value > pre) || (!isUp && value < pre))
				{
					valueData.add(value);
					pre = value;
				}
				else
				{
					// 将之前确定趋势的数据生成为一个Trend
					trend = new Trend(valueData, isUp, i-valueData.size());
					trends.add(trend);
					
					// 开始新的数据趋势解析
					valueData = new ArrayList<Float>();
					valueData.add(value);
					pre = value;
					
					confimTrend = false;
				}
			}
			
			// 执行至最后一项时，将之前的数据存储为一个趋势
			if(i==values.size()-1 && trends.size() > 0)
			{
				if(!confimTrend) isUp = !trends.get(trends.size() -1).isUp;
				if(valueData.size() > 0)
				{
					trend = new Trend(valueData, isUp, i-valueData.size());
					trends.add(trend);
				}
			}
		}
		
		return trends;
	}
	
	/** 判断当前是否出现新的最大值提示点 */
	public static boolean attachNewMax(List<Trend> trends)
	{
		if(trends.size() > 1)
		{
			Trend lastTrend = trends.get(trends.size()-1);
			Trend preLast = trends.get(trends.size()-2);
			
			if(preLast.isUp && !lastTrend.isUp && lastTrend.values.size() >= 3)
			{
				float trendsMax = getMax(trends);	// 获取最大值
				if(preLast.Max() >= trendsMax) return true;
			}
		}
		
		return false;
	}
	
	/** 判断当前是否出现新的最小值提示点 */
	public static boolean attachNewMin(List<Trend> trends)
	{
		if(trends.size() > 1)
		{
			Trend lastTrend = trends.get(trends.size()-1);
			Trend preLast = trends.get(trends.size()-2);
			
			if(!preLast.isUp && lastTrend.isUp && lastTrend.values.size() >= 3)
			{
				float trendsMin = getMin(trends);	// 获取最小值
				if(preLast.Min() <= trendsMin) return true;
			}
		}
		
		return false;
	}
	
	
	/** 获取所有趋势区间的最大值 */
	public static float getMax(List<Trend> trends)
	{
		float max = 0;
		
		for(int i = 0; i<trends.size(); i++)
		{
			Trend trend = trends.get(i);
			if(i==0) max = trend.Max();
			else if(trend.Max() > max) max = trend.Max();
		}
		
		return max;
	}
	
	/** 获取所有趋势区间的最小值 */
	public static float getMin(List<Trend> trends)
	{
		float min = 0;
		
		for(int i = 0; i<trends.size(); i++)
		{
			Trend trend = trends.get(i);
			if(i==0) min = trend.Min();
			else if(trend.Min() < min) min = trend.Min();
		}
		
		return min;
	}
}

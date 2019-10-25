package com.sc.valueTool;

import java.util.ArrayList;
import java.util.List;

/** 对给定的数据信息，进行数据处理 */
public class ValueAnalyse
{	
	public List<Float> values = new ArrayList<Float>();	
	public List<Float> Avg = new ArrayList<Float>();
	
	public TrendAnalyse trendAnalyse = null;
	
	/** 对数据进行临近均值处理，avgTimes为取邻近均值运算次数 */
	public ValueAnalyse(List<Float> values, int avgTimes)
	{
		this.values = values;
		
		// 计算邻近均值
		for(int i= 0; i<avgTimes; i++)
		{
			if(i==0) Avg = getAvg(values);
			else Avg = getAvg(Avg);
		}
		
		// 数据趋势分组
		trendAnalyse = new TrendAnalyse(Avg);
	}
	
	/** 取临近数据的均值作为当前项的值 */
	private List<Float> getAvg(List<Float> values)
	{
		List<Float> Avg = new ArrayList<Float>();
		
		for(int i = 0; i<values.size(); i++)
		{
			if(i==0 || i==values.size()-1) 
			{
				Avg.add(values.get(i));
			}
			else
			{
				Float avg = (values.get(i-1) + values.get(i) * 2 + values.get(i+1)) / 4;
				Avg.add(avg);
			}
		}
		
		return Avg;
	}
	
	//------------------------
	
	/** 出现最大值点 */
	public boolean attchMax()
	{
		return trendAnalyse.isAttachNewMax;
	}
	
	/** 出现最小值点 */
	public boolean attchMin()
	{
		return trendAnalyse.isAttachNewMin;
	}
	
	/** 最大值、最小值对应的数据索引 */
	public int Index()
	{
		return trendAnalyse.Index;
	}
}

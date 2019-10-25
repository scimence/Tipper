package com.sc.valueTool;

import java.util.List;

public class TrendAnalyse
{
	public List<Trend> trendList = null;		// 趋势分组信息
	
	public boolean isAttachNewMax = false;		// 出现新的最大值
	public boolean isAttachNewMin = false;		// 出现新的最小值
	
	public int Index = 0;						// 记录最大值或最小值对应的索引位置
	
	public float MaxValue = 0;					// 所有趋势的最大值
	public float MinValue = 0;					// 所有趋势的最小值
	
	/** 对均值化处理后的数据，进行趋势分组 */
	public TrendAnalyse(List<Float> Avg)
	{
		trendList = Trend.ToTrend(Avg);
		
		isAttachNewMax = Trend.attachNewMax(trendList);
		isAttachNewMin = Trend.attachNewMin(trendList);
		if(isAttachNewMax || isAttachNewMin) 
		{
			Index = Avg.size() - trendList.get(trendList.size()-1).values.size() - 1;
		}
		
		MaxValue = Trend.getMax(trendList);
		MinValue = Trend.getMin(trendList);
	}
}

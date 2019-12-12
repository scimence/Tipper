
package com.sc.serviceDemo;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.sc.Notification.NotificationTool;
import com.sc.service.BaseService;
import com.sc.tool.ActivityComponent;
import com.sc.tool.Http;
import com.sc.tool.Preference;
import com.sc.tool.ThreadTool;
import com.sc.tool.ThreadTool.ThreadPram;
import com.sc.tool.TimeTool;
import com.sc.tool.Tool;
import com.sc.valueTool.ValueAnalyse;


/** 信息提示处理逻辑 */
public class TipLogic
{
	private static String SetName = "tipper_data";
	
	/** 获取配置信息 */
	public static Preference getSet(Context context)
	{
		Preference Set = new Preference(context, SetName);
		return Set;
	}
	
	/** 信息提示处理逻辑 */
	public static void Doing(Context context)
	{
		if (TimeTool.IsTradeTime())
		{
			List<String> IdList = getSet(context).Values();
			
			for (String Id : IdList)
			{
				try
				{
					IdDataSave(Id);
					IdCheckTip(context, Id);
				}
				catch(Exception ex){}
			}
		}
//		else BaseService.IntervalMillis = 60000 * 3;
//		if(TimeTool.nowInTimeRegion("09:27:00", "09:30:00")) BaseService.IntervalMillis = 15000;
	}
	
	static int pre = 10000;
	static Map<String, Float> endDic = new HashMap<String, Float>();
	
	/** Id对应的数据获取与保存逻辑 */
	private static void IdDataSave(String Id)
	{
		// String Url = "" + Id;
		// String value = Http.Get(Url);
		String data = Tool.getRealData(Id);
		if (data.equals("")) return;
		
		String[] A = data.split(",");
		String nowValue = "";
		if (A.length > 3) nowValue = A[3];
		if (nowValue.equals("")) return;
		
		float endValue = Float.parseFloat(A[2]);
		if(!endDic.containsKey(Id)) endDic.put(Id, endValue);
		
		// Random random=new Random(System.currentTimeMillis());
		// pre = random.nextInt(10000);
		// String value = pre + "";
		
		TipData tipData = new TipData(Id);
		tipData.SaveValue(nowValue);
	}
	
	/** 从Id对应的数据进行检测与提示 */
	private static void IdCheckTip(Context context, String Id)
	{
		TipLog tipLog = new TipLog(Id);
		TipData tipData = new TipData(Id);
		
		List<Float> values = tipData.Values(true);	// 数据
//		List<String> names = tipData.Names(false);	// 数据项名称
		
		// 对数据趋势进行解析，提示
		ValueAnalyse analyse = new ValueAnalyse(values, 3);
		if (analyse.attchMin() || analyse.attchMax())
		{
			boolean isMin = analyse.attchMin();
			
			float vlaueNum = values.get(analyse.Index());
//			String name = names.get(analyse.Index());
			
//			float avg = analyse.Avg.get(analyse.Index());
			
			float endValue = 0;
			if(endDic.containsKey(Id)) endValue = endDic.get(Id);
			String Append = (endValue==0) ? "" : ("(" + Tool.GetRate(vlaueNum, endValue) + ")");
			
			String msg = "出现最" + (isMin ? "小" : "大") + "值:" + vlaueNum + Append /*+ ",  均值 " + Tool.F2(avg)*/;
			if (!tipLog.fileData.contains(msg))
			{
				ShowNotification(Id, context, Id + Append, msg, tipLog.filePath);
				tipLog.SaveValue(msg);
			}
		}
		
		// ShowText(context, values.toString());
	}
	
	/** 显示通知栏消息 */
	private static void ShowNotification(String Id, Context context, String tittle, String content, String FilePath)
	{
		String drawableName = "ic_launcher";
		int icon = ActivityComponent.getId(context, drawableName, "drawable");
		
		Intent intent = new Intent();
		intent.setAction("intent.action.ShowFile");
		intent.putExtra("FilePath", FilePath);
		intent.setPackage(context.getPackageName());	// 用当前应用中的ShowFile打开
		
		int notification_id = -1;
		try
		{
			notification_id = Integer.parseInt(Id);
		}catch(Exception ex){}
		
		NotificationTool.ShowNotification(context, notification_id, icon, "图标边的文字", tittle, content, intent);
	}
	
	/** 显示提示信息 */
	private static void ShowText(final Context context, final String msg)
	{
		ThreadTool.RunInMainThread(new ThreadPram()
		{
			@Override
			public void Function()
			{
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
}

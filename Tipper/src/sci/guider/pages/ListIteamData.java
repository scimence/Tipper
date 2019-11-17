package sci.guider.pages;

import java.util.ArrayList;
import java.util.List;

import com.sc.tool.Tool;

 
/** ListIteamData.java: 定义该结构，用于表示列表项的数据结构 ----- 2018-10-25 下午8:20:46 scimence */
public class ListIteamData
{
	public String code="", name="";				// 代码、名称
	public float avg=0, real=0, rate=0;			// 均值、市价、涨跌幅
	
//	Drawable icon, btn1, btn2;							// 图标图像、按钮图像、灰色按钮图像
//	public boolean isClicked = false;					// 其他信息，记录列表项是否已点击
	
	ListIteamData()
	{}
	
	/** 创建列表数据项 */
	public ListIteamData(String code, String name, String avg, String real)
	{
		this.code = code;
		this.name = name;
		this.avg = Tool.ToFloat(avg);
		this.real = Tool.ToFloat(real);
		
		if(this.real == 0) this.rate = 0;
		else this.rate = (this.real - this.avg) / this.avg * 100;
	}
	
	/** 从字符串，创建列表数据项 */
	public ListIteamData(String iteamStr)
	{
		String[]  A = iteamStr.split(";");
		if(A.length >= 1) code = A[0];
		if(A.length >= 2) name = A[1];
		if(A.length >= 3) avg = Tool.ToFloat(A[2]);
		if(A.length >= 4) real = Tool.ToFloat(A[3]);
		
		if(this.real == 0) this.rate = 0;
		else this.rate = (this.real - this.avg) / this.avg * 100;
	}
	
	/** 数据转化为字符串形式 */
	public String ToString()
	{
		return code + ";" + name + ";" + Tool.F2(avg) + ";" + Tool.F2(real);
	}
	
	/** 判断当前数据项，是否大于iteam，按第index列排序 */
	public boolean BiggerThan(ListIteamData iteam, int index)
	{
		if(index == 1) return this.code.compareTo(iteam.code) > 0;
		else if(index == 2) return avg > iteam.avg;
		else if(index == 3) return real > iteam.real;
		else if(index == 4) return rate > iteam.rate;
		else return false;
	}
	

	//---------------------------------------------
	
	/** 将所有数据项字符串，转化为数据项类型 */
	public static ListIteamData[] ToArray(List<String> iteamDatas)
	{
		ListIteamData[] Array = new ListIteamData[iteamDatas.size()];
		for(int i= 0; i < iteamDatas.size(); i++)
		{
			ListIteamData iteam = new ListIteamData(iteamDatas.get(i));
			Array[i] = iteam;
		}
		return Array;
	}
	
	/** 对data中的数据， 按第index列进行排序 */
	public static ListIteamData[] Sort(ListIteamData[] data0, int index)
	{
		ListIteamData[] data = new ListIteamData[data0.length];
		for(int i=0; i<data0.length; i++)
		{
			data[i] = data0[i];
		}
		
		// 数据项排序
		ListIteamData tmp = data[0];
		for(int i=0; i<data.length-1; i++)
		{
			for(int j=i+1; j<data.length; j++)
			{
				if(data[i].BiggerThan(data[j], index))
				{
					tmp = data[i];
					data[i] = data[j];
					data[j] = tmp;
				}
			}
		}
		
		return data;
	}
	
	/** 对data中的数据， 按第index列进行逆向排序 */
	public static ListIteamData[] ReSort(ListIteamData[] data, int index)
	{
		ListIteamData[] A = Sort(data, index);
		ListIteamData[] B = new ListIteamData[A.length];
		
		// 逆序存储
		for(int i=0; i<A.length; i++)
		{
			B[A.length - 1 - i] = A[i];
		}
		return B;
	}
	

	/** 对列表项数据进行排序 */
	public static ListIteamData[] Sort(ListIteamData[] IteamDatas, int index, boolean resort)
	{
		if(resort) return ListIteamData.ReSort(IteamDatas, index);
		else return ListIteamData.Sort(IteamDatas, index);
	}
	
	/** 获取所有代码值 */
	public static ArrayList<String> getCodeList(ListIteamData[] IteamDatas)
	{
		ArrayList<String> list = new ArrayList<String>();
		for(ListIteamData iteam : IteamDatas)
		{
			if(iteam != null) list.add(iteam.code);
		}
		return list;
	}
	
	/** 判断data1和data2是否相同 */
	public static boolean IsEqual(ListIteamData[] data1, ListIteamData[] data2)
	{
		if(data1.length != data2.length) return false;
		for(int i=0; i<data1.length; i++)
		{
			String Str1 = data1[i].ToString();
			String Str2 = data2[i].ToString();
			if(!Str1.equals(Str2)) return false;
		}
		return true;
	}
	
	// public ListIteamData(String... data)
	// {
	// this.iconUrl = data[0];
	// this.btnUrl1 = data[1];
	// this.note1 = data[2];
	// this.note2 = data[3];
	// }
	
//	// 从Json对象创建
//	public ListIteamData(JSONObject obj)
//	{
//		try
//		{
//			this.name = obj.optString("iconUrl", "");
//			this.code = obj.optString("btnUrl1", "");
//			this.avg = obj.optString("btnUrl2", "");
//			this.note = obj.optString("note", "");
//			
//			// 下载图像资源
//			icon = WebTool.GetDrawable(name);
//			btn1 = WebTool.GetDrawable(code);
//			btn2 = WebTool.GetDrawable(avg);
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
//	}
//	
//	// ---------------------------------
//	
//	// 从Json数组解析数据
//	public static ListIteamData[] ToArray(JSONArray data)
//	{
//		ListIteamData[] Array = new ListIteamData[data.length()];
//		
//		for (int i = 0; i < data.length(); i++)
//		{
//			try
//			{
//				JSONObject obj = data.getJSONObject(i);
//				Array[i] = new ListIteamData(obj);
//			}
//			catch (Exception ex)
//			{
//				ex.printStackTrace();
//				Log.e("thumbsupPage.java", "数据ListIteamData解析异常!");
//			}
//		}
//		return Array;
//	}
}


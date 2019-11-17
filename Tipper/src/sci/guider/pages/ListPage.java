package sci.guider.pages;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sc.serviceDemo.MainActivity;
import com.sc.tool.ActivityComponent;
import com.sc.tool.CallBackF;
import com.sc.tool.LocalDB;
import com.sc.tool.Preference;
import com.sc.tool.ThreadTool;
import com.sc.tool.ThreadTool.ThreadPram;
import com.sc.tool.TimeTool;
import com.sc.tool.Tool;
 
 
/** list列表页主界面  ---- 2018-10-25 下午8:32:21 scimence*/
public class ListPage extends ActivityComponent
{
	Preference Set;
	String SetName = "guider_data";
	
	String pageIndex = "1";
	
	@Override
	public void Init(Bundle savedInstanceState)
	{
		// 设置Activity页面布局
		setContentView("ltpay_layout_guider");
		
		// 载入配置页面信息
		String value = LocalDB.readData("pageIndex");
		if(!value.equals("")) pageIndex = value;
		
		NewDayClear();
		
		// 载入配置信息集
		LoadSetPage(pageIndex);
	}
	
	private void NewDayClear()
	{
		String nowDate = TimeTool.getNowDate();
		String preDate = LocalDB.readData("nowDate");
		if(!nowDate.equals(preDate))
		{
			LocalDB.saveData("nowDate", nowDate);
			Tool.ClearTmpData(); //清除缓存数据信息
		}
	}
	
	/** 载入对应页面的信息配置集合 */
	private void LoadSetPage(String pageIndex)
	{
		SetName = "guider_data_page" + pageIndex;
		Set = new Preference(this, SetName);
		SetPageButtonColor(pageIndex);
		
		codeList.clear();	// 清空代码集合
		LinearLayout("ltpay_content").removeAllViews();	// 移除所有
		LoadCodeList();
		
		RefreshLogic();
		Refresh();
	}
	
	/** 设置页面索引按钮对应的颜色 */
	private void SetPageButtonColor(String pageIndex)
	{
		int colorGray = Color.parseColor("#969696");
		int colorBlack = Color.parseColor("#000000");
		String nameId = "ltpay_page_index" + pageIndex;
		for(int i = 1; i < 3; i++)
		{
			String NAME = "ltpay_page_index" + i;
			TextView(NAME).setTextColor(NAME.equals(nameId) ? colorGray : colorBlack);
		}
	}
	
	/* 设置View点击响应事件 */
	@Override
	public void Click(String viewId)
	{
//		String text = "点击了View -> " + viewId;
//		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		
		// TODO View点击响应逻辑
		if(viewId.equals("ltpay_text_tipper"))
		{
			Show(this, MainActivity.class);
		}
		else if(viewId.equals("ltpay_text_watting") || viewId.equals("ltpay_text_holding"))
		{
			
		}
		else if(viewId.equals("ltpay_text_add_new"))
		{
			InputBox.Show(this, "请输入股票代码");
		}
		else if(viewId.startsWith("ltpay_text_t"))		// 列表排序
		{
			String indexStr = viewId.substring("ltpay_text_t".length());
			int index = Integer.parseInt(indexStr);
			
			SortListView(index);
		}
		else if(viewId.startsWith("ltpay_page_index"))	// 信息集合切换
		{
			pageIndex = viewId.substring("ltpay_page_index".length());
			LocalDB.saveData("pageIndex", pageIndex);
			
			// 载入配置信息集
			LoadSetPage(pageIndex);
		}
		else if(viewId.equals("ltpay_dialog_header_back"))
		{
			this.finish();
		}
		else if(viewId.equals("ltpay_dialog_header_refresh"))
		{
			Tool.ClearTmpData(); //清除缓存数据信息
		}
		
//		else if(viewId.equals("删除列表项"))
//		{
//			String Id = ListAdapter.iteamCode;
//			if(!Id.equals(""))
//			{
//				ListAdapter.iteamCode = "";
//				
//				codeList.contains(Id);
//				Set.remove(Id);
//				
//				Toast.makeText(this, Id + "已删除", Toast.LENGTH_SHORT).show();
//			}
//		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == InputBox.CODE_InputBox)
		{
			try
			{
				String value = data.getStringExtra("VALUE").trim();	// 获取输入的股票代码
				AddIteam(value);	// 添加
			}
			catch(Exception ex)
			{
				
			}
		}
	}
	
	/** 添加指定Id对应的数据项 */
	private void AddIteam(String Id)
	{
		PauseResh = true;
		
		if(Id.trim().equals("")) return;
		
		if(!codeList.contains(Id))
		{
			Set.put(Id, Id);
			
			codeList = ListIteamData.getCodeList(datas);				// 修改载入的列表顺序
			if(!codeList.contains(Id)) codeList.add(Id);
			String data = Tool.ToSingleString(codeList);
			LocalDB.saveData("codeList" + pageIndex, data);				// 保存代码数据信息
		}
		
		LoadWebData();
		RefreshLogic();
		Toast.makeText(this, Id + "已添加", Toast.LENGTH_SHORT).show();
		
		PauseResh = false;
	}
	
	/** 删除指定Id对应的数据项 */
	private void RemoveIteam(String Id)
	{
		PauseResh = true;
		
		Set.remove(Id);
		
		codeList = ListIteamData.getCodeList(datas);				// 修改载入的列表顺序
		if(codeList.contains(Id)) codeList.remove(Id);
		String data = Tool.ToSingleString(codeList);
		LocalDB.saveData("codeList" + pageIndex, data);				// 保存代码数据信息
		
		RefreshLogic();
		
		Toast.makeText(ListPage.this, Id + "已删除", Toast.LENGTH_SHORT).show();
		
		PauseResh = false;
	}
	
	/** 对列表信息进行排序 */
	private void SortListView(int index)
	{
		if(SortIndex != index) SortIndex = index;
		else Resort = !Resort;
		
		if(Resort) datas = ListIteamData.ReSort(datas, index);		// 对数据进行逆排序
		else datas = ListIteamData.Sort(datas, index);				// 对数据进行排序
		
		codeList = ListIteamData.getCodeList(datas);				// 修改载入的列表顺序
		SaveCodeList();
		
		LoadAllDatas();
		ShowList();
	}
	
	/** 保存代码数据信息 */
	private void SaveCodeList()
	{
		codeList = ListIteamData.getCodeList(datas);				// 修改载入的列表顺序
		String data = Tool.ToSingleString(codeList);
		
		LocalDB.saveData("codeList" + pageIndex, data);				// 保存代码数据信息
	}
	
	/** 载入代码数据信息 */
	private void LoadCodeList()
	{
		String data = LocalDB.readData("codeList" + pageIndex);				// 保存代码数据信息
		codeList = Tool.ToStringArrayList(data);
	}
	
	CallBackF ListIteamClickCall = new CallBackF()
	{
		@Override
		public void F(Object... param)
		{
			String Id = (String)param[0];
			RemoveIteam(Id);
		}
	};
	
	
	ListIteamData[] datas_pre = null;	// 记录前一次显示的列表信息
	
	ListIteamData[] datas = null;		// 当前待展示的列表信息
	int SortIndex = 1;		// 排序索引值
	boolean Resort = false;	// 是否为逆向排序
	
	/** 更新列表显示信息 */
	public void ShowList()
	{
		if(dataList == null || dataList.size() == 0) return;
		datas = ListIteamData.ToArray(dataList); 	// 从数据解析列表项数据
		
		if(datas_pre == null || !ListIteamData.IsEqual(datas, datas_pre))	// 当数据有变动时，更新显示
		{
			datas_pre = datas;
			
			ListAdapter adapter = new ListAdapter(ListPage.this, "ltpay_layout_guider_listiteam", datas, ListIteamClickCall);
			
	//		View addNew = TextView("ltpay_text_add_new");	// 获取添加按钮
			
			LinearLayout content = LinearLayout("ltpay_content");		// 获取页面的LinerLayout作为显示列表的ViewGroup
			adapter.ShowListViewIn(content);	// 显示列表
			
	//		content.addView(addNew);						// 添加按钮添加至末尾处
		}
	}
	
	//----------------------------------------
	// 刷新数据和显示

	private static boolean PauseResh = false;
	/** 定时刷新载入网络数据，并显示  */
	private void Refresh()
	{
		ThreadPram param = new ThreadPram()
		{
			@Override
			public void Function()
			{
				if(!PauseResh)
				{
					LoadWebData();
					
					RefreshLogic();
				}
				Refresh();
			}
		};
		
		ThreadTool.RunInMainThread(param, 15000);
	}
	
	/** 载入本地数据 并 刷新显示 */
	private void RefreshLogic()
	{
		loadAllCode();
		LoadAllDatas();
		
		ShowList();
	}
	
	
	//----------------------------------------
	// 载入数据配置信息
	
	List<String> codeList = new ArrayList<String>();	// 所有股票代码
	
	/** 载入已保存的代码信息 */
	private List<String> loadAllCode()
	{
		List<String> keys = Set.Keys();
		if(codeList.size() != keys.size())
		{
			// 移除配置中不含有的code值
			List<String> codeListRemove = new ArrayList<String>();
			for(String code : codeList)
			{
				if(!keys.contains(code)) codeListRemove.add(code);	// 记录所有待移除
			}
			for(String code : codeListRemove)
			{
				codeList.remove(code);		// 执行移除
			}
			
			
			// 从配置中添加编码code值
			for (String key : keys)
			{
				if(!codeList.contains(key))
				{
					codeList.add(key);
				}
			}
		}
		
		return codeList;
	}
	
	List<String> dataList = new ArrayList<String>();	// 数据信息
	
	/** 载入已保存的代码数据 */
	private List<String> LoadAllDatas()
	{
		dataList.clear();
		for (String key : codeList)
		{
			String value = Set.get(key);
			dataList.add(value);
		}
		
		return dataList;
	}
	
	/** 载入载入网络数据信息 */
	private void LoadWebData()
	{
		for(String Id : codeList)
		{
			try
			{
				String real = Tool.getRealValue(Id).trim();
				String[] A = Tool.getAvgData(Id);	
				if(A.length > 1)
				{
					if(real.equals("") && A.length > 2)  real = A[2];	// 若未查询到实时数据，则使用前一天的收盘值
					
					ListIteamData iteam = new ListIteamData(Id, A[0], A[1], real);
					Set.put(Id, iteam.ToString());
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	
}


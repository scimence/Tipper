
package com.sc.serviceDemo;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.service.TipService;
import com.sc.tool.ActivityComponent;
import com.sc.tool.Preference;
import com.sc.update.AppUpdate;


public class MainActivity extends ActivityComponent
{
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//	}
//	
//	// 启动服务
//	public void startService(View v)
//	{
//		TipService.GetInstance().start(this, TipService.class);
//	}
//	
//	// 暂时停止服务，服务逻辑会在应用退出后自行重启，并一直运行
//	public void stopService(View v)
//	{
//		TipService.GetInstance().stop();
//	}

//	public static String SetName = "tipper_data";
//	Preference Set = new Preference(this, SetName);
	Preference Set;
	
	LinearLayout linerSetting = null;
	ArrayList<TextView> MsgList = new ArrayList<TextView>();	// 信息
	
	@Override
	public void Init(Bundle savedInstanceState)
	{
		this.setContentView("activity_main"); // 设置Activity页面布局
		
		linerSetting = this.LinearLayout("linearSettings");
		
		Set = TipLogic.getSet(this);
		loadAllSet();
		
		
//		String logFile = FileTool.getLastFile(FileTool.LogPath);	// 获取目录下的文件
//		FileTool.showFileData(this.TextView("textMSG"), logFile);	// 显示文件内容

//		LoadContactInfo();	// 载入联系人信息
//		CallService.GetInstance().start(this, CallService.class, 500);	// 启动来电屏蔽服务
		
		// this.setOptionMenu("shield_option"); // 设置Option菜单
		// this.AddOptionMenu("拦截设置");
		// this.AddOptionMenu("不拦截");
		// this.AddOptionMenu("菜单项3(子菜单1;子菜单2;子菜单3(子菜单3-1;子菜单3-2(3-2-1;3-2-2;3-2-3)))");
		//
		// this.AddContextMenu(Button("btnKeepSet"), "Context菜单1");
		// this.AddContextMenu(Button("btnKeepSet"), "Context菜单2(2-1;2-2)");
		//
		// this.setContextMenu_Res(Button("btnClearRecord"), "shield_option");

		String ConfigUrl = "https://scimence.gitee.io/Tipper/update.txt";	// 服务端最新版本配置信息
		String curVersion = "201910291020";									// 当前版本信息
		AppUpdate.CheckUpdate(this, ConfigUrl, curVersion);					// 检测版本自动更新
	}


	@Override
	public void Click(String viewId)
	{
		if (viewId.equals("buttonStart"))
		{
			TipService.GetInstance().start(this, TipService.class);
			Toast.makeText(this, "服务已启动 !", Toast.LENGTH_SHORT).show();
		}
		else if (viewId.equals("buttonStop"))
		{
			TipService.GetInstance().stop();
			Toast.makeText(this, "服务已停止 !", Toast.LENGTH_SHORT).show();
		}
		else if (viewId.equals("btnAddSet"))
		{
			creatNewLine(linerSetting, "");
		}
		else if (viewId.equals("btnSaveSet"))
		{
			SaveSetting();
		}
		else if (viewId.equals("btnClearSet"))
		{
			ClearSet();
		}
	}
	
	// Toast显示消息
	public void ShowToast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	// 添加新的配置信息行
	public void creatNewLine(LinearLayout root, String msg)
	{
		Context context = root.getContext();
		
		LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		LinearLayout line1 = new LinearLayout(context);
		line1.setOrientation(LinearLayout.HORIZONTAL);
		
		root.addView(line1, lineParams);	// 添加line1控件
		{
			EditText text = new EditText(context);
			text.setHint("请输入待提示的**代码");
			if (msg != null && !msg.equals("")) text.setText(msg);
			line1.addView(text, textParams);
			
			MsgList.add(text);
		}
	}
	

	// 保存失败的数据信息
	private void SaveSetting()
	{
		Set.clear();
		
		ArrayList<String> tmp = new ArrayList<String>();
		
		for (int i = 0; i < MsgList.size(); i++)
		{
			TextView view = MsgList.get(i);
			String msg = view.getText().toString().trim();
			if(msg.equals("")) continue;
			
			if (!tmp.contains(msg))	// 缓存去重
			{
				Set.put("msg" + (i + 1), msg);
				tmp.add(msg);
			}
		}
		
		ShowToast("配置信息已保存");
	}
	
	// 清空配置
	private void ClearSet()
	{
		new AlertDialog.Builder(this).setTitle("确定清空所有配置信息？").setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Set.clear();
				linerSetting.removeAllViews();
				
				ShowToast("配置信息已清空");
			}
		}).setNegativeButton("取消", null).show();
	}
	
	// 载入所有已保存的配置信息
	private void loadAllSet()
	{
		for (String key : Set.Keys())
		{
			String value = Set.get(key);
			creatNewLine(linerSetting, value);
		}
	}
}

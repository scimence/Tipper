package sci.guider.pages;

import java.util.ArrayList;

import sci.tool.ListViewCommonAdapter;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.sc.tool.CallBackF;
import com.sc.tool.Tool;
 
 
/** ListAdapter.java: ----- 2018-10-25 下午8:32:21 scimence */
public class ListAdapter extends ListViewCommonAdapter<ListIteamData>
{
	CallBackF call=null;
	public ListAdapter(Context context, String listIteam_LayoutName, ListIteamData[] IteamDatas, CallBackF call)
	{
		super(context, listIteam_LayoutName, IteamDatas);
		this.call = call;
	}
	
	/** 1、继承方法 ListViewCommonAdapter(), 通过该接口指定单个列表项的布局文件名、设置所有列表项数据；
	 * 
	 * @param context
	 * @param listIteam_LayoutName 布局文件名
	 * @param IteamDatas 所有列表项数据 */
	public ListAdapter(Context context, String listIteam_LayoutName, ListIteamData[] IteamDatas)
	{
		super(context, listIteam_LayoutName, IteamDatas);
	}
	
	/** 2、setIteamView(TE iteamData)， 设置每个列表项的显示；
	 * 
	 * @param iteamData 将显示的列表项的对应数据 */
	@Override
	public void setIteamView(ListIteamData iteamData)
	{
		// TODO 根据列表项数据iteamData，设置列表项的显示
		// View view = IteamView("name"); //获取列表项中，指定名称的view
		
//		// 游戏Icon
//		ImageView Icon = (ImageView) IteamView("thumbsup_iteam_icon");
//		Icon.setImageDrawable(iteamData.icon);
//		
//		// 按钮
//		ImageView button = (ImageView) IteamView("thumbsup_iteam_button");
//		button.setImageDrawable(!iteamData.isClicked ? iteamData.btn1 : iteamData.btn2);
		
		// 名称
		TextView text = (TextView) IteamView("ltpay_text_name");
		text.setText(iteamData.name);
		
		// 代码
		text = (TextView) IteamView("ltpay_text_code");
		text.setText(iteamData.code);
		
		// 参考价
		text = (TextView) IteamView("ltpay_text_avg");
		text.setText(Tool.F2(iteamData.avg));
		
		// 市价
		text = (TextView) IteamView("ltpay_text_real");
		text.setText(Tool.F2(iteamData.real));
		
		// 涨跌幅
		text = (TextView) IteamView("ltpay_text_rate");
		text.setText(Tool.Rate(iteamData.rate));
		text.setTextColor(ToColor(iteamData.rate));		// 设置数据项颜色
	}
	
	/** 获取num值对应的数据项颜色值 */
	private int ToColor(float num)
	{
		int color = Color.parseColor("#969696");
		if(num <= -0.5f) color = Color.parseColor("#43a346");
		else if(num >= 0.5f) color = Color.parseColor("#f93413");
		
		return color;
	}
	
	private static Long preClickTime = 0L;
	private static String preClickCode = "";
	
	/** 3、setIteamClick(Context iteamContext, TE iteamData)， 设置每个列表项的点击响应；
	 * 
	 * @param iteamContext 列表项对应的context
	 * @param iteamData 待设置点击逻辑的列表项的对应数据 */
	@Override
	public void setIteamClick(Context iteamContext, ListIteamData iteamData)
	{
		// TODO 根据列表项数据iteamData，设置列表项的点击处理逻辑
		// View view = IteamView("name"); //获取列表项中，指定名称的view
		
		// 当用户双击列表项时，删除
		Long curTime = System.currentTimeMillis();
		if(curTime-preClickTime <= 300 && preClickCode.equals(iteamData.code))
		{
			if(call != null) call.F(iteamData.code);
		}
		preClickTime = curTime;
		preClickCode = iteamData.code;
		
//		if (!iteamData.isClicked)
//		{
//			iteamData.isClicked = true;
//			
//			// 设置按钮为已点击对应的图像
//			ImageView button = (ImageView) IteamView("thumbsup_iteam_button");
//			button.setImageDrawable(iteamData.btn2);
//			
//			// 执行按钮点击对应逻辑
//			// ...
//			
//		}
	}
	
}

package com.sc.broad;

//import java.util.HashMap;
 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sc.service.TipService;
 
/* 需在AndroidManifest.xml添加广播静态配置，<intent-filter/>中添加需要监听的广播类型
<application ...>
...
<receiver
    android:name="com.sc.broad.BroadCast_Start"
    android:exported="true" >
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
        <action android:name="android.intent.action.SCREEN_OFF" />
        <action android:name="android.intent.action.SCREEN_ON" />
        
        <!-- 自定义广播类型 -->
        <action android:name="com.sc.broad.actionDemo" />
    </intent-filter>
</receiver>
</application>
*/
 
/** AndroidManifest.xml的静态注册广播（安装app时会自动注册，app未运行时也可正常接收广播 ）
 * 
 * 用法：无需调用，通过开启、锁屏 广播启动电话监听Service */
public class BroadCast_Start extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		Broad(action, context, intent);
	}
	
	/** 广播回调,action为广播类型 */
	public void Broad(String action, Context context, Intent intent)
	{
//		if(action.equals("android.intent.action.BOOT_COMPLETED"))
//		{
//			CallService.GetInstance().start(context, CallService.class);
			TipService.GetInstance().start(context, TipService.class);
//		}
	}
	
	
	// ------------
	
//	/** 发送自定义广播，action广播名称 如："com.sc.broad.actionDemo"; extData广播附带的数据； extName广播数据对应的名称 */
//	public void SendBroadCast(Context context, String action, String extName, String extData)
//	{
//		BroadCastTool.SendBroadCast(context, action, extName, extData);
//	}
//	
//	/** 发送自定义广播，action广播名称 如："com.sc.broad.actionDemo"; ext为广播附带的数据 */
//	public void SendBroadCast(Context context, String action, HashMap<String, String> ext)
//	{
//		BroadCastTool.SendBroadCast(context, action, ext);
//	}
}


package com.sc.service;

import android.widget.Toast;

import com.sc.Notification.NotificationTool;
import com.sc.serviceDemo.TipLogic;
import com.sc.tipper.R;

/* 在AndroidManifest.xml添加 <service/>
<application ...>
....
        <service
            android:name="com.sc.service.TipService"
            android:enabled="true"
            android:exported="true" >
        </service>
</application>
*/

/**
 * 定义：继承BaseService，重写自定义服务逻辑serviceLogic()
 * 
 * 1、获取服务：TipServices.GetInstance()
 * 
 * 2、启动服务：TipService.GetInstance().start(context, TipService.class);
 * 3、停止服务：TipServices.GetInstance().stop();
 * */
public class TipService extends BaseService
{
//	static int count = 0;
	
	/** 在service中待执行的逻辑（在service未停止时，会一直执行）*/
	public void serviceLogic()
	{
//		Toast.makeText(this, "serviceLogic: " + count++, Toast.LENGTH_SHORT).show();
//		
//		if(count == 3) 
//		{
//			NotificationTool.ShowNotification(this, -1, R.drawable.ic_launcher, "图标边的文字", "标题", "内容");
//			count = 0;
//		}
		
		TipLogic.Doing(this.getApplicationContext());	// 执行提示信息处理逻辑
			
			
		// TODO Auto-generated method stub
		// ...
		// this.stop(); // 停止服务
	}
}

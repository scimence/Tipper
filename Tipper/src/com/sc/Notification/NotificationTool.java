
package com.sc.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


public class NotificationTool
{
	public static int notification_id = 19172439;
	
	// NotificationManager nm = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	
	/** 获取NotificationManager */
	public static NotificationManager getNotificationManager(Context context)
	{
		NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		return nm;
	}
	

	/** 显示通知栏消息, targIntent为点击消息时，跳转的Intent */
	public static void ShowNotification(Context context, int notification_id, int icon, String tickertext, String title, String content, Intent targIntent/*, String FilePath*/)
	{
		if (notification_id == -1) notification_id = NotificationTool.notification_id;
		
		PendingIntent pI = PendingIntent.getActivity(context, 0, targIntent, PendingIntent.FLAG_UPDATE_CURRENT/*, targIntent.getExtras()*/);		// 点击通知后的动作，转回main 这个Acticity
		
		Notification notification = new Notification.Builder(context)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(icon)
//        .setLargeIcon(icon)
        .setContentIntent(pI)
        .build();
		
		notification.defaults = Notification.DEFAULT_VIBRATE;
		
		NotificationManager nm = getNotificationManager(context);
		nm.notify(notification_id, notification);						// 显示通知消息
	}
	
	/** 显示通知栏消息, targIntent为点击消息时，跳转的Intent */
	public static void ShowNotification0(Context context, int notification_id, int icon, String tickertext, String title, String content, Intent targIntent/*, String FilePath*/)
	{
		if (notification_id == -1) notification_id = NotificationTool.notification_id;
		
		// 设置一个唯一的ID，随便设置
		Notification notification = new Notification(icon, tickertext, System.currentTimeMillis());
		notification.defaults = Notification.DEFAULT_ALL;
		// Notification.DEFAULT_SOUND;
		// Notification.DEFAULT_VIBRATE;
		// Notification.DEFAULT_LIGHTS
		
//		PendingIntent pI = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);		// 点击通知后的动作，转回main 这个Acticity
		
		// 点击通知消息， 展示的Intent对应的文件
		PendingIntent pI = PendingIntent.getActivity(context, 0, targIntent, 0);		// 点击通知后的动作，转回main 这个Acticity
		
		notification.setLatestEventInfo(context, title, content, pI);	// 设置通知信息
		
		
		NotificationManager nm = getNotificationManager(context);
		nm.notify(notification_id, notification);						// 显示通知消息
	}
	
	/** 移除状态栏通知, 此函数仅可删除当前应用自己发出的通知消息 */
	public static void RemoveNotification(Context context, int notification_id)
	{
		if (notification_id == -1) notification_id = NotificationTool.notification_id;
		
		if (context != null)
		{
			try
			{
				NotificationManager nm = getNotificationManager(context);
				nm.cancel(notification_id);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/** Notification权限开关 */
	public static void ShowSetting(Context context)
	{
		// NotificationMonitorService services = new NotificationMonitorService();
		// services.startService(main.getIntent());
		// String string = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
		// if (!string.contains(NotificationListenerService.class.getName()))
		// {
		context.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
		// }
	}
	
	// private static Context getContext(Context context)
	// {
	// Context c = null;
	// try
	// {
	// c = context.createPackageContext("sci.Notification", Context.CONTEXT_IGNORE_SECURITY);
	// }
	// catch (NameNotFoundException e)
	// {
	// e.printStackTrace();
	// }
	// return c;
	// }
}

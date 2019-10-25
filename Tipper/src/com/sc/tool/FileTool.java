
package com.sc.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;
import android.widget.TextView;


public class FileTool
{
	static String TAG = "FileLog";
	
	public static boolean OutLogFile = true;	// 输出log信息到文件
	public static String LogPath = "/sdcard/sc/Tipper/Log/";
	
	/** 输出log信息到文件中 */
	public static String FileLog(String info)
	{
		if (OutLogFile)
		{
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = formatter.format(new Date());
			
			DateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
			String time = formatter2.format(new Date()) + "  ";
			
			String fileName = "log-" + date + ".txt";
			info = "\r\n" + time + info;
			
			try
			{
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					// String path = "/sdcard/com.hm.pay.demo/crash/";
					File dir = new File(LogPath);
					if (!dir.exists())
					{
						dir.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream(LogPath + fileName, true);
					fos.write(info.getBytes());
					fos.close();
				}
			}
			catch (Exception e)
			{
				Log.e(TAG, "an error occured while writing file log...", e);
			}
			return LogPath + fileName;
		}
		else return "";
	}
	
	/** 获取目录下最后一个文件 */
	public static String getLastFile(String dirPath)
	{
		File f = new File(dirPath);
		if (f.exists() && f.isDirectory())
		{
			String[] subFiles = f.list();
			if (subFiles.length > 0) return dirPath + subFiles[subFiles.length - 1];
		}
		return "";
	}
	
	/** 删除文件或目录下所有文件 */
	public static boolean ClearDir(File dir)
	{
		if (!dir.exists()) return false;
		
		if (dir.isFile())
			dir.delete();
		else if (dir.isDirectory())
		{
			for (File f : dir.listFiles())
			{
				if (!ClearDir(f)) return false;
			}
			if (!dir.delete()) return false;
		}
		return true;
	}
	
	/** 在textView上显示文件filePath中数据 */
	public static void showFileData(TextView textView, String filePath)
	{
		if (filePath == null || filePath.equals("")) return;
		
		String data = FileToString(filePath);
		textView.setText(data);
	}
	
	/** 获取文件数据 */
	public static String FileToString(String filePath)
	{
		String data = "";
		try
		{
			FileInputStream in = new FileInputStream(filePath);
			data = TypeTool.InputStreamToString(in);
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return data;
	}
}

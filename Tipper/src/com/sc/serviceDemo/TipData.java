
package com.sc.serviceDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sc.tool.TypeTool;

import android.os.Environment;
import android.util.Log;


public class TipData
{
	private static String DataPath = "/sdcard/sc/Tipper/Data/";
	static String TAG = "TipData";
	
	String Id = "";
	String filePath = "";
	
	public TipData(String Id)
	{
		this.Id = Id;
		filePath = FilePath(Id);
	}
	
	/** 获取数据文件的完整保存路径 */
	private String FilePath(String Id)
	{
		File dir = new File(DataPath + Id + "/");
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(new Date());
		String fileName = date + ".txt";
		
		return DataPath + Id + "/" + fileName;
	}
	
	/** 实时输出value值到文件中 */
	public void SaveValue(String value)
	{
		DateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
		String time = formatter2.format(new Date());
		
		value = "\r\n" + time + "; " + value;
		
		try
		{
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				FileOutputStream fos = new FileOutputStream(filePath, true);
				fos.write(value.getBytes());
				fos.close();
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "TipData -> SaveValue时出现异常 ..." + "\r\n" + e.toString() , e);
		}
	}
	
	//----------------------
	
	List<Float> values = new ArrayList<Float>();	// 数据值
	List<String> names = new ArrayList<String>();	// 名称
	
	/** 重新载入数据信息 */
	private void ReloadData()
	{
		values.clear();
		names.clear();
		
		String data = FileToString(filePath);
		
		data = data.replace("\r\n", "\n");
		String[] Lines = data.split("\n");
		
		for(String line : Lines)
		{
			if(line.trim().equals("")) continue;	// 忽略空行
			String[] Array = line.split(";");
			if(Array.length > 1)
			{
				names.add(Array[0].trim());
				
				Float dNum = Float.valueOf(Array[1].trim());
				values.add(dNum);
			}
		}
	}
	
	/** 获取数据值 */
	public List<Float> Values(boolean reload)
	{
		if(reload) ReloadData();
		return values;
	}
	

	/** 获取数据名称 */
	public List<String> Names(boolean reload)
	{
		if(reload) ReloadData();
		return names;
	}
	
	//----------------------
	

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
	
//	/** 获取目录下最后一个文件 */
//	public static String getLastFile(String dirPath)
//	{
//		File f = new File(dirPath);
//		if (f.exists() && f.isDirectory())
//		{
//			String[] subFiles = f.list();
//			if (subFiles.length > 0) return dirPath + subFiles[subFiles.length - 1];
//		}
//		return "";
//	}
//	
//	/** 删除文件或目录下所有文件 */
//	public static boolean ClearDir(File dir)
//	{
//		if (!dir.exists()) return false;
//		
//		if (dir.isFile())
//			dir.delete();
//		else if (dir.isDirectory())
//		{
//			for (File f : dir.listFiles())
//			{
//				if (!ClearDir(f)) return false;
//			}
//			if (!dir.delete()) return false;
//		}
//		return true;
//	}
//	
//	/** 在textView上显示文件filePath中数据 */
//	public static void showFileData(TextView textView, String filePath)
//	{
//		if (filePath == null || filePath.equals("")) return;
//		
//		String data = FileToString(filePath);
//		textView.setText(data);
//	}
	
}

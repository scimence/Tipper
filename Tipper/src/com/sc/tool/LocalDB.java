package com.sc.tool;


//===========================================================================================================
//2、保存数据到文件
//===========================================================================================================

//类LocalDB.java

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.os.Environment;


/** 
* 读数据：readData(String Key)、保存数据：saveData(String Key, String value) 
* 
* AndroidManifest.xml中添加sd卡存储权限
* 	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
*  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
*  
*  检测存储路径，是否可以存储数据 CanUse()
*  
* ----- 2015-9-24 上午11:44:52 scimence */
public class LocalDB
{
	private static String pathString = "resource";
	private static String fileName = "TipperDB.data";    // 用于保存数据的文件名
	
	public static boolean loaded = false;               // 标识当前是否已进行了初始载入
	public static HashMap<String, Object> $object;
	public static HashMap<String, Integer> $int;
	public static HashMap<String, Float> $float;
	public static HashMap<String, String> $string;
	public static HashMap<String, Double> $double;
	public static HashMap<String, Long> $long;
	public static HashMap<String, Byte> $byte;
	public static HashMap<String, Short> $short;
	public static HashMap<String, Boolean> $boolean;
	
	// 使用HashMap的静态对象，在程序中临时保存数据
	// private static HashMap<String, Type> $type; //创建HashMap对象
	
	// HashMap用法如下：
	// $type.put(String key, Type value) //存储Type类型数据到$type，索引标识key 存储
	// $type.get(Object key) //从$type中获取索引标识key的数据 获取
	// $type.containsKey(Object key) //判断$type中是否含有索引标识key 判断
	// $type.remove(Object key) //将索引标识key对应的数据从$type中移除 移除
	
	/** 保存所有对象的数据，到文件fileName中 - 保存，HashMap对象数据到文件 */
	public static void save()
	{
		try
		{
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 有SD卡
				pathString = Environment.getExternalStorageDirectory().getPath(); // 获得SD卡路径
				
			{
				File file;
				FileOutputStream f;
				
				try
				{
					file = new File(pathString + "/" + fileName);
					
					if (!file.exists()) file.createNewFile(); // 若文件不存在，则新建
					f = new FileOutputStream(file, false);  // 创建一个文件输出流，true表示在文件末尾添加， false覆盖
				}
				catch (Exception e)
				{
					return;
				}
				
				ObjectOutputStream o = new ObjectOutputStream(f);
				o.writeObject($object);
				o.writeObject($int);
				o.writeObject($byte);
				o.writeObject($float);
				o.writeObject($double);
				o.writeObject($string);
				o.writeObject($long);
				o.writeObject($boolean);
				o.writeObject($short);
				o.flush();
				f.flush();
				o.close();
				f.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** 从文件fileName中，获取保存的数据到静态对象中 - 载入，保存数据到HashMap对象中 */
	@SuppressWarnings("unchecked")
	public static void load()
	{
		if (loaded) return;
		
		try
		{
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 有SD卡
				pathString = Environment.getExternalStorageDirectory().getPath(); // 获得SD卡路径
			{
				File file = null;
				FileInputStream f = null;
				
				try
				{
					file = new File(pathString + "/" + fileName);
					
					if (!file.exists()) file.createNewFile(); 	// 若文件不存在，则新建
					f = new FileInputStream(file);              // 从文件创建一个输入流
				}
				catch (Exception e)
				{
					String error = e.toString();
					return;
				}
				
				try
				{
					ObjectInputStream o = new ObjectInputStream(f);
					$object = (HashMap<String, Object>) o.readObject();
					$int = (HashMap<String, Integer>) o.readObject();
					$byte = (HashMap<String, Byte>) o.readObject();
					$float = (HashMap<String, Float>) o.readObject();
					$double = (HashMap<String, Double>) o.readObject();
					$string = (HashMap<String, String>) o.readObject();
					$long = (HashMap<String, Long>) o.readObject();
					$boolean = (HashMap<String, Boolean>) o.readObject();
					$short = (HashMap<String, Short>) o.readObject();
					
					o.close();
					f.close();
				}
				catch (Exception e)
				{
					$object = new HashMap<String, Object>();
					$float = new HashMap<String, Float>();
					$int = new HashMap<String, Integer>();
					$string = new HashMap<String, String>();
					$double = new HashMap<String, Double>();
					$long = new HashMap<String, Long>();
					$byte = new HashMap<String, Byte>();
					$short = new HashMap<String, Short>();
					$boolean = new HashMap<String, Boolean>();
					
					f.close();
				}
			}
			
			loaded = true;  // 初始载入完成
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** 清除游戏数据 */
	public static void reset()
	{
		// 清除已有数据
		if ($object != null) $object.clear();
		if ($float != null) $float.clear();
		if ($int != null) $int.clear();
		if ($string != null) $string.clear();
		if ($double != null) $double.clear();
		if ($long != null) $long.clear();
		if ($byte != null) $byte.clear();
		if ($short != null) $short.clear();
		if ($boolean != null) $boolean.clear();
		
		if ($object == null) $object = new HashMap<String, Object>();
		if ($float == null) $float = new HashMap<String, Float>();
		if ($int == null) $int = new HashMap<String, Integer>();
		if ($string == null) $string = new HashMap<String, String>();
		if ($double == null) $double = new HashMap<String, Double>();
		if ($long == null) $long = new HashMap<String, Long>();
		if ($byte == null) $byte = new HashMap<String, Byte>();
		if ($short == null) $short = new HashMap<String, Short>();
		if ($boolean == null) $boolean = new HashMap<String, Boolean>();
		
		save(); // 保存空数据到文件
	}
	
	/** 检测存储路径，是否可以存储数据 */
	public static boolean CanUse()
	{
		try
		{
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 有SD卡
				pathString = Environment.getExternalStorageDirectory().getPath(); // 获得SD卡路径
				
			File file = new File(pathString + "/" + fileName);
			
			if (!file.exists()) file.createNewFile(); // 若文件不存在，则新建
			if (file.exists()) return true;
			
		}
		catch (Exception e)
		{}
		
		return false;
	}
	
	// --------------------------存储、读取-------------------------------------------
	
	/** 数据存储，将数据value加密后，按键值Key存储至HashMap和数据文件中 */
	public static void saveData(String Key, String value)
	{
		saveData2HashMap(Key, value);
		save();                             // 保存数据到文件
	}
	
	/** 数据存储，将数据value加密后，按键值Key存储至HashMap中 */
	public static void saveData2HashMap(String Key, String value)
	{
		if ($string == null) load();        // 初始化
		Key = Encryption(Key, 33741);       // 加密键名
		
		value = Encryption(value, 50413);   // 加密保存数据
		if ($string != null) $string.put(Key, value);            // 本地数据保存
	}
	
	/** 数据读取，读取指定键值的数据，并解密，无则返回空串 "" */
	public static String readData(String Key)
	{
		String value = "";
		
		if ($string == null) load();            // 从文件读取数据，或初始化
		Key = Encryption(Key, 33741);           // 加密键名
		
		if ($string != null && $string.containsKey(Key))
		{
			value = $string.get(Key);           // 获取数据
			value = Encryption(value, -50413);  // 解密串
		}
		return value;
	}
	
	/** 加密或解密字符串，change加密、-change解密 */
	public static String Encryption(String str, int change)
	{
		if (str.equals("") || str == null) return "";   // 空串不加密
			
		short sign = 1;
		if (change < 0)
		{
			sign = -1;
			change *= -1;
		}
		int num = 0;
		int tmp;
		
		byte[] bytes = str.getBytes();
		for (int i = 0; i < bytes.length; i++)
		{
			if (num == 0) num = change;
			
			// 限定在[0,127]之间
			tmp = bytes[i] + sign * (num % 3);
			if (tmp > 127) tmp -= 127;
			if (tmp < 0) tmp += 127;
			
			bytes[i] = (byte) tmp;
			num /= 3;
		}
		str = new String(bytes);
		return new String(bytes);
	}
}


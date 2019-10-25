package com.sc.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/** 应用数据保存 */
public class Preference
{
	private String Name = "data";
	private Context context = null;
	
	/** 创建指定名称的数据集 */
	public Preference(Context context, String NAME)
	{
		this.context = context;
		this.Name = NAME;
		if (NAME == null || NAME.equals("")) Name = "data";
	}
	
	private SharedPreferences getSharedPreferences()
	{
		SharedPreferences share = context.getApplicationContext().getSharedPreferences(Name, Context.MODE_PRIVATE);
		return share;
	}
	
	/** 记录key,value数据到context对应的Preferences */
	public void put(String key, String value)
	{
		SharedPreferences share = getSharedPreferences();
		Editor edit = share.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	/** 获取key对应的数据 */
	public String get(String key)
	{
		SharedPreferences share = getSharedPreferences();
		String value = share.getString(key, "");
		
		return value;
	}
	
	/** 获取所有key对应的数据 */
	public HashMap<String, String> getAll()
	{
		SharedPreferences share = getSharedPreferences();
		HashMap<String, String> map = (HashMap<String, String>) share.getAll();
		
		return map;
	}
	
	/** 获取所有key名称 */
	public Set<String> KeySet()
	{
		return getAll().keySet();
	}
	
	/** 获取所有key名称 */
	public List<String> Keys()
	{
		Set<String> set = getAll().keySet();
		List<String> list = new ArrayList<String>();
		for (String key : set)
		{
			list.add(0, key);
		}
		return list;
	}
	

	/** 获取所有Value值 */
	public List<String> Values()
	{
		List<String> list = new ArrayList<String>();
		for (String key : Keys())
		{
			String value = get(key);
			list.add(value);
		}
		return list;
	}
	
	/** 移除key对应的数据 */
	public void remove(String key)
	{
		SharedPreferences share = getSharedPreferences();
		Editor edit = share.edit();
		edit.remove(key);
		edit.commit();
	}
	
	/** 清空所有数据 */
	public void clear()
	{
		SharedPreferences share = getSharedPreferences();
		Editor edit = share.edit();
		edit.clear();
		edit.commit();
	}
}

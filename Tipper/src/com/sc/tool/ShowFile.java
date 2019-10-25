
package com.sc.tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;


/** 显示指定文件中的内容 */
public class ShowFile extends Activity
{
	TextView view = null;
	
	// EditText view = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		
		ScrollView scroll = new ScrollView(this);
		scroll.setPadding(15, 0, 15, 0);
		this.setContentView(scroll);
		
		view = new TextView(this);
		// view = new EditText(this);
		scroll.addView(view);
		// scroll.fullScroll(ScrollView.FOCUS_DOWN);
		
		// 获取指定文件的内容并显示
		Intent intent = this.getIntent();
		String filePath = intent.getStringExtra("FilePath");
		
		this.setTitle(filePath);
		loadFileData(filePath);
	}
	
	/** 载入并显示文件数据 */
	private void loadFileData(String filePath)
	{
		if (filePath == null || filePath.equals("")) return;
		
		String data = FileToString(filePath);
		view.setText(data);
	}
	
	/** 获取文件数据 */
	private String FileToString(String filePath)
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

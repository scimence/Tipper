package com.sc.tool;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


/** TypeTool.java:数据类型相互转化工具类 ----- 2018-10-23 上午10:08:54 scimence */
public class TypeTool
{
	/** Byte -> String */
	public static String ByteToString(byte[] bytes)
	{
		return new String(bytes);
	}
	
	/** String -> Byte */
	public static byte[] StringToByte(String data)
	{
		return data.getBytes();
	}
	
	/** Byte -> InputStream */
	public static final InputStream ByteToInputStream(byte[] bytes)
	{
		return new ByteArrayInputStream(bytes);
	}
	
	/** InputStream -> Byte */
	public static final byte[] InputStreamToByte(InputStream in)
	{
		byte[] bytes = {};
		
		try
		{
			ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
			byte[] data = new byte[1024];
			int count = 0;
			while ((count = in.read(data, 0, 1024)) > 0)
			{
				byteOutStream.write(data, 0, count);
			}
			
			bytes = byteOutStream.toByteArray();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return bytes;
	}
	
	/** InputStream -> String */
	public static String InputStreamToString(InputStream in)
	{
		String data = "";
		
		byte[] bytes = InputStreamToByte(in);
		data = new String(bytes);
		
		return data;
	}
	
	/** String -> InputStream */
	public static InputStream StringToInputStream(String data)
	{
		byte[] bytes = data.getBytes();
		InputStream inputstream = ByteToInputStream(bytes);
		return inputstream;
	}
	
	/** String -> InputStreamReader */
	public static InputStreamReader StringToInputStreamReader(String data)
	{
		byte[] bytes = data.getBytes();
		InputStream inputstream = ByteToInputStream(bytes);
		InputStreamReader reader = new InputStreamReader(inputstream);
		return reader;
	}
	
}



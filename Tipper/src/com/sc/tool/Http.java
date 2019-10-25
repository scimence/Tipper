
package com.sc.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class Http
{
	// url = "http://www.baidu.com"
	/** 获取指定网页数据 */
	public static String Get(String url)
	{
		try
		{
			StringBuilder tmp = new StringBuilder();
			
			URL URL_ = new URL(url);
			InputStream in = URL_.openStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader bufr = new BufferedReader(isr);
			
			String str;
			while ((str = bufr.readLine()) != null)
			{
				// System.out.println(str);
				tmp.append(str + "\r\n");
			}
			bufr.close();
			isr.close();
			in.close();
			
			return tmp.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
}
	
//	// webUrl = "http://www.baidu.com";
//	public static void getData1()
//	{
//		try
//		{
//			URL url = new URL("http://www.baidu.com");
//			URLConnection URLconnection = url.openConnection();
//			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
//			int responseCode = httpConnection.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_OK)
//			{
//				System.err.println("成功");
//				InputStream in = httpConnection.getInputStream();
//				InputStreamReader isr = new InputStreamReader(in);
//				BufferedReader bufr = new BufferedReader(isr);
//				String str;
//				while ((str = bufr.readLine()) != null)
//				{
//					System.out.println(str);
//				}
//				bufr.close();
//			}
//			else
//			{
//				System.err.println("失败");
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	public static void getData2(String args[]) throws Exception
//	{
//		try
//		{
//			URL url = new URL("http://www.baidu.com");
//			InputStream in = url.openStream();
//			InputStreamReader isr = new InputStreamReader(in);
//			BufferedReader bufr = new BufferedReader(isr);
//			String str;
//			while ((str = bufr.readLine()) != null)
//			{
//				System.out.println(str);
//			}
//			bufr.close();
//			isr.close();
//			in.close();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//}

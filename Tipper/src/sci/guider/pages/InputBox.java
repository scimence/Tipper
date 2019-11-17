
package sci.guider.pages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.sc.tool.ActivityComponent;


/** 获取屏幕点击处的坐标 */
public class InputBox extends ActivityComponent
{
	/** 显示当前Activity */
	public static void Show(Activity context, String tipInfo)
	{
//		context.finish();	// 关闭context对应的Activity
		
//		final Class cls = GetClickPostion.class;
//		new Handler(Looper.getMainLooper()).post(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				
//			}
//		});
		
		Intent intent = new Intent(context, InputBox.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("tipInfo", tipInfo);
		
//		context.startActivity(intent);
		context.startActivityForResult(intent, CODE_InputBox);
	}
	
	// ----------------
	
	@Override
	public void Init(Bundle savedInstanceState)
	{
		this.setContentView("layout_input");
		
		String tipInfo = this.getIntent().getStringExtra("tipInfo");
		EditText("editText").setHint(tipInfo);
		
//		Click("buttonReset");
	}

	public static final int CODE_InputBox = 50001;
	
	@Override
	public void Click(String viewId)
	{
		if (viewId.equals("buttonReset"))
		{
//			this.LinearLayout("linear1").setVisibility(View.INVISIBLE);
//			this.LinearLayout("linear2").setVisibility(View.INVISIBLE);
//			isSet = false;
			
			this.finish();
		}
		else if (viewId.equals("buttonFinish"))
		{
			EditText edit = EditText("editText");
			String value = edit.getText().toString();
			
			Intent data = new Intent();
			data.putExtra("VALUE", value);
			
			this.setResult(CODE_InputBox, data);	// 返回选取的坐标
			this.finish();
		}
	}
	
//	public boolean dispatchTouchEvent(MotionEvent event)
//	{
//		switch (event.getAction())
//		{
//			case MotionEvent.ACTION_DOWN:
//				setPosition((int) event.getX(), (int) event.getY());
//				break;
//			
//			case MotionEvent.ACTION_UP:
//				break;
//		}
//		return super.dispatchTouchEvent(event);
//		
//	}
//	
//	boolean isSet = false;
//	int x;
//	int y;
//	
//	public void setPosition(int x, int y)
//	{
//		if (!isSet)
//		{
//			isSet = true;
//			LinearLayout("linear1").setVisibility(View.VISIBLE);
//			LinearLayout("linear2").setVisibility(View.VISIBLE);
//			this.EditText("editText1").setText("" + x + ", " + y);
//		}
//	}
//	
}

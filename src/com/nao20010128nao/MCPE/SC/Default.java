package com.nao20010128nao.MCPE.SC;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import com.nao20010128nao.MC_PE.SkinChanger.CONVERTER.*;
import android.view.*;
import android.widget.*;
import android.util.*;
import java.util.*;
import android.content.*;
import java.lang.ref.*;
import android.graphics.*;

public class Default extends SmartFindViewActivity
{
	static WeakReference<Default> wr=new WeakReference(null);
	LinearLayout content;
	TextView input,output;
	boolean whileCreate;
	int defTextColor;
	Map<Integer,View> index=new HashMap<>();
	Map<Object,Object> cache=new HashMap<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		wr=new WeakReference<>(this);
		whileCreate=true;
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.def);
		content=find(R.id.content);
		input=find(R.id.inputext);
		output=find(R.id.outputext);
		whileCreate=false;
		setInside(((Intent)getIntent().clone()).setClass(this,MainActivity.class));
		defTextColor=input.getTextColors().getDefaultColor();
	}
	public void setInside(Intent intent){
		getLocalActivityManager().destroyActivity("main",true);
		content.removeAllViews();
		View v=getLocalActivityManager().startActivity("main",intent).getDecorView();
		content.addView(v);
	}
	public void setInputExtension(Formats fmt){
		int format=R.string.unknown;
		switch(fmt.internalValue()){
			case 0://JPEG
				format=R.string.jpeg;
				break;
			case 1://PNG
				format=R.string.png;
				break;
			case 2://GIF
				format=R.string.gif;
				break;
			case 3://WEBP
				format=R.string.webp;
				break;
			case 4://TGA
				format=R.string.tga;
				break;
		}
		input.setText(format);
		input.setTextColor(fmt==null?defTextColor:(fmt.isDeprecated()?Color.RED:defTextColor));
	}
	public void setOutputExtension(Formats fmt){
		int format=R.string.unknown;
		switch(fmt.internalValue()){
			case 0://JPEG
				format=R.string.jpeg;
				break;
			case 1://PNG
				format=R.string.png;
				break;
			case 2://GIF
				format=R.string.gif;
				break;
			case 3://WEBP
				format=R.string.webp;
				break;
			case 4://TGA
				format=R.string.tga;
				break;
		}
		output.setText(format);
		output.setTextColor(fmt==null?defTextColor:(fmt.isDeprecated()?Color.RED:defTextColor));
	}
}

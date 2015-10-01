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

public class Default extends SmartFindViewActivity
{
	static WeakReference<Default> wr=new WeakReference(null);
	LinearLayout content;
	TextView input,output;
	boolean whileCreate;
	Map<Integer,View> index=new HashMap<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		wr=new WeakReference(this);
		whileCreate=true;
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.def);
		content=find(R.id.content);
		input=find(R.id.inputext);
		output=find(R.id.outputext);
		whileCreate=false;
		setInside(((Intent)getIntent().clone()).setClass(this,MainActivity.class));
	}
	public void setInside(Intent intent){
		getLocalActivityManager().destroyActivity("main",true);
		content.removeAllViews();
		View v=getLocalActivityManager().startActivity("main",intent).getDecorView();
		content.addView(v);
	}
}

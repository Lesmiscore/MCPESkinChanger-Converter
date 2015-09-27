package com.nao20010128nao.MCPE.SC;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import com.nao20010128nao.MC_PE.SkinChanger.CONVERTER.*;
import android.view.*;
import android.widget.*;

public class Default extends SmartFindViewActivity
{
	LinearLayout content;
	TextView input,output;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.def);
		content=find(R.id.content);
		input=find(R.id.inputext);
		output=find(R.id.outputext);
	}

	@Override
	public void setContentView(int layoutResID) {
		// TODO: Implement this method
		content.removeAllViews();
		View v=getLayoutInflater().inflate(layoutResID,content,true);
		v.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
	}

	@Override
	public void setContentView(View view) {
		// TODO: Implement this method
		content.removeAllViews();
		content.addView(view);
		view.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
	}
}

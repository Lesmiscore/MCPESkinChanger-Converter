package com.nao20010128nao.MCPE.SC;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import com.nao20010128nao.MC_PE.SkinChanger.CONVERTER.*;
import android.view.*;
import android.widget.*;
import android.util.*;
import java.util.*;

public class Default extends SmartFindViewActivity
{
	LinearLayout content;
	TextView input,output;
	boolean whileCreate;
	Map<Integer,View> index=new HashMap<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		whileCreate=true;
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.def);
		content=find(R.id.content);
		input=find(R.id.inputext);
		output=find(R.id.outputext);
		whileCreate=false;
	}

	@Override
	public void setContentView(int layoutResID) {
		// TODO: Implement this method
		if(whileCreate)super.setContentView(layoutResID);
		View v=getLayoutInflater().inflate(layoutResID,null,false);
		setContentView(v);
	}

	@Override
	public void setContentView(View view) {
		// TODO: Implement this method
		if(whileCreate)super.setContentView(view);
		content.removeAllViews();
		content.addView(view);
		view.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
		runIndexing();
	}

	@Override
	public View findViewById(int id) {
		// TODO: Implement this method
		if(whileCreate)return super.findViewById(id);
		View s=super.findViewById(id);
		View c=content.findViewById(id);
		View i=index.get(id);
		if(s==null){
			if(c==null){
				if(i==null){
					return null;
				}else{
					return i;
				}
			}else{
				return c;
			}
		}else{
			return s;
		}
	}
	private void runIndexing(){
		for(int i=0;i<content.getChildCount();i++){
			View v=content.getChildAt(i);
			if(v.getId()!=View.NO_ID){
				index.put(v.getId(),v);
			}
			if(v instanceof ViewGroup){
				findAll((ViewGroup)v);
			}
		}
	}
	private void findAll(ViewGroup vg){
		for(int i=0;i<vg.getChildCount();i++){
			View v=vg.getChildAt(i);
			if(v.getId()!=View.NO_ID){
				index.put(v.getId(),v);
			}
			if(v instanceof ViewGroup){
				findAll((ViewGroup)v);
			}
		}
	}
}

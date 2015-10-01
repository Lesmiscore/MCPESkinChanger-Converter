package com.nao20010128nao.MCPE.SC;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import com.nao20010128nao.MC_PE.SkinChanger.CONVERTER.*;

public class InputSelected extends SmartFindViewActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inputcheck);
		new AsyncTask<String,Void,Formats>(){
			public Formats doInBackground(String[]s){
				String from=s[0];
				Formats result=null;
				
				return result;
			}
		}.execute(getIntent().getDataString());
	}
}

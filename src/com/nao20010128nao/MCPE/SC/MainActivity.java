package com.nao20010128nao.MCPE.SC;

import android.content.*;
import android.net.*;
import android.os.*;
import com.nao20010128nao.MCPE.SC.misc.*;
import com.nao20010128nao.MC_PE.SkinChanger.CONVERTER.*;
import com.nao20010128nao.ToolBox.HandledPreference.*;
import java.lang.ref.*;
import com.nao20010128nao.MCPE.SC.plugin.*;
import java.io.*;
import android.util.*;
import java.net.*;
import android.app.*;
import android.view.*;

public class MainActivity extends SmartFindViewActivity {
	public static WeakReference<MainActivity> instance=new WeakReference<>(null);
	static final String MIME_TGA="image/targa";
	volatile String changeTmp=null;
	public DiffMap<String,byte[]> data;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		instance = new WeakReference<MainActivity>(this);
		setContentView(R.layout.start);
       	try {
			data = PluginUtils.getMapFromIntent(getIntent());
		} catch (IOException e) {
			setResult(RESULT_CANCELED);
			finish();
		}
		findViewById(R.id.selectfile).setOnClickListener(new View.OnClickListener(){
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
					startActivityForResult(intent, 123);
				}
			});
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Implement this method
		switch(requestCode){
			case 123:
				Intent i=new Intent(this,InputSelected.class);
				i.putExtra("path",data.getDataString());
				startActivity(i);
				break;
		}
	}
}

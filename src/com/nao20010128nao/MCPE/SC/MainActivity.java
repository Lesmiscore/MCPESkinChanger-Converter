package com.nao20010128nao.MCPE.SC;

import android.content.*;
import android.net.*;
import android.os.*;
import com.nao20010128nao.MCPE.SC.misc.*;
import com.nao20010128nao.MC_PE.SkinChanger.REJECTED.*;
import com.nao20010128nao.ToolBox.HandledPreference.*;
import java.lang.ref.*;
import com.nao20010128nao.MCPE.SC.plugin.*;
import java.io.*;
import android.util.*;
import java.net.*;
import android.app.*;

public class MainActivity extends SHablePreferenceActivity {
	public static WeakReference<MainActivity> instance=new WeakReference<>(null);
	static final String MIME_TGA="image/targa";
	static boolean preventStart=false;
	volatile String changeTmp=null;
	DiffMap<String,byte[]> data;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		instance = new WeakReference<MainActivity>(this);
       	addPreferencesFromResource(R.xml.pref_main);
		try {
			data = PluginUtils.getMapFromIntent(getIntent());
		} catch (IOException e) {
			setResult(RESULT_CANCELED);
			finishF();
		}
		/*Rejected changes*/
		link("selectCaveSpider","assets/images/mob/cave_spider.tga",MIME_TGA);
		link("selectEnderman","assets/images/mob/enderman.tga",MIME_TGA);
		link("selectSpider","assets/images/mob/spider.tga",MIME_TGA);
		link("selectTamedWolf","assets/images/mob/wolf_tame.tga",MIME_TGA);
		/*Rejected changes END*/
		sH("openPlayStore",new OnClickListener(){
				public void onClick(String p1, String p2, String p3) {
					startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())),getResources().getString(R.string.openPlayStore)));
				}
			});
    }
	void selectFileForSkin(String name,String mime){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		changeTmp = name;
		startActivityForResult(intent, 123);
	}
	void link(String pref,String name,String mime){
		sH(pref,createListenerForPerf(name,mime));
	}
	OnClickListener createListenerForPerf(final String name,final String mime){
		return new OnClickListener(){
			public void onClick(String p1, String p2, String p3) {
				selectFileForSkin(name,mime);
			}
		};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Implement this method
		switch(requestCode){
			case 123:
				if(resultCode==RESULT_OK){
					loadInBackground(data.getDataString());
				}
				break;
		}
	}
	void loadInBackground(final String path){
		new Thread(){
			public void run(){
				InputStream is=null;
				ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
				try{
					is=tryOpen(path);
					byte[] buf=new byte[30000];
					while(true){
						int r=is.read(buf);
						if(r<=0)break;
						baos.write(buf,0,r);
					}
					data.put(changeTmp,baos.toByteArray());
				}catch(Throwable e){
					e.printStackTrace();
				}finally{
					try {
						is.close();
					} catch (Throwable e) {
						
					}
					changeTmp=null;
				}
			}
			InputStream tryOpen(String uri) throws IOException {
				Log.d("dbg", "tryOpen:" + uri);
				if (uri.startsWith("content://")) {
					return getContentResolver().openInputStream(Uri.parse(uri));
				} else if (uri.startsWith("/")) {
					return new FileInputStream(uri);
				} else {
					return URI.create(uri).toURL().openConnection().getInputStream();
				}
			}
		}.start();
	}
	@Override
	public void finish() {
		// Finish the activity with prompt
		// Reverse Positive and Negative because prevent finishing the activity by mistakes
		new AlertDialog.Builder(this)
			.setPositiveButton(android.R.string.no,new DialogInterface.OnClickListener(){public void onClick(DialogInterface di,int which){}})
			.setNegativeButton(android.R.string.yes,new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface di,int which){
					finishF();
				}
			})
			.setNeutralButton(R.string.saveAndFinish,new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface di,int which){
					finishS();
				}
			})
			.setMessage(R.string.askExit)
			.show();
	}
	public void finishF() {
		// Finish the activity without prompt
		setResult(RESULT_CANCELED);
		super.finish();
	}
	public void finishS() {
		// Upload changes and finish the activity
		Intent i=new Intent();
		try {
			PluginUtils.putDiffIntoIntent(data, i);
		} catch (IOException e) {}
		setResult(RESULT_OK,i);
		super.finish();
	}
}

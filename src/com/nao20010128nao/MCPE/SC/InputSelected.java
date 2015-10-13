package com.nao20010128nao.MCPE.SC;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import com.nao20010128nao.MC_PE.SkinChanger.CONVERTER.*;
import java.io.*;
import java.net.*;
import android.net.*;
import android.graphics.*;
import android.widget.*;
import android.util.*;

public class InputSelected extends SmartFindViewActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inputcheck);
		new AsyncTask<String,Void,FormatAndImage>(){
			public FormatAndImage doInBackground(String[]s){
				String from=s[0];
				byte[] file=null;
				InputStream is=null;
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				try {
					byte[]d=new byte[1000];
					is=tryOpen(from);
					while(true){
						int r=is.read(d);
						if(r<=0)
							break;
						baos.write(d,0,r);
					}
				} catch (IOException e) {
					return null;
				}finally{
					try {
						is.close();
					} catch (Throwable e) {
						
					}
					file=baos.toByteArray();
				}
				Log.d("check","file loaded");
				Log.d("check","loaded:"+file.length);
				Log.d("check","dump:"+dumpHead(file));
				FormatAndImage result=new FormatAndImage();
				if(Formats.PNG.isCorrectFormat(file)){
					result.format=Formats.PNG;
				}else if(Formats.WEBP.isCorrectFormat(file)){
					result.format=Formats.WEBP;
				}else if(Formats.JPEG.isCorrectFormat(file)){
					result.format=Formats.JPEG;
				}else if(Formats.GIF.isCorrectFormat(file)){
					result.format=Formats.GIF;
				}else if(from.endsWith(".tga")|from.endsWith(".tpic")){
					result.format=Formats.TGA;
				}
				result.bmp=result.format!=null?result.format.load(file):null;
				Log.d("check","going to main");
				return result;
			}
			public InputStream tryOpen(String uri) throws IOException {
				if (uri.startsWith("content://")) {
					return getContentResolver().openInputStream(Uri.parse(uri));
				} else if (uri.startsWith("/")) {
					return new FileInputStream(uri);
				} else {
					return URI.create(uri).toURL().openConnection().getInputStream();
				}
			}
			public void onPostExecute(FormatAndImage f){
				if(f.format==null){
					Toast.makeText(InputSelected.this,R.string.unknownType,Toast.LENGTH_LONG).show();
					return;
				}
				Default.wr.get().setInputExtension(f.format);
				Default.wr.get().cache.put("bitmap",f.bmp);
				
			}
			public String dumpHead(byte[] b){
				String s="";
				for(int i=0;i<=Math.min(20,b.length);i++){
					s+=" ";
					String d=Integer.toHexString(b[i]);
					if(d.length()==1){
						s+="0";
					}
					s+=d;
				}
				return s.substring(1);
			}
		}.execute(getIntent().getStringExtra("path"));
	}
	class FormatAndImage{
		public Formats format;
		public Bitmap bmp;
	}
}

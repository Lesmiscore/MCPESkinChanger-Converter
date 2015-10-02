package com.nao20010128nao.MCPE.SC;
import com.nao20010128nao.MCPE.SC.misc.*;
import android.os.*;
import com.nao20010128nao.MC_PE.SkinChanger.CONVERTER.*;
import java.io.*;
import java.net.*;
import android.net.*;
import android.graphics.*;

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
				Formats result=null;
				if(Formats.PNG.isCorrectFormat(file)){
					result=Formats.PNG;
				}else if(Formats.WEBP.isCorrectFormat(file)){
					result=Formats.WEBP;
				}else if(Formats.JPEG.isCorrectFormat(file)){
					result=Formats.JPEG;
				}else if(Formats.GIF.isCorrectFormat(file)){
					result=Formats.GIF;
				}else if(from.endsWith(".tga")|from.endsWith(".tpic")){
					result=Formats.TGA;
				}
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
			public void onPostExecute(Formats f){
				if(f==null){
					return;
				}
			}
		}.execute(getIntent().getStringExtra("path"));
		class FormatAndImage{
			public Formats format;
			public Bitmap bmp;
		}
	}
}

package com.nao20010128nao.MCPE.SC;
import android.graphics.*;
import java.io.*;
import android.os.*;
import net.zhuoweizhang.mercator.*;

public enum Formats {
	JPEG{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return data[0]==0xff&data[1]==0xd8;
		}
		@Override
		public boolean isDeprecated()
		{
			// TODO: Implement this method
			return true;
		}
		@Override
		public byte[] save(Bitmap bmp)
		{
			// TODO: Implement this method
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
			return baos.toByteArray();
		}
		@Override
		public Bitmap load(byte[] arr)
		{
			// TODO: Implement this method
			return null;
		}
		@Override
		public boolean isSupported()
		{
			// TODO: Implement this method
			return true;
		}
	},
	PNG{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]==0x89&
				data[1]=='P'&
				data[2]=='N'&
				data[3]=='G'&
				data[4]==0x0d&
				data[5]==0x0a&
				data[6]==0x1a&
				data[7]==0x0a;
		}
		@Override
		public boolean isDeprecated()
		{
			// TODO: Implement this method
			return false;
		}
		@Override
		public byte[] save(Bitmap bmp)
		{
			// TODO: Implement this method
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG,100,baos);
			return baos.toByteArray();
		}
		@Override
		public Bitmap load(byte[] arr)
		{
			// TODO: Implement this method
			return null;
		}
		@Override
		public boolean isSupported()
		{
			// TODO: Implement this method
			return true;
		}
	},
	GIF{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]=='G'&
				data[1]=='I'&
				data[2]=='F'&
				data[3]=='8'&
				(data[4]=='7'|data[4]=='9')&
				data[5]=='a';
		}
		@Override
		public boolean isDeprecated()
		{
			// TODO: Implement this method
			return true;
		}
		@Override
		public byte[] save(Bitmap bmp)
		{
			// TODO: Implement this method
			throw new UnsupportedOperationException("Android doesn't support to save a Bitmap as a GIF file.");
		}
		@Override
		public Bitmap load(byte[] arr)
		{
			// TODO: Implement this method
			return null;
		}
		@Override
		public boolean isSupported()
		{
			// TODO: Implement this method
			return false;
		}
	},
	WEBP{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]=='W'&
				data[1]=='E'&
				data[2]=='B'&
				data[3]=='P';
		}
		@Override
		public boolean isDeprecated()
		{
			// TODO: Implement this method
			return false;
		}
		@Override
		public byte[] save(Bitmap bmp)
		{
			// TODO: Implement this method
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.WEBP,100,baos);
			return baos.toByteArray();
		}
		@Override
		public Bitmap load(byte[] arr)
		{
			// TODO: Implement this method
			return null;
		}
		@Override
		public boolean isSupported()
		{
			// TODO: Implement this method
			return Build.VERSION.SDK_INT>=14;
		}
	},
	TGA{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return true;
		}
		@Override
		public boolean isDeprecated()
		{
			// TODO: Implement this method
			return false;
		}
		@Override
		public byte[] save(Bitmap bmp)
		{
			// TODO: Implement this method
			return null;
		}
		@Override
		public Bitmap load(byte[] arr)
		{
			// TODO: Implement this method
			FileOutputStream fos=null;
			try{
				fos=new FileOutputStream(RunOnceApplication.tmpFileA);
				fos.write(arr);
			}catch(Throwable e){
				return null;
			}finally{
				try{
					fos.close();
				}catch (Throwable e) {
					
				}
			}
			try{
				ConvertTGA.tgaToPng(RunOnceApplication.tmpFileA,RunOnceApplication.tmpFileB);
			}catch (IOException e) {
				
			}
			return BitmapFactory.decodeFile(RunOnceApplication.tmpFileB.toString());
		}
		@Override
		public boolean isSupported()
		{
			// TODO: Implement this method
			return true;
		}
	};
	public abstract boolean isCorrectFormat(byte[] data);
	public abstract boolean isDeprecated();
	public abstract byte[] save(Bitmap bmp);
	public abstract Bitmap load(byte[] arr);
	public abstract boolean isSupported();
}

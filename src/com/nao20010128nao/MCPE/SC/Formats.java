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
			return 
				data[0]==(byte)0xff&
				data[1]==(byte)0xd8;
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
		@Override
		public int internalValue()
		{
			// TODO: Implement this method
			return 0;
		}
	},
	PNG{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]==(byte)0x89&
				data[1]==(byte)'P'&
				data[2]==(byte)'N'&
				data[3]==(byte)'G'&
				data[4]==(byte)0x0d&
				data[5]==(byte)0x0a&
				data[6]==(byte)0x1a&
				data[7]==(byte)0x0a;
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
		@Override
		public int internalValue()
		{
			// TODO: Implement this method
			return 1;
		}
	},
	GIF{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]==(byte)'G'&
				data[1]==(byte)'I'&
				data[2]==(byte)'F'&
				data[3]==(byte)'8'&
				(data[4]==(byte)'7'|data[4]==(byte)'9')&
				data[5]==(byte)'a';
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
		@Override
		public int internalValue()
		{
			// TODO: Implement this method
			return 2;
		}
	},
	WEBP{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]==(byte)'W'&
				data[1]==(byte)'E'&
				data[2]==(byte)'B'&
				data[3]==(byte)'P';
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
		@Override
		public int internalValue()
		{
			// TODO: Implement this method
			return 3;
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
		@Override
		public int internalValue()
		{
			// TODO: Implement this method
			return 4;
		}
	};
	public abstract boolean isCorrectFormat(byte[] data);
	public abstract boolean isDeprecated();
	public abstract byte[] save(Bitmap bmp);
	public abstract Bitmap load(byte[] arr);
	public abstract boolean isSupported();
	public abstract int internalValue();
}

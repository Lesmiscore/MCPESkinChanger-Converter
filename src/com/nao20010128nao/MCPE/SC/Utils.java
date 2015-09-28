package com.nao20010128nao.MCPE.SC;
import android.content.*;
import android.content.pm.*;
import android.content.pm.PackageManager.*;
import android.content.res.*;
import android.util.*;
import com.nao20010128nao.MC_PE.SkinChanger.CONVERTER.*;
import java.util.*;

public class Utils
{
	public static int getVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        int versionCode = 0;
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        }catch(NameNotFoundException e){
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context){
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        }catch(NameNotFoundException e){
            e.printStackTrace();
        }
        return versionName;
	}
	public static String getRandomString() {
		StringBuilder sb=new StringBuilder("cache_");
		Random r=new Random();
		for (int i=0;i < 9;i++) {
			String append=String.format("%06x", r.nextInt() & 0xff).substring(4);
			sb.append(append);
		}
		Log.d("random", sb.toString());
		return sb.toString();
	}
}

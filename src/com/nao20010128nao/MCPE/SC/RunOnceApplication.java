package com.nao20010128nao.MCPE.SC;
import android.app.*;
import java.io.*;

public class RunOnceApplication extends Application {
	boolean isCheckedMCPE=false;
	public static File tmpFileA,tmpFileB;
	public static RunOnceApplication instance;
	@Override
	public void onCreate() {
		// TODO: Implement this method
		super.onCreate();
		instance = this;
		tmpFileA=new File(getCacheDir(),"a");
		tmpFileB=new File(getCacheDir(),"b");
	}
	public boolean isCheckedMCPE() {
		return isCheckedMCPE;
	}
	public void completeCheckMCPE() {
		isCheckedMCPE = true;
	}
}

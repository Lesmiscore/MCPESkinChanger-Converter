package pref;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class ImagePref extends StartPref{
	Handler h=new Handler();
	View v;
	public ImagePref(Context c,AttributeSet attrs){
		super(c,attrs);
	}

	@Override
	public View getView(View convertView, ViewGroup parent) {
		// TODO: Implement this method
		return fixImage(v=super.getView(convertView, parent));
	}

	View fixImage(View v){
		ImageView iv=(ImageView)v.findViewById(android.R.id.icon);
		iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return v;
	}
	
	@Override
	public void setImage(Bitmap bmp) {
		// TODO: Implement this method
		bmp=Bitmap.createScaledBitmap(bmp,bmp.getWidth()*20,bmp.getHeight()*20,true);
		final Drawable draw=new BitmapDrawable(bmp);
		Runnable r=new Runnable(){
			public void run(){
				setIcon(draw);
				if(v==null)
					return;
				ImageView iv=(ImageView)v.findViewById(android.R.id.icon);
				iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
			}
		};
		Context ctx=getContext();
		if(ctx instanceof Activity){
			Activity act=(Activity)ctx;
			act.runOnUiThread(r);
		}else{
			h.post(r);
		}
	}
}

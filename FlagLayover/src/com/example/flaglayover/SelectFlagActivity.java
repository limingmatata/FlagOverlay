package com.example.flaglayover;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;

import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.utilities.Global;
import com.example.utilities.LoadAdViewInEveryActivity;

public class SelectFlagActivity extends Activity implements OnClickListener {

	ImageView ivImage;
	public static String tempDir;
	private String uniqueId;
	File mypath;
	public String current = null;
	public String realpath = null;
	Uri o;
	
	LinearLayout u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectflag);
		
		u = (LinearLayout)findViewById(R.id.sd);
		u.setVisibility(View.GONE);
		
		Button btn_share = (Button)findViewById(R.id.btn_share);
		btn_share.setVisibility(View.GONE);
		
		findViewById(R.id.btn_selectFlag).setOnClickListener(this);
		findViewById(R.id.btn_share).setOnClickListener(this);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
		
		findViewById(R.id.btn_yes).setOnClickListener(this);
		findViewById(R.id.btn_no).setOnClickListener(this);
		findViewById(R.id.btn_save).setOnClickListener(this);
		
		ivImage = (ImageView) findViewById(R.id.img_photo);
		
		LinearLayout mAddMobsLinearLayout;
		mAddMobsLinearLayout = (LinearLayout) findViewById(R.id.add_mobs_lnrlyt);
		LoadAdViewInEveryActivity.loadAdds(this, mAddMobsLinearLayout);
		//mAddMobsLinearLayout.setOnClickListener(this);
		
		Intent intent = getIntent();
		int flagId = intent.getIntExtra("selected-item", 0);
		Bitmap bmp_flag = BitmapFactory.decodeResource(getResources(), flagId);
		
		Bitmap bmp_source = (Bitmap) intent.getParcelableExtra("Image");
		
		
		if (bmp_flag == null){
			
			if(bmp_source == null)
				ivImage.setImageBitmap(Global.bmpData);
			else 
			{
				Global.bmpData = bmp_source;
			
				ivImage.setImageBitmap(Global.bmpData);
			}
			
		}else{
			btn_share.setVisibility(View.VISIBLE);
			
			Bitmap bmp1 = Global.bmpData;
			Bitmap bmp2 = bmp_flag;
			   //int maxWidth = (bmp1.getWidth() > bmp2.getWidth() ? bmp1.getWidth() : bmp2.getWidth());
			   //int maxHeight = (bmp1.getHeight() > bmp2.getHeight() ? bmp1.getHeight() : bmp2.getHeight());
			
			   int maxWidth = bmp1.getWidth();
			   int maxHeight  = bmp1.getHeight();
			   Config config = bmp1.getConfig();
			   if(config == null){
			    config = Bitmap.Config.ARGB_8888;
			   }
			   Bitmap bmOverlay = Bitmap.createBitmap(maxWidth, maxHeight, config);
			   Canvas canvas = new Canvas(bmOverlay);
			   
			   Paint paint = new Paint();
			   paint.setAlpha(100);
			   
			   Rect src = new Rect(0,0,maxWidth,maxHeight);
			   Rect dst = new Rect(0,0,maxWidth,maxHeight);
			   canvas.drawBitmap(bmp1, null, dst, null);
			   canvas.drawBitmap(bmp2,null,dst, paint);
			   
			   ivImage.setImageBitmap(bmOverlay);
			   Global.bmpData = bmOverlay;
		       
			   save(bmOverlay);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_share:
			
			shareImage();
			//u.setVisibility(View.VISIBLE);
			break;
			
		case R.id.btn_selectFlag:
			Intent intent = new Intent(this,ListFlagActivity.class);
			startActivity(intent);
			break;
			
		case R.id.btn_cancel:
			Intent intent1 = new Intent(this,MainActivity.class);
			startActivity(intent1);
			break;
			
		case R.id.btn_yes:
			save(Global.bmpData);
			shareImage();
			//share("facebook");
			u.setVisibility(View.GONE);
			break;	
			
		case R.id.btn_no:
			save(Global.bmpData);
			shareImage();
			//share("twitter");
			u.setVisibility(View.GONE);
			break;	
			
		case R.id.btn_save:
			save(Global.bmpData);
			u.setVisibility(View.GONE);
			break;
			
		}
	}
	public void save(Bitmap result) 
    {
       
		tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
 
        prepareDirectory();
      
  		       
        uniqueId = getTodaysDate() + "_" + getCurrentTime();
        Log.d("matata","uniqueid"+uniqueId);
        current = uniqueId + ".png";
        mypath= new File(directory,current);
        
        realpath = tempDir + current;
        String f = mypath.toString();
        Log.d("matata","filepath"+f);
        
        try
        {
            FileOutputStream mFileOutStream = new FileOutputStream(mypath);

            result.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream); 
            mFileOutStream.flush();
            mFileOutStream.close();
            String url = Images.Media.insertImage(getContentResolver(), result, "title", null);
            o = Uri.parse(url);
            Log.v("matata","url: " + url);
            
        }
        catch(Exception e) 
        { 
            Log.v("log_tag", e.toString()); 
        } 
    }
	
	private void shareImage() {
		try{
			
        Intent share = new Intent(Intent.ACTION_SEND);

        share.setType("image/*");
 
        //Uri uri = Uri.fromFile(mypath);
        
        share.putExtra(Intent.EXTRA_STREAM, o);
        
        startActivity(Intent.createChooser(share, "Share Image!"));

		}catch (final ActivityNotFoundException e) {
	        Toast.makeText(this, "You don't seem to have twitter installed on this device", Toast.LENGTH_SHORT).show();
	    }  
        
    }
	
		
	void share(String nameApp ) {
		  try
		  {
		      List<Intent> targetedShareIntents = new ArrayList<Intent>();
		      Intent share = new Intent(android.content.Intent.ACTION_SEND);
		      share.setType("image/*");
		      List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
		      if (!resInfo.isEmpty()){
		          for (ResolveInfo info : resInfo) {
		              Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
		              targetedShare.setType("image/jpeg"); // put here your mime type
		              if (info.activityInfo.packageName.toLowerCase().contains(nameApp) || info.activityInfo.name.toLowerCase().contains(nameApp)) {
		                  targetedShare.putExtra(Intent.EXTRA_SUBJECT, "Sample Photo");
		                  //targetedShare.putExtra(Intent.EXTRA_TEXT,"This photo is created by App Name");
		                  targetedShare.putExtra(Intent.EXTRA_STREAM, o );
		                  targetedShare.setPackage(info.activityInfo.packageName);
		                  targetedShareIntents.add(targetedShare);
		              }
		          }
		          Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
		          chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
		          startActivity(chooserIntent);
		      }
		  }
		  catch(Exception e){
		      Log.v("VM","Exception while sending image on" + nameApp + " "+  e.getMessage());
		  }
		}
	
	
	
	private boolean prepareDirectory() 
    {
        try
        {
            if (makedirs()) 
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", 1000).show();
            return false;
        }
    }
	private boolean makedirs() 
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();
 
        if (tempdir.isDirectory()) 
        {
            File[] files = tempdir.listFiles();
            for (File file : files) 
            {
                if (!file.delete()) 
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }
	private String getTodaysDate() { 
		 
        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) + 
        ((c.get(Calendar.MONTH) + 1) * 100) + 
        (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:",String.valueOf(todaysDate));
        return(String.valueOf(todaysDate));
 
    }
 
    private String getCurrentTime() {
 
        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) + 
        (c.get(Calendar.MINUTE) * 100) + 
        (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));
 
    }

}

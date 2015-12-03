package com.example.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.example.flaglayover.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {

	   String [] result;
	   String [] description;
	   Context context;
	   int [] imageId;
	   
	   List<Integer> ints;
	   
	   private static LayoutInflater inflater=null;
	   
	    public CustomAdapter(Context mainActivity, String[] prgmNameList, List<Integer> prgmImages,String[] descriptionList ){
	        // TODO Auto-generated constructor stub
	        result = prgmNameList;
	        context = mainActivity;
	        //imageId = prgmImages;
	        ints = prgmImages;
	        description = descriptionList;
	        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return result.length;
	    }

	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }

	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }

	    public class Holder
	    {
	        TextView tv;
	        ImageView img;
	        TextView des;
	    }
	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	        Holder holder=new Holder();
	        View rowView = null;  
	        
	        if(convertView == null)
	        {
	        	rowView = inflater.inflate(R.layout.listitem, null);
	        }else{
	        	rowView = convertView;
	        }
	        
        	holder.tv= (TextView) rowView.findViewById(R.id.textView1);
        	holder.img= (ImageView) rowView.findViewById(R.id.imageView1); 
        	holder.tv.setText(result[position]);
        	// holder.img.setImageResource(imageId[position]);
        	holder.img.setImageResource(ints.get(position));
	        
	        
	        return rowView;
	    }
	    
	    /*public static Bitmap convertBitmap(String path)   {

	        Bitmap bitmap=null;
	        BitmapFactory.Options bfOptions=new BitmapFactory.Options();
	        bfOptions.inDither=false;                     //Disable Dithering mode
	        bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
	        bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
	        bfOptions.inTempStorage=new byte[32 * 1024]; 

	        
	        File file=new File(path);
	        FileInputStream fs=null;
	        try {
	            fs = new FileInputStream(file);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }

	        try {
	            if(fs!=null)
	            {
	                bitmap=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
	            }
	            } catch (IOException e) {

	            e.printStackTrace();
	        } finally{ 
	            if(fs!=null) {
	                try {
	                    fs.close();
	                } catch (IOException e) {

	                    e.printStackTrace();
	                }
	            }
	        }

	        return bitmap;
	    }*/
}

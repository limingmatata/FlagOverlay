package com.example.flaglayover;

import java.util.ArrayList;
import java.util.List;

import com.example.utilities.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class ListFlagActivity extends Activity implements OnClickListener{

	ListView lv;
	Context context;
	ArrayList prgmName;
	ImageView background;
	
	public static final String MyPREFERENCES = "Select folder" ;
	public static final String Sel_folder = "sel_folder";
		
	List<Integer> ints;
	public static int [] prgmImages;
	
    public static String [] prgmNameList={"Algeria","Australia","Austria","Bahrain","Bangladesh",
    									"Belgium","Brazil","Bulgaria","Canada","China",
    									"Croatia","Czech Republic","Denmark","Egypt","Finland",
    									"France","Georgia","Germany","Greece","Hungry",
    									"India","Indonesia","Iran","Iraq","Ireland"
    									};
    
    
    
    
	public static String [] descriptionList = {"1 Template","10 Templates"};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_listflag);
			
		findViewById(R.id.btn_cancel).setOnClickListener(this);
		
		ints = new ArrayList<Integer>();
		for (int i =3 ;i < 28; i++)
	    {
	    	String uri = "@drawable/a"+ i;  // where myresource.png is the file
	        // extension removed from the String

	    	int imageResource = getResources().getIdentifier(uri, null, getPackageName());
	    	Log.d("matata","asdf"+imageResource);
	    	ints.add(imageResource);
	    }
		
		// load listview
		context = this;
		lv = (ListView) findViewById(R.id.listView);
		lv.setAdapter(new CustomAdapter(this,prgmNameList,ints,descriptionList));
		lv.setOnItemClickListener(new ListClickHandler());
				
	}
	@Override
	    protected void onResume() {
	        super.onResume();
	        
	    }
    
	 @Override
	  	public void onBackPressed() {
	  		super.onBackPressed();
	  		Log.d("matata","matata===List======Backpress=================");
	  		//System.exit(1);
	  		//this.finish(); 
	  		
	  	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent1 = new Intent(this,SelectFlagActivity.class);
		startActivity(intent1);
	}
	public class ListClickHandler implements OnItemClickListener{
		 
	    @Override
	    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
	        // TODO Auto-generated method stub
	    	Log.d("matata","position"+position);
	    	
	    		    	
	    	int selectedFlag = ints.get(position); 
	        	    	
	        Intent intent = new Intent(ListFlagActivity.this, SelectFlagActivity.class);
	        intent.putExtra("selected-item", selectedFlag);
	        startActivity(intent); 
	         
	    }
	}
}

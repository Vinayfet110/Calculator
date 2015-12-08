package com.example.calculator;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultHistoryActivity extends Activity {

	private ListView resultList;
	
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	 
	        // hide the window title.
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        // hide the status bar and other OS-level chrome
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.result_history);
	       
	        
	        resultList=(ListView) findViewById(R.id.resultlist);
	        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.layout_list_view,MainActivity.resultdb);
	        
	        
	        
	        resultList.setAdapter(adapter);
	 }
}

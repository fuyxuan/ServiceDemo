package com.example.servicedemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	
	Button btn_start_service,btn_close_service,btn_bind_service,btn_unbind_service;
	private MyService.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findView();
        setAction();
    }

    private void findView(){
    	btn_start_service = (Button)findViewById(R.id.btn_start_service);
    	btn_close_service = (Button)findViewById(R.id.btn_close_service);
    	btn_bind_service = (Button)findViewById(R.id.btn_bind_service);
    	btn_unbind_service = (Button)findViewById(R.id.btn_unbind_service);
    }
    
    
    private void setAction(){
    	btn_start_service.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("msg","MainActivity");
				Intent startIntent = new Intent(MainActivity.this , MyService.class);
				startService(startIntent);
			}
		});
    	
    	btn_close_service.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent stopIntent = new Intent(MainActivity.this, MyService.class);
				stopService(stopIntent);
				
			}
		});
    	
    	btn_bind_service.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent bindIntent = new Intent(MainActivity.this, MyService.class);  
//				Log.i("msg","connection");
	            bindService(bindIntent, connection, BIND_AUTO_CREATE); 				
			}
		});
    	
    	btn_unbind_service.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				unbindService(connection);
			}
		});
    	
    }
    
    private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myBinder = ((MyService.MyBinder) service);  
            myBinder.startTODO();  
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}  
    	  
        
    };  
}

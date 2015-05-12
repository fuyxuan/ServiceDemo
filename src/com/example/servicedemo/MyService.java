package com.example.servicedemo;

import android.R.drawable;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	public static final String TAG = "msg";
	private MyBinder myBinder=new MyBinder();

	@Override
	public void onCreate() {
		super.onCreate();
		
		
		NotificationManager nm = (NotificationManager)getSystemService(this.NOTIFICATION_SERVICE);//獲取通知管理器
		Notification n = new Notification(R.drawable.ic_launcher, "訊息顯示", System.currentTimeMillis());//建立一個通知欄
		n.flags |= Notification.FLAG_AUTO_CANCEL;//點擊後可以自動清除
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//點擊後intent MainActivity。FLAG_CANCEL_CURRENT如果該PendingIntent已存在，就的刪除另產生新的PendingIntent
		n.setLatestEventInfo(this, "通知欄的Title", "通知欄文字內容", pi);
		startForeground(1, n);  //啟動service前景
		//nm.notify(0, n);
		Log.d(TAG, "onCreate() ");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.i("msg","onStartCommand");
				for(int i=0;i<50;i++){
					try {
						Thread.sleep(100);
						Log.i("msg","i>>> "+i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy()");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("msg", "onUnBind");
		return super.onUnbind(intent);
	}
	
	class MyBinder extends Binder {
		public void startTODO() {
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 30; i++) {
						try {
							Thread.sleep(100);
							Log.i("msg","TODO:"+i);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}).start();
		}

	}


	

}
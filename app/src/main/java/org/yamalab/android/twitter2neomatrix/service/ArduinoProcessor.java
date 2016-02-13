package org.yamalab.android.twitter2neomatrix.service;

import java.util.Date;


import android.util.Log;

public class ArduinoProcessor {
   private static final String TAG = "AdkService";
   private AdkService adkService;
   private long lastTime;
   private long minimumHoldTime=100000; 
     // ????????????????(msec)?Btwitter ?????????Btweet ???P????1000???????B?P??????42???????B
   private boolean led_on=false;
   public ArduinoProcessor(AdkService s){
	   adkService=s;
   }
   public void setAdkService(AdkService as){
	   adkService=as;
   }

   public void processDigitalInput(int vals){
	//	Log.d(TAG,"processDigitalInput-"+vals);
	   
   }
   public void processAnalogInput(int port, int val){
	//	Log.d(TAG,"processAnalogInput-port="+port+" val="+val);
	   if(port==0){
		   if(val<=10){
			   if(adkService==null) return;
			   if(!led_on){
				   Date date = new Date();
				   long timeNow=date.getTime();
				   if(timeNow-lastTime>minimumHoldTime){
				      this.adkService.parseSetCommand("out-d-8=1");
			          this.adkService.sendCommandToTwitter("tweet", "LED ON at "+date.toString());
			          led_on=true;
			          lastTime=timeNow;
				   }
			   }
		   }
		   else{
			   if(adkService==null) return;
			   if(led_on){
				   Date date=new Date();
				   long timeNow=date.getTime();
				   if(timeNow-lastTime>minimumHoldTime){
			          this.adkService.parseSetCommand("out-d-8=0");
//			          this.adkService.sendCommandToActivity("twitter tweet", "LED OFF at "+date.toString());
			          this.adkService.sendCommandToTwitter("tweet", "LED OFF at "+date.toString());
			          led_on=false;
			          lastTime=timeNow;
			       }
			   }
		   }
	   }
   }
   public void wait(int t){
	   try{
		   Thread.sleep((long)t);
	   }
	   catch(InterruptedException e){
		   
	   }
   }
}
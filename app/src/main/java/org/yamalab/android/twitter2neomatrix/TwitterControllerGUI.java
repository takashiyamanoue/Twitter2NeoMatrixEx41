package org.yamalab.android.twitter2neomatrix;

import org.yamalab.android.twitter2neomatrix.service.StringMsg;
import org.yamalab.android.twitter2neomatrix.service.Util;
import org.yamalab.android.twitter2neomatrix.R;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TwitterControllerGUI extends AccessoryController
implements OnClickListener  {
	static final String TAG = "TwitterControllerGUI";
			
	private AdkTwitterActivity activity;
	private Button loginbutton;
	private Button logoutbutton;
	public RequestToken requestToken = null;
	public Twitter twitter = null;
//	public OAuthAuthorization twitterOauth;
	private boolean accessingWeb=false;
    /** Called when the activity is first created. */
	private EditText mLoginResultText;
	public String CALLBACK_URL = "http://www.yama-lab.org" ; // "myapp://oauth";
    
    TwitterControllerGUI(AdkTwitterActivity hostActivity) {
        super(hostActivity);
		Log.d(TAG,"TwitterController");
        activity=hostActivity;
//        mTwitterController=activity.mTwitterController;
        //????????????????????????????????????????????????????????????
	    SharedPreferences pref = activity.getSharedPreferences("Twitter_setting", activity.MODE_PRIVATE);
	    //?????????????????????K???v??????????????????????????????????????????????????????

		loginbutton = (Button) findViewById(R.id.tweetlogin_button);
        loginbutton.setOnClickListener(this);
        logoutbutton = (Button) findViewById(R.id.logout_button);
        logoutbutton.setOnClickListener(this);
        mLoginResultText=(EditText)findViewById(R.id.loginResultTextArea);
      
    }

	private class connectTwitterTask extends AsyncTask<Void, Void, String> {
		 String resultURL;
		 @Override
	     protected String doInBackground(Void... params ) {
	    	Log.d(TAG, "connectTwitterTask.doInBackground - " );
	    	try{
//	    	   connectTwitter();
	    		sendMessageToService("twitter","connect");
	    	}
	    	catch(Exception e){
//	    		setAccessingWeb(false);
	    		activity.sendCommandToService("twitter set accessingweb","false");
	    		Log.d(TAG,"tweetTask error:"+e.toString());
				e.printStackTrace();
				return "";
			}
//    		setAccessingWeb(false);
    		activity.sendCommandToService("twitter set accessingweb","false");
    		String rtn=requestToken.getAuthorizationURL();
	    	return rtn;
	     }
	     @Override
	     protected void onPostExecute(String result) {
	         super.onPostExecute(result);
	         resultURL=result;
	         if (result != null) {
	             ((Activity)mHostActivity).runOnUiThread(new Runnable() {
	             	@Override
	             	public void run(){
	     		       activity.showTabContents(R.id.main_login_label);
	   	     		   activity.mTwitterLoginController.loadUrl(resultURL);
	             	}
	             });

	         } else {
	                Log.d(TAG,"");
	         }
	    }
	}
	
    final private boolean isConnected(String nechatterStatus){
		if(nechatterStatus != null && nechatterStatus.equals("available")){
			return true;
		}else{
			return false;
		}
	}
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//???????????????????????????????????????????????????????????????H
	  if(v==loginbutton){
		  activity.sendCommandToService("twitter login", "");
	  }
	  else
	  if(v==logoutbutton){
//  		disconnectTwitter();
		try {
//			connectTwitter();
//			new connectTwitterTask().execute();
			activity.sendCommandToService("twitter logout", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
		
	}		

	@Override
	protected void onAccesssoryAttached() {
		// TODO Auto-generated method stub
		
	}

	String loadingURL;
	public boolean parseCommand(String x, StringMsg m){
		String subcmd=Util.skipSpace(x);
		String [] rest=new String[1];
		String [] match=new String[1];
	   	if(this.activity==null) return false;
	   	if(this.activity.mTweet==null) return false;
		Log.d(TAG,"parseCommand("+x+","+m.getValue()+")");
		int [] intv = new int[1];
		if(m==null) return false;
		String v=m.getValue();
		if(Util.parseKeyWord(subcmd,"tweet",rest)){
		   	String subsub=Util.skipSpace(rest[0]);
		   	 activity.mTweet.tweet(v);
		   	return true;
		}
		else
		if(Util.parseKeyWord(subcmd,"set ",rest)){
		   	String subsub=Util.skipSpace(rest[0]);
		   	if(subsub.equals("resultMessage")){
		   		activity.mTweet.setHashTweet(v);
		   	}
		   	else
		   	if(subsub.equals("loginMessage")){
		   		mLoginResultText.setText(v);
		   	}
		   	else
		   	if(subsub.equals("uploadHashTag")){
		   		activity.mTweet.setUploadHashTag(v);
		   	}
		   	else
		   	if(subsub.equals("downloadHashTag")){
		   		activity.mTweet.setDownloadHashTag(v);
		   	}
		}
		else
		if(Util.parseKeyWord(subcmd,"loadUrl",rest)){
		   	String subsub=Util.skipSpace(rest[0]);
		   	loadingURL=v;
            ((Activity)mHostActivity).runOnUiThread(new Runnable() {
             	@Override
             	public void run(){
     		       activity.showTabContents(R.id.main_login_label);
   	     		   activity.mTwitterLoginController.loadUrl(loadingURL);
             	}
             });
		   	
		}		
		else
		if(Util.parseKeyWord(subcmd,"show ",rest)){
		   	String subsub=Util.skipSpace(rest[0]);
		   	if(subsub.equals("label")){
		   		if(v.equals("main_tweet_label")){
		             ((Activity)mHostActivity).runOnUiThread(new Runnable() {
			             	@Override
			             	public void run(){
								activity.showTabContents(R.id.main_tweet_label);		   			
			             	}
			             });
		   		}
//		   		activity.mTweet.setHashTweet(v);
		   	}
		}

		return false;
	}
	public void sendMessageToService(String cmd, String val){
		this.activity.sendCommandToService(cmd, val);
	}

}

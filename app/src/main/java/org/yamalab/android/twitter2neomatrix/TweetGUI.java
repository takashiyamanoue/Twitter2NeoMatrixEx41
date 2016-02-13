
package org.yamalab.android.twitter2neomatrix;

import java.util.List;
import java.util.Properties;

import org.yamalab.android.twitter2neomatrix.R;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;


public class TweetGUI extends AccessoryController
implements OnClickListener, OnCheckedChangeListener
{
	static final String TAG = "Tweet";
	
	private String hashTag = null;
	private String uploadTag=null;
	private String mainTEXT = null;
	private Button mTweetButton;
	private Button mQueryButton;
	private Button mUploadHashTagSetButton;
	private Button mDownloadHashTagSetButton;
	private EditText edit;
	private EditText mUpLoadHashTagField;
	private EditText mDownloadHashTagField;
	private EditText mResultField;
	private TwitterControllerGUI mTwitterController;
	private CheckBox mLocationSendingCheckBox;
	private AdkTwitterActivity activity;
	private ArrayAdapter sendIntervals;
	private Spinner mSendIntervalSpinner;
	public Properties setting;
	
	TweetGUI(AdkTwitterActivity hostActivity, TwitterControllerGUI tc) {
		super(hostActivity);
		Log.d(TAG,"Twitter");
		activity=hostActivity;
//        setContentView(R.layout.tweet);
		mTwitterController=tc;
        
        //?n?b?V???^?O?????I
        hashTag = " #testTwitter";
        uploadTag = "#testTwitterUp" ;
        
        edit = (EditText) findViewById(R.id.edittext1);
        edit.setText(hashTag);
        edit.setMaxLines(3); 
        edit.setSelection(0);
		sendIntervals = ArrayAdapter.createFromResource(activity, R.array.sendingIntervalItems,
				android.R.layout.simple_spinner_item);

		mTweetButton = (Button) findViewById(R.id.tweet_tweet);
        mTweetButton.setOnClickListener(this);
        mUploadHashTagSetButton = (Button) findViewById(R.id.setUploadHashButton);
        mUploadHashTagSetButton.setOnClickListener(this);
        mDownloadHashTagSetButton = (Button) findViewById(R.id.setDownloadHashButton);
        mDownloadHashTagSetButton.setOnClickListener(this);

        mUpLoadHashTagField = (EditText) findViewById(R.id.uploadHashText);
        mDownloadHashTagField = (EditText) findViewById(R.id.queryText);
    	mResultField = (EditText) findViewById(R.id.answerTextArea);   
    	mQueryButton = (Button) findViewById(R.id.queryButton);
    	mLocationSendingCheckBox = (CheckBox) findViewById(R.id.locationSendCheckbox);
    	mLocationSendingCheckBox.setOnCheckedChangeListener(this);
        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mUpLoadHashTagField.setText(uploadTag);
				mUpLoadHashTagField.setSelection(0);
			}
		});

        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
        	@Override
        	public void run(){
                mDownloadHashTagField.setText(hashTag); 
                mDownloadHashTagField.setSelection(0);
        	}
        });

		sendIntervals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSendIntervalSpinner = (Spinner) findViewById(R.id.sendIntervalSpinner);
		mSendIntervalSpinner.setAdapter(sendIntervals);
		mSendIntervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Spinner spinner = (Spinner) parent;
				activity.sendCommandToService("service set sendLocInterval", (spinner.getSelectedItem()).toString());
//				showToast(Integer.toString(spinner.getSelectedItemPosition()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		int spinnerPosition = sendIntervals.getPosition("5-min");
		mSendIntervalSpinner.setSelection(spinnerPosition);
		activity.sendCommandToService("service set sendLocInterval", (mSendIntervalSpinner.getSelectedItem()).toString());
	}
		
	public void tweet() throws TwitterException {
		// ?L?^?????????????t?@?C?????????????B
		Log.d(TAG,"tweet");
	}

	String xwork;
	String writeHash="";
    public void tweet(String x){
    	xwork=x;
    	writeHash=mUpLoadHashTagField.getText().toString();
        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
        	@Override
        	public void run(){
                edit.setText(xwork+" "+writeHash); 
                edit.setSelection(0);
        	}
        });
//		new tweetTask().execute(x);
    }

	public void getHashTweet(){
		String x=mDownloadHashTagField.getText().toString();
		hashTag=x;
//		new getHashTweetTask().execute(x);
	}	
	public void getHashTweet(String x){
		hashTag=x;
        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
        	@Override
        	public void run(){
                mDownloadHashTagField.setText(hashTag); 
                mDownloadHashTagField.setSelection(0);
        	}
        });
//		new getHashTweetTask().execute(x);
	}
	public void setHashTweet(String x){
		xwork=x;
		this.setting.setProperty("xwork",x);
		mHostActivity.saveProperties(this.setting);
        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
        	@Override
        	public void run(){
                mResultField.setText(xwork); 
                mResultField.setSelection(0);
        	}
        });
//		new getHashTweetTask().execute(x);
	}
	public void setUploadHashTag(String x){
		uploadTag=x;
		this.setting.setProperty("uploadTag",uploadTag);
		mHostActivity.saveProperties(this.setting);
        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
        	@Override
        	public void run(){
        		mUpLoadHashTagField.setText(uploadTag);
                mUpLoadHashTagField.setSelection(0);
        	}
        });
//		new getHashTweetTask().execute(x);
	}
	public void setDownloadHashTag(String x){
		hashTag=x;
		this.setting.setProperty("hashTag",hashTag);
		mHostActivity.saveProperties(this.setting);
        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
        	@Override
        	public void run(){
        		mDownloadHashTagField.setText(hashTag);
                mDownloadHashTagField.setSelection(0);
        	}
        });
//		new getHashTweetTask().execute(x);
	}
	public void setSetting(Properties s){
		Log.d(TAG,"TweetGUI-setSetting");
		if(s==null){
			this.setting=new Properties();
		}
		else{
			this.setting=s;
		}
		if(setting!=null){
			Log.d(TAG,"TweetGUI-setSetting setting!=null");
			hashTag=setting.getProperty("hashTag");
			if(hashTag!=null){
//				this.sendCommandToActivity("connector setUrl-", url);
			}
			else{
//				this.setting.put("managerUrl", ""+this.urlArea.getText());
				hashTag="#Twitter2LedDown";
			}
			this.mDownloadHashTagField.setText(hashTag);
	        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
	        	@Override
	        	public void run(){
	                mDownloadHashTagField.setText(hashTag); 
	                mDownloadHashTagField.setSelection(0);
	        	}
	        });
//			activity.sendCommandToService("twitter set downloadHashTag",hashTag);
			/* */
	        uploadTag=setting.getProperty("uploadTag");
			if(uploadTag!=null){
//				this.sendCommandToActivity("connector setUrl-", url);
			}
			else{
//				this.setting.put("managerUrl", ""+this.urlArea.getText());
				uploadTag="#Twitter2LedUp";
			}
	        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
	        	@Override
	        	public void run(){
	                mUpLoadHashTagField.setText(uploadTag);
					mUpLoadHashTagField.setSelection(0);
	        	}
	        });
//			activity.sendCommandToService("twitter set uploadHashTag",uploadTag);
/*
			if(debugger!=null){
	    	    debugger.setSetting(setting);
			}
			*/
		}
	}
	
	@Override
	protected void onAccesssoryAttached() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void onClick(View v) {
            // ?N???b?N????????
		if(v==mTweetButton){
        	SpannableStringBuilder sb = (SpannableStringBuilder)edit.getText();
            mainTEXT = sb.toString();  
            activity.sendCommandToService("twitter tweet", mainTEXT);
		}
		else
		if(v==mUploadHashTagSetButton){
//        	SpannableStringBuilder sb = (SpannableStringBuilder)edit.getText();
//            hashTag = sb.toString();                        
			activity.sendCommandToService("twitter set uploadHashTag", (mUpLoadHashTagField.getText()).toString());
		}
		else
		if(v==mDownloadHashTagSetButton){
//        	SpannableStringBuilder sb = (SpannableStringBuilder)edit.getText();
//            hashTag = sb.toString();                        
			activity.sendCommandToService("twitter set downloadHashTag",(mDownloadHashTagField.getText()).toString());
		}

    }
	public String getUploadHashTag(){
    	return mUpLoadHashTagField.getText().toString();
	}


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(this.mLocationSendingCheckBox.isChecked()){
			activity.sendCommandToService("service set locationSendingEnabled","true");			
		}
		else{
			activity.sendCommandToService("service set locationSendingEnabled","false");			
		}
	}

}


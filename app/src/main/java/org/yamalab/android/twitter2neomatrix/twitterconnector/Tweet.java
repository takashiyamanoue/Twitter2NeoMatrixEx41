
package org.yamalab.android.twitter2neomatrix.twitterconnector;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Tweet
{
	static final String TAG = "Tweet";
	
	private String hashTag = null;
	private String uploadTag=null;
	private String mainTEXT = null;
	private Button mTweetButton;
	private Button mQueryButton;
	private EditText edit;
	private EditText mUpLoadHashTagField;
	private String mDownloadHashTag;
	private EditText mResultField;
	private TwitterController mTwitterController;
	private TwitterApplication service;
	private Context context;
	
	public Tweet(TwitterController tc) {
		Log.d(TAG,"Twitter");
//        setContentView(R.layout.tweet);
		mTwitterController=tc;
		service=tc.getService();
        
        //?n?b?V???^?O?????I
        uploadTag = "#up787" ;
                    	    	
		hashTag="#787-2015";
		this.service=tc.getService();
		this.context=tc.getContext();
		service.parseCommand("adk twitter set uploadHashTag", uploadTag);
		service.parseCommand("adk twitter set downloadHashTag",hashTag);
    	
	}
	
	public void setDownloadHashTag(String x){
		this.hashTag=x;
	}
	public void setUploadHashTag(String x){
		this.uploadTag=x;
	}
	
	public void tweet() throws TwitterException {
		// ?L?^?????????????t?@?C?????????????B
		Log.d(TAG,"tweet");

		SharedPreferences pref = context.getSharedPreferences("Twitter_setting",context.MODE_PRIVATE);

		// ?????t?@?C??????oauth_token??oauth_token_secret???????B
		String oauthToken  = pref.getString("oauth_token", "");
		String oauthTokenSecret = pref.getString("oauth_token_secret", "");

		ConfigurationBuilder confbuilder  = new ConfigurationBuilder(); 

		confbuilder.setOAuthAccessToken(oauthToken) 
		.setOAuthAccessTokenSecret(oauthTokenSecret) 
		.setOAuthConsumerKey(mTwitterController.CONSUMER_KEY) 
		.setOAuthConsumerSecret(mTwitterController.CONSUMER_SECRET); 

		Twitter twitter=null;
		try{
		 twitter= new TwitterFactory(confbuilder.build()).getInstance(); 
		}
		catch(Exception e){
			Log.d(TAG,"tweet "+e.toString());
			e.printStackTrace();
			
		}

		// ?C???????????????????????A?c?C?[?g?B
		if(twitter!=null)
		twitter.updateStatus(mainTEXT);
		
//		Toast.makeText(this, "??????????????", Toast.LENGTH_SHORT).show();
	}

	String xwork;
	String writeHash="";
    public void tweet(String x){
    	xwork=x;
    	/*
    	writeHash=mUpLoadHashTagField.getText().toString();
        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
        	@Override
        	public void run(){
                edit.setText(xwork+" "+writeHash); 
                edit.setSelection(0);
        	}
        });
        */
		new tweetTask().execute(x);
    }
	private class tweetTask extends AsyncTask<String, Integer, Long> {
	     protected synchronized Long doInBackground(String... params ) {
	    	Log.d(TAG, "doInBackground - " + params[0]);
	    	mainTEXT=params[0];
	    	mTwitterController.setAccessingWeb(true);
//        	SpannableStringBuilder sb = (SpannableStringBuilder)edit.getText();
//            mainTEXT = sb.toString();
	    	try{
		   	  tweet();
	    	}
	    	catch(Exception e){
	    		Log.d(TAG,"tweetTask error:"+e.toString());
				e.printStackTrace();
	    	}
	    	mTwitterController.setAccessingWeb(false);	    	
		   	return 1L;	
		 }

	}
	public void getHashTweet(){
		new getHashTweetTask().execute(hashTag);
	}	
	public void getHashTweet(String x){
		hashTag=x;
		new getHashTweetTask().execute(x);
	}
	private class getHashTweetTask extends AsyncTask<String, Integer, Long> {
	     String answer;
	     String answerTime;
	     String answerWriter;
	     protected synchronized Long doInBackground(String... params ) {
	    	Log.d(TAG, "doInBackground - getHashTweetTask " + params[0]);
	    	mTwitterController.setAccessingWeb(true);
	    	
			// ?????t?@?C??????oauth_token??oauth_token_secret???????B
			SharedPreferences pref = context.getSharedPreferences("Twitter_setting",context.MODE_PRIVATE);
			String oauthToken  = pref.getString("oauth_token", "");
			String oauthTokenSecret = pref.getString("oauth_token_secret", "");

			ConfigurationBuilder confbuilder  = new ConfigurationBuilder(); 

			confbuilder.setOAuthAccessToken(oauthToken) 
			.setOAuthAccessTokenSecret(oauthTokenSecret) 
			.setOAuthConsumerKey(mTwitterController.CONSUMER_KEY) 
			.setOAuthConsumerSecret(mTwitterController.CONSUMER_SECRET); 
			Twitter twitter=null;
			try{
			 twitter= new TwitterFactory(confbuilder.build()).getInstance(); 
			}
			catch(Exception e){
				Log.d(TAG,"tweet "+e.toString());
				e.printStackTrace();
				
			}
	    	try{
	    		Query query = new Query(hashTag);
//	    		query.since("2009-10-25");
//	    		query.until("2010-10-25");
	    		query.setResultType(query.RECENT);
	    		query.setCount(1);
//	    		query.setRpp(50);
	    		QueryResult result = twitter.search(query);
	    		List<twitter4j.Status> twitterSearches = result.getTweets();
	    		for (twitter4j.Status tweet : twitterSearches) {
	    		    answer=tweet.getText();
	    		    answerTime=tweet.getCreatedAt().toString();
	    		    answerWriter=tweet.getUser().getName();
	    		    /*
	    	        ((Activity)mHostActivity).runOnUiThread(new Runnable() {
	    	        	@Override
	    	        	public void run(){
	    		    		mResultField.setText("");
	    	    		    mResultField.append( answerTime+ ":" +answerWriter + ":" + answer);
	    	                mResultField.setSelection(0);
	    	        	}
	    	        });
	    		    */
//	    		    service.sendCommandToActivity("twitter set resultMessage", 
	    		    service.parseCommand("activity twitter set resultMessage",
	    		    		answerTime+":"+answerWriter+":"+answer);
	    		    int pos=answer.indexOf(hashTag);
	    		    if(pos>=0){
	    		       String head=answer.substring(0,pos);
	    		       String tail=answer.substring(pos+hashTag.length());
	    		       String toArduino=head+tail;
//	    		    mResultField.append("---"+toArduino);
	    		       service.parseCommand("adk set message",toArduino);
//	    			   service.sendCommandToService("adk command: set message "+toArduino,"");
	    		    }
	    		}
	    	}
	    	catch(Exception e){
	    		Log.d(TAG,"tweetTask error:"+e.toString());
				e.printStackTrace();
	    	}
	    	mTwitterController.setAccessingWeb(false);	    	
		   	return 1L;	
		 }

	}
	public String getUploadTag(){
		return this.uploadTag;
	}

}


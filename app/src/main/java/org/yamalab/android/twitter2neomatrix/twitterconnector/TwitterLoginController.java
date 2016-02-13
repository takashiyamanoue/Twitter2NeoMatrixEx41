
package org.yamalab.android.twitter2neomatrix.twitterconnector;

import java.util.Vector;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class TwitterLoginController {
	static final String TAG = "TwitterLoginController";

    Context context;
    WebView webView;
    TwitterController mTwitterController;
    Twitter mTwitter;
   RequestToken requestToken;
	TwitterLoginController(TwitterController tc) {
		Log.d(TAG,"TwitterLoginController");
        context=tc.getContext();
        mTwitterController=tc;
//		webView = (WebView)findViewById(R.id.twitterlogin);
    }
	/*
	public void loadUrl(String x){
//		mTwitterController.setAccessingWeb(true);
		WebSettings webSettings = webView.getSettings();
		//?????????????[?U?[???????T?C???C???????B?????s??????
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){

			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				if(url != null && url.startsWith(mTwitterController.CALLBACK_URL )){
					String[] urlParameters = url.split("\\?")[1].split("&");

					String oauthToken = "";
					String oauthVerifier = "";

					if(urlParameters[0].startsWith("oauth_token")){
						oauthToken = urlParameters[0].split("=")[1];
					}else if(urlParameters[1].startsWith("oauth_token")){
						oauthToken = urlParameters[1].split("=")[1];
					}

					if(urlParameters[0].startsWith("oauth_verifier")){
						oauthVerifier = urlParameters[0].split("=")[1];
					}else if(urlParameters[1].startsWith("oauth_verifier")){
						oauthVerifier = urlParameters[1].split("=")[1];
					}

					new setOAuthTask().execute(oauthToken,oauthVerifier);
				}
			}
		});


//		new loadUrlTask().execute(x);
	}
	*/
	/*
	@Override
	protected void onAccesssoryAttached() {
		// TODO Auto-generated method stub
		
	}
	*/

	private class setOAuthTask extends AsyncTask<String, Void, String> {
		 @Override
	     protected String doInBackground(String... params ) {
	    	Log.d(TAG, "connectTwitterTask.doInBackground - " );
	    	try{
	    		String token=params[0];
	    		String verifier=params[1];
                setOAuth(token,verifier);
	    	}
	    	catch(Exception e){
	    		Log.d(TAG,"tweetTask error:"+e.toString());
				e.printStackTrace();
				return "error!";
			}
	    	return "ok";
	     }
	     @Override
	     protected void onPostExecute(String result) {
	         super.onPostExecute(result);
			  mTwitterController.setAccessingWeb(false);
	    }
	}
			
	/* */
    protected void setOAuth(String oauthToken, String oauthVerifier) {
		Log.d(TAG,"setOAuth token="+oauthToken+" verifier="+oauthVerifier);

        mTwitter=mTwitterController.twitter;
//		mTwitter=mTwitterController.twitterOauth;
        requestToken=mTwitterController.requestToken;
			AccessToken accessToken = null;
	        if(mTwitter==null) {
	        	Log.d(TAG,"setOAuth activity.twitter==null");
	        	return;
	        }

			try {
				accessToken = mTwitter.getOAuthAccessToken(
						requestToken, oauthVerifier);

		        SharedPreferences pref=context.getSharedPreferences(
		        		  "Twitter_setting",context.MODE_PRIVATE);

		        SharedPreferences.Editor editor=pref.edit();
		        editor.putString("oauth_token",accessToken.getToken());
		        editor.putString("oauth_token_secret",accessToken.getTokenSecret());
		        editor.putString("status","available");

		        editor.commit();
		        
//		        context.showTabContents(R.id.main_tweet_label);
		        //finish();
			} catch (TwitterException e) {
	    		Log.d(TAG,"setOAuth error:"+e.toString());
				e.printStackTrace();
			}
    }
    public void startOAuthTask(String oAuthToken, String oAuthVerifier){
		new setOAuthTask().execute(oAuthToken,oAuthVerifier);
    }
}


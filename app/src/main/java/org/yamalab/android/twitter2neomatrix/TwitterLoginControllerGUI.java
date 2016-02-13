
package org.yamalab.android.twitter2neomatrix;

import java.util.Vector;

import org.yamalab.android.twitter2neomatrix.R;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class TwitterLoginControllerGUI extends AccessoryController {
	static final String TAG = "TwitterLoginControllerGUI";

    AdkTwitterActivity activity;
    WebView webView;
    TwitterControllerGUI mTwitterControllerGUI;
    Twitter mTwitter;
   RequestToken requestToken;
	TwitterLoginControllerGUI(AdkTwitterActivity hostActivity, TwitterControllerGUI tc) {
        super(hostActivity);
		Log.d(TAG,"TwitterLoginController");
        activity=hostActivity;
        mTwitterControllerGUI=tc;
		webView = (WebView)findViewById(R.id.twitterlogin);
    }
	public void loadUrl(String x){
//		mTwitterControllerGUI.setAccessingWeb(true);
		WebSettings webSettings = webView.getSettings();
		//?????????????[?U?[???????T?C???C???????B?????s??????
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){

			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				if(url != null && url.startsWith(mTwitterControllerGUI.CALLBACK_URL )){
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

//					new setOAuthTask().execute(oauthToken,oauthVerifier);
					activity.sendCommandToService("twitter set OAuth", oauthToken+" "+oauthVerifier);
				}
			}
		});

		new loadUrlTask().execute(x);
	}
		
	@Override
	protected void onAccesssoryAttached() {
		// TODO Auto-generated method stub
		
	}
	
	private class loadUrlTask extends AsyncTask<String, Void, String> {
		 @Override
	     protected String doInBackground(String... params ) {
	    	Log.d(TAG, "connectTwitterTask.doInBackground - " );
	    	try{
		    	String url=params[0];
				webView.loadUrl(url);
				webView.setFocusable(true);
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
//			  mTwitterControllerGUI.setAccessingWeb(false);
			  activity.sendCommandToService("twitter set accessingweb","false");
	    }
	}
		
}


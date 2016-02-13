package org.yamalab.android.twitter2neomatrix.service;

import java.io.IOException;

import android.util.Log;

public class FontTransmitter {
	/*
	 Android                         Arduino
	            <--------------       'y'  (yes, can send)
	            <--------------       'n'  (no, can't send)
	  
	  (if yes)
	  'f'     --------------->
	  font-position
	  font[32]
	    	            
	 
	 */
	AdkService service;
	boolean sendEnabled;
	FontAccessor fontAccessor;
	String currentTweet;
	String nextTweet;
	int fontPosition;
	char[] charArray;
	int times;
	private static String TAG="FontTransmitter";
	public FontTransmitter(AdkService s){
		service=s;
		times=-1;
	}
	public void setSendEnable(String f){
		if(f.equals("r")){
		   byte[] cf=getCurrentFont();
		   if(cf==null) return;
		   service.outputFontToDevice(cf);
		}
	}
    public boolean putString(String x){
    	if(x==null) return false;
    	if(x.equals("")) return true;
    	if(nextTweet!=null) return false;
    	if(currentTweet==null){
    		currentTweet=x;
    	}
    	else{
	        nextTweet=x;
    	}
    	return true;
    }
    private byte[] getCurrentFont(){
    	if(fontAccessor==null){
    		fontAccessor=service.getFontAccessor();
    	}
    	if(fontAccessor==null) {
			service.showLog(TAG+"-getCurrentFont fontAccessor== null.");
    		return null;
    	}
    	if(charArray==null){
    		if(currentTweet!=null){
        		charArray= fontAccessor.getJISString(currentTweet+"  ");    			
        		fontPosition=0;
    		}
    	}
    	if(charArray==null) {
			service.showLog(TAG+"-getCurrentFont, charArray==null.");
    		return null;
    	}
		byte[] fa=fontAccessor.getFontJIS(charArray[fontPosition]);
		if(fa==null){
			service.showLog(TAG+"-getCurrentFont, fa== null.");
			return null;
		}
//		printFontToLog(fa);
		fontPosition++;
        if(fontPosition>=currentTweet.length()){
        	fontPosition=0;
        	if(nextTweet!=null){
        		currentTweet=nextTweet;
        		nextTweet=null;
        		charArray= fontAccessor.getJISString(currentTweet);
        	}
        }
        return fa;
    }
		
}

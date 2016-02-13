package org.yamalab.android.twitter2neomatrix.twitterconnector;

public interface TwitterApplication {
	public String getOutput();
	public boolean parseCommand(String cmd, String v);
}

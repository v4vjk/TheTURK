package com.theturk;

import java.util.HashMap;
import java.util.Map;

public class XJob implements ITurkJob {
	public Map<String, String> perform(Map<String, String> x) {
		Map<String, String> y = new HashMap<String, String>();
		y.put("FULL_NAME", x.get("LAST_NAME") + ", " +
				x.get("FIRST_NAME"));
		return y;
	}
	// Added for testing
	public static void main(String[] args) {
		ITurkJob j = new XJob();
		Map<String, String> x = new HashMap<String, String>();
		x.put("FIRST_NAME", "Lionel");
		x.put("LAST_NAME", "Messi");
		Map<String, String> y = j.perform(x);
		System.out.println(y.get("FULL_NAME"));
	}

}

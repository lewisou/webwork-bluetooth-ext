package com.vxmt.blackberry.webwork.ext.bluetooth;

import net.rim.device.api.browser.field2.BrowserField;

public class Ut {
	static public void jsa_alert(BrowserField b, String s){
		b.executeScript("alert('" + s + "');");
	}
}

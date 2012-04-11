package com.vxmt.blackberry.webwork.ext.bluetooth;

import java.util.Enumeration;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.collection.util.SparseList;
import net.rim.device.api.script.ScriptEngine;
import net.rim.device.api.web.WidgetConfig;
import net.rim.device.api.web.WidgetExtension;

import org.w3c.dom.Document;

public final class BluetoothExtension implements WidgetExtension {

	private BrowserField _browser;
	private static final String FEATURE_BASIC = "vxmt.bluetooth.basic"; 
	private static SparseList loadedScripts = new SparseList();
	
	
	public String[] getFeatureList() {
		String[] result = new String[1];
		result[0] = FEATURE_BASIC;
		return result;
	}

	public void loadFeature(String feature, String version, Document doc,
			ScriptEngine scriptEngine) throws Exception {
		if (FEATURE_BASIC.equals(feature)) {
			BasicScript bs = new BasicScript(_browser);
			loadedScripts.add(bs);
			scriptEngine.addExtension(FEATURE_BASIC, bs);
		}
	}

	public void register(WidgetConfig _, BrowserField browserField) {
		_browser = browserField;
	}

	public void unloadFeatures(Document _) {
	     for (Enumeration e = loadedScripts.elements(); e.hasMoreElements();) {
	    	 BasicScript bs = (BasicScript)e.nextElement();
	    	 bs.release();
	     }
	     loadedScripts.removeAll();
	}
}

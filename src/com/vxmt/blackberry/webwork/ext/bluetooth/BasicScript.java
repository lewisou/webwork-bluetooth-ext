package com.vxmt.blackberry.webwork.ext.bluetooth;

import java.util.Enumeration;

import net.rim.device.api.bluetooth.BluetoothSerialPort;
import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.collection.util.SparseList;
import net.rim.device.api.script.Scriptable;

import com.vxmt.blackberry.webwork.ext.bluetooth.function.CloseFunction;
import com.vxmt.blackberry.webwork.ext.bluetooth.function.ConnectFunction;
import com.vxmt.blackberry.webwork.ext.bluetooth.function.ServiceListFunction;
import com.vxmt.blackberry.webwork.ext.bluetooth.function.WriteBackFunction;

public final class BasicScript  extends Scriptable {
	public final static String FIELD_SERVICE_LIST = "service_list";
	public final static String FIELD_CONNECT = "connect";
	public final static String FIELD_CLOSE = "close";
	public final static String FIELD_WRITE_BACK = "write_back";

	private static SparseList allconns = new SparseList();
	private BrowserField _browser;

	public BasicScript(BrowserField browserField) {
		super();
		_browser = browserField;
	}

	public Object getField(String name) throws Exception {
		if (FIELD_SERVICE_LIST.equals(name)) {
			return new ServiceListFunction(_browser);
		} else if (FIELD_CONNECT.equals(name)) {
			ConnectFunction rs = new ConnectFunction(_browser); 
			allconns.add(rs);
			return rs;
		} else if (FIELD_WRITE_BACK.equals(name)) {
			return new WriteBackFunction(this);
		} else if (FIELD_CLOSE.equals(name)) {
			return new CloseFunction(this);
		}

		return super.getField(name);
	}

	public BluetoothSerialPort findConnectByKey(String key) {
	     for (Enumeration e = allconns.elements(); e.hasMoreElements();) {
	    	 ConnectFunction cf = (ConnectFunction)e.nextElement();
	    	 if(key != null && key.equals(cf.getKey())) {
	    		 return cf.getPort();
	    	 }
	     }
	     return null;
	}
	

	public void release() {
	     for (Enumeration e = allconns.elements(); e.hasMoreElements();) {
	    	 ConnectFunction cf = (ConnectFunction)e.nextElement();
	    	 cf.close();
	     }
	     allconns.removeAll();
	}
}

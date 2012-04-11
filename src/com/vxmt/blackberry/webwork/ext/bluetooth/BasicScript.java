package com.vxmt.blackberry.webwork.ext.bluetooth;

import net.rim.device.api.bluetooth.BluetoothSerialPort;
import net.rim.device.api.bluetooth.BluetoothSerialPortInfo;
import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.script.Scriptable;

import com.vxmt.blackberry.webwork.ext.bluetooth.function.ConnectFunction;
import com.vxmt.blackberry.webwork.ext.bluetooth.function.WriteBackFunction;

public final class BasicScript  extends Scriptable {
	public final static String FIELD_SERVICE_LIST = "service_list";
	public final static String FIELD_CONNECT = "connect";
	public final static String FIELD_WRITE_BACK = "write_back";

	private BrowserField _browser;

	public BasicScript(BrowserField browserField) {
		super();
		_browser = browserField;
	}

	public Object getField(String name) throws Exception {
		if (FIELD_SERVICE_LIST.equals(name)) {
			return getServiceList();
		} else if (FIELD_CONNECT.equals(name)) {
			return new ConnectFunction(_browser);
		} else if (FIELD_WRITE_BACK.equals(name)) {
			return new WriteBackFunction();
		}

		return super.getField(name);
	}

	private String[] getServiceList() {
		BluetoothSerialPortInfo[] portInfos = BluetoothSerialPort.getSerialPortInfo();
		String[] rs = new String[portInfos.length];
		for(int i = 0; i < portInfos.length; i ++) {
			BluetoothSerialPortInfo info = portInfos[i];
			rs[i] = info.getDeviceName();
		}
		return rs;
	}

	public void release() {

	}
}

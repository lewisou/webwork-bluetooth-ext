package com.vxmt.blackberry.webwork.ext.bluetooth.function;

import net.rim.device.api.bluetooth.BluetoothSerialPort;
import net.rim.device.api.bluetooth.BluetoothSerialPortInfo;
import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.script.ScriptableFunction;

import com.vxmt.blackberry.webwork.ext.bluetooth.Ut;

public class ServiceListFunction extends ScriptableFunction {
	private BrowserField _browser;
	
	public ServiceListFunction(BrowserField browser) {
		_browser = browser;
	}
	
	public Object invoke(Object obj, Object[] args) throws Exception {
		String[] rs = getServiceList();
		return rs;
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

}

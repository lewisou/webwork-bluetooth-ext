package com.vxmt.blackberry.webwork.ext.bluetooth.function;

import net.rim.device.api.bluetooth.BluetoothSerialPort;
import net.rim.device.api.bluetooth.BluetoothSerialPortInfo;
import net.rim.device.api.bluetooth.BluetoothSerialPortListener;
import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.script.ScriptableFunction;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.util.Arrays;

public final class ConnectFunction extends ScriptableFunction implements BluetoothSerialPortListener {
	private BluetoothSerialPort _port;
	private BrowserField _browser;
    private byte[] _receiveBuffer = new byte[1024];
    private String _callBack;
    private String _key;

	public ConnectFunction(BrowserField bf) {
		_browser = bf;
		Arrays.fill(_receiveBuffer, (byte)'a');
	}

	public Object invoke(Object obj, Object[] args) throws Exception {
		if(_port == null) {
			_key = (String)args[0];
			BluetoothSerialPortInfo info = getPortInfo((String)args[1]);
			_port = new BluetoothSerialPort(info, BluetoothSerialPort.BAUD_115200, BluetoothSerialPort.DATA_FORMAT_PARITY_NONE | BluetoothSerialPort.DATA_FORMAT_STOP_BITS_1 | BluetoothSerialPort.DATA_FORMAT_DATA_BITS_8, BluetoothSerialPort.FLOW_CONTROL_NONE, 1024, 1024, this);
			_callBack = (String)args[2];
		}
		return new Boolean(true);
	}

	public String getKey() {
		return _key;
	}

	public BluetoothSerialPort getPort() {
		return _port;
	}
	
    public void dataReceived(int length) {
    	if(length == -1) {
    		return;
    	}

        int len = 0;
        try {
            if((len = _port.read(_receiveBuffer, 0, length)) != 0) {
            	if(len == 1 && _receiveBuffer[0] == '\r') {
                        _receiveBuffer[1] = '\n';
                        ++len;
            	}
            	len = len - 2;
            	js(_callBack + "('" + new String(_receiveBuffer, 0, len) + "');");
            }
        } catch(Exception ioex) {
        	EventLogger.logEvent( 1, ioex.toString().getBytes(), EventLogger.ERROR);
        	js("alert('exception:" + ioex.toString() +"');");
        }
    }

	public void dataSent() {
		// TODO Auto-generated method stub
	}

	public void deviceConnected(boolean success) {
		//js("alert(" + success + ");");
		// TODO Auto-generated method stub
	}

	public void deviceDisconnected() {
//		js("alert('about to close.');");
		if(_port != null)
			_port.close();
	}

	public void dtrStateChange(boolean high) {
		// TODO Auto-generated method stub	
	}

	private static BluetoothSerialPortInfo getPortInfo(String deviceName) {
		BluetoothSerialPortInfo[] portInfos = BluetoothSerialPort.getSerialPortInfo();
		for(int i = 0; i < portInfos.length; i ++) {
			BluetoothSerialPortInfo info = portInfos[i];
			if(info.getDeviceName().equals(deviceName)) {
				return info; 
			}
		}
		return null;
	}

	private Object js(String script){
		return _browser.executeScript(script);
	}

	public void close() {
		_port.close();
	}
}

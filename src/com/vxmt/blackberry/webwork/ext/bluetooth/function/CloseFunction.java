package com.vxmt.blackberry.webwork.ext.bluetooth.function;

import net.rim.device.api.bluetooth.BluetoothSerialPort;
import net.rim.device.api.script.ScriptableFunction;

import com.vxmt.blackberry.webwork.ext.bluetooth.BasicScript;

public final class CloseFunction extends ScriptableFunction {
	private BasicScript _script;

	public CloseFunction(BasicScript script) {
		_script = script;
	}

	public Object invoke(Object obj, Object[] args) throws Exception {
		String key = (String)args[0];
		BluetoothSerialPort port = _script.findConnectByKey(key);
		port.close();
		return new Boolean(true);
	}
}

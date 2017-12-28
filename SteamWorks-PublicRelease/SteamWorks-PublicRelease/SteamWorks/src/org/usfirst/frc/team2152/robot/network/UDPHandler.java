package org.usfirst.frc.team2152.robot.network;


import java.util.HashMap;

import org.usfirst.frc.team2152.robot.network.Vars.Var;


	/** <b>USAGE:</b> Listener for UDPReceiver, parses and stores data.
	 * 
	 * <p>To retrieve data, use getValue method as follows: <br/>
	 * Say you're listening for a float called <code>Distance</code>
	 * in the group called <code>Boiler</code>. Then, to retrieve
	 * the value (or a default value if it hasn't been delivered),
	 *  do this:</p>
	 * 
	 * <code>
	 * > // Assuming handler is a UDPHandler <br/>
	 * > float x = handler.getValue(Vars.Boiler.Float.Distance); <br/>
	 * </code>
	 * 
	 * <p>As another example: say you're listening for a boolean named
	 * <code>Running</code> in a group called <code>Misc</code>:</p>
	 *
	 * <code>
	 * > boolean running = handler.getValue(Vars.Misc.Boolean.Running);
	 * </code>
	 * 
	 * <p>All variables that you want to use must be specified in the
	 * appropriate enum in Vars.java. Names and groupd must match those
	 * in the sender code (python), case-insensitive.</p>
	 * 
	 * @see org.usfirst.frc.team2152.robot.network.Vars.java
	 */
public class UDPHandler implements UDPListener {

	// Hashmap of data received (name and value string)
	private HashMap<String, String> map = new HashMap<>();

	/**
	 * Returns a value for a variable sent over the network and declared in Vars.java.
	 * @param  var - The variable to be retrieved, from one of the enums in Vars.java
	 * @param <T> - The type of the variable that will be retrieved
	 * @return The value of the variable as stored, or a default value if it hasn't been received yet
	 * @see Vars.java
	 */
	public <T, V extends Var<T> & Vars.Group> T getValue(V param) {
		Vars.Group group = (Vars.Group) param;
		Vars.Var<T> var  = (Vars.Var) param;

		String name = var.toString().toLowerCase(); //e.g. "distance"
		String groupName = group.getName().toLowerCase(); //e.g. "peg"

		String keyName = String.format("%s/%s", groupName, name); // e.g. "Boiler/XAngle"
		String strVal;

		synchronized(map) {
			strVal = map.getOrDefault(keyName, null);
		}

		T val = null;
		if(strVal == null) {
			val = var.getDefault();
		} else {
			val = var.read(strVal);
		}

		return val;
	}

	@Override
	public void packetReceived(byte[] data) {
		deserialize(data);
	}
	
	private void deserialize(byte[] data) {
		String str = new String(data).toLowerCase();
//		System.out.println(str);
		int startIndex = str.indexOf('%');
		int endIndex = str.indexOf(';');
		
		// Ignore invalid data
		if (startIndex == -1 || endIndex == -1) return;

		// Split string into counterparts
		String inner = str.substring(startIndex + 1, endIndex);
		String[] group_fields = inner.split(":");

		String group = group_fields[0],
			   fieldString = group_fields[1];

		String[] kvStrings = fieldString.split(",");
		String[][] key_value = new String[kvStrings.length][2];

		for(int i = 0; i < kvStrings.length; i++) {
			key_value[i] = kvStrings[i].split("=");
		}

		for(String[] kv : key_value) {
			String key = kv[0];
			String value = kv[1];
			synchronized(map) {
				map.put(String.format("%s/%s", group, key), value);
			}
		}
	}

	public synchronized void resetValues() {
		map.clear();
	}
}

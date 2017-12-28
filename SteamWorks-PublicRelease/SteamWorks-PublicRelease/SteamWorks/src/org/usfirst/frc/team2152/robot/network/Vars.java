package org.usfirst.frc.team2152.robot.network;

	/** <b>USAGE</b>: Declare variables to be used in the program that will be send over the network.
	 * 
	 * <p>Variable names are enum values in the enum whose name matches
	 * the variable's corresponding type, within the enum whose name matches
	 * the variable's corresponding group.</p>
	 */
public class Vars {	

	public static class Boiler {
		public static enum Double implements Vars.Double, BoilerField {
			XAngle, Distance, YAngle
		}
	}

	public static class Peg {
		public static enum Double implements Vars.Double, PegField {
			XAngle, Distance, Parallax
		}
	}

	/*-------------------------------------------------*/

	/* Groups for organization of variables. These interfaces are used
	 * to pass the group name with the enum values.
	 */
	private static interface BoilerField extends Group {
		public default java.lang.String getName() {
			return "Boiler";
		}
	}

	private static interface PegField extends Group {
		public default java.lang.String getName() {
			return "Peg";
		}
	}

	/*-------------------------------------------------*/
	/*-------------------------------------------------*/
	/*-------------------------------------------------*/
	/*-------------------------------------------------*/
	/*-------------------------------------------------*/
	/*-------------------------------------------------*/
	/*-------------------------------------------------*/
	/*-------------------------------------------------*/

	/* Types of variables that can be received; use this template
	 *  if you want to add another type called TYPE:
	 *  private static interface private static interface Name extends Var<TYPE> {
	 *  	public default TYPE getDefault() {
	 *  		// return default value
	 *  	}
	 *  	
	 *  	public default TYPE read(java.lang.String str) {
	 *  		// Code to parse str into TYPE value
	 *  	}
	 *  }
	 */
	private static interface Double extends Var<java.lang.Double> {
		public default java.lang.Double getDefault() {
			return 0d;
		}

		public default java.lang.Double read(java.lang.String str) {
			return java.lang.Double.parseDouble(str);
		}
	}

	private static interface Boolean extends Var<java.lang.Boolean> {
		public default java.lang.Boolean getDefault() {
			return false;
		}

		public default java.lang.Boolean read(java.lang.String str) {
			return java.lang.Boolean.parseBoolean(str);
		}
	}

	private static interface String extends Var<java.lang.String> {
		public default java.lang.String getDefault() {
			return "";
		}

		public default java.lang.String read(java.lang.String str) {
			return str;
		}
	}

	// Used to pass group name into UDPHandler.getValue through enums
	public static interface Group {
		public java.lang.String getName();
	} 

	static interface Var<T>{
		public T getDefault(); 
		public T read(java.lang.String str);
	};

}

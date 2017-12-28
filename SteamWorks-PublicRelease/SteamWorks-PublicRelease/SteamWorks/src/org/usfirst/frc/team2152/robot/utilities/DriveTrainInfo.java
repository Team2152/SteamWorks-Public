package org.usfirst.frc.team2152.robot.utilities;

public class DriveTrainInfo {

	private boolean reverseMode;
	private boolean highGear;

	public DriveTrainInfo() {
		reverseMode = false;
		highGear = true;
	}

	public void setReverseMode(boolean value) {
		reverseMode = value;
	}

	public boolean getReverseMode() {
		return reverseMode;
	}

	public boolean toggle() {
		if (reverseMode == true) {
			reverseMode = false;
		} else {
			reverseMode = true;
		}

		return reverseMode;

	}

	public void setHighGear(boolean value) {
		highGear = value;
	}

	public boolean getHighGear() {
		return highGear;

	}

}
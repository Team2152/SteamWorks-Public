package org.usfirst.frc.team2152.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

/**
 *
 */
public class NavX extends Subsystem {

	AHRS navx;

	// Initialize your subsystem here
	public NavX() {
		try {
			/** Communicate w/navX-MXP via the MXP SPI Bus. */
			/*
			 * Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or
			 * SerialPort.Port.kUSB
			 */
			/*
			 * See
			 * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/
			 * for details.
			 */
			navx = new AHRS(SPI.Port.kMXP);
			resetAngle();
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new NAVXGetAngle());
	}
	/**
	 * Returns object <code>navx<code>
	 * @return navx
	 */
	public AHRS getAHRS() {
		return navx;
	}
	
	/**
	 * Resets current angle recorded by the NavX to 0
	 */
	public void resetAngle() {
		navx.reset();
	}
	
	/**
	 * Returns current recorded angle from NavX
	 * @return double angle
	 */
	public double getAngle() {
		return navx.getAngle();
	}
}
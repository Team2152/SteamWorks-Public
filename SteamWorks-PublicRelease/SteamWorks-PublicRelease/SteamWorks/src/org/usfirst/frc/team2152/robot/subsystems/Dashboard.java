package org.usfirst.frc.team2152.robot.subsystems;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {

	// Put Methods
	
	/**
	 * Updates the boolean "UDP Connection" on the SmartDashboard
	 * @param udpRunning
	 * 				The boolean showing the connection between the Driver Station and the UDP
	 */
	public void putUDP(boolean udpRunning) {
		SmartDashboard.putBoolean("UDP Connection", udpRunning);
	}

	/**
	 * Updates the value of "Motor Gains" on the SmartDashboard
	 * @param motorGain
	 * 				The gain of the DriveTrain motors, value should be between -1 and 1.
	 */
	public void putDriveGain(double motorGain) {
		// Editable Field
		SmartDashboard.putNumber("Motor Gains", motorGain);
	}

	/**
	 * Updates the values of "High Gear" and "Low Gear" on the SmartDashboard
	 * @param highGear
	 * 				The value of the current Drive Gear setting
	 */
	public void putDriveGear(boolean highGear) {
		SmartDashboard.putBoolean("High Gear", highGear);
		SmartDashboard.putBoolean("Low Gear", !highGear);
	}

	/**
	 * Updates the values of "Angle to Peg" and "Distance to Peg" on the SmartDashboard
	 * @param angle
	 * 				The angle between the robot and the gear peg
	 * @param distance
	 * 				The distance between the robot and the gear peg
	 */
	public void putPegVision(double angle, double distance) {
		SmartDashboard.putNumber("Angle to Peg", angle);
		SmartDashboard.putNumber("Distance to Peg", distance);
	}

	/**
	 * Updates the values of "EncoderR" and "EncoderL" on the SmartDashboard
	 * @param encoderR
	 * 				Value of the right encoder
	 * @param encoderL
	 * 				Value of the left encoder
	 */
	public void putEncoders(double encoderR, double encoderL) {
		SmartDashboard.putNumber("EncoderR", encoderR);
		SmartDashboard.putNumber("EncoderL", encoderL);
	}

	/**
	 * Sets the reset values to false. If true, chosen encoder values would be reset.
	 */
	public void putEncoderReset() {
		// Editable Fields
		SmartDashboard.putBoolean("Reset All Encoders", false);
		SmartDashboard.putBoolean("Reset R Encoder", false);
		SmartDashboard.putBoolean("Reset L Encoder", false);
	}

	/**
	 * Shows the selected auto on the SmartDashboard
	 */
	public void putAutoMode() {
		SmartDashboard.putData("Auto mode", Robot.autoModeChooser);
	}

	/**
	 * Updates the value "NavxAngle" on the SmartDashboard
	 * @param angle
	 * 				The angle being read from the Navx
	 */
	public void putNavxAngle(double angle) {
		SmartDashboard.putNumber("NavxAngle", angle);
	}

	/**
	 * Updates the value "Amperage" on the SmartDashboard
	 * @param amperage
	 * 				The value of the current amperage.
	 */
	public void putAmperage(double amperage) {
		SmartDashboard.putNumber("Amperage", amperage);
	}

	/**
	 * Updates the value of UDPInterrupted on the SmartDashboard
	 * @param interrupted
	 * 				The value that returns true if the UDP has been interrupted.
	 */
	public void putUDPInterrupted(boolean interrupted) {
		SmartDashboard.putBoolean("interrupted", interrupted);
	}

	/**
	 * Puts the remaining match time from the Driver Station onto the SmartDashboard.
	 */
	public void putMatchTime() {
		SmartDashboard.putNumber("Match Time Left", DriverStation.getInstance().getMatchTime());
	}

	/**
	 * Puts a boolean that flashes true and false during the last 30 seconds of the match.
	 */
	public void putLast30Seconds() {
		// Interacts with the match timer
		// Do not run this in autonomous, it will consider the entire period as
		// end-game
		boolean flash = false;
		int time = (int) Robot.steamworksDashboard.getMatchTime();
		if ((time % 2) == 0) {
			flash = true;
		} else if ((time % 2) == 1) {
			flash = false;
		}
		SmartDashboard.putBoolean("Last 30 Seconds", flash);
	}

	/**
	 * Puts the "Record" boolean onto the SmartDashboard
	 * @param record
	 * 				The boolean that determines if we want the robot to be recording or not.
	 */
	public void putRecord(boolean record) {
		SmartDashboard.putBoolean("Record", record);
	}

	/**
	 * Sets the "Last 30 Seconds" boolean on the SmartDashboard to a given value
	 * @param b
	 * 			The new value of "Last 30 Seconds"
	 */
	public void putSetLast30Seconds(boolean b) {
		// Uses set value
		SmartDashboard.putBoolean("Last 30 Seconds", b);
	}

	/**
	 * Puts the current angle of the Gear Manipulator onto the SmartDashboard
	 * @param encoderAngle
	 * 					The current angle of the Gear Manipulator
	 */
	public void putGMAngle(double encoderAngle) {
		SmartDashboard.putNumber("GM Angle", encoderAngle);
	}

	/**
	 * Shows if the Gear Manipulator's lower limit is being activated.
	 * @param lowerLimit
	 * 				The boolean value of the limit switch
	 */
	public void putGMLowerLimit(boolean lowerLimit) {
		SmartDashboard.putBoolean("Lower Limit", lowerLimit);
	}

	/**
	 * Shows if the intake limit of the Gear Manipulator has been activated
	 * @param intakeLimit
	 * 				The boolean value of the limit
	 */
	public void putGMIntakeLimit(boolean intakeLimit) {
		SmartDashboard.putBoolean("Intake Limit", intakeLimit);
	}

	/**
	 * Shows if the Gear Manipulator's upper limit is being activated.
	 * @param upperLimit
	 * 				The boolean value of the limit switch
	 */
	public void putGMUpperLimit(boolean upperLimit) {
		SmartDashboard.putBoolean("Upper Limit", upperLimit);
	}

	/**
	 * Puts the current autonomous status on the SmartDashboard
	 * @param autoStatus
	 * 				The current status of autonomous
	 */
	public void putAutoStatus(String autoStatus) {
		SmartDashboard.putString("AUTOSTATUS: ", autoStatus);
	}

	/**
	 * Puts the exit status of autonomous mode
	 * @param autoExit
	 * 				The exit status of autonomous
	 */
	public void putAutoExit(String autoExit) {
		SmartDashboard.putString("AUTOEXITS:", autoExit);
	}

	/**
	 * Puts the boolean value that displays whether the watchdog timer has exited gear auto
	 * @param exitGearAuto
	 * 				The boolean value that displays whether the watchdog timer has exited gear auto
	 */
	public void putWatchdogExitGearAuto(boolean exitGearAuto) {
		SmartDashboard.putBoolean("Watchdog Exit Gear Auto", exitGearAuto);
	}

	/**
	 * Updates the values of "Left Heading PCT" and "Right Heading PCT" on the SmartDashboard
	 * @param rightHeading
	 * 				The desired value of "Right Heading PCT"
	 */
	public void putHeadingPCT(double rightHeading) {
		SmartDashboard.putNumber("Left Heading PCT", -rightHeading);
		SmartDashboard.putNumber("Right Heading PCT", rightHeading);
	}

	/**
	 * Puts the error of PCT onto the SmartDashboard
	 * @param PCTError
	 * 				The error of PCT
	 */
	public void putPCTError(double PCTError) {
		SmartDashboard.putNumber("PCT Error", PCTError);
	}
	
	/**
	 * Puts a boolean showing the log setting
	 * @param logSetting
	 * 				Name of the boolean on the SmartDashboard
	 * @param log
	 * 				The log setting to be displayed
	 */
	public void putLogSetting(String logSetting, boolean log) {
		// Editable Field
		SmartDashboard.putBoolean(logSetting, log);
	}
	
	/**
	 * Puts a custom shooter speed
	 * @param shooterSpeed
	 * 					The driver-chosen custom shooter speed
	 */
	public void putCustomShooterSpeed(double shooterSpeed){
		SmartDashboard.putNumber("Custom Shooter Speed", shooterSpeed);
	}
	
	/**
	 * Allows the driver to choose a shooter speed from the SmartDashboard.
	 */
	public void putBallShooterSpeed(){
		SmartDashboard.putData("Ball Shooter Speed", Robot.shooterSpeedChooser);
	}
	// Get Methods

	/**
	 * Gets the drivetrain motor gains from the SmartDashboard
	 * @return The drivetrain motor gains
	 */
	public double getMotorGain() {
		return SmartDashboard.getNumber("Motor Gains", 1.0);
	}

	/**
	 * Gets the angle between the robot and the gear peg
	 * @return The angle between the robot and the gear peg
	 */
	public double getPegAngle() {
		return SmartDashboard.getNumber("Angle to Peg", 0);
	}

	/**
	 * Gets the distance between the robot and the gear peg
	 * @return The distance between the robot and the gear peg
	 */
	public double getPegDistance() {
		return SmartDashboard.getNumber("Distance to Peg", 0);
	}

	/**
	 * Gets the value of the right encoder
	 * @return The value of Encoder R
	 */
	public double getEncoderRValue() {
		return SmartDashboard.getNumber("Encoder R", 0);
	}

	/**
	 * Gets the value of the left encoder
	 * @return The value of Encoder L
	 */
	public double getEncoderLValue() {
		return SmartDashboard.getNumber("Encoder L", 0);
	}

	/**
	 * Gets the value of the ResetAll switch
	 * @return The value of the ResetAllEncoders boolean
	 */
	public boolean getResetAllEncoders() {
		return SmartDashboard.getBoolean("Reset All Encoders", false);
	}

	/**
	 * Gets the value of the ResetR switch
	 * @return The value of the ResetREncoder boolean
	 */
	public boolean getResetREncoder() {
		return SmartDashboard.getBoolean("Reset R Encoder", false);

	}

	/**
	 * Gets the value of the ResetL switch
	 * @return The value of the ResetLEncoder boolean
	 */
	public boolean getResetLEncoder() {
		return SmartDashboard.getBoolean("Reset L Encoder", false);
	}

	/**
	 * Gets the value of the MatchTime
	 * @return The remaining time in the match
	 */
	public double getMatchTime() {
		return SmartDashboard.getNumber("Match Time Left", 0);
	}

	/**
	 * Gets the log setting from the SmartDashboard
	 * @param logSetting The name of the boolean on the SmartDashboard
	 * @param logDefault The default setting
	 * @return The current log setting
	 */
	public boolean getLogSetting(String logSetting, boolean logDefault) {
		return SmartDashboard.getBoolean(logSetting, logDefault);
	}
	
	/**
	 * Gets the current custom shooter speed
	 * @return The current custom shooter speed
	 */
	public double getCustomShooterSpeed(){
		return SmartDashboard.getNumber("Custom Shooter Speed", 0.5);
	}
}

package org.usfirst.frc.team2152.robot.subsystems;

import org.usfirst.frc.team2152.robot.Robot;
import org.usfirst.frc.team2152.robot.RobotMap;
import org.usfirst.frc.team2152.robot.commands.LimeDrive;
import org.usfirst.frc.team2152.robot.network.NetworkPIDSourceDistance;
import org.usfirst.frc.team2152.robot.network.NetworkPIDSourceXAngle;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// for this subsystem a reasonable amount of speed when performing non-PID
	// operations
	private static final double HIGH_GEAR_RAMP = 8.7;
	private static final double LOW_GEAR_RAMP = 9;
	// LOW 9
	// HIGH 8.7

	// === Speed Controllers
	private CANTalon right1;
	private CANTalon right2;
	private CANTalon right3;
	private CANTalon left1;
	private CANTalon left2;
	private CANTalon left3;

	// === Create encoders
	private Encoder encoderR;
	private int encoderRA = RobotMap.DIO_0;
	private int encoderRB = RobotMap.DIO_1;

	private Encoder encoderL;
	private int encoderLA = RobotMap.DIO_2;
	private int encoderLB = RobotMap.DIO_3;
	/*
	 * Finding the Distance Per Pulse Juan Halleran and Alex Basset FRC Team:
	 * 2152 S*M*A*S*H
	 * 
	 * This number was determined by taking in account the gear ratio of the
	 * output shaft (50:34) the gear ratio from the output shaft to the encoder
	 * wheel (3:1) the circumference of the wheel (4pi for a 4 inch diameter
	 * wheel) and the count of the encoder 256. We can then find the amount of
	 * encoder ticks per wheel rotation by using this equation: (Gear Ratio
	 * 1)*(Gear Ratio 2)*(encoder ticks) (50/34)*(3/1)*256 =
	 * 1129.4117647058823529411764705882 Then we can get the distance of the
	 * wheel by dividing the circumference of the wheel by the encoder ticks per
	 * wheel rotation. (4pi)/1129.4117647058823529411764705882 That gives us
	 * this number = 0.01112647398146385105288852864912 set that as your encoder
	 * distance per pulse
	 * encoder.setDistancePerPulse(0.01112647398146385105288852864912); When you
	 * call encoder.getDistance(); the number will be in inches.
	 */
	private static final double DISTANCE_PER_PULSE = 0.01112647398146385105288852864912;

	// === Drive Train
	private RobotDrive drive;
	private DoubleSolenoid shifter;

	private NetworkPIDSourceXAngle netPIDXangle;
	private NetworkPIDSourceDistance netPIDDistance;

	// dd
	// Initialize your subsystem here
	/**
	 * Method for the setup of the Drivetrain Subsystem
	 */
	public DriveTrain() {

		// === Create ESC objects for each of the motors
		right1 = new CANTalon(RobotMap.RIGHT_DRIVE_1_CAN_Id);
		right1.enableBrakeMode(true);
		right1.setSafetyEnabled(false);
		right1.setVoltageRampRate(LOW_GEAR_RAMP);

		right2 = new CANTalon(RobotMap.RIGHT_DRIVE_2_CAN_Id);
		right2.enableBrakeMode(true);
		right2.setSafetyEnabled(false);
		right2.changeControlMode(TalonControlMode.Follower);
		right2.set(RobotMap.RIGHT_DRIVE_1_CAN_Id);
		right2.setVoltageRampRate(LOW_GEAR_RAMP);

		right3 = new CANTalon(RobotMap.RIGHT_DRIVE_3_CAN_Id);
		right3.enableBrakeMode(true);
		right3.setSafetyEnabled(false);
		right3.changeControlMode(TalonControlMode.Follower);
		right3.set(RobotMap.RIGHT_DRIVE_1_CAN_Id);
		right3.setVoltageRampRate(LOW_GEAR_RAMP);

		left1 = new CANTalon(RobotMap.LEFT_DRIVE_1_CAN_Id);
		left1.enableBrakeMode(true);
		left1.setSafetyEnabled(false);
		left1.setVoltageRampRate(LOW_GEAR_RAMP);

		left2 = new CANTalon(RobotMap.LEFT_DRIVE_2_CAN_Id);
		left2.enableBrakeMode(true);
		left2.setSafetyEnabled(false);
		left2.changeControlMode(TalonControlMode.Follower);
		left2.set(RobotMap.LEFT_DRIVE_1_CAN_Id);
		left2.setVoltageRampRate(LOW_GEAR_RAMP);

		left3 = new CANTalon(RobotMap.LEFT_DRIVE_3_CAN_Id);
		left3.enableBrakeMode(true);
		left3.setSafetyEnabled(false);
		left3.changeControlMode(TalonControlMode.Follower);
		left3.set(RobotMap.LEFT_DRIVE_1_CAN_Id);
		left3.setVoltageRampRate(LOW_GEAR_RAMP);

		// === Create drive train object for 4 motors ===
		drive = new RobotDrive(left1, right1);
		drive.setSafetyEnabled(false);

		// === Create Encoders

		encoderR = new Encoder(encoderRA, encoderRB, true, EncodingType.k4X);
		encoderR.setDistancePerPulse(DISTANCE_PER_PULSE);
		encoderR.setSamplesToAverage(1);
		encoderR.reset();

		encoderL = new Encoder(encoderLA, encoderLB, false, EncodingType.k4X);
		encoderL.setDistancePerPulse(DISTANCE_PER_PULSE);
		encoderL.setSamplesToAverage(1);
		encoderL.reset();

		shifter = new DoubleSolenoid(0, 1);
		shiftLow();

		netPIDXangle = new NetworkPIDSourceXAngle();
		netPIDDistance = new NetworkPIDSourceDistance();
	}

	/**
	 * Provide tank steering to the Robot.
	 * 
	 * @param left
	 *            The value of the left stick.
	 * @param right
	 *            The value of the right stick
	 */
	public void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}

	/**
	 * Arcade drive implements single stick driving.
	 * 
	 * @param forward
	 *            The value to use for forwards/backwards
	 * @param turn
	 *            The value to use for the rotate right/left
	 */
	public void arcadeDrive(double forward, double turn) {
		drive.arcadeDrive(forward, turn);

	}

	/**
	 * Multiplies the turn value by the throttle, quickTurn allows you to make
	 * spin turns
	 * 
	 * @param quickTurn
	 *            boolean to control quick turn
	 * @param throttle
	 *            The value to use for forwards/backwards
	 * @param turn
	 *            The value to use for the rotate right/left
	 */
	public void cheesyDrive(boolean quickTurn, double throttle, double turn) {
		if (quickTurn || (Math.abs(throttle) <= 0.25)) {
			arcadeDrive(0.0, (turn * 0.75));
		} else {
			if (Robot.driveTrainInfo.getHighGear()) {
				arcadeDrive((throttle), -(throttle * (turn * 0.66)));
			} else {
				arcadeDrive((throttle), -(throttle * turn));
			}
		}
	}

	/**
	 * Sets the default command. If this is not called or is called with null,
	 * then there will be no default command for the subsystem.
	 */
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new LimeDrive());
	}

	/**
	 * The inches on the right encoder
	 * 
	 * @return The distance in inches
	 */
	public double getRightDistance() {
		return (encoderR.getDistance());
	}

	/**
	 * The inches on the left encoder
	 * 
	 * @return The distance in inches
	 */

	public double getLeftDistance() {
		return (encoderL.getDistance());
	}

	/**
	 * The average distance of the left and right encoders
	 * 
	 * @return the average distance in inches
	 */
	public double getAverageDistance() {
		return (encoderR.getDistance() + encoderL.getDistance()) / 2;
	}

	/**
	 * Resets the encoders
	 */
	public void resetAllEncoders() {
		encoderR.reset();
		encoderL.reset();
	}

	/**
	 * Reset the right encoder
	 */
	public void resetREncoder() {
		encoderR.reset();
	}

	/**
	 * Reset the left encoder
	 */
	public void resetLEncoder() {
		encoderL.reset();
	}

	/**
	 * The rate of the left encoder
	 * 
	 * @return Rate of the left encoder in inches per second
	 */
	public double getEncoderRateL() {
		return (encoderL.getRate());
	}

	/**
	 * The rate of the right encoder
	 * 
	 * @return Rate of the right encoder in inches per second
	 */
	public double getEncoderRateR() {
		return (encoderR.getRate());
	}

	/**
	 * Shift the drive train into high gear
	 */
	public void shiftHigh() {
		// Can be changed to shiftUp/shiftDown
		Robot.driveTrainInfo.setHighGear(true);
		shifter.set(DoubleSolenoid.Value.kForward);
		changeRampRate(HIGH_GEAR_RAMP);
	}

	/**
	 * Shift the drive train into low gear
	 */
	public void shiftLow() {
		// Can be changed to shiftUp/shiftDown
		Robot.driveTrainInfo.setHighGear(false);
		shifter.set(DoubleSolenoid.Value.kReverse);
		changeRampRate(LOW_GEAR_RAMP);
	}

	/**
	 * Calculate the power draw from all of the drive motors
	 * 
	 * @return the total draw in amps
	 */
	public double totalPowerDraw() {
		double totalPower = 0;
		double totalCurrent = 0;
		totalCurrent = (right1.getOutputCurrent() + right2.getOutputCurrent() + right3.getOutputCurrent()
				+ left1.getOutputCurrent() + left2.getOutputCurrent() + left3.getOutputCurrent());
		totalPower = totalCurrent;
		return totalPower;
	}

	/**
	 * returns the right encoder object to be used in PIDs
	 * 
	 * @param type
	 *            Which parameter of the encoder you are using as a process
	 *            control variable.
	 * @return the right encoder object
	 */
	public Encoder getEncoderR(PIDSourceType type) {
		encoderR.setPIDSourceType(type);
		return encoderR;
	}

	/**
	 * returns the left encoder object to be used in PIDs
	 * 
	 * @param type
	 *            Which parameter of the encoder you are using as a process
	 *            control variable.
	 * @return the left encoder object
	 */
	public Encoder getEncoderL(PIDSourceType type) {
		encoderL.setPIDSourceType(type);
		return encoderL;
	}

	/**
	 * Set the speed of the right motors
	 * 
	 * @param speed
	 *            from -1 to 1
	 */
	public void setRightSpeed(double speed) {
		right1.set(speed);
	}

	/**
	 * Set the speed of the left motors
	 * 
	 * @param speed
	 *            from -1 to 1
	 */
	public void setLeftSpeed(double speed) {
		left1.set(speed);
	}

	/**
	 * PID source for vision angle
	 * 
	 * @param type
	 *            Which parameter of the encoder you are using as a process
	 *            control variable.
	 * @return the vision angle object
	 */
	public NetworkPIDSourceXAngle getNetXanglePID(PIDSourceType type) {
		netPIDXangle.setPIDSourceType(type);
		return netPIDXangle;
	}

	/**
	 * PID source for vision distance
	 * 
	 * @param type
	 *            Which parameter of the encoder you are using as a process
	 *            control variable.
	 * @return the vision distance object
	 */
	public NetworkPIDSourceDistance getNetDistancePID(PIDSourceType type) {
		netPIDDistance.setPIDSourceType(type);
		return netPIDDistance;
	}
/**
 * Changes the ramp rate of the driveTrain motors
 * @param rampRate Ramp rate in Volts per second
 */
	private void changeRampRate(double rampRate) {
		right1.setVoltageRampRate(rampRate);
		right2.setVoltageRampRate(rampRate);
		right3.setVoltageRampRate(rampRate);
		left1.setVoltageRampRate(rampRate);
		left2.setVoltageRampRate(rampRate);
	}
/**
 * Sets the ramp rate to 0
 */
	public void disableRamp() {
		right1.setVoltageRampRate(0);
		right2.setVoltageRampRate(0);
		right3.setVoltageRampRate(0);
		left1.setVoltageRampRate(0);
		left2.setVoltageRampRate(0);
	}
/**
 * Set the ramp rate to the preset rates for high and low gears
 */
	public void enableRamp() {
		if (Robot.driveTrainInfo.getHighGear()) {
			changeRampRate(HIGH_GEAR_RAMP);
		} else {
			changeRampRate(HIGH_GEAR_RAMP);
		}
	}
}

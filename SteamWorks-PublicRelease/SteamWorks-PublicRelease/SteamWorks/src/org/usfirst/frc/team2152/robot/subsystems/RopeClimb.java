package org.usfirst.frc.team2152.robot.subsystems;

import org.usfirst.frc.team2152.robot.Robot;
import org.usfirst.frc.team2152.robot.RobotMap;
import org.usfirst.frc.team2152.robot.commands.RopeClimbTrigger;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RopeClimb extends Subsystem {
	
	public static final boolean LIMIT_HIT_LEFT = false;
	public static final boolean LIMIT_HIT_RIGHT = false;


	private CANTalon ropeMotor;


	public RopeClimb() {

		ropeMotor = new CANTalon(RobotMap.ropeMotorID);
		ropeMotor.enableBrakeMode(true);
		ropeMotor.set(0);

	}
/**
 * Sets the speed of the motor
 * @param speed
 */
	public void setSpeed(double speed) {
		ropeMotor.set(speed);
	
	}
	/**
	 * Gets the current draw
	 * @return
	 */
	public double getCurrent() {
		double current = 0.0;
		current = ropeMotor.getOutputCurrent();
		return current;
	}

	
	/**
	 * Sets setSpeed to inputed speed
	 * @param speed
	 */
	
	public void move(double speed) {
		setSpeed(speed);
	}

	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new RopeClimbTrigger(Robot.oi.driverXbox));
	}

}
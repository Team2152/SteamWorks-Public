package org.usfirst.frc.team2152.robot.subsystems;

import org.usfirst.frc.team2152.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallManipulator extends Subsystem {

	private Victor ballShooter;
	private CANTalon ballSpinner;
	public BallManipulator(){
		ballShooter = new Victor(RobotMap.PWM_BALL_SHOOTER);
		ballShooter.setInverted(true);
		ballSpinner = new CANTalon(RobotMap.AGITATOR_CAN_Id);
		ballSpinner.setCurrentLimit(30);
		ballSpinner.EnableCurrentLimit(true);

	}
	
	
	public void setShooter(double speed){
		ballShooter.set(speed);
		
		/**
		 * This method sets the shooters speed.
		 * @param speed
		 */
	}
	
	public void setSpinner(double speed){
		ballSpinner.set(speed);
		
		/**
		 * This method sets the spinners speed.
		 * @param speed
		 */
	}
	
	
	// Put methods for controlling this subsystem
		// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}


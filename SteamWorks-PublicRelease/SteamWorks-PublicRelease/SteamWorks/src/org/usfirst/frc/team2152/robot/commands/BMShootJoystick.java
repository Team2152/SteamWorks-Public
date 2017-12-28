package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BMShootJoystick extends Command {
	Timer wdTimer;

	private final double chargeTime = 3;
	private final double spinnerSpeed = -.35; // .25

	public BMShootJoystick() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.ballManipulatorSubsystem);
		wdTimer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		wdTimer.reset();
		wdTimer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.ballManipulatorSubsystem.setShooter(0.75); // 0.75
		if (wdTimer.get() >= chargeTime) {
			Robot.ballManipulatorSubsystem.setSpinner(-0.45);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (!Robot.oi.operatorXbox.getRawButton(6)) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.ballManipulatorSubsystem.setShooter(0);
		Robot.ballManipulatorSubsystem.setSpinner(0);
		wdTimer.stop();
		wdTimer.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.ballManipulatorSubsystem.setShooter(0);
		Robot.ballManipulatorSubsystem.setSpinner(0);
		wdTimer.stop();
		wdTimer.reset();
	}
}

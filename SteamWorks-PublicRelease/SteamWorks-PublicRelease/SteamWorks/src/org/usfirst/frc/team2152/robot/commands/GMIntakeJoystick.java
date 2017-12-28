package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.OI;
import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GMIntakeJoystick extends Command {
	private boolean intakeLimitState;
	private boolean previousIntakeLimitState;
	private boolean upperLimitState;

	public GMIntakeJoystick() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		previousIntakeLimitState = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		intakeLimitState = Robot.gearManipulator.getIntakeLimit();
		upperLimitState = Robot.gearManipulator.getUpperLimit();
		//Auto Raise if gear is in GM
		if (Robot.oi.driverXbox.getRawAxis(OI.XBOX_RIGHT_TRIGGER) > 0.1
				|| Robot.oi.operatorXbox.getRawAxis(OI.XBOX_RIGHT_TRIGGER) > 0.1) {
			if (intakeLimitState != previousIntakeLimitState) {
				Robot.gearManipulator.intake(0);
				Robot.gearManipulator.up();
				previousIntakeLimitState = intakeLimitState;
			} else if (upperLimitState) {
				Robot.gearManipulator.pin();
				Robot.gearManipulator.latch();
				Robot.gearManipulator.unPin();
			} else {
				Robot.gearManipulator.intake(Robot.oi.driverXbox.getRawAxis(OI.XBOX_RIGHT_TRIGGER)
						+ Robot.oi.operatorXbox.getRawAxis(OI.XBOX_RIGHT_TRIGGER));
			}
		} else if (Robot.oi.driverXbox.getRawAxis(OI.XBOX_LEFT_TRIGGER) > 0
				|| Robot.oi.operatorXbox.getRawAxis(OI.XBOX_RIGHT_TRIGGER) > 0) {
			Robot.gearManipulator.intake(-0.3 * Robot.oi.driverXbox.getRawAxis(OI.XBOX_LEFT_TRIGGER)
					+ Robot.oi.operatorXbox.getRawAxis(OI.XBOX_RIGHT_TRIGGER));
		} else {
			Robot.gearManipulator.intake(0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
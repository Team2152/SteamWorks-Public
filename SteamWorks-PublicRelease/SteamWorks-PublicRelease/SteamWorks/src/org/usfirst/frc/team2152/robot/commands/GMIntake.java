package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GMIntake extends Command {
	private boolean intakeLimitState;
	private boolean previousIntakeLimitState;
	private boolean upperLimitState;
	private boolean previousUpperLimitState;
	private double speed;

	public GMIntake(double speed) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		previousIntakeLimitState = false;
		previousUpperLimitState = false;
		Robot.gearManipulator.unLatch();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intakeLimitState = Robot.gearManipulator.getIntakeLimit();
		upperLimitState = Robot.gearManipulator.getUpperLimit();

		if (speed > 0) {
			//Auto Raise if gear is in GM
			if (intakeLimitState != previousIntakeLimitState) {
				Robot.gearManipulator.intake(0);
				Robot.gearManipulator.up();
				previousIntakeLimitState = intakeLimitState;
				if (upperLimitState != previousUpperLimitState) {
					Robot.gearManipulator.pin();
					Robot.gearManipulator.latch();
					Robot.gearManipulator.unPin();
					previousUpperLimitState = upperLimitState;
				}
			} else {
				Robot.gearManipulator.intake(speed);
			}
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

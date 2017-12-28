package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchGear extends Command {
	boolean highGear = false;
	boolean newGear = false;

	public SwitchGear() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrainSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		highGear = Robot.driveTrainInfo.getHighGear();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (highGear) {
			Robot.driveTrainSubsystem.shiftLow();
		} else {
			Robot.driveTrainSubsystem.shiftHigh();
		}

		newGear = Robot.driveTrainInfo.getHighGear();

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (newGear != highGear) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

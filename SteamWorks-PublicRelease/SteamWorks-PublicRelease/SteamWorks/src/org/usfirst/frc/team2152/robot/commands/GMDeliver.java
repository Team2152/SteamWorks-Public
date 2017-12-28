package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GMDeliver extends Command {
	Timer watchdogTimer;

	public GMDeliver() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
		watchdogTimer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		watchdogTimer.reset();
		Robot.gearManipulator.pin();
		Robot.gearManipulator.unLatch();
		Timer.delay(0.05);
		watchdogTimer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.gearManipulator.setLift(-0.3);
		Robot.gearManipulator.expell(0.3);
		Robot.driveTrainSubsystem.arcadeDrive(0.5, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Robot.gearManipulator.getLowerLimit() || watchdogTimer.get() >= 2) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.gearManipulator.setLift(0);
		Robot.gearManipulator.intake(0);
		Robot.driveTrainSubsystem.tankDrive(0, 0);

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.gearManipulator.setLift(0);
		Robot.gearManipulator.intake(0);
		Robot.driveTrainSubsystem.tankDrive(0, 0);
	}
}

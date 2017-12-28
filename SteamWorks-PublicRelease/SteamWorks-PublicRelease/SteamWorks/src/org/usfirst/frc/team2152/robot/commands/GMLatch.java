package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GMLatch extends Command {

	public GMLatch() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gearManipulator.unLatch();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.gearManipulator.up();

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(Robot.gearManipulator.getUpperLimit()){
			return true;
		}else{
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.gearManipulator.pin();
		Robot.gearManipulator.latch();
		Robot.gearManipulator.unPin();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

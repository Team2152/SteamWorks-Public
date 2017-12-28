package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.OI;
import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RopeClimbTrigger extends Command {
	private Joystick joystick;

	public RopeClimbTrigger(Joystick joystick) {
		requires(Robot.ropeClimbSubsystem);
		this.joystick = joystick;
	}

	protected void initialize() {
	}

	protected void execute() {

		if (joystick.getRawButton(6)) {
			Robot.ropeClimbSubsystem.setSpeed(1);
		} else if (joystick.getRawButton(7)) {
			Robot.ropeClimbSubsystem.setSpeed(-.3);
		} else {
			Robot.ropeClimbSubsystem.setSpeed(0);
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.ropeClimbSubsystem.setSpeed(0);

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.ropeClimbSubsystem.setSpeed(0);
	}
}

package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BMShootAuto extends Command {
	Timer wdTimer;
	Timer runTimeTimer;

	private final double chargeTime = 3.5;
	private final double spinnerSpeed = .5;

	private double runTime = 0; // runTime cannot be less than chargeTime

	public BMShootAuto(double runTime) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.ballManipulatorSubsystem);
		wdTimer = new Timer();
		runTimeTimer = new Timer();

		this.runTime = runTime;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		wdTimer.reset();
		wdTimer.start();
		runTimeTimer.reset();
		runTimeTimer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		Robot.ballManipulatorSubsystem.setShooter(0.65); // 0.71
		if (wdTimer.get() >= chargeTime) {
			Robot.ballManipulatorSubsystem.setSpinner(-0.5);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (runTimeTimer.get() >= runTime) {
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
		runTimeTimer.stop();
		runTimeTimer.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.ballManipulatorSubsystem.setShooter(0);
		Robot.ballManipulatorSubsystem.setSpinner(0);
		wdTimer.stop();
		wdTimer.reset();
		runTimeTimer.stop();
		runTimeTimer.reset();
	}
}

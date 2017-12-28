package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.Robot;
import org.usfirst.frc.team2152.robot.utilities.PIDConstants;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearLiftMoveToAngle extends Command implements PIDOutput {
	PIDController pidGMLift;
	private double setpoint = 0;
	private double PIDOutput = 0;
	private boolean hold = false;
	private Timer timer;
	private double adaptedOutput = 0;
	private double errorIntegrated = 0;
	private double combinedOutput = 0;
	private double errorSquared = 0;
	private boolean bMoveRollers = false;
	private double feedForward = 0;

	public GearLiftMoveToAngle(double setpoint, boolean hold, boolean bMoveRollers) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearManipulator);
		this.setpoint = setpoint;
		this.hold = hold;
		this.bMoveRollers = bMoveRollers;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		errorIntegrated = 0;
		timer = new Timer();
		timer.start();
		timer.reset();
		pidGMLift = new PIDController(PIDConstants.GEAR_LIFT_Kp, PIDConstants.GEAR_LIFT_Ki, PIDConstants.GEAR_LIFT_Kd,
				(80 - setpoint) / 1000, Robot.gearManipulator.getEncoder(PIDSourceType.kDisplacement), this);
		pidGMLift.disable();
		pidGMLift.setOutputRange(PIDConstants.GEAR_LIFT_OUT_MIN, PIDConstants.GEAR_LIFT_OUT_MAX);
		pidGMLift.setAbsoluteTolerance(PIDConstants.GEAR_LIFT_TOLERANCE);
		pidGMLift.setSetpoint(setpoint);
		pidGMLift.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Augmented adaptive controller
		errorSquared = (pidGMLift.getError() * pidGMLift.getError());
		errorIntegrated = ((timer.get()) * errorSquared) + errorIntegrated;
		adaptedOutput = ((errorIntegrated * (PIDConstants.GEAR_ADAPTIVE_GAIN)) * pidGMLift.getError());
		if (Math.abs(pidGMLift.getError()) <= 1) {
			feedForward = 0.07;
			errorIntegrated = 0;
		} else {
			feedForward = 0;
		}
		
		combinedOutput = adaptedOutput + PIDOutput + feedForward;
		// Limits outputs to pre-set Min and Max
		if (combinedOutput < PIDConstants.GEAR_LIFT_OUT_MIN) {
			combinedOutput = PIDConstants.GEAR_LIFT_OUT_MIN;
		} else if (combinedOutput > PIDConstants.GEAR_LIFT_OUT_MAX) {
			combinedOutput = PIDConstants.GEAR_LIFT_OUT_MAX;
		}
		// Checks for switches
		if (Robot.gearManipulator.getUpperLimit() && combinedOutput > 0) {
			combinedOutput = 0;
		} else if (Robot.gearManipulator.getLowerLimit() && combinedOutput < 0) {
			combinedOutput = 0;
		}
		// Adds rollers if required
		Robot.gearManipulator.setLift(combinedOutput);
		if (bMoveRollers) {
			Robot.gearManipulator.intake(1);
		}
		System.out.println(combinedOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (hold) {
			return false;
		} else {
			if (Math.abs(pidGMLift.getError()) <= PIDConstants.GEAR_LIFT_TOLERANCE) {
				return true;
			} else {
				return false;
			}

		}

	}

	// Called once after isFinished returns true
	protected void end() {
		pidGMLift.disable();
		Robot.gearManipulator.setLift(0);
		Robot.gearManipulator.intake(0);

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		timer.stop();
		pidGMLift.disable();
		Robot.gearManipulator.setLift(0);
		Robot.gearManipulator.intake(0);
	}

	@Override
	public void pidWrite(double output) {
		
		PIDOutput = output;
	}
}
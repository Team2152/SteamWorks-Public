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
public class MoveByEncoder extends Command implements PIDOutput {
	double motorSpeed;
	PIDController contrL;
	PIDController contrR;
	double LeftDistance = 0;
	double RightDistance = 0;
	PIDController pidHH;
	double errorFromHeadingHH = 0.0;
	float setPointHH = 0.0f;
	private boolean clearBacklash = false;

	public MoveByEncoder(double leftDistance, double rightDistance, double speed, boolean clearBacklash) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrainSubsystem);
		this.clearBacklash = clearBacklash;
		motorSpeed = speed;
		LeftDistance = leftDistance;
		RightDistance = rightDistance;
		requires(Robot.navxSubsystem);

		// Setup Heading Hold PID

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pidHH = new PIDController(PIDConstants.HH_Kp, PIDConstants.HH_Ki, PIDConstants.HH_Kd,
				Robot.navxSubsystem.getAHRS(), this);
		pidHH.disable();
		pidHH.setInputRange(PIDConstants.HH_IN_MIN, PIDConstants.HH_IN_MAX);
		pidHH.setOutputRange(PIDConstants.HH_OUT_MIN, PIDConstants.HH_OUT_MAX);
		pidHH.setAbsoluteTolerance(PIDConstants.HH_TOLERANCE);
		pidHH.setContinuous(true);

		contrR = new PIDController(PIDConstants.ENCODER_DRIVE_HIGH_Kp, PIDConstants.ENCODER_DRIVE_HIGH_Ki,
				PIDConstants.ENCODER_DRIVE_HIGH_Kd, Robot.driveTrainSubsystem.getEncoderR(PIDSourceType.kDisplacement),
				e -> {
					Robot.driveTrainSubsystem.setRightSpeed(e + pidHH.get());
				});
		contrR.setAbsoluteTolerance(PIDConstants.ENCODER_DRIVE_HIGH_TOLERANCE);

		contrL = new PIDController(PIDConstants.ENCODER_DRIVE_HIGH_Kp, PIDConstants.ENCODER_DRIVE_HIGH_Ki,
				PIDConstants.ENCODER_DRIVE_HIGH_Kd, Robot.driveTrainSubsystem.getEncoderL(PIDSourceType.kDisplacement),
				e -> {
					Robot.driveTrainSubsystem.setLeftSpeed(-e - pidHH.get());
				});
		contrL.setAbsoluteTolerance(PIDConstants.ENCODER_DRIVE_HIGH_TOLERANCE);
		Robot.navxSubsystem.getAHRS().reset();
		setPointHH = Robot.navxSubsystem.getAHRS().getYaw();
		pidHH.enable();
		if (clearBacklash) {
			for (int time = 1; time <= 2; time++) {
				Robot.driveTrainSubsystem.arcadeDrive(0.5, 0);
				Robot.gearManipulator.intake(0.15);
				Timer.delay(0.1);
			}
		}
		
		Robot.gearManipulator.intake(0);
		Robot.driveTrainSubsystem.arcadeDrive(0, 0);

		if (Robot.driveTrainInfo.getHighGear()) {
			//Changes gain based on gear
			contrL.setPID(PIDConstants.ENCODER_DRIVE_HIGH_Kp, PIDConstants.ENCODER_DRIVE_HIGH_Ki,
					PIDConstants.ENCODER_DRIVE_HIGH_Kd);
			contrR.setPID(PIDConstants.ENCODER_DRIVE_HIGH_Kp, PIDConstants.ENCODER_DRIVE_HIGH_Ki,
					PIDConstants.ENCODER_DRIVE_HIGH_Kd);
		} else {
			contrL.setPID(PIDConstants.ENCODER_DRIVE_LOW_Kp, PIDConstants.ENCODER_DRIVE_LOW_Ki,
					PIDConstants.ENCODER_DRIVE_LOW_Kd);
			contrR.setPID(PIDConstants.ENCODER_DRIVE_LOW_Kp, PIDConstants.ENCODER_DRIVE_LOW_Ki,
					PIDConstants.ENCODER_DRIVE_LOW_Kd);
		}
		Robot.driveTrainSubsystem.resetAllEncoders();
		contrR.setSetpoint(RightDistance);
		contrL.setSetpoint(LeftDistance);
		contrL.setOutputRange(-motorSpeed, motorSpeed);
		contrR.setOutputRange(-motorSpeed, motorSpeed);
		contrR.enable();
		contrL.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if ((Math.abs(Robot.driveTrainSubsystem.getLeftDistance()) >= Math.abs(LeftDistance))
				&& (Math.abs(Robot.driveTrainSubsystem.getRightDistance()) >= Math.abs(RightDistance))) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		contrL.disable();
		contrR.disable();
		Robot.driveTrainSubsystem.setRightSpeed(0);
		Robot.driveTrainSubsystem.setLeftSpeed(0);

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		contrL.disable();
		contrR.disable();
		Robot.driveTrainSubsystem.setRightSpeed(0);
		Robot.driveTrainSubsystem.setLeftSpeed(0);
	}

	@Override
	public void pidWrite(double output) {
		

	}

}

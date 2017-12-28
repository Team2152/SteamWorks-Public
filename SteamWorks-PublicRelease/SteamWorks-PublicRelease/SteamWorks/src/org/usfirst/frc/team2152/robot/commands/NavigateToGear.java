package org.usfirst.frc.team2152.robot.commands;

import org.usfirst.frc.team2152.robot.Robot;
import org.usfirst.frc.team2152.robot.network.Vars;
import org.usfirst.frc.team2152.robot.utilities.PIDConstants;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class NavigateToGear extends Command implements PIDOutput {
	PIDController gearDistance;
	PIDController gearHeading;
	Timer watchdogTimer;
	double headingOutput;
	double distanceOutput;
	double distanceTolerance;
	double headingTolerance;
	double maxForwardSpeed;
	double maxTurnSpeed;
	double watchdogDistance;
	double toleranceWait;
	double theDistanceSetpoint = 14.5;
	private boolean reachedVisionLimit = false;
	private double visionLimit = 25;
	private double encoderAdjustment;

	/**
	 * Navigate to gear uses the Vision processing on the ODroids to navigate to
	 * the peg using the reflective tape, usable in both auto and teleOp
	 * 
	 * @param maxForwardSpeed
	 *            The maximum allowable speed for the distance PID.
	 * @param headingTolerance
	 *            The tolerance for the heading PID.
	 * @param distanceTolerance
	 *            The tolerance for the distance PID
	 * @param maxTurnSpeed
	 *            The maximum allowable speed for the heading PID
	 * @param waitTime
	 *            How long to wait for the watchdog timer.
	 * @param watchdogDistance
	 *            The distance at which to start the watchdog timer.
	 * @param encoderAdjustment
	 *            How far to move the robot after the PID cutoff.
	 * @param visionLimit
	 *            When to cut off the PID.
	 */
	public NavigateToGear(double maxForwardSpeed, double headingTolerance, double distanceTolerance,
			double maxTurnSpeed, double waitTime, double watchdogDistance, double encoderAdjustment,
			double visionLimit) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrainSubsystem);
		requires(Robot.LEDsubsystem);
		requires(Robot.gearManipulator);
		this.headingTolerance = headingTolerance;
		this.distanceTolerance = distanceTolerance;
		this.maxForwardSpeed = maxForwardSpeed;
		this.maxTurnSpeed = maxTurnSpeed;
		toleranceWait = waitTime;
		this.watchdogDistance = watchdogDistance;
		this.encoderAdjustment = encoderAdjustment;
		this.visionLimit = visionLimit;
		// System.out.println(" NavigateToGear Constructor Initialized");

		// LiveWindow.addActuator("Gear Distance", "Gear", gearDistance);
		// LiveWindow.addActuator("Gear Angle", "Gear", gearHeading);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		reachedVisionLimit = false;
		SmartDashboard.putBoolean("Watchdog Exit Gear Auto", false);
		Robot.steamworksDashboard.putWatchdogExitGearAuto(false);

		watchdogTimer = new Timer();
		watchdogTimer.reset();
		watchdogTimer.start();
		gearDistance = new PIDController(PIDConstants.GEAR_DRIVE_Kp, PIDConstants.GEAR_DRIVE_Ki,
				PIDConstants.GEAR_DRIVE_Kd, Robot.driveTrainSubsystem.getNetDistancePID(PIDSourceType.kDisplacement),
				this);
		gearDistance.setContinuous(false);
		gearDistance.disable();
		gearDistance.setAbsoluteTolerance(distanceTolerance);
		gearDistance.setOutputRange(-maxForwardSpeed, maxForwardSpeed);

		gearHeading = new PIDController(PIDConstants.AUTO_GEAR_LOW_Kp, PIDConstants.AUTO_GEAR_LOW_Ki,
				PIDConstants.AUTO_GEAR_LOW_Kd, Robot.driveTrainSubsystem.getNetXanglePID(PIDSourceType.kDisplacement),
				this);
		gearHeading.setContinuous(false);
		gearHeading.disable();

		gearHeading.setAbsoluteTolerance(headingTolerance);
		gearHeading.setOutputRange(-maxTurnSpeed, maxTurnSpeed);
		Robot.driveTrainSubsystem.shiftLow();
		gearDistance.setSetpoint(theDistanceSetpoint);
		gearHeading.setSetpoint(0);
		gearHeading.enable();
		gearDistance.enable();
		Robot.LEDsubsystem.setVoltage("on");

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.udp.getValue(Vars.Peg.Double.Distance) <= visionLimit) {
			if (reachedVisionLimit == false) {
				gearDistance.disable();
				gearHeading.disable();
				Robot.driveTrainSubsystem.resetAllEncoders();
				reachedVisionLimit = true;
			}
			// System.out.println(Robot.driveTrainSubsystem.getAverageDistance());
			Robot.driveTrainSubsystem.arcadeDrive(-0.35, 0);
		} else {
			Robot.driveTrainSubsystem.arcadeDrive(gearDistance.get(), -gearHeading.get());
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {

		boolean bWatchdogExit = (watchdogTimer.get() > toleranceWait);

		double averageDistance = Robot.driveTrainSubsystem.getAverageDistance();
		if (bWatchdogExit || (reachedVisionLimit && averageDistance >= encoderAdjustment)) {
			if (bWatchdogExit) {
				Robot.steamworksDashboard.putWatchdogExitGearAuto(true);
			}
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrainSubsystem.tankDrive(0, 0);
		gearHeading.disable();
		gearDistance.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.driveTrainSubsystem.tankDrive(0, 0);
		gearHeading.disable();
		gearDistance.disable();
	}

	@Override
	public void pidWrite(double output) {

	}
}

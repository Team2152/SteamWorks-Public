package org.usfirst.frc.team2152.robot;

import org.usfirst.frc.team2152.robot.auto.Baseline;
import org.usfirst.frc.team2152.robot.auto.BlueBoiler;
import org.usfirst.frc.team2152.robot.auto.CenterVision;
import org.usfirst.frc.team2152.robot.auto.JustShoot;
import org.usfirst.frc.team2152.robot.auto.LeftVision;
import org.usfirst.frc.team2152.robot.auto.RedBoiler;
import org.usfirst.frc.team2152.robot.auto.RightVision;
import org.usfirst.frc.team2152.robot.network.OdroidsCameraSettings;
import org.usfirst.frc.team2152.robot.network.UDPHandler;
import org.usfirst.frc.team2152.robot.network.UDPReceiver;
import org.usfirst.frc.team2152.robot.network.Vars;
import org.usfirst.frc.team2152.robot.subsystems.BallManipulator;
import org.usfirst.frc.team2152.robot.subsystems.Dashboard;
import org.usfirst.frc.team2152.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2152.robot.subsystems.NavX;
import org.usfirst.frc.team2152.robot.subsystems.PressureSensor;
import org.usfirst.frc.team2152.robot.subsystems.LEDAnalog;
import org.usfirst.frc.team2152.robot.subsystems.RopeClimb;
import org.usfirst.frc.team2152.robot.utilities.DriveTrainInfo;
import org.usfirst.frc.team2152.robot.subsystems.GearManipulator;
import org.usfirst.frc.team2152.robot.utilities.Gain;
import org.usfirst.frc.team2152.robot.utilities.Log;
import org.usfirst.frc.team2152.robot.utilities.NAVXPortMapping;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// === Create the Logger
	// First so that it can be
	// used
	public static final Log logger = new Log(true);
	public static OI oi;
	public static final OdroidsCameraSettings cameras = new OdroidsCameraSettings();
	Command autonomousCommand;
	public static final NavX navxSubsystem = new NavX();
	public static final GearManipulator gearManipulator = new GearManipulator();
	public static final BallManipulator ballManipulatorSubsystem = new BallManipulator();
	public static Dashboard steamworksDashboard = new Dashboard();
	public static final NAVXPortMapping navxPortMapping = new NAVXPortMapping();
	public static final PressureSensor pressureSensor = new PressureSensor();
	public static final DriveTrainInfo driveTrainInfo = new DriveTrainInfo();
	public static final Gain driveTrainJoystickGain = new Gain(Gain.PCT_100, Gain.DEFAULT_DEADBAND);
	public static final DriveTrain driveTrainSubsystem = new DriveTrain();
	public static final RopeClimb ropeClimbSubsystem = new RopeClimb();
	public static SendableChooser<Command> autoModeChooser = new SendableChooser<Command>();
	public static SendableChooser<Double> shooterSpeedChooser = new SendableChooser<Double>();
	public static LEDAnalog LEDsubsystem = new LEDAnalog();
	private UDPReceiver udpReceiver = new UDPReceiver(UDPReceiver.UDP_PORT);

	// This field will hold the UDP data. See UDPHandler javadocs (mouse over
	// it) for instructions on how to retrieve data.
	public static final UDPHandler udp = new UDPHandler();

	@Override
	public void robotInit() {
		oi = new OI();


		try {
			udpReceiver.setListener(udp);
			udpReceiver.start();
		} catch (Exception e) {

		}

		cameras.start();

		autoModeChooser = new SendableChooser<Command>();
		autoModeChooser.addDefault("No Autonomous", null);
		autoModeChooser.addObject("Center Vision", new CenterVision());
		autoModeChooser.addObject("Right", new RightVision());
		autoModeChooser.addObject("Left", new LeftVision());
		autoModeChooser.addObject("Baseline", new Baseline());
		autoModeChooser.addObject("Just Shoot", new JustShoot());
		autoModeChooser.addObject("Blue Shoot", new BlueBoiler());
		autoModeChooser.addObject("Red Shoot", new RedBoiler());
		steamworksDashboard.putAutoMode();

		steamworksDashboard.putCustomShooterSpeed(0.5);
		shooterSpeedChooser = new SendableChooser<Double>();
		shooterSpeedChooser.addObject("Shooter 100% Speed", 1.0);
		shooterSpeedChooser.addDefault("Shooter 50% Speed", 0.5);
		shooterSpeedChooser.addObject("Shooter Custom Speed", steamworksDashboard.getCustomShooterSpeed());
		steamworksDashboard.putBallShooterSpeed();

		/*
		 * IN CASE OF OLD DASHBOARD VALUES You can normally remove a value from
		 * the SmartDashboard by going into editable mode (Ctrl + E),
		 * right-clicking the value and click "Remove" Sometimes there are
		 * values that return but are not part of the code. In this case, the
		 * value has to be manually removed by deleting its value from the XML
		 * file that builds the dashboard.
		 */
		steamworksDashboard.putUDPInterrupted(false);
		steamworksDashboard.putDriveGain(1.0);
		steamworksDashboard.putEncoders(0, 0);
		steamworksDashboard.putEncoderReset();
		steamworksDashboard.putMatchTime();
		steamworksDashboard.putSetLast30Seconds(false);
		steamworksDashboard.putRecord(true);
		SmartDashboard.putNumber("Auto Agitator", 0);
		SmartDashboard.putNumber("Auto Flywheel", 0);
		SmartDashboard.putNumber("Tele Agitator", 0);
		SmartDashboard.putNumber("tele Flywheel", 0);

		cameras.setToDisabledMode();

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		cameras.setToDisabledMode();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		steamworksDashboard.putUDP(udpReceiver.isRunning());
		steamworksDashboard.putPegVision(udp.getValue(Vars.Peg.Double.XAngle), udp.getValue(Vars.Peg.Double.Distance));

		cameras.setToDisabledMode();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		cameras.setToAutoMode();

		// schedule the autonomous command (example)
		autonomousCommand = (Command) autoModeChooser.getSelected();
		Robot.driveTrainSubsystem.shiftLow();

		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		cameras.setToAutoMode();
		cameras.shouldRecord(false);

		steamworksDashboard.putMatchTime();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		cameras.setToAutoMode();
		cameras.shouldRecord(true);
		steamworksDashboard.putNavxAngle(navxSubsystem.getAHRS().getAngle());
		Robot.driveTrainSubsystem.shiftLow();
		Scheduler.getInstance().run();

		steamworksDashboard.putPegVision(udp.getValue(Vars.Peg.Double.XAngle), udp.getValue(Vars.Peg.Double.Distance));

		steamworksDashboard.putUDP(udpReceiver.isRunning());
		steamworksDashboard.putEncoders(driveTrainSubsystem.getRightDistance(), driveTrainSubsystem.getLeftDistance());
		steamworksDashboard.putMatchTime();

	}

	@Override
	public void teleopInit() {

		// This makes sure that the
		// autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		cameras.setToTeleMode();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		System.out.println(Robot.udp.getValue(Vars.Peg.Double.Distance));
		steamworksDashboard.putUDP(udpReceiver.isRunning());
		steamworksDashboard.putNavxAngle(navxSubsystem.getAHRS().getAngle());
		steamworksDashboard.putEncoders(driveTrainSubsystem.getRightDistance(), driveTrainSubsystem.getLeftDistance());
		steamworksDashboard.putGMAngle(Robot.gearManipulator.getEncoderAngle());
		steamworksDashboard.putGMLowerLimit(gearManipulator.getLowerLimit());
		steamworksDashboard.putGMIntakeLimit(gearManipulator.getIntakeLimit());
		steamworksDashboard.putGMUpperLimit(gearManipulator.getUpperLimit());

		steamworksDashboard.putDriveGear(driveTrainInfo.getHighGear());
		steamworksDashboard.putAmperage(driveTrainSubsystem.totalPowerDraw());
		steamworksDashboard.putMatchTime();

		if (steamworksDashboard.getMatchTime() <= 30) {
			steamworksDashboard.putLast30Seconds();
		}

		if (steamworksDashboard.getResetAllEncoders() == true) {
			driveTrainSubsystem.resetAllEncoders();
			steamworksDashboard.putEncoderReset();
		} else if (steamworksDashboard.getResetLEncoder() == true) {
			driveTrainSubsystem.resetLEncoder();
			steamworksDashboard.putEncoderReset();
		} else if (steamworksDashboard.getResetREncoder() == true) {
			driveTrainSubsystem.resetREncoder();
			steamworksDashboard.putEncoderReset();
		}

	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}

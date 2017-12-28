package org.usfirst.frc.team2152.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2152.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS Test
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand()); TEST
	public final static int POV_0 = 0;
	public final static int POV_45 = 45;
	public final static int POV_90 = 90;
	public final static int POV_135 = 135;
	public final static int POV_180 = 180;
	public final static int POV_225 = 225;
	public final static int POV_270 = 270;
	public final static int POV_315 = 315;

	public final static int OPERATOR_XBOX = 1;
	public final static int DRIVER_XBOX = 0;

	// === Axis ids; they are the same for each joystick
	public final static int XBOX_LEFT_XAXIS = 0;
	public final static int XBOX_LEFT_YAXIS = 1;
	public final static int XBOX_RIGHT_XAXIS = 4;
	public final static int XBOX_RIGHT_YAXIS = 5;

	public final static int XBOX_LEFT_TRIGGER = 2;
	public final static int XBOX_RIGHT_TRIGGER = 3;
	// public final static int SPEED_STICK = 999999; // Deoderant...always wear
	// it.

	public Joystick operatorXbox;
	private Button oButtonA;
	private Button oButtonB;
	private Button oButtonX;
	private Button oButtonY;
	private Button oButtonBumpL;
	private Button oButtonBumpR;
	private Button oButtonBack;
	private Button oButtonStart;
	private Button oButtonLClick;
	private Button oButtonRClick;
	final private int oButtonAid = 1;
	final private int oButtonBid = 2;
	final private int oButtonXid = 3;
	final private int oButtonYid = 4;
	final private int oButtonBumpLid = 5;
	final private int oButtonBumpRid = 6;
	final private int oButtonBackid = 7;
	final private int oButtonStartid = 8;
	final private int oButtonLClickid = 9;
	final private int oButtonRClickid = 10;

	public Joystick driverXbox;
	private Button dButtonA;
	private Button dButtonB;
	private Button dButtonX;
	private Button dButtonY;
	private Button dButtonBumpL;
	private Button dButtonBumpR;
	private Button dButtonBack;
	private Button dButtonStart;
	private Button dButtonLClick;
	private Button dButtonRClick;
	final private int dButtonAid = 1;
	final private int dButtonBid = 2;
	final private int dButtonXid = 3;
	final private int dButtonYid = 4;
	final private int dButtonBumpLid = 5;
	final private int dButtonBumpRid = 6;
	final private int dButtonBackid = 7;
	final private int dButtonStartid = 8;
	final private int dButtonLClickid = 9;
	final private int dButtonRClickid = 10;

	public OI() {
		// Setup driver joystick
		try {
			driverXbox = new Joystick(DRIVER_XBOX);
			dButtonA = new JoystickButton(driverXbox, dButtonAid);
			dButtonB = new JoystickButton(driverXbox, dButtonBid);
			dButtonX = new JoystickButton(driverXbox, dButtonXid);
			dButtonY = new JoystickButton(driverXbox, dButtonYid);
			dButtonBumpL = new JoystickButton(driverXbox, dButtonBumpLid);
			dButtonBumpR = new JoystickButton(driverXbox, dButtonBumpRid);
			dButtonBack = new JoystickButton(driverXbox, dButtonBackid);
			dButtonStart = new JoystickButton(driverXbox, dButtonStartid);
			dButtonLClick = new JoystickButton(driverXbox, dButtonLClickid);
			dButtonRClick = new JoystickButton(driverXbox, dButtonRClickid);
			setupDriverXboxButtons();
		} catch (Exception e) {
			Robot.logger.console("OI: Unable to setup driver joystick: " + e.toString());
		}

		try {
			operatorXbox = new Joystick(OPERATOR_XBOX);
			oButtonA = new JoystickButton(operatorXbox, oButtonAid);
			oButtonB = new JoystickButton(operatorXbox, oButtonBid);
			oButtonX = new JoystickButton(operatorXbox, oButtonXid);
			oButtonY = new JoystickButton(operatorXbox, oButtonYid);
			oButtonBumpL = new JoystickButton(operatorXbox, oButtonBumpLid);
			oButtonBumpR = new JoystickButton(operatorXbox, oButtonBumpRid);
			oButtonBack = new JoystickButton(operatorXbox, oButtonBackid);
			oButtonStart = new JoystickButton(operatorXbox, oButtonStartid);
			oButtonLClick = new JoystickButton(operatorXbox, oButtonLClickid);
			oButtonRClick = new JoystickButton(operatorXbox, oButtonRClickid);
			setupOperatorButtons();
		} catch (Exception e) {
			Robot.logger.console("OI: Unable to setup operator joystick: " + e.toString());
		}
	}

	public void setupOperatorButtons() {
		oButtonA.whenReleased(new GMLatch());
		oButtonB.whenReleased(new GMClear());
		oButtonY.whenReleased(new GMReset());
		oButtonX.whenReleased(new GMDeliver());
		oButtonBumpR.whenPressed(new BMShootJoystick());
		
	}

	public void setupDriverXboxButtons() {
		dButtonY.whenReleased(new GMLatch());
		dButtonB.whenReleased(new GMClear());
		dButtonA.whenReleased(new GMReset());
		dButtonX.whenReleased(new GMDeliver());
		dButtonStart.whenReleased(new GMAutoDeliverInTele());
		
		dButtonBumpR.whenPressed(new setLEDRing("plink"));
		dButtonBumpR.whenReleased(new setLEDRing("off"));
		dButtonBumpL.whenReleased(new SwitchGear());
		

	}

}

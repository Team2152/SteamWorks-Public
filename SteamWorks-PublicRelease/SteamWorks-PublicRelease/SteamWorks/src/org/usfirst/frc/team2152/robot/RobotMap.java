package org.usfirst.frc.team2152.robot;

import org.usfirst.frc.team2152.robot.utilities.NAVXPortMapping;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

	// === Drive Train subsystem ids
	public static final int LEFT_DRIVE_1_CAN_Id = 1;
	public static final int LEFT_DRIVE_2_CAN_Id = 2;
	public static final int LEFT_DRIVE_3_CAN_Id = 3;

	public static final int RIGHT_DRIVE_1_CAN_Id = 4;
	public static final int RIGHT_DRIVE_2_CAN_Id = 5;
	public static final int RIGHT_DRIVE_3_CAN_Id = 6;

	public static final int ROPE_CLIMBER_CAN_Id = 7;

	public static final int AGITATOR_CAN_Id = 9;
	public static final int SHOOTER_CAN_Id = 9;

	public static final int RIGHT_ENCODER_A_DI = 0;
	public static final int RIGHT_ENCODER_B_DI = 1;
	public static final int LEFT_ENCODER_A_DI = 2;
	public static final int LEFT_ENCODER_B_DI = 3;

		// === DIO ids
	public static final int DIO_0 = 0; // ENCODER 1A
	public static final int DIO_1 = 1; // ENCODER 1B
	public static final int DIO_2 = 2; // ENCODER 2A
	public static final int DIO_3 = 3; // ENCODER 2B
	public static final int GEAR_LS_BOTTOM = 4; // 
	public static final int GEAR_LS_TOP = 5;
	public static final int GEAR_LS_INTAKE = 7; // Gear LS Intake
	public static final int GEAR_ENCODER_A = 8; // Gear Encoder A
	public static final int GEAR_ENCODER_B = 9; // Gear Encoder B
	
		// === PWM ids
	public static final int PWM_0 = 0; // Rope Pin Servo
	public static final int PWM_1 = 1; // Rope Clamp Servo
	public static final int PWM_2 = 2; // Gear Servo Right
	public static final int PWM_3 = 3; 
	public static final int PWM_BALL_SHOOTER = 4;
	public static final int PWM_6 = 6;
	public static final int PWM_7 = 7;
	public static final int PWM_8 = 8;
	public static final int PWM_9 = 9;
	
		//NAVX Port IDS
	public static final int NAVX_DIO_0 = NAVXPortMapping.getNAVXPort(NAVXPortMapping.DIO, 0);
	
		//=== Drive Train subsystem ids
	public static final int motorFrontRightId          = 1;
	public static final int motorFrontLeftId           = 2;
	public static final int motorRearLeftId            = 3;
	public static final int motorRearRightId           = 4;

	public static final int ropeMotorID                = 7;
	
		//=== PDP Positions
	public static final int PDP_POSITION_10_GM_LIFT	   = 10;
	
}

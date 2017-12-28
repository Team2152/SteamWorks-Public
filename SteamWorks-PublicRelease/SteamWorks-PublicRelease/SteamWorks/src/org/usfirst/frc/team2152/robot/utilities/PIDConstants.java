package org.usfirst.frc.team2152.robot.utilities;

public class PIDConstants {

	// Trying to keep PID values in central place so that we can update them as
	// needed.

	// === Drive Train Heading Hold PID Gains (retune when robot is present)
	public final static double HH_Kp = 0.015;
	public final static double HH_Ki = 0.001;
	public final static double HH_Kd = 0.0;
	public final static double HH_TOLERANCE = 2.0;
	public final static float HH_IN_MIN = -180.0f; // Kauai Labs - navx sample
													// code uses float
	public final static float HH_IN_MAX = 180.0f; // Kauai Labs - navx sample
													// code uses float
	public final static double HH_OUT_MIN = -0.5;
	public final static double HH_OUT_MAX = 0.5;

	// === Pre-Canned turns PID gains (retune when robot is present)
	public final static double PCT_Kp = .015;
	public final static double PCT_Ki = 0.0003;    
	public final static double PCT_Kd = 0;
	public final static double PCT_TOLERANCE = 4;
	public final static float PCT_IN_MIN = -180.0f;
	public final static float PCT_IN_MAX = 180.0f;

	// === Pre-Canned turns PID gains (retune when robot is present)
	public final static double SPEED_MATCH_Kp = 0.01;
	public final static double SPEED_MATCH_Ki = 0.0;
	public final static double SPEED_MATCH_Kd = 0.0;
	public final static double SPEED_MATCH_OUT_MIN = -1;
	public final static double SPEED_MATCH_OUT_MAX = 1;

	public final static double ENCODER_DRIVE_HIGH_Kp = 0.1;
	public final static double ENCODER_DRIVE_HIGH_Ki = 0.0015;
	public final static double ENCODER_DRIVE_HIGH_Kd = 0.0;
	public final static double ENCODER_DRIVE_HIGH_TOLERANCE = 2.0;
	public final static double ENCODER_DRIVE_HIGH_OUT_MIN = -0.5;
	public final static double ENCODER_DRIVE_HIGH_OUT_MAX = 0.5;

	public final static double ENCODER_DRIVE_LOW_Kp = 0.06;
	public final static double ENCODER_DRIVE_LOW_Ki = 0.0025;
	public final static double ENCODER_DRIVE_LOW_Kd = 0.0;
	public final static double ENCODER_DRIVE_LOW_TOLERANCE = 3.0;
	public final static double ENCODER_DRIVE_LOW_OUT_MIN = -0.5;
	public final static double ENCODER_DRIVE_LOW_OUT_MAX = 0.5;

	public final static double AUTO_GEAR_LOW_Kp = 0.05; // 0.045
	public final static double AUTO_GEAR_LOW_Ki = 0.00; // 0.005
	public final static double AUTO_GEAR_LOW_Kd = 0.0; // 0.0
	public final static double AUTO_GEAR_LOW_TOLERANCE = 1.0; // 1.0

	public final static double GEAR_DRIVE_Kp = 0.065; // 0.065
	public final static double GEAR_DRIVE_Ki = 0.00; // 0.005
	public final static double GEAR_DRIVE_Kd = 0.01; // 0.0
	public final static double GEAR_DRIVE_TOLERANCE = 3.0; // 3.0
	public final static double GEAR_DRIVE_OUT_MIN = -0.5; // -0.5
	public final static double GEAR_DRIVE_OUT_MAX = 0.5; // 0.5

	public final static double GEAR_LIFT_Kp = 0.013; // 0.009
	public final static double GEAR_LIFT_Ki = 0; // 0.001
	public final static double GEAR_LIFT_Kd = 0; // .015
	public final static double GEAR_LIFT_TOLERANCE = 1.0; // 1.0
	public final static double GEAR_LIFT_OUT_MIN = -0.3; // -0.5
	public final static double GEAR_LIFT_OUT_MAX = 0.3; // 0.5
	public final static double GEAR_ADAPTIVE_GAIN = 0.00001;

}

package org.usfirst.frc.team2152.robot.subsystems;

import org.usfirst.frc.team2152.robot.RobotMap;
import org.usfirst.frc.team2152.robot.commands.GMIntakeJoystick;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	
 */
public class GearManipulator extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private final double DISTANCE_PER_PULSE = 0.3515625;

	private Victor gearLift;

	private Encoder gearEncoder;
	private DoubleSolenoid latch;
	private DigitalInput gearLimitTop;
	public DigitalInput gearLimitBottom;
	private CANTalon gearIntake;
	private DigitalInput gearLimitIntake;

	public GearManipulator() {

		latch = new DoubleSolenoid(2, 3);

		gearLift = new Victor(0);
		gearLift.setInverted(true);

		// Encoder
		gearEncoder = new Encoder(RobotMap.GEAR_ENCODER_A, RobotMap.GEAR_ENCODER_B);// 1024
		// counts
		// per
		// revolution
		gearEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		gearEncoder.setReverseDirection(true);

		gearIntake = new CANTalon(8);
		gearIntake.setCurrentLimit(40);
		gearIntake.EnableCurrentLimit(true);

		gearLimitIntake = new DigitalInput(RobotMap.GEAR_LS_INTAKE);
		// Limit Switches

		gearLimitTop = new DigitalInput(RobotMap.GEAR_LS_TOP);
		gearLimitBottom = new DigitalInput(RobotMap.GEAR_LS_BOTTOM);
	}

	/**
	 * This method is used to set the speed of the lift a positive number will
	 * move it up, and a negative one will move it down. This method will not
	 * respond to the limit switches.
	 * 
	 * @param speed
	 *            How fast the lift will move, values should be inbetween -1 and
	 *            1
	 */
	public void setLift(double speed) {
		gearLift.set(speed);
	}

	/**
	 * Get the current status of the Encoder on the GM.
	 * 
	 * @return The angle of the GM in degrees
	 */
	public double getEncoderAngle() {
		return gearEncoder.getDistance();
	}

	/**
	 * Reset the Encoder
	 */
	public void resetEncoder() {
		gearEncoder.reset();
	}

	/**
	 * Returns the state of the limit switch if it is True the GM is against the
	 * switch.
	 * 
	 * @return The state of the upper limit switch.
	 */
	public boolean getUpperLimit() {
		return !gearLimitTop.get();
	}

	/**
	 * Returns the state of the limit switch if it is True the GM is against the
	 * switch.
	 * 
	 * @return The state of the lower limit switch.
	 */
	public boolean getLowerLimit() {
		boolean bAtLowerLimit = !gearLimitBottom.get();
		if (bAtLowerLimit == true)
			gearEncoder.reset();
		return bAtLowerLimit;
	}

	/**
	 * This is to be used as PID Source
	 * 
	 * @param type
	 *            the type of pid either kDistance or kRate
	 * @return The encoder on the GM
	 */
	public Encoder getEncoder(PIDSourceType type) {
		gearEncoder.setPIDSourceType(type);
		return gearEncoder;
	}

	/**
	 * Moves the GM to an Upper Limit, it will stop when it hits the switch.
	 */
	public void up() {
		if (!getUpperLimit()) {
			setLift(0.35);
		} else {
			setLift(0);
		}
	}

	/**
	 * Holds the GM against the Upper Limit.
	 */
	public void pin() {
		setLift(0.05);
	}

	/**
	 * Activates the latch to hold the GM in place.
	 */
	public void latch() {
		latch.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Retracts the latch in the GM
	 */
	public void unLatch() {
		latch.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * Sets the GM lift to 0.
	 */
	public void unPin() {
		setLift(0);
	}

	/**
	 * Moves the GM down while expelling balls that are underneath the GM.
	 */
	public void clearLow() {
		if (!getIntakeLimit()) {
			intake(1);
		} else {
			intake(0);
		}
		setLift(-0.1);
	}

	/**
	 * Sets the speed of the intake rollers (does not check the limit switch)
	 * 
	 * @param speed
	 *            A number between -1 and 1 positive is in, negative is out.
	 */
	public void intake(double speed) {
		if (getIntakeLimit() && speed > 0) {
			gearIntake.set(0);
		} else {
			gearIntake.set(speed);
		}
	}
	
	/**
	 * Sets the speed of the intake motor to the inputted speed multiplied by -1
	 * @param speed
	 */
	public void expell(double speed) {
		gearIntake.set(-Math.abs(speed));

	}

	/**
	 * Get the current value of the switch.
	 * 
	 * @return true if the switch is hit.
	 */
	public boolean getIntakeLimit() {
		return !gearLimitIntake.get();
	}

	/**
	 * Gets current of Intake Motor
	 * @return double intake current
	 */
	public double getIntakeCurrent(){
		return gearIntake.getOutputCurrent();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new GMIntakeJoystick());


	}
}

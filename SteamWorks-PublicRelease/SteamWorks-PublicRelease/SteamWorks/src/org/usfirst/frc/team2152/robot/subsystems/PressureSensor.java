package org.usfirst.frc.team2152.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PressureSensor extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public final static String PRESSURE_SENSOR_LABEL = "Current Pressure: ";
	public final static int AI_ID = 0;
	private AnalogInput pressureSensor = null;
	
	public PressureSensor() {
		pressureSensor = new AnalogInput(AI_ID);
	}
	
	//Returns pressure if sensor not null; Otherwise 0.0
	/**
	 * Get the pressure of the pneumatic system
	 * @return the pressure in psi
	 */
	public double getPressure() {
		double psi = 0.0;
		if (pressureSensor != null) {
			//part number AM-3219: logic found on andymark for this sensor; Note: labview example (begin/teleop)
			psi = (50.0 * pressureSensor.getVoltage()) - 25.0;
		}
		return psi;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}


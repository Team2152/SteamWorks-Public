package org.usfirst.frc.team2152.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDAnalog extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private AnalogOutput arduinoPort = new AnalogOutput(0);
	
	double off = 0;
	double plink = 10.0/6;
	double bload = 14.0/6;
	double rload = 17.5/6;
	double dload = 21.0/6;
	double on = 5;
	
	/**
	 * Sets the voltage of the arduino port to <code> mode</code>.
	 * @param mode (String)
	 */
	public void setVoltage(String mode){
		switch(mode){
			case("off"):
				arduinoPort.setVoltage(off);
				break;
			case("on"):
				arduinoPort.setVoltage(on);
				break;
			case("plink"):
				arduinoPort.setVoltage(plink);
				break;
			case("load red"):
				arduinoPort.setVoltage(rload);
				break;
			case("load blue"):
				arduinoPort.setVoltage(bload);
				break;
			case("load"):
				arduinoPort.setVoltage(dload);
				break;
			default:
				arduinoPort.setVoltage(off);
				break;
				
		}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}


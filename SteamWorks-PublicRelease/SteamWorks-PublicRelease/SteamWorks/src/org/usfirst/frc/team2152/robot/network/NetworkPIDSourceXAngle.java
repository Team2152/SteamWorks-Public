package org.usfirst.frc.team2152.robot.network;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class NetworkPIDSourceXAngle implements PIDSource {
	private PIDSourceType pidSourceXAngle = PIDSourceType.kDisplacement;

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		//
		this.pidSourceXAngle = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSourceXAngle;

	}

	@Override
	public double pidGet() {
		if (pidSourceXAngle == PIDSourceType.kDisplacement) {
			return Robot.udp.getValue(Vars.Peg.Double.XAngle);
		} else {
			return Robot.udp.getValue(Vars.Peg.Double.XAngle);
		}
	}

}

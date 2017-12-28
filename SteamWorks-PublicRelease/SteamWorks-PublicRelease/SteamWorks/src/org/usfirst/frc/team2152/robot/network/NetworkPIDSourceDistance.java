package org.usfirst.frc.team2152.robot.network;

import org.usfirst.frc.team2152.robot.Robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class NetworkPIDSourceDistance implements PIDSource {
	private PIDSourceType pidSourceDistance = PIDSourceType.kDisplacement;

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSourceDistance = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSourceDistance;

	}

	@Override
	public double pidGet() {
		if (pidSourceDistance == PIDSourceType.kDisplacement) {
			return Robot.udp.getValue(Vars.Peg.Double.Distance);
		} else {
			return Robot.udp.getValue(Vars.Peg.Double.Distance);
		}
	}

}

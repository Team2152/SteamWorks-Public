package org.usfirst.frc.team2152.robot.auto;

import org.usfirst.frc.team2152.robot.Robot;
import org.usfirst.frc.team2152.robot.commands.GMDeliver;
import org.usfirst.frc.team2152.robot.commands.GMLatch;
import org.usfirst.frc.team2152.robot.commands.MoveByEncoder;
import org.usfirst.frc.team2152.robot.commands.NavigateToGear;
import org.usfirst.frc.team2152.robot.commands.PreCannedTurn;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LeftVision extends CommandGroup {

	public LeftVision() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.

		Alliance alliance = DriverStation.getInstance().getAlliance();
		addSequential(new GMLatch());

		Robot.driveTrainSubsystem.shiftLow();

		if (alliance == Alliance.Red) {
			addSequential(new MoveByEncoder(65, 65, 0.75, true));
		} else {
			addSequential(new MoveByEncoder(57, 57, 0.75, true));
		}

		SmartDashboard.putString("AUTOSTATUS: ", "FINISHED FORWARD");
		addSequential(new PreCannedTurn(55, 0.5, true));
		addSequential(new NavigateToGear(0.75, 2, 2, 0.45, 4, 10, 8, 25));
		addSequential(new GMDeliver());
		addSequential(new MoveByEncoder(-20, -20, 0.75, false));
		addSequential(new PreCannedTurn(-55, 0.5, false));
		addParallel(new GMLatch());
		addSequential(new MoveByEncoder(150, 150, 0.75, false));
	}
}

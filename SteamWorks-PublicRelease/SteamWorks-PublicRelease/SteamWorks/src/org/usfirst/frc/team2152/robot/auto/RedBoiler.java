package org.usfirst.frc.team2152.robot.auto;

import org.usfirst.frc.team2152.robot.Robot;
import org.usfirst.frc.team2152.robot.commands.BMShootAuto;
import org.usfirst.frc.team2152.robot.commands.GMLatch;
import org.usfirst.frc.team2152.robot.commands.MoveByEncoder;
import org.usfirst.frc.team2152.robot.commands.PreCannedTurn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RedBoiler extends CommandGroup {

	public RedBoiler() {
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
		Robot.driveTrainSubsystem.shiftLow();
		addSequential(new GMLatch());
		addSequential(new BMShootAuto(10));
		addSequential(new PreCannedTurn(60, 0.5, true));
		addSequential(new MoveByEncoder(50, 50, 0.75, false));
	}
}

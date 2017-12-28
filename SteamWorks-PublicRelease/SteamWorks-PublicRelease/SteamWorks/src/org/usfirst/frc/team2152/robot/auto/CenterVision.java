package org.usfirst.frc.team2152.robot.auto;

import org.usfirst.frc.team2152.robot.Robot;
import org.usfirst.frc.team2152.robot.commands.GMDeliver;
import org.usfirst.frc.team2152.robot.commands.GMLatch;
import org.usfirst.frc.team2152.robot.commands.MoveByEncoder;
import org.usfirst.frc.team2152.robot.commands.NavigateToGear;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterVision extends CommandGroup {

    public CenterVision() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	Robot.driveTrainSubsystem.shiftLow();
    	addSequential(new MoveByEncoder(0, 0, 0, true));
    	addSequential(new GMLatch());
		addSequential(new NavigateToGear(0.75, 2, 2, 0.45, 4, 10, 9, 25));
		addSequential(new GMDeliver());
		addSequential(new MoveByEncoder(-20, -20, 0.75, false));
		addSequential(new GMLatch());
		
    }
}

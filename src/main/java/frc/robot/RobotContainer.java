package frc.robot;

import java.util.List;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.core.util.oi.SmartController;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.SwerveDrive;
import frc.robot.commands.SwerveJoystickCmd;
import frc.robot.commands.SwerveTurn;
import frc.robot.subsystems.SwerveSubsystem;

public class RobotContainer {

        private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();

        private final XboxController driverJoytick = new XboxController(OIConstants.kDriverControllerPort);
        private final SmartController driver = new SmartController(0);
        public RobotContainer() {
                swerveSubsystem.setDefaultCommand(new SwerveJoystickCmd(
                                swerveSubsystem,
                                () -> driver.getLeftY(),
                                () -> - driver.getLeftX(),
                                () -> driver.getRightX(),
                                () -> !driver.getAButton().getAsBoolean()));

                configureButtonBindings();
        }

        private void configureButtonBindings() {

                driver.whileRightBumper(new SwerveTurn(swerveSubsystem));
                driver.whileLeftBumper(new SwerveDrive(swerveSubsystem));
        }

        public Command getAutonomousCommand() {
                return null;
        }
}

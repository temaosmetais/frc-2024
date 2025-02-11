package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.util.pid.Gains;
import frc.core.util.pid.TalonVelocity;
import frc.robot.constants.ShooterConstants;

public class Shooter extends SubsystemBase {

	private static final int NEUTRAL_SENSOR_VELOCITY = Math.abs(200);
	private static final int SAFE_REVERSE_SENSOR_VELOCITY = -1200;
	private final WPI_TalonSRX motor;
	private final TalonVelocity shooterPID;

	public Shooter() {
		this.motor = new WPI_TalonSRX(ShooterConstants.motorPort);
		this.motor.setNeutralMode(NeutralMode.Coast);

		this.shooterPID = new TalonVelocity(
				this.motor,
				ShooterConstants.isMotorInverted,
				ShooterConstants.isSensorPhase,
				new Gains(
						ShooterConstants.PID.kPVelocity,
						ShooterConstants.PID.kIVelocity,
						ShooterConstants.PID.kDVelocity,
						ShooterConstants.PID.kFVelocity,
						ShooterConstants.PID.kIZoneVelocity,
						ShooterConstants.PID.kPeakOutputVelocity));

	}

	public void set(double speed) {
		this.motor.set(ControlMode.PercentOutput, speed);
	}

	public void setRPM(double rpm) {
		this.shooterPID.setRPM(
				rpm,
				ShooterConstants.PID.dutyCycle);
	}

	public void setVelocityMetersPerSecond(double velocityMetersPerSecond) {
		this.shooterPID.setVelocityMetersPerSecond(
				velocityMetersPerSecond,
				ShooterConstants.PID.dutyCycle,
				ShooterConstants.wheelRadius);
	}

	public boolean atSettedVelocity() {
		return this.shooterPID.atSettedVelocity();
	}

	public boolean isSafetyShoot() {
		return this.shooterPID.getSelectedSensorVelocity() >= SAFE_REVERSE_SENSOR_VELOCITY;
	}

	public boolean isShooterMoving() {
		return Math.abs(this.shooterPID.getSelectedSensorVelocity()) > NEUTRAL_SENSOR_VELOCITY;
	}

	//210 > 200
	// 250
	// 
	

	public void stop() {
		this.set(0);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("error", shooterPID.getClosedLoopError());
		SmartDashboard.putNumber("selected sensor velocity", this.shooterPID.getSelectedSensorVelocity());
		SmartDashboard.putBoolean("isSettedVelocity", this.atSettedVelocity());
	}
}
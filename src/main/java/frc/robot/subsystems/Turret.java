// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
  private TalonFX motor_0 = new TalonFX(25);
  private TalonFX motor_1 = new TalonFX(26);
  private TalonSRX motor_turn = new TalonSRX(12);

  /** Creates a new ExampleSubsystem. */
  public Turret() {
    motor_0.setInverted(true);
    motor_1.setInverted(false);
  }

  public void setShooterVelocity(double vel) {
    motor_0.set(ControlMode.PercentOutput, vel);
    motor_1.set(ControlMode.PercentOutput, vel);
  }

  public void setTurnVelocity(double vel) {
    motor_turn.set(ControlMode.PercentOutput, vel);
  }

  public void log() {
    SmartDashboard.putNumber("turret pos", motor_turn.getSelectedSensorPosition());
  }
}

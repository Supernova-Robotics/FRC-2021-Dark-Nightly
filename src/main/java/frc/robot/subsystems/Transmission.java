// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Transmission extends SubsystemBase {
  private VictorSPX motor_0 = new VictorSPX(13);
  private VictorSPX motor_1 = new VictorSPX(11);

  /** Creates a new ExampleSubsystem. */
  public Transmission() {}

  public void setVelocity(double vel) {
    motor_0.set(ControlMode.PercentOutput, vel);
    // motor_1.set(ControlMode.PercentOutput, vel);
    // motor_2.set(ControlMode.PercentOutput, vel);
  }

  public void log() {
    
  }
}

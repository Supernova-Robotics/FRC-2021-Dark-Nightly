// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private Solenoid intake_solenoid = new Solenoid(0);
  private VictorSPX motor_0 = new VictorSPX(14);

  /** Creates a new ExampleSubsystem. */
  public Intake() {
    motor_0.setInverted(true);
  }

  public void collect(double vel) {
    motor_0.set(ControlMode.PercentOutput, vel);
  }

  public void pull() {
    intake_solenoid.set(false);
  }
  
  public void push() {
    intake_solenoid.set(true);
  }

  public void log() {
    
  }
}

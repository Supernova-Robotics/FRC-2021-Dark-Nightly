// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Chassis extends SubsystemBase {
  private TalonFX motor_0 = new TalonFX(20);
  private TalonFX motor_1 = new TalonFX(21);
  private TalonFX motor_2 = new TalonFX(22);
  private TalonFX motor_3 = new TalonFX(23);

  public int gear = 0;

  private double[] k_y = {0.08, 0.2, 0.5, 1};
  private double[] k_z = {0.1, 0.2, 0.3, 0.5};


  /** Creates a new ExampleSubsystem. */
  public Chassis() {
    motor_0.config_kP(0, 0.1, 1000);
    motor_2.config_kP(0, 0.1, 1000);

    motor_0.setSelectedSensorPosition(0);
    motor_2.setSelectedSensorPosition(0);

    motor_0.setInverted(false);
    motor_1.setInverted(false);
    motor_1.follow(motor_0);
    motor_2.setInverted(true);
    motor_3.setInverted(true);
    motor_3.follow(motor_2);
  }

  public double getPosY() {
    return .5 * (motor_0.getSelectedSensorPosition() + motor_2.getSelectedSensorPosition());
  }
  
  public double getPosZ() {
    return .5 * (-motor_0.getSelectedSensorPosition() + motor_2.getSelectedSensorPosition());
  }

  public double getVelY() {
    return .5 * (motor_0.getSelectedSensorVelocity() + motor_2.getSelectedSensorVelocity());
  }

  public void stop() {
    drive(0, 0);
  }

  public void setGear(int gear_val) {
    if (gear_val > 3) gear_val = 3;
    if (gear_val < 0) gear_val = 0;
    gear = gear_val;
  }

  public void drive(double y, double z) {
    /* normalize input to -1. ~ 1. */
    if (y > 1.) y = 1.;
    else if (y < -1.) y = -1.;
    if (z > 1.) z = 1.;
    else if (z < -1.) z = -1.;

    /* apply scaling factor */
    y *= k_y[gear];
    z *= k_z[gear];

    motor_0.set(ControlMode.PercentOutput, y - z);
    motor_2.set(ControlMode.PercentOutput, y + z);
  }

  public void log() {
    SmartDashboard.putNumber("encoder L", motor_0.getSelectedSensorPosition());
    SmartDashboard.putNumber("encoder R", motor_2.getSelectedSensorPosition());
    SmartDashboard.putNumber("Gear!", gear + 1);

  }
}

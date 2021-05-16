// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.Collect;
import frc.robot.commands.DriveToDistance;
import frc.robot.commands.Shoot;
import frc.robot.commands.SwitchGear;
import frc.robot.commands.Transmit;
import frc.robot.commands.TurnToAngle;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Transmission;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Chassis subsys_chassis = new Chassis();
  private final Intake subsys_intake = new Intake();
  private final Transmission subsys_transmission = new Transmission();
  private final Turret subsys_turret = new Turret();

  private final AutoCommand cmd_auto_command = new AutoCommand(subsys_chassis);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    subsys_chassis.setDefaultCommand(
      new RunCommand(() -> subsys_chassis.drive(
        -IO.stick_0.getY(Hand.kLeft), 
        IO.stick_0.getTriggerAxis(Hand.kLeft) - IO.stick_0.getTriggerAxis(Hand.kRight)
      ), subsys_chassis));
      
    subsys_turret.setDefaultCommand(
      new RunCommand(() -> subsys_turret.setTurnVelocity(
        0.1 * IO.stick_1.getX(Hand.kRight)
      ), subsys_turret));
  }

  public void stop() {
    subsys_intake.pull();
    subsys_chassis.setGear(0);
  }

  public void log() {
    subsys_chassis.log();
    subsys_intake.log();
    subsys_turret.log();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(IO.stick_0, Button.kA.value)
      .whenPressed(() -> subsys_intake.pull());
      
    new JoystickButton(IO.stick_0, Button.kB.value)
      .whenPressed(() -> subsys_intake.push());

    new JoystickButton(IO.stick_0, Button.kBumperRight.value)
      .whenPressed(new SwitchGear(subsys_chassis, 1));
      
    new JoystickButton(IO.stick_0, Button.kBumperLeft.value)
      .whenPressed(new SwitchGear(subsys_chassis, -1));
        
    new JoystickButton(IO.stick_0, Button.kY.value)
      .whenPressed(new DriveToDistance(subsys_chassis, 16000)); // .withTimeout(0)
        
    new JoystickButton(IO.stick_0, Button.kX.value)
      .whenPressed(new TurnToAngle(subsys_chassis, 16800)); // .withTimeout(0)
      
    new JoystickButton(IO.stick_1, Button.kBumperRight.value)
      .toggleWhenPressed(new Shoot(subsys_turret));
    
    new JoystickButton(IO.stick_1, Button.kBumperLeft.value)
      .toggleWhenPressed(new Collect(subsys_intake));
      
    new JoystickButton(IO.stick_1, Button.kB.value)
      .whileHeld(new Transmit(subsys_transmission));
  
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return cmd_auto_command;
  }
}

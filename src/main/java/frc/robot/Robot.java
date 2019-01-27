/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;
  public static NetworkTableEntry centerX;
  public static NetworkTableEntry centerY;
  public static NetworkTableEntry coProcUp;
  public static double speed;
  public static double x;
  public static double y;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    inst.setUpdateRate(0.01);
    NetworkTable table = inst.getTable("Vision");
    centerX = table.getEntry("centerX");
    centerY = table.getEntry("centerY");
    RobotMap.init();
    centerX.setDouble(0);
    centerY.setDouble(0);
    //CameraServer.getInstance().startAutomaticCapture();
    m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    //chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    RobotMap.vPID.run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    speed = 0;
    RobotMap.rotator.disable();
    RobotMap.visionThing.disable();
  }

  @Override
  public void disabledPeriodic() {
    RobotMap.visionThing.disable();
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
    RobotMap.visionThing.setSetpoint(0);
    RobotMap.visionThing.enable();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if(RobotMap.visionThing.isEnabled()){
      System.out.println("Alignment Enabled");
    }
    System.out.println("Error: " + RobotMap.visionThing.getAvgError());
    System.out.println("Drive Left: " + RobotMap.left.get());
    System.out.println("Drive Right: " + RobotMap.right.get());
    System.out.println("Lidar: " + RobotMap.lidar.getDistance());
    Scheduler.getInstance().run();
    x = centerX.getDouble(-1);
    y = centerY.getDouble(-1);
    System.out.println("x: " + x);
    System.out.println("y: " + y);
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    //RobotMap.rotator.setSetpoint(45);
    //RobotMap.rotator.enable();
    RobotMap.visionThing.enable();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */

  @Override
  public void teleopPeriodic() {
    speed = m_oi.x.getRawAxis(1);
    double turnSpeed = m_oi.x.getRawAxis(4);
    
    // if(Math.abs(speed) > 0.05 || Math.abs(turnSpeed) > 0.05){
    //   RobotMap.drive.arcadeDrive(-turnSpeed, -speed);
    // }
    
    if(RobotMap.rotator.isEnabled()){
      RobotMap.rotator.disable();
    }
    
    System.out.println("Angle: " + RobotMap.navx.getAngle());
    System.out.println("Error: " + RobotMap.rotator.getError());
    System.out.println("Drive Left: " + RobotMap.left.get());
    System.out.println("Drive Right: " + RobotMap.right.get());

    Scheduler.getInstance().run();

    x = centerX.getDouble(-1);
    y = centerY.getDouble(-1);
    
    System.out.println("x: " + x);
    System.out.println("y: " + y);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    System.out.println("Vision Angle: " + RobotMap.vPID.getVisionAngle());
    System.out.println("Lidar: " + RobotMap.lidar.getDistance());
  }
}

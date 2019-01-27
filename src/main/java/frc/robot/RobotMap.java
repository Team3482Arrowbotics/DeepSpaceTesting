/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.subsystems.PWMLidar;
import frc.robot.subsystems.RotateOut;
import frc.robot.subsystems.Vision;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static WPI_TalonSRX frontLeft;
  public static WPI_TalonSRX frontRight;
  public static WPI_TalonSRX backLeft;
  public static WPI_TalonSRX backRight;
  public static SpeedControllerGroup left;
  public static SpeedControllerGroup right;
  public static DifferentialDrive drive;
  public static AHRS navx;
  public static Vision vPID;
  public static PWMLidar lidar;
  public static RotateOut driveOut;
  public static PIDController rotator;
  public static PIDController visionThing;
  public static PIDController lidarController;
  
  public static void init(){
    frontLeft = new WPI_TalonSRX(9);
    backLeft = new WPI_TalonSRX(2);
    frontRight = new WPI_TalonSRX(4);
    backRight = new WPI_TalonSRX(8);
    left = new SpeedControllerGroup(frontLeft, backLeft);
    right = new SpeedControllerGroup(frontRight, backRight);
    drive = new DifferentialDrive(left, right);
    navx = new AHRS(Port.kMXP);
    vPID = new Vision(PIDSourceType.kDisplacement);
    driveOut = new RotateOut();
    lidar = new PWMLidar(1, PIDSourceType.kDisplacement);
    lidarController = new PIDController(20000, 0, 0, lidar, driveOut);

    // rotator.setAbsoluteTolerance(2);
    // rotator.setInputRange(-180, 180);
    // rotator.setOutputRange(-0.65, 0.65);
    // rotator.setContinuous(true);
    
    rotator = new PIDController(2, 0, 0, navx, driveOut);
    
    rotator.setAbsoluteTolerance(2);
    rotator.setInputRange(-180, 180);
    rotator.setOutputRange(-0.65, 0.65);
    rotator.setContinuous(true);
    // Vpid(P=0.01, I=0, D=0.02)
    visionThing = new PIDController(0.01, 0, 0.02, vPID, driveOut);
    visionThing.setPercentTolerance(25);
    visionThing.setSetpoint(0);
    visionThing.setInputRange(-320, 320);
    visionThing.setOutputRange(-.5, .5);
    visionThing.setContinuous(true);
  }

  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}

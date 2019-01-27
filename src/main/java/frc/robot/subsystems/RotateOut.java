/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class RotateOut implements PIDOutput{
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public RotateOut(){

  }

  @Override
  public void pidWrite(double output) {
    if(Math.abs(output) > 0){
      System.out.println("Giving Output: " + output);
    }
    RobotMap.drive.arcadeDrive(output, -Robot.speed);
  }

}

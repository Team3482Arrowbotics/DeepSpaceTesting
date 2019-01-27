/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class Vision extends Subsystem implements PIDSource{
    
    public final double PIXELS_TO_DEGREES = 0.085625;
    
    PIDSourceType type;

    public double centerX;
    public double centerY;

    public Vision(PIDSourceType type){
        this.type = type;
    }

    
    public void run(){
        centerX = Robot.centerX.getDouble(0.0);
        centerY = Robot.centerY.getDouble(0.0);
    }
    
    public double getVisionAngle(){
        return centerX * PIXELS_TO_DEGREES;
    }

    public double pidGet(){
        System.out.println("Source Activated.");
        return centerX * -1;
    }
    
    public PIDSourceType getPIDSourceType(){
        return type;
    }
    
    public void setPIDSourceType(PIDSourceType type){
        this.type = type;
    }

    public void initDefaultCommand(){
        
    }
}

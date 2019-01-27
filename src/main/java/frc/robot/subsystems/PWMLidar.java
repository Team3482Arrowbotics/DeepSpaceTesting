/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PWMLidar implements PIDSource{
	int channel;
	DigitalInput in;
	Counter counter;
	private int printedWarningCount = 5;
	static final int CALIBRATION_OFFSET = 0;
	double last = 0;
  public boolean drasticChange = false;
  PIDSourceType type;

	public PWMLidar(int channel, PIDSourceType type) {
    this.channel = channel;
    this.type = type;
		in = new DigitalInput(channel);
		counter = new Counter(in);
		counter.setMaxPeriod(1.0);
		counter.setSemiPeriodMode(true);
		counter.reset();
	}
  public PIDSourceType getPIDSourceType(){
    return type;
  }

  public void setPIDSourceType(PIDSourceType type){
    this.type = type;
  }

  public double pidGet(){
    return getDistance();
  }
	public double getDistance() {

		double cm;
		if (counter.get() < 1) {
			if (printedWarningCount-- > 0) {
				System.out.println("Lidar waiting for distance measurement");
			}
			return 0;
		}
		// Period is in microseconds, multiply by 1 million to make numbers
		// nice, divide by 10 to get cm
		cm = (counter.getPeriod() * 1000000.0 / 10.0) + CALIBRATION_OFFSET;
		if (Math.abs(last - cm) > 100 && last != 0 && cm < 60) {
			drasticChange = true;
			// System.out.println("DRASTIC");
		} else {
			if (cm > 65)
				drasticChange = false;
		}
		last = cm;
		return cm;
    }
}

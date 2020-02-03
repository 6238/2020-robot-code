package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainController implements RobotController {

    // robot drive object (drop six/west coast)
    private DifferentialDrive robotDrive;

    // left and right joysticks
    private JoystickController l_Stick;
    private JoystickController r_Stick;

    // speed multiplier/reducer
    private double insanityFactor = 0.5;

    // inverted drive
    private boolean reverseDrive = false;

    // joystick drive or no?
    private boolean joyDrive = true;

    // left joystick values
    private double l_JoyY;

    // right joystick values
    private double r_JoyY;

    private double sliderValue;

    public DriveTrainController(RobotProperties properties) {
        robotDrive = properties.getRobotDrive();

        l_Stick = properties.getL_Stick();
        r_Stick = properties.getR_Stick();

        sliderValue = r_Stick.getSlider();
        sliderValue += 1;
        sliderValue /= 2;

        SmartDashboard.putNumber("insanityFactor", insanityFactor);
        SmartDashboard.putBoolean("reverseDrive", reverseDrive);
        SmartDashboard.putBoolean("joyDrive", joyDrive);
    }

    @Override
    public boolean performAction() {
        reverseDrive = SmartDashboard.getBoolean("reverseDrive", false);
        joyDrive = SmartDashboard.getBoolean("joyDrive", true);

        double newSliderValue = r_Stick.getSlider();
        newSliderValue += 1;
        newSliderValue /= 2;

        l_JoyY = l_Stick.getY();
        
        r_JoyY = r_Stick.getY();

        if (SmartDashboard.getNumber("insanityFactor", 0.5) != insanityFactor) {
            insanityFactor = SmartDashboard.getNumber("insanityFactor", insanityFactor);
        } else if (newSliderValue != sliderValue) {
            insanityFactor = newSliderValue;
            sliderValue = newSliderValue;
        }
        SmartDashboard.putNumber("insanityFactor", insanityFactor);
        
        if (joyDrive) {
            if (reverseDrive) {
                robotDrive.tankDrive(-1 * insanityFactor * l_JoyY, -1 * insanityFactor * r_JoyY);
            } else {
                robotDrive.tankDrive(insanityFactor * l_JoyY, insanityFactor * r_JoyY);
            }
        }

        return true;
    }

}
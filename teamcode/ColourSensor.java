//This was a used in testing
//To read HSV values for the bottom colour sensor
package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="ReyBot: ColourSensor", group = "ReyBot")
public class ColourSensor extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareReyBot ReyBot = new HardwareReyBot();
    private ElapsedTime runtime = new ElapsedTime();

    static final double ticks_per_rotation = 24.0;
    static final double wheel_diameter = 3.9375;
    static final double gear_reduction = 60.0;
    static final double ticks_per_inch = (ticks_per_rotation*gear_reduction) / (wheel_diameter*3.141593);

    static final double drive_speed = 0.5;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        ReyBot.init(hardwareMap);

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};
        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        ReyBot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        ReyBot.leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ReyBot.rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ReyBot.leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ReyBot.rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                ReyBot.leftDriveFront.getCurrentPosition(),
                ReyBot.rightDriveFront.getCurrentPosition());
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            Color.RGBToHSV((ReyBot.CSensorBottom.red() * 255), (ReyBot.CSensorBottom.green() * 255), (ReyBot.CSensorBottom.blue() * 255), hsvValues);

            float hue = hsvValues[0];

            // send the info back to driver station using telemetry function.
            telemetry.addData("Bottom: Alpha", ReyBot.CSensorBottom.alpha());
            telemetry.addData("Bottom: Red  ", ReyBot.CSensorBottom.red());
            telemetry.addData("Bottom: Green", ReyBot.CSensorBottom.green());
            telemetry.addData("Bottom: Blue ", ReyBot.CSensorBottom.blue());
            telemetry.addData("Bottom: Hue", hue);
            telemetry.update();

        }
    }

    public void encoderDrive(double leftSpeed, double rightSpeed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newLeftTarget = ReyBot.leftDriveFront.getCurrentPosition() + (int)(leftInches * ticks_per_inch);
            newRightTarget = ReyBot.rightDriveFront.getCurrentPosition() + (int)(rightInches * ticks_per_inch);
            ReyBot.leftDriveFront.setTargetPosition(newLeftTarget);
            ReyBot.rightDriveFront.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            ReyBot.leftDriveFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ReyBot.rightDriveFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            ReyBot.leftDriveFront.setPower(Math.abs(leftSpeed));
            ReyBot.rightDriveFront.setPower(Math.abs(rightSpeed));

            while (opModeIsActive() && (runtime.seconds() < timeoutS) && (ReyBot.leftDriveFront.isBusy() && ReyBot.rightDriveFront.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        ReyBot.leftDriveFront.getCurrentPosition(),
                        ReyBot.rightDriveFront.getCurrentPosition());
                telemetry.update();

            }
            // Stop all motion;
            ReyBot.leftDriveFront.setPower(0);
            ReyBot.rightDriveFront.setPower(0);

            // Turn off RUN_TO_POSITION
            ReyBot.leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ReyBot.rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);

        }
    }
}
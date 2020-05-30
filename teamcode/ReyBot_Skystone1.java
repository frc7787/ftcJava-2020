//Autonomous Phase for the Blue Alliance

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="ReyBot: Skystone - Blue Alliance1", group = "ReyBot")
public class ReyBot_Skystone1 extends LinearOpMode {

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
        float hsvValues1[] = {0F, 0F, 0F};
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

        encoderDrive(drive_speed, drive_speed,  25,  25, 5.0);
        ReyBot.elevator.setPower(1);
        sleep(1500);
        ReyBot.elevator.setPower(0);
        sleep(500);
        encoderDrive(drive_speed, drive_speed,  7,  -7, 5.0);
        while (opModeIsActive()) {
            Color.RGBToHSV((ReyBot.CSensorLeft.red() * 8), (ReyBot.CSensorLeft.green() * 8), (ReyBot.CSensorLeft.blue() * 8), hsvValues);
            Color.RGBToHSV((ReyBot.CSensorRight.red() * 8), (ReyBot.CSensorRight.green() * 8), (ReyBot.CSensorRight.blue() * 8), hsvValues1);
            float hue = hsvValues[0];
            float hue1 = hsvValues1[0];

            boolean yellow = hue > 20 && hue < 60;
            boolean yellow1 = hue1 > 20 && hue1 < 60;
            float black = ReyBot.CSensorLeft.alpha();
            float black1 = ReyBot.CSensorRight.alpha();

            if((black < 40 || black1 < 40) && (yellow || yellow1)) {
                ReyBot.leftIntake.setPower(0);
                ReyBot.rightIntake.setPower(0);
                ReyBot.rightExit.setPower(0);
                ReyBot.leftExit.setPower(0);
                ReyBot.leftDriveFront.setPower(0);
                ReyBot.rightDriveFront.setPower(0);
                break;
            } else {
                ReyBot.leftDriveFront.setPower(0.1);
                ReyBot.rightDriveFront.setPower(0.1);
                ReyBot.leftIntake.setPower(1);
                ReyBot.rightIntake.setPower(-1);
                ReyBot.rightExit.setPower(1);
                ReyBot.leftExit.setPower(1);
            }

            // send the info back to driver station using telemetry function.
            telemetry.addData("Left: Alpha", ReyBot.CSensorLeft.alpha());
            telemetry.addData("Left: Red  ", ReyBot.CSensorLeft.red());
            telemetry.addData("Left: Green", ReyBot.CSensorLeft.green());
            telemetry.addData("Left: Blue ", ReyBot.CSensorLeft.blue());
            telemetry.addData("Right: Alpha", ReyBot.CSensorRight.alpha());
            telemetry.addData("Right: Red  ", ReyBot.CSensorRight.red());
            telemetry.addData("Right: Green", ReyBot.CSensorRight.green());
            telemetry.addData("Right: Blue ", ReyBot.CSensorRight.blue());
            telemetry.addData("Left: Hue", hue);
            telemetry.update();

        }
        ReyBot.leftExit.setPower(-1);
        ReyBot.rightExit.setPower(-1);
        sleep(1000);
        ReyBot.leftExit.setPower(0);
        ReyBot.rightExit.setPower(0);
        sleep(500);
        encoderDrive(-drive_speed, drive_speed,  7,  -7, 5.0);
        sleep(500);
        ReyBot.elevator.setPower(-1);
        sleep(1500);
        ReyBot.elevator.setPower(0);
        sleep(500);
        encoderDrive(drive_speed, drive_speed,  8,  8, 5.0);
        encoderDrive(-drive_speed, drive_speed,  7,  -7, 5.0);
        encoderDrive(drive_speed, drive_speed,  35,  35, 5.0);
        ReyBot.leftIntake.setPower(-1);
        ReyBot.rightIntake.setPower(1);
        ReyBot.rightExit.setPower(-1);
        ReyBot.leftExit.setPower(-1);
        sleep(500);
        ReyBot.leftIntake.setPower(0);
        ReyBot.rightIntake.setPower(0);
        ReyBot.rightExit.setPower(0);
        ReyBot.leftExit.setPower(0);
        encoderDrive(-drive_speed, -drive_speed,  -25,  -25, 5.0);
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

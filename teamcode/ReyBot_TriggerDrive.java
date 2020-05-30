//TeleOp Phase

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="ReyBot: Trigger Drive", group = "ReyBot")

public class ReyBot_TriggerDrive extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareReyBot ReyBot = new HardwareReyBot();
    private ElapsedTime runtime = new ElapsedTime();

    static final double ticks_per_rotation = 24.0;
    static final double wheel_diameter = 3.9375;
    static final double gear_reduction = 60.0;
    static final double ticks_per_inch = (ticks_per_rotation * gear_reduction) / (wheel_diameter * 3.141593);

    @Override
    public void runOpMode() {
        //initialize throttle and turn value
        //initialize right turn speed and left turn speed
        double reverse, forward, turnValue, servo, elevator, cap;
        double rightTurn, leftTurn;

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};
        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        ReyBot.init(hardwareMap);

        telemetry.addData("Mode", "waiting");
        telemetry.addData("Path1",
                ReyBot.elevator.getCurrentPosition());
        telemetry.update();

        // wait for start button.
        waitForStart();

        while (opModeIsActive()) {
            Color.RGBToHSV((ReyBot.CSensorLeft.red() * 8), (ReyBot.CSensorLeft.green() * 8), (ReyBot.CSensorLeft.blue() * 8), hsvValues);
            float hue = hsvValues[0];

            boolean yellowBlock = hue > 20 && hue < 60;

            //Get input values of gamepad
            forward = gamepad1.right_trigger;
            reverse = gamepad1.left_trigger * -1;
            turnValue = gamepad1.left_stick_x;
            //Elevator Servo
            servo = gamepad2.right_trigger;
            elevator = gamepad2.left_stick_y * -1;
            //Capstone Servo
            cap = gamepad2.right_stick_y * -1;

            if (elevator != 0) {
                ReyBot.elevator.setPower(elevator);
            } else {
                ReyBot.elevator.setPower(0);
            }

            if (servo != 0) {
                ReyBot.blockServo.setPosition(servo);
            } else {
                ReyBot.blockServo.setPosition(0);
            }

            if (cap != 0) {
                ReyBot.capstone.setPower(cap);
            } else {
                ReyBot.capstone.setPower(0);
            }

            //Servo that keeps capstone in place
            if (gamepad2.a) {
                ReyBot.fan.setPosition(0.8);
            } else {
                ReyBot.fan.setPosition(0);
            }

            //If A is pressed then intake and exit motors will run
            //Otherwise the motors are set to zero
            if (gamepad1.a) {
                //Emergency Exit
                if (gamepad1.x) {
                    ReyBot.leftIntake.setPower(-1);
                    ReyBot.rightIntake.setPower(1);

                    ReyBot.rightExit.setPower(-0.7);
                    ReyBot.leftExit.setPower(-0.7);
                } else {
                    if (yellowBlock) {
                        ReyBot.leftIntake.setPower(0);
                        ReyBot.rightIntake.setPower(0);

                        ReyBot.rightExit.setPower(0);
                        ReyBot.leftExit.setPower(0);
                    } else {
                        ReyBot.leftIntake.setPower(0.85);
                        ReyBot.rightIntake.setPower(-0.95);

                        ReyBot.rightExit.setPower(1);
                        ReyBot.leftExit.setPower(1);
                    }
                }
            } else {
                ReyBot.leftIntake.setPower(0);
                ReyBot.rightIntake.setPower(0);

                ReyBot.rightExit.setPower(0);
                ReyBot.leftExit.setPower(0);
            }

            //All Motors run
            if (gamepad1.y) {
                ReyBot.leftIntake.setPower(0.85);
                ReyBot.rightIntake.setPower(-0.95);

                ReyBot.rightExit.setPower(1);
                ReyBot.leftExit.setPower(1);
            }

            //Move Servos to grab the base
            if (gamepad1.b) {
                ReyBot.leftServo.setPosition(0.4);
                ReyBot.rightServo.setPosition(0.5);
            } else {
                ReyBot.leftServo.setPosition(1);
                ReyBot.rightServo.setPosition(0);
            }

            //If RB is held down then throttle is half the speed
            if (gamepad1.right_bumper) {
                reverse = reverse / 2;
                forward = forward / 2;
                turnValue = turnValue / 2;
            }

            //Move Backward
            if (turnValue == 0 && forward == 0 && reverse != 0) {
                ReyBot.leftDriveFront.setPower(reverse);
                ReyBot.rightDriveFront.setPower(reverse);
            }//Move Forward
            if (reverse == 0 && turnValue == 0 && forward != 0) {
                ReyBot.leftDriveFront.setPower(forward);
                ReyBot.rightDriveFront.setPower(forward);
            } //Turn Right
            else if (turnValue > 0 && (reverse != 0 || forward != 0)) {
                rightTurn = 1 - turnValue;
                if ((reverse > rightTurn) && forward == 0) {
                    ReyBot.rightDriveFront.setPower(rightTurn);
                    ReyBot.leftDriveFront.setPower(reverse);
                } else if ((reverse < rightTurn) && forward == 0) {
                    ReyBot.rightDriveFront.setPower(-0.00000001);
                    ReyBot.leftDriveFront.setPower(reverse);
                } else if ((forward < rightTurn) && reverse == 0) {
                    ReyBot.rightDriveFront.setPower(0.00000001);
                    ReyBot.leftDriveFront.setPower(forward);
                } else if ((forward > rightTurn) && reverse == 0) {
                    ReyBot.rightDriveFront.setPower(rightTurn);
                    ReyBot.leftDriveFront.setPower(forward);
                }
            } //Turn Left
            else if (turnValue < 0 && (reverse != 0 || forward != 0)) {
                leftTurn = 1 + turnValue;
                if ((reverse > leftTurn) && forward == 0) {
                    ReyBot.leftDriveFront.setPower(leftTurn);
                    ReyBot.rightDriveFront.setPower(reverse);
                } else if ((reverse < leftTurn) && forward == 0) {
                    ReyBot.leftDriveFront.setPower(-0.00000001);
                    ReyBot.rightDriveFront.setPower(reverse);
                } else if ((forward < leftTurn) && reverse == 0) {
                    ReyBot.leftDriveFront.setPower(0.00000001);
                    ReyBot.rightDriveFront.setPower(forward);
                } else if ((forward > leftTurn) && reverse == 0) {
                    ReyBot.leftDriveFront.setPower(leftTurn);
                    ReyBot.rightDriveFront.setPower(forward);
                }
            } else if (reverse == 0 && forward == 0 && turnValue != 0) {
                turnValue = turnValue / 2;
                if (turnValue > 0) {
                    ReyBot.rightDriveFront.setPower(-turnValue);
                    ReyBot.leftDriveFront.setPower(turnValue);
                } else if (turnValue < 0) {
                    ReyBot.rightDriveFront.setPower(-turnValue);
                    ReyBot.leftDriveFront.setPower(turnValue);
                }
            } //Robot Stop
            else if (reverse == 0 && forward == 0 && turnValue == 0) {
                ReyBot.leftDriveFront.setPower(0.0);
                ReyBot.rightDriveFront.setPower(0.0);
            }

            // send the info back to driver station using telemetry function.
            telemetry.addData("Alpha", ReyBot.CSensorLeft.alpha());
            telemetry.addData("Red  ", ReyBot.CSensorLeft.red());
            telemetry.addData("Green", ReyBot.CSensorLeft.green());
            telemetry.addData("Blue ", ReyBot.CSensorLeft.blue());
            telemetry.addData("Hue", hue);
            telemetry.addData("Mode", "running");
            telemetry.addData("Forward:", forward);
            telemetry.addData("Reverse:", reverse);
            telemetry.addData("Intake/Exit", gamepad1.a);
            telemetry.update();

            idle();
        }
    }
}

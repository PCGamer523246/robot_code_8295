package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.JavaUtil;


@TeleOp(name = "Gary", group = "TeleOp")
public class jacobTelop8295 extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotorEx launcherMotor;
    private DcMotor intakeMotorRight;

    private CRServo launcherCRServoRight;
    private CRServo launcherCRServoLeft;

    private HuskyLens huskylens;

    final double LAUNCHER_TARGET_VELOCITY = 1320;
    final double LAUNCHER_MIN_VELOCITY = 1075;

    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;


    @Override
    public void runOpMode() {
        // Initialize hardware
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");


        intakeMotorRight = hardwareMap.get(DcMotor.class, "intakeMotorRight");

        launcherMotor = hardwareMap.get(DcMotorEx.class, "launcherMotor");


        launcherCRServoRight = hardwareMap.get(CRServo.class, "launcherCRServoRight");
        launcherCRServoLeft = hardwareMap.get(CRServo.class, "launcherCRServoLeft");


        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Reverse right side motors
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);


        intakeMotorRight.setDirection(DcMotor.Direction.REVERSE);
        launcherMotor.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addLine("Ready to start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Mecanum drive
            double y = -gamepad1.left_stick_y; // forward
            double x = gamepad1.left_stick_x * 1.1; // strafe
            double rx = gamepad1.right_stick_x; // rotate

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            // Arm control
            if (gamepad1.right_bumper) {
                launcherMotor.setVelocity(LAUNCHER_TARGET_VELOCITY);
            } else if (gamepad1.left_bumper) {
                launcherMotor.setPower(0);
            }

            if (gamepad1.dpad_up) {
                launcherMotor.setPower(.65);
            } else if (gamepad1.dpad_right) { // stop flywheel
                launcherMotor.setPower(.60);
            } else if (gamepad1.dpad_down) {
                launcherMotor.setPower(.55);
            }
/*
            if (gamepad2.left_trigger > 0){
                intakeMotorLeft.setPower(gamepad2.left_trigger);
                intakeMotorRight.setPower(gamepad2.left_trigger);
                intakeCRServoLeft.setPower(-gamepad2.left_trigger);
                intakeCRServoRight.setPower(gamepad2.left_trigger);
            }
            else if (gamepad2.right_trigger > 0){
                intakeMotorLeft.setPower(-1 * gamepad2.left_trigger);
                intakeMotorRight.setPower(-1 * gamepad2.left_trigger);
                intakeCRServoLeft.setPower(-1 * gamepad2.left_trigger);
                intakeCRServoRight.setPower(-1 * gamepad2.left_trigger);
            }
*/

            // Intake Servo
            if (gamepad1.a) {
                intakeMotorRight.setPower(-1);
            } else if (gamepad1.b) {
                intakeMotorRight.setPower(0);
            }


            // Launcher Servo control
            if (gamepad1.x) {
                launcherCRServoRight.setPower(-1); // open
                launcherCRServoLeft.setPower(1); // open
            } else if (gamepad1.y) {
                launcherCRServoRight.setPower(0); // closed
                launcherCRServoLeft.setPower(0); // closed
            }

            ElapsedTime myElapsedTime;
            HuskyLens.Block[] myHuskyLensBlocks;
            HuskyLens.Block tag;


            huskylens = hardwareMap.get(HuskyLens.class, "huskylens");


            telemetry.update();
            myElapsedTime = new ElapsedTime();

            myHuskyLensBlocks = huskylens.blocks();
            telemetry.addData("Block count", JavaUtil.listLength(myHuskyLensBlocks));
            for (HuskyLens.Block myHuskyLensBlock_item : myHuskyLensBlocks) {
                tag = myHuskyLensBlock_item;
                telemetry.addData("Tag", "id=" + tag.id + " size: " + tag.width + "x" + tag.height + " position: " + tag.x + "," + tag.y);


                telemetry.addLine();
                telemetry.addLine();


                // Telemetry
                telemetry.addData("Front Left Power", frontLeftPower);
                telemetry.addData("Front Right Power", frontRightPower);
                telemetry.addData("Back Left Power", backLeftPower);
                telemetry.addData("Back Right Power", backRightPower);
                telemetry.addData("Intake Motor Left", intakeMotorRight.getPower());
                telemetry.addData("launcherMotor", launcherMotor.getPower());
                telemetry.addData("launcherMotor", launcherMotor.getVelocity());


                telemetry.update();

            }


        }
    }
}
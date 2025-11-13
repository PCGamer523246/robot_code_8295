package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.JavaUtil;


@Autonomous(name = "Gary", group = "8295")
public class jacobOp8295 extends LinearOpMode {

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

                telemetry.addData("Intake Motor Left", intakeMotorRight.getPower());
                telemetry.addData("launcherMotor", launcherMotor.getPower());
                telemetry.addData("launcherMotor", launcherMotor.getVelocity());


                telemetry.update();

            }


        }
    }
}

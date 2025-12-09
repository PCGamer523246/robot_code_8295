package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

//Code From Ryan V
//SMH BARRONS 8295
//SIR ONTIVERIUS IS A CONTRIBUTE
//He got me to do this. A role model
@TeleOp(name = "GARRY2.0", group = "TeleOp")
public class REBELGARY extends OpMode {


    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotorEx launcherMotor;
    private DcMotor intakeMotorRight;

    private CRServo launcherCRServoRight, launcherCRServoLeft;

    private enum Range {
        SHORT,

        FAR;
    }
    private enum Intake{

        ON,

        OFF;
    }

    private enum IntakeServos{
        ON,

        OFF;
    }
    private enum Launchers{
        ON,

        OFF;
    }


    final double LAUNCHER_TARGET_VELOCITY = 1320 ;
    final double LAUNCHER_MIN_VELOCITY = 1075;

    final double STOP_SPEED = 0.0; //We send this power to the servos when we want them to stop.
    final double FULL_SPEED = 1.0;

   // private ShootState shootState;

    private Range range;

    private Intake intake;

    private IntakeServos intakeServos;

    private HuskyLens huskylens;

    private Launchers launchers;


    @Override
    public void init() {

        // Drive Motors
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

        //The Default. Need This to actually have something declared for the state machine. If removed will not be able to know where to start. Its the starting varible.

        range = Range.SHORT;

        intake = Intake.ON;

        intakeServos = IntakeServos.ON;

        launchers = Launchers.OFF;


        //___________________________________________________________________BREAK__________________


        //Drive Motors


        telemetry.addLine("Larry is ready");
        telemetry.update();

        telemetry.addLine("Declaring Some Variables");

        huskylens = hardwareMap.get(HuskyLens.class, "huskylens");

        ElapsedTime myElapsedTime;
        HuskyLens.Block[] myHuskyLensBlocks;
        HuskyLens.Block tag;

    }

    @Override
    public void start() {

        telemetry.addLine("Ready To Start");

    }

    @Override
    public void loop() {

        //This is what the does when started.
        //equivalent to whileOpMode is active

        //Drive
        double y = -gamepad1.left_stick_y; // forward
        double x = gamepad1.left_stick_x * 1.1; // strafe
        double rx = gamepad1.right_stick_x; // rotate

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);


        ElapsedTime myElapsedTime;
        HuskyLens.Block[] myHuskyLensBlocks;
        HuskyLens.Block myHuskyLensBlock;

        // Put initialization blocks here
        telemetry.update();
        myElapsedTime = new ElapsedTime();

        myHuskyLensBlocks = huskylens.blocks();

        //telemetry.addLine("ShootState" +" "+ shootState);
        telemetry.addLine("Range" +" "+ range);
        telemetry.addLine("Launchers" + launchers);
        telemetry.addLine();
        telemetry.addLine();
        telemetry.addData("Block count", JavaUtil.listLength(myHuskyLensBlocks));


        for (HuskyLens.Block myHuskyLensBlock_item : myHuskyLensBlocks) {
            myHuskyLensBlock = myHuskyLensBlock_item;

            telemetry.addLine("Range" +" "+ range);
            telemetry.addLine("IntakeServos" +" "+ intakeServos);
            telemetry.addLine("Intake" +" "+ intake);
            telemetry.addData("Tag", "id=" + myHuskyLensBlock.id + " size: " + myHuskyLensBlock.width + "x" + myHuskyLensBlock.height + " position: " + myHuskyLensBlock.x + "," + myHuskyLensBlock.y);

            telemetry.update();

        }

        switch (range) {

            case SHORT:

                if(gamepad1.y){
                    range=Range.FAR;
                }

                switch(launchers){

                    case ON:

                        launcherMotor.setVelocity(LAUNCHER_TARGET_VELOCITY);
                        launcherMotor.setPower(0.60);

                        if (gamepad1.x){
                            launchers = Launchers.OFF;
                        }

                        break;

                    case OFF:

                        launcherMotor.setPower(0);

                        if (gamepad1.x){
                            launchers = Launchers.ON
                            ;
                        }
                        break;

                }
                break;

            case FAR:

                if(gamepad1.y){
                    range=Range.SHORT;
                }

                switch(launchers){

                    case ON:

                        launcherMotor.setVelocity(LAUNCHER_TARGET_VELOCITY);
                        launcherMotor.setPower(0.65);

                        if (gamepad1.x){
                            launchers = Launchers.OFF;
                        }

                        break;

                    case OFF:

                        launcherMotor.setPower(0);

                        if (gamepad1.x){
                            launchers = Launchers.ON;
                        }
                        break;
                }
            break;
        }

        switch (intake){

            case ON:
                intakeMotorRight.setPower(-1);

                if(gamepad1.a){
                    intake = Intake.OFF;
                }
            break;

            case OFF:
                intakeMotorRight.setPower(0);

                if (gamepad1.a){
                    intake = Intake.ON;
                }
            break;
        }


        switch (intakeServos){

            case ON:
                launcherCRServoRight.setPower(-1);
                launcherCRServoLeft.setPower(1);

                if (gamepad1.b){
                    intakeServos = IntakeServos.OFF;
                }

            break;


            case OFF:
                launcherCRServoRight.setPower(0);
                launcherCRServoLeft.setPower(0);

                if (gamepad1.b){
                    intakeServos = IntakeServos.ON;
                }
            break;
        }
    }
}

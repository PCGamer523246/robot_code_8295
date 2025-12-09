package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

//Code From Ryan V
//SMH BARRONS 8295
//SIR ONTIVERIUS IS A CONTRIBUTE
//He got me to do this. A role model
@TeleOp(name = "Barry", group = "TeleOp")
public class REBELLARRY extends OpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;         //Motors

    private DcMotor intakeMotorLeft, launcherMotorLeft, launcherMotorRight;     //Intake Plus Launcher Motors

    private CRServo intakeCRServoLeft, intakeCRServoRight;          //Intake Servos

    private CRServo launcherCRServoLeft, launcherCRServoRight; //Intake Server Launchers          The second level

    private CRServo intakeServokicker;

    private CRServo stageOneServo;

    private enum ShootState {

        NOBALL,
        ONEBALL,
        TWOBALLS,
        THREEBALLS,
        ALLGO;


    }

    private enum Range {
        FAR,
        SHORT;
    }

    private ShootState shootState;

    private Range range;

    private HuskyLens huskylens;


    @Override
    public void init() {

        // Drive Motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, " backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // Launch Motors
        intakeMotorLeft = hardwareMap.get(DcMotor.class, "intakeMotorLeft");

        launcherMotorLeft = hardwareMap.get(DcMotor.class, "launcherMotorLeft");
        launcherMotorRight = hardwareMap.get(DcMotor.class, "launcherMotorRight");


        intakeCRServoLeft = hardwareMap.get(CRServo.class, "intakeCRServoLeft");
        intakeCRServoRight = hardwareMap.get(CRServo.class, "intakeCRServoRight");

        launcherCRServoLeft = hardwareMap.get(CRServo.class, "launcherCRServoLeft");
        launcherCRServoRight = hardwareMap.get(CRServo.class, "launcherCRServoRight");

        intakeServokicker = hardwareMap.get(CRServo.class, "intakeServokicker");

        stageOneServo = hardwareMap.get(CRServo.class, "stageOneServo");


        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        launcherMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE); //huh;ljk
        launcherMotorRight.setDirection(DcMotorSimple.Direction.FORWARD);

        launcherCRServoLeft.setDirection(CRServo.Direction.REVERSE);
        launcherCRServoRight.setDirection(CRServo.Direction.FORWARD);

        intakeCRServoLeft.setDirection(CRServo.Direction.REVERSE);
        intakeCRServoRight.setDirection(CRServo.Direction.FORWARD);

        intakeServokicker.setDirection(CRServo.Direction.FORWARD);

        stageOneServo.setDirection(CRServo.Direction.FORWARD);

        //The Default. Need This to actually have something declared for the state machine. If removed will not be able to know where to start. Its the starting varible.

        shootState = ShootState.NOBALL;

        range = Range.SHORT;


        //___________________________________________________________________BREAK__________________


        //Drive Motors
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Launch Motors
        intakeMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcherMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcherMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


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


        if (shootState != ShootState.NOBALL) {
            if (gamepad1.back){
                shootState = ShootState.ALLGO;
            }
        }

//add if statemnt to no ball if it doesnt work


          //CAMERA CODE

     //  CONFIG IS NOT CONFIG FOR IT IF ENABLED AND ASKS WHAT IS A HUSKYLENS DO /* and /* TO DISABLE IT

         ElapsedTime myElapsedTime;
            HuskyLens.Block[] myHuskyLensBlocks;
            HuskyLens.Block myHuskyLensBlock;

            // Put initialization blocks here
            telemetry.update();
            myElapsedTime = new ElapsedTime();

            myHuskyLensBlocks = huskylens.blocks();

            telemetry.addLine("ShootState" +" "+ shootState);
            telemetry.addLine("Range" +" "+ range);

            telemetry.addLine();
            telemetry.addLine();
            telemetry.addData("Block count", JavaUtil.listLength(myHuskyLensBlocks));




        for (HuskyLens.Block myHuskyLensBlock_item : myHuskyLensBlocks) {
                myHuskyLensBlock = myHuskyLensBlock_item;
                telemetry.addData("Tag", "id=" + myHuskyLensBlock.id + " size: " + myHuskyLensBlock.width + "x" + myHuskyLensBlock.height + " position: " + myHuskyLensBlock.x + "," + myHuskyLensBlock.y);

            telemetry.update();

            }





        switch (shootState) {
            case NOBALL:
                //WARM UP Shooters


                launcherMotorLeft.setPower(0);
                launcherMotorRight.setPower(0);

                intakeCRServoRight.setPower(0);
                intakeCRServoLeft.setPower(0);

                launcherCRServoLeft.setPower(0);
                launcherCRServoRight.setPower(0);


                intakeMotorLeft.setPower(0);
                intakeServokicker.setPower(0);

                stageOneServo.setPower(0);

                if (gamepad1.a){
                    shootState=ShootState.ONEBALL;

                }

                break;



            case ONEBALL:

                intakeCRServoRight.setPower(1);
                intakeCRServoLeft.setPower(1);

                launcherCRServoLeft.setPower(1);
                launcherCRServoRight.setPower(1);


                intakeMotorLeft.setPower(1);
                intakeServokicker.setPower(-1);

                stageOneServo.setPower(1);

                if (gamepad1.x){
                    shootState=ShootState.TWOBALLS;

                }

                break;

            case TWOBALLS:

                intakeCRServoRight.setPower(1);
                intakeCRServoLeft.setPower(1);

                launcherCRServoLeft.setPower(0);
                launcherCRServoRight.setPower(0);


                intakeMotorLeft.setPower(1);
                intakeServokicker.setPower(-1);

                stageOneServo.setPower(1);

                if (gamepad1.y){
                    shootState=ShootState.THREEBALLS;

                }

                break;
            case THREEBALLS:

                intakeCRServoRight.setPower(0);
                intakeCRServoLeft.setPower(0);

                launcherCRServoLeft.setPower(0);
                launcherCRServoRight.setPower(0);


                intakeMotorLeft.setPower(1);
                intakeServokicker.setPower(-1);

                stageOneServo.setPower(0);

                if (gamepad1.b){
                    shootState=ShootState.ALLGO;

                }

                break;

            case ALLGO:

                intakeCRServoRight.setPower(1);
                intakeCRServoLeft.setPower(1);

                launcherCRServoLeft.setPower(1);
                launcherCRServoRight.setPower(1);


                intakeMotorLeft.setPower(1);

                intakeServokicker.setPower(1);

                stageOneServo.setPower(1);

                if (gamepad1.a){
                    shootState=ShootState.ONEBALL;

                }
                break;
        }

        switch (range) {

            case SHORT:
                launcherMotorLeft.setPower(0.5);
                launcherMotorRight.setPower(0.5);

                if(gamepad1.dpad_up){
                   range=Range.FAR;
                }

                break;

            case FAR:
                launcherMotorLeft.setPower(0.7);
                launcherMotorRight.setPower(0.7);

                if(gamepad1.dpad_down){
                    range=Range.SHORT;
                }

                break;


        }
    }
}

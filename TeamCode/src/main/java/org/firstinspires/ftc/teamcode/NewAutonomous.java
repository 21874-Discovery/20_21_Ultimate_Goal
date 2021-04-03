package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "OptimizedAuto", group = "team")

public class NewAutonomous extends LinearOpMode {

    //400 encoder rotations = 1 foot

    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BackRight;
    DcMotor BackLeft;
    DcMotor Launcher;
    DcMotor Pickup; //intakes rings from playing field
    Servo Flick; //flicks rings from the intake to the launcher

    double Flick_Power;
    public final static double ARM_FLICKED = 0.4;
    public final static double ARM_HOME = 0; //sets the starting position for the servo. it will go to this position when robot starts
    public final static double ARM_MIN_RANGE = 0;
    public final static double ARM_MAX_RANGE = 1;
    double FlickPosition = ARM_HOME;

    double RobotDiameter = 20; //Max robot size is 18x18 with max diagonal width of 25.46 in)
    //Robot spins in a circle, rough diameter of robot's circle can be no more than 25.42 (diagonal)
    double RobotCircumference = RobotDiameter * 3.14;//Max circumference of Robot (d * pi) = 80 in
    double WheelSize = 4;  //diameter in inches of wheels (the engineers like 4in)
    double WheelCircumference = WheelSize*3.14; //Circumference (d * pi) of wheel (distance wheel travels for 1 rotation)
    double RotationsPerCircle = RobotCircumference/WheelCircumference;// wheel rotations to turns in complete circle

    int DriveTicks = 480;  //1 wheel rotation = DriveTicks - based on motor and gear ratio  => 1 Tetrix DC motor 60:1 revolution = 1440 encoder ticks (20:1 = 480 ticks (divide by 60/20) or 400 ticks = 1 foot)
    //DriveTicks * RotationsPerCircle = 360 degrees
    //Rotations per degree
    int TicksPerDegree = (int) Math.round((DriveTicks * RotationsPerCircle)/360);

    int currentstep = 0;

    public void runOpMode() {
        TopLeft = hardwareMap.dcMotor.get("TL");
        TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        //   TopLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        TopRight = hardwareMap.dcMotor.get("TR");
        TopRight.setDirection(DcMotorSimple.Direction.REVERSE);

        BackRight = hardwareMap.dcMotor.get("BR");
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        BackLeft = hardwareMap.dcMotor.get("BL");
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        Launcher = hardwareMap.dcMotor.get("L");
        Launcher.setDirection(DcMotorSimple.Direction.REVERSE);

        Pickup =hardwareMap.dcMotor.get("P");
        Pickup.setDirection(DcMotorSimple.Direction.FORWARD);

        TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Flick = hardwareMap.servo.get("F");
        Flick.setDirection(Servo.Direction.FORWARD);
        Flick_Power = 0.2;
        Flick.setPosition(ARM_HOME);

        TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Pickup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        TopLeft.setTargetPosition(2250);
        TopRight.setTargetPosition(1800);
        BackLeft.setTargetPosition(1800);
        BackRight.setTargetPosition(2250);
        Launcher.setTargetPosition(5000);
        Pickup.setTargetPosition(5000);
        //1 Tetrix DC motor 60:1 revolution = 1440 encoder ticks

        waitForStart();
        while (opModeIsActive()) {

            if (currentstep == 0) {
                currentstep++;
            }

            if (currentstep == 1) {
                //drive forward, deliver B
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                OmniTurn("Right",0.125,15);
                OmniDrive("Forward",0.125,2500);

                currentstep++;
            }

            if (currentstep == 2) {
                //Turn launcher motor on
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                Launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                Launcher.setPower(1);
                sleep(1000);

                currentstep++;
            }

            if (currentstep == 3) {
                //Back up behind line
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

               OmniTurn("Left",0.125,12);
               OmniDrive("Backward",0.25,100);
               OmniDrive("Left",0.25,400);  //600
                OmniDrive("Backward",0.25,1000);

                currentstep++;
            }

            if (currentstep == 4) {
                //Launch rings
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                for (int i = 0; i < 3 && opModeIsActive(); i++) {
                    telemetry.addData("Loop", i);
                    telemetry.update();

                    FlickPosition = (ARM_FLICKED);
                    FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                    Flick.setPosition(FlickPosition);
                    //Flick.setPower(1);
                    sleep (500);
                    //CR2.setPower(0);
                    FlickPosition = (ARM_HOME);
                    FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                    Flick.setPosition(FlickPosition);
                    OmniTurn("Left",0.25,3);
                    sleep (500);


                    telemetry.addData("end of loop", "");
                    telemetry.update();

                }

                currentstep++;
            }

            if (currentstep == 5) {
                //Turn pickup on

                /*Pickup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                Pickup.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                Pickup.setPower(1);*/

                currentstep++;
            }

            if (currentstep == 6){
                //launch more rings
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

               /* for (int i = 0; i < 3 && opModeIsActive(); i++) {
                    telemetry.addData("Loop", i);
                    telemetry.update();

                    FlickPosition = (ARM_FLICKED);
                    FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                    Flick.setPosition(FlickPosition);
                    //Flick.setPower(1);
                    sleep (500);
                    //CR2.setPower(0);
                    FlickPosition = (ARM_HOME);
                    FlickPosition = Range.clip(FlickPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
                    Flick.setPosition(FlickPosition);
                    sleep (500);


                    telemetry.addData("end of loop", "");
                    telemetry.update();

                }
                */

                currentstep++;
            }

            if (currentstep == 7){
                //turn off launcher and pickup
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                Launcher.setPower(0);

                Pickup.setPower(0);

                currentstep ++;
            }

            if (currentstep == 8){
                //drive back to wall
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                //OmniTurn("Right",0.25,5);
                OmniDrive("Backward",0.25,1320);

                currentstep++;
            }

            if (currentstep == 9){
                //Drive right, line up with wobble A
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                OmniDrive("Right",0.25,530);

                currentstep ++;
            }

            if (currentstep == 10) {
                //Drive forward and deliver to box A
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                OmniTurn("Right",0.125,20);
                OmniDrive("Forward",0.125,2100);
                OmniDrive("Backward",0.125,100);
                OmniDrive("Left", 0.125, 800);
                OmniDrive("Forward",0.125, 150);
                currentstep++;
            }

            /*if (currentstep == 11){
                //Park
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                OmniDrive("Backward",0.25,100);
                OmniDrive("Left",0.25,400);

                currentstep++;
            }

            if (currentstep == 12){
                //Launching
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                currentstep++;
            }

            if (currentstep == 13){
                //Actually park
                telemetry.addData("inside currentstep:", currentstep);
                telemetry.update();

                OmniDrive("Forward",0.25,100);

                currentstep++;
            }*/

        }

    }

    public void OmniDrive(String Dir, double Spd, int Dist) {
        telemetry.addData("Direction", Dir);
        telemetry.update();

        TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        switch (Dir) {
            case "Forward":
                TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            case "Backward":
                TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                TopRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            case "Left":
                TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                break;
            case "Right":
                TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                TopRight.setDirection(DcMotorSimple.Direction.FORWARD);
                BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
                BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
            default:
                telemetry.addData("Invalid Direction", Dir);
                telemetry.update();
                return;
        }
        Dist=Math.abs(Dist);
        TopLeft.setTargetPosition(Dist);
        TopRight.setTargetPosition(Dist);
        BackLeft.setTargetPosition(Dist);
        BackRight.setTargetPosition(Dist);

        TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Spd=Range.clip(Spd, 0, 1);
        TopLeft.setPower(Spd);
        TopRight.setPower(Spd);
        BackLeft.setPower(Spd);
        BackRight.setPower(Spd);



        while (opModeIsActive() && TopLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", TopLeft.getCurrentPosition() + "  busy=" + TopLeft.isBusy());
            telemetry.addData("encoder-fwd-right", TopRight.getCurrentPosition() + "  busy=" + TopRight.isBusy());
            telemetry.update();
            idle();
        }
        //stop
        TopLeft.setPower(0);
        TopRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

    }


    public void OmniTurn(String DirT, double SpdT, int Deg) {
        //Ugh even more Math!!
        //number of ticks for a turn of 1 degree TicksPerDegree
        //if I want to turn more than 1 degree; multiple by number of degrees I want to turn
        //had to convert Rotate to int to use in setTargetPosition below
        int Rotate = (int) Math.round(Deg * TicksPerDegree);
        telemetry.addData("Rotating", Rotate + " ticks or " +Deg + " degrees");
        telemetry.update();

        TopLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TopRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if(DirT.equals("Left")) {
            TopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            TopRight.setDirection(DcMotorSimple.Direction.REVERSE);
            BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
            BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        }
        if(DirT.equals("Right")) {
            TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            TopRight.setDirection(DcMotorSimple.Direction.FORWARD);
            BackRight.setDirection(DcMotorSimple.Direction.FORWARD);
            BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        Rotate=Math.abs(Rotate);
        TopLeft.setTargetPosition(Rotate);
        TopRight.setTargetPosition(Rotate);
        BackLeft.setTargetPosition(Rotate);
        BackRight.setTargetPosition(Rotate);

        TopLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TopRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        SpdT=Range.clip(SpdT, 0, 1);
        TopLeft.setPower(SpdT);
        TopRight.setPower(SpdT);
        BackLeft.setPower(SpdT);
        BackRight.setPower(SpdT);



        while (opModeIsActive() && TopLeft.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", TopLeft.getCurrentPosition() + "  busy=" + TopLeft.isBusy());
            telemetry.addData("encoder-fwd-right", TopRight.getCurrentPosition() + "  busy=" + TopRight.isBusy());
            telemetry.update();
            idle();
        }
        //stop
        TopLeft.setPower(0);
        TopRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

    }
}
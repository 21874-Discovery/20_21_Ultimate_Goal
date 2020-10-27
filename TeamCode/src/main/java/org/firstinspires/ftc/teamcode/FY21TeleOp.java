package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import static android.os.SystemClock.sleep;

@TeleOp(name = "TeleOp_Code", group = "team")
public class FY21TeleOp extends OpMode {

    //define motors and assign type
    DcMotor TopLeft;
    DcMotor TopRight;
    DcMotor BottomRight;
    DcMotor BottomLeft;
    DcMotor Launcher;
    DcMotor Pickup;

    //define variables and assign type
    double drivepower = 1;
    double Top_Left_Power;
    double Top_Right_Power;
    double Bottom_Right_Power;
    double Bottom_Left_Power;

    public void init() {
        //assigning motor variables to hardware (as defined on phone)
        TopLeft = hardwareMap.dcMotor.get("TL");
        TopLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        TopRight = hardwareMap.dcMotor.get("TR");
        TopRight.setDirection(DcMotorSimple.Direction.FORWARD);

        BottomLeft = hardwareMap.dcMotor.get("BL");
        BottomLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        BottomRight = hardwareMap.dcMotor.get("BR");
        BottomRight.setDirection(DcMotorSimple.Direction.FORWARD);

        Launcher = hardwareMap.dcMotor.get("L");
        Launcher.setDirection(DcMotorSimple.Direction.FORWARD);

        Pickup = hardwareMap.dcMotor.get("P");
        Pickup.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public void loop() {
        //telemetry means any data you want to send to a phone
      telemetry.addData("Inside Loop","");
      telemetry.update();

              //calculating where the robot is going to move based on the position of the joystick
              //left joystick is the direction and the right joystick is rotation
              Top_Left_Power = -gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x;
              Top_Right_Power = gamepad1.left_stick_y+gamepad1.left_stick_x+gamepad1.right_stick_x;
              Bottom_Left_Power = gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x;
              Bottom_Right_Power = -gamepad1.left_stick_y-gamepad1.left_stick_x+gamepad1.right_stick_x;


            // 
            Top_Left_Power = Range.clip(Top_Left_Power,-1,1);
            Top_Right_Power = Range.clip(Top_Right_Power,-1,1);
            Bottom_Right_Power = Range.clip(Bottom_Right_Power,-1,1);
            Bottom_Left_Power = Range.clip(Bottom_Left_Power,-1,1);

            TopLeft.setPower(Top_Left_Power);
            TopRight.setPower(Top_Right_Power);
            BottomRight.setPower(Bottom_Right_Power);
            BottomLeft.setPower(Bottom_Left_Power);

            if (gamepad2.y) {
            Launcher.setPower(0.5);
            sleep(1000);
            Launcher.setPower(0);
        }
            if (!gamepad2.y) {
            Launcher.setPower(0);

         
        }





    }


}




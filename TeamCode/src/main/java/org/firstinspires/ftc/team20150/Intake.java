package org.firstinspires.ftc.team20150;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import static java.lang.Thread.sleep;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private final DcMotor intakeMotor;
    private final String intakeCaption = "Intake.Motor";

    private final double intakeSpeed = 0.7;
    public Intake(HardwareMap hardwareMap, Telemetry telemetry) {
        intakeMotor = hardwareMap.get(DcMotor.class, "INTAKE");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setPower(0.0);
        telemetry.addData(intakeCaption,"Overjoyed");
    }

    public void run(){
        intakeMotor.setPower(intakeSpeed);
    }
    public void stop(){
        intakeMotor.setPower(0.0);
    }
    public void reverse(){
        intakeMotor.setPower(-intakeSpeed);
    }

    public void shortReverse() {
            this.reverse();
            try {
                sleep(200);
            } catch (InterruptedException ignored) {
            }
            this.run();
    }
}

package org.firstinspires.ftc.team20150;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name = "BioBuzz 2 Dr")
public class BioBuzzTestAuto extends BaseAutoOpMode {

    private Shooter shooter;
    private Intake gatekeeper;


    @Override
    public void driveForwardInches(double speed, double distanceInches, double timeoutSec) {
        super.driveForwardInches(speed, distanceInches, timeoutSec);
    }






    @Override
    public void runOpMode() {
        super.runOpMode();
        shooter = new Shooter(hardwareMap,telemetry);
        gatekeeper = new Intake(hardwareMap,telemetry);


        waitForStart();

        driveForwardInches(0.5,30,0);
        sleep(2000);
        driveForwardInches(0.5,28,0);


    }
}
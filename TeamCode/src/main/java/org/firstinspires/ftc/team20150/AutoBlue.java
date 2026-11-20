package org.firstinspires.ftc.team20150;

public class AutoBlue extends BaseAutoOpMode{
    private Shooter shooter;

    private Intake intake;

    @Override
    public void runOpMode() {
        super.runOpMode();
        shooter = new Shooter(hardwareMap,telemetry);
        intake = new Intake(hardwareMap,telemetry);
        shooter.nextFlywheelMode();
        intake.run();

        waitForStart();
        shooter.setCurrentSpeed(0.45);



        driveForwardInches(0.5, 55, 10);


        sleep(1000);


        shooter.stop();
    }
}

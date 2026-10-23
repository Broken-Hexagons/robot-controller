
# FTC BioBuzz 2026 — Broken Hexagons (Team 20150)

Repository for **Broken Hexagons (Team 20150)** competing in the FIRST Tech Challenge (FTC) 2025–2026 season (**BioBuzz 2026**).

---

## 🚀 Overview

The `TeamCode` module contains custom autonomous and teleoperated OpModes, subsystem controllers, and drivetrain algorithms developed for the Broken Hexagons robot. The architecture emphasizes modularity, precision driving, and intuitive two-driver control during matches.

---

## ⚙️ Key Features & Architecture

### 🛡️ Core Subsystems
- **Shooter (`Shooter.java`)**: 
  - Manages the flywheel shooter motor (`FLYWHEEL`) and element gatekeeper (`GATEKEEPER`).
  - **Preset Modes**: Pre-calibrated shooting speeds (`DUNK`, `FREETHROW`, `THREEPOINTER`).
  - **FLEX Mode**: Allows real-time incrementing/decrementing of flywheel power.
  - **Safety**: Includes speed clamping (0.0 – 0.8 max power) and automated telemetry/voice notifications.
- **Intake (`Intake.java`)**:
  - Manages the intake mechanism (`INTAKE`).
  - Features forward intake and reverse eject capabilities.

### 🏎️ Drivetrain & Drive Modes
- **TeleOp Drivetrain Base (`BaseTeleOpMode.java`)**:
  - 4-wheel drive system mapped to Gamepad 1.
  - Trigonometric angle snapping, magnitude processing, and differential wheel power scaling.
  - Speed toggling via bumpers: **Standard (50%)**, **Fast (75%)**, and **Slow (25%)**.
- **Autonomous Drive Base (`BaseAutoOpMode.java`)**:
  - Encoder-calculated movement functions: `driveForwardInches`, `strafeLeftInches`, `strafeRightInches`, and `turnLeftDegrees`/`turnRightDegrees`.
  - Closed-loop motor control (`RUN_TO_POSITION`).

---

## 🎮 OpModes

- **`BioBuzzTwoDriverMode`** (`BioBuzz 2 Driver TeleOp`):
  - **Driver 1 (Gamepad 1)**: Drivetrain controls (steering, strafing, speed modes).
  - **Driver 2 (Gamepad 2)**: Subsystem mechanisms (Intake reverse/run, Gatekeeper release/close, Flywheel preset & speed adjustments).

---

## 🔌 Hardware Configuration Map

| Component | Type | Hardware Name |
| :--- | :--- | :--- |
| **Front Left Motor** | `DcMotor` | `FL` |
| **Back Left Motor** | `DcMotor` | `BL` |
| **Front Right Motor** | `DcMotor` | `FR` |
| **Back Right Motor** | `DcMotor` | `BR` |
| **Flywheel Motor** | `DcMotor` | `FLYWHEEL` |
| **Gatekeeper Motor/Servo** | `DcMotor` | `GATEKEEPER` |
| **Intake Motor** | `DcMotor` | `INTAKE` |

---

## 📂 Package Structure

```
TeamCode/src/main/java/org/firstinspires/ftc/team20150/
├── BaseAutoOpMode.java         # Autonomous movement and encoder drive helper methods
├── BaseTeleOpMode.java         # TeleOp drivetrain joystick mapping and speed scaling
├── BioBuzzTwoDriverMode.java   # Main 2-driver TeleOp OpMode
├── Intake.java                 # Intake subsystem logic
└── Shooter.java                # Flywheel and gatekeeper shooter subsystem logic
```

---

## 🛠️ Building & Deploying

1. Open this project in **Android Studio**.
2. Connect the **FTC Robot Controller** phone or Control Hub via Wi-Fi Direct or USB.
3. Select the `TeamCode` run configuration and click **Run** to deploy to the robot controller.


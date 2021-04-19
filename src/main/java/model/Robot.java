package model;

import javax.vecmath.Vector2d;

public class Robot {
    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private volatile double robotDirection = 0;

    private final double velocity = 1.0;
    private final double maxAngularVelocity = 0.001;

    private Vector2d rotation;

    public Robot(Vector2d rotation) {
        this.rotation = rotation;
    }
    

    public double getRobotPositionX() {
        return robotPositionX;
    }

    public void setRobotPositionX(double robotPositionX) {
        this.robotPositionX = robotPositionX;
    }

    public double getRobotPositionY() {
        return robotPositionY;
    }

    public void setRobotPositionY(double robotPositionY) {
        this.robotPositionY = robotPositionY;
    }

    public double getRobotDirection() {
        return robotDirection;
    }

    public void setRobotDirection(double robotDirection) {
        this.robotDirection = robotDirection;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getMaxAngularVelocity() {
        return maxAngularVelocity;
    }

    public Vector2d getRotation() {
        return rotation;
    }

    public void setRotation(Vector2d rotation) {
        this.rotation = rotation;
    }
}

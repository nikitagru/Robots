package model;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Robot {
    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private volatile double robotDirection = 0;

    private final double velocity = 5.0;
    private final double maxAngularVelocity = 0.001;

    private BufferedImage robotImage = null;

    private int rotation;

    public Robot() {
        try {
            ClassLoader cl = getClass().getClassLoader();
            robotImage = ImageIO.read(new File(cl.getResource("wall.png").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public BufferedImage getRobotImage() {
        return robotImage;
    }
}

package model;

public class LevelModel {
    public boolean isFinished(RobotController robotController, double finishX, double finishY, int linkSize) {
        if (robotController.getRobot().getRobotPositionX() <= finishX + linkSize
                && robotController.getRobot().getRobotPositionX() >= finishX - linkSize
                && robotController.getRobot().getRobotPositionY() <= finishY + linkSize
                && robotController.getRobot().getRobotPositionY() >= finishY - linkSize) {
            return true;
        }
        return false;
    }
}

package model;

import gui.LevelPresenter;
import gui.Wall;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;

public class LevelController {

    private double finishX;
    private double finishY;
    private int linkSize = 32;

    public int getLinkSize() {
        return linkSize;
    }

    public double getFinishX() {
        return finishX;
    }

    public double getFinishY() {
        return finishY;
    }

    public void setFinishX(int levelX, int levelGapX) {
        this.finishX = levelX * linkSize + levelGapX;
    }

    public void setFinishY(int levelY, int levelGapY) {
        this.finishY = levelY * linkSize + levelGapY;
    }

    public boolean isFinished(RobotController robotController, double finishX, double finishY, int linkSize) {
        if (robotController.getRobot().getRobotPositionX() <= finishX + linkSize
                && robotController.getRobot().getRobotPositionX() >= finishX - linkSize
                && robotController.getRobot().getRobotPositionY() <= finishY + linkSize
                && robotController.getRobot().getRobotPositionY() >= finishY - linkSize) {
            return true;
        }
        return false;
    }

    public void drawComponent(LevelPresenter levelMain, int i, int j) {

        if (levelMain.getLevel()[i][j] == 1) {
            Wall wall = new Wall(j * 32 + levelMain.getLevelGapX(),
                    i * 32 + levelMain.getLevelGapY());
            wall.paintComponent(levelMain.getGraphics());
        } else if (levelMain.getLevel()[i][j] == 2) {
            levelMain.getRobotController().getRobot().setRobotPositionX(j * 32 + levelMain.getLevelGapX());
            levelMain.getRobotController().getRobot().setRobotPositionY(i * 32 + levelMain.getLevelGapY());
            levelMain.setLevelPoint(i, j, 0);
        }
    }

    public void changeLevel(LevelPresenter level) {
        if (isFinished(level.getRobotController(), finishX, finishY, linkSize)) {
            level.setLevelNum(level.getLevelNum() + 1);
            if (level.getLevelNum() == level.getGameLevels().getLevelsCount() + 1) {
                level.setVisible(false);
                level.getMainWindow().setVisible(true);
                level.getMainWindow().revalidate();
            }
            level.setLevel(level.getGameLevels().getLevel(level.getLevelNum()));
            level.getCurrentPlayer().setLevel(level.getLevelNum());
            try {
                UserSerialization.userSer(level.getCurrentPlayer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

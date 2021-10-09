package model;

import gui.GameLevels;
import gui.LevelPresenter;
import gui.Wall;

import java.io.IOException;

public class LevelController {

    private double finishX;
    private double finishY;
    private int levelGapX = 650;
    private int levelGapY = 300;
    private int linkSize = 32;
    private int levelNum;

    private UsersProfile usersProfile;

    private GameLevels gameLevels = new GameLevels();
    private RobotController robotController = new RobotController();

    public LevelController(UsersProfile usersProfile) {
        this.usersProfile = usersProfile;
    }

    public GameLevels getGameLevels() {
        return gameLevels;
    }

    public RobotController getRobotController() {
        return robotController;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public UsersProfile getUsersProfile() {
        return usersProfile;
    }

    public void setUsersProfile(UsersProfile usersProfile) {
        this.usersProfile = usersProfile;
    }

    public int getLevelGapX() {
        return levelGapX;
    }

    public void setLevelGapX(int levelGapX) {
        this.levelGapX = levelGapX;
    }

    public int getLevelGapY() {
        return levelGapY;
    }

    public void setLevelGapY(int levelGapY) {
        this.levelGapY = levelGapY;
    }

    public int getLinkSize() {
        return linkSize;
    }

    public double getFinishX() {
        return finishX;
    }

    public double getFinishY() {
        return finishY;
    }

    public void setFinishX(int levelX) {
        this.finishX = levelX * linkSize + levelGapX;
    }

    public void setFinishY(int levelY) {
        this.finishY = levelY * linkSize + levelGapY;
    }

    public boolean isFinished() {
        if (robotController.getRobot().getRobotPositionX() <= finishX + linkSize
                && robotController.getRobot().getRobotPositionX() >= finishX - linkSize
                && robotController.getRobot().getRobotPositionY() <= finishY + linkSize
                && robotController.getRobot().getRobotPositionY() >= finishY - linkSize) {
            return true;
        }
        return false;
    }

    public void changeRobotDirection() {
        if (robotController.getRobot().getRobotPositionX()
                > robotController.getTargetPositionX()) {
            robotController.getRobot().setRobotDirection(1);
        } else {
            robotController.getRobot().setRobotDirection(0);
        }
    }

    public void drawComponent(LevelPresenter levelMain, int i, int j) {

        if (levelMain.getLevel()[i][j] == 1) {
            Wall wall = new Wall(j * 32 + levelGapX,
                    i * 32 + levelGapY);
            wall.paintComponent(levelMain.getGraphics());
        } else if (levelMain.getLevel()[i][j] == 2) {
            robotController.getRobot().setRobotPositionX(j * 32 + levelGapX);
            robotController.getRobot().setRobotPositionY(i * 32 + levelGapY);
            levelMain.setLevelPoint(i, j, 0);
        }
    }

    public void changeLevel(LevelPresenter level) {
        if (isFinished()) {
            levelNum++;
            if (levelNum == gameLevels.getLevelsCount() + 1) {
                level.setVisible(false);
                level.getMainWindow().setVisible(true);
                level.getMainWindow().revalidate();
            }
            level.setLevel(gameLevels.getLevelArray(levelNum));

            setFinishX(gameLevels.getCurrentLevel(levelNum).getFinishX());
            setFinishY(gameLevels.getCurrentLevel(levelNum).getFinishY());

            usersProfile.setLevel(levelNum);
            try {
                UserSerialization.userSer(usersProfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

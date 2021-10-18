package model;

import gui.GameLevels;
import gui.LevelPresenter;
import gui.Wall;

import java.io.IOException;

public class LevelController {
    private int levelGapX = 650;
    private int levelGapY = 300;
    private int levelNum;

    private UsersProfile usersProfile;

    private GameLevels gameLevels = new GameLevels();

    public LevelController(UsersProfile usersProfile) {
        this.usersProfile = usersProfile;
    }

    public GameLevels getGameLevels() {
        return gameLevels;
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

    public void changeLevel(LevelPresenter level) {
        levelNum++;
        if (levelNum == gameLevels.getLevelsCount() + 1) {
            level.setVisible(false);
            level.getMainWindow().setVisible(true);
            level.getMainWindow().revalidate();
        }

        usersProfile.setLevel(levelNum);
        try {
            UserSerialization.userSer(usersProfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

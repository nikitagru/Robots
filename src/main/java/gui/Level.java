package gui;

public class Level {
    private int[][] level;
    private int finishX;
    private int finishY;
    private int linkSize = 32;

    public Level(int[][] level) {
        this.level = level;
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[0].length; j++) {
                if (level[i][j] == 3) {
                    setFinish(j, i);
                }
            }
        }
    }

    public int getLinkSize() {
        return linkSize;
    }

    public void setLinkSize(int linkSize) {
        this.linkSize = linkSize;
    }

    public int[][] getLevel() {
        return level;
    }

    public void setLevel(int[][] level) {
        this.level = level;
    }

    public int getFinishX() {
        return finishX;
    }

    public int getFinishY() {
        return finishY;
    }

    public void setFinish(int finishX, int finishY) {
        this.finishX = finishX * linkSize;
        this.finishY = finishY * linkSize;
    }
    public void setLevelPoint(int x, int y, int value) {
        level[x][y] = value;
    }


}

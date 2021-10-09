package gui;

public class Level {
    private int[][] level;
    private int finishX;
    private int finishY;

    public Level(int[][] level) {
        this.level = level;
        setFinish();
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

    public void setFinishX(int finishX) {
        this.finishX = finishX;
    }

    public int getFinishY() {
        return finishY;
    }

    public void setFinishY(int finishY) {
        this.finishY = finishY;
    }

    private void setFinish() {
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[0].length; j++) {
                if (level[i][j] == 3) {
                    setFinishX(j);
                    setFinishY(i);
                }
            }
        }
    }
}

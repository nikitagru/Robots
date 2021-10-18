package gui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameLevels{
    private List<Level> levels = new ArrayList<>();

    public GameLevels() {
        Gson gson = new Gson();

        ClassLoader cl = getClass().getClassLoader();

        File folder = new File(Objects.requireNonNull(cl.getResource("levelMaps/")).getPath());

        File[] files = folder.listFiles();

        for (File file : files) {
            JsonReader reader = null;
            try {
                reader = new JsonReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Level level = new Level(gson.fromJson(reader, int[][].class));

            levels.add(level);
        }
    }

    public int[][] getLevelArray(int currentLevel) {
        return levels.get(currentLevel).getLevel();
    }

    public Level getCurrentLevel(int levelNum) {
        return levels.get(levelNum);
    }

    public int getLevelsCount() {
        return levels.size();
    }
}

package gui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GameLevels{
    private List<int[][]> levels = new ArrayList<>();


    public GameLevels() throws FileNotFoundException {
        Gson gson = new Gson();

        File folder = new File("F:\\JavaProjects\\OOP\\Robots\\robots\\src\\main\\resources\\levelMaps\\");

        File[] files = folder.listFiles();

        for (File file : files) {
            JsonReader reader1 = new JsonReader(new FileReader(file));

            levels.add(gson.fromJson(reader1, int[][].class));
        }
    }

    public int[][] getLevel(int currentLevel) {
        return levels.get(currentLevel);
    }

    public int getLevelsCount() {
        return levels.size();
    }
}

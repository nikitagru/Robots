package gui;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame
{
    private JFrame frame = new JFrame();

    private JButton NewGame = new JButton("Новая игра");
    private JButton LoadingGame = new JButton("Загрузить игру");
    private JButton Settings = new JButton("Настройки");
    private JButton Exit = new JButton("Выход");
    private JComboBox<UsersProfile> userProfileJComboBox = new JComboBox<>();

    private LevelPresenter level;

    private UsersProfile usersProfile;
    
    public void MakeWindow() throws IOException, ClassNotFoundException {
        JPanel panel = new JPanel();

        NewGame.addActionListener(e -> {
            usersProfile = (UsersProfile) userProfileJComboBox.getSelectedItem();

            if (usersProfile == null) {
                String result = JOptionPane.showInputDialog("Введите ваше имя");
                usersProfile = new UsersProfile(result);
            }

            level = createLevelPresenter(0);
            frame.add(level);
            frame.setVisible(true);

            level.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            level.setVisible(true);
            this.setVisible(false);
        });

        LoadingGame.addActionListener(e -> {
            usersProfile = (UsersProfile) userProfileJComboBox.getSelectedItem();

            if (usersProfile == null) {
                String result = JOptionPane.showInputDialog("Введите ваше имя");
                usersProfile = new UsersProfile(result);
            }

            level = createLevelPresenter(usersProfile.getLevel());
            frame.add(level);
            frame.setVisible(true);

            level.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            level.setVisible(true);
            this.setVisible(false);

        });


        Settings.addActionListener(e -> {

        });

        Exit.addActionListener(e -> System.exit(1));
        UserSerialization.getProfiles(userProfileJComboBox);
        panel.add(NewGame);
        panel.add(LoadingGame);
        panel.add(Settings);
        panel.add(Exit);
        userProfileJComboBox.addItem(null);
        panel.setBackground(Color.BLUE);
        panel.add(userProfileJComboBox);
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }

    private LevelPresenter createLevelPresenter(int levelNum) {
        LevelController levelController = new LevelController(usersProfile);
        levelController.setLevelNum(levelNum);
        try {
            return new LevelPresenter(frame, levelController);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


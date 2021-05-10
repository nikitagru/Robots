package gui;
import model.UserDeser;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame
{
    static JFrame frame = new JFrame();

    private JButton NewGame = new JButton("Новая игра");
    private JButton LoadingGame = new JButton("Загрузить игру");
    private JButton Settings = new JButton("Настройки");
    private JButton Exit = new JButton("Выход");
    private JComboBox<UsersProfile> userProfileJComboBox = new JComboBox<>();

    public void MakeWindow() throws IOException, ClassNotFoundException {
        JPanel panel = new JPanel();

        NewGame.addActionListener(e -> {
            UsersProfile usersProfile = (UsersProfile) userProfileJComboBox.getSelectedItem();

            if (usersProfile == null) {
                String result = JOptionPane.showInputDialog("Введите ваше имя");
                usersProfile = new UsersProfile(result);
            }
            Level level = null;
            try {
                level = new Level(0, frame, usersProfile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            frame.add(level);
            frame.setVisible(true);

            level.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            level.setVisible(true);
            this.setVisible(false);
        });

        LoadingGame.addActionListener(e -> {
            UsersProfile usersProfile = (UsersProfile) userProfileJComboBox.getSelectedItem();

            if (usersProfile == null) {
                String result = JOptionPane.showInputDialog("Введите ваше имя");
                usersProfile = new UsersProfile(result);
            }

            Level level = null;
            try {
                level = new Level(usersProfile.getLevel(), frame, usersProfile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            frame.add(level);
            frame.setVisible(true);

            level.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            level.setVisible(true);
            this.setVisible(false);
        });



        Settings.addActionListener(e -> {

        });

        Exit.addActionListener(e -> System.exit(1));
        checkProfiles();
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

    private void checkProfiles() throws IOException, ClassNotFoundException {
        File folder = new File("F:\\JavaProjects\\OOP\\Robots\\robots\\src\\main\\resources\\profiles\\");

        File[] files = folder.listFiles();

        UserDeser userDeser = new UserDeser();

        if (files != null) {
            for (File file : files) {
                userProfileJComboBox.addItem(userDeser.userDeser(file));
            }
        }
    }
}

// Исправить зоны ответственности, записать конфиги уровней в ресурсы

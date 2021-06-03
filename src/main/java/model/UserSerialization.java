package model;

import javax.swing.*;
import java.io.*;

public class UserSerialization {

    private static String savePath = "F:\\profiles\\";

    public static UsersProfile userDeser(File file)  {

        FileInputStream fileInputStream;
        UsersProfile usersProfile = null;
        try {
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            usersProfile = (UsersProfile) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return usersProfile;
    }

    public static void userSer(UsersProfile usersProfile) throws IOException
    {
        FileOutputStream outputStream = new FileOutputStream(savePath + usersProfile.getName() + ".ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // сохраняем игру в файл
        objectOutputStream.writeObject(usersProfile);

        //закрываем поток и освобождаем ресурсы
        objectOutputStream.close();
    }

    public static void getProfiles(JComboBox<UsersProfile> usersProfileJComboBox) {
        File folder = new File(savePath);

        File[] files = folder.listFiles();


        if (files != null) {
            for (File file : files) {
                usersProfileJComboBox.addItem(userDeser(file));
            }
        }
    }
}

package model;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
public class UserSer
{
    public static void userSer(UsersProfile usersProfile) throws IOException
    {
        FileOutputStream outputStream = new FileOutputStream("F:\\JavaProjects\\OOP\\Robots\\robots\\src\\main\\resources\\profiles\\save" + usersProfile.getName() + ".ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // сохраняем игру в файл
        objectOutputStream.writeObject(usersProfile);

        //закрываем поток и освобождаем ресурсы
        objectOutputStream.close();
    }
}

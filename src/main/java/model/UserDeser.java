package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
public class UserDeser
{
    private UsersProfile usersProfile;

    public UsersProfile userDeser(File file) throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        usersProfile = (UsersProfile) objectInputStream.readObject();
        objectInputStream.close();
        return usersProfile;
    }
}

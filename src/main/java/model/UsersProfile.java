package model;
import java.io.Serializable;
public class UsersProfile implements Serializable
{

    private static final long serialVersionUID = 1L;

    private int level;
    private String strName;

    public UsersProfile(String name) {
        this.strName = name;
        this.level = 1;
    }

    public UsersProfile(String name, int level) {
        this.strName = name;
        this.level = level;
    }

    public int getLevel()
    { return level; }
    public void setLevel(int level)
    { this.level = level; }
    public String getName()
    { return strName; }
    public void setName(String name)
    { this.strName = name;}

}
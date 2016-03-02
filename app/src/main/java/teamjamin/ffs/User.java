package teamjamin.ffs;

import java.util.Vector;

/**
 * Created by ilia on 3/1/2016.
 */
public class User implements Notifiable{
    public long userID;
    public String Username;
    public String password;
    public Vector<Item> notifications;

    public User(long userID, String Username, String password) {
        this.userID=userID;
        this.Username = Username;
        this.password=password;
    }

    public void update(Vector<Item> notifications){
        this.notifications=notifications;
    }



}

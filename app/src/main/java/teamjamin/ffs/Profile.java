package teamjamin.ffs;

/**
 * Created by ilia on 3/1/2016.
 */
public class Profile {
    private String Username;
    private String password;
    private boolean

    public Profile(String Username) {
        this.Username = Username;
    }

    public String getName(){
        return Username;
    }
    public void setName(String Username){
        this.Username=Username;
    }

    public void setPassword(String password){
        this.password=password;
    }

}

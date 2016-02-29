package teamjamin.ffs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jenny on 2/10/16.
 */
public class SettingsActivity extends AppCompatActivity {protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
}
    public void setNotifications_on() {

    }
    public void setNotifications_off(){

    }

    public boolean changeUsername(String username){
        return true;
    }
    public boolean changePassword(String password){
        return true;
    }

    public void deleteAccount(){

    }


}


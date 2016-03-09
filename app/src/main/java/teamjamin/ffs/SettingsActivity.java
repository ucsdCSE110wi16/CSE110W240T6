package teamjamin.ffs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jenny on 2/10/16.
 */
public class SettingsActivity extends PreferenceActivity {

    SharedPreferences sharedPreferences;
    String currEmail, currPassword;

    EditTextPreference email;
    PreferenceScreen logout;

    private static final int RESULT_SETTINGS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(currEmail == null) {
            currEmail = Config.EMAIL;
        } else {
            currEmail = sharedPreferences.getString("prefUsername", null);
        }

        logout = (PreferenceScreen) findPreference("prefLogout");
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Firebase ref = new Firebase("httpw://ffs.firebaseio.com/");
                if(ref.getAuth() != null) {
                    Map<String, Object> offline = new HashMap<String, Object>();
                    offline.put("/connection", "offline");
                    ref.child("users").child(ref.getAuth().getUid()).updateChildren(offline);

                    ref.unauth();
                    Toast.makeText(getApplicationContext(), "You've successfully logged out.", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_SETTINGS:
                updateUserSettings();
                break;
        }
    }

    private void updateUserSettings() {
        // TODO: updating logic here
        if(!currEmail.equals(Config.EMAIL)) {
            Config.EMAIL = currEmail;
            Config.updateEmail(currEmail);
        }
    }

}
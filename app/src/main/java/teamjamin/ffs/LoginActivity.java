package teamjamin.ffs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_GUEST_LOGIN = 0;

    private EditText _emailText, _passwordText;
    private Button _loginButton;
    private TextView _guestLogin, _signupLink;

    // private String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //***for Espresso testing. Comment this out when running app. Uncomment for testing***
        Firebase.setAndroidContext(this);

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        _guestLogin = (TextView) findViewById(R.id.guest_login);

        // Set up login button
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Set up signup button
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        // Set up guest login
        _guestLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start MainActivity
                Config.GUEST_LOGIN = true;
                onLoginSuccess();
            }
        });
    }

    /**
     * Method to perform login and authentication logic
     */
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //omit space
        email = email.trim();
        password = password.trim();

        // TODO: Implement authentication logic here.
        final Firebase authenticate = new Firebase("https://ffs.firebaseio.com/");
        authenticate.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {

                Firebase onlineRef = authenticate.child("users").child(authData.getUid());
                Map<String, Object> online = new HashMap<String, Object>();
                online.put("/connection", "online");
                onlineRef.updateChildren(online);

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                Config.GUEST_LOGIN = false;
                                onLoginSuccess();
                            }
                        }, 1500);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                final FirebaseError fError = firebaseError;
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                onLoginFailed();
                                Config.GUEST_LOGIN = false;

                                switch (fError.getCode()) {
                                    case FirebaseError.USER_DOES_NOT_EXIST:
                                        Toast.makeText(getApplicationContext(), "No user with these credentials exists.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case FirebaseError.INVALID_EMAIL:
                                        Toast.makeText(getApplicationContext(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case FirebaseError.INVALID_PASSWORD:
                                        Toast.makeText(getApplicationContext(), "Wrong Password. Please try again.", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        break;
                                }

                                progressDialog.dismiss();
                            }
                        }, 1500);
            }
        });

    }

    /**
     * On Activity result, perfrm login logic and launch appropriate actvity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: implement successful login stuff here
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    /**
     * Login successful
     */
    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    /**
     * Login failed, create toast to tell user.
     * Usual case is that account doesn't exist
     */
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    /**
     * Make sure email and password fits criteria
     * @return whether valid email and password is used
     */
    public boolean validate() {
        boolean validate = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // Makes sure whatever email used is an actual email address
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Please enter a valid email address.");
            validate = false;
        } else {
            _emailText.setError(null);
        }

        // Makes sure password length is met
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Must be between 4 and 10 alphanumeric characters.");
            validate = false;
        } else {
            _passwordText.setError(null);
        }

        return validate;
    }
}

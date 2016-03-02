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

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_GUEST_LOGIN = 0;

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;
    @Bind(R.id.guest_login)
    TextView _guestLogin;
    //@Bind(R.id.facebook_login) TextView _facebookLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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
        // By default, this fills in the info with guest info
        _guestLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start MainActivity
                // will loop back to login screen
                Config.GUEST_LOGIN = true;
                onLoginSuccess();
            }
        });

        // Set up facebook login button (might have to change later with facebook integration
        // By default, this fills in the info with guest info
        /*_facebookLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start MainActivity
                // Does same thing as guest login for now until facebook integration is done
                _emailText.setText("guest@guest.com");
                _passwordText.setText("password");
                login();
                Log.d(TAG, "Facebook Login");
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivityForResult(intent, REQUEST_GUEST_LOGIN);
            }
        });*/
    }

    /**
     * Method to perform login and authetication logic
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
        Firebase authenticate = new Firebase("https://ffs.firebaseio.com/");
        authenticate.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
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

        /**
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
         **/
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
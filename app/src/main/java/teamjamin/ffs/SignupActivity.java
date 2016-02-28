package teamjamin.ffs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import teamjamin.ffs.Chat_Function.ReferenceUrl;
import teamjamin.ffs.Chat_Function.ChatHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

import java.util.*;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        // Set up sign up button
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        // Set up login button
        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    /**
     * Method for signup logic
     */
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement signup logic here.

        final Firebase register = new Firebase("https://popping-heat-3804.firebaseio.com/");
        register.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                // success
                register.authWithPassword(email, password, new Firebase.AuthResultHandler() {

                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //store user data necessary
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(ReferenceUrl.PROVIDER, authData.getProvider()); // The authentication method used
                        map.put(ReferenceUrl.FIRST_NAME, name);   // User first name
                        map.put(ReferenceUrl.USER_EMAIL, (String) authData.getProviderData().get(ReferenceUrl.EMAIL)); // User email address
                        map.put(ReferenceUrl.CONNECTION, ReferenceUrl.ONLINE);  // User status
                        map.put(ReferenceUrl.AVATAR_ID, ChatHelper.generateRandomAvatarForUser()); // User avatar id

                        // Time user date is stored in database
                        long createTime = new Date().getTime();
                        map.put(ReferenceUrl.TIMESTAMP, String.valueOf(createTime)); // Timestamp is string type

                        // Store user data in the path https://<YOUR-FIREBASE-APP>.firebaseio.com/users/<uid>,
                        // where users/ is any arbitrary path to store user data, and <uid> represents the
                        // unique id obtained from the authentication data
                        register.child(ReferenceUrl.USERS).child(authData.getUid()).setValue(map);

/*
                        // After storing, go to main activity
                        Intent intent = new Intent(SignupActivity.this,BaseActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
*/
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(SignupActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
                    }

                });
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                onSignupSuccess();
                            }
                        }, 3000);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // onSignupFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
        });

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    /**
     * Make sure name, email. and password meets criteria
     * @return true if everything is met
     */
    public boolean validate() {
        boolean validate = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            validate = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Please enter a valid email address.");
            validate = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Must be between 4 and 10 alphanumeric characters.");
            validate = false;
        } else {
            _passwordText.setError(null);
        }

        return validate;
    }
}
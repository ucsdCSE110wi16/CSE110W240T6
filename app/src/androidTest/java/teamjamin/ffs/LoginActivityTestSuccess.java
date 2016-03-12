package teamjamin.ffs;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by nicolesakamoto on 3/11/16.
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityTestSuccess {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivity =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLoginSuccess() {
        Intent intent = new Intent();
        mLoginActivity.launchActivity(intent);
        onView(withId(R.id.input_email)).perform(replaceText("test@email.com"));
        onView(withId(R.id.input_password)).perform(replaceText("test"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Login failed")).inRoot(withDecorView(not(mLoginActivity.getActivity()
                .getWindow().getDecorView()))).check(doesNotExist());
    }

    @Test
    public void testGuestLogin() {
        Intent intent = new Intent();
        mLoginActivity.launchActivity(intent);
        onView(withId(R.id.guest_login)).perform(click());
        onView(withText("Login failed")).inRoot(withDecorView(not(mLoginActivity.getActivity()
                .getWindow().getDecorView()))).check(doesNotExist());
    }
}

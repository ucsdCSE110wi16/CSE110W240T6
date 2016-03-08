package teamjamin.ffs;

/**
 * Created by nicolesakamoto on 3/4/16.
 */

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivity =
            new ActivityTestRule<>(LoginActivity.class);

    @Test

    /**
     * Test login failure
     */
    public void testLoginFailure() {
        onView(withId(R.id.input_email)).perform(typeText("testemail@email.com"));
        onView(withId(R.id.input_password)).perform(typeText("passwordtest"));
        onView(withId(R.id.btn_login)).perform(click());

        onView(withText("Login failed")).check(matches(isDisplayed()));
        onView(withText("No user with these credentials exists."))
                .check(matches(isDisplayed()));
//        onView(withText("Login failed")).inRoot(withDecorView(not(mLoginActivity.ge‌​tActivity()
//                .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

}

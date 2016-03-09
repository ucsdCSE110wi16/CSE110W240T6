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
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivity =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLoginUnregistered() {
        //When using an unregistered email
        onView(withId(R.id.input_email)).perform(typeText("testemail@email.com"));
        onView(withId(R.id.input_password)).perform(typeText("test"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Login failed")).inRoot(withDecorView(not(mLoginActivity.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginWrongPassword() {
        //When entering wrong password
        onView(withId(R.id.input_email)).perform(replaceText("test@email.com"));
        onView(withId(R.id.input_password)).perform(replaceText("password"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Login failed")).inRoot(withDecorView(not(mLoginActivity.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginWrongEmail() {
        //When entering an invalid email
        onView(withId(R.id.input_email)).perform(replaceText("test"));
        onView(withId(R.id.input_password)).perform(replaceText("test"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Login failed")).inRoot(withDecorView(not(mLoginActivity.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginSuccess() {
        onView(withId(R.id.input_email)).perform(replaceText("test@email.com"));
        onView(withId(R.id.input_password)).perform(replaceText("test"));
        onView(withId(R.id.btn_login)).perform(click());
    }




}

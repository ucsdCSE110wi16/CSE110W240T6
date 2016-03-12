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
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by nicolesakamoto on 3/4/16.
 *
 * NOTE: Run this test file separately from all other test files.
 *
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivity =
            new ActivityTestRule<>(LoginActivity.class,true,false);

    @Test
    public void testLoginPage() {
        Intent intent = new Intent();
        mLoginActivity.launchActivity(intent);
        onView(withId(R.id.input_email)).check(matches(isDisplayed()));
        onView(withId(R.id.input_password)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        onView(withId(R.id.link_signup)).check(matches(isDisplayed()));
        onView(withId(R.id.guest_login)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginUnregistered() {
        Intent intent = new Intent();
        mLoginActivity.launchActivity(intent);
        //When using an unregistered email
        onView(withId(R.id.input_email)).perform(replaceText("testemail@email.com"));
        onView(withId(R.id.input_password)).perform(replaceText("test"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(anyOf(withText("Login failed"), withText("No user with these credentials exists."))).
                inRoot(withDecorView(not(mLoginActivity.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginWrongPassword() {
        Intent intent = new Intent();
        mLoginActivity.launchActivity(intent);
        //When entering wrong password
        onView(withId(R.id.input_email)).perform(replaceText("test@email.com"));
        onView(withId(R.id.input_password)).perform(replaceText("password"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(anyOf(withText("Login failed"), withText("Wrong Password. Please try again."),
                withText("No user with these credentials exists."))).
                inRoot(withDecorView(not(mLoginActivity.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginWrongEmail() {
        Intent intent = new Intent();
        mLoginActivity.launchActivity(intent);
        //When entering an invalid email
        onView(withId(R.id.input_email)).perform(replaceText("test"));
        onView(withId(R.id.input_password)).perform(replaceText("test"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(anyOf(withText("Login failed"), withText("Please enter a valid email."),
                withText("No user with these credentials exists."))).
                inRoot(withDecorView(not(mLoginActivity.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}

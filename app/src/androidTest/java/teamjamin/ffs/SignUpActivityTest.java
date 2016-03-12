package teamjamin.ffs;

/**
 * Created by nicolesakamoto on 3/6/16.
 */

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> mSignupActivityTest =
            new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void testCreateAccountFailure() {

        //When entering an invalid email
        onView(withId(R.id.input_name)).perform(typeText("..."));
        onView(withId(R.id.input_email)).perform(typeText("test"));
        onView(withId(R.id.input_password)).perform(typeText("password"));
        onView(withId(R.id.btn_signup)).perform(click());
        onView(withText("Login failed")).inRoot(withDecorView(not(mSignupActivityTest.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));

        //When entering an invalid password
        onView(withId(R.id.input_name)).perform(replaceText("..."));
        onView(withId(R.id.input_email)).perform(replaceText("test@email.com"));
        onView(withId(R.id.input_password)).perform(replaceText("passwordtest"));
        onView(withId(R.id.btn_signup)).perform(click());
        onView(withText("Login failed")).inRoot(withDecorView(not(mSignupActivityTest.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountSuccess() {
        onView(withId(R.id.input_name)).perform(replaceText("test"));
        onView(withId(R.id.input_email)).perform(replaceText("test@email.com"));
        onView(withId(R.id.input_password)).perform(replaceText("test"));
        onView(withId(R.id.btn_signup)).perform(click());
        onView(withText("Login failed")).check(doesNotExist());
    }
}

package teamjamin.ffs;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.PositionAssertions.isLeftOf;
import static android.support.test.espresso.assertion.PositionAssertions.isRightOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by nicolesakamoto on 3/4/16.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testHomeButton() {
        onView(withId(R.id.homeBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.homeBtn)).check(isLeftOf(withId(R.id.chatBtn)));
    }

    @Test
    public void testChatButton() {
        onView(withId(R.id.chatBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.chatBtn)).check(isRightOf(withId(R.id.homeBtn)));
        onView(withId(R.id.chatBtn)).check(isLeftOf(withId(R.id.cartBtn)));
    }

    @Test
    public void testCartButton() {
        onView(withId(R.id.cartBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.cartBtn)).check(isRightOf(withId(R.id.chatBtn)));
        onView(withId(R.id.cartBtn)).check(isLeftOf(withId(R.id.sellBtn)));
    }

    @Test
    public void testSellButton() {
        onView(withId(R.id.sellBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.sellBtn)).check(isRightOf(withId(R.id.cartBtn)));
        onView(withId(R.id.sellBtn)).check(isLeftOf(withId(R.id.settingsBtn)));
    }

    @Test
    public void testSettingsButton() {
        onView(withId(R.id.settingsBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.settingsBtn)).check(isRightOf(withId(R.id.sellBtn)));
    }
}

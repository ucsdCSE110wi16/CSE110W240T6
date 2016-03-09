//package teamjamin.ffs;
//
//import android.support.test.espresso.ViewAssertion;
//import android.support.test.espresso.assertion.ViewAssertions;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//
///**
// * Created by nicolesakamoto on 3/4/16.
// */
//
//
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.clearText;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static android.support.test.espresso.action.ViewActions.replaceText;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//
//import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//
//import static org.hamcrest.Matchers.not;
//
//@RunWith(AndroidJUnit4.class)
//public class MainActivityTest {
//    @Rule public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void testChatButton() {
//        onView(withId(R.id.chatBtn)).perform(click());
//    }
//
//    @Test
//    public void testCartButton() {
//        onView(withId(R.id.cartBtn)).perform(click());
//    }
//
//    @Test
//    public void testHomeButton() {
//        onView(withId(R.id.homeBtn)).perform(click());
//    }
//
//    @Test
//    public void testSellButton() {
//        onView(withId(R.id.sellBtn)).perform(click());
//    }
//
//    public void testSettingsButton() {
//        onView(withId(R.id.settingsBtn)).perform(click());
//    }
//}

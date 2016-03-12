//package teamjamin.ffs;
//
///**
// * Created by nicolesakamoto on 3/8/16.
// */
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.Before;
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
//public class ChatActivityTest {
//    @Rule
//    public final ActivityTestRule<ChatFunction> chat =
//            new ActivityTestRule<>(ChatFunction.class);
//
//    @Test
//    public void testSendMessage() {
//        onView(withId(R.id.chat_user_message)).check(matches(isDisplayed()));
//                //perform(replaceText("Hello"));
//        onView(withId(R.id.sendUserMessage)).check(matches(isDisplayed()));
//                //perform(click());
//        onView(withId(R.id.chat_recycler_view)).check(matches(withText("Hello")));
//    }
//}

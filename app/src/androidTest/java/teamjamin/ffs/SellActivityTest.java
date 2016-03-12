package teamjamin.ffs;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
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
public class SellActivityTest {

    private String ITEM_VALID_TITLE = "test title";
    private String ITEM_VALID_DESCRIPTION = "for espresso testing";
    private String ITEM_VALID_PRICE = "7";

    @Rule
    public IntentsTestRule sellActivity = new IntentsTestRule<>(SellActivity.class);

    @Test
    public void sellPagetest() {
        onView(withId(R.id.camera_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.upload_btn)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.gallery_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.input_item_description)).check(matches(isDisplayed()));
        onView(withId(R.id.input_item_price)).check(matches(isDisplayed()));
        onView(withId(R.id.input_item_title)).check(matches(isDisplayed()));
        onView(withId(R.id.electronics)).check(matches(isDisplayed()));
        onView(withId(R.id.appliances)).check(matches(isDisplayed()));
        onView(withId(R.id.furniture)).check(matches(isDisplayed()));
        onView(withId(R.id.book)).check(matches(isDisplayed()));
        onView(withId(R.id.services)).check(matches(isDisplayed()));
        onView(withId(R.id.others)).check(matches(isDisplayed()));
    }

    @Test
    public void testNoPictureFailure() {
//        Intent intent = new Intent();
//        sellActivity.launchActivity(intent);

        onView(withId(R.id.input_item_title)).perform(replaceText(ITEM_VALID_TITLE));
        onView(withId(R.id.input_item_price)).perform(replaceText(ITEM_VALID_PRICE));
        onView(withId(R.id.input_item_description)).perform(replaceText(ITEM_VALID_DESCRIPTION));
        onView(withId(R.id.others)).perform(click());
        onView(withId(R.id.upload_btn)).perform(scrollTo()).perform(click());
        onView(anyOf(withText("Please fill in all missing fields."),
                withText("Please take a picture or select an image from the gallery before you try to upload")))
                .inRoot(withDecorView(not(sellActivity.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testNoTitleFailure() {
//        Intent intent = new Intent();
//        sellActivity.launchActivity(intent);

        Instrumentation.ActivityResult result = buildResultForCameraIntent();

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage("com.android.camera2")).respondWith(result);

        onView(withId(R.id.input_item_title)).perform(clearText());
        onView(withId(R.id.input_item_price)).perform(replaceText(ITEM_VALID_PRICE));
        onView(withId(R.id.input_item_description)).perform(replaceText(ITEM_VALID_DESCRIPTION));
        onView(withId(R.id.others)).perform(click());
        onView(withId(R.id.upload_btn)).perform(scrollTo()).perform(click());
        onView(anyOf(withText("Please fill in all missing fields."), withText("Please put a name for your product or service.")))
                .inRoot(withDecorView(not(sellActivity.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testNoPriceFailure() {
//        Intent intent = new Intent();
//        sellActivity.launchActivity(intent);

        Instrumentation.ActivityResult result = buildResultForCameraIntent();

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage("com.android.camera2")).respondWith(result);

        onView(withId(R.id.input_item_title)).perform(replaceText(ITEM_VALID_TITLE));
        onView(withId(R.id.input_item_price)).perform(clearText());
        onView(withId(R.id.input_item_description)).perform(replaceText(ITEM_VALID_DESCRIPTION));
        onView(withId(R.id.others)).perform(click());
        onView(withId(R.id.upload_btn)).perform(scrollTo()).perform(click());
        onView(anyOf(withText("Please fill in all missing fields."), withText("Please enter a valid price.")))
                .inRoot(withDecorView(not(sellActivity.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }



    /**
     *
     * Helper Methods
     *
     */

    /**
     * Retrieve result from Camera intent
     * @return ActivityResult to insert into Camera intent
     */
    public static Instrumentation.ActivityResult buildResultForCameraIntent() {
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);

        // Build a result to return from the Camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        return result;
    }

}
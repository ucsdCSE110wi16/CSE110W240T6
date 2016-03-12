//package teamjamin.ffs;
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.espresso.intent.rule.IntentsTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.replaceText;
//import static android.support.test.espresso.action.ViewActions.scrollTo;
//import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
//import static android.support.test.espresso.intent.Intents.intending;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
//import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.anyOf;
//import static org.hamcrest.Matchers.not;
//
///**
// * Created by nicolesakamoto on 3/11/16.
// */
//
//@RunWith(AndroidJUnit4.class)
//public class SellActivityTestSuccess {
//
//    private static final int MEDIA_TYPE_IMAGE = 2;
//    private Uri fileUri; // file url to store image
//
//    private String ITEM_VALID_TITLE = "test title";
//    private String ITEM_VALID_DESCRIPTION = "for espresso testing";
//    private String ITEM_VALID_PRICE = "7";
//
//    public IntentsTestRule sellActivity = new IntentsTestRule<>(SellActivity.class);
//
//    @Test
//    public void testSellUploadSuccess() {
//        Bitmap icon = BitmapFactory.decodeResource(
//                InstrumentationRegistry.getTargetContext().getResources(),
//                R.mipmap.ic_launcher);
//
//        // Build a result to return from the Camera app
//        Intent resultData = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //Intent resultData = new Intent();
//        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//        resultData.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        //resultData.putExtra("data", icon);
//        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//
//        intending(toPackage("com.android.camera2")).respondWith(result);
//
//        onView(withId(R.id.input_item_title)).perform(replaceText(ITEM_VALID_TITLE));
//        onView(withId(R.id.input_item_price)).perform(replaceText(ITEM_VALID_PRICE));
//        onView(withId(R.id.input_item_description)).perform(replaceText(ITEM_VALID_DESCRIPTION));
//        onView(withId(R.id.others)).perform(click());
//        onView(withId(R.id.upload_btn)).perform(scrollTo()).perform(click());
//        onView(anyOf(withText("Please fill in all missing fields."), withText("Please enter a valid price."),
//                withText("Please put a name for your product or service."),
//                withText("Please take a picture or select an image from the gallery before you try to upload"),
//                withText("You must select a tag before uploading.")))
//                .inRoot(withDecorView(not(sellActivity.getActivity()
//                        .getWindow().getDecorView()))).check(doesNotExist());
//        }
//
//
//        /**
//         * Creating file uri to store image
//         */
//        public Uri getOutputMediaFileUri(int image) {
//            return Uri.fromFile(getOutputMediaFile(image));
//        }
//
//        /**
//         * Create and output the image file
//         */
//        private static File getOutputMediaFile(int type) {
//
//            // SD card location
//            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Config.IMAGE_DIRECTORY_NAME);
//
//            // Create the storage directory if it does not exist
//            if (!mediaStorageDir.exists()) {
//                if (!mediaStorageDir.mkdirs()) {
//                    return null;
//                }
//            }
//
//            // Create the image file
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//            File mediaFile;
//            if (type == MEDIA_TYPE_IMAGE) {
//                mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                        + "IMG_" + timeStamp + ".jpg");
//            } else {
//                return null;
//            }
//            return mediaFile;
//        }
//
//}

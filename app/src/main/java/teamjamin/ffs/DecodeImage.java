package teamjamin.ffs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Aaron on 3/6/2016.
 */
public class DecodeImage {

    private static String img;

    public static Bitmap getImage(String post)
    {
       // Firebase itRef = new Firebase("https://ffs.firebaseio.com/items/");
        byte[] decodedString;
        Bitmap decodedByte;

        decodedString = Base64.decode(post, Base64.DEFAULT);
        decodedByte = decodeSampledBitmap(decodedString, 250, 250);

        return decodedByte;
    }

    public static byte[] getImageByteArray(String post) {
        byte[] decodedString = Base64.decode(post, Base64.DEFAULT);
        return decodedString;
    }

    public static Bitmap decodeByteArray(byte[] decodedArray) {
        return BitmapFactory.decodeByteArray(decodedArray, 0, decodedArray.length);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 4;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    private static Bitmap decodeSampledBitmap(byte[] bytes, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }
}

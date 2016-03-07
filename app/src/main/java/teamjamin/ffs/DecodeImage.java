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
        Firebase itRef = new Firebase("https://ffs.firebaseio.com/items/");
        byte[] decodedString;
        Bitmap decodedByte;
        final String pst = post;

        // Attach an listener to read the data at our posts reference
        itRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                img = snapshot.child(pst).child("itemPicture").getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        decodedString = Base64.decode(img, Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }
}

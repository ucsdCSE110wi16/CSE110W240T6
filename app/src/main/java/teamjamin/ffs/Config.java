package teamjamin.ffs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Aaron on 2/16/2016.
 */
public class Config {

    public static boolean GUEST_LOGIN;

    public static String EMAIL;

    public static ArrayList<Item> cart_item_list = new ArrayList<>();

    public static int NUM = 0;

    // File upload URL, default set to blank
    public static String fileUploadUrl = "";

    // Directory name to store images
    public static final String IMAGE_DIRECTORY_NAME = "FFS";

    public static String getUrl() {
        return fileUploadUrl;
    }

    public static void setUrl(String url) {
        fileUploadUrl = url;
    }

    public static void updateEmail(String newEmail) {
        // TODO: updating username on firebase
        EMAIL = newEmail;
    }
}

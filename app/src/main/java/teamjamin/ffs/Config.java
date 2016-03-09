package teamjamin.ffs;

/**
 * Created by Aaron on 2/16/2016.
 */
public class Config {

    public static boolean GUEST_LOGIN;

    public static String EMAIL;

    public static String PASSWORD;

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

    public static void updatePassword(String newPassword) {
        // TODO: updating username on firebase
        PASSWORD = newPassword;
    }

}

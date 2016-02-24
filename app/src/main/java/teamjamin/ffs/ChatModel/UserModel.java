package teamjamin.ffs.ChatModel;

import android.nfc.Tag;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Jessica on 2/24/16.
 */
public class UserModel implements Parcelable {

    // recipient
    private String provider;
    private String recipient;
    private String recipient_email;

    // user
    private String user;
    private String user_email;

    // constructor
    UserModel() {}

    UserModel(Parcel parcel) {
        provider = parcel.readString();
        recipient = parcel.readString();
        recipient_email = parcel.readString();
        user = parcel.readString();
        user_email = parcel.readString();
    }

    /* Getter + Setter */
    public String getProvider() {
        return provider;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getRecipient_email() {
        return recipient_email;
    }

    public String getUser() {
        return user;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    // chat endpoint
    public String getChatRef() {
        return createUniqueChatRef();
    }

    private String createUniqueChatRef() {
        String unique = "";
        unique = cleanEmailAddress(getUser_email()) + "-" + cleanEmailAddress(getUser_email());
        return unique;
    }

    private long createAtCurrentUser() {
        return Long.parseLong(getUser());
    }

    private long createdAtRecipient() {
        return Long.parseLong(getRecipient());
    }

    private String cleanEmailAddress(String email) {
        return email.replace(".","-");
    }


    /* Parcelable */
    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {}

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };


}

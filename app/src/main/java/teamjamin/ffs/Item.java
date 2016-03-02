package teamjamin.ffs;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aaron on 2/26/2016.
 */
public class Item {

    private String itemTitle;
    private double itemPrice;
    private String itemDescription;

    private String itemPicture;

    Item()
    {
        // Firebase needs default; left empty
    }

    Item(String title, double price, String description, String picture) {
        itemTitle = title;
        itemPrice = price;
        itemDescription = description;
        itemPicture = picture;
    }

    public String getItemTitle() {
        return this.itemTitle;
    }

    public void setItemTitle(String title) {
        this.itemTitle = title;
    }

    public String getItemDescription() {
        if(itemDescription.length() >= 1) {
            return this.itemDescription;
        } else {
            return "No description found.";
        }
    }

    public void setItemDescription(String description) {
        this.itemDescription = description;
    }

    public double getItemPrice() {
        return this.itemPrice;
    }

    public void setItemPrice(double price) {
        itemPrice = price;
    }

    public String getItemPicture() {
        return this.itemPicture;
    }

    public void setItemPicture() {
        // Set image for item here...
    }
}

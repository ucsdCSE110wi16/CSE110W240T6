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
    private String sellerEmail;
    private String sellerName;
    private boolean[] array = new boolean[6];
    private String itemPicture;
    private String post_id;


    Item()
    {
        // Firebase needs default; left empty
    }

    Item(String title, double price, String description, String picture, String email, String name, String cate) {
        itemTitle = title;
        itemPrice = price;
        itemDescription = description;
        itemPicture = picture;
        sellerEmail = email;
        sellerName = name;
        if (cate.contains("ELECTRONICS")) {
            array[0] = true;
        }
        if (cate.contains("APPLIANCES")) {
            array[1] = true;
        }
        if (cate.contains("FURNITURE")) {
            array[2] = true;
        }
        if (cate.contains("BOOKS")) {
            array[3] = true;
        }
        if (cate.contains("SERVICES")) {
            array[4] = true;
        }
        if (cate.contains("OTHERS")) {
            array[5] = true;
        }
    }
/*
    boolean isElectronics () {
        return this.tag == "ELECTRONICS";
    }

    boolean isAppliances() {
        return this.tag == "APPLIANCES";
    }

    boolean isFurniture() {
        return this.tag == "FURNITURE";
    }

    boolean isBooks() {
        return this.tag == "BOOKS";
    }

    boolean isOthers() {
        return this.tag == "OTHERS";
    }*/

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

    public String getSellerEmail () { return this.sellerEmail;}

    public String getSellerName ()  { return this.sellerName; }

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

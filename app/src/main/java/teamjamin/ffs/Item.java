package teamjamin.ffs;

import java.text.DecimalFormat;

/**
 * Created by Aaron on 2/26/2016.
 */

public class Item {

    private String itemTitle;
    private double itemPrice;
    private String itemDescription;
    private String sellerEmail;
    private String sellerName;
    private String itemPicture;
    private String post_id;
    private double category;


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

        category = 0;
        if (cate.contains("OTHERS")) {
            category += 1;
        }
        if (cate.contains("ELECTRONICS")) {
            category += 10;
        }
        if (cate.contains("APPLIANCES")) {
            category += 100;
        }
        if (cate.contains("FURNITURE")) {
            category += 1000;
        }
        if (cate.contains("BOOKS")) {
            category += 10000;
        }
        if (cate.contains("SERVICES")) {
            category += 100000;
        }
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

    public String getSellerEmail () { return this.sellerEmail;}

    public String getSellerName ()  { return this.sellerName; }

    public void setItemDescription(String description) {
        this.itemDescription = description;
    }

    public double getItemPrice() {
        return this.itemPrice;
    }

    public double getCategory() {return this.category; }

    public String getPost_id() { return this.post_id; }

    public String getItemPicture() {
        return this.itemPicture;
    }

    public void setItemPicture() {
        // Set image for item here...
    }
}

package teamjamin.ffs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

/**
 * Created by Jenny on 2/17/16.
 */
public class DetailActivity  extends AppCompatActivity {

    private ImageView itemImage;
    private TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSeller;
    private Button chat_btn, cart_btn;
    private Item item;
    private byte[] bytes;

    private View rootView;
    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        rootView = findViewById(R.id.activity_detail_container);

        Bundle extras = getIntent().getExtras();

        // Fix issue where title in ActionBar doesn't show
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(extras.getString("ITEM_TITLE"));

        itemImage = (ImageView) findViewById(R.id.item_picture);
        itemTitle = (TextView) findViewById(R.id.item_name);
        itemDescription = (TextView) findViewById(R.id.item_description);
        itemCategory = (TextView) findViewById(R.id.item_category);
        itemSeller = (TextView) findViewById(R.id.item_seller);
        itemPrice = (TextView) findViewById(R.id.item_price);

        bytes = extras.getByteArray("ITEM_PICTURE");
        itemImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

        itemTitle.setText(extras.getString("ITEM_TITLE"));
        itemDescription.setText(extras.getString("ITEM_DESCRIPTION"));

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Double price = Double.parseDouble(decimalFormat.format(extras.getDouble("ITEM_PRICE")));
        itemPrice.setText("$" + price);
        itemCategory.setText(getCate(extras.getDouble("ITEM_CATEGORY")));
        itemSeller.setText(extras.getString("ITEM_SELLER_EMAIL"));

        item = new Item(extras.getString("ITEM_TITLE"), extras.getDouble("ITEM_PRICE"), extras.getString("ITEM_DESCRIPTION"),
                encodeImage(bytes), extras.getString("ITEM_SELLER_EMAIL"), "", extras.getString("ITEM_CATEGORY"));

        chat_btn = (Button) findViewById(R.id.btn_chat);
        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        cart_btn = (Button) findViewById(R.id.btn_cart);
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item _item = item;
                byte[] b = bytes;
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                intent.putExtra("ITEM_TITLE", _item.getItemTitle());
                intent.putExtra("ITEM_DESCRIPTION", _item.getItemDescription());
                intent.putExtra("ITEM_PRICE", _item.getItemPrice());
                intent.putExtra("ITEM_SELLER_EMAIL", _item.getSellerEmail());
                intent.putExtra("ITEM_CATEGORY", getCate(_item.getCategory()));
                intent.putExtra("ITEM_PICTURE", b);

                startActivity(intent);
            }
        });


    }

    private String encodeImage(byte[] bytes) {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length, options);
        ByteArrayOutputStream baostream = new ByteArrayOutputStream();

        // Compress the image to reduce the image size making uploading easier
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baostream);
        byte[] byteArray = baostream.toByteArray();

        String encodedString = Base64.encodeToString(byteArray, 0);

        // Encode image to string
        return encodedString;
    }

    // To decode Miguel's categories thing
    private String getCate(double d) {
        String result = "";
        if( d % 10 == 1) {
            result += "OTHERS ";
        }

        d /= 10;

        if( d % 10 == 1) {
            result += "ELECTRONICS";
        }

        d /= 10;

        if( d % 10 == 1) {
            result = "APPLIANCES";
        }

        d /= 10;

        if( d % 10 == 1) {
            result = "FURNITURE";
        }

        d /= 10;

        if( d % 10 == 1) {
            result = "BOOKS";
        }

        d /= 10;

        if( d % 10 == 1) {
            result += "SERVICE";
        }
        return result;
    }
}
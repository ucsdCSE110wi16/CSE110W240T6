package teamjamin.ffs;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends BaseActivity {

    private View rootView;
    private Button empty;

    private ImageView itemImage;
    private TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSeller;
    private Button chat_btn, cart_btn;
    private Item item;
    private byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rootView = findViewById(R.id.activity_cart_container);

        //Set nav drawer selected to second item in list
        //mNavigationView.getMenu().getItem(2).setChecked(true);

        Bundle extras = getIntent().getExtras();
        LinearLayout all_items = (LinearLayout) findViewById(R.id.all_items);

        itemImage = (ImageView) findViewById(R.id.item_picture);
        itemTitle = (TextView) findViewById(R.id.item_title);
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
        itemCategory.setText(extras.getString("ITEM_CATEGORY"));
        itemSeller.setText(extras.getString("ITEM_SELLER_EMAIL"));


        empty = (Button) findViewById(R.id.btn_empty);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private View buildCartItem(Bundle extras) {
        return null;
    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

}

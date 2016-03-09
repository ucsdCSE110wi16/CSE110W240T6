package teamjamin.ffs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends BaseActivity {

    private View rootView;
    private Button empty, refresh;
    private Item _item;
    private int pos;

    private TextView cart_item;
    private ArrayList<TextView> cart_item_array;
    private Item item;
    private byte[] bytes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rootView = findViewById(R.id.activity_cart_container);

        LinearLayout cart = (LinearLayout) findViewById(R.id.all_items);

        cart.removeAllViews();
        cart_item_array = new ArrayList<>();

        Toast.makeText(getApplicationContext(), "Item was added to your cart. " + Config.cart_item_list.size(), Toast.LENGTH_LONG).show();

        if(!Config.cart_item_list.isEmpty()) {
            for (int i = 0; i < Config.cart_item_list.size(); ++i) {
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(50,50,50,50);

                _item = Config.cart_item_list.get(i);

                cart_item = new TextView(this);
                cart_item.setText("Name: " + Config.cart_item_list.get(i).getItemTitle() + "\nPrice: " + Config.cart_item_list.get(i).getItemPrice() +
                        "\nSeller: " + Config.cart_item_list.get(i).getSellerEmail() + "\nCategory: " + Config.cart_item_list.get(i).getCategory() +
                        "\nDescriptions: " + Config.cart_item_list.get(i).getItemDescription());

                cart_item.setTextColor(getResources().getColor(R.color.white));

                cart_item_array.add(cart_item);


                cart.addView(cart_item, params);
            }
        }

        for(int i = 0; i < Config.cart_item_list.size(); i++ ) {
            pos = i;
            cart_item_array.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CartActivity.this, DetailActivity.class);
                    intent.putExtra("ITEM_TITLE", Config.cart_item_list.get(pos).getItemTitle());
                    intent.putExtra("ITEM_DESCRIPTION", Config.cart_item_list.get(pos).getItemDescription());
                    intent.putExtra("ITEM_PRICE", Config.cart_item_list.get(pos).getItemPrice());
                    intent.putExtra("ITEM_SELLER_EMAIL", Config.cart_item_list.get(pos).getSellerEmail());
                    intent.putExtra("ITEM_CATEGORY", Config.cart_item_list.get(pos).getCategory());

                    byte[] bm = DecodeImage.getImageByteArray(Config.cart_item_list.get(pos).getItemPicture());
                    intent.putExtra("ITEM_PICTURE", bm);
                    intent.putExtra("ADDED", true);
                    intent.putExtra("POSITION", pos);

                    startActivity(intent);
                }
            });

        }

        empty = (Button) findViewById(R.id.btn_empty);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout cart = (LinearLayout)findViewById(R.id.all_items);
                cart.removeAllViewsInLayout();
                Config.cart_item_list.clear();
            }
        });

        refresh = (Button) findViewById(R.id.btn_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
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

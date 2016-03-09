package teamjamin.ffs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainActivity extends BaseActivity {
    private ImageView imgBtn_home, imgBtn_chat, imgBtn_cart, imgBtn_sell, imgBtn_settings;
    private Button btn, update_btn;
    private ArrayList<Item> item_list;


    private Bitmap bm;
    private Double _price;

    private View rootView;

    private LinearLayout feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setup firebase on android
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.activity_main_container);

        //Set nav drawer selected to first item in list
        //mNavigationView.getMenu().getItem(0).setChecked(true);

        Firebase ref = new Firebase("https://ffs.firebaseio.com/");
        item_list = new ArrayList<Item>();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        feed = (LinearLayout) findViewById(R.id.new_items_feed);

        if(!Config.GUEST_LOGIN && ref.getAuth() == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        imgBtn_home = (ImageView)findViewById(R.id.homeBtn);
        imgBtn_chat = (ImageView)findViewById(R.id.chatBtn);
        imgBtn_cart = (ImageView)findViewById(R.id.cartBtn);
        imgBtn_sell = (ImageView)findViewById(R.id.sellBtn);
        imgBtn_settings = (ImageView)findViewById(R.id.settingsBtn);

        update_btn = (Button) findViewById(R.id.update_feed);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feed.removeAllViewsInLayout();
                item_list.clear();
                Firebase nref = new Firebase("https://ffs.firebaseio.com/items/");

                Query query = nref.orderByValue().limitToLast(5);
                getItemFromFireBase(query);
            }
        });

        imgBtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        imgBtn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Config.GUEST_LOGIN) {
                    Toast.makeText(getApplicationContext(),"You must be logged in to access Chat. If you need to logout, access menu options above.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                }
            }
        });

        imgBtn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        imgBtn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SellActivity.class);
                startActivity(intent);
            }
        });

        imgBtn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Config.GUEST_LOGIN) {
                    Toast.makeText(getApplicationContext(), "You must be logged in to access Settings. If you need to logout, access menu options above.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            }
        });
        ref = new Firebase("https://ffs.firebaseio.com/items/");

        Query query = ref.orderByValue().limitToLast(5);
        getItemFromFireBase( query);

    }

    private void getItemFromFireBase(Query q) {


        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                feed.removeAllViewsInLayout();
                item_list.clear();

                for( DataSnapshot s : snapshot.getChildren()) {
                    item_list.add( 0, s.getValue(Item.class));
                }

                for( Item it: item_list) {
                    addImageView(it);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void addImageView(Item item) {
        ImageView new_item = new ImageView(this);
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(250, 250);
        new_item.setLayoutParams(lp);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm = DecodeImage.getImage(item.getItemPicture());
       // Toast.makeText(getApplicationContext(), item.getItemPicture(), Toast.LENGTH_LONG).show();
        bm.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        new_item.setImageBitmap(bm);
        feed.addView(new_item);

        final Item _item = item;

        new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ITEM_TITLE", _item.getItemTitle());
                intent.putExtra("ITEM_DESCRIPTION", _item.getItemDescription());
                intent.putExtra("ITEM_PRICE", _item.getItemPrice());
                intent.putExtra("ITEM_SELLER_EMAIL", _item.getSellerEmail());
                intent.putExtra("ITEM_CATEGORY", _item.getCategory());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm = DecodeImage.getImage(_item.getItemPicture());
                bm.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                byte[] bytes = stream.toByteArray();
                intent.putExtra("ITEM_PICTURE", bytes);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_profile) {
            //Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            //startActivity(intent);
            return true;
        }

        if (id == R.id.action_settings) {
            if (Config.GUEST_LOGIN) {
                Toast.makeText(getApplicationContext(), "You must be logged in to access Settings. If you need to logout, access menu options above.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        }

        if(id == R.id.action_logout) {
            if (Config.GUEST_LOGIN) {
                Config.GUEST_LOGIN = false;
            }
            Firebase ref = new Firebase("httpw://ffs.firebaseio.com/");
            if(ref.getAuth() != null) {
                Map<String, Object> offline = new HashMap<String, Object>();
                offline.put("/connection", "offline");
                ref.child("users").child(ref.getAuth().getUid()).updateChildren(offline);

                ref.unauth();
                Toast.makeText(getApplicationContext(), "You've successfully logged out.", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
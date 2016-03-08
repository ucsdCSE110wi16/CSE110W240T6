package teamjamin.ffs;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private ImageView imgBtn_home, imgBtn_chat, imgBtn_cart, imgBtn_sell, imgBtn_settings;
    private Button btn, update_btn;


    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setup firebase on android
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.activity_main_container);

        //Set nav drawer selected to first item in list
        //mNavigationView.getMenu().getItem(0).setChecked(true);

        Firebase ref = new Firebase("httpw://ffs.firebaseio.com/");

        if(!Config.GUEST_LOGIN && ref.getAuth() == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

//        else if (ref.getAuth() != null) {
//            Toast.makeText(getApplicationContext(), "Welcome, " + ref.child("users").child(ref.getAuth().getUid()).child("firstName"), Toast.LENGTH_LONG).show();
//        }

        imgBtn_home = (ImageView)findViewById(R.id.homeBtn);
        imgBtn_chat = (ImageView)findViewById(R.id.chatBtn);
        imgBtn_cart = (ImageView)findViewById(R.id.cartBtn);
        imgBtn_sell = (ImageView)findViewById(R.id.sellBtn);
        imgBtn_settings = (ImageView)findViewById(R.id.settingsBtn);

        update_btn = (Button) findViewById(R.id.update_feed);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNewItemsFeed();
            }
        });

        imgBtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
                return;
            }
        });

        imgBtn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
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

    }

    public void updateNewItemsFeed() {
        LinearLayout feed = (LinearLayout) findViewById(R.id.new_items_feed);

        ImageView new_item = new ImageView(this);
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(150, 150);
        new_item.setLayoutParams(lp);

        //new_item.setImageBitmap(bm);

        feed.addView(new_item);
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

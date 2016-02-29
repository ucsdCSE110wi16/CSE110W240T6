package teamjamin.ffs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Aaron on 2/23/2016.
 */
public class CategoryActivity extends BaseActivity {

    private View rootView;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if(intent.getStringExtra("Category") == "Electronics") {
            // Implement Category listing for electronics here
            setContentView(R.layout.activity_category_electronics);
            rootView = findViewById(R.id.activity_category_electronics_container);

        }  if(intent.getStringExtra("Category") == "Furniture") {
            // Implement Category listing for Furniture here

            setContentView(R.layout.activity_category_electronics);
            rootView = findViewById(R.id.activity_category_electronics_container);
            text = (TextView)findViewById(R.id.electronics_text);
            text.setText("Furniture");

        }  if(intent.getStringExtra("Category") == "Other") {
            // Implement Category listing for Other stuff here

            setContentView(R.layout.activity_category_electronics);
            rootView = findViewById(R.id.activity_category_electronics_container);
            text = (TextView)findViewById(R.id.electronics_text);
            text.setText("Other");

        } else {
            // do nothing
            setContentView(R.layout.activity_category_electronics);
            rootView = findViewById(R.id.activity_category_electronics_container);
            Toast.makeText(getApplicationContext(),intent.getStringExtra("Category"), Toast.LENGTH_LONG).show();
        }

        //Set nav drawer selected to second item in list
        mNavigationView.getMenu().getItem(0).setChecked(true);
        mNavigationView.getMenu().getItem(1).setChecked(true);
        mNavigationView.getMenu().getItem(2).setChecked(true);
        mNavigationView.getMenu().getItem(3).setChecked(true);

    }

}

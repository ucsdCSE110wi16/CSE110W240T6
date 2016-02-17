package teamjamin.ffs;

import android.os.Bundle;
import android.view.View;

public class CartActivity extends BaseActivity {

    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rootView = findViewById(R.id.activity_cart_container);

        //Set nav drawer selected to second item in list
        mNavigationView.getMenu().getItem(2).setChecked(true);

    }



    /** HIDE TOOLBAR **/
//    @Override
//    protected boolean useToolbar() {
//        return false;
//    }



    /** HIDE hamburger menu **/
//    @Override
//    protected boolean useDrawerToggle() {
//        return false;
//    }

}

package teamjamin.ffs;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Aaron on 2/13/2016.
 */
public class ChatActivity extends BaseActivity {

    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rootView = findViewById(R.id.activity_chat_container);

        //Set nav drawer selected to second item in list
        mNavigationView.getMenu().getItem(1).setChecked(true);

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

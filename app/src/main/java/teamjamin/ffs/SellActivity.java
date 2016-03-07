package teamjamin.ffs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jenny on 2/10/16.
 */
public class SellActivity extends AppCompatActivity {
    private View rootView;
    private ProgressDialog progressDialog;
    private Button btn_camera;
    private Button btn_gallery;
    private Button btn_upload;
    private ImageView img_item;
    private CheckBox check_electronics;
    private CheckBox check_appliances;
    private CheckBox check_furniture;
    private CheckBox check_books;
    private CheckBox check_services;
    private CheckBox check_others;
    private EditText item_title, item_price, item_description;

    Bitmap bitmap;
    private String encodedString;

    private String imgPath, fileName;
    private static final int RESULT_LOAD_IMG = 1;
    private static final int CAMERA_CAPTURE_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 2;
    private Uri fileUri; // file url to store image

    private String tagIDs;
    private String postID;
    private boolean posted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        check_electronics = (CheckBox) findViewById(R.id.electronics);
        if(check_electronics.isChecked()) {
            tagIDs = tagIDs + " " + "ELECTRONICS";
        }

        check_appliances = (CheckBox) findViewById(R.id.appliances);
        if(check_appliances.isChecked()) {
            tagIDs = tagIDs + " " + "APPLIANCES";
        }

        check_furniture = (CheckBox) findViewById(R.id.furniture);
        if(check_furniture.isChecked()) {
            tagIDs = tagIDs + " " + "FURNITURE";
        }

        check_books = (CheckBox) findViewById(R.id.book);
        if(check_books.isChecked()) {
            tagIDs = tagIDs + " " + "BOOKS";
        }

        check_services = (CheckBox) findViewById(R.id.services);
        if(check_services.isChecked()) {
            tagIDs = tagIDs + " " + "SERVICES";
        }

        check_others = (CheckBox) findViewById(R.id.others);
        if(check_services.isChecked()) {
            tagIDs = tagIDs + " " + "OTHERS";
        }

        rootView = findViewById(R.id.activity_sell_container);

        //Set nav drawer selected to second item in list
        //mNavigationView.getMenu().getItem(1).setChecked(true);

        progressDialog = new ProgressDialog(this);
        // Set cancelable false
        progressDialog.setCancelable(false);

        btn_camera = (Button) findViewById(R.id.camera_btn);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCamera(v);
            }
        });

        btn_gallery = (Button) findViewById(R.id.gallery_btn);
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery(v);
            }
        });

        btn_upload = (Button) findViewById(R.id.upload_btn);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                posted = false;
                uploadItem(v);
            }
        });

        item_title = (EditText) findViewById(R.id.input_item_title);
        item_description = (EditText) findViewById(R.id.input_item_description);
        item_price = (EditText) findViewById(R.id.input_item_price);



        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Camera Not Found", Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
        }
    }

    // Load up camera app in an activity
    public void loadCamera(View view) {
        // Intent to open camera app
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // Start intent
        startActivityForResult(cameraIntent, CAMERA_CAPTURE_REQUEST_CODE);
    }

    // Load up gallery app in an activity
    public void loadImageFromGallery(View view) {
        // Intent to open gallery app
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAMERA_CAPTURE_REQUEST_CODE){
                if (resultCode == RESULT_OK) {
                    imgPath = fileUri.getPath();
                    // Image was captured successfully! Set it into ImageView for preview
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

                    img_item = (ImageView) findViewById(R.id.itemPicture);
                    img_item.setImageBitmap(bitmap);

                } else if (requestCode == RESULT_CANCELED) {
                    // Tell user cancelled the camera capture
                    Toast.makeText(getApplicationContext(), "You cancelled taking a picture.", Toast.LENGTH_LONG).show();
                } else {
                    // Failed to capture image
                    Toast.makeText(getApplicationContext(), "Oh no! Failed to capture image!", Toast.LENGTH_LONG).show();
                }
            }

            // Image is selected
            else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get image data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                img_item = (ImageView) findViewById(R.id.itemPicture);

                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 2;

                // Set the image in the ImageView to preview
                img_item.setImageBitmap(BitmapFactory.decodeFile(imgPath, options));

                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
            } else {
                Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Oops! Something isn't right", Toast.LENGTH_SHORT).show();
        }
    }

    // Upload Image on button press
    public void uploadItem(View view) {
        // Image IS selected from gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            posted = false;
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            // Convert image to String using Base64
            encodeImageToString();
            btn_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // check values
                    validate();
                }
            });
        }
        // Image IS NOT selected from gallery
        else {
            Toast.makeText(getApplicationContext(), "Please select an image from the gallery before you try to upload", Toast.LENGTH_SHORT).show();
        }
    }

    /** HELPER METHODS **/

    /**
     * Checking device has camera hardware or not
     */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                // Camera found
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            // Camera not found on device
            return false;
        }
    }

    /**
     * Creating file uri to store image
     */
    public Uri getOutputMediaFileUri(int image) {
        return Uri.fromFile(getOutputMediaFile(image));
    }

    /**
     * Create and output the image file
     */
    private static File getOutputMediaFile(int type) {

        // SD card location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create the image file
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    // AsyncTask to convert image to string
    public void encodeImageToString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {
            }
            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                bitmap = BitmapFactory.decodeFile(imgPath, options);
                ByteArrayOutputStream baostream = new ByteArrayOutputStream();

                // Compress the image to reduce the image size making uploading easier
                bitmap.compress(Bitmap.CompressFormat.PNG, 25, baostream);
                byte[] byteArray = baostream.toByteArray();

                // Encode image to string
                encodedString = Base64.encodeToString(byteArray, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String message) {
                progressDialog.setMessage("Calling Upload Server...");

                if( posted == false) {

                    // check values
                    validate();

                    encodeImageToString();

                    Firebase itemRef = new Firebase("https://ffs.firebaseio.com/items/");
                    Firebase newItemRef = itemRef.push();

                    Item it = new Item(item_title.getText().toString(), Double.parseDouble(item_price.getText().toString())
                            ,item_description.getText().toString(), encodedString);

                    //Firebase itemRef = ref.child("items")/*.child(/ *USERINFO* /)*/;
                    newItemRef.setValue(it);

                    String postID = newItemRef.getKey();

                    posted = true;
                }
                progressDialog.dismiss();
                finish();
          }
        }.execute(null, null, null);
    }

    private boolean validate() {
        boolean validate = true;
        String itemTitle = item_title.getText().toString();
        String itemDescription = item_description.getText().toString();

        double itemPrice_entered;
        double itemPrice_received = -1.0;

        // Format price to 2 decimal points
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if(!item_price.getText().toString().isEmpty()) {
            itemPrice_entered = Double.parseDouble(item_price.getText().toString());
            itemPrice_received = Double.parseDouble(decimalFormat.format(itemPrice_entered));
        } else {
            item_price.setError("Please enter a valid price.");
            validate = false;
        }

        // Check if title of item is entered.
        if(itemTitle.isEmpty()) {
            item_title.setError("Please put a name for your product or service.");
            validate = false;
        } else {
            item_title.setError(null);
        }

        // Check if there's a description (not required)
        if(itemDescription.length() > 200) {
            item_description.setError("You have reached the character limit of 140 characters.");
            validate = false;
        } else {
            if(itemDescription.isEmpty()) {
                item_description.setText("No description provided.");
            }
            item_description.setError(null);
        }

        // Check if price is valid.
        if(itemPrice_received < 0 ) {
            item_price.setError("Please enter a valid price.");
            validate = false;
        } else {
            item_price.setError(null);
            // Toast.makeText(getApplicationContext(), "Price: " + itemPrice_received, Toast.LENGTH_LONG).show();
        }
        return validate;
    }
}

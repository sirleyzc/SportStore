package com.example.myapplication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.DB.DBFirebaseBranch;
import com.example.myapplication.DB.DBHelperBranch;
import com.example.myapplication.Entities.Branch;
import com.example.myapplication.Services.BranchService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

public class BranchForm extends AppCompatActivity {
    private Button btnBranchForm;
    private EditText editNameBranchForm, editPhoneBranchForm, editIdBranchForm;
    private TextView textLatitudeBranchForm, textLongitudeBranchForm;
    private ImageView imageBranchForm;
    private DBHelperBranch dbHelperBranch;
    private DBFirebaseBranch dbFirebaseBranch;
    private ActivityResultLauncher<String> content;
    private BranchService branchService;
    private MapView map;
    private MapController mapController;
    private StorageReference storageReference;
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_form);

        urlImage = "";
        btnBranchForm = (Button) findViewById(R.id.btnBranchForm);
        editNameBranchForm = (EditText) findViewById(R.id.editNameBranchForm);
        editPhoneBranchForm = (EditText) findViewById(R.id.editPhoneBranchForm);
        imageBranchForm = (ImageView) findViewById(R.id.imageBranchForm);
        textLatitudeBranchForm = (TextView) findViewById(R.id.textLatitudeBranchForm);
        textLongitudeBranchForm = (TextView) findViewById(R.id.textLongitudeBranchForm);
        map = (MapView) findViewById(R.id.mapForm);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        GeoPoint colombia = new GeoPoint(4.570868, -74.297333);

        mapController = (MapController) map.getController();
        mapController.setCenter(colombia);
        mapController.setZoom(10);
        map.setMultiTouchControls(true);

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                textLatitudeBranchForm.setText(String.valueOf(p.getLatitude()));
                textLongitudeBranchForm.setText(String.valueOf(p.getLongitude()));

                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay eventsOverlay = new MapEventsOverlay(getApplicationContext(), mapEventsReceiver);
        map.getOverlays().add(eventsOverlay);

        editPhoneBranchForm.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(getApplicationContext(), "Hello Enter", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        try {
            dbHelperBranch = new DBHelperBranch(this);
            dbFirebaseBranch = new DBFirebaseBranch();
            branchService = new BranchService();
            storageReference = FirebaseStorage.getInstance().getReference();
            content = registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri result) {
                            Uri uri = result;
                            StorageReference filePath = storageReference.child("images").child(uri.getLastPathSegment());
                            filePath.putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(getApplicationContext(),"Imagen Cargada", Toast.LENGTH_SHORT).show();
                                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Uri downLoadUrl = uri;
                                                    urlImage = downLoadUrl.toString();
                                                    Glide.with(BranchForm.this)
                                                            .load(downLoadUrl)
                                                            .override(500, 500)
                                                            .into(imageBranchForm);
                                                }
                                            });
                                        }
                                    });
                        }
                    });
        }catch (Exception e){
            Log.e("DB", e.toString());
        }


        Intent intentIN = getIntent();
        editNameBranchForm.setText(intentIN.getStringExtra("name"));
        editPhoneBranchForm.setText(intentIN.getStringExtra("phone"));
        textLatitudeBranchForm.setText(String.valueOf(intentIN.getDoubleExtra("latitude", 0.0)));
        textLongitudeBranchForm.setText(String.valueOf(intentIN.getDoubleExtra("longitude", 0.0)));

        imageBranchForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");
            }
        });

        btnBranchForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Branch branch = new Branch(
                        editNameBranchForm.getText().toString(),
                        editPhoneBranchForm.getText().toString(),
                        urlImage,
                        Double.parseDouble(textLatitudeBranchForm.getText().toString().trim()),
                        Double.parseDouble(textLongitudeBranchForm.getText().toString().trim())
                );

                if(intentIN.getBooleanExtra("edit", false)){
                    String id = intentIN.getStringExtra("id");
                    branch.setId(id);
                    dbFirebaseBranch.updateData(branch);
                }else{
                    dbFirebaseBranch.insertData(branch);
                }
                Intent intent = new Intent(getApplicationContext(), BranchCatalog.class);
                startActivity(intent);
            }
        });
    }

    public void clean(){
        editNameBranchForm.setText("");
        editPhoneBranchForm.setText("");
        imageBranchForm.setImageResource(R.drawable.ic_launcher_background);
    }
}
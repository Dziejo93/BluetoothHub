package com.example.dziejo.bluetoothhub;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final String STREAM_MODE_CHOSEN = "Sender";
    private static ToggleButton btnBt;
    private BluetoothAdapter mBluetoothAdapter;
    private Button btnSender, btnStream;
    private boolean WENT_THROUGH_BT_CHECK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElems();
        isExternalStorageWritable();

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initElems() {

        btnBt = findViewById(R.id.tglBt);
        btnStream = findViewById(R.id.btnStream);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void btChecker() {

        if (mBluetoothAdapter != null) { // Checking for btAdapter
            if (mBluetoothAdapter.isEnabled()) { //Checking is it enabled
                WENT_THROUGH_BT_CHECK = true;
            } else {
                Log.d("btAdptr", "btChecker:Bluetooth is not enabled");

                new MaterialDialog.Builder(this)
                        .title(R.string.btTitle)
                        .content(R.string.btContent)
                        .positiveText(R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                startActivityForResult(new Intent(
                                        BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
                            }
                        })
                        .negativeText(R.string.cancel)
                        .show();

            }


        } else {
            Log.d("btAdptr", "btChecker:device doesn't have bluetooth adapter");
            return;//btAdapter not avaible
        }
    }


    public void onStreamBtnClicked(View view) {
        btChecker();
        singleFileBrowser("/sdcard");
    }

    public void onRecieveStream(View view) {
        btChecker();
        Intent streamRecieveIntent = new Intent(this, SimpleActivity.class);
        startActivity(streamRecieveIntent);
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void singleFileBrowser(String startPath) {
        new ChooserDialog().with(this)
                .withStartFile(startPath)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(MainActivity.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                        chosenFile(path);

                    }
                })
                .build()
                .show();

    }


    private void chosenFile(String mydir) {


        Intent openSender = new Intent(this, SimpleActivity.class);
        openSender.putExtra("File", mydir);
        startActivityForResult(openSender, 0);
    }

}


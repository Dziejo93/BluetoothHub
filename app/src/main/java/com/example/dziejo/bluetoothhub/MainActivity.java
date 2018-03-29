package com.example.dziejo.bluetoothhub;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class MainActivity extends AppCompatActivity {
    public static final String STREAM_MODE_CHOSEN = "Sender";
    public static final String NORMAL_MODE_CHOSEN = "Normal";
    private static ToggleButton btnBt;
    private BluetoothAdapter mBluetoothAdapter;
    private Button btnSender, btnStream;
    private boolean WENT_THROUGH_BT_CHECK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElems();


    }

    public void clickSenderMode(View view) {
        btChecker();
        if (WENT_THROUGH_BT_CHECK) {
            Intent intentFileChooser = new Intent(MainActivity.this, FileChooser.class);
            intentFileChooser.putExtra("Which", NORMAL_MODE_CHOSEN);
            startActivity(intentFileChooser);
        }

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
        btnSender = findViewById(R.id.btnSender);
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
        Intent streamIntent = new Intent(MainActivity.this, FileChooser.class);
        streamIntent.putExtra("Which", STREAM_MODE_CHOSEN);
        startActivity(streamIntent);
    }
}


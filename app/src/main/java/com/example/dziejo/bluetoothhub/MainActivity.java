package com.example.dziejo.bluetoothhub;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private Button btnSender;
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
            Intent intentFileChooser = new Intent(MainActivity.this, DeviceListActivity.class);
            startActivityForResult(intentFileChooser,0);
        }

    }


    private void initElems() {
        btnSender = findViewById(R.id.btnSender);
    }

    private void btChecker() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) { // Checking for btAdapter
            if (mBluetoothAdapter.isEnabled()) { //Checking is it enabled
                WENT_THROUGH_BT_CHECK = true;
                /*if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {

                } else {
                    Log.d("btAdptr", "btChecker:Bluetooth state is off");
                    return; // state is off
                }*/

            } else {
                Log.d("btAdptr", "btChecker:Bluetooth is not enabled");

                new MaterialDialog.Builder(this)
                        .title(R.string.btTitle)
                        .content(R.string.btContent)
                        .positiveText(R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog,DialogAction which) {
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



}


package com.example.dziejo.bluetoothhub;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class SendMode extends AppCompatActivity {

    private static final String BT_ADRESS = "btAdress";
    private TextView txtPath;
    private Button btnSend;
    private String fileDir, address;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mode);

        txtPath = findViewById(R.id.editTxt1);
        btnSend = findViewById(R.id.btnSend);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Intent intentFile = getIntent();
        fileDir = intentFile.getStringExtra("File");

        if (fileDir != null) {
            File x = new File(intentFile.getStringExtra("File"));
            String lol = x.getAbsolutePath();
            if (lol != null) txtPath.setText(lol);
        }
        address = "0";//xD nie działa

    }

    public void sendViaBluetooth(View view) {
        if (!fileDir.equals(null)) {
            if (mBluetoothAdapter == null) {
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_LONG).show();
            } else {
                sendFileViaBluetooth(fileDir, address);
            }
        } else {
            Toast.makeText(this, "Please select a file.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean sendFileViaBluetooth(String file_path, String destinationMAC) {

        if (mBluetoothAdapter == null)
            return false;
        BluetoothDevice btdev = mBluetoothAdapter.getRemoteDevice(destinationMAC);
        Log.d(BT_ADRESS, "sendFileViaBluetooth: " + btdev.getAddress());
        if (btdev == null)
            return false;


        Uri uri = Uri.fromFile(new File(file_path));

        Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_STREAM, uri)
                .setType("*/*");

        List<ResolveInfo> resolvedActivities = getPackageManager().queryIntentActivities(shareIntent, 0);

        boolean found = false;
        for (ResolveInfo actInfo : resolvedActivities) {
            if (actInfo.activityInfo.packageName.equals("com.example.dziejo.bluetoothhub")) {
                shareIntent.setComponent(new ComponentName(actInfo.activityInfo.packageName, actInfo.activityInfo.name));
                shareIntent.putExtra("com.example.dziejo.bluetoothhub.extra.DEVICE_ADDRESS", btdev);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                found = true;
                break;
            }
        }

        if (found) {
            startActivity(shareIntent);
            return true;
        }

        return false;
    }


}

package com.example.dziejo.bluetoothhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;

public class FileChooser extends AppCompatActivity {
    private String whichMode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chooser);
        Intent whichModeIntent = getIntent();
        whichMode = whichModeIntent.getStringExtra("Which");
        singleFileBrowser("/sdcard");
    }


    private void singleFileBrowser(String startPath) {
        new ChooserDialog().with(this)
                .withStartFile(startPath)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(FileChooser.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                        chosenFile(path);

                    }
                })
                .build()
                .show();

    }


    private void chosenFile(String mydir) {


        Intent openSender = new Intent(FileChooser.this, SimpleActivity.class);
        openSender.putExtra("File", mydir);
        startActivityForResult(openSender, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}

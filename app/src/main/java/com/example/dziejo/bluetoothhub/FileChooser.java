package com.example.dziejo.bluetoothhub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;

public class FileChooser extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chooser);
        typeChooser();
    }


    private void typeChooser() {
        new MaterialDialog.Builder(this)
                .title("Choose type")
                .content("What do You want to send")
                .positiveText("File")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        singleFileBrowser("/root/");
                    }
                })
                .negativeText("Folder")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        wholeFolderBrowser("/root/");
                    }
                })
                .show();

    }

    private void singleFileBrowser(String path) {
        new ChooserDialog().with(this)
                .withStartFile(path)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(FileChooser.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .show();
    }


    private void wholeFolderBrowser(String path) {
        new ChooserDialog().with(this)
                .withFilter(true, false)
                .withStartFile(path)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(FileChooser.this, "FOLDER: " + path, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .show();
    }

}

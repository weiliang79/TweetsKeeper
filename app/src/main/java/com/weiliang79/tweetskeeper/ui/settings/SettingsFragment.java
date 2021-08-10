package com.weiliang79.tweetskeeper.ui.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.ExportDatabaseAsyncTask;
import com.weiliang79.tweetskeeper.database.ImportDatabaseAsyncTask;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final int PERMISSION_REQUEST_FILE_WRITE_READ_CODE = 101;

    private Toolbar toolbar;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        if(toolbar != null){
            toolbar.getMenu().clear();
        }

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        if(preference.getKey().equals("export_database")){

            if(checkPermission()){
                new ExportDatabaseAsyncTask(getContext()).execute();
            } else {
                requestPermission();
            }

            return true;

        } else if(preference.getKey().equals("import_database")){

            if(checkPermission()){
                new ImportDatabaseAsyncTask(getContext()).execute();
            } else {
                requestPermission();
            }

            return true;

        } else if(preference.getKey().equals("about_this_app")){

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.url_github)));
            startActivity(intent);

        }

        return super.onPreferenceTreeClick(preference);
    }

    private boolean checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        } else {
            int readPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getContext().getPackageName())));
                startActivityForResult(intent, PERMISSION_REQUEST_FILE_WRITE_READ_CODE);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, PERMISSION_REQUEST_FILE_WRITE_READ_CODE);
            }

        } else {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_FILE_WRITE_READ_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PERMISSION_REQUEST_FILE_WRITE_READ_CODE){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                if(!Environment.isExternalStorageManager()) {
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_REQUEST_FILE_WRITE_READ_CODE:
                for(int i = 0; i < grantResults.length; i++){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getContext(), "" + permissions[i] + " granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "" + permissions[i] + " denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PreferenceScreen preferenceScreen = getPreferenceScreen();

    }
}
package camdev.sokra.topnews.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class RequestPermissions {
    // Check if we have write permission
    public static void checkStorageAccessPermissions(Context context){
        int permission = ActivityCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((AppCompatActivity)context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
    }


}

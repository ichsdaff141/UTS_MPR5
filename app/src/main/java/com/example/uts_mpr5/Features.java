package com.example.uts_mpr5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

public class Features extends AppCompatActivity {
    private PopupWindow popupWindow;
    LinearLayout popupLayout;
    WebView webView;
    private boolean addFlash;
    private boolean webOn = false;
    private boolean flashLightStatus = false;
    private Camera camera;
    Camera.Parameters params;
    private Uri fileUri;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final  String IMAGE_DIRECTORY_NAME = "IKLC";
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_VIDEO = 2;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        popupLayout = findViewById(R.id.pop_layout);
        webView = findViewById(R.id.web_iklc);

        checkPermission();
    }

    public void writeIO(View view) {
        String filenameOutput = Environment.getExternalStorageDirectory() + "/iklc.txt";
        SimpleDateFormat id = new SimpleDateFormat("dd MMM yyyy @ HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

        try {
            PrintWriter pw = new PrintWriter(filenameOutput);
            pw.println("Selamat Datang");
            pw.println("Ini adalah file yang barusan Anda buat di ");
            pw.println(filenameOutput);
            pw.println();
            pw.println("Dibuat pada : " + id.format(calendar.getTime()));
            pw.close();

            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setMessage("Data selesai ditulis \nData disimpan di " + filenameOutput);
            dialog.show();
        }
        catch (FileNotFoundException e) {
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setMessage("Error :\n" + e.toString());
            dialog.show();
        }
    }

    public void readIO(View view) {
        String filenameInput = Environment.getExternalStorageDirectory() + "/iklc.txt";
        File fileInput = new File(filenameInput);
        Scanner scanner;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            scanner = new Scanner(fileInput);
            String baris;
            while (scanner.hasNextLine()) {
                baris = scanner.nextLine();
                stringBuilder.append(baris + '\n');
            }
            scanner.close();
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setMessage(stringBuilder.toString());
            dialog.show();
        }
        catch (FileNotFoundException e) {
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setMessage("Error membaca data \n Keterangan error :\n" + e.toString());
            dialog.show();
        }
    }

    public void camera(View view) {
        String fileName = System.currentTimeMillis()+".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void flash(View view) {
        addFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            }
            catch (Exception e) {
                Log.e("Err", "Flash: " + e.getMessage());
            }
        }
        if (addFlash) {
            if (flashLightStatus)
                flashLightOff();
            else
                flashLightOn();
        }
        else
            Toast.makeText(Features.this, "no Available Hardware", Toast.LENGTH_SHORT).show();
    }

    public void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(String.valueOf(cameraId), true);
            flashLightStatus = true;
        }
        catch (CameraAccessException e) {

        }
    }

    public void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            flashLightStatus = false;
        }
        catch (CameraAccessException e) {

        }
    }

    public void internet(View view) {
        if (webOn) popupWindow.dismiss();
        else {
            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_web, null);

            popupWindow = new PopupWindow(layout, 1000, 2000);
            webView = layout.findViewById(R.id.web_iklc);

            webView.loadUrl("https://www.google.com/");
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(true);
            popupWindow.showAtLocation(this.findViewById(R.id.mainLayout), Gravity.CENTER,0,0);
            webOn = true;
        }
    }

    public void video(View view) {
        String fileName = System.currentTimeMillis()+".mp4";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.TITLE, fileName);
        fileUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    private Uri getOutputMediaFileUri(int mediaTypeImage) {
        return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
    }

    public File getOutputMediaFile(int mediaTypeImage) {
        File mediaStoreDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (!mediaStoreDir.exists()) {
            if (!mediaStoreDir.mkdir()){
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + "directory");
                return null;
            }
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;

        if (mediaTypeImage == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStoreDir.getPath() + File.pathSeparator + "/IMG_" + timestamp + ".jpg");
        }
        else if (mediaTypeImage == MEDIA_TYPE_VIDEO){
            mediaFile = new File(mediaStoreDir.getPath() + File.pathSeparator + "/VID_" + timestamp + ".mp4");
        }
        else {
            return null;
        }
        return mediaFile;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        //exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission" + permissions[index] + "not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                //all permission were granted
                break;
        }
    }

    public void checkPermission() {
        final List<String> missingPermission = new ArrayList<>();
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(permission);
            }
        }
        if (!missingPermission.isEmpty()) {
            final String[] permission = missingPermission
                    .toArray(new String[missingPermission.size()]);
            ActivityCompat.requestPermissions(this, permission, REQUEST_CODE_ASK_PERMISSIONS);
        }
        else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS, grantResults);
        }
    }
}
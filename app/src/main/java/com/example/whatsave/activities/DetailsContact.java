package com.example.whatsave.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsave.adapters.DetailPagerAdapter;
import com.example.whatsave.data.DBHelper;
import com.example.whatsave.R;
import com.example.whatsave.helper.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class DetailsContact extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Constants.TOOLBAR_NAME);
        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            String type = intent.getType();
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if (type.equalsIgnoreCase("text/plain")) {
                    handleTextData(intent);
                } else if (type.startsWith("image/")) {
                    handleImage(intent);
                } else if (type.startsWith("audio/")) {
                    handleAudio(intent);
                } else if (type.startsWith("video/")) {
                    handleVideo(intent);
                } else if (type.equalsIgnoreCase("application/pdf")) {
                    handlePdfFile(intent);
                }
            } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                if (type.startsWith("image/")) {
                    handleMultipleImage(intent);
                }
            }
        }
        ViewPager2 viewPager2 = findViewById(R.id.tabviewpager2);
        viewPager2.setAdapter(new DetailPagerAdapter(this));
        viewPager2.setUserInputEnabled(false);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setIcon(R.drawable.ic_pdf);
                    break;
                }
                case 1: {
                    tab.setIcon(R.drawable.ic_image);
                    break;
                }
                case 2: {
                    tab.setIcon(R.drawable.ic_audio);
                    break;
                }
                case 3: {
                    tab.setIcon(R.drawable.ic_video);
                    break;
                }
//                    case 4: {
//                        tab.setIcon(R.drawable.ic_document);
//                        break;
//                    }
            }
        });
        tabLayoutMediator.attach();
    }

    private void handleVideo(Intent intent) {
        String videoName = null;
        String[] name = {null};
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String uriPath = uri.getPath();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            videoName = getName(uriPath);
        }
        String videoname = videoName + ".mp4";
        if (uri != null) {
            File path = getDir(Constants.DIR_NAME_VIDEO, MODE_PRIVATE);
            File file = new File(path, videoname);
            InputStream is = null;
            BufferedOutputStream bos = null;
            try {
                is = getApplicationContext().getContentResolver().openInputStream(uri);
                bos = new BufferedOutputStream(new FileOutputStream(file, false));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name");
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
                builder.setView(customLayout);
                EditText editText1 = customLayout.findViewById(R.id.editText1);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    long id = System.currentTimeMillis();
                    name[0] = editText1.getText().toString();
                    if (name[0].compareTo("") == 0) {
                        Toast.makeText(getApplicationContext(),
                                "missing", Toast.LENGTH_SHORT).show();
                    } else {
                        dbHelper.insertVideoData(id, name[0], videoname);
                    }
                }).setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                byte[] buf = new byte[1024];
                is.read(buf);
                do {
                    bos.write(buf);
                } while (is.read(buf) != -1);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (bos != null) bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.detail_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.edit:
//                edit();
//                return true;
//            case R.id.delete:
//                delete();
//                return true;
//            case R.id.detail:
//                detail();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void detail() {
//
//    }
//
//    private void delete() {
//
//    }
//
//    private void edit() {
//
//    }

    private void handleAudio(Intent intent) {
        String audioName = null;
        final String[] name = {null};
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String uriPath = uri.getPath();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            audioName = getName(uriPath);
        }
        String audioname = audioName + ".mp3";
        if (uri != null) {
            File path = getDir(Constants.DIR_NAME_AUDIO, MODE_PRIVATE);
            File file = new File(path, audioname);
            InputStream is = null;
            BufferedOutputStream bos = null;
            try {
                is = getApplicationContext().getContentResolver().openInputStream(uri);
                bos = new BufferedOutputStream(new FileOutputStream(file, false));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name");
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
                builder.setView(customLayout);
                EditText editText1 = customLayout.findViewById(R.id.editText1);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id = System.currentTimeMillis();
                        name[0] = editText1.getText().toString();
                        if (name[0].compareTo("") == 0) {
                            Toast.makeText(getApplicationContext(),
                                    "missing", Toast.LENGTH_SHORT).show();
                        } else {
                            dbHelper.insertAudioData(id, name[0], audioname);
                        }
                    }
                }).setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                byte[] buf = new byte[1024];
                is.read(buf);
                do {
                    bos.write(buf);
                } while (is.read(buf) != -1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (bos != null) bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handlePdfFile(Intent intent) {
        Uri pdffile = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        final String[] name = {null};
        String pdfname;
        String uriPath = pdffile.getPath();
        String pdfName = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            pdfName = getName(uriPath);
        }
        pdfname = pdfName + ".pdf";
        if (pdffile != null) {
            File path = getDir(Constants.DIR_NAME_PDF, MODE_PRIVATE);
            File file = new File(path, pdfname);
            InputStream is = null;
            BufferedOutputStream bos = null;
            try {
                is = getApplicationContext().getContentResolver().openInputStream(pdffile);
                bos = new BufferedOutputStream(new FileOutputStream(file, false));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name");
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
                builder.setView(customLayout);
                EditText editText1 = customLayout.findViewById(R.id.editText1);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id = System.currentTimeMillis();
                        name[0] = editText1.getText().toString();
                        if (name[0].compareTo("") == 0) {
                            Toast.makeText(getApplicationContext(),
                                    "missing", Toast.LENGTH_SHORT).show();
                        } else {
                            dbHelper.insertPdfData(id, name[0], pdfname);
                        }
                    }
                }).setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                byte[] buf = new byte[1024];
                is.read(buf);
                do {
                    bos.write(buf);
                } while (is.read(buf) != -1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (bos != null) bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void handleImage(Intent intent) {
        final String[] name = {null};
        String imagename;
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String uriPath = uri.getPath();
        String ImageName = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            ImageName = getName(uriPath);
        }
        imagename = ImageName + ".jpeg";
        if (uri != null) {
            File path = getDir(Constants.DIR_NAME_IMAGE, MODE_PRIVATE);
            File file = new File(path, imagename);
            InputStream is = null;
            BufferedOutputStream bos = null;
            try {
                is = getApplicationContext().getContentResolver().openInputStream(uri);
                bos = new BufferedOutputStream(new FileOutputStream(file, false));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name");
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
                builder.setView(customLayout);
//                EditText editText = customLayout.findViewById(R.id.editText);
                EditText editText1 = customLayout.findViewById(R.id.editText1);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id = System.currentTimeMillis();
                        name[0] = editText1.getText().toString();
                        if (name[0].compareTo("") == 0) {
                            Toast.makeText(getApplicationContext(),
                                    "missing", Toast.LENGTH_SHORT).show();
                        } else {
                            dbHelper.insertImageData(id, name[0], imagename);
                        }
                    }
                }).setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                byte[] buf = new byte[1024];
                is.read(buf);
                do {
                    bos.write(buf);
                } while (is.read(buf) != -1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (bos != null) bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleTextData(Intent intent) {
        String textdata = intent.getStringExtra(Intent.EXTRA_TEXT);
//        if (textdata != null) {
//        }
    }

    private void handleMultipleImage(Intent intent) {
        ArrayList<Uri> imageList = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageList != null) {
            for (Uri uri : imageList) {
                Log.d("Path ", "" + uri.getPath());
            }
        }
    }

    public static String getName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('/');
        return filename.substring(index + 1);
    }
}
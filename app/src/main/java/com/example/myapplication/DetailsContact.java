package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.icu.util.ULocale.getName;

public class DetailsContact extends AppCompatActivity {
    private String textdata;
    EditText editText;
    private TextView mTextView;
    private ImageView mImageView;
    private ViewPager2 viewPager2;
    Button btn;
    private static final String DIR_NAME_IMAGE = "Images";
    private static final String DIR_NAME_AUDIO = "Audio";
    private static final String DIR_NAME_VIDEO = "Video";
    private static final String IMAGE_NAME = "Test_Image.jpg";
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);
        dbHelper = new DBHelper(this);
        mImageView = findViewById(R.id.myImageView);
//        btn = findViewById(R.id.btn);
//        btn.setOnClickListener(this::insert);
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


        viewPager2 = findViewById(R.id.tabviewpager2);
        viewPager2.setAdapter(new DetailPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Location");
                        tab.setIcon(R.drawable.ic_location);
                        break;
                    }
                    case 1: {
                        tab.setText("Images");
                        tab.setIcon(R.drawable.ic_image);
                        break;
                    }
                    case 2: {
                        tab.setText("Audio");
                        tab.setIcon(R.drawable.ic_audio);
                        break;
                    }
                    case 3: {
                        tab.setText("Video");
                        tab.setIcon(R.drawable.ic_video);
                        break;
                    }
                    case 4: {
                        tab.setText("Documents");
                        tab.setIcon(R.drawable.ic_pending);
                        break;
                    }
                }
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
            File path = getDir(DIR_NAME_VIDEO, MODE_PRIVATE);
            File file = new File(path, videoname);
            InputStream is = null;
            BufferedOutputStream bos = null;
            try {
                is = getApplicationContext().getContentResolver().openInputStream(uri);
                bos = new BufferedOutputStream(new FileOutputStream(file, false));
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("New Tag");
                alertDialog.setMessage("Enter Tag");

                final EditText input = new EditText(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                name[0] = input.getText().toString();
                                if (name[0].compareTo("") == 0) {
                                    Toast.makeText(getApplicationContext(),
                                            "missing", Toast.LENGTH_SHORT).show();
                                } else {
                                    dbHelper.insertVideoData(name[0], videoname);
                                }
                            }

                        });

                alertDialog.show();
                byte[] buf = new byte[1024];
                is.read(buf);
                do {
                    bos.write(buf);
                } while (is.read(buf) != -1);

            } catch (IOException e) {
                e.printStackTrace();
            }
//             finally {
//                try {
//                    if (is != null) is.close();
//                    if (bos != null) bos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            Log.d("Video File Path : ", "" + uri.getPath());
        }
    }

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
            File path = getDir(DIR_NAME_AUDIO, MODE_PRIVATE);
            File file = new File(path, audioname);
            InputStream is = null;
            BufferedOutputStream bos = null;
            try {
                is = getApplicationContext().getContentResolver().openInputStream(uri);
                bos = new BufferedOutputStream(new FileOutputStream(file, false));
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("New Tag");
                alertDialog.setMessage("Enter Tag");

                final EditText input = new EditText(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                name[0] = input.getText().toString();
                                if (name[0].compareTo("") == 0) {
                                    Toast.makeText(getApplicationContext(),
                                            "missing", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "save", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                alertDialog.show();
                dbHelper.insertAudioData(name[0], audioname);
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
            Log.d("Audio File Path : ", "" + uri.getPath());
        }
    }

    private void insert(View view) {
        String name1 = null, messag2 = null;
//        editText = findViewById(R.id.ed_text);
//        messag = editText.getText().toString();
        dbHelper.insertLocatioData(name1, messag2);
    }

    private void handlePdfFile(Intent intent) {
        Uri pdffile = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (pdffile != null) {
            Log.d("Pdf File Path : ", "" + pdffile.getPath());
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
            File path = getDir(DIR_NAME_IMAGE, MODE_PRIVATE);
            File file = new File(path, imagename);
            InputStream is = null;
            BufferedOutputStream bos = null;
            try {
                is = getApplicationContext().getContentResolver().openInputStream(uri);
                bos = new BufferedOutputStream(new FileOutputStream(file, false));
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("New Tag");
                alertDialog.setMessage("Enter Tag");

                final EditText input = new EditText(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                name[0] = input.getText().toString();
                                if (name[0].compareTo("") == 0) {
                                    Toast.makeText(getApplicationContext(),
                                            "missing", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "save", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                alertDialog.show();
//                dbHelper.insertImageData(name[0], imagename);
                byte[] buf = new byte[1024];
                is.read(buf);
                do {
                    bos.write(buf);
                } while (is.read(buf) != -1);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            finally {
//                try {
//                    if (is != null) is.close();
//                    if (bos != null) bos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            Log.d("Image File Path : ", "" + uri.getPath());
        }
    }

    private void handleTextData(Intent intent) {
        textdata = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (textdata != null) {

//            mTextView = findViewById(R.id.myFrTextView);
//            mTextView.setVisibility(View.VISIBLE);
//            mTextView.setText(textdata);


            Log.d("TextDat", "" + textdata);
        }
    }

    private void handleMultipleImage(Intent intent) {
        ArrayList<Uri> imageList = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageList != null) {
            for (Uri uri : imageList) {
                Log.d("Path ", "" + uri.getPath());
            }
        }
    }

    private void readImage(View view) {
        Bitmap image = null;
        File path = getDir(DIR_NAME_IMAGE, MODE_PRIVATE);
        File file = new File(path, IMAGE_NAME);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            image = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mImageView.setImageBitmap(image);
    }

    public static String getName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('/');
        return filename.substring(index + 1);
    }
}
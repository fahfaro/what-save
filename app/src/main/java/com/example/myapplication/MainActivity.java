package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton btnFab;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    String messag;
    private DBHelper db;
    private static final String DIR_NAME_IMAGE = "Images";
    private static final String IMAGE_NAME = "4747.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
//        mainAdapter = new MainAdapter(this);
//        recyclerView = findViewById(R.id.contact_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        recyclerView.setAdapter(mainAdapter);
        ImageView mImageView = findViewById(R.id.myImageViewMain);
        Bitmap image = null;
        File path = getDir(DIR_NAME_IMAGE, MODE_PRIVATE);
        File file = new File(path, IMAGE_NAME );
        InputStream inputStream = null;
        if (file.exists()){
            Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
        try {
            inputStream = new FileInputStream(file);
            image = BitmapFactory.decodeStream(inputStream);
            mImageView.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        btnFab = findViewById(R.id.fabbtn_new);
        btnFab.setOnClickListener(this::newTask);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }
    private void newTask(View view) {
        db.insertLocatioData("Location");
        db.insertImageData("Image");
        db.insertAudioData("Audio");
        db.insertVideoData("Video");
        db.insertDocumentData("Documents");
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
//        alertDialog.setTitle("New Tag");
//        alertDialog.setMessage("Enter Tag");
//
//        final EditText input = new EditText(MainActivity.this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);
//        alertDialog.setView(input);
//
//        alertDialog.setPositiveButton("YES",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String tag = input.getText().toString();
//                        if (tag.compareTo("") == 0) {
//                            Toast.makeText(getApplicationContext(),
//                                    "missing", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "save", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                });
//
//        alertDialog.setNegativeButton("NO",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        alertDialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                search();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void search() {
        Intent intent = new Intent(this,DetailsContact.class);
        startActivity(intent);
    }

}
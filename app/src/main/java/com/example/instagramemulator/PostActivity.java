package com.example.instagramemulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.instagramemulator.Models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class PostActivity extends AppCompatActivity {
    public static final String TAG = "POST_ACTIVITY";
    public final Context context = this;
    EditText etCaption;
    Button btnPost;
    ImageView ivPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etCaption = findViewById(R.id.etCaption);
        btnPost = findViewById(R.id.btnPost);
        ivPicture = findViewById(R.id.ivPicture);
        Bitmap picture = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("picture_bytes")
                ,0, getIntent().getByteArrayExtra("picture_bytes").length);
        ivPicture.setImageBitmap(picture);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void postToFeed(View view) {
        String caption = etCaption.getText().toString();
        Intent data = new Intent();
        data.putExtra("caption", caption);
        setResult(RESULT_OK, data);
        etCaption.setText("");
        ivPicture.setImageBitmap(null);
        finish();
    }
}
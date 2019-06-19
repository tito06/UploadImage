package com.example.uploadimage;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;


import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.net.URI;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private static int LOAD_IMAGE =1;

    Button btn, btntext;
    TextView textView;
    EditText editText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_upload);
      //  textView = findViewById(R.id.text_view);
        editText = findViewById(R.id.edit_text);
        btntext = findViewById(R.id.buttontext);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, LOAD_IMAGE);

            }
        });


        String text = editText.getText().toString();

        btntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String text = editText.getText().toString();




               Intent intent = new Intent(MainActivity.this, Text.class);
                intent.putExtra("text", editText.getText().toString());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK && null !=data){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            final ImageView imageView =findViewById(R.id.imageView);
           // imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            final Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(bitmap);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  view.setRotation(view.getRotation() +90);
                    TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();


                    if (!textRecognizer.isOperational()) {
                        Toast.makeText(getApplicationContext(), "Could not get the text", Toast.LENGTH_LONG).show();
                    } else {
                        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

                        SparseArray<TextBlock> items = textRecognizer.detect(frame);

                        StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < items.size(); i++) {
                            TextBlock myItem = items.valueAt(i);
                            sb.append(myItem.getValue());
                            sb.append("\n");
                        }

                        editText.setText(sb.toString());
                    }

                }
            });
        }
    }



}

package com.example.uploadimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Text extends AppCompatActivity {

    TextView textView, mtextView, bloodtext, name, panNo, adhar_no, adhar_dob;
    Button button_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        textView = findViewById(R.id.texoutput);
        button_search = findViewById(R.id.btn_search);
        mtextView = findViewById(R.id.mdob);
        bloodtext = findViewById(R.id.bloodtext);
        name = findViewById(R.id.name);
        panNo = findViewById(R.id.pan_number);
        adhar_no = findViewById(R.id.adhar_no);
        adhar_no = findViewById(R.id.adhar_dob);


        textView.setText(getIntent().getStringExtra("text"));

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String s = textView.getText().toString();



                //Regex for the d.o.b
                Matcher m = Pattern.compile("(\\d{1,2}/\\d{1,2}/\\d{4}|\\d{1,2}/\\d{1,2})").matcher(s);

                //Regex for the blood group
                Matcher m1 = Pattern.compile("([A|B|AB|O]\\s?)(\\+|-)([A-Z]{1})\n", Pattern.CASE_INSENSITIVE).matcher(s);

               //Regex for the name
                Matcher m2 = Pattern.compile("([A-Z]{3,}\\s?){1,3}\n").matcher(s);

                //Regex for the pan no
                Matcher m3 = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}").matcher(s);

                //Regex for the adhar card
                Matcher m4 = Pattern.compile("([0-9]{4}\\s?)([]0-9]{4}\\s?)([0-9]{4})\n").matcher(s);

                //Regex for the adhar_dob
              //  Matcher m5 = Pattern.compile("[0-9]{4}").matcher(s);

                while (m.find()){
                    mtextView.setText("D.O.B: " + m.group());
                }

                if (m1.find()){
                    bloodtext.setText("Blood group: " + m1.group());

                }
                while (m2.find()){
                    String string = m2.group();
                  //     name.setText(string.replaceAll("\bINCOME |TAX |DEPA \b", ""));

                    string = string.replaceAll("\\bINCOME |TAX |DEPA| INDIA\\b", "");

                    name.setText("Name: " +string);


                }


                if (m3.find()){
                    panNo.setText("PAN NO: " +m3.group());

                }

//                if (m5.find()){
//                    adhar_dob.setText("D.O.B: "+m5.group());
//                }


                if (m4.find()){
                    adhar_no.setText("Adhar N: "+m4.group());
                }



            }
        });



    }
}

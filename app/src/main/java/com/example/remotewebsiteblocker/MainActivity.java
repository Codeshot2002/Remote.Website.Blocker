package com.example.remotewebsiteblocker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button addbutton;
    private Button sendButton;
    private EditText website_text;
    private TextView textView;
    private String databasedata;

    ListView mylistview;
    private ArrayList<String> websites = new ArrayList<String>();
    private String webs = "";

    private int count = 0;

    FirebaseDatabase database;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // database
        database = FirebaseDatabase.getInstance();
        myref = database.getReference("Websites");

        databasedata = "";
        //all the views referenced
        addbutton = findViewById(R.id.add_button);
        website_text = findViewById(R.id.editText_website);
        sendButton = findViewById(R.id.send_button);
        mylistview = findViewById(R.id.listview);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, websites);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = website_text.getText().toString();
                if(input.matches("")) {
                    Toast.makeText(MainActivity.this, "add something!", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        websites.add(website_text.getText().toString());
                        String data = (count + 1) + ") ";
                        String output = data + websites.get(count) + "\n";
                        webs += output;
                        mylistview.setAdapter(ad);
                        count++;
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }

                }
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //send button functions
                if(websites.isEmpty()){
                    Toast.makeText(MainActivity.this,"No websites have been added!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Sent!", Toast.LENGTH_SHORT).show();
                    for(int i=0;i<websites.size();i++){
                        databasedata += websites.get(i);
                        databasedata += "\n";
                    }
                    myref.setValue(databasedata);
                }
            }
        });
    }
}
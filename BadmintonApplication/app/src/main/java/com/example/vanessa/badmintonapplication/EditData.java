package com.example.vanessa.badmintonapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Vanessa.
 */

public class EditData extends AppCompatActivity
{
    /*private TextView editText;
    ImageView editImage;
    int SelectID;
    String selectedName;*/
    private static final String TAG = "EditData";
    private Button btnSelect, btnDelete;
    private EditText tbNameScore;
    DatabaseHelper myDatabaseHelper;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        tbNameScore = (EditText) findViewById(R.id.tbNameScore);
        myDatabaseHelper = new DatabaseHelper(this);

        //get intent
        Intent receiveIntent = getIntent();

        selectedID = receiveIntent.getIntExtra("id", -1);
        selectedName = receiveIntent.getStringExtra("name");

        tbNameScore.setText(selectedName);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = tbNameScore.getText().toString();
                if(!item.equals("")){
                    myDatabaseHelper.updateName(item, selectedID, selectedName);
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.deleteName(selectedID, selectedName);
                tbNameScore.setText(" ");
                toastMessage("removed from database");

            }
        });

        /*editText = (TextView) findViewById(R.id.editName);
        editImage = (ImageView) findViewById(R.id.editImage);
        Intent intent = getIntent();
        SelectID = intent.getIntExtra("ID", -1);
        selectedName = intent.getStringExtra("name");
        if (getIntent().hasExtra("byteArray"))
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0,getIntent().getByteArrayExtra("byteArray").length);
            editImage.setImageBitmap(bitmap);
        }
        editText.setText(selectedName);*/
    }

    private void toastMessage(String you_must_enter_a_name) {
        Toast.makeText(this, you_must_enter_a_name, Toast.LENGTH_SHORT).show();
    }
}

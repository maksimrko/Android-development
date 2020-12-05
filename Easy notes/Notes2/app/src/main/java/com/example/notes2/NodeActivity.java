package com.example.notes2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class NodeActivity extends AppCompatActivity {
    EditText editTextWrite;
    EditText editTextTitle;
    private SQLiteDatabase dbNode;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        editTextWrite = findViewById(R.id.editTextWrite);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextWrite.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    //region Setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_node, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveDbNote();
                return true;
            default:
                return true;
        }
    }
//endregion

    public void saveDbNote() {
        //get text from EditText(s)
        String str = editTextTitle.getText().toString();
        String str2 = editTextWrite.getText().toString();
        //Проверка на пустую строку
        if (str.contentEquals("")) {
            //If empty - message + exit
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.msg_str_empty),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (str2.contentEquals("")) {
                       //If empty - message + exit
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.msg_str_empty),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        dbNode = getBaseContext().openOrCreateDatabase("node.db", MODE_PRIVATE, null);
		//Query to create table
        String query = "CREATE TABLE IF NOT EXISTS Nodes(" +
                "id integer Primary key," +
                "title Varchar(256)," +
                "textWrite Varchar(256));";
        try {
            dbNode.execSQL(query);
            editTextWrite.setText("Con Ok");

        } catch (SQLException ex) {
            editTextWrite.setText(ex.getMessage());
        }

        try {
            dbNode.execSQL("INSERT INTO Nodes(title,textWrite) Values('" + str + "','"+str2+"')");
            editTextWrite.setText("");
            editTextTitle.setText("");
            return;
        } catch (SQLException ex) {
            editTextWrite.setText(ex.getMessage());
            return;
        }

    }


    public void markListClick(View view) {
        editTextWrite.setText("•");
    }
}
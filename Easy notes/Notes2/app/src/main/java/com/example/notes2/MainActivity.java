package com.example.notes2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView viewTextTitle;
    private TextView viewTextWrite;
    SimpleCursorAdapter userAdapter;
    private Cursor answer;
    SQLiteDatabase dbNode;
    private ListView listViewDB;  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewTextWrite = findViewById(R.id.viewTextWrite);
        listViewDB = findViewById(R.id.listViewDB);
        registerForContextMenu(listViewDB);
    }

    //region Work with menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Open NodeActivity
    public void addNewNoteClick(MenuItem item) {
        Intent dbActivity = new Intent(this, NodeActivity.class);
        startActivity(dbActivity);
    }

    //Change item menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {       
            case R.id.menuLook:
                showDbNode();
                return true;
            default:
                return true;
        }
    }

    //Create context menu:
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                delRec(info.id);
                userAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Запись удалена", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void delRec(long id) {
        dbNode.delete("Nodes", "id" + " = " + id, null);
        String dbN = "title";
        String dbW = "textWrite";

        String[] headers = new String[]{dbN, dbW};
        answer = dbNode.rawQuery("Select id as _id, title, textWrite From Nodes", null);
        userAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.two_line_list_item,
                answer, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        viewTextWrite.setText("Количество заметок: " + String.valueOf(answer.getCount()));
        listViewDB.setAdapter(userAdapter);
        listViewDB.invalidateViews();

    }

    //endregion

   
    @Override
    public void onResume() {
        super.onResume();
        showDbNode();
    }

    public void showDbNode() {
        dbNode = getBaseContext().openOrCreateDatabase("node.db", MODE_PRIVATE, null);
        String dbN = "title";
        String dbW = "textWrite";
        
        String[] headers = new String[]{dbN, dbW};
        answer = dbNode.rawQuery("Select id as _id, title, textWrite From Nodes", null);
        userAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.two_line_list_item,
                answer, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        viewTextWrite.setText("Количество заметок: " + String.valueOf(answer.getCount()));
        listViewDB.setAdapter(userAdapter);
    }


    public void addNodes(View view) {
        Intent dbActivity = new Intent(this, NodeActivity.class);
        startActivity(dbActivity);
    }


}

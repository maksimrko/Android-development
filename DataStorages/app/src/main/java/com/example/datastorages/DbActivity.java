package com.example.datastorages;

//region Comments
/*
- Работа с БД
- В андроид встроена Subd SQlite
- Каждому приложению положена БД(нет необходимости устанавливать подключения
-
*/
//endregion

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DbActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private TextView tvMsg;
    private EditText etNewStr;
    private String txtforDb;
    private String txtForView;
    private Runnable showDbStrings;
    private Cursor answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        tvMsg = findViewById(R.id.tvMsg);
        etNewStr = findViewById(R.id.etNewStr);
        tvMsg.setMovementMethod(new ScrollingMovementMethod());

    }

    public void btnAddClick(View view) {
        //Снимаем строку из EditText
        String str = etNewStr.getText().toString();
        //Проверка на пустую строку
        if (str.contentEquals("")) {
            //Если пустая  - сообщение + выход
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.msg_str_empty),//достанет из ресурсов и вернет строку(переведет ее)
                    Toast.LENGTH_SHORT).show();
            return;
        }
        txtforDb=str;
        (new  Thread(showDbStrings)).start();

    }

    public DbActivity() {
        //Внизу делаем через поток
        showDbStrings = () -> {
            db = getBaseContext().openOrCreateDatabase("datastorages.db", MODE_PRIVATE, null);
//            String query = "CREATE TABLE IF NOT EXISTS Strings(" +
//                    "id integer Primary key," +
//                    "str Varchar(256)," +
//                    "moment Datetime default current_timestamp);";
//            try {
//                db.execSQL(query);
//                tvMsg.setText("");
//
//            } catch (SQLException ex) {
//                tvMsg.setText(ex.getMessage());
//            }

            //Вносим строку в БД
            try {
                db.execSQL("INSERT INTO Strings(str) Values('" + txtforDb + "')");
                etNewStr.setText("");
                //tvMsg.setText("OK");
            } catch (SQLException ex) {
                tvMsg.setText(ex.getMessage());
                return;
            }

//            //Будет возвращать результат
//            String txt="";
//
//            //Дз show() onclick присобачить к button будет выводить в tvMsg
//            answer=null;
//            //Cursor ans = null; // Sql command
//            try {
//                answer = db.rawQuery("Select *From Strings", null);
//            } catch (SQLException ex) {
//                tvMsg.setText(ex.getMessage());
//            }
//            //перебор записей из базы данных через цикл while
//            boolean hasNext = answer.moveToFirst();
//            while (hasNext) {
//                txt += answer.getInt(answer.getColumnIndex("id"))
//                        + " "
//                        + answer.getString(answer.getColumnIndex("str"))
//                        + "\n";
//                hasNext = answer.moveToNext();
//            }
//            txtForView=txt;
//            runOnUiThread(()->{tvMsg.setText(txtForView);});
        };
    }

    public  void showDb(){
        db = getBaseContext().openOrCreateDatabase("datastorages.db", MODE_PRIVATE, null);
        //Будет возвращать результат
        String txt="";
        answer=null;
        //Cursor ans = null; // Sql command
        try {
            answer = db.rawQuery("Select *From Strings", null);
        } catch (SQLException ex) {
            tvMsg.setText(ex.getMessage());
        }
        //перебор записей из базы данных через цикл while
        boolean hasNext = answer.moveToFirst();
        while (hasNext) {
            txt += answer.getInt(answer.getColumnIndex("id"))
                    + " "
                    + answer.getString(answer.getColumnIndex("str"))
                    + "\n";
            hasNext = answer.moveToNext();
        }
        txtForView=txt;
       // tvMsg.setText(txtForView);
        runOnUiThread(()->{tvMsg.setText(txtForView);});
    }
    public void showDataBase(View view) {
    showDb();
    }
}
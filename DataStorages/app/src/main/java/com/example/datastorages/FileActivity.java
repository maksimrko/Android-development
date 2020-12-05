package com.example.datastorages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileActivity extends AppCompatActivity {

    private static final int OPEN_FILE_DIALOG = 1;// Запускает активность
    private TextView tvFileContent;
    private EditText etNewFileString;
    private String fileName;
    private SQLiteDatabase db;
    private ImageView imgContent;
    private Cursor answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        tvFileContent = findViewById(R.id.tvFileContent);
        etNewFileString = findViewById(R.id.etNewFileStr);
        tvFileContent.setMovementMethod(new ScrollingMovementMethod());
        imgContent=findViewById(R.id.imgContent);
    }

    public void btnAddFileClick(View view) {
        // Просмотреть обзор "работа с файлами Android"
        // 1. Файл только для данного приложения?
        // 2. Файл приложения или сторонний файл или используем готовый?
        // 3. Файл для приложения , без общего доступа. Хранилище данных
        //FileOutputStream  - низкоуревный доступ write(byte[])
        fileName = "data.txt";
        FileOutputStream f;
        // new FileOutputStream для нашего файла доступ только для чтения
        // для доступа к таким файлам openFileOutput

        try {
            f = openFileOutput(fileName, Context.MODE_APPEND);
        } catch (FileNotFoundException ex) {
            tvFileContent.setText(ex.getMessage());
            return;
        }

        String txt = etNewFileString.getText().toString() + "\n";
        byte[] buf = txt.getBytes();
        try {
            f.write(buf);
            f.flush(); // сбросить на диск, сохранить
            f.close(); // закрываем файл. Сделали так, что не создавать еще один try/catch
        } catch (IOException e) {
            tvFileContent.setText(e.getMessage());
            return;
        }
        etNewFileString.setText("");
//        //Чтение файла
//        FileInputStream fr;
//        try {
//            fr = openFileInput(fileName);
//        } catch (FileNotFoundException ex) {
//            tvFileContent.setText(ex.getMessage());
//            return;
//        }
//
//        buf = new byte[1024];
//        try {
//            fr.read(buf);
//        } catch (IOException ex) {
//            tvFileContent.setText(ex.getMessage());
//            return;
//        }
//
//        //Считаные байты перевели в string
//        String str = new String(buf);
//        tvFileContent.setText(str);
//        etNewFileString.setText("");

        //Более высокий уровень работы с потоками(streams), чем fileOutputString
        //  DataOutputStream writer1 = new DataOutputStream(f); // работа с основными типами данных
        //DataInputStream  чтение файла аналогично filInputStream

        //Разные уровни чтения файлов, аналоги fileOutputStream
        //InputStreamReader reader2 = new InputStreamReader(fr); // byte->char
        //BufferedReader reader3 = new BufferedReader(reader2); // readline чтение строк


    }

    public void showF() {
        fileName = "data.txt";
        //Чтение файла
        FileInputStream fr;
        try {
            fr = openFileInput(fileName);
        } catch (FileNotFoundException ex) {
            tvFileContent.setText(ex.getMessage());
            return;
        }
        byte[] buf;
        buf = new byte[1024];
        try {
            fr.read(buf);
        } catch (IOException ex) {
            tvFileContent.setText(ex.getMessage());
            return;
        }

        //Считаные байты перевели в string
        String str = new String(buf);
        tvFileContent.setText(str);
    }

    public void showFile(View view) {
        showF();
    }

    public void btnFileDialogClick(View view) {
        Intent fileDialog = new Intent(Intent.ACTION_GET_CONTENT); // Стандартны ресурс -  выбор файла
        fileDialog.setType("image/*");
        try {
            startActivityForResult(fileDialog, OPEN_FILE_DIALOG);
            // Запускает активность с обратной  связью
            // После выполнения запускается методж вызывающего обьекта и передается код OPEN_FILE_DIALOG
            //
        } catch (ActivityNotFoundException ex) {
            tvFileContent.setText(ex.getMessage());
            return;
        }
    }

    //Intent это класс для запуска других потоков активности.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        switch (requestCode) {
            case OPEN_FILE_DIALOG: // код файлового диалога
                if (resultCode == RESULT_OK) { // завершение нажатия ОК
                    //Добираемся до имени файла
                    String fname = data.getData().getEncodedPath();//encoded -> декодировани
                    tvFileContent.setText(fname);
                    //Скопировать файл в локальное хранилище
                    //Убираем из имени символы "/" (замяняем на "_")
                    fname = fname.replaceAll("/", "_");
                    try {
                        //Поток - источник
                        InputStream src = getContentResolver().openInputStream(data.getData());
                        //Поток назначения
                        OutputStream dst = openFileOutput(fname, Context.MODE_PRIVATE);
                        // Копируем через буфер
                        byte[] buf = new byte[1024];
                        while (src.read(buf) > 0) dst.write(buf);
                        dst.flush();    //Сбросить на диск
                        src.close();    //Осбодить ресурс
                        dst.close();    //Осбодить ресурс

                    } catch (IOException ex) {
                        tvFileContent.setText(ex.getMessage());
                        return;
                    }
                    //Устанавливаем перемещения файла на imageView
                    ImageView imgContent=findViewById(R.id.imgContent);
                    try( InputStream img = openFileInput(fname) ) {
                       imgContent.setImageDrawable(Drawable.createFromStream(img,null));
                       imgContent.setScaleType(ImageView.ScaleType.FIT_START);

                    }
                   catch (IOException ex) {
                       tvFileContent.setText(ex.getMessage());
                       return;
                    }
                }
                break;

        }
    }

    public void btnAddImgDb(View view) throws IOException {
        db = getBaseContext().openOrCreateDatabase("dataStoragesImg.db", MODE_PRIVATE, null);

//            String query = "CREATE TABLE IF NOT EXISTS imageTb(img)";
//            try {
//                db.execSQL(query);
//                tvFileContent.setText("OK database its load");
//
//            } catch (SQLException ex) {
//                tvFileContent.setText(ex.getMessage());
//            }


        //Вносим картинку в БД
//        try {
////            db.execSQL("INSERT INTO Strings(image) Values('" + imgContent + "')");
////            tvFileContent.setText("OK");
////        } catch (SQLException ex) {
////            tvFileContent.setText(ex.getMessage());
////            return;
////        }
        FileInputStream fis;
        fis=new FileInputStream("D:/Android Projects/DataStorages(1)/DataStorages/app/src/main/res/drawable-v24/one.png");

        byte[] image= new byte[fis.available()];
        fis.read(image);
        ContentValues values = new ContentValues();
        values.put("a",image);
        db.insert("imageTb", null,values);
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show();

    }

    public void btnShowImgFromDb(View view) {
        db = getBaseContext().openOrCreateDatabase("dataStoragesImg.db", MODE_PRIVATE, null);
        answer=null;

    }
}
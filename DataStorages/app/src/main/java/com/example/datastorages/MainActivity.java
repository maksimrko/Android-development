package com.example.datastorages;
// Сохранение данных в устройстве
// Запуск дополнительных активностей
// Файл - совокупность данных обьединенных под одним именем
// Типизированный файл/таблица
// База данных(Таблицы и связи)
// База знаний
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addDbClick(View view) {
        Intent dbActivity=new Intent(this,DbActivity.class);
        startActivity(dbActivity);
    }

    public void addFileClick(View view) {
        //Открывает другую activity.Ссылка на нее, кликаем и открываем fileActivity
        Intent fileActivity=new Intent(this,FileActivity.class);
        startActivity(fileActivity);
    }
}
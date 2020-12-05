package com.example.fifteen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.linearLayout).setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                if (canMoveLeft()) {
                    moveLeft();
                    if (isFinish()) {
                        endOfGame();
                    }
                    postchitat();
                } else {
                    Toast.makeText(MainActivity.this, "Нет хода", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipeRight() {
                if (canMoveRight()) {
                    moveRight();
                    if (isFinish()) {
                        endOfGame();
                    }
                    postchitat();

                } else {
                    Toast.makeText(MainActivity.this, "Нет хода", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipeTop() {
                if (canMoveTop()) {
                    moveTop();
                    if (isFinish()) {
                        endOfGame();
                    }
                    postchitat();

                } else {
                    Toast.makeText(MainActivity.this, "Нет хода", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipeBottom() {
                if (canMoveBottom()) {
                    moveBottom();
                    if (isFinish()) {
                        endOfGame();
                    }
                    postchitat();

                } else {
                    Toast.makeText(MainActivity.this, "Нет хода", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (savedInstanceState == null) {
            shuffle();
        }
    }

    private void swapCells(Button btn1, Button btn2) {
        CharSequence c1Button = btn1.getText();// CharSequence -> alternative string
        CharSequence c2Button = btn2.getText();// CharSequence -> alternative string

        btn1.setText(c2Button);
        btn2.setText(c1Button);

        Drawable c1background = btn1.getBackground();
        Drawable c2background = btn2.getBackground();

        btn1.setBackground(c2background);
        btn2.setBackground(c1background);
    }

    //region getsetNum
    private Button getCellByNum(int n) {
        return findViewById(getResources().getIdentifier("cell" + n, "id", getPackageName()));
    }

    private int getEmptyCellNumber() {
        for (int i = 0; i < 16; i++) {
            if (getCellByNum(i).getText().equals("")) {
                return i;
            }
        }
        return -1;
    }
    //endregion

    //region Moves
    private boolean canMoveRight() {
        return getEmptyCellNumber() % 4 != 0;
    }

    private boolean canMoveLeft() {
        return getEmptyCellNumber() % 4 != 3;
    }

    private boolean canMoveTop() {
        return getEmptyCellNumber() < 12;
    }

    private boolean canMoveBottom() {
        return getEmptyCellNumber() > 3;
    }

    private void moveRight() {
        if (canMoveRight()) {
            swapCells(getCellByNum(getEmptyCellNumber()), getCellByNum(getEmptyCellNumber() - 1));
        } else {
            Toast.makeText(MainActivity.this, "Right swipe detected", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveLeft() {
        if (canMoveLeft()) {
            swapCells(getCellByNum(getEmptyCellNumber()), getCellByNum(getEmptyCellNumber() + 1));
        } else {
            Toast.makeText(MainActivity.this, "Left swipe detected", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveTop() {
        if (canMoveTop()) {
            swapCells(getCellByNum(getEmptyCellNumber()), getCellByNum(getEmptyCellNumber() + 4));
        } else {
            Toast.makeText(MainActivity.this, "Top swipe detected", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveBottom() {
        if (canMoveBottom()) {
            swapCells(getCellByNum(getEmptyCellNumber()), getCellByNum(getEmptyCellNumber() - 4));
        } else {
            Toast.makeText(MainActivity.this, "Bottom swipe detected", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    private void shuffle() {
        Random rnd = new Random();
        int moves = 0;
        do {
            switch (rnd.nextInt(4)) {
                case 0:
                    if (canMoveRight()) {
                        moveRight();
                        moves++;

                    }
                    break;
                case 1:
                    if (canMoveLeft()) {
                        moveLeft();
                        moves++;

                    }
                    break;
                case 2:
                    if (canMoveTop()) {
                        moveTop();
                        moves++;
                    }
                    break;
                case 3:
                    if (canMoveBottom()) {
                        moveBottom();
                        moves++;

                    }
                    break;
                default:
                    break;
            }
        } while (moves < 3);
    }

    //сохранение сущности проекта сначала расписываем в ресторе, здесь активируем по key(ключу)который заложилт в put(int,string,double,float)
    @Override
    protected void onSaveInstanceState(@NonNull Bundle s) {
        super.onSaveInstanceState(s);
        Toast.makeText(MainActivity.this, "Save State", Toast.LENGTH_SHORT).show();
        //s.putInt("s1",10);//мы сохр когда актив
        for (int i = 0; i < 16; i++) {
            s.putString("cell" + i, getCellByNum(i).getText().toString());
        }
        s.putInt("emptyCellNumber", getEmptyCellNumber());

        //Сохранение количества ходов
        TextView cellCount = findViewById(R.id.countCell);
        s.putString("countCellSave", cellCount.getText().toString());
        s.putInt("countNumberStepSave", count);
    }

    //Восстановление сущности, получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle s) {
        super.onRestoreInstanceState(s);
        int s1 = s.getInt("s1");//восстановили 10
        Toast.makeText(MainActivity.this, "Restore " + s1, Toast.LENGTH_SHORT).show();

        for (int i = 0; i < 16; i++) {
            getCellByNum(i).setText(s.getString("cell" + i));
        }

        int saveEmptyCellIndex = s.getInt("emptyCellNumber");
        if (saveEmptyCellIndex != 15) {
            Drawable bg = getCellByNum(15).getBackground();
            getCellByNum(15).setBackground(getCellByNum(saveEmptyCellIndex).getBackground());
            getCellByNum(saveEmptyCellIndex).setBackground(bg);
        }

        TextView cellCount = findViewById(R.id.countCell);
        String textViewText = s.getString("countCellSave");
        cellCount.setText(textViewText);
        count = s.getInt("countNumberStepSave");
    }

    public boolean isFinish() {
        for (int i = 0; i < 15; i++) {
            if (!String.valueOf(i + 1).contentEquals(getCellByNum(i).getText())) {
                return false;
            }
        }
        return true;
    }

    private void endOfGame() {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                .setTitle("Победа!")
                .setMessage("Сыграть еще раз")
                .setIcon(android.R.drawable.ic_media_ff)
                .setPositiveButton("Еще раз", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        count = 0;
                        shuffle();
                        ((TextView) findViewById(R.id.countCell)).setText("0");

                    }
                })
                .setNegativeButton("Выход", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .show();
    }

    public void postchitat() {
        count++;
        ((TextView) findViewById(R.id.countCell)).setText("Ходов: " + count);

    }

    public void newGame() {
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newgame:
                newGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
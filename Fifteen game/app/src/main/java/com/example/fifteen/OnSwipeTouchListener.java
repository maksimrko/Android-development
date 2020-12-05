package com.example.fifteen;

import android.content.Context;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnTouchListener;

/*
-для интерфейсов implements
-для классов extends
-Context окружение активность приложения
-abs абсолют (модуль)
-velocity скорость

*/
public class OnSwipeTouchListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context ctx) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int Min_DELTA_X = 100;//размер притяжения пальца на сенсоре, чем больше тем дальше надо тянуть
        private static final int Min_DELTA_V = 100;//размер притяжения пальца на сенсоре, чем больше тем дальше надо тянуть

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float dx = e2.getX() - e1.getX();
                float dy = e2.getY() - e1.getY();
                //Получается left-right
                if (Math.abs(dx) > Math.abs(dy)) {
                    //допуски
                    if (Math.abs(dx) > Min_DELTA_X && Math.abs(velocityX) > Min_DELTA_V) {
                        if (dx > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result= true;
                    }
                }

                else if(Math.abs(dy)>Min_DELTA_X && Math.abs(velocityY)>Min_DELTA_V){
                    if(dy<0){
                        onSwipeTop();
                    }
                    else {
                        onSwipeBottom();
                    }
                    result= true;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return result;
        }


    }

    //Заготовки свайпов -> реализуем в mainActivity
     public void onSwipeTop() {
        //Toast.makeText(MyActivity.this, "top", Toast.LENGTH_SHORT).show();
    }
    public void onSwipeRight() {
       // Toast.makeText(MyActivity.this, "right", Toast.LENGTH_SHORT).show();
    }
    public void onSwipeLeft() {
        //Toast.makeText(MyActivity.this, "left", Toast.LENGTH_SHORT).show();

    }
    public void onSwipeBottom() {
       // Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
    }

}



package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button flashlightBtn;
    private Flashlight flashlightClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashlightBtn = findViewById(R.id.flashlightBtn);
        Init();
    }

    public void Init() {
        flashlightClass = new Flashlight(this);
    }

    public void BtnClickFlash(View view) {
        //true вкл
        if (flashlightClass.isFlashStatus()) {
            flashlightClass.turnOffFlashlight();
            flashlightBtn.setText(R.string.onBtnStatusOn);
            flashlightBtn.setBackgroundResource(R.drawable.btn_on_flashlight);

        }
        //false выкл
        else {
            flashlightClass.turnOnFlashlight();
            flashlightBtn.setText(R.string.onBtnStatusOff);
            flashlightBtn.setBackgroundResource(R.drawable.btn_off_flashlight);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(flashlightClass.isFlashStatus()){
            flashlightClass.turnOffFlashlight();
        }
    }
}
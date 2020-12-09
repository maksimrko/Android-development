package com.example.flashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class Flashlight {
    private boolean flashStatus = false;
    private Context context;
    private CameraManager cameraManager;
    private String cameraId;

    public Flashlight(Context context) {
        this.context = context;
    }

    public void turnOnFlashlight() {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            //Если камеры нет
            assert cameraManager != null;
            //[0] потому что, камер на телефоне может быть больше, чем одна
            cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);//чтоб включить фонарик
            flashStatus = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turnOffFlashlight() {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            //Если камеры нет
            assert cameraManager != null;
            //[0] потому что, камер на телефоне может быть больше, чем одна
            cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);//чтоб включить фонарик
            flashStatus = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isFlashStatus() {
        return flashStatus;
    }
}

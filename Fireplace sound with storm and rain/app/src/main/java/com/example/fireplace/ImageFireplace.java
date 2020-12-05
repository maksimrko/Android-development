package com.example.fireplace;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ImageFireplace extends Fragment {
    private  View fireplaceView;
    private ImageView fireplaceImageView;
    static AnimationDrawable fireplaceAnimationGif;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fireplaceView = inflater.inflate(R.layout.fragment_image_fireplace, container, false);
        fireplaceImageView= fireplaceView.findViewById(R.id.fireplaceImage);
        fireplaceImageView.setBackgroundResource(R.drawable.fireplace_gif);// 
        fireplaceAnimationGif = (AnimationDrawable) fireplaceImageView.getBackground();//On animation
        fireplaceAnimationGif.setOneShot(false);// Infitity animation
        return  fireplaceView;
    }
}
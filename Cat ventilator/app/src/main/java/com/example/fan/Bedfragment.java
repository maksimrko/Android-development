package com.example.fan;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Bedfragment extends Fragment {
    ImageView img;
   static AnimationDrawable frameAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bedfragment, container, false);
        img = view.findViewById(R.id.animationView);
        img.setBackgroundResource(R.drawable.cat_fan);
        // получаем объект анимации
        frameAnimation = (AnimationDrawable) img.getBackground();
        frameAnimation.setOneShot(false);
        return view;
    }
}
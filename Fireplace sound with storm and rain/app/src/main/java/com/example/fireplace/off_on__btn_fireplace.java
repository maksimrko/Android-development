package com.example.fireplace;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class off_on__btn_fireplace extends Fragment {
    private MediaPlayer mediaPlayer;
    private View off_on_view;
    private Button offButton, onButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        off_on_view = inflater.inflate(R.layout.fragment_off_on__btn_fireplace, container, false);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.fireplace_grom);
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        offButton = off_on_view.findViewById(R.id.btn_off);
        onButton  = off_on_view.findViewById(R.id.btn_on);
        on_btn_method();
        off_btn_method();

        return off_on_view;
    }

    private void off_btn_method() {
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay();
                ImageFireplace.fireplaceAnimationGif.stop();              
            }
        });
    }

    private void on_btn_method() {
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                ImageFireplace.fireplaceAnimationGif.start();
                onButton.setBackground(getActivity().getDrawable(R.drawable.change_mode_button));
            }
        });
    }

    private void stopPlay() {
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
            onButton.setBackground(getActivity().getDrawable(R.drawable.buttons));
            ImageFireplace.fireplaceAnimationGif.stop();
        } catch (Throwable t){
            Toast.makeText(getActivity(), "Fireplace off", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            stopPlay();
        }
    }
}
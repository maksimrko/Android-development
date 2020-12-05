package com.example.fan;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Off_On_Ventilator extends Fragment {

    MediaPlayer mPlayer;
    Button off, low;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_off__on__ventilator, container, false);

        mPlayer = MediaPlayer.create(getActivity(),R.raw.funtenlow);
        mPlayer.setLooping(true);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        off = view.findViewById(R.id.btn_off);
        low = view.findViewById(R.id.btn_low);

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.start();
                Bedfragment.frameAnimation.start();
                low.setBackground(getActivity().getDrawable(R.drawable.buttonon));
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay();
                Bedfragment.frameAnimation.stop();
            }
        });
        return  view;

    }
    private void stopPlay() {
        mPlayer.stop();
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            low.setBackground(getActivity().getDrawable(R.drawable.buttononoff));//Если нажал off вернет обыкновенный цвет
            Bedfragment.frameAnimation.stop();
        } catch (Throwable t){
             Toast.makeText(getActivity(), "Ventilator off", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
    }
}
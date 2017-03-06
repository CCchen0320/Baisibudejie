package com.mylvyi.baisibudejie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylvyi.baisibudejie.R;

/**
 * Created by qf on 2016/11/2.
 */
public class VoiceFragment extends Fragment {
    View view ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view=  inflater.inflate(R.layout.voice_fragment,container,false);

        }
        return view;
    }


}

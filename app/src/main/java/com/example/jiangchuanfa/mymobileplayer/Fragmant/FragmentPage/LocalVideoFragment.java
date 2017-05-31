package com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.jiangchuanfa.mymobileplayer.Fragmant.BaseFragment;

/**
 * Created by crest on 2017/5/31.
 */

public class LocalVideoFragment extends BaseFragment{
    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextColor(Color.GREEN);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("本地视频频");
    }
}

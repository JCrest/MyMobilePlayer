package com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.jiangchuanfa.mymobileplayer.Fragmant.BaseFragment;

/**
 * Created by crest on 2017/5/31.
 */

public class LocalAudioFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {
        Log.e("TAG", "本地音频ui初始化了。。");
        textView = new TextView(mContext);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "本地音频数据初始化了。。");
        textView.setText("本地音频");
    }


}

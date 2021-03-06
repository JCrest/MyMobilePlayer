package com.example.jiangchuanfa.mymobileplayer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jiangchuanfa.mymobileplayer.Fragmant.BaseFragment;
import com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage.LocalAudioFragment;
import com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage.LocalVideoFragment;
import com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage.NetAudioFragment;
import com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage.NetVideoFragment;
import com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage.RecyclerViewFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    private ArrayList<BaseFragment> fragments;

    private int position;

    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        initFragment();
        initListenter();
    }

    private void initListenter() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_local_video:
                        position = 0;
                        break;
                    case R.id.rb_local_audio:
                        position = 1;
                        break;
                    case R.id.rb_net_audio:
                        position = 2;
                        break;
                    case R.id.rb_net_video:
                        position = 3;
                        break;
                    case R.id.rb_recyclerview:
                        position = 4;
                        break;
                }

                Fragment currentFragment = fragments.get(position);
                switchFragment(currentFragment);
            }
        });
        rg_main.check(R.id.rb_local_video);

    }

    private void switchFragment(Fragment currentFragment) {
        if (tempFragment != currentFragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (currentFragment != null) {
                if (!currentFragment.isAdded()) {
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    ft.add(R.id.fl_mainc_content, currentFragment);

                } else {
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    ft.show(currentFragment);
                }
                ft.commit();
            }
            tempFragment = currentFragment;
        }
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LocalVideoFragment());
        fragments.add(new LocalAudioFragment());
        fragments.add(new NetAudioFragment());
        fragments.add(new NetVideoFragment());
        fragments.add(new RecyclerViewFragment());
    }

    private boolean isExit = false;



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK){
            if(position!= 0){
                rg_main.check(R.id.rb_local_video);
                return true;
            }else if(!isExit){
                Toast.makeText(MainActivity.this, "再按一次退出软件", Toast.LENGTH_SHORT).show();
                isExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

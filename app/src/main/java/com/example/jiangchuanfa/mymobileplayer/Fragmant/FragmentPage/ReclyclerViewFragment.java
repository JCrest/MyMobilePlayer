package com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jiangchuanfa.mymobileplayer.Fragmant.BaseFragment;
import com.example.jiangchuanfa.mymobileplayer.R;

import butterknife.ButterKnife;


/**
 * Created by crest on 2017/5/31.
 *
 *
 */

public class ReclyclerViewFragment extends BaseFragment {
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;
    @InjectView(R.id.tv_nomedia)
    TextView tvNomedia;
    /**
     * 数据集合
     */
    private List<NetAudioBean.ListBean> listDatas;
    private RecyclerFragmentAdapter myAdapter;

    @Override
    public View initView() {
        Log.e("TAG", "网络音频ui初始化了。。");
        View view = View.inflate(mContext, R.layout.fragment_recyclerview, null);
        ButterKnife.inject(this, view);


        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "网络音频数据初始化了。。");
//        tvNomedia.setVisibility(View.VISIBLE);
//        tvNomedia.setText("hh");
        getDataFromNet();
    }


    @Override
    public void onRefrshData() {
        super.onRefrshData();
//        Log.e("TAG","onHiddenChanged。。"+this.toString());

    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constant.NET_AUDIO);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.e("TAG", "网络音乐请求成功" + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "网络音乐请求失败" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {
        NetAudioBean netAudioBean = new Gson().fromJson(json, NetAudioBean.class);
        listDatas = netAudioBean.getList();


        Log.e("TAG","解决成功=="+listDatas.get(0).getText());
        if(listDatas != null && listDatas.size() >0){
            //有视频
            tvNomedia.setVisibility(View.GONE);
            //设置适配器
            myAdapter = new RecyclerFragmentAdapter(mContext,listDatas);
            recyclerview.setAdapter(myAdapter);

            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        }else{
            //没有视频
            tvNomedia.setVisibility(View.VISIBLE);
        }

        progressbar.setVisibility(View.GONE);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

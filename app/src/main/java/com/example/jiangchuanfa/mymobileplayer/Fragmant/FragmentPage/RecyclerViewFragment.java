package com.example.jiangchuanfa.mymobileplayer.Fragmant.FragmentPage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.jiangchuanfa.mymobileplayer.Adapter.RecyclerFragmentAdapter;
import com.example.jiangchuanfa.mymobileplayer.Bean.NetAudioBean;
import com.example.jiangchuanfa.mymobileplayer.Fragmant.BaseFragment;
import com.example.jiangchuanfa.mymobileplayer.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;


/**
 * Created by crest on 2017/5/31.
 *
 *
 */

public class RecyclerViewFragment extends BaseFragment {
    //private static final String TAG = RecyclerViewFragment.class.getSimpleName();
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.refresh)
    MaterialRefreshLayout refresh;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.tv_nomedia)
    TextView tvNomedia;
//    @Bind(R.id.)
//    ListView listview;
//    @Bind(R.id.progressbar)
//    ProgressBar progressbar;
//    @Bind(R.id.tv_nomedia)
//    TextView tvNomedia;

    private List<NetAudioBean.ListBean> listDatas;
    private RecyclerFragmentAdapter myAdapter;
    private MaterialRefreshLayout materialRefreshLayout;
    private boolean isLoadMore = false;

    @Override
    public View initView() {
        Log.e(TAG, "网络音频UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_recyclerview, null);
        ButterKnife.bind(this, view);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                isLoadMore = false;
                getDataFromNet();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                isLoadMore = true;
                getMoreData();
            }
        });
        return view;
    }

    private void getMoreData() {
        RequestParams params = new RequestParams("http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.2.8/0-20.json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.e("TAG", "网络音乐请求成功" + result);
                processData(result);
                materialRefreshLayout.finishRefreshLoadMore();
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

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "网络音频数据初始化了");
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams("http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.2.8/0-20.json?market=baidu&udid=863425026599592&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=98%3A6c%3Af5%3A4b%3A72%3A6d&ver=6.2.8");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.e("TAG", "网络音乐请求成功" + result);
                processData(result);
                Toast.makeText(mContext, "刷新成功", Toast.LENGTH_SHORT).show();
                materialRefreshLayout.finishRefresh();
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


        if (!isLoadMore) {
            listDatas = netAudioBean.getList();
            Log.e("TAG", "解决成功==" + listDatas.get(0).getText());
            if (listDatas != null && listDatas.size() > 0) {
                //有视频
                tvNomedia.setVisibility(View.GONE);
                //设置适配器
                myAdapter = new RecyclerFragmentAdapter(mContext, listDatas);
                recyclerview.setAdapter(myAdapter);
                recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            } else {
                //没有视频
                tvNomedia.setVisibility(View.VISIBLE);
            }


        } else {
            List<NetAudioBean.ListBean> NewList = netAudioBean.getList();
            listDatas.addAll(NewList);
            myAdapter.notifyDataSetChanged();
        }

        progressbar.setVisibility(View.GONE);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}

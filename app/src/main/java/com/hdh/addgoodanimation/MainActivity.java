package com.hdh.addgoodanimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author huangdianhua
 * @data 2016.8.1 11.05
 */
public class MainActivity extends AppCompatActivity implements RecycleAdapter.AddGoodAnimCallBack {
    private RecyclerView recycler;
    private TextView text_chart_num;
    private ImageView buyImg;//这是在界面上跑的小圆点
    private ViewGroup anim_mask_layout;//动画层
    private ImageView image_chart;//购物车

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        image_chart = (ImageView) findViewById(R.id.image_chart);
        text_chart_num = (TextView) findViewById(R.id.text_chart_num);

        text_chart_num.setText(0+"");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        RecycleAdapter recycleAdapter = new RecycleAdapter(this, setGoodList());
        recycler.setAdapter(recycleAdapter);
    }

    private List<Good> setGoodList() {
        List<Good> list = new ArrayList<>();
        Good good1 = new Good("水果1", BitMapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.t1, 100, 100), 28.00);
        Good good2 = new Good("水果2", BitMapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.t2, 100, 100), 13.00);
        Good good3 = new Good("水果3", BitMapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.t3, 100, 100), 16.00);
        Good good4 = new Good("水果4", BitMapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.t4, 100, 100), 41.00);
        Good good5 = new Good("水果5", BitMapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.t5, 100, 100), 28.00);
        Good good6 = new Good("水果6", BitMapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.t6, 100, 100), 13.00);
        Good good7 = new Good("水果7", BitMapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.t7, 100, 100), 15.00);
        Good good8 = new Good("水果8", BitMapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.t8, 100, 100), 19.00);
        list.add(good1);
        list.add(good2);
        list.add(good3);
        list.add(good4);
        list.add(good5);
        list.add(good6);
        list.add(good7);
        list.add(good8);
        return list;
    }

    @Override
    public void setGoodNum(final int goodNum,int time) {
        text_chart_num.postDelayed(new Runnable() {
            @Override
            public void run() {
                text_chart_num.setText(goodNum + "");
            }
        }, time);
    }

    @Override
    public void setAnim(View view) {
        // TODO Auto-generated method stub
        int[] start_location = new int[2];// 一个整型数组用来存储按钮在屏幕的X,Y坐标
        view.getLocationInWindow(start_location);// 购买按钮在屏幕中的坐标
        buyImg = new ImageView(this);// 动画的小圆圈
        buyImg.setImageResource(R.drawable.sign);// 设置buyImg的图片
        setAnim(buyImg, start_location);
    }

    /**
     * hdh: 创建动画层
     *
     * @return
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();// 获得Window界面的最顶层
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        //animLayout.setId();
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * hdh:
     *
     * @param vp
     * @param view
     * @param location
     * @return
     */
    private View addViewToAnimLayout(final ViewGroup vp, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * hdh:动画
     *
     * @param v
     * @param start_location
     */
    private void setAnim(final View v, int[] start_location) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);
        View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
        int[] end_location = new int[2];// 存储动画结束位置的X,Y坐标
        text_chart_num.getLocationInWindow(end_location);// 将购物车的位置存储起来
        // 计算位移
        int endX = end_location[0] - start_location[0];// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());// 设置此动画的加速曲线。默认为一个线性插值。
        translateAnimationX.setRepeatCount(0);// 动画重复的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationX);
        set.addAnimation(translateAnimationY);
        set.setDuration(1000);
        view.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                v.setVisibility(View.GONE);
            }
        });
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "scale", 1.0F, 1.5F, 1.0f)//
                .setDuration(500);//
        anim.setStartDelay(1000);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                image_chart.setScaleX(cVal);
                image_chart.setScaleY(cVal);
                text_chart_num.setScaleX(cVal);
                text_chart_num.setScaleY(cVal);
            }
        });
    }
}

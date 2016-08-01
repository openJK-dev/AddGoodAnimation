package com.hdh.addgoodanimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * RecycleView适配器
 * Created by huangdianhua on 2016/8/1 11:10.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private Context context;
    private List<Good> goodList;//存储商品类
    private int[] goodNumArr = null;//存储每个商品选中的数量
    private AddGoodAnimCallBack addGoodAnimCallBack;//动画回调接口

    public interface AddGoodAnimCallBack {
        /**
         * 设置添加商品的动画
         *
         * @param view
         */
        void setAnim(View view);

        /**
         * 设置添加商品后文本框的值
         *
         * @param count 选中商品的总数量
         * @param time  设置数量显示的延迟时间
         */
        void setGoodNum(int count, int time);
    }

    public RecycleAdapter(Context context, List<Good> goodList) {
        this.context = context;
        this.goodList = goodList;
        this.addGoodAnimCallBack = (AddGoodAnimCallBack) context;
        goodNumArr = new int[goodList.size()];
    }

    @Override
    public int getItemCount() {
        if (goodList != null) {
            return goodList.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.iv_mv_image.setImageBitmap(goodList.get(position).getBitmap());
        holder.text_name.setText(goodList.get(position).getName());
        holder.text_price.setText("￥" + goodList.get(position).getPrice());
        holder.image_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodNumArr[position] > 0) {
                    goodNumArr[position]--;
                }
                holder.text_chart_num.setText(goodNumArr[position] + "");
                addGoodAnimCallBack.setGoodNum(getGoodNum(), 0);
            }
        });
        holder.image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodNumArr[position]++;
                holder.text_chart_num.setText(goodNumArr[position] + "");
                addGoodAnimCallBack.setAnim(v);
                addGoodAnimCallBack.setGoodNum(getGoodNum(), 1000);
            }
        });
    }

    /**
     * 获得选中商品的总数量
     *
     * @return
     */
    private int getGoodNum() {
        int countGood = 0;
        for (int i = 0; i < goodNumArr.length; i++) {
            countGood += goodNumArr[i];
        }
        return countGood;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_good, parent, false));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_mv_image;
        private TextView text_name;
        private TextView text_price;
        private ImageView image_subtract;
        private TextView text_chart_num;
        private ImageView image_add;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_mv_image = (ImageView) itemView.findViewById(R.id.iv_mv_image);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_price = (TextView) itemView.findViewById(R.id.text_price);
            image_subtract = (ImageView) itemView.findViewById(R.id.image_subtract);
            text_chart_num = (TextView) itemView.findViewById(R.id.text_chart_num);
            image_add = (ImageView) itemView.findViewById(R.id.image_add);
        }
    }
}

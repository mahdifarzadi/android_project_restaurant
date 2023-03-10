package com.example.fooddeliveryrestaurant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryrestaurant.R;
import com.example.fooddeliveryrestaurant.ShowFood;
import com.example.fooddeliveryrestaurant.model.RestaurantFood;

import java.util.ArrayList;
import java.util.List;

public class ShowFoodAdapter extends RecyclerView.Adapter<ShowFoodAdapter.viewHolder> {

    private Context mContext;
    private List<RestaurantFood> mRestaurantFoodList = new ArrayList<>();
    private String restName;
    private String userName;

    public ShowFoodAdapter(Context mContext, List<RestaurantFood> mRestaurantFoodList, String restName, String userName) {
        this.mContext = mContext;
        this.mRestaurantFoodList = mRestaurantFoodList;
        this.restName = restName;
        this.userName = userName;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rest_food_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RestaurantFood food = mRestaurantFoodList.get(position);
        holder.tv_foodDesc.setText(food.getFoodDesc());
        holder.tv_foodName.setText(food.getFoodName());
        holder.tv_foodPrice.setText(food.getFoodPrice());
        Glide.with(mContext).load(food.getFoodImage()).into(holder.iv_foodImage);

        holder.ll_foodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowFood.class);
                intent.putExtra("restName", restName);
                intent.putExtra("foodImage", food.getFoodImage());
                intent.putExtra("foodPrice", food.getFoodPrice());
                intent.putExtra("foodDesc", food.getFoodDesc());
                intent.putExtra("foodName", food.getFoodName());
                intent.putExtra("userName", userName);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurantFoodList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_foodImage;
        private TextView tv_foodName;
        private TextView tv_foodPrice;
        private TextView tv_foodDesc;
        private LinearLayout ll_foodLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            iv_foodImage = itemView.findViewById(R.id.iv_foodImage);
            tv_foodName = itemView.findViewById(R.id.tv_foodName);
            tv_foodPrice = itemView.findViewById(R.id.tv_foodPrice);
            tv_foodDesc = itemView.findViewById(R.id.tv_foodDesc);

            ll_foodLayout = itemView.findViewById(R.id.ll_foodLayout);

        }
    }

}

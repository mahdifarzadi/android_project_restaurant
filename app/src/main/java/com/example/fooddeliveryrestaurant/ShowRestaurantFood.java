package com.example.fooddeliveryrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryrestaurant.adapter.ShowFoodAdapter;
import com.example.fooddeliveryrestaurant.model.RestaurantFood;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowRestaurantFood extends AppCompatActivity {

    public static final String RESTAURANT_USERS = "RestaurantsUser";
    public static final String RESTAURANT_FOOD = "restaurantfood";
    private static final int REQUEST_LOCATION = 100;
    public static final String LOCATION_RESTAURANTS = "RestaurantLocation";

    private RecyclerView rv_showAllFood;
    private ShowFoodAdapter adapter;
    private List<RestaurantFood> mList = new ArrayList<>();

    private String restName = "";
    private String userName = "";

    ImageView iv_cart;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurant_food);

        rv_showAllFood = findViewById(R.id.rv_showAllFood);
        rv_showAllFood.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowRestaurantFood.this);
        linearLayoutManager.setStackFromEnd(true);
        rv_showAllFood.setLayoutManager(linearLayoutManager);

        iv_cart = findViewById(R.id.iv_cart);

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowRestaurantFood.this, CustomerCart.class));
            }
        });

        intent = getIntent();

        restName = intent.getStringExtra("restName");
        userName = intent.getStringExtra("userName");

        // getting restaurant food from firebase
        getAllFood(restName);

    }

    private void getAllFood(String restName) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(RESTAURANT_FOOD).child(restName);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        RestaurantFood restaurantFood = dataSnapshot.getValue(RestaurantFood.class);
                        mList.add(restaurantFood);
                    }
                    adapter = new ShowFoodAdapter(ShowRestaurantFood.this, mList, restName, userName);
                    rv_showAllFood.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

}
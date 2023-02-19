package com.sanioluke00.plantrium_beaplanter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    TextView dash_logoutbtn, dash_username, dash_emailid;
    LinearLayout dash_mainlay;
    RecyclerView frn_flowers_rec;
    Functions functions= new Functions();
    FlowerDisplayAdapter flowerDisplayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dash_username= findViewById(R.id.dash_username);
        dash_emailid= findViewById(R.id.dash_emailid);
        frn_flowers_rec= findViewById(R.id.frn_flowers_rec);
        dash_logoutbtn= findViewById(R.id.dash_logoutbtn);
        dash_mainlay= findViewById(R.id.dash_mainlay);

        dash_username.setText(functions.getSharedPrefsValue(getApplicationContext(), "account_data","user_fullname","string","Not Available"));
        dash_emailid.setText(functions.getSharedPrefsValue(getApplicationContext(), "account_data","user_emailid","string","Not Available"));

        flowerDisplayAdapter= new FlowerDisplayAdapter(getApplicationContext(), flowerArrayList());
        frn_flowers_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        frn_flowers_rec.setAdapter(flowerDisplayAdapter);
        flowerDisplayAdapter.notifyDataSetChanged();

        dash_logoutbtn.setOnClickListener(v->{
            Snackbar.make(dash_mainlay,"Logout Successful.. Please come back again soon...", Snackbar.LENGTH_SHORT).show();
            functions.clearSharedPreferences(getApplicationContext(),"account_data");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getApplicationContext(), LoginPageActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            },1500);
        });

    }

    @NonNull
    private ArrayList<FlowerDataModel> flowerArrayList(){
        ArrayList<FlowerDataModel> flowerData_arr= new ArrayList<>();
        flowerData_arr.add(new FlowerDataModel("Ginger Flower", "Etlingera elatior",R.drawable.plant_1));
        flowerData_arr.add(new FlowerDataModel("Orange Lily", "Lilium bulbiferum",R.drawable.plant_2));
        flowerData_arr.add(new FlowerDataModel("Birds of Paradise", "Strelitzia",R.drawable.plant_4));
        flowerData_arr.add(new FlowerDataModel("Hyacinth", "Hyacinthus",R.drawable.plant_5));
        flowerData_arr.add(new FlowerDataModel("Flamingo Lily", "Anthurium andraeanum",R.drawable.plant_3));
        flowerData_arr.add(new FlowerDataModel("Calla Lily", "Zantedeschia",R.drawable.plant_6));
        flowerData_arr.add(new FlowerDataModel("Lilacs", "Syringa",R.drawable.plant_7));
        flowerData_arr.add(new FlowerDataModel("Frangipani", "Plumeria",R.drawable.plant_8));
        flowerData_arr.add(new FlowerDataModel("Water Parsley", "Oenanthe pimpinelloides",R.drawable.plant_9));
        flowerData_arr.add(new FlowerDataModel("Chicory", "Cichorium intybus",R.drawable.plant_10));
        return flowerData_arr;
    }
}
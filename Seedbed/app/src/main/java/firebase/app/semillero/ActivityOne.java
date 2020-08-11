package firebase.app.semillero;



import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivityOne extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    BottomNavigationView bottomNavigationView;
    private static final String TAG =ActivityOne.class.getSimpleName() ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        final TextView textViewTemperature = (TextView) findViewById(R.id.temperatureNumber);
        final TextView textViewAir = (TextView) findViewById(R.id.humidityAirNumber);
        final TextView textViewGround = (TextView) findViewById(R.id.humidityGroundNumber);
        final TextView textViewLDR = (TextView) findViewById(R.id.ldrNumber);
        final TextView textViewCo2 = (TextView) findViewById(R.id.co2Number);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.topAppBar);
        DatabaseReference myRef = database.getReference();
        LinearLayoutCompat linearLayout=(LinearLayoutCompat) findViewById(R.id.layoutHistorical);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        setSupportActionBar(mToolbar);

 linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ActivityOne.this, Historical.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
});
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:

                        break;

                    case R.id.nav_historical:
                        Intent intent1 = new Intent(ActivityOne.this, ActivityTwo.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.nav_profile:
                        Intent intent2 = new Intent(ActivityOne.this, ActivityThree.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.nav_actuators:
                        Intent intent3 = new Intent(ActivityOne.this, ActivityFour.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.nav_LogOut:

                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(ActivityOne.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                }


                return false;
            }
        });


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                SENSORS newSENSORS = dataSnapshot.getValue(SENSORS.class);
                textViewTemperature.setText(newSENSORS.getTemperature() + " ºC");
                textViewGround.setText(newSENSORS.getHumidityGround() + " %");
                textViewAir.setText(newSENSORS.getHumidityAir() + " %");
                textViewCo2.setText(newSENSORS.getCO2() + " ppm");
                textViewLDR.setText(newSENSORS.getLDR() + " lux");
            };
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;

    }




}


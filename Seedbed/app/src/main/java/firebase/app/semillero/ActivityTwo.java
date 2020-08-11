package firebase.app.semillero;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class ActivityTwo extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final String TAG =ActivityTwo.class.getSimpleName() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        final DatabaseReference myRef = database.getReference();
        final ImageView pumpCircle= findViewById(R.id.pumpCircle);
        final ImageView lightCircle =findViewById(R.id.lightCircle);
        final ImageView humidifierCircle= findViewById(R.id.humidifierCircle);
        final ImageView ventilatorCircle= findViewById(R.id.ventilatorCircle);
        final ImageView gasAlert= findViewById(R.id.alertCircle);
        final ImageView waterLevel= findViewById(R.id.waterCircle);
        final Switch pumpSwitcher=findViewById(R.id.pumpSwitch);
        final Switch lightSwitcher=findViewById(R.id.lightsSwitch);
        final Switch ventilatorSwitcher=findViewById(R.id.ventilatorSwitch);
        final Switch humidifierSwitcher=findViewById(R.id.humidifierSwitch);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        pumpSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (pumpSwitcher.isChecked()){
                    myRef.child("actuators").child("pump").setValue("H")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(ActivityTwo.this, "Pump activated", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(ActivityTwo.this, "Failed Activation", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });

                }else{
                    myRef.child("actuators").child("pump").setValue("L")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(ActivityTwo.this, "Pump deactivated", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(ActivityTwo.this, "Failed Activation", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                }

            }
        });

        ventilatorSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ventilatorSwitcher.isChecked()){
                    myRef.child("actuators").child("ventilator").setValue("H")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(ActivityTwo.this, "Ventilator activated", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(ActivityTwo.this, "Failed Activation", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });

                }else{
                    myRef.child("actuators").child("ventilator").setValue("L")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(ActivityTwo.this, "Ventilator deactivated", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(ActivityTwo.this, "Failed Activation", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                }

            }
        });

        lightSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (lightSwitcher.isChecked()){
                    myRef.child("actuators").child("lights").setValue("H")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(ActivityTwo.this, "Light activated", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(ActivityTwo.this, "Failed Activation", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });

                }else{
                    myRef.child("actuators").child("lights").setValue("L")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(ActivityTwo.this, "Light deactivated", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(ActivityTwo.this, "Failed Activation", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                }

            }
        });

        humidifierSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (humidifierSwitcher.isChecked()){
                    myRef.child("actuators").child("humidifier").setValue("H")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(ActivityTwo.this, "Humidifier activated", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(ActivityTwo.this, "Failed Activation", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });

                }else{
                    myRef.child("actuators").child("humidifier").setValue("L")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    Toast.makeText(ActivityTwo.this, "Humidifier deactivated", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    Toast.makeText(ActivityTwo.this, "Failed Activation", Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            });
                }

            }
        });


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                SENSORS newSENSORS = dataSnapshot.getValue(SENSORS.class);

                if (newSENSORS.getPumpCurrentState().equals("H")){
                    pumpCircle.setColorFilter(Color.GREEN);
            }else{
                    pumpCircle.setColorFilter(Color.RED);
                }
                if (newSENSORS.getLightCurrentState().equals("H")){
                   lightCircle.setColorFilter(Color.GREEN);
                }else{
                   lightCircle.setColorFilter(Color.RED);

                }

                if (newSENSORS.getVentilatorCurrentState().equals("H")){
                    ventilatorCircle.setColorFilter(Color.GREEN);
                }else{
                    ventilatorCircle.setColorFilter(Color.RED);
                }
                if (newSENSORS.getHumidifierCurrentState().equals("H")){
                    humidifierCircle.setColorFilter(Color.GREEN);
                }else{
                    humidifierCircle.setColorFilter(Color.RED);
                }
                if (newSENSORS.getGasAlert().equals("H")){
                    gasAlert.setColorFilter(Color.YELLOW);
                }else{
                    gasAlert.setColorFilter(Color.GRAY);
                }
                if (newSENSORS.getWaterLevel().equals("L")){
                    waterLevel.setColorFilter(Color.RED);
                }else{
                    waterLevel.setColorFilter(Color.BLUE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });





        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent(ActivityTwo.this, ActivityOne.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.nav_historical:

                        break;

                    case R.id.nav_profile:
                        Intent intent2 = new Intent(ActivityTwo.this, ActivityThree.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.nav_actuators:
                        Intent intent4 = new Intent(ActivityTwo.this, ActivityFour.class);
                        startActivity(intent4);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;


                    case R.id.nav_LogOut:
                        Intent intent3 = new Intent(ActivityTwo.this, MainActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

    }


                return false;
}
        });
    }}


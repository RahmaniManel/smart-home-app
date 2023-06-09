package com.example.smarthomeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.example.smarthomeapplication.fragment.Bathroom.FragmentBathroom;
import com.example.smarthomeapplication.fragment.Kitchen.FragmentKitchen;
import com.example.smarthomeapplication.fragment.LivingRoom.FragmentLivingRoom;
import com.example.smarthomeapplication.fragment.bedroom.FragmentBedroom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SummaryActivity extends AppCompatActivity  implements View.OnClickListener {

    private ImageView mEditBedroom, mEditLivingRoom, mEditKitchen, mEditBathroom, profile;
    private DatabaseReference mRefBedroom, mRefBathroom, mRefLivingRoom, mRefKitchen, mRefHouseLight;
    private DatabaseReference mRefCurrentTempBedroom, mRefACBedroom, mRefHeaterBedroom;
    private DatabaseReference mRefCurrentTempBathroom, mRefACBathroom, mRefHeaterBathroom;
    private DatabaseReference mRefCurrentTempLivingRoom, mRefACLivingRoom, mRefHeaterLivingRoom;
    private DatabaseReference mRefCurrentTempKitchen, mRefACKitchen, mRefHeaterKitchen;
    private DatabaseReference mRefLightKitchen, mRefWindowsKitchen, mRefDoorsKitchen, mRefShadesKitchen;
    private DatabaseReference mRefLightBedroom, mRefWindowsBedroom, mRefDoorsBedroom, mRefShadesBedroom;
    private DatabaseReference mRefLightBathroom, mRefWindowsBathroom, mRefDoorsBathroom, mRefShadesBathroom;
    private DatabaseReference mRefLightLivingRoom, mRefWindowsLivingRoom, mRefDoorsLivingRoom, mRefShadesLivingRoom;
    private DatabaseReference mRefLampBedroom, mRefAirPurifier, mRefBedAudio;
    private DatabaseReference mRefDryer, mRefWasher, mRefDiffuser;
    private DatabaseReference mRefTV, mRefVacuum, mRefLampLivingRoom;
    private DatabaseReference mRefMicrowave, mRefFridge, mRefDishwasher;
    private FirebaseDatabase db;
    private Boolean isOpened;
    private TextView mCurrentTempBed, mShadesLevelBed;
    private TextView mCurrentTempBath, mShadesLevelBath;
    private TextView mCurrentTempLiv, mShadesLevelLiv;
    private TextView mCurrentTempKit, mShadesLevelKit;

    private Switch mACBed, mHeaterBed, mLightBed, mDoorsBed, mWindowsBed;
    private Switch mLampBed, mAirPurifier, mBedAudio;

    private Switch mACBath, mHeaterBath, mLightBath, mDoorsBath, mWindowsBath;
    private Switch mDryer, mWasher, mDiffuser;

    private Switch mACLiv, mHeaterLiv, mLightLiv, mDoorsLiv, mWindowsLiv;
    private Switch mLampLiv, mVacuum, mTV;

    private Switch mACKit, mHeaterKit, mLightKit, mDoorsKit, mWindowsKit;
    private Switch mMicrowave, mFridge, mDishwasher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        initView();
    }


    private void initView() {

        // DB instance
        db = FirebaseDatabase.getInstance();
        mRefHouseLight = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Light");

        mRefBathroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom");
        mRefBedroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom");
        mRefLivingRoom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom");
        mRefKitchen = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen");

        // Heating
        mRefCurrentTempBathroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("HeatingIns").child("Temperature");
        mRefACBathroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("HeatingIns").child("AC");
        mRefHeaterBathroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("HeatingIns").child("Heater");
        mRefCurrentTempBedroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("HeatingIns").child("Temperature");
        mRefACBedroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("HeatingIns").child("AC");
        mRefHeaterBedroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("HeatingIns").child("Heater");
        mRefCurrentTempKitchen = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("HeatingIns").child("Temperature");
        mRefACKitchen = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("HeatingIns").child("AC");
        mRefHeaterKitchen = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("HeatingIns").child("Heater");
        mRefCurrentTempLivingRoom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("HeatingIns").child("Temperature");
        mRefACLivingRoom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("HeatingIns").child("AC");
        mRefHeaterLivingRoom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("HeatingIns").child("Heater");

        // Light
        mRefLightBathroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("LightIns").child("Light");
        mRefLightBedroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("LightIns").child("Light");
        mRefLightKitchen = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("LightIns").child("Light");
        mRefLightLivingRoom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("LightIns").child("Light");

        // Plugins
        mRefDryer = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("PlugIns").child("Dryer");
        mRefWasher = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("PlugIns").child("Washer");
        mRefDiffuser = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("PlugIns").child("Diffuser");
        mRefLampBedroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("PlugIns").child("Lamp");
        mRefAirPurifier = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("PlugIns").child("AirPurifier");
        mRefBedAudio = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("PlugIns").child("BedAudio");
        mRefMicrowave = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("PlugIns").child("Microwave");
        mRefFridge = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("PlugIns").child("Fridge");
        mRefDishwasher = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("PlugIns").child("Dishwasher");
        mRefTV = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("PlugIns").child("TV");
        mRefVacuum = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("PlugIns").child("Vacuum");
        mRefLampLivingRoom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("PlugIns").child("Lamp");

        //windows and doors
        mRefWindowsBathroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("WinDoorIns").child("Window");
        mRefDoorsBathroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("WinDoorIns").child("Door");
        mRefWindowsBedroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("WinDoorIns").child("Window");
        mRefDoorsBedroom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bedroom")
                .child("WinDoorIns").child("Door");
        mRefWindowsKitchen = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("WinDoorIns").child("Window");
        mRefDoorsKitchen = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitchen")
                .child("WinDoorIns").child("Door");
        mRefWindowsLivingRoom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("WinDoorIns").child("Window");
        mRefDoorsLivingRoom = db.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("WinDoorIns").child("Door");





        mEditBathroom = (ImageView) findViewById(R.id.EditBathroom);
        mEditLivingRoom = (ImageView) findViewById(R.id.EditLivingroom);
        mEditKitchen = (ImageView) findViewById(R.id.EditKitchen);
        mEditBedroom = (ImageView) findViewById(R.id.EditBedroom);
        profile = (ImageView) findViewById(R.id.profile);


        //light
        mLightBath = (Switch) findViewById(R.id.LightState_bathroom);
        mLightBed = (Switch) findViewById(R.id.LightState_bedroom);
        mLightKit = (Switch) findViewById(R.id.LightState_kitchen);
        mLightLiv = (Switch) findViewById(R.id.LightState_livingroom);

        // Windows
        mWindowsBath = (Switch) findViewById(R.id.WindowsState_bathroom);
        mWindowsBed = (Switch) findViewById(R.id.WindowsState_bedroom);
        mWindowsKit = (Switch) findViewById(R.id.WindowsState_kitchen);
        mWindowsLiv = (Switch) findViewById(R.id.WindowsState_livingroom);

        // Doors
        mDoorsBath = (Switch) findViewById(R.id.DoorsState_bathroom);
        mDoorsBed = (Switch) findViewById(R.id.DoorsState_bedroom);
        mDoorsKit = (Switch) findViewById(R.id.DoorsState_kitchen);
        mDoorsLiv = (Switch) findViewById(R.id.DoorsState_livingroom);

        // Heater
        mHeaterBath = (Switch) findViewById(R.id.HeaterState_bathroom);
        mHeaterBed = (Switch) findViewById(R.id.HeaterState_bedroom);
        mHeaterKit = (Switch) findViewById(R.id.HeaterState_kitchen);
        mHeaterLiv = (Switch) findViewById(R.id.HeaterState_livingroom);

        // AC
        mACBath = (Switch) findViewById(R.id.ACState_bathroom);
        mACBed = (Switch) findViewById(R.id.ACState_bedroom);
        mACKit = (Switch) findViewById(R.id.ACState_kitchen);
        mACLiv = (Switch) findViewById(R.id.ACState_livingroom);

        // Plugins Bedroom
        mLampBed = (Switch) findViewById(R.id.LampState_bedroom);
        mAirPurifier = (Switch) findViewById(R.id.AirPurifierState_bedroom);
        // Plugins Bathroom
        mDryer = (Switch) findViewById(R.id.DryerState_bathroom);
        mWasher = (Switch) findViewById(R.id.washerState_bathroom);
        // Plugins Living room
        mTV = (Switch) findViewById(R.id.TVState_livingroom);
        mVacuum = (Switch) findViewById(R.id.vacuumState_livingroom);
        mLampLiv = (Switch) findViewById(R.id.LampState_livingroom);
        // Plugins Kitchen
        mMicrowave = (Switch) findViewById(R.id.MicrowaveState);
        mFridge = (Switch) findViewById(R.id.Fridge);
        mDishwasher = (Switch) findViewById(R.id.DishwasherState);


        mEditBedroom.setOnClickListener(this);
        mEditLivingRoom.setOnClickListener(this);
        mEditKitchen.setOnClickListener(this);
        mEditBathroom.setOnClickListener(this);



        mRefDoorsBathroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mDoorsBath.setChecked(true);
                } else {
                    mDoorsBath.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefHeaterBathroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mHeaterBath.setChecked(true);
                } else {
                    mHeaterBath.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefACBathroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mACBath.setChecked(true);
                } else {
                    mACBath.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefDryer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mDryer.setChecked(true);
                } else {
                    mDryer.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefWasher.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mWasher.setChecked(true);
                } else {
                    mWasher.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefDiffuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mDiffuser.setChecked(true);
                } else {
                    mDiffuser.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefWindowsBathroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mWindowsBath.setChecked(true);
                } else {
                    mWindowsBath.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefCurrentTempBathroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (!user.toString().isEmpty()) {
                    int currentTemp = user.intValue();
                    mCurrentTempBath.setText("" + currentTemp + " C");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }
        });

        // Set state from Bedroom
        mRefLightBedroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mLightBed.setChecked(true);

                } else {
                    mLightBed.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefDoorsBedroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mDoorsBed.setChecked(true);
                } else {
                    mDoorsBed.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefHeaterBedroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mHeaterBed.setChecked(true);
                } else {
                    mHeaterBed.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefACBedroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mACBed.setChecked(true);
                } else {
                    mACBed.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefLampBedroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mLampBed.setChecked(true);
                } else {
                    mLampBed.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefAirPurifier.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mAirPurifier.setChecked(true);
                } else {
                    mAirPurifier.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefBedAudio.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mBedAudio.setChecked(true);
                } else {
                    mBedAudio.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefWindowsBedroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mWindowsBed.setChecked(true);
                } else {
                    mWindowsBed.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefCurrentTempBedroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (!user.toString().isEmpty()) {
                    int currentTemp = user.intValue();
                    mCurrentTempBed.setText("" + currentTemp + " C");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }
        });

        // Set state from Kitchen
        mRefLightKitchen.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mLightKit.setChecked(true);

                } else {
                    mLightKit.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefDoorsKitchen.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mDoorsKit.setChecked(true);
                } else {
                    mDoorsKit.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefHeaterKitchen.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mHeaterKit.setChecked(true);
                } else {
                    mHeaterKit.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefACKitchen.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mACKit.setChecked(true);
                } else {
                    mACKit.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefMicrowave.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mMicrowave.setChecked(true);
                } else {
                    mMicrowave.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefFridge.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mFridge.setChecked(true);
                } else {
                    mFridge.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefDishwasher.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mDishwasher.setChecked(true);
                } else {
                    mDishwasher.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefWindowsKitchen.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mWindowsKit.setChecked(true);
                } else {
                    mWindowsKit.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefCurrentTempKitchen.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (!user.toString().isEmpty()) {
                    int currentTemp = user.intValue();
                    mCurrentTempKit.setText("" + currentTemp + " C");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }
        });

        // Set state from Living room
        mRefLightLivingRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mLightLiv.setChecked(true);

                } else {
                    mLightLiv.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefDoorsLivingRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mDoorsLiv.setChecked(true);
                } else {
                    mDoorsLiv.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefHeaterLivingRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mHeaterLiv.setChecked(true);
                } else {
                    mHeaterLiv.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefACLivingRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mACLiv.setChecked(true);
                } else {
                    mACLiv.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefLampLivingRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mLampLiv.setChecked(true);
                } else {
                    mLampLiv.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefVacuum.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mVacuum.setChecked(true);
                } else {
                    mVacuum.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefTV.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mTV.setChecked(true);
                } else {
                    mTV.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
        mRefWindowsLivingRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mWindowsLiv.setChecked(true);
                } else {
                    mWindowsLiv.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefCurrentTempLivingRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long user = dataSnapshot.getValue(Long.class);
                if (!user.toString().isEmpty()) {
                    int currentTemp = user.intValue();
                    mCurrentTempLiv.setText("" + currentTemp + " C");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }
        });

        profile.setOnClickListener(view1 -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userEmail = user.getEmail();
                showPopupWindow(view1, userEmail);
            }

        });


        mLightBed.setOnClickListener(view1 -> {
            if (mLightBed.isChecked()) {
                mRefLightBedroom.setValue(1);
            } else {
                mRefLightBedroom.setValue(0);
            }
        });

        mWindowsBed.setOnClickListener(view1 -> {
            if (mWindowsBed.isChecked()) {
                mRefWindowsBedroom.setValue(1);
            } else {
                mRefWindowsBedroom.setValue(0);
            }
        });

        mDoorsBed.setOnClickListener(view1 -> {
            if (mDoorsBed.isChecked()) {
                mRefDoorsBedroom.setValue(1);
            } else {
                mRefDoorsBedroom.setValue(0);
            }
        });

        mLampBed.setOnClickListener(view1 -> {
            if (mLampBed.isChecked()) {
                mRefLampBedroom.setValue(1);
            } else {
                mRefLampBedroom.setValue(0);
            }
        });

        mAirPurifier.setOnClickListener(view1 -> {
            if (mAirPurifier.isChecked()) {
                mRefAirPurifier.setValue(1);
            } else {
                mRefAirPurifier.setValue(0);
            }
        });

        mHeaterBed.setOnClickListener(view1 -> {
            if (mHeaterBed.isChecked()) {
                mRefHeaterBedroom.setValue(1);
            } else {
                mRefHeaterBedroom.setValue(0);
            }
        });

        mACBed.setOnClickListener(view1 -> {
            if (mACBed.isChecked()) {
                mRefACBedroom.setValue(1);
            } else {
                mRefACBedroom.setValue(0);
            }
        });

        //bath
        mLightBath.setOnClickListener(view1 -> {
            if (mLightBath.isChecked()) {
                mRefLightBathroom.setValue(1);
            } else {
                mRefLightBathroom.setValue(0);
            }
        });

        mWindowsBath.setOnClickListener(view1 -> {
            if (mWindowsBath.isChecked()) {
                mRefWindowsBathroom.setValue(1);
            } else {
                mRefWindowsBathroom.setValue(0);
            }
        });

        mDoorsBath.setOnClickListener(view1 -> {
            if (mDoorsBath.isChecked()) {
                mRefDoorsBathroom.setValue(1);
            } else {
                mRefDoorsBathroom.setValue(0);
            }
        });

        mDryer.setOnClickListener(view1 -> {
            if (mDryer.isChecked()) {
                mRefDryer.setValue(1);
            } else {
                mRefDryer.setValue(0);
            }
        });

        mWasher.setOnClickListener(view1 -> {
            if (mWasher.isChecked()) {
                mRefWasher.setValue(1);
            } else {
                mRefWasher.setValue(0);
            }
        });

        mACBath.setOnClickListener(view1 -> {
            if (mACBath.isChecked()) {
                mRefACBathroom.setValue(1);
            } else {
                mRefACBathroom.setValue(0);
            }
        });

        mHeaterBath.setOnClickListener(view1 -> {
            if (mHeaterBath.isChecked()) {
                mRefHeaterBathroom.setValue(1);
            } else {
                mRefHeaterBathroom.setValue(0);
            }
        });

        //living room

        mLightLiv.setOnClickListener(view1 -> {
            if (mLightLiv.isChecked()) {
                mRefLightLivingRoom.setValue(1);
            } else {
                mRefLightLivingRoom.setValue(0);
            }
        });

        mWindowsLiv.setOnClickListener(view1 -> {
            if (mWindowsLiv.isChecked()) {
                mRefWindowsLivingRoom.setValue(1);
            } else {
                mRefWindowsLivingRoom.setValue(0);
            }
        });

        mDoorsLiv.setOnClickListener(view1 -> {
            if (mDoorsLiv.isChecked()) {
                mRefDoorsLivingRoom.setValue(1);
            } else {
                mRefDoorsLivingRoom.setValue(0);
            }
        });

        mTV.setOnClickListener(view1 -> {
            if (mTV.isChecked()) {
                mRefTV.setValue(1);
            } else {
                mRefTV.setValue(0);
            }
        });

        mVacuum.setOnClickListener(view1 -> {
            if (mVacuum.isChecked()) {
                mRefVacuum.setValue(1);
            } else {
                mRefVacuum.setValue(0);
            }
        });

        mLampLiv.setOnClickListener(view1 -> {
            if (mLampLiv.isChecked()) {
                mRefLampLivingRoom.setValue(1);
            } else {
                mRefLampLivingRoom.setValue(0);
            }
        });

        mHeaterLiv.setOnClickListener(view1 -> {
            if (mHeaterLiv.isChecked()) {
                mRefHeaterLivingRoom.setValue(1);
            } else {
                mRefHeaterLivingRoom.setValue(0);
            }
        });

        mACLiv.setOnClickListener(view1 -> {
            if (mACLiv.isChecked()) {
                mRefACLivingRoom.setValue(1);
            } else {
                mRefACLivingRoom.setValue(0);
            }
        });

        //kitchen
        mLightKit.setOnClickListener(view1 -> {
            if (mLightKit.isChecked()) {
                mRefLightKitchen.setValue(1);
            } else {
                mRefLightKitchen.setValue(0);
            }
        });

        mWindowsKit.setOnClickListener(view1 -> {
            if (mWindowsKit.isChecked()) {
                mRefWindowsKitchen.setValue(1);
            } else {
                mRefWindowsKitchen.setValue(0);
            }
        });

        mDoorsKit.setOnClickListener(view1 -> {
            if (mDoorsKit.isChecked()) {
                mRefDoorsKitchen.setValue(1);
            } else {
                mRefDoorsKitchen.setValue(0);
            }
        });

        mMicrowave.setOnClickListener(view1 -> {
            if (mMicrowave.isChecked()) {
                mRefMicrowave.setValue(1);
            } else {
                mRefMicrowave.setValue(0);
            }
        });

        mFridge.setOnClickListener(view1 -> {
            if (mFridge.isChecked()) {
                mRefFridge.setValue(1);
            } else {
                mRefFridge.setValue(0);
            }
        });

        mDishwasher.setOnClickListener(view1 -> {
            if (mDishwasher.isChecked()) {
                mRefDishwasher.setValue(1);
            } else {
                mRefDishwasher.setValue(0);
            }
        });

        mHeaterKit.setOnClickListener(view1 -> {
            if (mHeaterKit.isChecked()) {
                mRefHeaterKitchen.setValue(1);
            } else {
                mRefHeaterKitchen.setValue(0);
            }
        });

        mACKit.setOnClickListener(view1 -> {
            if (mACKit.isChecked()) {
                mRefACKitchen.setValue(1);
            } else {
                mRefACKitchen.setValue(0);
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.EditBathroom:
                this.showTopLevelFragment(new FragmentBathroom());
                break;
            case (R.id.EditLivingroom):
                showTopLevelFragment(new FragmentLivingRoom());

                break;

            case R.id.EditKitchen:
                showTopLevelFragment(new FragmentKitchen());


                break;
            case R.id.EditBedroom:
                this.showTopLevelFragment(new FragmentBedroom());
                break;
            case R.id.back_arrow:
                startActivity(new Intent(this,SummaryActivity.class));

                break;
        }
    }


    void showTopLevelFragment(Fragment f1) {

        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_host_main, f1); // f1_container is your FrameLayoutcontainer
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();

    }
    public void showPopupWindow(final View view, String mTExt) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_profile, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

        TextView test2 = popupView.findViewById(R.id.user_name);
        test2.setText(mTExt);

        Button logout = popupView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });



        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
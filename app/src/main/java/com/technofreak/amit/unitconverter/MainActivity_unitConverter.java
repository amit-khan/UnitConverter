package com.technofreak.amit.unitconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity_unitConverter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_converter);
    }

    public void length(View view) {
        Intent intent = new Intent(this,LayoutLengthActivity.class);
        startActivity(intent);
    }

    public void area(View view) {
        Intent intent = new Intent(this,AreaActivity.class);
        startActivity(intent);
    }

    public void volume(View view) {
        Intent intent = new Intent(this,VolumeActivity.class);
        startActivity(intent);
    }

    public void mass(View view) {
        Intent intent = new Intent(this,MassActivity.class);
        startActivity(intent);
    }

    public void velocity(View view) {
        Intent intent = new Intent(this,VelocityActivity.class);
        startActivity(intent);
    }

    public void angle(View view) {
        Intent intent = new Intent(this,AngleActivity.class);
        startActivity(intent);
    }

    public void energy(View view) {
        Intent intent = new Intent(this,EnergyActivity.class);
        startActivity(intent);
    }

    public void power(View view) {
        Intent intent = new Intent(this,PowerActivity.class);
        startActivity(intent);
    }

    public void temp(View view) {
        Intent intent = new Intent(this,TempActivity.class);
        startActivity(intent);
    }

    public void pressure(View view) {
        Intent intent = new Intent(this,PressureActivity.class);
        startActivity(intent);
    }
}

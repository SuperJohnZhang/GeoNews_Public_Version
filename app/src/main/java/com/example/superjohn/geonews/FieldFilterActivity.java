package com.example.superjohn.geonews;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

public class FieldFilterActivity extends AppCompatActivity {

    private static boolean[] FieldStates = {true, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_filter);

        ((CheckBox) findViewById(R.id.BusinessFieldActivity)).setChecked(FieldStates[0]);
        ((CheckBox) findViewById(R.id.EngineerFieldActivity)).setChecked(FieldStates[1]);
        ((CheckBox) findViewById(R.id.ScienceFieldActivity)).setChecked(FieldStates[2]);

    }


    public void TagBusiness(View view){
        FieldStates[0] = ((CheckBox)view).isChecked();
    }

    public void TagEngineer(View view){
        FieldStates[1] = ((CheckBox)view).isChecked();
    }

    public void TagScience(View view){
        FieldStates[2] = ((CheckBox)view).isChecked();
    }

}

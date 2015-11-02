package com.example.superjohn.geonews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void setLanguage(View view){
        Intent intent = new Intent(this, LanguageFilterActivity.class);
        this.startActivity(intent);
    }
    public void setField(View view){
        Intent intent = new Intent(this, FieldFilterActivity.class);
        this.startActivity(intent);
    }
    public void setTime(View view){
        Intent intent = new Intent(this, TimeFilterActivity.class);
        this.startActivity(intent);
    }


}

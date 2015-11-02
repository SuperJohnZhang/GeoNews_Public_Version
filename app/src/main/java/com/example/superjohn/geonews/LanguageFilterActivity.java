package com.example.superjohn.geonews;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class LanguageFilterActivity extends Activity {

    private static boolean[] languageStatus = {true,true,true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_filter);
        ((CheckBox)findViewById(R.id.EnglishLanguageActivity)).setChecked(languageStatus[0]);
        ((CheckBox)findViewById(R.id.ChineseLanguageActivity)).setChecked(languageStatus[1]);
        ((CheckBox)findViewById(R.id.HindiLanguageActivity)).setChecked(languageStatus[2]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //if (id == R.id.LanguageFilterActivity) {
        //    return true;
        //}
        return super.onOptionsItemSelected(item);
    }

    public void TagEnglish(View view){
        languageStatus[0] = ((CheckBox)view).isChecked();
    }

    public void TagChinese(View view){
        languageStatus[1] = ((CheckBox)view).isChecked();
    }

    public void TagHindi(View view){
        languageStatus[2] = ((CheckBox)view).isChecked();
    }



}

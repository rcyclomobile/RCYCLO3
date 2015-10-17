package com.hfad.rcyclo3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TopLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
    }

    public void companyRegister(View view){
        Intent intent = new Intent(this, CompanyLoginActivity.class);
        startActivity(intent);
    }


    public void establishmentRegister(View view){
        Intent intent = new Intent(this, EstablishmentLoginActivity.class);
        startActivity(intent);
    }


}

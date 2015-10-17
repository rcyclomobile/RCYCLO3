package com.hfad.rcyclo3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class CompanyMainActivity extends Activity {

    public static final String EMPRESA= "empresa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);
    }

    public void request(View view){
        String empresa = (String)getIntent().getExtras().get(EMPRESA);
        Intent intent = new Intent(this, SelectWasteActivity.class);
        intent.putExtra(SelectWasteActivity.EMPRESA, empresa);
        startActivity(intent);

    }

    public void myContainers(View view){
        String empresa = (String)getIntent().getExtras().get(EMPRESA);
        Intent intent = new Intent(this, CompanyContainersActivity.class);
        intent.putExtra(CompanyContainersActivity.EMPRESA, empresa);
        startActivity(intent);
    }

    public void logout(View view){
        Intent intent = new Intent(this,TopLevelActivity.class);
        startActivity(intent);

    }


}

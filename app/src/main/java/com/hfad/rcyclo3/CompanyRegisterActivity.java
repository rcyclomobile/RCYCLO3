package com.hfad.rcyclo3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CompanyRegisterActivity extends Activity {

    private final int DURACION = 1000;
    EditText etName, etEmail, etPassword,etPhone, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);
        etName      = (EditText) findViewById(R.id.name);
        etEmail     = (EditText) findViewById(R.id.email);
        etPassword  = (EditText) findViewById(R.id.password);
        etPhone     = (EditText) findViewById(R.id.phone);
        etAddress   = (EditText) findViewById(R.id.address);
    }

    @Override
    public void onStop() {
        super.onStop();
        Crouton.cancelAllCroutons();
    }

    public void sendForm(View view){
        String name     = etName.getText().toString();
        String email    = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String phone    = etPhone.getText().toString();
        String address  = etAddress.getText().toString();

        if (name.matches("") || email.matches("") || password.matches("") || phone.matches("") || address.matches("")) {
            Crouton.makeText(this,"Se deben llenar todos los campos!", Style.ALERT).show();
        }

        else {
            if (isEmailValid(email)) {

                if( isEmailUsed(email)){
                    Crouton.makeText(this, "El mail ya existe!!", Style.ALERT).show();
                }
                 else {
                    ContentValues companyValues = new ContentValues();
                    companyValues.put("NAME", name);
                    companyValues.put("EMAIL", email);
                    companyValues.put("PASSWORD", password);
                    companyValues.put("PHONE", phone);
                    companyValues.put("ADDRESS", address);

                    SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
                    SQLiteDatabase db = rcycloDatabaseHelper.getWritableDatabase();

                    db.insert("COMPANY", null, companyValues);
                    db.close();
                    Crouton.makeText(this, "Empresa ha sido Registrada correctamente!", Style.CONFIRM).show();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent().setClass(CompanyRegisterActivity.this, CompanyLoginActivity.class);
                            startActivity(intent);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, DURACION);
                }
            } else {
                Crouton.makeText(this, "El mail debe ser valido!!", Style.ALERT).show();
                etEmail.setText("");
            }
        }
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public boolean isEmailUsed(String email) {
        SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
        SQLiteDatabase db = rcycloDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("COMPANY", new String[]{"EMAIL"}, "EMAIL = ?", new String[]{email}, null, null, null);
        if (cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }
    }



}

package com.hfad.rcyclo3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class CompanyModifyContainerActivity extends Activity {

    public static final String NAME_CONTAINER = "nameContainer";
    public static final String LATLONG = "latLong";
    public static final String ESTABLISHMENT = "nameEstablishment";
    public static final String COMPANY = "nameCompany";
    public static final String ESTADO = "estateContainer";

    TextView tvNameContainer, tvNameEstablishment  , tvEstado, tvWaste;
    private RadioButton rbVacio, rbMitad, rbLLeno;
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_modify_container);

        rbVacio         = (RadioButton) findViewById(R.id.vacio);
        rbMitad         = (RadioButton) findViewById(R.id.mitad);
        rbLLeno         = (RadioButton) findViewById(R.id.lleno);

        String nameContainer        = (String) getIntent().getExtras().get(NAME_CONTAINER);
        String nameEstablishment    = (String) getIntent().getExtras().get(ESTABLISHMENT);
        String estado               = (String) getIntent().getExtras().get(ESTADO);

        tvNameContainer     = (TextView) findViewById(R.id.nombre_contenedor);
        tvNameEstablishment = (TextView) findViewById(R.id.fundacion);
        tvEstado            = (TextView) findViewById(R.id.estado);
        tvWaste             = (TextView) findViewById(R.id.tipo_desecho);


        if("VACIO".equals(estado) ){
            rbVacio.setChecked(true);        }

        else if("MEDIO".equals(estado)){
            rbVacio.setEnabled(false);
            rbMitad.setChecked(true);        }

        else if("LLENO".equals(estado)){
            rbVacio.setEnabled(false);
            rbMitad.setEnabled(false);
            rbLLeno.setChecked(true);        }

        SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
        db = rcycloDatabaseHelper.getReadableDatabase();

        cursor = db.query("ESTABLISHMENT",
                new String[]{"NAME", "WASTE"},
                "NAME = ?",
                new String[] {nameEstablishment},
                null, null, null);

        cursor.moveToFirst();
        String waste = cursor.getString(1);

        tvNameContainer.setText(nameContainer);
        tvNameEstablishment.setText(nameEstablishment);
        tvEstado.setText(estado);
        tvWaste.setText(waste);
    }

    public void actualizar(View view){
        String nameContainer    = (String) getIntent().getExtras().get(NAME_CONTAINER);
        String nameCompany      = (String) getIntent().getExtras().get(COMPANY);

        SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
        SQLiteDatabase db = rcycloDatabaseHelper.getWritableDatabase();

        ContentValues containerValues = new ContentValues();

        if(rbMitad.isChecked() == true){
            containerValues.put("ESTADO", "MEDIO"); }

        else if(rbLLeno.isChecked() == true){
            containerValues.put("ESTADO", "LLENO"); }

        db.update("CONTAINER", containerValues, "NAME_CONTAINER = ? AND COMPANY = ?", new String[]{nameContainer, nameCompany});

        Intent intent = new Intent(this, CompanyMainActivity.class);
        intent.putExtra(CompanyMainActivity.EMPRESA, nameCompany);
        startActivity(intent);
    }
}

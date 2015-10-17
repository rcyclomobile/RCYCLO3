package com.hfad.rcyclo3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ActivarContainerActivity extends Activity {

    private SQLiteDatabase db;

    public static final String NAME_CONTAINER = "nameContainer";
    public static final String LATLONG = "latLong";
    public static final String ESTABLISHMENT = "nameEstablishment";
    public static final String COMPANY = "nameCompany";

    TextView tvNameContainer, tvNameCompany  , tvLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activar_container);

        String nameContainer        = (String) getIntent().getExtras().get(NAME_CONTAINER);
        String nameCompany          = (String) getIntent().getExtras().get(COMPANY);
        String latLong              = (String) getIntent().getExtras().get(LATLONG);

        tvNameContainer = (TextView) findViewById(R.id.nombre_contenedor);
        tvNameCompany = (TextView) findViewById(R.id.empresa);
        tvLatLong = (TextView) findViewById(R.id.coordenadas);

        tvNameContainer.setText(nameContainer);
        tvNameCompany.setText(nameCompany);
        tvLatLong.setText(latLong);

    }

    public void confirmar(View view){
        String nameEstablishment    = (String) getIntent().getExtras().get(ESTABLISHMENT);
        String nameContainer        = (String) getIntent().getExtras().get(NAME_CONTAINER);
        String nameCompany          = (String) getIntent().getExtras().get(COMPANY);

        SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
        db = rcycloDatabaseHelper.getWritableDatabase();

        activateContainer(db, nameContainer, nameCompany);

        db.close();

        Toast toast = Toast.makeText(this, "Nuevo Contenedor Habilitado!", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(this, EstablishmentMainActivity.class);
        intent.putExtra(EstablishmentMainActivity.NAME, nameEstablishment);
        startActivity(intent);

    }

    public static void  activateContainer(SQLiteDatabase db, String nameContainer, String nameCompany) {
        ContentValues containerValues = new ContentValues();
        containerValues.put("ACTIVO", "ACTIVO");
        db.update("CONTAINER", containerValues, "NAME_CONTAINER = ? AND COMPANY = ?", new String[]{nameContainer, nameCompany});
    }


}

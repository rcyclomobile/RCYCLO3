package com.hfad.rcyclo3;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class ContainerRequestsActivity extends ListActivity {
    public static final String NAME = "name";
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listContainer = getListView();

        String nameEstablishment = (String) getIntent().getExtras().get(NAME);

        SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
        db = rcycloDatabaseHelper.getReadableDatabase();

        cursor = db.query("CONTAINER",
                new String[]{"_id", "NAME_CONTAINER", "LATLONG", "ESTABLISHMENT", "COMPANY", "ACTIVO"},
                "ESTABLISHMENT = ? AND ACTIVO = ?",
                new String[]{nameEstablishment, "INACTIVO"},
                null, null, null);

        if(cursor.moveToFirst() == false){
            Context context = getApplicationContext();
            CharSequence text = "No hay solicitudes pendientes!";
            int duration = Toast.LENGTH_SHORT;

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

            TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
            textToast.setText(text);

            Toast toast = new Toast(context);
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();

            Intent intent = new Intent(ContainerRequestsActivity.this,EstablishmentMainActivity.class);
            intent.putExtra(EstablishmentMainActivity.NAME,nameEstablishment);
            startActivity(intent);
        }



        CursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"COMPANY", "ACTIVO"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
        listContainer.setAdapter(listAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        cursor.moveToFirst();
        cursor.move(position);

        String nameContainer        = cursor.getString(1);
        String latLong              = cursor.getString(2);
        String nameEstablishment    = cursor.getString(3);
        String nameCompany          = cursor.getString(4);


        Intent intent = new Intent(ContainerRequestsActivity.this, ActivarContainerActivity.class);
        intent.putExtra(ActivarContainerActivity.NAME_CONTAINER, nameContainer);
        intent.putExtra(ActivarContainerActivity.LATLONG, latLong);
        intent.putExtra(ActivarContainerActivity.ESTABLISHMENT, nameEstablishment);
        intent.putExtra(ActivarContainerActivity.COMPANY, nameCompany);

        startActivity(intent);
    }


}
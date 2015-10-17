package com.hfad.rcyclo3;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyContainersActivity extends ListActivity {

    public static final String EMPRESA= "empresa";
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listContainer = getListView();

        String nameCompany = (String) getIntent().getExtras().get(EMPRESA);

        SQLiteOpenHelper rcycloDatabaseHelper = new RcycloDatabaseHelper(this);
        db = rcycloDatabaseHelper.getReadableDatabase();

        cursor = db.query("CONTAINER",
                new String[]{"_id", "NAME_CONTAINER", "LATLONG", "ESTABLISHMENT", "COMPANY", "ESTADO", "ACTIVO"},
                "COMPANY = ? AND ACTIVO = ?",
                new String[]{nameCompany, "ACTIVO"},
                null, null, null);


        if(cursor.moveToFirst() == false){
            Context context = getApplicationContext();
            CharSequence text = "Aun no tienes containers habilitados!";
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

            Intent intent = new Intent(CompanyContainersActivity.this,CompanyMainActivity.class);
            intent.putExtra(CompanyMainActivity.EMPRESA,nameCompany);
            startActivity(intent);
        }

        CursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"NAME_CONTAINER", "ESTADO"},
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
        String estateContainer      = cursor.getString(5);


        Intent intent = new Intent(CompanyContainersActivity.this, CompanyModifyContainerActivity.class);
        intent.putExtra(CompanyModifyContainerActivity.NAME_CONTAINER, nameContainer);
        intent.putExtra(CompanyModifyContainerActivity.LATLONG, latLong);
        intent.putExtra(CompanyModifyContainerActivity.ESTABLISHMENT, nameEstablishment);
        intent.putExtra(CompanyModifyContainerActivity.COMPANY, nameCompany);
        intent.putExtra(CompanyModifyContainerActivity.ESTADO, estateContainer);

        startActivity(intent);
    }

}

package edu.val.obtenerinfocontacto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

//vamos a permitir la selección de un contacto a esta aplicación
//1 No obtengo los datos directamente: obtengo una URI de content provider
//2 No tengo permisos desde esta app para leer los contactos: Uso la propia app de contactos

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent_seleccion_contactos = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        if (intent_seleccion_contactos.resolveActivity(getPackageManager()) != null) {
            Log.d("ETIQUETA_LOG", "Lanznado selección de contactos");
            startActivityForResult(intent_seleccion_contactos, 888);
            //saldrá la app de contactos
        } else {
            Toast.makeText(this, "NO EXISTE UNA APP DE CONTACTOS", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ETIQUETA_LOG", "Volvemos de la app de contactos");
        Uri uri_contacto = data.getData();
        Log.d("ETIQUETA_LOG", "uri_contacto  del content provider = " + uri_contacto);
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri_contacto, null, null, null, null);
        if (cursor.moveToFirst())
        {
            Log.d("ETIQUETA_LOG", "TENEMOS DATOS DEL CONTACTO ");
            //puedeo acceder por el número de columna o el nombre
            int ncolumnatelefono = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int ncolumnanombre = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String numero =  cursor.getString(ncolumnatelefono);
            String nombre =  cursor.getString(ncolumnanombre);

            Log.d("ETIQUETA_LOG" , "Número del contacto = " + numero);
            Log.d("ETIQUETA_LOG" , "Nombre del contacto = " + nombre);
        }

    }
}
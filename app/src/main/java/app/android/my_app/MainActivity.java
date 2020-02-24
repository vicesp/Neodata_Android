package app.android.my_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import DAO.DataBaseHelper;
import DTO.Variables_Universales;
import DTO.consultas;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsuario;
    private EditText txtCont;
    private ImageView iv_logo;
    private String Activo;
    private Button btnEnviar;
    protected SQLiteDatabase db;
    protected Cursor cursor;
    private TextView tv_version,tv_contrasena_admin;

    //djfa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_1);
        final DataBaseHelper myDbHelper = new DataBaseHelper(this);


        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }
        try {

            myDbHelper.openDataBase();
        }catch(SQLException sqle){

            throw sqle;

        }

        Variables_Universales.setContext(this);

        txtUsuario = (EditText)findViewById(R.id.edUsuario);
        txtCont = (EditText)findViewById(R.id.edContrase);
        btnEnviar=(Button)findViewById(R.id.buIngresar);
        tv_version=(TextView)findViewById(R.id.textView22);
        tv_contrasena_admin=(TextView)findViewById(R.id.textView59);


        tv_version.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv_contrasena_admin.setVisibility(View.VISIBLE);
                consultas con = new consultas();
                String c_generada = con.DAO_contrasena();
                tv_contrasena_admin.setText(c_generada);

                return true;
            }
        });

        iv_logo=(ImageView)findViewById(R.id.imageView6);
        byte[] data1=null;
        consultas con = new consultas();
        data1 = con.DAO_Select_Foto_LOGO_Panel_Control();

        if(data1!=null) {
            Bitmap originalBitmap = (BitmapFactory.decodeByteArray(data1, 0, data1.length));
            iv_logo.setImageBitmap(originalBitmap);

        }

        //iv_logo.getLayoutParams().width = 560;
        //iv_logo.requestLayout();

		/*LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(360, 100);
		iv_logo.setLayoutParams(layoutParams);*/


        btnEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DataBaseHelper dp = new DataBaseHelper(MainActivity.this);
                db = dp.getWritableDatabase();

                //if (txtUsuario.getText().toString().contentEquals("Admin") && txtCont.getText().toString().contentEquals(".x25,zeX")) {
                if (txtUsuario.getText().toString().contentEquals("Admin") && txtCont.getText().toString().contentEquals(".x25,zexHD")) {

                    Variables_Universales.setId_usuario(999);
                    Variables_Universales.setNombre_usuario("Admin");
                    finish();
                    startActivity(new Intent(MainActivity.this, Menu_seleccion.class));
                }

                else{
                    cursor = db.rawQuery("select id_usuario, contrasena, nombre from usuarios where nombre='" + txtUsuario.getText().toString() + "' and contrasena= '" + txtCont.getText().toString() + "' and isActive= 1 ", null);
                    if (cursor.moveToPosition(0)) {

                        if (txtUsuario.getText().toString().contentEquals(cursor.getString(cursor.getColumnIndex("nombre"))) && txtCont.getText().toString().contentEquals(cursor.getString(cursor.getColumnIndex("contrasena")))) {

                            Variables_Universales.setId_usuario(cursor.getInt(cursor.getColumnIndex("id_usuario")));
                            Variables_Universales.setNombre_usuario(cursor.getString(cursor.getColumnIndex("nombre")));

                            cursor.close();
                            myDbHelper.close();
                            db.close();

                            finish();
                            startActivity(new Intent(MainActivity.this, Menu_seleccion.class));

                        } else {

                            txtCont.setText("");
                            txtUsuario.setText("");
                            Alerta();

                        }
                        //db.close();
                        dp.close();
                    } else {

                        consultas con = new consultas();
                        String e_equivalente = con.DAO_compara_contrasena(tv_contrasena_admin.getText().toString());
                       if(txtUsuario.getText().toString().contentEquals("Admin") && e_equivalente.contentEquals(txtCont.getText().toString()))
                       {
                           Variables_Universales.setId_usuario(999);
                           Variables_Universales.setNombre_usuario("Admin");
                           finish();
                           startActivity(new Intent(MainActivity.this, Menu_seleccion.class));
                       }
                       else{

                           txtCont.setText("");
                           txtUsuario.setText("");
                           tv_contrasena_admin.setText("");
                           Alerta();
                           dp.close();
                       }

                    }

                }
            }
        });



    }

    public void Alerta(){

        AlertDialog.Builder myDialog
                = new AlertDialog.Builder(MainActivity.this);
        myDialog.setTitle(getResources().getString(R.string.m2));
        TextView textView = new TextView(this);
        textView.setText("");

        ViewGroup.LayoutParams textViewLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewLayoutParams);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(textView);
        myDialog.setView(layout);
        myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                myDialog.show();
    }


}


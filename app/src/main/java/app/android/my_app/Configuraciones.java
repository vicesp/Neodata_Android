package app.android.my_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import DAO.DataBaseHelper;
import DTO.Variables_Universales;
import DTO.consultas;

public class Configuraciones extends AppCompatActivity {
	

	private EditText IpActual,ip_actual1,ip_nueva1,nom_db1,nom_db2;
	private EditText IpNueva;
	private ImageButton ActualizarIP;
	protected SQLiteDatabase db;
	protected Cursor cursor;
	Variables_Universales var;
	private ImageView iv_logo;
TextView tv_user;
	Animation animRotar,anim_desaparece,anim_escala;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuraciones_1);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		animRotar = AnimationUtils.loadAnimation(Configuraciones.this, R.anim.anim_rotate);
		anim_desaparece = AnimationUtils.loadAnimation(Configuraciones.this, R.anim.anim_desaparece);
		anim_escala = AnimationUtils.loadAnimation(Configuraciones.this, R.anim.anim_escala);

		var=new Variables_Universales();
		tv_user=(TextView)findViewById(R.id.textView32);
		tv_user.setText(var.getNombre_usuario());


		IpActual=(EditText) findViewById(R.id.etConfIPActual);
        IpNueva=(EditText)findViewById(R.id.etConfNuevaIP);
		ip_nueva1=(EditText)findViewById(R.id.editText8);
		ip_actual1=(EditText)findViewById(R.id.editText5);
		nom_db1=(EditText)findViewById(R.id.editText7);
		nom_db2=(EditText)findViewById(R.id.editText4);

        ActualizarIP = (ImageButton)findViewById(R.id.imageButton14);

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
	    db = myDbHelper.getWritableDatabase();

		iv_logo=(ImageView)findViewById(R.id.imageView7);
		byte[] data1=null;
		consultas con = new consultas();
		data1 = con.DAO_Select_Foto_LOGO_Panel_Control();

		if(data1!=null) {
			Bitmap originalBitmap = (BitmapFactory.decodeByteArray(data1, 0, data1.length));
			iv_logo.setImageBitmap(originalBitmap);

		}

		cursor=null;
		cursor = db.rawQuery("SELECT ipActual FROM configuraciones  WHERE id=1 ", null);
		if(cursor.moveToFirst()){
		IpActual.setText(cursor.getString(cursor.getColumnIndex("ipActual")));
		}

		cursor=null;
		cursor = db.rawQuery("SELECT ipActual FROM configuraciones  WHERE id=2 ", null);
		if(cursor.moveToFirst()){
			ip_actual1.setText(cursor.getString(cursor.getColumnIndex("ipActual")));
		}

		cursor=null;
		cursor = db.rawQuery("SELECT nombre_db FROM configuraciones  WHERE id=1 ", null);
		if(cursor.moveToFirst()){
			nom_db1.setText(cursor.getString(cursor.getColumnIndex("nombre_db")));
		}

		cursor=null;
		cursor = db.rawQuery("SELECT nombre_db FROM configuraciones  WHERE id=2 ", null);
		if(cursor.moveToFirst()){
			nom_db2.setText(cursor.getString(cursor.getColumnIndex("nombre_db")));
		}

       ActualizarIP.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Actualizar();
		}
	});
    }
    
    public void Actualizar(){
    	
    	if(!IpNueva.getText().toString().contentEquals(""))
    	{
			DataBaseHelper myDbHelper = new DataBaseHelper(this);
			myDbHelper = new DataBaseHelper(this);
			db = myDbHelper.getWritableDatabase();

			db.execSQL("UPDATE configuraciones SET  ipActual = '"+ IpNueva.getText().toString() +"' WHERE id=1 ");
			cursor = db.rawQuery("SELECT ipActual FROM configuraciones WHERE id=1 ", null);
			if(cursor.moveToFirst()){
				IpActual.setText(cursor.getString(cursor.getColumnIndex("ipActual")));
			}
			db.close();
			cursor.close();

    	}
		if(!ip_nueva1.getText().toString().contentEquals(""))
		{
			DataBaseHelper myDbHelper = new DataBaseHelper(this);
			myDbHelper = new DataBaseHelper(this);
			db = myDbHelper.getWritableDatabase();

			db.execSQL("UPDATE configuraciones SET  ipActual = '"+ ip_nueva1.getText().toString() +"' WHERE id=2 ");
			cursor = db.rawQuery("SELECT ipActual FROM configuraciones WHERE id=2 ", null);
			if(cursor.moveToFirst()){
				ip_actual1.setText(cursor.getString(cursor.getColumnIndex("ipActual")));
			}
			db.close();
			cursor.close();

		}
		if(!nom_db1.getText().toString().contentEquals(""))
		{
			DataBaseHelper myDbHelper = new DataBaseHelper(this);
			myDbHelper = new DataBaseHelper(this);
			db = myDbHelper.getWritableDatabase();

			db.execSQL("UPDATE configuraciones SET  nombre_db = '"+ nom_db1.getText().toString() +"' WHERE id=1 ");
			cursor = db.rawQuery("SELECT nombre_db FROM configuraciones WHERE id=1 ", null);
			if(cursor.moveToFirst()){
				nom_db1.setText(cursor.getString(cursor.getColumnIndex("nombre_db")));
			}
			db.close();
			cursor.close();

		}
		if(!nom_db2.getText().toString().contentEquals(""))
		{
			DataBaseHelper myDbHelper = new DataBaseHelper(this);
			myDbHelper = new DataBaseHelper(this);
			db = myDbHelper.getWritableDatabase();

			db.execSQL("UPDATE configuraciones SET  nombre_db = '"+ nom_db2.getText().toString() +"' WHERE id=2 ");
			cursor = db.rawQuery("SELECT nombre_db FROM configuraciones WHERE id=2 ", null);
			if(cursor.moveToFirst()){
				nom_db2.setText(cursor.getString(cursor.getColumnIndex("nombre_db")));
			}
			db.close();
			cursor.close();

		}

		Alerta("La IP se actualizó correctamente");
    }

public void Alerta(String mensaje){
    	AlertDialog.Builder myDialog
      = new AlertDialog.Builder(Configuraciones.this);
     myDialog.setTitle(mensaje);
     TextView textView = new TextView(this);
     textView.setText("");
     LayoutParams textViewLayoutParams
      = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
     textView.setLayoutParams(textViewLayoutParams);
     LinearLayout layout = new LinearLayout(this);
     layout.setOrientation(LinearLayout.VERTICAL);
     layout.addView(textView);

     myDialog.setView(layout);
     myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

         public void onClick(DialogInterface arg0, int arg1) {
        	
           
          }
         });
     
    myDialog.show();
    }
}

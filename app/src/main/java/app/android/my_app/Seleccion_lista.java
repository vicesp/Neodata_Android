package app.android.my_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import DAO.DataBaseHelper;
import DTO.Variables_Universales;
import DTO.consultas;

public class Seleccion_lista extends AppCompatActivity implements OnItemClickListener,AdapterView.OnItemSelectedListener {

	protected SQLiteDatabase db;
	protected Cursor cursor;
	protected Cursor cursor2;
	protected ListView ListCondominios;
	protected ArrayAdapter<String> adapter;
	protected Variables_Universales var;
	String[] CONDOMINIOS,orden_resp;
	TextView tv_user;
	private ImageView iv_logo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_lista);

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

        ListCondominios = (ListView)findViewById(R.id.lv_desarrollos);
        var=new Variables_Universales();

		tv_user=(TextView)findViewById(R.id.textView31);
		tv_user.setText(var.getNombre_usuario());

		iv_logo=(ImageView)findViewById(R.id.imageView4);
		byte[] data1=null;
		consultas con = new consultas();
		data1 = con.DAO_Select_Foto_LOGO_Panel_Control();

		if(data1!=null) {
			Bitmap originalBitmap = (BitmapFactory.decodeByteArray(data1, 0, data1.length));
			iv_logo.setImageBitmap(originalBitmap);

		}

		DataBaseHelper myDbHelper=new DataBaseHelper(Seleccion_lista.this);
		 db = myDbHelper.getWritableDatabase();

		if(var.getId_usuario()==999){
			cursor = db.rawQuery("SELECT desarrollo,orden from desarrollo ORDER BY desarrollo ASC", null);
		}
		else{
			cursor = db.rawQuery("SELECT d.desarrollo,d.orden from desarrollo d INNER JOIN rel_usuarios_desarrollo rud on rud.id_desarrollo=d.id_desarrollo WHERE rud.id_usuario='"+var.getId_usuario()+"' ORDER BY d.desarrollo asc", null);

			//cursor = db.rawQuery("SELECT DISTINCT(d.desarrollo) from desarrollo d INNER JOIN rel_usuarios_desarrollo rud on rud.id_desarrollo=d.id_desarrollo WHERE rud.id_usuario='"+var.getId_usuario()+"' ORDER BY d.desarrollo asc", null);
		}
		 /*cursor = db.rawQuery("SELECT DISTINCT nombre_desarrollo FROM sembrado ORDER BY nombre_desarrollo asc", null);*/
		 CONDOMINIOS = new String[cursor.getCount()] ;
		orden_resp = new String[cursor.getCount()] ;

		 if (cursor != null ) {
		    if  (cursor.moveToFirst()) {
		          for(int x=0;x< cursor.getCount();x++)
		          {
		              CONDOMINIOS[x] = (cursor.getString(cursor.getColumnIndex("desarrollo")));
					  orden_resp[x] = (cursor.getString(cursor.getColumnIndex("orden")));
		              cursor.moveToNext();
		          }
				}
		 	}

		 adapter = new ArrayAdapter<String>(this,R.layout.employee_list_item, R.id.label, CONDOMINIOS);
		ListCondominios.setAdapter(adapter);

		ListCondominios.setTextFilterEnabled(true);
		ListCondominios.showContextMenu();
		ListCondominios.setScrollingCacheEnabled(true);
		ListCondominios.setScrollbarFadingEnabled(true);
		ListCondominios.setFastScrollEnabled(true);
		ListCondominios.setOnItemClickListener(this);
		ListCondominios.setCacheColorHint(0);  //para poner de color blanco la lista al momento de hacer scroll
    }
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		// TODO Auto-generated method stub

		var.setNombre_condominio_select((String) adapter.getItemAtPosition(position));
		//String is_orden="";
		//is_orden=orden_resp[position];
		var.setCalifica_orden(orden_resp[position]);
		String nom_cond=var.getNombre_condominio_select();
		 startActivity(new Intent(Seleccion_lista.this, Requisiciones.class));
		 
       }
		
	}






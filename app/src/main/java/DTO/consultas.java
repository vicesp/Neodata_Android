package DTO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;

import DAO.DataBaseHelper;

public class consultas extends Activity {
	protected SQLiteDatabase db;
	public String[] a_select,arreglo,f_ini,f_fin,arreglo2,arreglo_3,arreglo_4,arreglo_5,arreglo_6,arreglo_7,arreglo_8,arreglo_9,arreglo_10;
	public Integer[] arreglo_int;
	String frente,manzana,lote,interior;
	Cursor cursor,cursor2,cursor3,cursor4;
	
	 DataBaseHelper myDbHelper = new DataBaseHelper(Variables_Universales.getContext());

	public void AbreDB() {

		try {

			myDbHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}
	}
	
	
	public void met(int idcalle, int idcondominio, String nombrecalle){
		
		 
		    db = myDbHelper.getWritableDatabase();
		    db.execSQL("INSERT INTO CA_Calles (idCalle, idCondominio, nombreCalle) " +
	    			"VALUES ("+ idcalle +", "+idcondominio+" ,'"+nombrecalle+"' )");
		    myDbHelper.close();
		    db.close();
		}
	
	public void insertaManzanas(int idManzana, String nombreManana){
		
		 
		 db = myDbHelper.getWritableDatabase();
		    db.execSQL("INSERT INTO CA_Manzanas (idManzana, Manzana) " +
	    			"VALUES ("+ idManzana +",'"+nombreManana+"' )");
		    myDbHelper.close();
		    db.close();
		}
	
	public void insertaUsuarios(int id_usuario, String nombre, String contrasena, int isActive){
		
		
		    db = myDbHelper.getWritableDatabase();
		    db.execSQL("INSERT INTO usuarios (id_usuario, nombre, contrasena, isActive) " +
	    			"VALUES ("+id_usuario+",'"+ nombre +"','"+contrasena+"',"+isActive+" )");
		    myDbHelper.close();
		    db.close();
		}
	
	public void insertaCondominios(int isActive, String condominio, int id_condominio){
		
		 
		    db = myDbHelper.getWritableDatabase();
		    db.execSQL("INSERT INTO condominios (isActive, condominio, id_condominio) " +
	    			"VALUES ("+isActive+",'"+condominio+"',"+id_condominio+" )");
		    myDbHelper.close();
		    db.close();
		}
	public void insertaCondominos(String noMedidor, String noCondomino, int idManzana, int idCalle, int idCondominio, int id_condomino, int lote){
		
		
		    db = myDbHelper.getWritableDatabase();
		    db.execSQL("INSERT INTO condominos (noMedidor, noCondomino, idManzana, idCalle, idCondominio, id_condomino, lote) " +
	    			"VALUES ('"+noMedidor+"', '"+noCondomino+"', "+idManzana+", "+idCalle+", "+idCondominio+", "+id_condomino+", "+lote+")");
		    
		    myDbHelper.close();
		    db.close();
		}
	public void insertaActividades(int id_actividad, int codigo, String actividad){
		
		
		    db = myDbHelper.getWritableDatabase();
		    db.execSQL("INSERT INTO ca_actividades (id_actividad, codigo, actividad) " +
	    			"VALUES ("+ id_actividad +","+codigo+",'"+actividad+"' )");
		    myDbHelper.close();
		    db.close();
		}

		public String[] estaciones(String contrato){

			DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
			//AbreDB();
			db = myDbHelper.getWritableDatabase();
			cursor = null;

			cursor = db.rawQuery("select descripcion from estaciones  where contrato='"+contrato+"' order by id_estacion", null);
			consultas lista = new consultas();
			lista.arreglo = new String[cursor.getCount()];

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					for (int x = 0; x < cursor.getCount(); x++) {

						lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("descripcion"));
						cursor.moveToNext();
					}
				}
			}
			cursor.close();
			db.close();
			return lista.arreglo;
		}

	public consultas DAO_Lista_preguntas(String contrato, String contratista, String ubicacion){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		//AbreDB();
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String id_contrato="",id_contratista="";
		//contrato = contrato.trim();

		cursor = db.rawQuery("select id_contratista from sembrado  where  contratista  = '"+contratista+"' and  contrato  = '"+contrato+"' and ubicacion = '"+ubicacion+"' ", null);
		cursor2 = db.rawQuery("select id_contrato from sembrado  where contratista  = '"+contratista+"' and  contrato  = '"+contrato+"' and ubicacion = '"+ubicacion+"' ", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
					id_contratista = cursor.getString(cursor.getColumnIndex("id_contratista"));
			}
		}
		if (cursor2 != null) {
			if (cursor2.moveToFirst()) {
				id_contrato = cursor2.getString(cursor2.getColumnIndex("id_contrato"));
			}
		}

		//cursor = db.rawQuery("select descripcion, fecha_inicio, fecha_fin from estaciones  where contrato = '"+id_contrato+"' and contratista  = '"+id_contratista+"' and ubicacion = '"+ubicacion+"' order by CAST(id_estacion AS INTEGER) ", null);
		cursor = db.rawQuery("select descripcion, fecha_inicio, fecha_fin from estaciones  where contrato = '"+id_contrato+"' and contratista  = '"+id_contratista+"' and ubicacion = '"+ubicacion+"' order by CAST(id_estacion AS INTEGER) ", null);

		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];
		lista.f_ini = new String[cursor.getCount()];
		lista.f_fin = new String[cursor.getCount()];

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {
					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("descripcion"));
					lista.f_ini[x] = cursor.getString(cursor.getColumnIndex("fecha_inicio"));
					lista.f_fin[x] = cursor.getString(cursor.getColumnIndex("fecha_fin"));
					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista;
	}

    public String[] DAO_IP_Servidor(Integer id_conexion){

        DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
        //AbreDB();
        db = myDbHelper.getWritableDatabase();
        cursor = null;

        cursor = db.rawQuery("select ipActual, nombre_db from configuraciones where id='"+id_conexion+"' ", null);
        consultas lista = new consultas();
        lista.arreglo = new String[cursor.getColumnCount()];

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                    lista.arreglo[0] = cursor.getString(cursor.getColumnIndex("ipActual"));
                    lista.arreglo[1] = cursor.getString(cursor.getColumnIndex("nombre_db"));
            }
        }
        cursor.close();
        db.close();
        return lista.arreglo;
    }

    public consultas DAO_Lista_preguntas_respuestas(){

        DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
        db = myDbHelper.getWritableDatabase();
        cursor = null;

        cursor = db.rawQuery("SELECT codigo, descrip, unidad, moneda,cantidad,observaciones,id,por_requerir,id_explosion_ins from insumos_temporal " +
				"ORDER BY codigo", null);


        consultas lista = new consultas();
        lista.arreglo = new String[cursor.getCount()];
		lista.arreglo2 = new String[cursor.getCount()];
		lista.arreglo_3 = new String[cursor.getCount()];
		lista.arreglo_4 = new String[cursor.getCount()];
		lista.arreglo_5 = new String[cursor.getCount()];
		lista.arreglo_6 = new String[cursor.getCount()];
		lista.arreglo_7 = new String[cursor.getCount()];
		lista.arreglo_8 = new String[cursor.getCount()];
		lista.arreglo_9 = new String[cursor.getCount()];

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                for (int x = 0; x < cursor.getCount(); x++) {

                    lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("codigo"));
					lista.arreglo2[x] = cursor.getString(cursor.getColumnIndex("descrip"));
					lista.arreglo_3[x] = cursor.getString(cursor.getColumnIndex("unidad"));
					lista.arreglo_4[x] = cursor.getString(cursor.getColumnIndex("moneda"));
					lista.arreglo_5[x] = cursor.getString(cursor.getColumnIndex("cantidad"));
					lista.arreglo_6[x] = cursor.getString(cursor.getColumnIndex("observaciones"));
					lista.arreglo_7[x] = cursor.getString(cursor.getColumnIndex("id"));
					lista.arreglo_8[x] = cursor.getString(cursor.getColumnIndex("por_requerir"));
					lista.arreglo_9[x] = cursor.getString(cursor.getColumnIndex("id_explosion_ins"));

                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        db.close();
        return lista;
    }

	public consultas DAO_Select_insumos_requi(String id_req){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor2 = null;

		cursor = db.rawQuery("SELECT codigo, descrip, unidad, moneda,cantidad,observaciones,por_requerir,id_insumo " +
				"from insumos_requi where id_requi='"+id_req+"' " +
				"ORDER BY codigo", null);


		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];
		lista.arreglo2 = new String[cursor.getCount()];
		lista.arreglo_3 = new String[cursor.getCount()];
		lista.arreglo_4 = new String[cursor.getCount()];
		lista.arreglo_5 = new String[cursor.getCount()];
		lista.arreglo_6 = new String[cursor.getCount()];
		lista.arreglo_7 = new String[cursor.getCount()];
		lista.arreglo_9 = new String[cursor.getCount()];


		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {

					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("codigo"));
					lista.arreglo2[x] = cursor.getString(cursor.getColumnIndex("descrip"));
					lista.arreglo_3[x] = cursor.getString(cursor.getColumnIndex("unidad"));
					lista.arreglo_4[x] = cursor.getString(cursor.getColumnIndex("moneda"));
					lista.arreglo_5[x] = cursor.getString(cursor.getColumnIndex("cantidad"));
					lista.arreglo_6[x] = cursor.getString(cursor.getColumnIndex("observaciones"));
					lista.arreglo_7[x] = cursor.getString(cursor.getColumnIndex("por_requerir"));
					lista.arreglo_9[x] = cursor.getString(cursor.getColumnIndex("id_insumo"));

					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista;
	}

	public void DAO_Lista_preguntas_respuestas_temp(String id_req, String id_proyecto){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;

		db.delete("insumos_temporal", null, null);

		cursor = db.rawQuery("SELECT codigo, descrip, unidad, moneda,cantidad,observaciones,por_requerir,id_insumo,id_explosion_ins " +
				"from insumos_requi where id_requi='"+id_req+"' and id_proyecto='"+id_proyecto+"' " +
				"ORDER BY codigo", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {
					ContentValues cv = new ContentValues();
					cv.put("id", cursor.getString(cursor.getColumnIndex("id_insumo")));
					cv.put("codigo", cursor.getString(cursor.getColumnIndex("codigo")));
					cv.put("descrip", cursor.getString(cursor.getColumnIndex("descrip")));
					cv.put("unidad", cursor.getString(cursor.getColumnIndex("unidad")));
					cv.put("moneda", cursor.getString(cursor.getColumnIndex("moneda")));
					cv.put("cantidad", cursor.getString(cursor.getColumnIndex("cantidad")));
					cv.put("observaciones", cursor.getString(cursor.getColumnIndex("observaciones")));
					cv.put("por_requerir", cursor.getString(cursor.getColumnIndex("por_requerir")));
					cv.put("id_explosion_ins", cursor.getString(cursor.getColumnIndex("id_explosion_ins")));

					db.insert("insumos_temporal", null, cv);
					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
	}

	public String DAO_llena_sp_from_requi(String id_requi,String campo_tabla){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String valor="";

		cursor = db.rawQuery("SELECT "+campo_tabla+" from requisiciones  WHERE id='" + id_requi + "' ", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				valor = cursor.getString(cursor.getColumnIndex(campo_tabla));

			}
		}
		cursor.close();
		db.close();
		return valor;
	}

	public String DAO_contrasena(){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String valor="";

		Random rand = new Random();

		int randomNum = rand.nextInt((25 - 1) + 1) + 1;

		cursor = db.rawQuery("SELECT c_generada from c_admin  WHERE id='" + randomNum + "' ", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				valor = cursor.getString(cursor.getColumnIndex("c_generada"));
			}
		}
		cursor.close();
		db.close();
		return valor;
	}

	public String DAO_compara_contrasena(String c_generada){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String valor="";


		cursor = db.rawQuery("SELECT c_equivalente from c_admin  WHERE c_generada='" + c_generada + "' ", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				valor = cursor.getString(cursor.getColumnIndex("c_equivalente"));
			}
		}
		cursor.close();
		db.close();
		return valor;
	}

	public consultas DAO_Lista_insumos(String id_proyecto){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor2 = null;

		cursor = db.rawQuery("SELECT codigo, descrip, unidad, moneda,cantidad,observaciones,por_requerir,cantidad_total,id from insumos " +
				"WHERE id_proyecto='"+id_proyecto+"' ORDER BY codigo", null);


		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];
		lista.arreglo2 = new String[cursor.getCount()];
		lista.arreglo_3 = new String[cursor.getCount()];
		lista.arreglo_4 = new String[cursor.getCount()];
		lista.arreglo_5 = new String[cursor.getCount()];
		lista.arreglo_6 = new String[cursor.getCount()];
		lista.arreglo_7 = new String[cursor.getCount()];
		lista.arreglo_8 = new String[cursor.getCount()];
		lista.arreglo_9 = new String[cursor.getCount()];


		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {

					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("codigo"));
					lista.arreglo2[x] = cursor.getString(cursor.getColumnIndex("descrip"));
					lista.arreglo_3[x] = cursor.getString(cursor.getColumnIndex("unidad"));
					lista.arreglo_4[x] = cursor.getString(cursor.getColumnIndex("moneda"));
					lista.arreglo_5[x] = cursor.getString(cursor.getColumnIndex("cantidad"));
					lista.arreglo_6[x] = cursor.getString(cursor.getColumnIndex("observaciones"));
					lista.arreglo_7[x] = cursor.getString(cursor.getColumnIndex("por_requerir"));
					lista.arreglo_8[x] = cursor.getString(cursor.getColumnIndex("cantidad_total"));
					lista.arreglo_9[x] = cursor.getString(cursor.getColumnIndex("id"));

					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista;
	}

	public consultas DAO_Lista_insumos_all(){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor2 = null;

		cursor = db.rawQuery("SELECT codigo, descrip, unidad, moneda,cantidad,observaciones,por_requerir,cantidad_total,id from insumos " +
				"ORDER BY codigo", null);


		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];
		lista.arreglo2 = new String[cursor.getCount()];
		lista.arreglo_3 = new String[cursor.getCount()];
		lista.arreglo_4 = new String[cursor.getCount()];
		lista.arreglo_5 = new String[cursor.getCount()];
		lista.arreglo_6 = new String[cursor.getCount()];
		lista.arreglo_7 = new String[cursor.getCount()];
		lista.arreglo_8 = new String[cursor.getCount()];
		lista.arreglo_9 = new String[cursor.getCount()];


		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {

					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("codigo"));
					lista.arreglo2[x] = cursor.getString(cursor.getColumnIndex("descrip"));
					lista.arreglo_3[x] = cursor.getString(cursor.getColumnIndex("unidad"));
					lista.arreglo_4[x] = cursor.getString(cursor.getColumnIndex("moneda"));
					lista.arreglo_5[x] = cursor.getString(cursor.getColumnIndex("cantidad"));
					lista.arreglo_6[x] = cursor.getString(cursor.getColumnIndex("observaciones"));
					lista.arreglo_7[x] = cursor.getString(cursor.getColumnIndex("por_requerir"));
					lista.arreglo_8[x] = cursor.getString(cursor.getColumnIndex("cantidad_total"));
					lista.arreglo_9[x] = cursor.getString(cursor.getColumnIndex("id"));

					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista;
	}

	public consultas DAO_Lista_insumos_popup(String id_proyecto){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor2 = null;

		cursor = db.rawQuery("SELECT codigo, descrip, unidad, moneda,cantidad,observaciones,por_requerir,cantidad_total,id,id_explosion_ins from insumos " +
				"where no_mostrar_en_req='0' and id_proyecto='"+id_proyecto+"' " +
				"ORDER BY codigo", null);


		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];
		lista.arreglo2 = new String[cursor.getCount()];
		lista.arreglo_3 = new String[cursor.getCount()];
		lista.arreglo_4 = new String[cursor.getCount()];
		lista.arreglo_5 = new String[cursor.getCount()];
		lista.arreglo_6 = new String[cursor.getCount()];
		lista.arreglo_7 = new String[cursor.getCount()];
		lista.arreglo_8 = new String[cursor.getCount()];
		lista.arreglo_9 = new String[cursor.getCount()];
		lista.arreglo_10 = new String[cursor.getCount()];

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {

					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("codigo"));
					lista.arreglo2[x] = cursor.getString(cursor.getColumnIndex("descrip"));
					lista.arreglo_3[x] = cursor.getString(cursor.getColumnIndex("unidad"));
					lista.arreglo_4[x] = cursor.getString(cursor.getColumnIndex("moneda"));
					lista.arreglo_5[x] = cursor.getString(cursor.getColumnIndex("cantidad"));
					lista.arreglo_6[x] = cursor.getString(cursor.getColumnIndex("observaciones"));
					lista.arreglo_7[x] = cursor.getString(cursor.getColumnIndex("por_requerir"));
					lista.arreglo_8[x] = cursor.getString(cursor.getColumnIndex("cantidad_total"));
					lista.arreglo_9[x] = cursor.getString(cursor.getColumnIndex("id"));
					lista.arreglo_10[x] = cursor.getString(cursor.getColumnIndex("id_explosion_ins"));

					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista;
	}

	public consultas DAO_Lista_preguntas_respuestas_elimina_respuestas(String ubicacion, String contrato, String estacion, String modelo){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		contrato=contrato.trim();
		ubicacion=ubicacion.trim();
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		cursor = db.rawQuery("SELECT p.pregunta as pregunta,pr.is_active as is_active from preguntas p  LEFT JOIN  preg_respondidas pr on " +
				" p.id_estacion=pr.id_estacion " +
				"and p.id=pr.id_pregunta " +
				"AND pr.ubicacion = '"+ubicacion+"' " +
				"AND pr.contrato = '"+contrato+"' " +
				"AND pr.modelo=P.modelo " +
				"AND p.is_Active='1' " +
				"WHERE P.id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1) " +
				//"WHERE P.id_estacion='4' " +
				"AND p.modelo = '"+modelo+"' " +
				"ORDER BY CAST(p.orden as int) asc", null);

		/*cursor = db.rawQuery("SELECT p.pregunta as pregunta,pr.is_active as is_active from preguntas p  LEFT JOIN  preg_respondidas pr on " +
				" p.id_estacion=pr.id_estacion " +
				"and p.orden=pr.orden " +
				"AND pr.ubicacion='2010100601' " +
				"AND pr.contrato='103378' " +
				"AND pr.modelo=P.modelo " +
				"WHERE P.id_estacion='PR0010' " +
				"AND p.modelo='AVANTE' " +
				"ORDER BY CAST(p.orden as int) asc", null);*/

		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];
		lista.arreglo_int = new Integer[cursor.getCount()];

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {

					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("pregunta"));
					if(cursor.getString(cursor.getColumnIndex("is_active"))==null || cursor.getString(cursor.getColumnIndex("is_active")).contentEquals("false")){
						lista.arreglo_int[x] = 99999;
					}else{
						//lista.arreglo_int[x] = cursor.getString(cursor.getColumnIndex("is_active"));
						lista.arreglo_int[x]=x;
					}
					cursor.moveToNext();
				}
			}
			else{
				lista.arreglo = new String[1];
				lista.arreglo_int = new Integer[1];

				cursor = db.rawQuery("SELECT pregunta from preguntas where id='1' ", null);
				if(cursor.moveToFirst()){
					lista.arreglo[0]= cursor.getString(cursor.getColumnIndex("pregunta"));
					//lista.arreglo_int[0] = 99999;
				}


				if(Variables_Universales.getEstacion_completa()){
					lista.arreglo_int[0] = 0;
				}else{
					lista.arreglo_int[0]=99999;
				}



				//lista.arreglo[0]="¿Se cumplió con el objetivo de calidad de la estación?";

			}
		}
		cursor.close();
		db.close();
		return lista;
	}

	public String[] DAO_Spinner_Frente(){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String id_desarrollo="";

       /* cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+des+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}*/

		cursor = db.rawQuery("select DISTINCT clave from desarrollo order by id_desarrollo", null);
		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {
					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("clave"));
					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

	public String DAO_Proyecto_descrip(String clave){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String proyecto="";

		cursor = db.rawQuery("select DISTINCT desarrollo from desarrollo where clave='"+clave+"' ", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				proyecto = cursor.getString(cursor.getColumnIndex("desarrollo"));

			}
		}
		cursor.close();
		db.close();
		return proyecto;
	}

	public String[] DAO_Spinner_Responsables(){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String id_desarrollo="";

		cursor = db.rawQuery("select nombre from Responsables order by id", null);
		consultas lista = new consultas();
		//lista.arreglo = new String[cursor.getCount()];
		lista.arreglo = new String[cursor.getCount()+1];
		lista.arreglo[0] = "";

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 1; x <= cursor.getCount(); x++) {
					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("nombre"));
					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

	public String[] DAO_Spinner_Requi(String proyecto){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor = db.rawQuery("select folio_requi from requisiciones where proyecto='"+proyecto+"' order by CAST(id_auto AS INTEGER)", null);
		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()+1];
		lista.arreglo[0] = "Nueva";
		if (cursor != null) {
			if (cursor.moveToFirst()) {

				for (int x = 1; x <= cursor.getCount(); x++) {
					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("folio_requi"));
					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

	public String DAO_Select_id_from_folio(String proyecto,String folio){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor = db.rawQuery("select id from requisiciones where proyecto='"+proyecto+"' and folio_requi='"+folio+"' ", null);
		String id="";
		if (cursor != null) {
			if (cursor.moveToFirst()) {

					id = cursor.getString(cursor.getColumnIndex("id"));
			}
			else{
			    id="Nueva";
            }
		}
		cursor.close();
		db.close();
		return id;
	}

	public String[] DAO_Spinner_Desarrollo(int id_usuario){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String u;

		//////////////////////////////////////////////////

			cursor = db.rawQuery("SELECT * from desarrollo", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {
					u = cursor.getString(cursor.getColumnIndex("desarrollo"));
					Log.i("Select","Desarrollo"+u);
					u = cursor.getString(cursor.getColumnIndex("clave"));
					Log.i("Select","clave"+u);
					u = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
					Log.i("Select","id_desarrollo"+u);
					cursor.moveToNext();
				}
			}
		}

		cursor = db.rawQuery("SELECT * from rel_usuarios_desarrollo", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {
					u = cursor.getString(cursor.getColumnIndex("id_usuario"));
					Log.i("Select     ","id_usuario    "+u);
					u = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
					Log.i("Select    ","id_desarrollo     "+u);
					u = cursor.getString(cursor.getColumnIndex("fecha_creacion"));
					Log.i("Select    ","fecha_creacion      "+u);
					cursor.moveToNext();
				}
			}
		}

		cursor = db.rawQuery("SELECT * from usuarios", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {
					u = cursor.getString(cursor.getColumnIndex("isActive"));
					Log.i("Select","isActive"+u);
					u = cursor.getString(cursor.getColumnIndex("id_usuario"));
					Log.i("Select","id_usuario"+u);
					u = cursor.getString(cursor.getColumnIndex("nombre"));
					Log.i("Select","nombre"+u);
					u = cursor.getString(cursor.getColumnIndex("contrasena"));
					Log.i("Select","contrasena"+u);
					u = cursor.getString(cursor.getColumnIndex("fecha_creacion"));
					Log.i("Select","fecha_creacion"+u);
					cursor.moveToNext();
				}
			}
		}

		//////////////////////////////////////////////////

		//select DISTINCT clave from desarrollo order by id_desarrollo

		if(id_usuario==999){
			cursor = db.rawQuery("SELECT DISTINCT(clave) as desarrollo from desarrollo ORDER BY desarrollo ASC", null);
		}
		else {
			cursor = db.rawQuery("SELECT DISTINCT(d.clave) as desarrollo from desarrollo d INNER JOIN rel_usuarios_desarrollo rud on rud.id_desarrollo=d.id_desarrollo WHERE rud.id_usuario='" + id_usuario + "' ORDER BY d.desarrollo asc", null);
		}
			consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {

					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("desarrollo"));
					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

	public String DAO_Desarrollo_Sync(String nombre){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;

		cursor = db.rawQuery("SELECT id_desarrollo from desarrollo  WHERE desarrollo='" + nombre + "' ", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				nombre = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
			}
		}
		cursor.close();
		db.close();
		return nombre;
	}

	public String DAO_Desarrollo_Sync_2(String nombre){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;

		cursor = db.rawQuery("SELECT id_desarrollo from desarrollo  WHERE clave='" + nombre + "' ", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				nombre = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
			}
		}
		cursor.close();
		db.close();
		return nombre;
	}

	public String[] DAO_Spinner_Manzana(String frente, String desarrollo){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String id_desarrollo="";

		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

		cursor = db.rawQuery("select DISTINCT manzana from sembrado  where frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by manzana", null);
		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getCount()];

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				for (int x = 0; x < cursor.getCount(); x++) {

					lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("manzana"));
					cursor.moveToNext();
				}
			}
		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

    public String[] DAO_Spinner_lote(String manzana, String frente, String desarrollo){

        DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
        db = myDbHelper.getWritableDatabase();
        cursor = null;
		String id_desarrollo="";

		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

        cursor = db.rawQuery("select DISTINCT lote from sembrado  where manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by lote", null);
        consultas lista = new consultas();
        lista.arreglo = new String[cursor.getCount()];

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                for (int x = 0; x < cursor.getCount(); x++) {

                    lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("lote"));
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        db.close();
        return lista.arreglo;
    }

    public String[] DAO_Spinner_Interior(String lote, String manzana, String frente, String desarrollo){

        DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
        db = myDbHelper.getWritableDatabase();
        cursor = null;
		String id_desarrollo="";

		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

        cursor = db.rawQuery("select DISTINCT interior from sembrado  where lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);
        consultas lista = new consultas();
        lista.arreglo = new String[cursor.getCount()];

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                for (int x = 0; x < cursor.getCount(); x++) {

                    lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("interior"));
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        db.close();
        return lista.arreglo;
    }

	public String[] DAO_Spinner_Contratista(String lote, String manzana, String frente, String desarrollo, String interior){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String id_desarrollo="";
		consultas lista = new consultas();

		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

		//cursor2 = db.rawQuery("select DISTINCT interior from sembrado  where lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);

		if(!interior.contentEquals("")) {
			//id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
			cursor = db.rawQuery("select DISTINCT contratista from sembrado  where interior='"+interior+"' and lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);
			//consultas lista = new consultas();
			lista.arreglo = new String[cursor.getCount()];
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					for (int x = 0; x < cursor.getCount(); x++) {

						lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("contratista"));
						cursor.moveToNext();
					}
				}
			}
		}
		else{
			cursor = db.rawQuery("select DISTINCT contratista from sembrado  where lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);
			//consultas lista = new consultas();
			lista.arreglo = new String[cursor.getCount()];
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					for (int x = 0; x < cursor.getCount(); x++) {

						lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("contratista"));
						cursor.moveToNext();
					}
				}
			}

		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

	public String[] DAO_Spinner_Contrato(String lote, String manzana, String frente, String desarrollo, String interior, String contratista){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String id_desarrollo="";
		consultas lista = new consultas();

		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

		//cursor2 = db.rawQuery("select DISTINCT interior from sembrado  where lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);

		if(!interior.contentEquals("")) {
			//id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
			cursor = db.rawQuery("select DISTINCT contrato from sembrado  where contratista='"+contratista+"' and interior='"+interior+"' and lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);
			//consultas lista = new consultas();
			lista.arreglo = new String[cursor.getCount()];
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					for (int x = 0; x < cursor.getCount(); x++) {

						lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("contrato"));
						cursor.moveToNext();
					}
				}
			}
		}
		else{
			cursor = db.rawQuery("select DISTINCT contrato from sembrado  where contratista='"+contratista+"' and lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);
			//consultas lista = new consultas();
			lista.arreglo = new String[cursor.getCount()];
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					for (int x = 0; x < cursor.getCount(); x++) {

						lista.arreglo[x] = cursor.getString(cursor.getColumnIndex("contrato"));
						cursor.moveToNext();
					}
				}
			}

		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

	public String[] DAO_Select_Preg_Detalle(String ubicacion, String estacion, String modelo, String contrato, String contratista){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;

		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		cursor = db.rawQuery("SELECT observaciones,calificacion FROM preg_resp_detalle WHERE ubicacion='"+ubicacion+"' and id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) and modelo='"+modelo+"' and contrato='"+contrato+"' ", null);
		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getColumnCount()];

		if (cursor != null) {
			if (cursor.moveToFirst()) {
					lista.arreglo[0] = cursor.getString(cursor.getColumnIndex("observaciones"));
					lista.arreglo[1] = cursor.getString(cursor.getColumnIndex("calificacion"));
			}
		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

	public boolean DAO_estaciones_completa(String ubicacion, String estacion, String contrato, String contratista){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		Boolean resp;
		String val="";//,id_contrato="",id_contratista="",id_prototipo="";
		//contrato=contrato.trim();
		//ubicacion=ubicacion.trim();
		cursor = null;
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		//cursor = db.rawQuery("SELECT id_contrato FROM sembrado WHERE ubicacion = '"+ubicacion+"'  AND contrato='"+contrato+"' AND contratista='"+contratista+"' ", null);
		//cursor2 = db.rawQuery("SELECT id_contratista FROM sembrado WHERE ubicacion = '"+ubicacion+"'  AND contrato='"+contrato+"' AND contratista='"+contratista+"' ", null);
		//cursor3 = db.rawQuery("SELECT id_prototipo FROM sembrado WHERE ubicacion = '"+ubicacion+"'  AND contrato='"+contrato+"' AND contratista='"+contratista+"' ", null);

		/*if (cursor.moveToFirst()) {
			id_contrato=cursor.getString(cursor.getColumnIndex("id_contrato"));
			Variables_Universales.setId_contrato(id_contrato);
		}
		if (cursor2.moveToFirst()) {
			id_contratista=cursor2.getString(cursor2.getColumnIndex("id_contratista"));
			Variables_Universales.setId_contratista(id_contratista);
		}*/
		/*if (cursor3.moveToFirst()) {
			id_prototipo=cursor3.getString(cursor3.getColumnIndex("id_prototipo"));
			Variables_Universales.setId_prototipo(id_prototipo);
		}*/

		//cursor = db.rawQuery("SELECT fecha_fin FROM estaciones WHERE ubicacion = '"+ubicacion+"'  AND descripcion like '"+first+"%' ", null);
		cursor = db.rawQuery("SELECT fecha_fin FROM estaciones WHERE ubicacion = '"+ubicacion+"' AND descripcion like '"+first+"%' AND contrato='"+contrato+"' AND contratista='"+contratista+"' ", null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				val=cursor.getString(cursor.getColumnIndex("fecha_fin"));
				if(val==null || val.isEmpty()){
					resp= false;
				}else{
				resp= true;
				}
			}
			else {resp= false;}
		}else {resp= false;}

		cursor.close();
		//cursor2.close();
		//cursor3.close();
		db.close();
		return resp;
	}

	public void DAO_set_valores(String ubicacion, String estacion, String contrato, String contratista){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		Boolean resp;
		String val="",id_contrato="",id_contratista="",id_prototipo="",id_estacion="";
		cursor = null;
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		cursor = db.rawQuery("SELECT id_contrato FROM sembrado WHERE ubicacion = '"+ubicacion+"'  AND contrato='"+contrato+"' AND contratista='"+contratista+"' ", null);
		cursor2 = db.rawQuery("SELECT id_contratista FROM sembrado WHERE ubicacion = '"+ubicacion+"'  AND contrato='"+contrato+"' AND contratista='"+contratista+"' ", null);
		cursor3 = db.rawQuery("SELECT id_prototipo FROM sembrado WHERE ubicacion = '"+ubicacion+"'  AND contrato='"+contrato+"' AND contratista='"+contratista+"' ", null);



		if (cursor.moveToFirst()) {
			id_contrato=cursor.getString(cursor.getColumnIndex("id_contrato"));
			Variables_Universales.setId_contrato(id_contrato);
		}
		if (cursor2.moveToFirst()) {
			id_contratista=cursor2.getString(cursor2.getColumnIndex("id_contratista"));
			Variables_Universales.setId_contratista(id_contratista);
		}
		if (cursor3.moveToFirst()) {
			id_prototipo=cursor3.getString(cursor3.getColumnIndex("id_prototipo"));
			Variables_Universales.setId_prototipo(id_prototipo);
		}
		cursor4 = db.rawQuery("SELECT id_estacion from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+id_contrato+"' and contratista='"+id_contratista+"' ", null);

		if (cursor4.moveToFirst()) {
			id_estacion=cursor4.getString(cursor4.getColumnIndex("id_estacion"));
			Variables_Universales.setId_estacion(id_estacion);
		}
		cursor.close();
		cursor2.close();
		cursor3.close();
		db.close();
	}



	public String DAO_Modelo(String lote, String manzana, String frente, String desarrollo, String contratista, String contrato, String interior){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String val="";
		String id_desarrollo="";
		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

		if(!interior.contentEquals("")){
			cursor = db.rawQuery("select modelo from sembrado  where contratista='"+contratista+"' and contrato='"+contrato+"' and interior='"+interior+"' and lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					val=cursor.getString(cursor.getColumnIndex("modelo"));
				}
			}
		}
		else{
			cursor = db.rawQuery("select modelo from sembrado  where contratista='"+contratista+"' and contrato='"+contrato+"' and lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					val=cursor.getString(cursor.getColumnIndex("modelo"));
				}
			}

		}
		cursor.close();
		db.close();
		return val;
	}

	public String DAO_Ubicacion(String lote, String manzana, String frente, String desarrollo, String contratista, String contrato, String interior){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String val="";
		String id_desarrollo="";

		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

		if(!interior.contentEquals("")){
			cursor = db.rawQuery("select ubicacion from sembrado  where contratista='"+contratista+"' and contrato='"+contrato+"' and interior='"+interior+"' and lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"'", null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					val=cursor.getString(cursor.getColumnIndex("ubicacion"));
				}
			}
		}
		else{
			cursor = db.rawQuery("select ubicacion from sembrado  where contratista='"+contratista+"' and contrato='"+contrato+"' and  lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"'", null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					val=cursor.getString(cursor.getColumnIndex("ubicacion"));
				}
			}
		}
		cursor.close();
		db.close();
		return val;
	}


	public String DAO_Contrato(String lote, String manzana, String frente, String desarrollo){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String val="";

		String id_desarrollo="";

		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

		cursor = db.rawQuery("select contrato from sembrado  where lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);


		if (cursor != null) {
			if (cursor.moveToFirst()) {
				val=cursor.getString(cursor.getColumnIndex("contrato"));
			}
		}

		cursor.close();
		db.close();
		return val;
	}

	public String DAO_Contratista(String lote, String manzana, String frente, String desarrollo){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		String val="";

		String id_desarrollo="";

		cursor = db.rawQuery("select DISTINCT id_desarrollo from desarrollo  where desarrollo = '"+desarrollo+"' ", null);

		if(cursor.moveToFirst()) {
			id_desarrollo = cursor.getString(cursor.getColumnIndex("id_desarrollo"));
		}

		cursor = db.rawQuery("select contratista from sembrado  where lote='"+lote+"' and manzana='"+manzana+"' and frente='"+frente+"' and fracc = '"+id_desarrollo+"' order by interior", null);


		if (cursor != null) {
			if (cursor.moveToFirst()) {
				val=cursor.getString(cursor.getColumnIndex("contratista"));
			}
		}

		cursor.close();
		db.close();
		return val;
	}

	public consultas DAO_llena_QR(String contrato){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		//AbreDB();
		db = myDbHelper.getWritableDatabase();
		cursor = null;

		cursor = db.rawQuery("select frente,manzana,lote,interior from sembrado  where contrato = '"+contrato+"' ", null);
		consultas lista = new consultas();

		if (cursor != null) {
			if (cursor.moveToFirst()) {
					lista.frente = cursor.getString(cursor.getColumnIndex("frente"));
					lista.manzana = cursor.getString(cursor.getColumnIndex("manzana"));
					lista.lote = cursor.getString(cursor.getColumnIndex("lote"));
					lista.interior = cursor.getString(cursor.getColumnIndex("interior"));
			}
		}
		cursor.close();
		db.close();
		return lista;
	}

	public void DAO_Actualiza_fecha_fin(String ubicacion, String estacion, String contrato, String contratista) throws ParseException {
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		int dias_diff=0;
		String fecha_ini="",fecha_fin="",verifica="";
		Calendar c = Calendar.getInstance();
		//SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String hoyConFormato = format.format(c.getTime());
		contrato=contrato.trim();
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

			ContentValues cv = new ContentValues();
			cv.put("fecha_fin",hoyConFormato);
			db.update("estaciones",cv,"ubicacion='"+ubicacion+"' AND descripcion like '"+first+"%'  and contrato='"+contrato+"' and contratista='"+contratista+"' ",null);

			db.close();
	}

	public void DAO_Guarda_Respuesta(String ubicacion, String estacion, String modelo, String pregunta, String is_active, String contrato, String desarrollo, String contratista){

		cursor = null;
		db = myDbHelper.getWritableDatabase();
		contrato=contrato.trim();

		try {

			//String VALUE = DatabaseUtils.sqlEscapeString(pregunta);

			//String stringToStore = Normalizer.normalize(pregunta.toLowerCase(), Normalizer.Form.NFC);
			StringTokenizer tokens = new StringTokenizer(estacion, "'");
			String first = tokens.nextToken();

			cursor = db.rawQuery("SELECT ubicacion FROM preg_respondidas where ubicacion='"+ubicacion+"' " +
					"AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) " +
					"AND modelo='"+modelo+"' " +
					"AND id_pregunta=(select id from preguntas where pregunta = '"+pregunta+"' and id_modelo='"+modelo+"' AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1)) " +
					"AND contrato = '"+contrato+"' ", null);

			/*cursor = db.rawQuery("SELECT ubicacion FROM preg_respondidas where ubicacion='"+ubicacion+"' " +
					"AND id_estacion=(SELECT DISTINCT trim(id_estacion) from estaciones where descripcion like '"+estacion+"%') " +
					"AND modelo='"+modelo+"' AND id_pregunta=(select id from preguntas where pregunta like '"+pregunta+"%' and " +
					"modelo='"+modelo+"') AND contrato like '"+contrato+"%' ", null);*/

			/*cursor = db.rawQuery("SELECT ubicacion FROM preg_respondidas where ubicacion='"+ubicacion+"' " +
					"AND id_estacion=(SELECT DISTINCT trim(id_estacion) from estaciones where descripcion like '"+estacion+"%') " +
					"AND modelo='"+modelo+"' AND id_pregunta=(select id from preguntas where pregunta like '"+pregunta+"%' and " +
					"modelo='"+modelo+"') AND contrato='"+contrato+"' ", null);*/


			if (cursor.moveToPosition(0)) {
				ContentValues cv = new ContentValues();
				cv.put("is_active",is_active);
/*
				db.update("preg_respondidas", cv, "ubicacion='"+ubicacion+"' AND id_estacion=(SELECT DISTINCT trim(id_estacion) from estaciones where descripcion like '"+estacion+"%') AND modelo='"+modelo+"' AND id_pregunta=(select id from preguntas where pregunta like '"+pregunta+"%' and modelo='"+modelo+"' AND id_estacion=(SELECT DISTINCT trim(id_estacion) from estaciones where descripcion like '"+estacion+"%') AND contrato like '"+contrato+"%' ", null);
*/
				db.update("preg_respondidas", cv, "ubicacion='"+ubicacion+"' " +
						"AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) " +
						"AND modelo='"+modelo+"' " +
						"AND id_pregunta=(select id from preguntas where pregunta = '"+pregunta+"' and id_modelo='"+modelo+"' AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1)) " +
						"AND contrato = '"+contrato+"' ", null);

				cursor.close();
				db.close();

			} else {

				cursor = db.rawQuery("SELECT ubicacion FROM preg_respondidas where ubicacion='" + ubicacion + "' " +
						"AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '" + first + "%' and ubicacion='" + ubicacion + "' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) " +
						"AND modelo='" + modelo + "' " +
						"AND id_pregunta is NULL " +
						"AND contrato = '" + contrato + "' ", null);
				if (cursor.moveToPosition(0)) {
					ContentValues cv = new ContentValues();
					cv.put("is_active", is_active);
/*
				db.update("preg_respondidas", cv, "ubicacion='"+ubicacion+"' AND id_estacion=(SELECT DISTINCT trim(id_estacion) from estaciones where descripcion like '"+estacion+"%') AND modelo='"+modelo+"' AND id_pregunta=(select id from preguntas where pregunta like '"+pregunta+"%' and modelo='"+modelo+"' AND id_estacion=(SELECT DISTINCT trim(id_estacion) from estaciones where descripcion like '"+estacion+"%') AND contrato like '"+contrato+"%' ", null);
*/
					db.update("preg_respondidas", cv, "ubicacion='" + ubicacion + "' " +
							"AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '" + first + "%' and ubicacion='" + ubicacion + "' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) " +
							"AND modelo='" + modelo + "' " +
							"AND id_pregunta is NULL " +
							"AND contrato = '" + contrato + "' ", null);

					cursor.close();
					db.close();

				} else{

					db.execSQL("INSERT INTO preg_respondidas (ubicacion, id_estacion, modelo, id_pregunta,is_active,contrato,desarrollo) " +
							"VALUES ('" + ubicacion + "',(SELECT (id_estacion) from estaciones where descripcion like '" + first + "%' and ubicacion='" + ubicacion + "' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1)," +
							"'" + modelo + "',(select id from preguntas where pregunta = '" + pregunta + "' and id_modelo='" + modelo + "' AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '" + first + "%' and ubicacion='" + ubicacion + "' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1))," +
							"'" + is_active + "','" + contrato + "','" + desarrollo + "')");


				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String hoyConFormato = df.format(c.getTime());


				ContentValues cv = new ContentValues();
				cv.put("fecha_inicio", hoyConFormato);
				db.update("estaciones", cv, "ubicacion='" + ubicacion + "' AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '" + first + "%' and ubicacion='" + ubicacion + "' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) ", null);

				cursor.close();
				db.close();
			}
			}

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){

			}

		}
	}

	public void DAO_Guarda_Insumos_temporal(String id,String s_codigo,String s_descripcion,String s_unidad,String s_moneda,String s_por_requerir,
											String s_total_global,String id_explosion_ins){
		cursor = null;
		db = myDbHelper.getWritableDatabase();

		try {

			cursor = db.rawQuery("SELECT id FROM insumos_temporal where id='"+id+"'  ", null);
			if (cursor.moveToPosition(0)) {
				cursor.close();
				db.close();
			}
			else{
				ContentValues cv = new ContentValues();
				cv.put("id", id);  //Colocar todos los campos a insertar
				cv.put("codigo", s_codigo);
				cv.put("descrip", s_descripcion);
				cv.put("unidad", s_unidad);
				cv.put("moneda", s_moneda);
				cv.put("cantidad", s_por_requerir);
				cv.put("id_explosion_ins", id_explosion_ins);
				//cv.put("observaciones", s_total_global);

				db.insert("insumos_temporal", null, cv);
				cursor.close();
				db.close();
			}

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
		}
	}

	public void DAO_Elimina_Insumos_temporal(String id){
		cursor = null;
		db = myDbHelper.getWritableDatabase();

		try {

			cursor = db.rawQuery("SELECT id FROM insumos_temporal where id='"+id+"'  ", null);
			if (cursor.moveToPosition(0)) {
				db.delete("insumos_temporal","id='"+id+"'",null);
				cursor.close();
				db.close();
			}
			else{
				cursor.close();
				db.close();
			}

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
		}
	}

	public void DAO_Guarda_Insumos_temporal_cant_observaciones(String[] id,String[] s_por_requerir,String[] observaciones){

		try {

			for(int i = 0;i<id.length;i++){
				cursor = null;
				db = myDbHelper.getWritableDatabase();
				cursor = db.rawQuery("SELECT id FROM insumos_temporal where id='"+id[i]+"'  ", null);
				if (cursor.moveToPosition(0)) {
					ContentValues cv = new ContentValues();

					cv.put("por_requerir", s_por_requerir[i]);
					cv.put("observaciones", observaciones[i]);

					db.update("insumos_temporal",cv,"id='"+id[i]+"'",null);
					cursor.close();
					db.close();
				}
				else{

					cursor.close();
					db.close();
				}
			}

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
		}
	}

	public consultas DAO_Select_Insumo_Checked(int tam){
		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor=db.rawQuery("SELECT id from insumos_temporal",null);
		consultas lista = new consultas();
		//lista.a_select = new String[cursor.getCount()];
		lista.a_select = new String[tam];



		if (cursor != null) {
				if (cursor.moveToFirst()) {
					for (int x = 0; x < cursor.getCount(); x++) {
						lista.a_select[x] = cursor.getString(cursor.getColumnIndex("id"));
						cursor.moveToNext();
					}
				}

			int i=0;
			//cursor.getColumnCount();
			for(i=cursor.getCount();i<tam;i++) {
				lista.a_select[i] = "0";
			}

			}


		cursor.close();
		db.close();
		return lista;
	}

	public boolean DAO_Guarda_Actualiza_Insumos_Requisicion(String id,String id_requi,String s_codigo,String s_descripcion,String s_unidad,
															String s_moneda,String s_por_requerir,String s_total_global, String s_observ, String renglon,
															String id_proyecto,String id_explosion_ins){
		cursor = null;
		cursor2 = null;
		db = myDbHelper.getWritableDatabase();
		double total=0,total_anterior=0,por_requerir=0,por_requerir_anterior=0;

		ContentValues cv = new ContentValues();
		cv.put("codigo", s_codigo);
		cv.put("descrip", s_descripcion);
		cv.put("unidad", s_unidad);
		cv.put("moneda", s_moneda);
		cv.put("cantidad", s_total_global);
		cv.put("por_requerir", s_por_requerir);
		cv.put("observaciones", s_observ);
		cv.put("renglon", renglon);
		cv.put("id_proyecto", id_proyecto);
		cv.put("id_insumo", id);
		cv.put("id_requi", id_requi);
		cv.put("id_explosion_ins", id_explosion_ins);

		total=Double.parseDouble(s_total_global);
		por_requerir=Double.parseDouble(s_por_requerir);

		//db.delete("insumos_requi", "id_requi='"+id_requi+"' and id_proyecto='"+id_proyecto+"' ", null);


		try {
			cursor = db.rawQuery("SELECT por_requerir FROM insumos_requi " +
					"where id_requi='"+id_requi+"' and id_insumo='"+id+"' and id_proyecto='"+id_proyecto+"'  ", null);
			cursor2 = db.rawQuery("SELECT por_requerir FROM insumos " +
					"where id='"+id+"' and id_proyecto='"+id_proyecto+"' and id_explosion_ins='"+id_explosion_ins+"' ", null);
			if (cursor.moveToPosition(0) && cursor2.moveToPosition(0)) {
				ContentValues cv2 = new ContentValues();

				por_requerir_anterior=Double.parseDouble(cursor.getString(cursor.getColumnIndex("por_requerir")));
				total_anterior=Double.parseDouble(cursor2.getString(cursor2.getColumnIndex("por_requerir")));

				total=por_requerir_anterior-por_requerir+total_anterior;

				cv2.put("por_requerir", (double)Math.round(total * 10000d) / 10000d);


				db.update("insumos",cv2,"id='"+id+"' and id_explosion_ins='"+id_explosion_ins+"'",null);
				db.update("insumos_requi", cv, "id_requi='"+id_requi+"' and id_insumo='"+id+"' and id_proyecto='"+id_proyecto+"'", null);
				cursor.close();
				db.close();
			}
			else{
				total=total-por_requerir;
				ContentValues cv2 = new ContentValues();
				cv2.put("por_requerir", total);

				db.insert("insumos_requi", null, cv);
				db.update("insumos",cv2,"id='"+id+"' and id_explosion_ins='"+id_explosion_ins+"'",null);
				cursor.close();
				db.close();
			}
			return true;

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_Guarda_Actualiza_Insumos_Requisicion2(String id_requi,String id_proyecto){
		db = myDbHelper.getWritableDatabase();

		try {

			db.delete("insumos_requi", "id_requi='"+id_requi+"' and id_proyecto='"+id_proyecto+"' ", null);

			db.close();
			return true;

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_Update_insumos_e_insumosRequi(String id_insumo,String id_explosion_insumo,String proyecto,String cant_por_req_global,String cant_total){
		cursor = null;
		cursor2 = null;
		db = myDbHelper.getWritableDatabase();
		double total=0,total_anterior=0,por_requerir=0,por_requerir_anterior=0;

		ContentValues cv = new ContentValues();
		ContentValues cv2 = new ContentValues();

		cv.put("por_requerir", cant_por_req_global);
		cv.put("cantidad_total", cant_total);

		cv2.put("cantidad", cant_total);

		try {
				db.update("insumos",cv,"id='"+id_insumo+"' and id_explosion_ins='"+id_explosion_insumo+"' and id_proyecto='"+proyecto+"'",null);
				db.update("insumos_requi", cv2, "id_explosion_ins='"+id_explosion_insumo+"' and id_insumo='"+id_insumo+"' and id_proyecto='"+proyecto+"'", null);
				cursor.close();
				db.close();

			return true;
		}
		catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_Update_ID_requi(String id_requi_UUID_Android, String folio_requi_from_server, String id_from_server){

		db = myDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		ContentValues cv2 = new ContentValues();

		cv.put("id", id_from_server);
		cv.put("is_sincronizado", "1");
		cv.put("folio_requi", folio_requi_from_server);
		cv2.put("id_requi", id_from_server);

		try {
			db.update("requisiciones",cv,"id='"+id_requi_UUID_Android+"' ",null);
			db.update("insumos_requi", cv2, "id_requi='"+id_requi_UUID_Android+"' ", null);
			db.close();
			return true;
		}
		catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_Sincro_Insumos_Requisicion(String id,String id_requi,String s_codigo,String s_descripcion,String s_unidad,
															String s_moneda,String s_por_requerir,String s_total_global, String s_observ,
												  String renglon,String id_proyecto,String id_explosion_ins){
		cursor = null;
		db = myDbHelper.getWritableDatabase();
		double total=0,por_requerir=0;

		ContentValues cv = new ContentValues();
		cv.put("codigo", s_codigo);
		cv.put("descrip", s_descripcion);
		cv.put("unidad", s_unidad);
		cv.put("moneda", s_moneda);
		cv.put("cantidad", s_total_global);
		cv.put("por_requerir", s_por_requerir);
		cv.put("observaciones", s_observ);
		cv.put("renglon", renglon);
		cv.put("id_proyecto", id_proyecto);
		cv.put("id_explosion_ins", id_explosion_ins);

		total=Double.parseDouble(s_total_global);
		por_requerir=Double.parseDouble(s_por_requerir);

		try {
			cursor = db.rawQuery("SELECT id_requi FROM insumos_requi where id_requi='"+id_requi+"' and id_insumo='"+id+"' and id_proyecto='"+id_proyecto+"'  ", null);
			if (cursor.moveToPosition(0)) {
				//db.update("insumos_requi", cv, "id_requi='"+id_requi+"' and id_insumo='"+id+"'", null);
				cursor.close();
				db.close();
			}
			else{
				cv.put("id_insumo", id);
				cv.put("id_requi", id_requi);

				/*total=total-por_requerir;
				ContentValues cv2 = new ContentValues();
				cv2.put("por_requerir", total);*/

				db.insert("insumos_requi", null, cv);
				//db.update("insumos",cv2,"id='"+id+"'",null);
				cursor.close();
				db.close();
			}
			return true;

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_Sync_Insumos(String id,String s_codigo,String s_descripcion,String s_unidad,
									String s_moneda,String s_por_requerir,String s_total_global, String s_observ
			, String tipo, String bloqueado, String topar_cantidad, String es_inventariable, String es_administrativo,
									String no_mostrar_en_req, String id_proyecto,String id_explosion_ins){
		cursor = null;
		db = myDbHelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("codigo", s_codigo);
		cv.put("descrip", s_descripcion);
		cv.put("unidad", s_unidad);
		cv.put("moneda", s_moneda);
		cv.put("cantidad_total", s_total_global);
		cv.put("por_requerir", s_por_requerir);
		cv.put("observaciones", s_observ);

		cv.put("tipo", tipo);
		cv.put("bloqueado", bloqueado);
		cv.put("topar_cantidad", topar_cantidad);
		cv.put("es_inventariable", es_inventariable);
		cv.put("es_administrativo", es_administrativo);
		cv.put("no_mostrar_en_req", no_mostrar_en_req);
		cv.put("id_proyecto", id_proyecto);
		cv.put("id_explosion_ins", id_explosion_ins);

		try {
			cursor = db.rawQuery("SELECT id FROM insumos where id='"+id+"' and id_proyecto='"+id_proyecto+"'  ", null);
			if (cursor.moveToPosition(0)) {
				db.update("insumos", cv, "id='"+id+"'  and id_proyecto='"+id_proyecto+"'", null);
				cursor.close();
				db.close();
			}
			else{
				cv.put("id", id);
				db.insert("insumos", null, cv);
				cursor.close();
				db.close();
			}
			return true;

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_Guarda_Actualiza_Requisicion(String id,String proyecto,String solicitante,String fecha,String fecha_req,String codigo_aux,
													String contrato,String aut_tecnica,String aut_compra,String observ_requi,String lugar_entrega){
		cursor = null;
		db = myDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("proyecto", proyecto);
		cv.put("solicitante", solicitante);
		cv.put("fecha", fecha);
		cv.put("fecha_req", fecha_req);
		cv.put("codigo_aux", codigo_aux);
		cv.put("contrato", contrato);
		cv.put("aut_tecnica", aut_tecnica);
		cv.put("aut_compra", aut_compra);
		cv.put("observ_requi", observ_requi);
		cv.put("lugar_entrega", lugar_entrega);

		try {
			cursor = db.rawQuery("SELECT id FROM requisiciones where id='"+id+"' and proyecto='"+proyecto+"'    ", null);
			if (cursor.moveToPosition(0)) {
				//cv.put("is_sincronizado", "1");
				db.update("requisiciones", cv, "id='"+id+"' and proyecto='"+proyecto+"'   ", null);
				cursor.close();
				db.close();
			}
			else{
				cv.put("is_sincronizado", "0");
				cv.put("id", id);
				cv.put("folio_requi", id);
				db.insert("requisiciones", null, cv);
				cursor.close();
				db.close();
			}
			return true;

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_actualiza_compara_is_sincronizado(String id,String proyecto){
		cursor = null;
		db = myDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		//cv.put("is_sincronizado", proyecto);

		try {
			cursor = db.rawQuery("SELECT is_sincronizado FROM requisiciones where id='"+id+"' and proyecto='"+proyecto+"'  ", null);
			if (cursor.moveToPosition(0)) {
				if(cursor.getString(cursor.getColumnIndex("is_sincronizado")).contentEquals("0")) {
					//cv.put("is_sincronizado", "1");
					//db.update("requisiciones", cv, "id='" + id + "' and proyecto='"+proyecto+"' ", null);
					cursor.close();
					db.close();
					return true;
				}else return false;
			}
			else{
				cursor.close();
				db.close();
				return false;
			}


		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_actualiza__is_sincronizado(String id,String proyecto){ //Nuevo
		cursor = null;
		db = myDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		//cv.put("is_sincronizado", proyecto);

		try {
			cursor = db.rawQuery("SELECT is_sincronizado FROM requisiciones where id='"+id+"' and proyecto='"+proyecto+"'  ", null);
			if (cursor.moveToPosition(0)) {
				if(cursor.getString(cursor.getColumnIndex("is_sincronizado")).contentEquals("0")) {
					cv.put("is_sincronizado", "1");
					db.update("requisiciones", cv, "id='" + id + "' and proyecto='"+proyecto+"' ", null);
					cursor.close();
					db.close();
					return true;
				}else return false;
			}
			else{

				cursor.close();
				db.close();
				return false;
			}


		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}

	public boolean DAO_Sync_Requisicion(String id,String folio_requi,String proyecto,String solicitante,String fecha,String fecha_req,String codigo_aux,
													String contrato,String aut_tecnica,String aut_compra,String observ_requi,String lugar_entrega,
										String cancelada,String pedida_totalmente){
		cursor = null;
		db = myDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("proyecto", proyecto);
		cv.put("solicitante", solicitante);
		cv.put("fecha", fecha);
		cv.put("fecha_req", fecha_req);
		cv.put("codigo_aux", codigo_aux);
		cv.put("contrato", contrato);
		cv.put("aut_tecnica", aut_tecnica);
		cv.put("aut_compra", aut_compra);
		cv.put("observ_requi", observ_requi);
		cv.put("lugar_entrega", lugar_entrega);
		cv.put("cancelada", cancelada);
		cv.put("pedida_totalmente", pedida_totalmente);

		try {
			cursor = db.rawQuery("SELECT id FROM requisiciones where id='"+id+"'  ", null);
			if (cursor.moveToPosition(0)) {
				//db.update("requisiciones", cv, "id='"+id+"' ", null);
				cursor.close();
				db.close();
			}
			else{

				cv.put("id", id);
				cv.put("folio_requi", folio_requi);
				db.insert("requisiciones", null, cv);
				cursor.close();
				db.close();
			}
			return true;

		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){
			}
			return false;
		}
	}


	/****************    Consulta para Insert Estaciones     *************/
	public void DAO_Insert_Estaciones(String ubicacion, String contrato, String id_estacion
			, String dias_ejecucion, String descripcion, String fecha_inicio, String fecha_fin, String desarrollo, String idCentro, String idPresupuestoControl, String cantidad, String estimado, String contratista_est) {

		contrato=contrato.trim();
		db = myDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("ubicacion", ubicacion);
		cv.put("contrato", contrato);
		cv.put("id_estacion", id_estacion);
		cv.put("dias_ejecucion", dias_ejecucion);
		cv.put("descripcion", descripcion);
		cv.put("fecha_inicio", fecha_inicio);
		cv.put("fecha_fin", fecha_fin);
		cv.put("desarrollo", desarrollo);
		cv.put("idCentro", idCentro);
		cv.put("IdPresupuestoControl", idPresupuestoControl);
		cv.put("cantidad", cantidad);
		cv.put("estimado", estimado);
		cv.put("contratista", contratista_est);

		try {
			cursor = db.rawQuery("SELECT ubicacion FROM estaciones where ubicacion='"+ubicacion+"' " +
					"AND id_estacion='"+id_estacion+"' " +
					"AND contrato = '"+contrato+"' and contratista='"+contratista_est+"' ", null);

			if (cursor.moveToPosition(0)) {
				cursor.close();
				db.close();

			} else {
				db.insert("estaciones", null, cv);
				cursor.close();
				db.close();
			}
		}
		catch (Exception e) {
		}
	}

	/****************    Consulta para Insert Responsables      *************/
	public boolean DAO_Insert_Responsables(String id, String pregunta, String modelo, String orden) {

		db = myDbHelper.getWritableDatabase();

		try {
			cursor = db.rawQuery("SELECT id FROM Responsables where id='"+id+"' ", null);

			if (cursor.moveToPosition(0)) {
				/*ContentValues cv2 = new ContentValues();
				cv2.put("is_Active", is_Active);
				db.update("preguntas", cv2, "id='"+id+"' ", null);*/
				cursor.close();
				db.close();

			} else {
				ContentValues cv = new ContentValues();
				cv.put("id", id);
				cv.put("codigo", pregunta);
				cv.put("nombre", modelo);
				cv.put("cargo", orden);

				db.insert("Responsables", null, cv);
				cursor.close();
				db.close();
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/****************    Consulta para Insert Usuarios      *************/
	public boolean DAO_Insert_Usuarios(String isActive, String id_usuario, String nombre, String contrasena, String fecha_creacion) {

		db = myDbHelper.getWritableDatabase();

		try {
			cursor = db.rawQuery("SELECT id_usuario FROM usuarios where id_usuario='"+id_usuario+"' ", null);

			if (cursor.moveToPosition(0)) {
				ContentValues cv2 = new ContentValues();
				cv2.put("isActive", isActive);
				cv2.put("contrasena", contrasena);

				db.update("usuarios", cv2, "id_usuario='"+id_usuario+"' ", null);
				cursor.close();
				db.close();

			} else {
				ContentValues cv = new ContentValues();
				cv.put("isActive", isActive);
				cv.put("id_usuario", id_usuario);
				cv.put("nombre", nombre);
				cv.put("contrasena", contrasena);
				cv.put("fecha_creacion", fecha_creacion);

				db.insert("usuarios", null, cv);
				cursor.close();
				db.close();
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/****************    Consulta para Insert Desarrollos      *************/
	public boolean DAO_Insert_Desarrollo(String id_desarrollo, String clave, String desarrollo) {

		db = myDbHelper.getWritableDatabase();

		try {
			cursor = db.rawQuery("SELECT id_desarrollo FROM desarrollo where id_desarrollo='"+id_desarrollo+"' ", null);

			if (cursor.moveToPosition(0)) {
				/*ContentValues cv2 = new ContentValues();
				cv2.put("id_desarrollo", id_desarrollo);

				db.update("usuarios", cv2, "id_desarrollo='"+id_desarrollo+"' ", null);*/
				cursor.close();
				db.close();

			} else {
				ContentValues cv = new ContentValues();
				cv.put("id_desarrollo", id_desarrollo);
				cv.put("clave", clave);
				cv.put("desarrollo", desarrollo);

				db.insert("desarrollo", null, cv);
				cursor.close();
				db.close();
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/****************    Consulta para Insert Relacion User-Desarrollo      *************/
	public boolean DAO_Insert_Rel_User_Des(String id_usuario, String id_desarrollo, String fecha_creacion) {

		db = myDbHelper.getWritableDatabase();

		try {
			cursor = db.rawQuery("SELECT id_desarrollo FROM rel_usuarios_desarrollo where id_desarrollo='"+id_desarrollo+"' and id_usuario='"+id_usuario+"' ", null);

			if (cursor.moveToPosition(0)) {
				/*ContentValues cv2 = new ContentValues();
				cv2.put("id_desarrollo", id_desarrollo);

				db.update("usuarios", cv2, "id_desarrollo='"+id_desarrollo+"' ", null);*/
				cursor.close();
				db.close();

			} else {
				ContentValues cv = new ContentValues();
				cv.put("id_desarrollo", id_desarrollo);
				cv.put("id_usuario", id_usuario);
				cv.put("fecha_creacion", fecha_creacion);

				db.insert("rel_usuarios_desarrollo", null, cv);
				cursor.close();
				db.close();
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/****************    Consulta para Insert Preguntas      *************/
	public boolean DAO_Elimina_todo() {

		db = myDbHelper.getWritableDatabase();

		try {
			db.execSQL("delete from desarrollo");
			db.execSQL("delete from Responsables");
			db.execSQL("delete from insumos");
			db.execSQL("delete from insumos_requi");
			//db.execSQL("delete from logo_app");

			db.execSQL("delete from requisiciones");
			db.execSQL("delete from usuarios");

			db.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/****************    Consulta para Eliminar insumos temporal      *************/
	public boolean DAO_Elimina_insumos_temporal() {

		db = myDbHelper.getWritableDatabase();

		try {
			db.execSQL("delete from insumos_temporal");

			db.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/****************    Consulta para Eliminar insumos temporal      *************/
	public boolean DAO_Elimina_Registro_insumos_temporal(String id) {

		db = myDbHelper.getWritableDatabase();

		try {
			db.execSQL("delete from insumos_temporal where id='"+id+"'");

			db.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/****************    Consulta para Insert Sembrado      *************/
	public boolean DAO_Insert_Requi_Sync(String fracc, String nombre_desarrollo, String frente, String manzana,
                                       String lote, String interior, String modelo, String ubicacion,
                                       String contrato, String contratista, String id_contrato, String id_contratista, String id_prototipo) {

		db = myDbHelper.getWritableDatabase();
		cursor = null;
		ContentValues cv = new ContentValues();
		cv.put("fracc", fracc);
		cv.put("nombre_desarrollo", nombre_desarrollo);
		cv.put("frente", frente);
		cv.put("manzana", manzana);
		cv.put("lote", lote);
		cv.put("interior", interior);
		cv.put("modelo", modelo);
		cv.put("ubicacion", ubicacion);
		cv.put("contrato", contrato);
		cv.put("contratista", contratista);
		cv.put("id_contrato", id_contrato);
		cv.put("id_contratista", id_contratista);
		cv.put("id_prototipo", id_prototipo);

		try {

			cursor = db.rawQuery("SELECT ubicacion from sembrado  where ubicacion='"+ubicacion+"' and " +
					"id_prototipo='"+id_prototipo+"' and id_contrato='"+id_contrato+"' and id_contratista='"+id_contratista+"' ", null);

			if (cursor.moveToPosition(0)) {
				cursor.close();
				db.close();
				return true;

			} else {
				db.insert("sembrado", null, cv);
				cursor.close();
				db.close();
				return true;
			}
		}
		catch (Exception e) {
			return false;
		}
	}

	public String[] DAO_Select_envio_SMS_Email(String ubicacion, String estacion, String modelo, String contrato, String contratista){

		cursor = null;
		db = myDbHelper.getWritableDatabase();

		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();
			cursor = db.rawQuery("SELECT sms,email FROM preg_resp_detalle where ubicacion='"+ubicacion+"' " +
					"AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) " +
					"AND modelo='"+modelo+"' AND " +
					"contrato='"+contrato+"' ", null);

		consultas lista = new consultas();
		lista.arreglo = new String[cursor.getColumnCount()];

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					lista.arreglo[0] = cursor.getString(cursor.getColumnIndex("sms"));
					lista.arreglo[1] = cursor.getString(cursor.getColumnIndex("email"));
				}
				else{
					lista.arreglo[0] = "0";
					lista.arreglo[1] = "0";
				}
			}
			else{
				lista.arreglo[0] = "0";
				lista.arreglo[1] = "0";
			}
			cursor.close();
			db.close();
			return lista.arreglo;

	}

	public void DAO_Guarda_Respuesta_Detalle(String ubicacion, String estacion, String modelo, String contrato,
                                             String observaciones, String calificacion, String sms, String email, String contratista){

		cursor = null;
		db = myDbHelper.getWritableDatabase();
		//contrato=contrato.trim();

		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		try {
			/*cursor = db.rawQuery("SELECT ubicacion FROM preg_resp_detalle where ubicacion='"+ubicacion+"' " +
					"AND id_estacion=(SELECT DISTINCT trim(id_estacion) from estaciones where descripcion like '"+estacion+"%') " +
					"AND modelo='"+modelo+"' AND " +
					"contrato like '"+contrato+"%' ", null);*/

			cursor = db.rawQuery("SELECT ubicacion FROM preg_respondidas where ubicacion='"+ubicacion+"' " +
					"AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) " +
					"AND modelo='"+modelo+"' AND contrato = '"+contrato+"' ", null);


			if (cursor.moveToPosition(0)) {
				ContentValues cv = new ContentValues();
				cv.put("observaciones",observaciones);
				cv.put("calificacion",calificacion);
				cv.put("sms",sms);
				cv.put("email",email);
				db.update("preg_resp_detalle", cv, "ubicacion='"+ubicacion+"' AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1) AND modelo='"+modelo+"' AND contrato = '"+contrato+"' ", null);
				cursor.close();
				db.close();

			} else {
				db.execSQL("INSERT INTO preg_resp_detalle (ubicacion, id_estacion, modelo,contrato,observaciones,calificacion,sms,email) " +
						"VALUES ('"+ubicacion+"',(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1)," +
						"'"+modelo+"'," +
						"'"+contrato+"','"+observaciones+"','"+calificacion+"','"+sms+"','"+email+"')");

				cursor.close();
				db.close();
			}

		} catch (Exception e) {

		}

	}

	public void DAO_resetea_Respuesta_Detalle(String ubicacion, String estacion, String modelo, String contrato,
                                              String observaciones, String calificacion, String sms, String email){
		cursor = null;
		db = myDbHelper.getWritableDatabase();

		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		try {
				ContentValues cv = new ContentValues();
				cv.put("observaciones",observaciones);
				cv.put("calificacion",calificacion);
				cv.put("sms",sms);
				cv.put("email",email);
				//db.update("preg_resp_detalle", cv, "ubicacion='"+ubicacion+"' AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1) AND modelo='"+modelo+"' AND contrato = '"+contrato+"' ", null);
			db.delete("preg_resp_detalle", "ubicacion='"+ubicacion+"' AND id_estacion='"+Variables_Universales.getId_estacion()+"' AND modelo='"+modelo+"' AND contrato = '"+contrato+"' ", null);

			cursor.close();
				db.close();

		} catch (Exception e) {

		}
	}

	public void DAO_resetea_Respuesta(String ubicacion, String estacion, String modelo, String contrato){

		cursor = null;
		db = myDbHelper.getWritableDatabase();
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		try {
				//db.update("preg_respondidas", cv, "ubicacion='"+ubicacion+"' AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1) AND modelo='"+modelo+"' AND id_pregunta=(select id from preguntas where pregunta = '"+pregunta+"' and modelo='"+modelo+"' AND id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1)) AND contrato like '"+contrato+"%' ", null);

			db.delete("preg_respondidas", "ubicacion='"+ubicacion+"' AND id_estacion='"+Variables_Universales.getId_estacion()+"' AND modelo='"+modelo+"' " +
					"AND contrato = '"+contrato+"' ", null);

			/*db.delete("preg_respondidas", null, null);*/

				db.close();
		} catch (Exception e) {
			String error;
			error=e.toString();
			if(error.isEmpty()){

			}
		}
	}

	public void DAO_estaciones_completa_elimina(String ubicacion, String estacion, String contrato){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		ContentValues cv2 = new ContentValues();
		cv2.putNull("fecha_fin");
		cv2.putNull("fecha_inicio");

		db.update("estaciones", cv2, "ubicacion = '"+ubicacion+"'  AND descripcion like '"+first+"%' and id_estacion='"+Variables_Universales.getId_estacion()+"' ", null);
		db.close();
	}

	/****************    Consulta para Insert foto sincronizada      *************/
	public int DAO_Insert_foto_sinc(byte[] iv_logo,String id_logo) {

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("foto", iv_logo);
		cv.put("id_logo", id_logo);

		cursor = null;
		cursor = db.rawQuery("select id_logo from logo_app where id_logo='"+id_logo+"'", null);

			if (cursor.moveToFirst()) {
				db.close();
				cursor.close();
				return 2;
			}

			else {

			try {
				db.insert("logo_app", null, cv);
				db.close();
				cursor.close();

				return 2;

			} catch (Exception e) {
				return 0;
			}
		}

	}

	/***************  Consulta para seleccionar foto LOGO    *************/
	public byte[] DAO_Select_Foto_LOGO_Panel_Control() {

		byte[] data2 = null;
		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor = db.rawQuery("select foto from logo_app limit 1", null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {

				data2 = new byte[0];
				data2 = cursor.getBlob(cursor.getColumnIndex("foto"));
			}
		}

		cursor.close();
		db.close();
		return data2;
	}

	/***************  Consulta para comparar cantidades al guardar    *************/
	public boolean DAO_compara_cantidades(String estacion, String ubicacion, double por_estimar, String id_contrato, String id_contratista) {

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		cursor2 = null;
		double cantidad_total=0,estimado=0, cantidad_estimadas_anterior=0;

		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		cursor = db.rawQuery("select cantidad,estimado from estaciones where contrato='"+id_contrato+"' and contratista ='"+id_contratista+"' and descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1", null);
		/*cursor2 = db.rawQuery("select cantidad from estaciones_detalle where id_estacion=(SELECT (id_estacion) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1) and id_centro=(SELECT (idCentro) from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1)", null);

		if(cursor2.moveToFirst()){
			for (int x = 0; x < cursor2.getCount(); x++) {

				cantidad_estimadas_anterior = cantidad_estimadas_anterior+Double.parseDouble(cursor2.getString(cursor2.getColumnIndex("cantidad")));
				cursor2.moveToNext();
			}

		}*/  //Se comenta la suma del detalle de estimaciones

			if (cursor.moveToFirst()) {

				cantidad_total= Double.parseDouble(cursor.getString(cursor.getColumnIndex("cantidad")));
				estimado= Double.parseDouble(cursor.getString(cursor.getColumnIndex("estimado")));
				//cantidad_total=10.50;
				//estimado=5;
				if((por_estimar+estimado+cantidad_estimadas_anterior)>cantidad_total){

					cursor.close();
					db.close();
					return false;
				}
				else {
					Variables_Universales.setTotal_estimado(""+(por_estimar+estimado+cantidad_estimadas_anterior));
					cursor.close();
					db.close();
					return true;
				}
			}
			else {
				cursor.close();
				db.close();
				return false;
			}
	}

	public String[] DAO_cantidad_y_porEstimar(String estacion, String ubicacion, String contrato, String contratista){

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		//AbreDB();
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();
		double total,estimado,por_estimar;

		cursor = db.rawQuery("select cantidad,estimado from estaciones where contrato='"+contrato+"' and contratista='"+contratista+"' and descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' LIMIT 1", null);
		consultas lista = new consultas();
		lista.arreglo = new String[2];
		lista.arreglo[0]="1";
		lista.arreglo[1]="1";


		if (cursor != null) {
			if (cursor.moveToFirst()) {

				total= Double.parseDouble(cursor.getString(cursor.getColumnIndex("cantidad")));
				estimado= Double.parseDouble(cursor.getString(cursor.getColumnIndex("estimado")));
				por_estimar=total-estimado;
					lista.arreglo[0] = ""+total;
				lista.arreglo[1] = ""+por_estimar;

			}
		}
		cursor.close();
		db.close();
		return lista.arreglo;
	}

	/****************    Consulta para Insert Preguntas      *************/
	public boolean DAO_actualiza_estacion_Insert_estaciones_detalle(String estacion, String ubicacion, String total_estimado, String estimado, String contrato, String contratista) {

		db = myDbHelper.getWritableDatabase();
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		ContentValues cv2 = new ContentValues();
		cv2.put("estimado", total_estimado);

		ContentValues cv = new ContentValues();
		cv.put("UUID", UUID.randomUUID().toString());
		cv.put("cantidad", estimado);

		try {
			cursor = db.rawQuery("select id_estacion,idCentro from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1", null);

			if (cursor.moveToPosition(0)) {
				db.update("estaciones", cv2, "id_estacion='"+cursor.getString(cursor.getColumnIndex("id_estacion"))+"' and idCentro='"+cursor.getString(cursor.getColumnIndex("idCentro"))+"' ", null);

				cv.put("id_estacion", cursor.getString(cursor.getColumnIndex("id_estacion")));
				cv.put("id_centro", cursor.getString(cursor.getColumnIndex("idCentro")));
				db.insert("estaciones_detalle", null, cv);

				cursor.close();
				db.close();
			} else {
				cursor.close();
				db.close();
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/***************  Consulta para comparar cantidades al guardar    *************/
	public boolean DAO_estimacion_completa(String estacion, String ubicacion, String contrato, String contratista) {

		DataBaseHelper myDbHelper=new DataBaseHelper(Variables_Universales.getContext());
		db = myDbHelper.getWritableDatabase();
		cursor = null;
		double cantidad_total,estimado;

		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();

		cursor = db.rawQuery("select cantidad,estimado from estaciones where descripcion like '"+first+"%' and ubicacion='"+ubicacion+"' and contrato='"+contrato+"' and contratista='"+contratista+"' LIMIT 1", null);


		if (cursor.moveToFirst()) {

			cantidad_total= Double.parseDouble(cursor.getString(cursor.getColumnIndex("cantidad")));
			estimado= Double.parseDouble(cursor.getString(cursor.getColumnIndex("estimado")));

			if(cantidad_total==estimado){

				cursor.close();
				db.close();
				return true;
			}
			else {
				cursor.close();
				db.close();
				return false;
			}
		}
		else {
			cursor.close();
			db.close();
			return false;
		}
	}

	/****************    Consulta eliminar cantidades estimacion     *************/
	public boolean DAO_borra_estimaciones(String estacion, String ubicacion) {

		db = myDbHelper.getWritableDatabase();
		StringTokenizer tokens = new StringTokenizer(estacion, "'");
		String first = tokens.nextToken();
		double total=0,estimado=0,nuevo_total=0;

		ContentValues cv2 = new ContentValues();


		try {

			cursor = db.rawQuery("select id_estacion,idCentro,estimado from estaciones where descripcion like '"+first+"%' and id_estacion='"+Variables_Universales.getId_estacion()+"' and ubicacion='"+ubicacion+"' LIMIT 1", null);
			if (cursor.moveToPosition(0)) {

				cursor2 = db.rawQuery("select cantidad from estaciones_detalle where id_estacion='"+cursor.getString(cursor.getColumnIndex("id_estacion"))+"' and id_centro='"+cursor.getString(cursor.getColumnIndex("idCentro"))+"'", null);

				if (cursor2 != null) {
					if (cursor2.moveToFirst()) {
						for (int x = 0; x < cursor2.getCount(); x++) {

							total = total + Double.parseDouble(cursor2.getString(cursor2.getColumnIndex("cantidad")));
							cursor2.moveToNext();
						}
					}
				}
				nuevo_total= Double.parseDouble(cursor.getString(cursor.getColumnIndex("estimado")))-total;

				cv2.put("estimado", nuevo_total);
				db.update("estaciones", cv2, "id_estacion='"+cursor.getString(cursor.getColumnIndex("id_estacion"))+"' and idCentro='"+cursor.getString(cursor.getColumnIndex("idCentro"))+"' ", null);
				db.delete("estaciones_detalle", "id_estacion='"+cursor.getString(cursor.getColumnIndex("id_estacion"))+"' and id_centro='"+cursor.getString(cursor.getColumnIndex("idCentro"))+"' ", null);

				cursor.close();
				cursor2.close();
				db.close();
			} else {
				cursor.close();
				db.close();
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}

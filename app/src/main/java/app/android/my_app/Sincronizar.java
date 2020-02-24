 package app.android.my_app;

 import android.app.AlertDialog;
 import android.app.ProgressDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.os.Looper;
 import android.support.v7.app.AppCompatActivity;
 import android.support.v7.widget.Toolbar;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.view.ViewGroup.LayoutParams;
 import android.view.animation.Animation;
 import android.view.animation.AnimationUtils;
 import android.widget.AdapterView;
 import android.widget.AdapterView.OnItemSelectedListener;
 import android.widget.ArrayAdapter;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.ImageView;
 import android.widget.LinearLayout;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;

 import org.ksoap2.SoapEnvelope;
 import org.ksoap2.serialization.SoapObject;
 import org.ksoap2.serialization.SoapPrimitive;
 import org.ksoap2.serialization.SoapSerializationEnvelope;
 import org.ksoap2.transport.HttpTransportSE;
 import org.xml.sax.InputSource;
 import org.xml.sax.XMLReader;

 import java.io.BufferedReader;
 import java.io.StringReader;
 import java.util.List;

 import javax.xml.parsers.SAXParser;
 import javax.xml.parsers.SAXParserFactory;

 import DAO.DataBaseHelper;
 import DTO.AlertCalendar;
 import DTO.Variables_Universales;
 import DTO.XMLParser;
 import DTO.XMLParser_2;
 import DTO.XMLParser_Fotos;
 import DTO.XmlValuesModel;
 import DTO.consultas;
 import app.android.my_app.R.string;

 public class Sincronizar extends AppCompatActivity {
	
	protected Cursor cursor;

	private ImageButton descargar,fecha_desde,fecha_hasta;
	private ImageButton Enviar;
	private ImageButton SincronizarCatalogos, desc_preg;
	private ImageButton EliminarTodo;
	TextView tv_user;
	Variables_Universales var;
	 Spinner sp_desarrollo;
	 String sfecha_desde="",sfecha_hasta="",ip_servidor,nombre_db,ip_servidor1,ip_servidor2,nombre_db1,nombre_db2,desarrollo_select,desde_select,hasta_select;
	 EditText et_desde,et_hasta;
	 AlertCalendar AC;
	 consultas con;
	 Cursor c_estaciones,c_preg_resp_det,c_preg_resp,c_preguntas,c_sembrado,c_usuarios_desarrollos,c_usuarios,c_desarrollos,c_est_det;
	 ImageView iv_logo;

	private SQLiteDatabase db;
	public String XmlPa;
	public Context mycon;


	 Animation animRotar,anim_desaparece,animEscala;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sincronizar_1);

         this.mycon = this;
         Variables_Universales.setContext(this);
		AC=new AlertCalendar();
		con=new consultas();

		Variables_Universales.setView_gen(getWindow().getDecorView().getRootView());
         
         final DataBaseHelper myDbHelper = new DataBaseHelper(this);
         db = myDbHelper.getWritableDatabase();

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		animRotar = AnimationUtils.loadAnimation(Sincronizar.this, R.anim.anim_rotate);
		anim_desaparece = AnimationUtils.loadAnimation(Sincronizar.this, R.anim.anim_desaparece);
		animEscala = AnimationUtils.loadAnimation(Sincronizar.this, R.anim.anim_escala);

		et_desde=(EditText)findViewById(R.id.editText2);
		et_hasta=(EditText)findViewById(R.id.editText3);

		var=new Variables_Universales();
		tv_user=(TextView)findViewById(R.id.textView33);
		tv_user.setText(var.getNombre_usuario());


		et_desde.setEnabled(false);
		et_hasta.setEnabled(false);

		iv_logo=(ImageView)findViewById(R.id.imageView5);
		byte[] data1=null;
		data1 = con.DAO_Select_Foto_LOGO_Panel_Control();

		if(data1!=null) {
			Bitmap originalBitmap = (BitmapFactory.decodeByteArray(data1, 0, data1.length));
			iv_logo.setImageBitmap(originalBitmap);

		}

		sp_desarrollo=(Spinner)findViewById(R.id.sp_desarrollo);
		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_text_layout, con.DAO_Spinner_Desarrollo(Variables_Universales.getId_usuario()));
		adapter.setDropDownViewResource(R.layout.spinner_text_layout);
		sp_desarrollo.setAdapter(adapter);
		sp_desarrollo.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				desarrollo_select=sp_desarrollo.getSelectedItem().toString();
				var.setDesarrollo_sync(sp_desarrollo.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		descargar=(ImageButton) findViewById(R.id.ib_descargar);
        Enviar=(ImageButton)findViewById(R.id.imageButton10);
        EliminarTodo=(ImageButton)findViewById(R.id.imageButton12);
        SincronizarCatalogos=(ImageButton) findViewById(R.id.imageButton11);
		 fecha_desde=(ImageButton) findViewById(R.id.ib_desde);
		fecha_hasta=(ImageButton) findViewById(R.id.ib_hasta);
		desc_preg=(ImageButton) findViewById(R.id.ib_desc_preg);


		fecha_desde.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Variables_Universales.setCalendar_desde("sincro_desde");
				AC.dialogee.show();
			}
		});

		fecha_hasta.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Variables_Universales.setCalendar_desde("sincro_hasta");
				AC.dialogee.show();
			}
		});


       
       SincronizarCatalogos.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {

			v.startAnimation(animEscala);
			/*if(et_desde.getText().toString().contentEquals("") || et_hasta.getText().toString().contentEquals(""))
			{
				AlertaFaltanCampos();
			}
			else {*/
				String[] ip_db = con.DAO_IP_Servidor(1); // numero 1 para seleccionar la conexion de NEODATA
				ip_servidor = ip_db[0];
				nombre_db = ip_db[1];
				desarrollo_select = con.DAO_Desarrollo_Sync_2(desarrollo_select);

				/*desde_select = et_desde.getText().toString();
				hasta_select = et_hasta.getText().toString();*/
			/*String[] ip_db2=con.DAO_IP_Servidor(2); // numero 2 para seleccionar la conexion de cONTROL CALIDAD
			ip_servidor1=ip_db2[0];
			nombre_db1=ip_db2[1];*/


				Sincroniza_Catalogos task = new Sincroniza_Catalogos();
				task.execute();
				Descarga_Imagen task2 = new Descarga_Imagen();
				//task.execute();
			//task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			//task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


		}
	});
       
       descargar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			v.startAnimation(animEscala);

				/*String[] ip_db=con.DAO_IP_Servidor(2); // numero 2 para seleccionar la conexion de cONTROL CALIDAD
				ip_servidor1=ip_db[0];
				nombre_db1=ip_db[1];*/

			String[] ip_db1 = con.DAO_IP_Servidor(1); // numero 1 para seleccionar la conexion de NEODATA
			ip_servidor2 = ip_db1[0];
			nombre_db2 = ip_db1[1];


			Descargar_info_Usuarios task= new Descargar_info_Usuarios();
			task.execute();
			//Descargar_info_desarrollos_V2 task2 = new Descargar_info_desarrollos_V2(); //No hay pex en este
			//task2.execute();
			//task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			//ask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		}
	});
       
       Enviar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			v.startAnimation(animEscala);
				//AlertaEnviarLecturasAgua();

            /*if(et_desde.getText().toString().contentEquals("") || et_hasta.getText().toString().contentEquals(""))
            {
                AlertaFaltanCampos();
            }
            else {*/

                String[] ip_db1 = con.DAO_IP_Servidor(1); // numero 1 para seleccionar la conexion de SL
                ip_servidor1 = ip_db1[0];
                nombre_db1 = ip_db1[1];

                String[] ip_db = con.DAO_IP_Servidor(2); // numero 2 para seleccionar la conexion de Control de calidad
                ip_servidor = ip_db[0];
                nombre_db = ip_db[1];

                desarrollo_select = con.DAO_Desarrollo_Sync(desarrollo_select);
                //desde_select = et_desde.getText().toString();
                //hasta_select = et_hasta.getText().toString();

                //Enviar_Info task = new Enviar_Info();
                //Enviar_Info_Estaciones task2 = new Enviar_Info_Estaciones();
                //task.execute();
				//task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            //}

		}
	});
       
       EliminarTodo.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			v.startAnimation(animEscala);
			AlertaEliminar();
			
			
		}
	});

		sfecha_desde=et_desde.getText().toString();
		sfecha_hasta=et_hasta.getText().toString();
    }
	 ////**********  Para Sincronizar Sembrado y estaciones *****/////////
	 public class Sincroniza_Catalogos extends AsyncTask<String, Void, Boolean>
	 {
		 private final ProgressDialog dialog = new ProgressDialog(Sincronizar.this);
		 @Override
		 protected void onPreExecute()
		 {
			 this.dialog.setMessage("Sincronizando datos...");
			 this.dialog.show();
		 }

		 protected Boolean doInBackground(final String... args)
		 {
			 String nuevo = "<?xml version=\"1.0\"  encoding=\"utf-8\"?><principal>";
			 final String NAMESPACE = "http://Serv.net/";
			 final String URL="http://"+ip_servidor+"/WS_NEODATA/ServicioClientes.asmx";
			 final String METHOD_NAME = "Sincroniza_Insumos_Req";
			 final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
			 final int time=20000,time2=190000;
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.dotNet = true;

			 request.addProperty("nombre_bd", nombre_db);
			 request.addProperty("id_proyecto", desarrollo_select);
			 //request.addProperty("fecha_desde", desde_select);
			 //request.addProperty("fecha_hasta", hasta_select);

			 envelope.setOutputSoapObject(request);
			 HttpTransportSE transporte = new HttpTransportSE(URL,999999999);


			 try
			 {
				 transporte.call(SOAP_ACTION, envelope);
				 SoapPrimitive resSoap =(SoapPrimitive)envelope.getResponse();

				 nuevo = nuevo + resSoap.toString() + "</principal>";

				 BufferedReader br=new BufferedReader(new StringReader(nuevo));
				 InputSource is=new InputSource(br);

				 /************  Parse XML **************/

				 //Looper.prepare();
                 if (Looper.myLooper() == null)
                 {
                     Looper.prepare();
                 }
				 XMLParser parser=new XMLParser();
				 SAXParserFactory factory= SAXParserFactory.newInstance();
				 SAXParser sp=factory.newSAXParser();
				 XMLReader reader=sp.getXMLReader();
				 reader.setContentHandler(parser);
				 reader.parse(is);

				 return true;
			 }
			 catch (Exception e)
			 {
					return false;
			 }

		 }


		 protected void onPostExecute(final Boolean success)
		 {
			 if (this.dialog.isShowing())
			 {
				 this.dialog.dismiss();
			 }

			 if (success)
			 {
				 Toast.makeText(Sincronizar.this, "Sincronización Exitosa", Toast.LENGTH_SHORT).show();
			 }

			 else
			 {
				 Toast.makeText(Sincronizar.this, "Error de Sincronización", Toast.LENGTH_SHORT).show();
			 }
		 }
		 }




	 ///*****  Descargar Responsables y Desarrollos  ***///////
	 public class Descargar_info_Usuarios extends AsyncTask<String, Void, Boolean> //

	 {
		 private final ProgressDialog dialog = new ProgressDialog(Sincronizar.this);

		 @Override
		 protected void onPreExecute()
		 {
			 this.dialog.setMessage("Descargando información...");
			 this.dialog.show();
		 }

		 protected Boolean doInBackground(final String... args)
		 {
			 String nuevo = "<?xml version=\"1.0\"  encoding=\"utf-8\"?><principal>";
			 final String NAMESPACE = "http://Serv.net/";
			 final String URL="http://"+ip_servidor2+"/WS_NEODATA/ServicioClientes.asmx";
			 /*final String METHOD_NAME = "Sincroniza_Preg_User_des_RelUserDes";*/
			 final String METHOD_NAME = "Sincroniza_Responsables_Desarrollos";
			 final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
			 final int time=20000,time2=190000;
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.dotNet = true;

			 //desarrollo_select = con.DAO_Desarrollo_Sync(desarrollo_select);

			 request.addProperty("nombre_bd", nombre_db2);
			 //request.addProperty("id_proyecto", desarrollo_select);
			 //request.addProperty("id_desarrollo", desarrollo_select);  //Agregado para la nueva consulta

			 envelope.setOutputSoapObject(request);
			 HttpTransportSE transporte = new HttpTransportSE(URL);

			 try
			 {
				 transporte.call(SOAP_ACTION, envelope);
				 SoapPrimitive resSoap =(SoapPrimitive)envelope.getResponse();

				 nuevo = nuevo + resSoap.toString() + "</principal>";

				 BufferedReader br=new BufferedReader(new StringReader(nuevo));
				 InputSource is=new InputSource(br);

				 /************  Parse XML **************/


				 if (Looper.myLooper() == null)
				 {
					 Looper.prepare();
				 }
				 XMLParser_2 parser=new XMLParser_2();
				 SAXParserFactory factory= SAXParserFactory.newInstance();
				 SAXParser sp=factory.newSAXParser();
				 XMLReader reader=sp.getXMLReader();
				 reader.setContentHandler(parser);
				 reader.parse(is);

				 return true;
			 }
			 catch (Exception e)
			 {
				 return false;
			 }
		 }


		 protected void onPostExecute(final Boolean success)
		 {
			 if (this.dialog.isShowing())
			 {
				 this.dialog.dismiss();
			 }
			 if (success)
			 {
				 Toast.makeText(Sincronizar.this, "Descarga de información Exitosa", Toast.LENGTH_SHORT).show();
				 ArrayAdapter adapter = new ArrayAdapter(Sincronizar.this, R.layout.spinner_text_layout, con.DAO_Spinner_Desarrollo(Variables_Universales.getId_usuario()));
				 adapter.setDropDownViewResource(R.layout.spinner_text_layout);
				 sp_desarrollo.setAdapter(adapter);
			 }
			 else
			 {
				 Toast.makeText(Sincronizar.this, "No se encontró información", Toast.LENGTH_SHORT).show();
			 }
		 }
	 }


     public void AlertaFaltanCampos(){
         AlertDialog.Builder myDialog
                 = new AlertDialog.Builder(Sincronizar.this);
         myDialog.setTitle("Alerta");
         TextView textView = new TextView(this);
         textView.setText("Seleccione la fecha desde y fecha hasta");

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

	 public void AlertaSeElimino(){
		 AlertDialog.Builder myDialog
				 = new AlertDialog.Builder(Sincronizar.this);
		 myDialog.setTitle("Alerta");
		 TextView textView = new TextView(this);
		 textView.setText("Toda la información fué eliminada exitosamente");

		 LayoutParams textViewLayoutParams
				 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 textView.setLayoutParams(textViewLayoutParams);
		 LinearLayout layout = new LinearLayout(this);
		 layout.setOrientation(LinearLayout.VERTICAL);
		 layout.addView(textView);
		 myDialog.setView(layout);

		 myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

			 public void onClick(DialogInterface arg0, int arg1) {
				finish();
			 }
		 });
		 myDialog.show();
	 }
    
 public void AlertaEliminar(){
 	AlertDialog.Builder myDialog
   = new AlertDialog.Builder(Sincronizar.this);
  myDialog.setTitle(getResources().getString(string.m5));
  TextView textView = new TextView(this);
  textView.setText("Se borraran todos los datos para siempre");

  LayoutParams textViewLayoutParams
   = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
  textView.setLayoutParams(textViewLayoutParams);
  LinearLayout layout = new LinearLayout(this);
  layout.setOrientation(LinearLayout.VERTICAL);
  layout.addView(textView);
  myDialog.setView(layout);
      
  myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface arg0, int arg1) {

		  Elimina();
       }
      });
  
  myDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface arg0, int arg1) {
     	
        
       }
      });
  
 myDialog.show();
 	

 	
 
 }
    
public void Alerta(){
    	AlertDialog.Builder myDialog
      = new AlertDialog.Builder(Sincronizar.this);
     myDialog.setTitle(getResources().getString(string.m6));

     TextView textView = new TextView(this);
     textView.setText(getResources().getString(string.m7));

     LayoutParams textViewLayoutParams
      = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
     textView.setLayoutParams(textViewLayoutParams);

     LinearLayout layout = new LinearLayout(this);
     layout.setOrientation(LinearLayout.VERTICAL);
     layout.addView(textView);

     myDialog.setView(layout);
         
     myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

         public void onClick(DialogInterface arg0, int arg1) {
			 /*String[] ip_db=con.DAO_IP_Servidor(1); // numero 1 para seleccionar la conexion de SL
			 ip_servidor=ip_db[0];
			 nombre_db=ip_db[1];

			 Sincroniza_Catalogos task = new Sincroniza_Catalogos();
			 task.execute();*/
          }
         });
     
     myDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

         public void onClick(DialogInterface arg0, int arg1) {
        	
           
          }
         });
     
    myDialog.show();
    }


public void AlertaTermino(String mensaje){

	

	
	AlertDialog.Builder myDialog
  = new AlertDialog.Builder(Sincronizar.this);
 myDialog.setTitle(mensaje);
 

 
 TextView textView = new TextView(this);
 textView.setText("");

 LayoutParams textViewLayoutParams
  = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
 textView.setLayoutParams(textViewLayoutParams);
 //textView.setTranslationX(10);

 
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


public void guardaLecturas(int idcondominio, int idcondomino, int lectura, int mesperiodo, int anioPeriodo, String fechalectura){

	DataBaseHelper myDbHelper = new DataBaseHelper(Sincronizar.this);
	db = myDbHelper.getWritableDatabase();

	db.execSQL("INSERT INTO lecturasagua (idCondominio, idCondomino, lectura, mesPeriodo, anioPeriodo, fechaLectura ) " +
			"VALUES ("+ idcondominio +", "+idcondomino+" ,"+lectura+" , "+mesperiodo+" ,"+ anioPeriodo +", '"+ fechalectura +"' )");
	db.close();
	
}

	 ////*******    Enviar los insumos a verificar su cantidad de existencia   ********///////////////
	 public class Enviar_Info_insumos extends AsyncTask<String, Void, Boolean>
	 {
		 private final ProgressDialog dialog = new ProgressDialog(Sincronizar.this);
		 @Override
		 protected void onPreExecute()
		 {
			 this.dialog.setMessage("Verificando existencia de insumos...");
			 this.dialog.show();
		 }
		 protected Boolean doInBackground(final String... args)
		 {

			 final String NAMESPACE = "http://Serv.net/";
			 final String URL="http://"+ip_servidor+"/WS_NEODATA/ServicioClientes.asmx";
			 final String METHOD_NAME = "verifica_insumo_cantidad";
			 final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
			 final int time=20000,time2=190000;
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			 DataBaseHelper myDbHelper = new DataBaseHelper(Sincronizar.this);
			 db = myDbHelper.getWritableDatabase();

			 c_preg_resp_det = db.rawQuery("SELECT observaciones,calificacion,sms,email,ubicacion,id_estacion,modelo,contrato " +
					 "FROM preg_resp_detalle " +
					 "where ubicacion = (SELECT ubicacion from preg_respondidas where desarrollo='"+var.getDesarrollo_sync()+"')" ,null);

			 c_preg_resp = db.rawQuery("SELECT ubicacion, id_estacion,modelo,id_pregunta,is_active,contrato,desarrollo from preg_respondidas where desarrollo='"+var.getDesarrollo_sync()+"' " ,null);

			 String bodyXml="";

			 if (c_preg_resp_det.moveToFirst()) {
				 for (int i = 0; i < c_preg_resp_det.getCount(); i++) {
					 bodyXml=bodyXml+"<Preg_resp_det>" +
							 "<observaciones>"+c_preg_resp_det.getString(c_preg_resp_det.getColumnIndex("observaciones"))+"</observaciones>" +
							 "<calificacion>"+c_preg_resp_det.getString(c_preg_resp_det.getColumnIndex("calificacion"))+"</calificacion>" +
							 "<sms>"+c_preg_resp_det.getString(c_preg_resp_det.getColumnIndex("sms"))+"</sms>" +
							 "<email>"+c_preg_resp_det.getString(c_preg_resp_det.getColumnIndex("email"))+"</email>" +
							 "<ubicacion>"+c_preg_resp_det.getString(c_preg_resp_det.getColumnIndex("ubicacion"))+"</ubicacion>" +
							 "<id_estacion>"+c_preg_resp_det.getString(c_preg_resp_det.getColumnIndex("id_estacion"))+"</id_estacion>" +
							 "<modelo>"+c_preg_resp_det.getString(c_preg_resp_det.getColumnIndex("modelo"))+"</modelo>" +
							 "<contrato>"+c_preg_resp_det.getString(c_preg_resp_det.getColumnIndex("contrato"))+"</contrato>" +
							 "<nombre_db>"+nombre_db+"</nombre_db>" +
							 "<desarrollo>"+desarrollo_select+"</desarrollo>" +
							 "<nd>"+var.getDesarrollo_sync()+"</nd>" +
							 "<nu>"+Variables_Universales.getNombre_usuario()+"</nu>" +
							 "</Preg_resp_det>";
					 c_preg_resp_det.moveToNext();
				 }
			 }

			 if (c_preg_resp.moveToFirst()) {
				 for (int i = 0; i < c_preg_resp.getCount(); i++) {

					 bodyXml=bodyXml+"<Preg_resp>" +
							 "<ubicacion>"+c_preg_resp.getString(c_preg_resp.getColumnIndex("ubicacion"))+"</ubicacion>" +
							 "<id_estacion>"+c_preg_resp.getString(c_preg_resp.getColumnIndex("id_estacion"))+"</id_estacion>" +
							 "<modelo>"+c_preg_resp.getString(c_preg_resp.getColumnIndex("modelo"))+"</modelo>" +
							 "<id_pregunta>"+c_preg_resp.getString(c_preg_resp.getColumnIndex("id_pregunta"))+"</id_pregunta>" +
							 "<is_active>"+c_preg_resp.getString(c_preg_resp.getColumnIndex("is_active"))+"</is_active>" +
							 "<contrato>"+c_preg_resp.getString(c_preg_resp.getColumnIndex("contrato"))+"</contrato>" +
							 //"<des>"+c_preg_resp.getString(c_preg_resp.getColumnIndex("desarrollo"))+"</des>" +
							 "<nombre_db>"+nombre_db+"</nombre_db>" +
							 "</Preg_resp>";
					 c_preg_resp.moveToNext();
				 }
			 }

			 bodyXml = "<Principal>"+bodyXml+"</Principal>";
			 request.addProperty("soap", bodyXml);
			 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.dotNet = true;

			 envelope.setOutputSoapObject(request);
			 HttpTransportSE transporte = new HttpTransportSE(URL);

			 try
			 {
				 transporte.call(SOAP_ACTION, envelope);
				 SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
				 String mensaje = resultado_xml.toString();
				 if(mensaje.contentEquals("true")){
					 return true;
				 }
				 else {return  false;}
			 }
			 catch (Exception e)
			 {
				 return false;
			 }
		 }

		 protected void onPostExecute(final Boolean success)
		 {
			 if (this.dialog.isShowing())
			 {
				 this.dialog.dismiss();
			 }
			 if (success)
			 {
				 Toast.makeText(Sincronizar.this, "Sincronización Exitosa", Toast.LENGTH_SHORT).show();
			 }
			 else
			 {
				 Toast.makeText(Sincronizar.this, "Error de Sincronización", Toast.LENGTH_SHORT).show();
			 }
		 }
	 }

	 ////*******    Enviar estaciones   ********///////////////
	 public class Enviar_Info_Estaciones extends AsyncTask<String, Void, Boolean>
	 {
		 private final ProgressDialog dialog = new ProgressDialog(Sincronizar.this);
		 @Override
		 protected void onPreExecute()
		 {
			 this.dialog.setMessage("Enviando datos...");
			 this.dialog.show();
		 }
		 protected Boolean doInBackground(final String... args)
		 {

			 final String NAMESPACE = "http://Serv.net/";
			 final String URL="http://"+ip_servidor1+"/WS_construc/ServicioClientes.asmx";
			 final String METHOD_NAME = "Respaldar_Info_estaciones_v2";
			 final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
			 final int time=20000,time2=190000;
			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			 DataBaseHelper myDbHelper = new DataBaseHelper(Sincronizar.this);
			 db = myDbHelper.getWritableDatabase();
			 //desde_select=desde_select.substring(6,10)+"-"+desde_select.substring(3,5)+"-"+desde_select.substring(0,2);
			 //hasta_select=hasta_select.substring(6,10)+"-"+hasta_select.substring(3,5)+"-"+hasta_select.substring(0,2);
			 desarrollo_select=desarrollo_select.trim();

			 /*c_estaciones = db.rawQuery("SELECT ubicacion,contrato,desarrollo,idCentro,IdPresupuestoControl  " +
					 "FROM estaciones " +
					 "WHERE fecha_inicio is not null and fecha_inicio != '' " +
					 "and fecha_fin is not null and fecha_fin != '' " +
					 "and desarrollo = '"+desarrollo_select+"'" ,null);  //cambiar logica para que envie lo que capturaron en estaciones detalle*/ //Original

			 c_estaciones = db.rawQuery("SELECT e.ubicacion,e.contrato,e.desarrollo,e.idCentro,e.IdPresupuestoControl,ed.UUID,ed.cantidad " +
					 "from estaciones e " +
					 "inner JOIN estaciones_detalle ed " +
					 "on e.id_estacion=ed.id_estacion " +
					 "and e.idCentro=ed.id_centro " +
					 "where e.desarrollo='"+desarrollo_select+"' and e.fecha_inicio is not null and e.fecha_inicio != '' " ,null);


			 String bodyXml="";

			 if (c_estaciones.moveToFirst()) {
				 for (int i = 0; i < c_estaciones.getCount(); i++) {
					 bodyXml=bodyXml+"<Estaciones>" +
							 "<ubicacion>"+c_estaciones.getString(c_estaciones.getColumnIndex("ubicacion"))+"</ubicacion>" +
							 "<contrato>"+c_estaciones.getString(c_estaciones.getColumnIndex("contrato"))+"</contrato>" +
							 "<desarrollo>"+c_estaciones.getString(c_estaciones.getColumnIndex("desarrollo"))+"</desarrollo>"+
							 "<idCentro>"+c_estaciones.getString(c_estaciones.getColumnIndex("idCentro"))+"</idCentro>" +
							 "<IdPresupuestoControl>"+c_estaciones.getString(c_estaciones.getColumnIndex("IdPresupuestoControl"))+"</IdPresupuestoControl>" +
							 "<nombre_db>"+nombre_db1+"</nombre_db>" +
							 "<nu>"+Variables_Universales.getId_usuario()+"</nu>" +  //nuevo para enviar el usuario que sincroniza
							 "<uuid_android>"+c_estaciones.getString(c_estaciones.getColumnIndex("UUID"))+"</uuid_android>" +
							 "<cant>"+c_estaciones.getString(c_estaciones.getColumnIndex("cantidad"))+"</cant>" +
							 "</Estaciones>"; //hacer un iner con estaciones_detalle y mandar uuid, cantidad y id_usuario  y en el store que llegue como uuid android
					 c_estaciones.moveToNext();
				 }
			 }

			 /*//Enviar estaciones detalle
			 c_est_det = db.rawQuery("SELECT id_estacion,id_centro,UUID,cantidad  " +
					 "FROM estaciones_detalle",null);

			 if (c_est_det.moveToFirst()) {
				 for (int i = 0; i < c_est_det.getCount(); i++) {
					 bodyXml=bodyXml+"<est_det>" +
							 "<id_est>"+c_est_det.getString(c_est_det.getColumnIndex("id_estacion"))+"</id_est>" +
							 "<id_c>"+c_est_det.getString(c_est_det.getColumnIndex("id_centro"))+"</id_c>" +
							 "<UUID>"+c_est_det.getString(c_est_det.getColumnIndex("UUID"))+"</UUID>"+
							 "<cant>"+c_est_det.getString(c_est_det.getColumnIndex("cantidad"))+"</cant>" +
							 "</est_det>";
					 c_est_det.moveToNext();
				 }
			 }*/

			 bodyXml = "<Principal>"+bodyXml+"</Principal>";
			 request.addProperty("soap", bodyXml);
			 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.dotNet = true;

			 envelope.setOutputSoapObject(request);
			 HttpTransportSE transporte = new HttpTransportSE(URL);

			 try
			 {
				 transporte.call(SOAP_ACTION, envelope);
				 SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
				 String mensaje = resultado_xml.toString();
				 if(mensaje.contentEquals("true")){
					 return true;
				 }
				 else {return  false;}
			 }
			 catch (Exception e)
			 {
				 return false;
			 }
		 }

		 protected void onPostExecute(final Boolean success)
		 {
			 if (this.dialog.isShowing())
			 {
				 this.dialog.dismiss();
			 }
			 if (success)
			 {
				 //Toast.makeText(Sincronizar.this, "Sincronización Exitosa", Toast.LENGTH_SHORT).show();
			 }
			 else
			 {
				 //Toast.makeText(Sincronizar.this, "Error de Sincronización", Toast.LENGTH_SHORT).show();
			 }
		 }
	 }

	 //**********  Descargar imagen   *******/////////
	 public class Descarga_Imagen extends AsyncTask<String, Void, Boolean>
	 {
		 private final ProgressDialog dialog = new ProgressDialog(Sincronizar.this);

		 @Override
		 protected void onPreExecute()
		 {
			 //this.dialog.setMessage("Descargando Información...");
			 //this.dialog.show();
		 }

		 protected Boolean doInBackground(final String... args)
		 {

			 //consultorio = con.DAO_Select_Nombre_Consultorio();

			 final String NAMESPACE = "http://Serv.net/";
			 /*final String URL = "http://" + ip_servidor1 + "/WS_construc/ServicioClientes.asmx";*/
			 final String URL = "http://" + ip_servidor1 + "/WS_construc/ServicioClientes.asmx";
			 final String METHOD_NAME = "select_foto";
			 final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
			 final int time = 20000, time2 = 190000;
			 final String nombre_db = nombre_db1;

			 String nuevo = "<?xml version=\"1.0\"  encoding=\"utf-8\"?><principal>";
			 List<XmlValuesModel> myData = null;

			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			 request.addProperty("nombre_bd", nombre_db);

			 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.dotNet = true;
			 envelope.setOutputSoapObject(request);
			 HttpTransportSE transporte = new HttpTransportSE(URL);

			 try
			 {
				 transporte.call(SOAP_ACTION, envelope);
				 SoapPrimitive resSoap =(SoapPrimitive)envelope.getResponse();


				 nuevo = nuevo + resSoap.toString() + "</principal>";

				 BufferedReader br=new BufferedReader(new StringReader(nuevo));
				 InputSource is=new InputSource(br);

				 /************  Parse XML **************/
/*

				 if(Looper.getMainLooper()==null){
					 Looper.prepare();
				 }
*/

				 if (Looper.myLooper() == null)
				 {
					 Looper.prepare();
				 }

				 XMLParser_Fotos parser=new XMLParser_Fotos();
				 SAXParserFactory factory= SAXParserFactory.newInstance();
				 SAXParser sp=factory.newSAXParser();
				 XMLReader reader=sp.getXMLReader();
				 reader.setContentHandler(parser);
				 reader.parse(is);

				/* ************ Get Parse data in a ArrayList **********/
				 myData=parser.listXML;
				 //AlertaTermino(getResources().getString(string.m1));

				 if(myData!=null)
				 {
					 String OutputData = "";
				 }
				 return true;
			 }
			 catch (Exception e)
			 {
				 return false;
			 }
		 }

		 protected void onPostExecute(final Boolean success)
		 {
			 if (this.dialog.isShowing())
			 {
				 //this.dialog.dismiss();
			 }
			 if (success)
			 {
				 //Toast.makeText(Sincronizar.this, "Sincronización DE FOTOS", Toast.LENGTH_SHORT).show();
			 }
			 else
			 {
				 //Toast.makeText(Sincronizar.this, "No FOTOS", Toast.LENGTH_SHORT).show();
			 }
		 }
	 }


public void AlertaEnviarLecturasAgua(){
	

 	AlertDialog.Builder myDialog
   = new AlertDialog.Builder(Sincronizar.this);
  myDialog.setTitle("¿Desea enviar la información al servidor? \n\n");
  TextView textView = new TextView(this);
  LayoutParams textViewLayoutParams
   = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
  textView.setLayoutParams(textViewLayoutParams);
  //textView.setTranslationX(10);

  
  LinearLayout layout = new LinearLayout(this);
  layout.setOrientation(LinearLayout.VERTICAL);

  layout.addView(textView);

  
  myDialog.setView(layout);
      
  myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface arg0, int arg1) {
     	
     	 //insertaenDispositivo();
		  /*Enviar_Info task = new Enviar_Info();
		  task.execute();*/
    	  
        
       }
      });
  
  myDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

	  public void onClick(DialogInterface arg0, int arg1) {


	  }
  });
  
 myDialog.show();
}

	 public static void llena_fecha_sinc(int cual){
		 if(cual==1) {
			 EditText et_fecha_desde = (EditText)Variables_Universales.getView_gen().findViewById(R.id.editText2);
			 et_fecha_desde.setText(Variables_Universales.getFecha_inicio_trat());
		 }
		 else{
			 EditText et_fecha_hasta=(EditText)Variables_Universales.getView_gen().findViewById(R.id.editText3);
			 et_fecha_hasta.setText(Variables_Universales.getFecha_inicio_trat());
		 }
	 }

	 public void Elimina(){
		 con.DAO_Elimina_todo();
		 AlertaSeElimino();
	 }

}

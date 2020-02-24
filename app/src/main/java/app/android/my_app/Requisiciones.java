package app.android.my_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import DAO.DataBaseHelper;
import DTO.Alert;
import DTO.AlertCalendar;
import DTO.CustomAdapter;
import DTO.CustomAdapter_Insumos;
import DTO.Valores_Recibidos_Insumos;
import DTO.Variables_Universales;
import DTO.XMLParser_2;
import DTO.XMLParser_3;
import DTO.XMLParser_4;
import DTO.consultas;

public class Requisiciones extends AppCompatActivity {

    TextView tv_user,tv_descrip_proy;//,tv_fecha,tv_requerido_para;
String ip_servidor,desarrollo_select,nombre_db,requi_select="",id_proyecto,solicitante_select,aut_tecnica_select,aut_compra_select,descrip,clave_desarrollo;
    EditText et_codigo_aux,et_contrato,et_obser,et_lugar_entr,et_fecha_desde,et_fecha_hasta;
    Button bt_fecha,bt_fecha_requerido_para;
    ImageButton ib_guarda_actualiza,ib_sincroniza;
    Spinner sp_proyecto,sp_requi,sp_solicitante,sp_aut_tecnica,sp_aut_copra;
    ListView lv_insumos;
    Integer cont=0,cont2=0;
    double por_reque=0,total=0, dif=0;
    ArrayList<String> al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_observaciones,al_id,al_por_req,al_cant_tot,al_id_explosion_ins;
    ArrayList<Integer> al_partida;
    private String pregunt,s_codigo,s_descripcion,s_unidad,s_moneda,s_por_requerir,s_total_global,s_observ,s_ids,id_req="",s_id_explosion;;
    CustomAdapter_Insumos customAdapter;
    Variables_Universales var;
    AlertCalendar AC;
    consultas con;
    ArrayAdapter adap_requi;
    private SQLiteDatabase db;
    private Cursor cursor,cursor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisiciones);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        var= new Variables_Universales();
        var.setContext(this);
        var.setView_gen(getWindow().getDecorView().getRootView());
        var.setActivity_gral(Requisiciones.this);
        var.setIs_from_guarda(false);
        AC=new AlertCalendar();
        con=new consultas();

        con.DAO_Elimina_insumos_temporal();

        //Listview
        lv_insumos=(ListView)findViewById(R.id.lv_insumos_r);
        lv_insumos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                view.onTouchEvent(motionEvent);
                return true;
            }
        });

        consultas lista=new consultas();
        lista=con.DAO_Lista_preguntas_respuestas();  //******************************************************
        al_codigo = new ArrayList<String>(Arrays.asList(lista.arreglo));
        al_descrip = new ArrayList<String>(Arrays.asList(lista.arreglo2));
        al_unidad = new ArrayList<String>(Arrays.asList(lista.arreglo_3));
        al_moneda = new ArrayList<String>(Arrays.asList(lista.arreglo_4));
        al_cantidad = new ArrayList<String>(Arrays.asList(lista.arreglo_5));
        al_observaciones = new ArrayList<String>(Arrays.asList(lista.arreglo_6));
        al_id = new ArrayList<String>(Arrays.asList(lista.arreglo_7));
        al_id_explosion_ins = new ArrayList<String>(Arrays.asList(lista.arreglo_9));

        if(al_codigo.size()==0){
            al_codigo=new ArrayList<String>(1);
            al_descrip=new ArrayList<String>(1);
            al_unidad=new ArrayList<String>(1);
            al_moneda=new ArrayList<String>(1);
            al_cantidad=new ArrayList<String>(1);
            al_observaciones=new ArrayList<String>(1);
            al_id=new ArrayList<String>(1);

            al_codigo.add("Clic aquí");
            al_descrip.add("");
            al_unidad.add("");
            al_moneda.add("");
            al_cantidad.add("");
            al_observaciones.add("");
            al_id.add("");
            al_id_explosion_ins.add("");
        }
        customAdapter=new CustomAdapter_Insumos(Requisiciones.this, al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_observaciones,al_id,al_id,al_id_explosion_ins);
        //lv_insumos.destroyDrawingCache();
        lv_insumos.setAdapter(customAdapter);

        //TextView
        tv_user=(TextView)findViewById(R.id.textView36);
        tv_user.setText(var.getNombre_usuario());
        tv_descrip_proy=(TextView)findViewById(R.id.textView41);

        //EditText
        et_codigo_aux=(EditText)findViewById(R.id.editText13);
        et_contrato=(EditText)findViewById(R.id.editText14);
        et_obser=(EditText)findViewById(R.id.editText15);
        et_lugar_entr=(EditText)findViewById(R.id.editText16);
        et_fecha_desde = (EditText)Variables_Universales.getView_gen().findViewById(R.id.editText9);
        et_fecha_hasta = (EditText)Variables_Universales.getView_gen().findViewById(R.id.editText12);

        //Buttons
        bt_fecha=(Button)findViewById(R.id.button8);
        bt_fecha_requerido_para=(Button)findViewById(R.id.button9);

        //ImageButtons
        ib_guarda_actualiza=(ImageButton)findViewById(R.id.guarda_actualiza);
        ib_sincroniza=(ImageButton)findViewById(R.id.sincroniza);

        //Spinners
        sp_proyecto=(Spinner)findViewById(R.id.spinner);
        sp_solicitante=(Spinner)findViewById(R.id.spinner3);
        sp_aut_copra=(Spinner)findViewById(R.id.spinner5);
        sp_aut_tecnica=(Spinner)findViewById(R.id.spinner4);
        sp_requi=(Spinner)findViewById(R.id.spinner2);

        //Eventos Spinners
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_text_layout, con.DAO_Spinner_Desarrollo(Variables_Universales.getId_usuario()));
        //ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_text_layout, con.DAO_Spinner_Frente());
        adapter.setDropDownViewResource(R.layout.spinner_text_layout);
        sp_proyecto.setAdapter(adapter);

        sp_proyecto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                clave_desarrollo=sp_proyecto.getSelectedItem().toString();
                descrip=con.DAO_Proyecto_descrip(sp_proyecto.getSelectedItem().toString());
                tv_descrip_proy.setText(descrip);
                id_proyecto = con.DAO_Desarrollo_Sync(descrip);
                var.setId_proyecto_select(id_proyecto);

                //consultas con=new consultas();

                adap_requi = new ArrayAdapter(Requisiciones.this, R.layout.spinner_text_layout, con.DAO_Spinner_Requi(sp_proyecto.getSelectedItem().toString()));
                adap_requi.setDropDownViewResource(R.layout.spinner_text_layout);
                sp_requi.setAdapter(adap_requi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.spinner_text_layout, con.DAO_Spinner_Responsables());
        adapter2.setDropDownViewResource(R.layout.spinner_text_layout);
        sp_solicitante.setAdapter(adapter2);

        sp_solicitante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                solicitante_select=sp_solicitante.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        sp_requi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //requi_select es el id de la requisicion
                requi_select=con.DAO_Select_id_from_folio(sp_proyecto.getSelectedItem().toString(),sp_requi.getSelectedItem().toString());
                //requi_select=sp_requi.getSelectedItem().toString();
                String val=con.DAO_llena_sp_from_requi(requi_select,"solicitante");
                selectSpinnerValue(sp_solicitante,val);
                 val=con.DAO_llena_sp_from_requi(requi_select,"aut_tecnica");
                selectSpinnerValue(sp_aut_tecnica,val);
                 val=con.DAO_llena_sp_from_requi(requi_select,"aut_compra");
                selectSpinnerValue(sp_aut_copra,val);

                val=con.DAO_llena_sp_from_requi(requi_select,"fecha");
                et_fecha_desde.setText(val);
                val=con.DAO_llena_sp_from_requi(requi_select,"fecha_req");
                et_fecha_hasta.setText(val);

                val=con.DAO_llena_sp_from_requi(requi_select,"codigo_aux");
                et_codigo_aux.setText(val);
                val=con.DAO_llena_sp_from_requi(requi_select,"contrato");
                et_contrato.setText(val);
                val=con.DAO_llena_sp_from_requi(requi_select,"observ_requi");
                et_obser.setText(val);
                val=con.DAO_llena_sp_from_requi(requi_select,"lugar_entrega");
                et_lugar_entr.setText(val);
                con.DAO_Elimina_insumos_temporal();
                refresca_lista_limpia();

                if(!requi_select.contentEquals("Nueva")){
                Llena_lista_from_insumos_requi(requi_select);
                Refresca_lista();
                var.setIsFrom_requi_guardada(true);
                }
                else{
                    var.setIsFrom_requi_guardada(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ArrayAdapter adapter3 = new ArrayAdapter(this, R.layout.spinner_text_layout, con.DAO_Spinner_Responsables());
        //adapter3.setDropDownViewResource(R.layout.spinner_text_layout);
        sp_aut_tecnica.setAdapter(adapter2);

        sp_aut_tecnica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                aut_tecnica_select=sp_aut_tecnica.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.spinner_text_layout, con.DAO_Spinner_Responsables());
        //adapter2.setDropDownViewResource(R.layout.spinner_text_layout);
        sp_aut_copra.setAdapter(adapter2);

        sp_aut_copra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                aut_compra_select=sp_aut_copra.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Eventos Buttons
        bt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables_Universales.setCalendar_desde("sincro_desde");
                AC.dialogee.show();
            }
        });

        bt_fecha_requerido_para.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables_Universales.setCalendar_desde("sincro_hasta");
                AC.dialogee.show();
            }
        });

        //Eventos Image Buttons

        ib_sincroniza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(var.isIsFrom_requi_guardada()){
                    String[] ip_db = con.DAO_IP_Servidor(1); // numero 1 para seleccionar la conexion de NEODATA
                    ip_servidor = ip_db[0];
                    nombre_db = ip_db[1];

                    if(con.DAO_actualiza_compara_is_sincronizado(requi_select,sp_proyecto.getSelectedItem().toString())){
                        Enviar_Info_insumos task=new Enviar_Info_insumos();
                        task.execute();
                    }else{
                        Alerta("Esta requisición ya había sido enviada");
                    }


                }
                else{
                    Alerta("Por favor primero guarde la requisición para después sincronizarla");
                }

            }
        });

        ib_guarda_actualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cont=0;
                cont2=0;
                for (int i = 0; i < lv_insumos.getCount(); i++) {

                    Variables_Universales.setIs_from_guarda(true);
                            View v3 = lv_insumos.getAdapter().getView(i, null, lv_insumos);
                            //View v3=lv_insumos.getChildAt(i);
                            if (v3 != null) {
                                EditText et_por_pedir=(EditText)v3.findViewById(R.id.et_por_pedir);
                                EditText et_o=(EditText)v3.findViewById(R.id.et_observaciones);
                                TextView textView6 = (TextView) v3.findViewById(R.id.tv_cantidad); //Por requerir global

                                if(et_por_pedir.getText().toString().contentEquals("")){
                                    cont++;
                                }
                                else {
                                    por_reque=Double.parseDouble(et_por_pedir.getText().toString());
                                    total=Double.parseDouble(textView6.getText().toString());
                                    if(por_reque>total){
                                        cont2++;
                                    }
                                }
                            }
                        }
                if(sp_solicitante.getSelectedItem().toString().contentEquals("") || et_fecha_desde.getText().toString().contentEquals("") || et_fecha_hasta.getText().toString().contentEquals("")){

                    Alerta("Existen campos obligatorios que se encuentran vacíos, por favor complete la información");
                }

                else if(cont>0 && cont2>0){
                    Alerta("Existen "+cont+" registros donde NO se capturó la cantidad a solicitar y hay "+cont2+" registros donde la cantidad por pedir es mayor a la cantidad total existente\nPor favor revise la información");
                }else if(cont>0){
                    Alerta("Existen "+cont+" registros donde NO se capturó la cantidad a solicitar\nPor favor complete la información");

                }
                else if(cont2>0){
                    Alerta("Existen "+cont2+" registros donde la cantidad por pedir es mayor a la cantidad total existente\nPor favor revise la información");

                }
                /*if(1==2){

                }*/

                else{

                    if(sp_requi.getSelectedItem().toString().contentEquals("Nueva")){
                        id_req="A-"+UUID.randomUUID().toString().substring(0,8);
                    }
                    else {
                        id_req=sp_requi.getSelectedItem().toString();
                        con.DAO_Guarda_Actualiza_Insumos_Requisicion2(id_req,id_proyecto); //Aqui se borra la info por que si eliminan un insumo de una requi
                        //previamente guardada se deben eliminar todos para poder refrescar
                    }

                boolean e=con.DAO_Guarda_Actualiza_Requisicion(id_req,sp_proyecto.getSelectedItem().toString(),sp_solicitante.getSelectedItem().toString(),
                        et_fecha_desde.getText().toString(),et_fecha_hasta.getText().toString(),et_codigo_aux.getText().toString(),et_contrato.getText().toString(),
                        sp_aut_tecnica.getSelectedItem().toString(),sp_aut_copra.getSelectedItem().toString(),et_obser.getText().toString(),et_lugar_entr.getText().toString());

                if(e) {

                    int cont_renglon=10;

                    for (int i = 0; i < lv_insumos.getCount(); i++) {

                        //View v3=lv_insumos.getChildAt(i);
                        View v3 = lv_insumos.getAdapter().getView(i, null, lv_insumos);
                        if (v3 != null) {
                            //EditText
                            EditText et_por_pedir=(EditText)v3.findViewById(R.id.et_por_pedir);
                            EditText et_o=(EditText)v3.findViewById(R.id.et_observaciones);

                            //Textview
                            TextView textView6 = (TextView) v3.findViewById(R.id.tv_cantidad); //Por requerir global
                            TextView tv1 = (TextView) v3.findViewById(R.id.tv_descrip);
                            TextView tv2 = (TextView) v3.findViewById(R.id.tv_unidad);
                            TextView tv3 = (TextView) v3.findViewById(R.id.tv_moneda);
                            TextView tv_id = (TextView) v3.findViewById(R.id.textView52);
                            TextView tv_id_explosion = (TextView) v3.findViewById(R.id.textView73);

                            //Button
                            Button btn_cod=(Button)v3.findViewById(R.id.btn_insumos);

                            s_ids = tv_id.getText().toString();
                            s_codigo = btn_cod.getText().toString();
                            s_descripcion = tv1.getText().toString();
                            s_unidad = tv2.getText().toString();
                            s_moneda = tv3.getText().toString();
                            s_por_requerir = et_por_pedir.getText().toString();
                            s_total_global = textView6.getText().toString();
                            s_observ = et_o.getText().toString();
                            s_id_explosion = tv_id_explosion.getText().toString();

                            //Consulta para insertar/actualizar insumos de una reque
                            boolean exitoso = con.DAO_Guarda_Actualiza_Insumos_Requisicion(s_ids,id_req, s_codigo, s_descripcion, s_unidad, s_moneda,
                                    s_por_requerir, s_total_global,s_observ, String.valueOf(cont_renglon),id_proyecto,s_id_explosion);
                            cont_renglon+=10;
                        }
                    }
                    Alerta("La información se guardó exitosamente");
                    //var.setIs_from_guarda(false);

                    ArrayAdapter adap_requi = new ArrayAdapter(Requisiciones.this, R.layout.spinner_text_layout, con.DAO_Spinner_Requi(sp_proyecto.getSelectedItem().toString()));
                    adap_requi.setDropDownViewResource(R.layout.spinner_text_layout);
                    sp_requi.setAdapter(adap_requi);

                    refresca_lista_limpia();
                }
                else{
                    Alerta("Ocurrió un error al guardar la información, intente nuevamente");
                }
                }//fin else principal
            }
        });


    }
    Runnable run = new Runnable() {
        public void run() {
            //reload content

            customAdapter.notifyDataSetChanged();
            lv_insumos.invalidateViews();
            lv_insumos.refreshDrawableState();
        }
    };

    public static void llena_fecha_sinc(int cual){
        if(cual==1) {
            final EditText et_fecha_desde = (EditText)Variables_Universales.getView_gen().findViewById(R.id.editText9);
            et_fecha_desde.setText(Variables_Universales.getFecha_inicio_trat());
        }
        else{
            final EditText et_fecha_hasta=(EditText)Variables_Universales.getView_gen().findViewById(R.id.editText12);
            et_fecha_hasta.setText(Variables_Universales.getFecha_inicio_trat());
        }
    }

    public void Refresca_lista(){

        //var.getView_gen().post(new Runnable() {
           // public void run() {
                lv_insumos=(ListView)var.getView_gen().findViewById(R.id.lv_insumos_r);
                ArrayList<String> al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_observaciones,al_id,al_vacio,al_id_explosion_ins;
                consultas lista=new consultas();
                consultas con=new consultas();
                lista=con.DAO_Lista_preguntas_respuestas();  //******************************************************
                al_codigo = new ArrayList<String>(Arrays.asList(lista.arreglo));
                al_descrip = new ArrayList<String>(Arrays.asList(lista.arreglo2));
                al_unidad = new ArrayList<String>(Arrays.asList(lista.arreglo_3));
                al_moneda = new ArrayList<String>(Arrays.asList(lista.arreglo_4));
                al_cantidad = new ArrayList<String>(Arrays.asList(lista.arreglo_5));
                al_observaciones = new ArrayList<String>(Arrays.asList(lista.arreglo_6));
                al_id = new ArrayList<String>(Arrays.asList(lista.arreglo_7));
                al_vacio = new ArrayList<String>(Arrays.asList(lista.arreglo_8));
                al_id_explosion_ins = new ArrayList<String>(Arrays.asList(lista.arreglo_9));

                //al_partida = new ArrayList<Integer>(Arrays.asList(lista.arreglo_int));

                customAdapter=new CustomAdapter_Insumos(var.getActivity_gral(), al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_observaciones,al_id,al_vacio,al_id_explosion_ins);
                lv_insumos.setAdapter(customAdapter);

            //}
        //});
    }

  public void Llena_lista_from_insumos_requi(String id_requisicion){
        consultas lista=new consultas();
        consultas con=new consultas();
        con.DAO_Lista_preguntas_respuestas_temp(id_requisicion,id_proyecto);

    }

    public void refresca_lista_limpia(){
        al_codigo=new ArrayList<String>(1);
        al_descrip=new ArrayList<String>(1);
        al_unidad=new ArrayList<String>(1);
        al_moneda=new ArrayList<String>(1);
        al_cantidad=new ArrayList<String>(1);
        al_observaciones=new ArrayList<String>(1);
        al_id=new ArrayList<String>(1);
        al_id_explosion_ins=new ArrayList<String>(1);

        al_codigo.add("Clic aquí");
        al_descrip.add("");
        al_unidad.add("");
        al_moneda.add("");
        al_cantidad.add("");
        al_observaciones.add("");
        al_id.add("");
        al_id_explosion_ins.add("");

        customAdapter=new CustomAdapter_Insumos(Requisiciones.this, al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_observaciones,al_id,al_observaciones,al_id_explosion_ins);
        lv_insumos.setAdapter(customAdapter);
    }

    public void Alerta(String mensaje){

        AlertDialog.Builder myDialog
                = new AlertDialog.Builder(Requisiciones.this, AlertDialog.THEME_HOLO_LIGHT);

        myDialog.setTitle("Alerta");

        TextView textView = new TextView(this);
        textView.setText(mensaje);
        //textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.BLUE);
        textView.setTextSize(26);

        ViewGroup.LayoutParams textViewLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewLayoutParams);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        //layout.setBackgroundColor(Color.WHITE);

        layout.addView(textView);
        myDialog.setView(layout);
        myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        myDialog.show();
    }

    public void Alerta_sincroniza(String mensaje){

        AlertDialog.Builder myDialog
                = new AlertDialog.Builder(Requisiciones.this, AlertDialog.THEME_HOLO_LIGHT);

        myDialog.setTitle("Alerta");

        TextView textView = new TextView(this);
        textView.setText(mensaje);
        //textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.BLUE);
        textView.setTextSize(22);

        ViewGroup.LayoutParams textViewLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewLayoutParams);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        //layout.setBackgroundColor(Color.WHITE);

        layout.addView(textView);
        myDialog.setView(layout);
        myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                Llena_lista_from_insumos_requi(requi_select);
                Refresca_lista();
                var.setMensaje_insumos("");
            }
        });
        myDialog.show();
    }

    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

    ////*******    Enviar los insumos a verificar su cantidad de existencia   ********///////////////
    public class Enviar_Info_insumos extends AsyncTask<String, Void, Boolean>
    {
        private final ProgressDialog dialog = new ProgressDialog(Requisiciones.this);
        @Override
        protected void onPreExecute()
        {
            this.dialog.setMessage("Verificando existencia de insumos...");
            this.dialog.show();
        }
        protected Boolean doInBackground(final String... args)
        {
            String nuevo = "<?xml version=\"1.0\"  encoding=\"utf-8\"?><principal>";
            final String NAMESPACE = "http://Serv.net/";
            final String URL="http://"+ip_servidor+"/WS_NEODATA/ServicioClientes.asmx";
            final String METHOD_NAME = "verifica_insumo_cantidad";
            final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
            final int time=20000,time2=190000;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            DataBaseHelper myDbHelper = new DataBaseHelper(Requisiciones.this);
            db = myDbHelper.getWritableDatabase();

            cursor = db.rawQuery("SELECT id_insumo,por_requerir,id_proyecto " +
                    "FROM insumos_requi " +
                    "where id_requi='"+requi_select+"' and id_proyecto='"+id_proyecto+"' " ,null);
            String bodyXml="";

            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    bodyXml=bodyXml+"<requi>" +
                            "<id_insumo>"+cursor.getString(cursor.getColumnIndex("id_insumo"))+"</id_insumo>" +
                            "<por_requerir>"+cursor.getString(cursor.getColumnIndex("por_requerir"))+"</por_requerir>" +
                            "<id_proyecto>"+cursor.getString(cursor.getColumnIndex("id_proyecto"))+"</id_proyecto>" +
                            "<bd>"+nombre_db+"</bd>" +
                            "</requi>";
                    cursor.moveToNext();
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
                    var.setCantidad_correcta(true);
                    return true;
                }
                else {
                    var.setCantidad_correcta(false);
                    nuevo = nuevo + resultado_xml.toString() + "</principal>";

                    BufferedReader br=new BufferedReader(new StringReader(nuevo));
                    InputSource is=new InputSource(br);

                    /************  Parse XML **************/
                    if (Looper.myLooper() == null)
                    {
                        Looper.prepare();
                    }
                    XMLParser_3 parser=new XMLParser_3();
                    SAXParserFactory factory= SAXParserFactory.newInstance();
                    SAXParser sp=factory.newSAXParser();
                    XMLReader reader=sp.getXMLReader();
                    reader.setContentHandler(parser);
                    reader.parse(is);

                    return true;
                }

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
                //Toast.makeText(Requisiciones.this, "Sincronización Exitosa", Toast.LENGTH_SHORT).show();
                if(var.isCantidad_correcta()) {
                    //Ejecutar la funcion para insertar la requi
                    Insert_Requisicion task=new Insert_Requisicion();
                    task.execute();
                }
                else {
                    Alerta_sincroniza("Los siguientes insumos presentan cantidades excedentes, favor de ajustarlos: \n" + var.getMensaje_insumos());

                }
            }
            else
            {
                //Toast.makeText(Requisiciones.this, "Error de Sincronización", Toast.LENGTH_SHORT).show();
                Alerta("Ocurrió un error, intente nuevamente");
            }
        }
    }

    ////*******   Manda para Insertar la requisicion    ********///////////////
    public class Insert_Requisicion extends AsyncTask<String, Void, Boolean>
    {
        private final ProgressDialog dialog = new ProgressDialog(Requisiciones.this);
        @Override
        protected void onPreExecute()
        {
            this.dialog.setMessage("Enviando Información...");
            this.dialog.show();
        }
        protected Boolean doInBackground(final String... args)
        {
            String nuevo = "<?xml version=\"1.0\"  encoding=\"utf-8\"?><principal>";
            final String NAMESPACE = "http://Serv.net/";
            final String URL="http://"+ip_servidor+"/WS_NEODATA/ServicioClientes.asmx";
            final String METHOD_NAME = "Insert_Requisicion";
            final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
            final int time=20000,time2=190000;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            DataBaseHelper myDbHelper = new DataBaseHelper(Requisiciones.this);
            db = myDbHelper.getWritableDatabase();

            cursor=db.rawQuery("select rq.id,ir.id_proyecto,rq.fecha,rq.fecha_req,(select id from Responsables where nombre='"+solicitante_select+"') as id_soli," +
                    "(select id from Responsables where nombre='"+aut_tecnica_select+"') as id_aut_tec," +
                    "(select id from Responsables where nombre='"+aut_compra_select+"') as id_aut_compra," +
                    "rq.lugar_entrega,rq.codigo_aux,rq.observ_requi,rq.contrato,ir.id_explosion_ins,ir.id_insumo,ir.por_requerir,ir.observaciones,ir.renglon " +
                    "from requisiciones rq " +
                    "INNER JOIN insumos_requi ir " +
                    "ON ir.id_proyecto=(SELECT id_desarrollo from desarrollo  WHERE desarrollo='" + descrip + "') " +
                    "and ir.id_requi=rq.id WHERE rq.id='"+requi_select+"' " +
                    "and rq.proyecto='" + clave_desarrollo + "' ",null);

            String bodyXml="";
            if (cursor.moveToFirst()) {
                var.setId_requi_mandada(cursor.getString(cursor.getColumnIndex("id")));
                for (int i = 0; i < cursor.getCount(); i++) {
                    bodyXml=bodyXml+"<i_req>" +
                            "<bd>"+nombre_db+"</bd>" +
                            "<proyecto>"+cursor.getString(cursor.getColumnIndex("id_proyecto"))+"</proyecto>" +
                            "<fecha>"+cursor.getString(cursor.getColumnIndex("fecha"))+"</fecha>" +
                            "<fecha_req>"+cursor.getString(cursor.getColumnIndex("fecha_req"))+"</fecha_req>" +
                            "<id_soli>"+cursor.getString(cursor.getColumnIndex("id_soli"))+"</id_soli>" +
                            "<id_aut_tec>"+cursor.getString(cursor.getColumnIndex("id_aut_tec"))+"</id_aut_tec>" +
                            "<id_aut_compra>"+cursor.getString(cursor.getColumnIndex("id_aut_compra"))+"</id_aut_compra>" +
                            "<lugar_entrega>"+cursor.getString(cursor.getColumnIndex("lugar_entrega"))+"</lugar_entrega>" +
                            "<codigo_aux>"+cursor.getString(cursor.getColumnIndex("codigo_aux"))+"</codigo_aux>" +
                            "<observ_requi>"+cursor.getString(cursor.getColumnIndex("observ_requi"))+"</observ_requi>" +
                            "<contrato>"+cursor.getString(cursor.getColumnIndex("contrato"))+"</contrato>" +
                            "<id_explosion_ins>"+cursor.getString(cursor.getColumnIndex("id_explosion_ins"))+"</id_explosion_ins>" +
                            "<id_insumo>"+cursor.getString(cursor.getColumnIndex("id_insumo"))+"</id_insumo>" +
                            "<por_requerir>"+cursor.getString(cursor.getColumnIndex("por_requerir"))+"</por_requerir>" +
                            "<observaciones>"+cursor.getString(cursor.getColumnIndex("observaciones"))+"</observaciones>" +
                            "<renglon>"+cursor.getString(cursor.getColumnIndex("renglon"))+"</renglon>" +
                            "<idUser>"+var.getId_usuario()+"</idUser>" +
                            "</i_req>";
                    cursor.moveToNext();
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
                if(!mensaje.contentEquals("false")){

                    nuevo = nuevo + resultado_xml.toString() + "</principal>";

                    BufferedReader br=new BufferedReader(new StringReader(nuevo));
                    InputSource is=new InputSource(br);

                    /************  Parse XML **************/
                    if (Looper.myLooper() == null)
                    {
                        Looper.prepare();
                    }
                    XMLParser_4 parser=new XMLParser_4();
                    SAXParserFactory factory= SAXParserFactory.newInstance();
                    SAXParser sp=factory.newSAXParser();
                    XMLReader reader=sp.getXMLReader();
                    reader.setContentHandler(parser);
                    reader.parse(is);

                    return true;
                }
                else {

                    return false;
                }

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
                    Alerta(""+var.getMensaje_insumos());
                    var.setMensaje_insumos("");
                ArrayAdapter adap_requi = new ArrayAdapter(Requisiciones.this, R.layout.spinner_text_layout, con.DAO_Spinner_Requi(sp_proyecto.getSelectedItem().toString()));
                adap_requi.setDropDownViewResource(R.layout.spinner_text_layout);
                sp_requi.setAdapter(adap_requi);
            }
            else
            {
                Alerta("Ocurrió un error, intente nuevamente");
            }
        }
    }
}

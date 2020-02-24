package DTO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.android.my_app.R;
import app.android.my_app.Requisiciones;

/**
 * Created by Vicente on 17/08/2017.
 */

public class CustomAdapter extends BaseAdapter implements ListAdapter {

    ArrayList<String> estaciones=null;
    ArrayList<String> fecha_inicio=null;
    ArrayList<String> fecha_fin=null;
    private Context ctx;
    //private LayoutInflater mInflater=null;
    String datavalue="";
    Button estacion;
    Variables_Universales var;
    consultas con;

    public CustomAdapter (Activity activty, ArrayList<String> list, ArrayList<String> list_fi, ArrayList<String> list_ff){
        this.ctx=activty;
        //mInflater = activty.getLayoutInflater();
        this.estaciones=list;
        this.fecha_inicio=list_fi;
        this.fecha_fin=list_ff;
    }

    @Override
    public int getCount() {
        return estaciones.size();
    }

    @Override
    public Object getItem(int i) {
        return estaciones.get(i);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        if (convertView == null ) {
            LayoutInflater vi;
            vi = LayoutInflater.from(ctx);
            convertView = vi.inflate(R.layout.lista_preguntas, null);

        }

        estacion=(Button)convertView.findViewById(R.id.button2);
        datavalue=estaciones.get(position);
        estacion.setText(datavalue);
        var=new Variables_Universales();
        con=new consultas();

        if((fecha_inicio.get(position)!=null && !fecha_inicio.get(position).isEmpty()) && (fecha_fin.get(position)==null || fecha_fin.get(position).isEmpty())){
            estacion.setBackgroundColor(Color.parseColor("#08263E"));  //azul
            estacion.setEnabled(true);

        }
        else if((fecha_inicio.get(position)!=null && !fecha_inicio.get(position).isEmpty()) && (fecha_fin.get(position)!=null && !fecha_fin.get(position).isEmpty())){
            estacion.setBackgroundColor(Color.parseColor("#92be69")); //verde
            estacion.setEnabled(true);
            Variables_Universales.setEstacion_completa(true);

        }
        else{
            estacion.setBackgroundColor(Color.parseColor("#BDBDBD")); //gris

            if(var.getCalifica_orden().contentEquals("1")) {
               // if(1==2) {
                if (position != 0) {
                    if(fecha_fin.get(position-1)==null || fecha_fin.get(position-1).isEmpty()){

                        estacion.setEnabled(false);

                    } else {
                        estacion.setEnabled(true);
                    }
                } else if (position == 0) {
                    estacion.setEnabled(true);
                }
            }
        }

       estacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables_Universales.setBoton_select(((Button)view).getText().toString());

                alert_preguntas ap=new alert_preguntas();
                ap.show_Dialog(Variables_Universales.getActivity_gral());
            }
        });

        estacion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Variables_Universales.setBoton_select(((Button)view).getText().toString());
                //con.DAO_set_valores(var.getUbicacion(),var.getBoton_select(),var.getContrato(),var.getContratista());
                Alerta();
                return true;
            }
        });

        return convertView;
    }

    private static class ViewHolder {
//        TextView fecha;
        Button estacion;
    }

    public void Alerta(){

        final AlertDialog.Builder myDialog
                = new AlertDialog.Builder(Variables_Universales.getActivity_gral());
        myDialog.setTitle("Alerta");
        TextView textView = new TextView(Variables_Universales.getActivity_gral());
        textView.setText("¿Desea eliminar el insumo seleccionado?");

        ViewGroup.LayoutParams textViewLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewLayoutParams);
        LinearLayout layout = new LinearLayout(Variables_Universales.getActivity_gral());
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(textView);
        myDialog.setView(layout);
        myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                consultas con=new consultas();
                Variables_Universales var=new Variables_Universales();
                //con.DAO_Guarda_Respuesta_Detalle(var.getUbicacion(), var.getBoton_select(), var.getModelo(), var.getContrato(), et_observaciones.getText().toString(), Float.toString(rb_preguntas.getRating()), sms_enviado, email_enviado);
                con.DAO_resetea_Respuesta_Detalle(var.getUbicacion(), var.getBoton_select(), var.getId_prototipo(), var.getId_contrato(), "", "", "", "");
                con.DAO_estaciones_completa_elimina(var.getUbicacion(),var.getBoton_select(),var.getId_contrato());
                con.DAO_resetea_Respuesta(var.getUbicacion(),var.getBoton_select(),var.getId_prototipo(),var.getId_contrato());
                con.DAO_borra_estimaciones(var.getBoton_select(),var.getUbicacion());


                //equisiciones refresca = new Requisiciones();
                //refresca.Refresca_lista();
                //Seleccion_casa sc=new Seleccion_casa();
                //sc.Refresca_lista();


            }
        });

        myDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        myDialog.show();
    }

}

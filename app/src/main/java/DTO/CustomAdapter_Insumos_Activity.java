package DTO;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.android.my_app.R;

/**
 * Created by Vicente on 17/08/2017.
 */

public class CustomAdapter_Insumos_Activity extends BaseAdapter implements ListAdapter {

    ArrayList<String> preguntas=null,respuestas=null,unidad=null,moneda=null,cantidad=null,ids_insumos=null,por_requerir=null,cantidad_total=null;
     Context ctx;
     LayoutInflater mInflater=null;
    ArrayList<Integer> checkedRows;
    String datavalue="",datavalue2="",datavalue3="",datavalue4="",datavalue5="",datavalue6="",datavalue7="",datavalue8="";
    private TextView tv_codigo,tv_descripcion,tv_unidad,tv_moneda,tv_por_requerir,tv_cantidad_total,tv_ids;


    public CustomAdapter_Insumos_Activity(Activity activty, ArrayList<String> list, ArrayList<String> list_resp,
                                          ArrayList<String> list_unidad, ArrayList<String> list_moneda, ArrayList<String> list_cantidad
            , ArrayList<String> list_por_requerir, ArrayList<String> list_cantidad_total, ArrayList<String> list_ids){
        this.ctx=activty;
        mInflater = activty.getLayoutInflater();
        this.preguntas=list;
        this.respuestas=list_resp;
        this.unidad=list_unidad;
        this.moneda=list_moneda;
        this.cantidad=list_cantidad;
        this.por_requerir=list_por_requerir;
        this.cantidad_total=list_cantidad_total;
        this.ids_insumos=list_ids;
    }

    @Override
    public int getCount() {
        return preguntas.size();
    }

    @Override
    public Object getItem(int i) {
        return preguntas.get(i);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(ctx);
            convertView = vi.inflate(R.layout.lista_insumos_activity, null);
        }
        //Asignacion de valores
        datavalue=preguntas.get(position);
        datavalue2=respuestas.get(position);
        datavalue3=unidad.get(position);
        datavalue4=moneda.get(position);
        datavalue5=cantidad.get(position);
        datavalue6=por_requerir.get(position);
        datavalue7=cantidad_total.get(position);
        datavalue8=ids_insumos.get(position);

        //Textviews
        tv_codigo=(TextView) convertView.findViewById(R.id.textView21);
        tv_descripcion=(TextView)convertView.findViewById(R.id.tv_descrip);
        tv_unidad=(TextView)convertView.findViewById(R.id.tv_unidad);
        tv_moneda=(TextView)convertView.findViewById(R.id.tv_moneda);
        tv_por_requerir=(TextView) convertView.findViewById(R.id.tv_cantidad);
        tv_cantidad_total=(TextView) convertView.findViewById(R.id.textView37);
        tv_ids=(TextView)convertView.findViewById(R.id.tv_ids_insumos);

        //Set de valores
        tv_codigo.setText(datavalue);
        tv_descripcion.setText(datavalue2);
        tv_unidad.setText(datavalue3);
        tv_moneda.setText(datavalue4);
        tv_por_requerir.setText(datavalue6);
        tv_cantidad_total.setText(datavalue7);
        tv_ids.setText(datavalue8);

        return convertView;
    }

}

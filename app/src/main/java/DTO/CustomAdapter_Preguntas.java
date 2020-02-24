package DTO;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;
import app.android.my_app.R;
import java.util.ArrayList;

/**
 * Created by Vicente on 17/08/2017.
 */

public class CustomAdapter_Preguntas extends BaseAdapter implements ListAdapter {

    ArrayList<String> preguntas=null,respuestas=null,unidad=null,moneda=null,cantidad=null,ids_insumos=null,por_requerir=null,cantidad_total=null,seleccionados=null,id_explosion_ins=null;
     Context ctx;
     LayoutInflater mInflater=null;
    ArrayList<Integer> checkedRows;
    String datavalue="",datavalue2="",datavalue3="",datavalue4="",datavalue5="",datavalue6="",datavalue7="",datavalue8="",datavalue9="";
    private TextView tv_codigo,tv_descripcion,tv_unidad,tv_moneda,tv_por_requerir,tv_cantidad_total,tv_ids,tv_ids_explosion_ins;
    private CheckBox cb_insumo;
    boolean[] itemChecked;
    private String[] arrTemp,a_select;

    public CustomAdapter_Preguntas(Activity activty, ArrayList<String> list, ArrayList<String> list_resp,
                                   ArrayList<String> list_unidad, ArrayList<String> list_moneda, ArrayList<String> list_cantidad
            , ArrayList<String> list_por_requerir, ArrayList<String> list_cantidad_total, ArrayList<String> list_ids, ArrayList<String> list_seleccionados, ArrayList<String> list_id_explosion_ins){
        this.ctx=activty;
        mInflater = activty.getLayoutInflater();
        this.preguntas=list; //Todos los id del insumo
        this.respuestas=list_resp; //Todos los codigos de insumo
        this.unidad=list_unidad;
        this.moneda=list_moneda;
        this.cantidad=list_cantidad;
        this.seleccionados=list_seleccionados;
        //this.observaciones=list_observaciones;
        this.por_requerir=list_por_requerir;
        this.cantidad_total=list_cantidad_total;
        this.ids_insumos=list_ids;
        this.id_explosion_ins=list_id_explosion_ins;
        itemChecked = new boolean[list.size()];
        arrTemp = new String[preguntas.size()];
        a_select=new String[seleccionados.size()];

        Object[] mStringArray = list_ids.toArray();
        for(int i = 0; i < mStringArray.length ; i++){
            arrTemp[i]=(String)mStringArray[i];
        }

        Object[] mStringArray2 = list_seleccionados.toArray();
        for(int i = 0; i < mStringArray2.length ; i++){
            a_select[i]=(String)mStringArray2[i];
        }

        //if(a_select!=null) {
            for (int i = 0; i < itemChecked.length; i++) {
                for (int j = 0; j < a_select.length; j++) {
                    if (arrTemp[i].contentEquals(a_select[j]))
                        itemChecked[i] = true;
                }
            }
        //}
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
            convertView = vi.inflate(R.layout.lista_insumos, null);
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
        datavalue9=id_explosion_ins.get(position);

        //CheckBox
        cb_insumo=(CheckBox)convertView.findViewById(R.id.checkBox);

        cb_insumo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                itemChecked[position]=b;
            }
        });
        //cb_insumo.setChecked(itemChecked[position]);
        if(itemChecked[position])
            cb_insumo.setChecked(true);
        else
            cb_insumo.setChecked(false);

        //Textviews
        tv_codigo=(TextView) convertView.findViewById(R.id.textView21);
        tv_descripcion=(TextView)convertView.findViewById(R.id.tv_descrip);
        tv_unidad=(TextView)convertView.findViewById(R.id.tv_unidad);
        tv_moneda=(TextView)convertView.findViewById(R.id.tv_moneda);
        tv_por_requerir=(TextView) convertView.findViewById(R.id.tv_cantidad);
        tv_cantidad_total=(TextView) convertView.findViewById(R.id.textView37);
        tv_ids=(TextView)convertView.findViewById(R.id.tv_ids_insumos);//
        tv_ids_explosion_ins=(TextView)convertView.findViewById(R.id.textView74);

        //Set de valores
        tv_codigo.setText(datavalue);
        tv_descripcion.setText(datavalue2);
        tv_unidad.setText(datavalue3);
        tv_moneda.setText(datavalue4);
        tv_por_requerir.setText(datavalue6);
        tv_cantidad_total.setText(datavalue7);
        tv_ids.setText(datavalue8);
        tv_ids_explosion_ins.setText(datavalue9);

        return convertView;
    }

}

package DTO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import app.android.my_app.R;
import app.android.my_app.Requisiciones;

/**
 * Created by Vicente on 17/08/2017.
 */

public class CustomAdapter_Insumos extends BaseAdapter implements ListAdapter {

    ArrayList<String> preguntas=null,respuestas=null,unidad=null,moneda=null,cantidad=null,observaciones=null,ids=null,por_req=null,id_explosion_ins=null;
    //ArrayList<Integer> respuestas=null;
     Context ctx;
     LayoutInflater mInflater=null;
    ArrayList<Integer> checkedRows;
    String datavalue="",datavalue2="",datavalue3="",datavalue4="",datavalue5="",datavalue6="",datavalue7="",datavalue8="",datavalue9="";
    private TextView tv_descripcion,tv_unidad,tv_moneda,tv_cantidad,tv_id,tv_id_explosion_ins;

    Button insumo;
    String[]cantidades;
    private String[] arrTemp,arrTemp2,arrTemp_ids;

    public CustomAdapter_Insumos(Activity activty, ArrayList<String> list, ArrayList<String> list_resp,
                                 ArrayList<String> list_unidad, ArrayList<String> list_moneda, ArrayList<String> list_cantidad,
                                 ArrayList<String> list_observaciones, ArrayList<String> list_ids,
                                ArrayList<String> list_cant_por_req,ArrayList<String> list_id_explosion_ins){
        this.ctx=activty;
        mInflater = activty.getLayoutInflater();
        this.preguntas=list;
        this.respuestas=list_resp;
        this.unidad=list_unidad;
        this.moneda=list_moneda;
        this.cantidad=list_cantidad;
        this.observaciones=list_observaciones;
        this.ids=list_ids;
        this.id_explosion_ins=list_id_explosion_ins;
        this.por_req=list_cant_por_req;
        arrTemp = new String[preguntas.size()];
        arrTemp2 = new String[preguntas.size()];
        arrTemp_ids = new String[preguntas.size()];

        Object[] mStringArray = list_cant_por_req.toArray();

        for(int i = 0; i < mStringArray.length ; i++){
            arrTemp[i]=(String)mStringArray[i];
        }

        Object[] mStringArray2 = list_observaciones.toArray();

        for(int i = 0; i < mStringArray2.length ; i++){
            arrTemp2[i]=(String)mStringArray2[i];
        }

        Object[] mStringArray3 = list_ids.toArray();

        for(int i = 0; i < mStringArray3.length ; i++){
            arrTemp_ids[i]=(String)mStringArray3[i];
        }


        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return preguntas.size();

    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            LayoutInflater vi;
            vi = LayoutInflater.from(ctx);
            convertView = vi.inflate(R.layout.lista_insumos_requi, null);
            cantidades=new String[preguntas.size()];
            holder.editText1=(EditText)convertView.findViewById(R.id.et_por_pedir);

            holder.editText2=(EditText)convertView.findViewById(R.id.et_observaciones);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        //Asignacion de valores
        datavalue=preguntas.get(position);
        datavalue2=respuestas.get(position);
        datavalue3=unidad.get(position);
        datavalue4=moneda.get(position);
        datavalue5=cantidad.get(position);
        datavalue6=observaciones.get(position);
        datavalue7=ids.get(position);
        datavalue8=por_req.get(position);
        datavalue9=id_explosion_ins.get(position);

        //Buttons
        insumo=(Button)convertView.findViewById(R.id.btn_insumos);
        insumo.setTag(position);
        //estacion=(Button)convertView.findViewById(R.id.btn_partida);

        //Textviews
        tv_descripcion=(TextView)convertView.findViewById(R.id.tv_descrip);
        tv_unidad=(TextView)convertView.findViewById(R.id.tv_unidad);
        tv_moneda=(TextView)convertView.findViewById(R.id.tv_moneda);
        tv_cantidad=(TextView)convertView.findViewById(R.id.tv_cantidad);
        tv_id=(TextView)convertView.findViewById(R.id.textView52);
        tv_id_explosion_ins=(TextView)convertView.findViewById(R.id.textView73);

        holder.ref = position;
        holder.editText1.setText(arrTemp[position]);
        holder.editText2.setText(arrTemp2[position]);

        holder.editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                arrTemp2[holder.ref] = editable.toString();

            }
        });

        holder.editText1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                arrTemp[holder.ref] = arg0.toString();
            }
        });

        //Set de valores
        insumo.setText(datavalue);
        tv_descripcion.setText(datavalue2);
        tv_unidad.setText(datavalue3);
        tv_moneda.setText(datavalue4);
        tv_cantidad.setText(datavalue5);
        //holder.editText1.setHint(datavalue5);
        if(!Variables_Universales.isIs_from_guarda()){
            holder.editText1.setText(datavalue5);
        }
        //Variables_Universales.setIs_from_guarda(false);

        tv_id.setText(datavalue7);
        tv_id_explosion_ins.setText(datavalue9);

        insumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variables_Universales.setIs_from_guarda(false);
                consultas con= new consultas();
                con.DAO_Guarda_Insumos_temporal_cant_observaciones(arrTemp_ids,arrTemp,arrTemp2);
                alert_preguntas ap=new alert_preguntas();
                ap.show_Dialog(Variables_Universales.getActivity_gral());

            }
        });
        insumo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int posi=(Integer)view.getTag();
                String id_select=ids.get(posi);
                Alerta(id_select);
                return true;
            }
        });
        return convertView;
    }

    public void Alerta(final String id){

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

                con.DAO_Elimina_Registro_insumos_temporal(id);

                Requisiciones refresca = new Requisiciones();
                refresca.Refresca_lista();
            }
        });

        myDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        myDialog.show();
    }

    private class ViewHolder {
        TextView textView1;
        EditText editText1,editText2;
        int ref;

    }

}

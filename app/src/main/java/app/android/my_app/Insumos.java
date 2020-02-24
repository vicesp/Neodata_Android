package app.android.my_app;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import DTO.Alert_insumos;
import DTO.CustomAdapter_Insumos;
import DTO.CustomAdapter_Insumos_Activity;
import DTO.CustomAdapter_Preguntas;
import DTO.Variables_Universales;
import DTO.alert_preguntas;
import DTO.consultas;

public class Insumos extends AppCompatActivity {

    Variables_Universales var;
    ArrayList<String> al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_observaciones,al_por_requi,al_cantidad_total,al_ids;
    ArrayList<Integer> al_respuestas;
    ListView lv_preguntas;
    TextView tv_user;
    private String pregunt,s_codigo,s_descripcion,s_unidad,s_moneda,s_por_requerir,s_total_global;

    ImageButton cerrar;
    TextView tv_boton_sel;
    consultas con;
    private EditText et_b_descrip,et_b_codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insumos);

        con=new consultas();
        var=new Variables_Universales();

        final ArrayList<String> array_sort_all_codigo,array_sort_all_unidad,array_sort_all_moneda,
                array_sort_all_cantidad,array_sort_all_por_requerir,array_sort_all_cant_total,
                array_sort_all_id,array_sort_all_select,array_sort_all_id_explos_ins ,array_sort,
                array_sort_familias, array_sort_all,array_sort_all_cel, array_sort_all2,array_sort_all3,array_celular;

        array_sort_all = new ArrayList<String>();
        array_sort_all_codigo = new ArrayList<String>();
        array_sort_all_unidad = new ArrayList<String>();
        array_sort_all_moneda = new ArrayList<String>();
        array_sort_all_cantidad = new ArrayList<String>();
        array_sort_all_por_requerir = new ArrayList<String>();
        array_sort_all_cant_total = new ArrayList<String>();
        array_sort_all_id = new ArrayList<String>();
        array_sort_all_select = new ArrayList<String>();
        array_sort_all_id_explos_ins = new ArrayList<String>();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_user=(TextView)findViewById(R.id.textView65);
        tv_user.setText(var.getNombre_usuario());

        et_b_descrip=(EditText)findViewById(R.id.editText11);
        et_b_codigo=(EditText)findViewById(R.id.editText);

        lv_preguntas=(ListView)findViewById(R.id.lv_ins);
        consultas lista=new consultas();
        lista=con.DAO_Lista_insumos_all();  //Para que aparezcan todos los insumos
        al_codigo = new ArrayList<String>(Arrays.asList(lista.arreglo));
        al_descrip = new ArrayList<String>(Arrays.asList(lista.arreglo2));
        al_unidad = new ArrayList<String>(Arrays.asList(lista.arreglo_3));
        al_moneda = new ArrayList<String>(Arrays.asList(lista.arreglo_4));
        al_cantidad = new ArrayList<String>(Arrays.asList(lista.arreglo_5));
        al_observaciones = new ArrayList<String>(Arrays.asList(lista.arreglo_6));
        al_por_requi = new ArrayList<String>(Arrays.asList(lista.arreglo_7));
        al_cantidad_total = new ArrayList<String>(Arrays.asList(lista.arreglo_8));
        al_ids = new ArrayList<String>(Arrays.asList(lista.arreglo_9));

        CustomAdapter_Insumos_Activity customAdapter=new CustomAdapter_Insumos_Activity(Insumos.this, al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_por_requi,al_cantidad_total,al_ids);
        lv_preguntas.setAdapter(customAdapter);

        lv_preguntas.showContextMenu();
        lv_preguntas.setScrollingCacheEnabled(true);
        lv_preguntas.setScrollbarFadingEnabled(true);
        lv_preguntas.setFastScrollEnabled(true);
        lv_preguntas.setCacheColorHint(0);

        array_sort_all.addAll(al_descrip);
        array_sort_all_codigo.addAll(al_codigo);

        array_sort_all2 = new ArrayList<String>();
        array_sort_all2.addAll(array_sort_all);

        array_sort_all3 = new ArrayList<String>();
        array_sort_all3.addAll(array_sort_all_codigo);

        et_b_codigo.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_search, 0, 0, 0);
        et_b_descrip.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_search, 0, 0, 0);

        et_b_codigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                et_b_codigo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                int textlength = et_b_codigo.getText().length();

                array_sort_all.clear();
                array_sort_all_cant_total.clear();
                array_sort_all_cantidad.clear();
                array_sort_all_codigo.clear();
                array_sort_all_id.clear();
                array_sort_all_id_explos_ins.clear();
                array_sort_all_moneda.clear();
                array_sort_all_por_requerir.clear();
                array_sort_all_select.clear();
                array_sort_all_unidad.clear();

                for (int i = 0; i < array_sort_all3.size(); i++) {
                    if (textlength <= array_sort_all3.get(i).length()) {

                        if (array_sort_all3.get(i).toLowerCase().contains(et_b_codigo.getText().toString().toLowerCase().trim())) {
                            array_sort_all.add(array_sort_all2.get(i));
                            array_sort_all_cant_total.add(al_cantidad_total.get(i));
                            array_sort_all_cantidad.add(al_cantidad.get(i));
                            array_sort_all_codigo.add(al_codigo.get(i));
                            array_sort_all_id.add(al_ids.get(i));
                            array_sort_all_moneda.add(al_moneda.get(i));
                            array_sort_all_por_requerir.add(al_por_requi.get(i));
                            array_sort_all_unidad.add(al_unidad.get(i));
                        }
                    }
                }
                //cambiar los al_* por los array_sort_all*
                CustomAdapter_Insumos_Activity customAdapter=new CustomAdapter_Insumos_Activity(Insumos.this, array_sort_all_codigo,
                        array_sort_all,array_sort_all_unidad,array_sort_all_moneda,array_sort_all_cantidad,array_sort_all_por_requerir,array_sort_all_cant_total,array_sort_all_id);
                lv_preguntas.setAdapter(customAdapter);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_b_descrip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_b_descrip.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                int textlength = et_b_descrip.getText().length();

                array_sort_all.clear();
                array_sort_all_cant_total.clear();
                array_sort_all_cantidad.clear();
                array_sort_all_codigo.clear();
                array_sort_all_id.clear();
                array_sort_all_id_explos_ins.clear();
                array_sort_all_moneda.clear();
                array_sort_all_por_requerir.clear();
                array_sort_all_select.clear();
                array_sort_all_unidad.clear();

                for (int i = 0; i < array_sort_all2.size(); i++) {
                    if (textlength <= array_sort_all2.get(i).length()) {

                        if (array_sort_all2.get(i).toLowerCase().contains(et_b_descrip.getText().toString().toLowerCase().trim())) {
                            array_sort_all.add(array_sort_all2.get(i));
                            array_sort_all_cant_total.add(al_cantidad_total.get(i));
                            array_sort_all_cantidad.add(al_cantidad.get(i));
                            array_sort_all_codigo.add(al_codigo.get(i));
                            array_sort_all_id.add(al_ids.get(i));
                            array_sort_all_moneda.add(al_moneda.get(i));
                            array_sort_all_por_requerir.add(al_por_requi.get(i));
                            array_sort_all_unidad.add(al_unidad.get(i));
                        }
                    }
                }
                CustomAdapter_Insumos_Activity customAdapter=new CustomAdapter_Insumos_Activity(Insumos.this, array_sort_all_codigo,
                        array_sort_all,array_sort_all_unidad,array_sort_all_moneda,array_sort_all_cantidad,array_sort_all_por_requerir,array_sort_all_cant_total,array_sort_all_id);
                lv_preguntas.setAdapter(customAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

}
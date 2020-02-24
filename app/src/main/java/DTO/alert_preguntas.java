package DTO;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import app.android.my_app.R;
import app.android.my_app.Requisiciones;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class alert_preguntas extends DialogFragment {

    ArrayList<String> al_select,al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_observaciones,al_por_requi,al_cantidad_total,al_ids,al_id_explosion_ins;
    ArrayList<Integer> al_respuestas;
     ListView lv_preguntas;
    View vi;
    Dialog dialog;
    Boolean is_active;
    private String pregunt,s_codigo,s_descripcion,s_unidad,s_moneda,s_por_requerir,s_total_global,id_explosion_ins;
    Button guardar;
    ImageButton cerrar,seleccionar_nuevo;
    TextView tv_boton_sel;
    consultas con;
    private EditText et_b_descrip,et_b_codigo;

    Variables_Universales var;
    String[] Respuesta_observ=null;
    String sms_enviado="0",email_enviado="0";


    public void show_Dialog(final Activity activity) {
         dialog = new Dialog(activity);
        //Variables.setDialog(dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_alert_preguntas);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        con=new consultas();
        var=new Variables_Universales();
        var.setContext(activity.getApplicationContext());

        lv_preguntas=(ListView)dialog.findViewById(R.id.lv_ap_preguntas);
        lv_preguntas.setOnTouchListener(new View.OnTouchListener() {
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


        tv_boton_sel=(TextView)dialog.findViewById(R.id.tv_boton_select);
        //tv_boton_sel.setText(Variables_Universales.getBoton_select());

        guardar=(Button)dialog.findViewById(R.id.btn_ap_guarda);
        seleccionar_nuevo=(ImageButton)dialog.findViewById(R.id.imageButton2);
        cerrar=(ImageButton) dialog.findViewById(R.id.ib_ap_cerrar);
        et_b_descrip=(EditText)dialog.findViewById(R.id.et_b_descrip);
        et_b_codigo=(EditText)dialog.findViewById(R.id.editText10);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        consultas lista=new consultas();
        lista=con.DAO_Lista_insumos_popup(var.getId_proyecto_select());
        al_codigo = new ArrayList<String>(Arrays.asList(lista.arreglo));
        al_descrip = new ArrayList<String>(Arrays.asList(lista.arreglo2));
        al_unidad = new ArrayList<String>(Arrays.asList(lista.arreglo_3));
        al_moneda = new ArrayList<String>(Arrays.asList(lista.arreglo_4));
        al_cantidad = new ArrayList<String>(Arrays.asList(lista.arreglo_5));
        al_observaciones = new ArrayList<String>(Arrays.asList(lista.arreglo_6));
        al_por_requi = new ArrayList<String>(Arrays.asList(lista.arreglo_7));
        al_cantidad_total = new ArrayList<String>(Arrays.asList(lista.arreglo_8));
        al_ids = new ArrayList<String>(Arrays.asList(lista.arreglo_9));
        al_id_explosion_ins = new ArrayList<String>(Arrays.asList(lista.arreglo_10));

        consultas li=new consultas();
        li=con.DAO_Select_Insumo_Checked(al_codigo.size());

        //ArrayList<String> a_p = new ArrayList<String>(al_codigo.size());
        //a_p.addAll(Arrays.asList(li.a_select));
        al_select = new ArrayList<String>(Arrays.asList(li.a_select));

        CustomAdapter_Preguntas customAdapter=new CustomAdapter_Preguntas(activity, al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_por_requi,al_cantidad_total,al_ids,al_select,al_id_explosion_ins);
        lv_preguntas.setAdapter(customAdapter);

        lv_preguntas.showContextMenu();
        lv_preguntas.setScrollingCacheEnabled(true);
        lv_preguntas.setScrollbarFadingEnabled(true);
        lv_preguntas.setFastScrollEnabled(true);
        lv_preguntas.setCacheColorHint(0);

        String Nombre_PT[],cel[];
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
        //array_sort_all_cel = new ArrayList<String>();

        array_sort_all.addAll(al_descrip);
        array_sort_all_codigo.addAll(al_codigo);

        array_sort_all2 = new ArrayList<String>();
        array_sort_all2.addAll(array_sort_all);

        array_sort_all3 = new ArrayList<String>();
        array_sort_all3.addAll(array_sort_all_codigo);

        et_b_codigo.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_search, 0, 0, 0);
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
                            array_sort_all_id_explos_ins.add(al_id_explosion_ins.get(i));
                            array_sort_all_moneda.add(al_moneda.get(i));
                            array_sort_all_por_requerir.add(al_por_requi.get(i));
                            array_sort_all_select.add(al_select.get(i));
                            array_sort_all_unidad.add(al_unidad.get(i));
                        }
                    }
                }
                CustomAdapter_Preguntas customAdapter=new CustomAdapter_Preguntas(activity, array_sort_all_codigo,array_sort_all,
                        array_sort_all_unidad,array_sort_all_moneda,array_sort_all_cantidad,array_sort_all_por_requerir,array_sort_all_cant_total,array_sort_all_id,
                        al_select,array_sort_all_id_explos_ins);
                lv_preguntas.setAdapter(customAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_b_descrip.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_search, 0, 0, 0);
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
                            array_sort_all_id_explos_ins.add(al_id_explosion_ins.get(i));
                            array_sort_all_moneda.add(al_moneda.get(i));
                            array_sort_all_por_requerir.add(al_por_requi.get(i));
                            array_sort_all_select.add(al_select.get(i));
                            array_sort_all_unidad.add(al_unidad.get(i));
                        }
                    }
                }
                CustomAdapter_Preguntas customAdapter=new CustomAdapter_Preguntas(activity, array_sort_all_codigo,array_sort_all,
                        array_sort_all_unidad,array_sort_all_moneda,array_sort_all_cantidad,array_sort_all_por_requerir,array_sort_all_cant_total,array_sort_all_id,
                        al_select,array_sort_all_id_explos_ins);
                lv_preguntas.setAdapter(customAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        seleccionar_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < array_sort_all.size(); i++) {
                    View view2 = lv_preguntas.getAdapter().getView(i, null, lv_preguntas);
                    if (view2 != null) {

                        //Textviews
                        TextView textView = (TextView) view2.findViewById(R.id.tv_ids_insumos); //ids
                        TextView textView2 = (TextView) view2.findViewById(R.id.textView21); //codigo
                        TextView textView3 = (TextView) view2.findViewById(R.id.tv_descrip);
                        TextView textView4 = (TextView) view2.findViewById(R.id.tv_unidad);
                        TextView textView5 = (TextView) view2.findViewById(R.id.tv_moneda);
                        TextView textView6 = (TextView) view2.findViewById(R.id.tv_cantidad); //Por requerir global
                        TextView textView7 = (TextView) view2.findViewById(R.id.textView37); // cantidad total
                        TextView textView8 = (TextView) view2.findViewById(R.id.textView74);

                        pregunt = textView.getText().toString();
                        s_codigo = textView2.getText().toString();
                        s_descripcion = textView3.getText().toString();
                        s_unidad = textView4.getText().toString();
                        s_moneda = textView5.getText().toString();
                        s_por_requerir = textView6.getText().toString();
                        s_total_global = textView7.getText().toString();
                        id_explosion_ins=textView8.getText().toString();

                        //Checkbox
                        CheckBox cb_insumo=(CheckBox)view2.findViewById(R.id.checkBox);
                        is_active = cb_insumo.isChecked();

                        if (is_active) {
                            //Consulta para insertar en la tabla temporal de los insumos
                            con.DAO_Guarda_Insumos_temporal(pregunt,s_codigo,s_descripcion,s_unidad,s_moneda,s_por_requerir,s_total_global,id_explosion_ins);
                        }
                        else{
                            con.DAO_Elimina_Insumos_temporal(pregunt);
                        }
                    }
                }
                dialog.dismiss();
                Requisiciones refresca = new Requisiciones();
                refresca.Refresca_lista();
            }
        });

        dialog.show();
    }

    public void Alerta(){

        AlertDialog.Builder myDialog
                = new AlertDialog.Builder(var.getActivity_gral());
        myDialog.setTitle("Alerta");
        TextView textView = new TextView(var.getActivity_gral());
        textView.setText("La cantidad por estimar más las cantidades estimadas previamente son mayores a la cantidad total.");

        ViewGroup.LayoutParams textViewLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewLayoutParams);
        LinearLayout layout = new LinearLayout(var.getActivity_gral());
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(textView);
        myDialog.setView(layout);
        myDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        myDialog.show();
    }
    public void Alerta2(){

        AlertDialog.Builder myDialog
                = new AlertDialog.Builder(var.getActivity_gral());
        myDialog.setTitle("Alerta");
        TextView textView = new TextView(var.getActivity_gral());
        textView.setText("No puede guardar un valor vacío en el campo Por estimar.");

        ViewGroup.LayoutParams textViewLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewLayoutParams);
        LinearLayout layout = new LinearLayout(var.getActivity_gral());
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

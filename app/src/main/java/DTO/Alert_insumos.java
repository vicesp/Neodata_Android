package DTO;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import app.android.my_app.Insumos;
import app.android.my_app.Menu_seleccion;
import app.android.my_app.R;
import app.android.my_app.Requisiciones;

public class Alert_insumos extends DialogFragment {

    ArrayList<String> al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_observaciones,al_por_requi,al_cantidad_total,al_ids;
    ArrayList<Integer> al_respuestas;
     ListView lv_preguntas;
    View vi;
    Dialog dialog;
    Boolean is_active;
    private String pregunt,s_codigo,s_descripcion,s_unidad,s_moneda,s_por_requerir,s_total_global;

    ImageButton cerrar;
    TextView tv_boton_sel;
    consultas con;

    Variables_Universales var;
    String[] Respuesta_observ=null;
    String sms_enviado="0",email_enviado="0";

    public void show_Dialog(final Activity activity) {
         dialog = new Dialog(activity);
        //Variables.setDialog(dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_insumos);

        con=new consultas();
        var=new Variables_Universales();
        var.setContext(activity.getApplicationContext());

        //var.getActivity_gral().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //activity.getApplicationContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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

        cerrar=(ImageButton) dialog.findViewById(R.id.ib_ap_cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        consultas lista=new consultas();
        lista=con.DAO_Lista_insumos(var.getId_proyecto_select());
        al_codigo = new ArrayList<String>(Arrays.asList(lista.arreglo));
        al_descrip = new ArrayList<String>(Arrays.asList(lista.arreglo2));
        al_unidad = new ArrayList<String>(Arrays.asList(lista.arreglo_3));
        al_moneda = new ArrayList<String>(Arrays.asList(lista.arreglo_4));
        al_cantidad = new ArrayList<String>(Arrays.asList(lista.arreglo_5));
        al_observaciones = new ArrayList<String>(Arrays.asList(lista.arreglo_6));
        al_por_requi = new ArrayList<String>(Arrays.asList(lista.arreglo_7));
        al_cantidad_total = new ArrayList<String>(Arrays.asList(lista.arreglo_8));
        al_ids = new ArrayList<String>(Arrays.asList(lista.arreglo_9));

        CustomAdapter_Insumos_Activity customAdapter=new CustomAdapter_Insumos_Activity(activity, al_codigo,al_descrip,al_unidad,al_moneda,al_cantidad,al_por_requi,al_cantidad_total,al_ids);
        lv_preguntas.setAdapter(customAdapter);

        lv_preguntas.showContextMenu();
        lv_preguntas.setScrollingCacheEnabled(true);
        lv_preguntas.setScrollbarFadingEnabled(true);
        lv_preguntas.setFastScrollEnabled(true);
        lv_preguntas.setCacheColorHint(0);

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

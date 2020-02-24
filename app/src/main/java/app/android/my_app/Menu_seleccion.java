package app.android.my_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import DTO.Alert_insumos;
import DTO.Variables_Universales;
import DTO.consultas;

public class Menu_seleccion extends AppCompatActivity {

    ImageButton ib_condominios, ib_config,ib_sincro,ib_insumos;
    TextView tv_user;
    Variables_Universales var;
    private ImageView iv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_seleccion);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        var=new Variables_Universales();

        tv_user=(TextView)findViewById(R.id.textView30);
        tv_user.setText(var.getNombre_usuario());


        ib_condominios = (ImageButton)findViewById(R.id.ib_ms_condominios);
        ib_config = (ImageButton)findViewById(R.id.ib_ms_config);
        ib_sincro = (ImageButton)findViewById(R.id.ib_ms_sinc);
        ib_insumos = (ImageButton)findViewById(R.id.imageButton);

        iv_logo=(ImageView)findViewById(R.id.imageView);
        byte[] data1=null;
        consultas con = new consultas();
        data1 = con.DAO_Select_Foto_LOGO_Panel_Control();

        if(data1!=null) {
            Bitmap originalBitmap = (BitmapFactory.decodeByteArray(data1, 0, data1.length));
            iv_logo.setImageBitmap(originalBitmap);

        }

        ib_insumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_seleccion.this, Insumos.class));

               /* var= new Variables_Universales();
                var.setContext(Menu_seleccion.this);
                var.setView_gen(getWindow().getDecorView().getRootView());
                var.setActivity_gral(Menu_seleccion.this);

                //Menu_seleccion.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                Alert_insumos ap=new Alert_insumos();
                ap.show_Dialog(Variables_Universales.getActivity_gral());*/
            }
        });

        ib_condominios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Menu_seleccion.this, Requisiciones.class));

            }
        });

        ib_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Variables_Universales.getNombre_usuario().contentEquals("Admin")) {
                    startActivity(new Intent(Menu_seleccion.this, Configuraciones.class));
                }
                else{

                    Alerta();
                }
            }
        });

        ib_sincro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_seleccion.this, Sincronizar.class));
            }
        });

    }

    public void Alerta(){

        AlertDialog.Builder myDialog
                = new AlertDialog.Builder(Menu_seleccion.this);
        myDialog.setTitle("Alerta");
        TextView textView = new TextView(this);
        textView.setText("El usuario no tiene privilegios para ingresar a las configuraciones");

        ViewGroup.LayoutParams textViewLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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


}

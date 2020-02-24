package DTO;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sistemas on 09/03/2017.
 */

public class Alert extends AppCompatActivity {

    private String titulo,mensaje;



    public Alert(String titulo, String mensaje){
        this.titulo=titulo;
        this.mensaje=mensaje;

    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void mostrar(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Variables_Universales.getContext());

        alertDialogBuilder.setTitle(titulo);

        alertDialogBuilder.setMessage(mensaje);

        alertDialogBuilder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface dialog, int id) {


        }

    });

    AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

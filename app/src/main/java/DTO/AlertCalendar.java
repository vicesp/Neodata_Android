package DTO;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.Calendar;

import app.android.my_app.Requisiciones;
import app.android.my_app.Sincronizar;


public class AlertCalendar {

	Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    public DatePickerDialog dialogee = new DatePickerDialog(Variables_Universales.getContext(),
            new mDateSetListener(), mYear, mMonth, mDay);

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();

            int mMonth = monthOfYear+1;

            String mes_mod;
            String dia_mop;

            if (dayOfMonth<10){dia_mop="0"+dayOfMonth;}
            else{dia_mop=""+dayOfMonth;}

            if (mMonth<10){mes_mod="0"+mMonth;}
            else{mes_mod=""+mMonth;}
            
            
            Variables_Universales.setFecha_inicio_trat(dia_mop+"-"+mes_mod+"-"+year);

            if (Variables_Universales.getCalendar_desde().contentEquals("sincro_desde")){
                Requisiciones sincronizar= new Requisiciones();
                sincronizar.llena_fecha_sinc(1);
            }
            else if (Variables_Universales.getCalendar_desde().contentEquals("sincro_hasta")){
                Requisiciones sincronizar= new Requisiciones();
                sincronizar.llena_fecha_sinc(0);
            }
        }
    }

}
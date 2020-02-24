package DTO;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Vicente on 08/07/2018.
 */

public class Valores_Recibidos_Insumos implements KvmSerializable {
    public String v_id_insumo,v_nom_insumo,v_descrip,v_unidad,v_id_explos_ins,v_por_req_global,v_cant_total;

    public Valores_Recibidos_Insumos(String v_id_insumo, String v_nom_insumo, String v_descrip, String v_unidad,
                                     String v_id_explos_ins, String v_por_req_global, String v_cant_total) {
        super();
        this.v_id_insumo = v_id_insumo;
        this.v_nom_insumo = v_nom_insumo;
        this.v_descrip = v_descrip;
        this.v_unidad = v_unidad;
        this.v_id_explos_ins = v_id_explos_ins;
        this.v_por_req_global = v_por_req_global;
        this.v_cant_total = v_cant_total;
    }
    public Valores_Recibidos_Insumos(){
        v_id_insumo="";
        v_nom_insumo="";
        v_descrip="";
        v_unidad="";
        v_id_explos_ins="";
        v_por_req_global="";
        v_cant_total="";
    }

    @Override
    public Object getProperty(int i) {
        switch(i)
        {
            case 0:
                return v_id_insumo;
            case 1:
                return v_nom_insumo;
            case 2:
                return v_descrip;
            case 3:
                return v_unidad;
            case 4:
                return v_id_explos_ins;
            case 5:
                return v_por_req_global;
            case 6:
                return v_cant_total;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 7;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch(i)
        {
            case 0:
                v_id_insumo = o.toString();
                break;
            case 1:
                v_nom_insumo = o.toString();
                break;
            case 2:
                v_descrip = o.toString();
                break;
            case 3:
                v_unidad = o.toString();
                break;
            case 4:
                v_id_explos_ins = o.toString();
                break;
            case 5:
                v_por_req_global = o.toString();
                break;
            case 6:
                v_cant_total = o.toString();
                break;

            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch(i)
        {
            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "idInsumo";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "insumo";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "descripcionlarga";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "unidad";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "idexplosioninsumos";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "porRequerirglobal";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "cantidadtotal";
                break;

            default:break;
        }

    }
}

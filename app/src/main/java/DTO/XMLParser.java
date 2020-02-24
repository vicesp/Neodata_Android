package DTO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLParser extends DefaultHandler
{

    public List<XmlValuesModel> listXML=null;
    // string builder acts as a buffer
    StringBuilder builder;


    XmlValuesModel Valores_Insumos=null;
    XmlValuesModel Valores_Requi=null;
    XmlValuesModel Valores_Requi_detalle=null;

    consultas con = new consultas();

    @Override
    public void startDocument() throws SAXException {
        /******* Create ArrayList To Store XmlValuesModel object ******/
        listXML = new ArrayList<XmlValuesModel>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        builder=new StringBuilder();
         if(localName.equals("r_insumos")){
            Valores_Insumos = new XmlValuesModel();
        }
        else if(localName.equals("r_requi")){
            Valores_Requi = new XmlValuesModel();
        }
         else if(localName.equals("r_detalle")){
             Valores_Requi_detalle = new XmlValuesModel();
         }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

/////////     para el catalogo de Insumos //////////////////////

         if(localName.equals("r_insumos")){

            listXML.add(Valores_Insumos);
            con.DAO_Sync_Insumos(Valores_Insumos.getR_ins_id(),Valores_Insumos.getR_ins_codigo(),Valores_Insumos.getR_ins_descrip(),
                    Valores_Insumos.getR_ins_unidad(),Valores_Insumos.getR_ins_d_mon(),Valores_Insumos.getR_ins_global(),
                    Valores_Insumos.getR_ins_total(),"",Valores_Insumos.getR_ins_tipo(),Valores_Insumos.getR_ins_bloq(),Valores_Insumos.getR_ins_topar_cant(),
                    Valores_Insumos.getR_ins_es_inven(),Valores_Insumos.getR_ins_is_admin(),Valores_Insumos.getR_ins_no_most(),Valores_Insumos.getR_ins_id_proyecto(),Valores_Insumos.getR_ins_id_explosion());
        }

        else  if(localName.equalsIgnoreCase("id_insum")){
            Valores_Insumos.setR_ins_id(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("insumo")){
             Valores_Insumos.setR_ins_codigo(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("d_l")){
             Valores_Insumos.setR_ins_descrip(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("unidad")){
             Valores_Insumos.setR_ins_unidad(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("d_m")){
             Valores_Insumos.setR_ins_d_mon(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("r_global")){
             Valores_Insumos.setR_ins_global(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("c_total")){
             Valores_Insumos.setR_ins_total(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("tipo")){
             Valores_Insumos.setR_ins_tipo(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("bloqueado")){
             Valores_Insumos.setR_ins_bloq(builder.toString());
        }
         else  if(localName.equalsIgnoreCase("toparcantidad")){
             Valores_Insumos.setR_ins_topar_cant(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("is_inven")){
             Valores_Insumos.setR_ins_es_inven(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("admini")){
             Valores_Insumos.setR_ins_is_admin(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("no_mostrar")){
             Valores_Insumos.setR_ins_no_most(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("idproy_i")){
             Valores_Insumos.setR_ins_id_proyecto(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("id_ins")){
             Valores_Insumos.setR_ins_id_explosion(builder.toString());
         }

        /////////     para Requisiciones  //////////////////////

        else if(localName.equals("r_requi")){

            listXML.add(Valores_Requi);
            con.DAO_Sync_Requisicion(Valores_Requi.getRequi_id(),Valores_Requi.getRequi_folio(),Valores_Requi.getRequi_nom_proy(),Valores_Requi.getRequi_solicit(),
                    Valores_Requi.getRequi_fecha(),Valores_Requi.getRequi_fecha_entrega(),Valores_Requi.getRequi_codigo_aux(),Valores_Requi.getRequi_contrato(),
                    Valores_Requi.getRequi_tecnica(),Valores_Requi.getRequi_compra(),Valores_Requi.getRequi_observaciones(),Valores_Requi.getRequi_lugar_entrega(),
                    Valores_Requi.getRequi_cancelada(),Valores_Requi.getRequi_pedida_total());
        }

         else  if(localName.equalsIgnoreCase("idrequisicion")){
             Valores_Requi.setRequi_id(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("requisicion")){
             Valores_Requi.setRequi_folio(builder.toString());
         }

        else  if(localName.equalsIgnoreCase("proyecto")){
             Valores_Requi.setRequi_nom_proy(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("nombresolicitante")){
             Valores_Requi.setRequi_solicit(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("fecha")){
             Valores_Requi.setRequi_fecha(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("fechaposibleentrega")){
             Valores_Requi.setRequi_fecha_entrega(builder.toString());
        }
        else  if(localName.equalsIgnoreCase("codigoauxiliar")){
             Valores_Requi.setRequi_codigo_aux(builder.toString());
        }

         else  if(localName.equalsIgnoreCase("contrato")){
             Valores_Requi.setRequi_contrato(builder.toString());
         }

         else  if(localName.equalsIgnoreCase("tecnica")){
             Valores_Requi.setRequi_tecnica(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("compra")){
             Valores_Requi.setRequi_compra(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("observaciones")){
             Valores_Requi.setRequi_observaciones(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("lugarentregamat")){
             Valores_Requi.setRequi_lugar_entrega(builder.toString());
        }
         else  if(localName.equalsIgnoreCase("cancelada")){
             Valores_Requi.setRequi_cancelada(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("pedidatotalmente")){
             Valores_Requi.setRequi_pedida_total(builder.toString());
         }

         /////////     para Requisiciones DETALLE  //////////////////////

         else if(localName.equals("r_detalle")){

             listXML.add(Valores_Requi_detalle);
             con.DAO_Sincro_Insumos_Requisicion(Valores_Requi_detalle.getD_id_insumo(),Valores_Requi_detalle.getD_id_req(),Valores_Requi_detalle.getD_insumo(),
                     Valores_Requi_detalle.getD_desc_larga(),Valores_Requi_detalle.getD_unidad(),Valores_Requi_detalle.getD_moneda(),Valores_Requi_detalle.getD_cant_req(),
                     Valores_Requi_detalle.getD_cant_total(),Valores_Requi_detalle.getD_observ(),Valores_Requi_detalle.getD_renglon(),Valores_Requi_detalle.getD_id_p(),Valores_Requi_detalle.getD_id_explosion_ins());
         }

         else  if(localName.equalsIgnoreCase("idreq_d")){
             Valores_Requi_detalle.setD_id_req(builder.toString());
         }

         else  if(localName.equalsIgnoreCase("Renglon")){
             Valores_Requi_detalle.setD_renglon(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("idInsumo")){
             Valores_Requi_detalle.setD_insumo(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("dl_d")){
             Valores_Requi_detalle.setD_desc_larga(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("u_d")){
             Valores_Requi_detalle.setD_unidad(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("m_d")){
             Valores_Requi_detalle.setD_moneda(builder.toString());
         }

         else  if(localName.equalsIgnoreCase("ct_d")){
             Valores_Requi_detalle.setD_cant_total(builder.toString());
         }

         else  if(localName.equalsIgnoreCase("cantpedi_d")){
             Valores_Requi_detalle.setD_cant_req(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("o_d")){
             Valores_Requi_detalle.setD_observ(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("idi_d")){
             Valores_Requi_detalle.setD_id_insumo(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("idp_d")){
             Valores_Requi_detalle.setD_id_p(builder.toString());
         }
         else  if(localName.equalsIgnoreCase("i_d")){
             Valores_Requi_detalle.setD_id_explosion_ins(builder.toString());
         }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        /******  Read the characters and append them to the buffer  ******/
        String tempString=new String(ch, start, length);
        builder.append(tempString);
    }
}
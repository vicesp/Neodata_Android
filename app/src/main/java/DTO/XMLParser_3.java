package DTO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLParser_3 extends DefaultHandler
{

    public List<XmlValuesModel> listXML=null;
    // string builder acts as a buffer
    StringBuilder builder;
String valores="";
    XmlValuesModel Valores_I=null;
Variables_Universales var;
    consultas con = new consultas();

    @Override
    public void startDocument() throws SAXException {
        /******* Create ArrayList To Store XmlValuesModel object ******/
        listXML = new ArrayList<XmlValuesModel>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        /****  When New XML Node initiating to parse this function called *****/
        // Create StringBuilder object to store xml node value
        builder=new StringBuilder();

        if(localName.equals("resp_valor")){
            Valores_I = new XmlValuesModel();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        var=new Variables_Universales();
        if(localName.equals("resp_valor")){
            listXML.add( Valores_I );
            con.DAO_Update_insumos_e_insumosRequi(Valores_I.getV_id_ins(),Valores_I.getV_id_explosion_ins(),var.getId_proyecto_select(),Valores_I.getV_cant_por_req_global(),
                    Valores_I.getV_cant_total());

            String insumos="";
            insumos="-Código: "+Valores_I.getV_insumo()+" -Descripción: "+Valores_I.getV_desc_insumo()+" *Cantidad: "+Valores_I.getV_cant_por_req_global()+"\n";
            var.setMensaje_insumos(var.getMensaje_insumos()+insumos);

        }

        else  if(localName.equalsIgnoreCase("idInsumo")){
            Valores_I.setV_id_ins(builder.toString());
        }
        else if(localName.equalsIgnoreCase("insumo")){
            Valores_I.setV_insumo(builder.toString());
        }
        else if(localName.equalsIgnoreCase("descripcionlarga")){
            Valores_I.setV_desc_insumo(builder.toString());
        }
        else if(localName.equalsIgnoreCase("unidad")){
            Valores_I.setV_unidad(builder.toString());
        }
        else if(localName.equalsIgnoreCase("idexplosioninsumos")){
            Valores_I.setV_id_explosion_ins(builder.toString());
        }
        else if(localName.equalsIgnoreCase("porRequerirglobal")){
            Valores_I.setV_cant_por_req_global(builder.toString());
        }
        else if(localName.equalsIgnoreCase("cantidadtotal")){
            Valores_I.setV_cant_total(builder.toString());
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
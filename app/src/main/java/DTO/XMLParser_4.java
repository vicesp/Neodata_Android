package DTO;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLParser_4 extends DefaultHandler
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

        if(localName.equals("ins_req")){
            Valores_I = new XmlValuesModel();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        var=new Variables_Universales();
        if(localName.equals("ins_req")){
            listXML.add( Valores_I );
            con.DAO_Update_ID_requi(var.getId_requi_mandada(),Valores_I.getFolioRequisicion(),Valores_I.getIdRequisicion());

            String insumos="";
            insumos="Se actualizó el folio de la requisición a: "+Valores_I.getFolioRequisicion()+" ";
            var.setMensaje_insumos(var.getMensaje_insumos()+insumos);

        }

        else  if(localName.equalsIgnoreCase("IdRequisicion")){
            Valores_I.setIdRequisicion(builder.toString());
        }
        else if(localName.equalsIgnoreCase("FolioRequisicion")){
            Valores_I.setFolioRequisicion(builder.toString());
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